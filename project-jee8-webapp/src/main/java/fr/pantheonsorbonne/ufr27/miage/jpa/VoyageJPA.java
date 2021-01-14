package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Id;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class VoyageJPA {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	LocalDate dateVoyage;
	
	int heureDepart;
	
	int heureArrivee;
	
	double vitesse;
	
	@ManyToOne
	TrajetJPA trajet;
	
	public TrajetJPA getTrajet() {
		return trajet;
	}

	public void setTrajet(TrajetJPA trajet) {
		this.trajet = trajet;
	}

	@ManyToOne
	TrainJPA train;
	
	@OneToMany
	List<PassageSegment> passageSegments;
	
	
	@Transient
	Double distance;
	
	@OneToMany
	List<PerturbationJPA> perturbations;
	
	
	
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

	public double getVitesse() {
		return vitesse;
	}

	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	boolean isVoyageSupprime;
	
	public boolean isVoyageSupprime() {
		return isVoyageSupprime;
	}

	public void setIsVoyageSupprime(boolean isVoyageSupprime) {
		this.isVoyageSupprime = isVoyageSupprime;
	}

	
	public VoyageJPA() {
	}

	public VoyageJPA(LocalDate dateVoyage, int heureDepart, int heureArrivee, double vitesse, TrajetJPA trajet,
			TrainJPA train, List<PassageSegment> passageSegments, Double distance, List<PerturbationJPA> perturbations, boolean isVoyageSupprime) {
		super();
		this.dateVoyage = dateVoyage;
		this.heureDepart = heureDepart;
		this.heureArrivee = heureArrivee;
		this.vitesse = vitesse;
		this.trajet = trajet;
		this.train = train;
		this.passageSegments = passageSegments;
		this.distance = distance;
		this.perturbations = perturbations;
		this.isVoyageSupprime = isVoyageSupprime;
	}
	
	
}
