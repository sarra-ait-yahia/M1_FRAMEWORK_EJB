package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.VoyageDuJour;

public interface InformationVoyageService {

	public VoyageDuJour getListVoyage(int trainId);

}
