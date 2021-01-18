package fr.pantheonsorbonne.ufr27.miage.classe.client;

import java.util.ArrayList;
import java.util.Collections;
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
    protected String type ;
    protected TableauDeBord tableauDeBord;
    protected List<Passage> passages = new ArrayList<Passage>();
    protected Passage passageActuel;
    protected Double distanceparcouru;
    protected Perturbation perturbationActuel;
    protected List<PerturbationTrain> perturbations = new ArrayList<PerturbationTrain>();
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
		this.distanceparcouru = this.vitesseActuel * (time/60);
		
	}
	
	
	public void getVoyagesDuJour(){
		AccesRest rest = new AccesRest(this.idTrain,this.time);
		VoyageDuJour voyagesDuJour= rest.getListVoyages();
		if(voyagesDuJour != null) {
			this.listVoyage = voyagesDuJour.getVoyages();
			Collections.sort(listVoyage);
			for (Voyage v : this.listVoyage) {
				Collections.sort(v.getPassages());
			}
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
		List<Passage> passages = new ArrayList<Passage>();
		passages.add(this.passageActuel);
		voyageAenvoye.setPassages(passages);
		rest.postInfoVoyage(voyageAenvoye);
	}
	
	public void move() {
		if(this.passageActuel != null) {
		     calculeDistanceParcouru(this.time-this.voyageActuel.getHeureDepart());
		}
	}
	
	private int indexVoyageNonsupprime(int index) {
		int i = index;
		while (this.listVoyage.get(i).getStatut() == "supprime") {
			i++;
		}
		return i;
	}
	
	@Override
	public void run() {
		getVoyagesDuJour();
		int nombreVoyage;
		int compteurVoyage = indexVoyageNonsupprime(0);
		int nombrePassageVoyage;
		int compteurPassage = 0;
		Boolean ChangeVoyage = true;
		int indexVoyagePrecedent = compteurVoyage;
		int indexPassagePrecedent = compteurPassage;
		while (true) {
			nombreVoyage = this.listVoyage.size();
			if(compteurVoyage < nombreVoyage) {
				if(this.listVoyage.get(compteurVoyage).getHeureDepartModifie() == time ) {
					this.voyageActuel = this.listVoyage.get(compteurVoyage);
				}else if( this.listVoyage.get(compteurVoyage).getHeureDepartModifie() > time && indexVoyagePrecedent != compteurVoyage) {
					this.voyageActuel = null;
					this.passageActuel = null;
					indexVoyagePrecedent = compteurVoyage;
				}
			}
			if(this.voyageActuel != null) {
				
					setVitesseActuel(ChangeVoyage);
					ChangeVoyage = false;
					this.listPassage = this.voyageActuel.getPassages();
					nombrePassageVoyage = this.listPassage.size();
					if(this.listPassage.get(compteurPassage).getHeureDepartModifie() == time){
					      this.passageActuel = this.listPassage.get(compteurPassage);
					      }
					if(this.listPassage.get(compteurPassage).getHeureDepartModifie() > time && indexPassagePrecedent != compteurPassage){
					      this.passageActuel = null;
					      indexPassagePrecedent = compteurPassage;
					      }
					move();
					if(this.passageActuel != null && this.passageActuel.getHeureArriveeModifie()==time) {
						if((compteurPassage+1) >= nombrePassageVoyage )
							if((compteurVoyage+1) >= nombreVoyage)
								this.voyageActuel = null;
							else {
								indexVoyagePrecedent = compteurVoyage;
								compteurVoyage = indexVoyageNonsupprime(compteurVoyage++);
								ChangeVoyage = true;
						        this.distanceparcouru = (double) 0 ;
							}
						else {
							indexPassagePrecedent = compteurPassage;
							compteurPassage++;
						}
						    
					}
					
					if(time % 20 == 0) {
						if(this.passageActuel != null) {
						  int numSegments = this.passageActuel.getSegments().size();
						  System.out.print("heure : "+this.time+"train : "+this.idTrain+ " roule entre "+this.passageActuel.getSegments().get(0).getStationA()+ " et "+this.passageActuel.getSegments().get(numSegments-1).getStationB()+"\n");
						}
						  else {
							Passage passagePrecedent = this.voyageActuel.getPassages().get(compteurPassage-1);
							int numSegments = passagePrecedent.getSegments().size();
							System.out.print("heure : "+this.time+"Le train : "+this.idTrain+ " est stationné à la gare : "+this.passageActuel.getSegments().get(0).getStationA()+ " et "+passagePrecedent.getSegments().get(numSegments-1).getStationB()+"\n");
							
						}
					}
				    
				}
			
			
			if(time % 5 == 0) {
				getVoyagesDuJour();
			}
			
			if(this.voyageActuel == null) {
				System.out.print("le train: "+this.idTrain+ " n'est pas en train d'effectuer un voyage");
			}
			
		
		for (PerturbationTrain p : perturbations) {
			if (p.getHeure() == time) {
				this.perturbationActuel.setDuree(p.getDuree());
				this.perturbationActuel.setHeure(p.getHeure());
				String type = (p instanceof Retard)? "Retard" : ((Incident) p ).getType();
				this.perturbationActuel.setType(type);
				this.retard = p.getDuree();
				postInfoVoyageActuel();
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
