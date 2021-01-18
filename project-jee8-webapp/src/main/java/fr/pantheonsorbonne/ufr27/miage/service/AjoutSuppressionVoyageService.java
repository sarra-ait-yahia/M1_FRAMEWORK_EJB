package fr.pantheonsorbonne.ufr27.miage.service;

import java.util.List;

import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;

public interface AjoutSuppressionVoyageService {

	void deleteVoyages(List<VoyageJPA> voyagesASupprimer);

}
