package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="VoyageJPAAvecRes")
public class VoyageJPAAvecRes extends VoyageJPA {

	@OneToMany
	List<Reservation> reservations;

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public VoyageJPAAvecRes() {
		super();
	}

	public VoyageJPAAvecRes(LocalDate dateVoyage, int heureDepart, int heureArrivee, double vitesse, TrajetJPA trajet,
			String direction,TrainJPA train, List<PassageSegment> passageSegments, Double distance, List<PerturbationJPA> perturbations,
			String statut, List<Gare> garesAdesservir, List<Quai> quaiAdesservir,List<Reservation> reservations) {
		super(dateVoyage, heureDepart, heureArrivee, vitesse, trajet,direction, train, passageSegments, distance, perturbations,
				statut, garesAdesservir,quaiAdesservir);
		this.reservations = reservations;
	}

}
