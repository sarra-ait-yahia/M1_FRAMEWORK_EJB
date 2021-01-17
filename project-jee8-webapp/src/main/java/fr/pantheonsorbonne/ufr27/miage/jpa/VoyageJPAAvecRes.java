package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="VoyageJPAAvecRes")
public class VoyageJPAAvecRes extends VoyageJPA {

	@OneToMany
	List<Reservation> reservations;
	
	@ManyToMany
	List<VoyageJPASansRes> terLie;

	
	public List<VoyageJPASansRes> getTerLie() {
		return terLie;
	}

	public void setTerLie(List<VoyageJPASansRes> terLie) {
		this.terLie = terLie;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public VoyageJPAAvecRes() {
		super();
	}

	public VoyageJPAAvecRes(LocalDate dateVoyage, int heureDepart,int heureDepartModifie, int heureArrivee,int heureArriveeModifie, double vitesse, TrajetJPA trajet,
			String direction,TrainJPA train, List<PassageSegment> passageSegments, Double distance, List<PerturbationJPA> perturbations,
			String statut, List<Gare> garesAdesservir, List<Quai> quaiAdesservir,List<Reservation> reservations, List<VoyageJPASansRes> terLie) {
		super(dateVoyage, heureDepart,heureDepartModifie, heureArrivee, heureArriveeModifie, vitesse, trajet,direction, train, passageSegments, distance, perturbations,
				statut, garesAdesservir,quaiAdesservir);
		this.reservations = reservations;
		this.terLie = terLie;
	}

}
