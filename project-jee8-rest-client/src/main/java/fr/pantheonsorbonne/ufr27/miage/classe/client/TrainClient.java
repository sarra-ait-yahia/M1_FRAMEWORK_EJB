package fr.pantheonsorbonne.ufr27.miage.classe.client;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.validation.Validator;

import org.eclipse.persistence.internal.oxm.Marshaller;

import fr.pantheonsorbonne.ufr27.miage.appel.rest.AccesRest;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.FreeTrialPlan;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Passage;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Perturbation;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.TableauDeBord;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Trajet;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.VoyageDuJour;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

public class TrainClient implements Runnable {

	@Inject
	ObjectFactory factory; 
	
    protected String idTrain;
    protected List<Voyage> listVoyage;
    protected List<Passage> listPassage;
    protected Voyage voyageActuel;
    protected String type = this.voyageActuel.getTrain().getType();
    protected TableauDeBord tableauDeBord;
    protected List<Passage> passages;
    protected Passage passageActuel;
    protected Double distanceparcouru;
    protected Perturbation perturbationActuel;
    protected List<PerturbationTrain> perturbations;
    protected double vitesseActuel;
    protected int time = 0;
    protected int retard = 0;
    
    
	
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


	public double getVitesseActuel() {
		return vitesseActuel;
	}

	public void setVitesseActuel(Boolean ChangeVoyage) {
		if(ChangeVoyage)
			this.vitesseActuel = this.voyageActuel.getVitesse();
		
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
		this.distanceparcouru = this.vitesseActuel * time;
		
	}
	
	
	public void getVoyagesDuJour(){
		AccesRest rest = new AccesRest(this.idTrain,this.time);
		VoyageDuJour voyagesDuJour= rest.getListVoyages();
		if(voyagesDuJour != null) {
			this.listVoyage = voyagesDuJour.getVoyages();
			this.type = listVoyage.get(0).getTrain().getType();
		}	
	}
	

	public void postInfoVoyageActuel() {
		AccesRest rest = new AccesRest(this.idTrain,this.time);
		Voyage voyageAenvoye = this.voyageActuel.clone();
		this.tableauDeBord.setDistanceParcouru(this.distanceparcouru);
		this.tableauDeBord.setHeure(this.time);
		this.tableauDeBord.setRetard(this.retard);
		voyageAenvoye.getTrain().setTableauDeBord(this.tableauDeBord);
		voyageAenvoye.getTrain().setPerturbation(this.perturbationActuel);
		rest.postInfoVoyage(voyageAenvoye);
	}
	
	public void move(int time) {
		if(this.passageActuel.getHeureArrivee()>=time) {
		     calculeDistanceParcouru(time-this.voyageActuel.getHeureDepart());
		}
	}
	
	
	
	@Override
	public void run() {
		getVoyagesDuJour();
		int nombreVoyage;
		int compteurVoyage = 1;
		int nombrePassageVoyage;
		int compteurPassage = 1;
		Boolean ChangeVoyage = true;
		while (true) {
			if(this.listVoyage.get(compteurVoyage).getHeureDepart() == time || this.listVoyage.get(compteurVoyage).getHeureDepart() > time) {
				this.voyageActuel = this.listVoyage.get(compteurVoyage);
				setVitesseActuel(ChangeVoyage);
				ChangeVoyage = false;
				nombreVoyage = this.listVoyage.size();
				this.listPassage = this.voyageActuel.getPassages();
				nombrePassageVoyage = this.listPassage.size();
				if(this.listPassage.get(compteurPassage).getHeureDepartModifie() == time){
				      this.passageActuel = this.listPassage.get(compteurPassage);
				      }
				move(time);
				if(this.passageActuel.getHeureArriveeModifie()==time) {
					if(compteurPassage++ >= nombrePassageVoyage )
						if(compteurVoyage++ >= nombreVoyage)
							break;
						else {
							compteurVoyage++;
							ChangeVoyage = true;
					        this.distanceparcouru = (double) 0 ;
						}
					else 
						compteurPassage++;
				}
			    
			}
			
			if(time % 5 == 0) {
				getVoyagesDuJour();
			}
		
		/*for (PerturbationTrain p : perturbations) {
			if (p.getHeure() == time) {
				this.perturbationActuel.setDuree(p.getDuree());
				this.perturbationActuel.setHeure(p.getHeure());
				String type = (p instanceof Retard)? "Retard" : ((Incident) p ).getType();
				this.perturbationActuel.setType(type);
				this.retard = p.getDuree();
				postInfoVoyageActuel();
			}
		}
*/
		time++;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

}
