package fr.pantheonsorbonne.ufr27.miage.service;

import java.util.List;

import fr.pantheonsorbonne.ufr27.miage.jpa.PassageSegment;
import fr.pantheonsorbonne.ufr27.miage.jpa.TrainJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPASansRes;

public interface RetarderVoyageService {

	void RetarderVoyagesTrain(List<VoyageJPA> voyagesDuTrain, PassageSegment passage, int retard,boolean changeHeureDebut);

	

}
