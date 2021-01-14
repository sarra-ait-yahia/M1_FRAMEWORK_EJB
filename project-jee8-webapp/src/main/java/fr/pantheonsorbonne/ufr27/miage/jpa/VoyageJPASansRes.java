package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="VoyageJPASansRes")
public class VoyageJPASansRes extends VoyageJPA {

	public VoyageJPASansRes() {
		super();
	}

	public VoyageJPASansRes(LocalDate dateVoyage, int heureDepart, int heureArrivee, double vitesse, TrajetJPA trajet,
			TrainJPA train, List<PassageSegment> passageSegments, Double distance, List<PerturbationJPA> perturbations,
			boolean isVoyageSupprime) {
		
		super(dateVoyage, heureDepart, heureArrivee, vitesse, trajet, train, passageSegments, distance, perturbations,
				isVoyageSupprime);
	}

}
