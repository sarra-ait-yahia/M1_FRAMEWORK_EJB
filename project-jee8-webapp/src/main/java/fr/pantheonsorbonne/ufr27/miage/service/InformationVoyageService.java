package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.VoyageDuJour;

//Service qui permet d'envoyer et récuperer des informations sur des voyages 

public interface InformationVoyageService {

	public VoyageDuJour getListVoyage(String trainId);

}
