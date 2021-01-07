package fr.pantheonsorbonne.ufr27.miage;

import java.util.List;

import javax.annotation.Generated;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.validation.Validator;

import org.eclipse.persistence.internal.oxm.Marshaller;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
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
    protected Voyage voyageActuel;
    protected String type;
    protected TableauDeBord tableauDeBord;
    protected List<Trajet> trajets;
    protected Trajet trajetActuel;
    protected Double distanceparcouru;
    protected List<Perturbation> perturbations;
    protected double vitesse;
    int time = 0;
    
    public String getIdTrain() {
		return idTrain;
	}
	public void setIdTrain(String idTrain) {
		this.idTrain = idTrain;
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
	public List<Perturbation> getPerturbations() {
		return perturbations;
	}
	public void setPerturbations(List<Perturbation> perturbations) {
		this.perturbations = perturbations;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	
	public TrainClient(String id) {
	        this.idTrain = id;	
	    }
	
	
	public void calculeDistanceParcouru(int retard) {
		this.distanceparcouru = this.vitesse * time;
		
	}
	
	public void getVoyage(int n) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		/*faire le get et extraire le xsd, mise à jour des données*/
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
	
	@Override
	public void run() {
		
		//n= getNombre des voyage du jour
		//int i = 1;
		//getVoyage(i)
		while (true) {
		/* 
		 if(time est multiple de 5){
		     getVoyageN(i)
		     }
		  if(this.distanceParcouru = voyage.getDistance){
		   i++;
		   getVoyage(i);
		   }
		 
		 */
		for (Perturbation p : perturbations) {
			if (p.getHeure() == time) {
				calculeDistanceParcouru(p.getDuree());
				this.vitesse += p.getModificationVitesse();
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
