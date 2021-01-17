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

	public VoyageJPASansRes(LocalDate dateVoyage, int heureDepart,int heureDepartModifie, int heureArrivee, int heureArriveeModifie, double vitesse, TrajetJPA trajet,
			String direction,TrainJPA train, List<PassageSegment> passageSegments, Double distance, List<PerturbationJPA> perturbations,
			String statut, List<Gare> garesAdesservir, List<Quai> quaiAdesservir) {
		
		super(dateVoyage, heureDepart,heureDepartModifie, heureArrivee, heureArriveeModifie,vitesse, trajet,direction, train, passageSegments, distance, perturbations,
				statut,garesAdesservir,quaiAdesservir);
	}

}
