package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

@Entity
public class VoyageJPA {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	LocalDate dateVoyage;
	
	int heureDepart;
	
	int heureDepartModifie;
	
	int heureArrivee;
	
	int heureArriveeModifie;
	
	double vitesse;
	
	String direction;

	@ManyToOne
	TrajetJPA trajet;
	

	@ManyToOne
	TrainJPA train;
	
	@OneToMany
	@OrderBy(value="heuredepartModifie")
	List<PassageSegment> passageSegments;
	
	
	
	Double distance;
	
	@OneToMany
	List<PerturbationJPA> perturbations;
	

	String statut;
	
	@ManyToMany
	List<Gare> garesAdesservir; 

	@ManyToMany
	List<Quai> quaiAdesservir; 
	
	public List<Quai> getQuaiAdesservir() {
		return quaiAdesservir;
	}

	public void setQuaiAdesservir(List<Quai> quaiAdesservir) {
		this.quaiAdesservir = quaiAdesservir;
	}

	
	public List<Gare> getGaresAdesservir() {
		return garesAdesservir;
	}

	public void setGaresAdesservir(List<Gare> garesAdesservir) {
		this.garesAdesservir = garesAdesservir;
	}

	public VoyageJPA() {
	}

	
	public VoyageJPA(LocalDate dateVoyage, int heureDepart,int heureDepartModifie, int heureArrivee,int heureArriveeModifie, double vitesse, TrajetJPA trajet,String direction,
			TrainJPA train, List<PassageSegment> passageSegments, Double distance, List<PerturbationJPA> perturbations, String statut, List<Gare> garesAdesservir, List<Quai> quaiAdesservir) {
		super();
		this.dateVoyage = dateVoyage;
		this.heureDepart = heureDepart;
		this.heureArrivee = heureArrivee;
		this.vitesse = vitesse;
		this.trajet = trajet;
		this.direction = direction;
		this.train = train;
		this.passageSegments = passageSegments;
		this.distance = distance;
		this.perturbations = perturbations;
		this.statut = statut;
		this.garesAdesservir = garesAdesservir; 
        this.quaiAdesservir = quaiAdesservir; 
        this.heureDepartModifie = heureDepartModifie;
        this.heureArriveeModifie = heureArriveeModifie; 
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDateVoyage() {
		return dateVoyage;
	}

	public void setDateVoyage(LocalDate dateVoyage) {
		this.dateVoyage = dateVoyage;
	}

	public int getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(int heureDepart) {
		this.heureDepart = heureDepart;
	}

	public int getHeureArrivee() {
		return heureArrivee;
	}

	public void setHeureArrivee(int heureArrivee) {
		this.heureArrivee = heureArrivee;
	}
	
	

	public int getHeureDepartModifie() {
		return heureDepartModifie;
	}

	public void setHeureDepartModifie(int heureDepartModifie) {
		this.heureDepartModifie = heureDepartModifie;
	}

	public int getHeureArriveeModifie() {
		return heureArriveeModifie;
	}

	public void setHeureArriveeModifie(int heureArriveeModifie) {
		this.heureArriveeModifie = heureArriveeModifie;
	}

	public double getVitesse() {
		return vitesse;
	}

	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public TrajetJPA getTrajet() {
		return trajet;
	}

	public void setTrajet(TrajetJPA trajet) {
		this.trajet = trajet;
	}

	public TrainJPA getTrain() {
		return train;
	}

	public void setTrain(TrainJPA train) {
		this.train = train;
	}

	public List<PassageSegment> getPassageSegments() {
		return passageSegments;
	}

	public void setPassageSegments(List<PassageSegment> passageSegments) {
		this.passageSegments = passageSegments;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public List<PerturbationJPA> getPerturbations() {
		return perturbations;
	}

	public void setPerturbations(List<PerturbationJPA> perturbations) {
		this.perturbations = perturbations;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}
	
	
	
}
