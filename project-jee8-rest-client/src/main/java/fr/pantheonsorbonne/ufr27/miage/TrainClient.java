package fr.pantheonsorbonne.ufr27.miage;

import java.util.List;

import javax.annotation.Generated;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.validation.Validator;

import org.eclipse.persistence.internal.oxm.Marshaller;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Passage;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Perturbation;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.TableauDeBord;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Trajet;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

public class TrainClient implements Runnable{

    protected String idTrain;
    protected List<Voyage> listVoyage;
    protected List<Passage> listPassage;
    protected Voyage voyageActuel;
    protected String type;
    protected TableauDeBord tableauDeBord;
    protected List<Passage> passages;
    protected Passage passageActuel;
    protected Double distanceparcouru;
    protected List<PerturbationTrain> perturbations;
    protected double vitesse;
    int time = 0;
    int retard = 0;
    
    
	
	public String getIdTrain() {
		return idTrain;
	}


	public void setIdTrain(String idTrain) {
		this.idTrain = idTrain;
	}


	public List<Voyage> getListVoyage() {
		return listVoyage;
	}


	public void setListVoyage(List<Voyage> listVoyage) {
		this.listVoyage = listVoyage;
	}


	public Voyage getVoyageActuel() {
		return voyageActuel;
	}


	public void setVoyageActuel(Voyage voyageActuel) {
		this.voyageActuel = voyageActuel;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public TableauDeBord getTableauDeBord() {
		return tableauDeBord;
	}


	public void setTableauDeBord(TableauDeBord tableauDeBord) {
		this.tableauDeBord = tableauDeBord;
	}


	public List<Passage> getPassages() {
		return passages;
	}


	public void setPassages(List<Passage> passages) {
		this.passages = passages;
	}


	public Passage getPassageActuel() {
		return passageActuel;
	}


	public void setPassageActuel(Passage passageActuel) {
		this.passageActuel = passageActuel;
	}


	public Double getDistanceparcouru() {
		return distanceparcouru;
	}


	public void setDistanceparcouru(Double distanceparcouru) {
		this.distanceparcouru = distanceparcouru;
	}


	public List<PerturbationTrain> getPerturbations() {
		return perturbations;
	}


	public void setPerturbations(List<PerturbationTrain> perturbations) {
		this.perturbations = perturbations;
	}


	public double getVitesse() {
		return vitesse;
	}


	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}


	public int getTime() {
		return time;
	}


	public void setTime(int time) {
		this.time = time;
	}


	public int getRetard() {
		return retard;
	}


	public void setRetard(int retard) {
		this.retard = retard;
	}


	public TrainClient(String id) {
	        this.idTrain = id;	
	    }
	
	
	public void calculeDistanceParcouru(int time) {
		this.distanceparcouru = this.vitesse * time;
		
	}
	
	public void getListVoyages() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		Response resp = target.path("listVoyageDuTrain").queryParam("trainId", this.idTrain).request().accept(MediaType.APPLICATION_XML).get();
		String xmlString = resp.readEntity(String.class);
		/*faire le get et extraire le xsd, mise à jour des données*/
		/*mettre les voyages dans une liste 
	}
	
	@SuppressWarnings("deprecation")
	public void postVoyage(int n) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		/*faire le get et extraire le xsd, mise à jour des données*/
		ObjectFactory objFactory = new ObjectFactory();
        Voyage voyage = (Voyage) objFactory.createVoyage();
        JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance("fr.pantheonsorbonne.ufr27.miage.model.jaxb");
			jaxbContext.createMarshaller().marshal(voyage,System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public void move(int time) {
		if(this.passageActuel.getHeureArrivee()>=time) {
		     calculeDistanceParcouru(time-this.voyageActuel.getHeureDepart());
		}
	}
	
	
	@Override
	public void run() {
		
		getListVoyages();
		int nombreVoyage;
		int compteurVoyage = 1;
		int nombrePassageVoyage;
		int compteurPassage = 1;
		while (true) {
			if(this.listVoyage.get(compteurVoyage).getHeureDepart() == time || this.listVoyage.get(compteurVoyage).getHeureDepart() > time) {
				this.voyageActuel = this.listVoyage.get(compteurVoyage);
				nombreVoyage = this.listVoyage.size();
				this.listPassage = this.voyageActuel.getPassages();
				nombrePassageVoyage = this.listPassage.size();
				if(this.listPassage.get(compteurPassage).getHeureDepart() == time){
				      this.passageActuel = this.listPassage.get(compteurPassage);
				      }
				move(time);
				if(this.passageActuel.getHeureArrivee()==time) {
					if(compteurPassage++ >= nombrePassageVoyage )
						if(compteurVoyage++ >= nombreVoyage)
							break;
						else {
							compteurVoyage++;
					        this.distanceparcouru = (double) 0 ;
						}
					else 
						compteurPassage++;
				}
			    
			}
			
			if(time % 5 == 0)
				getListVoyages();
		
		for (PerturbationTrain p : perturbations) {
			if (p.getHeure() == time) {
				
				//calcul distanceParcouru
				//postVoyage(i)
			}
		}

		time++;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

}
