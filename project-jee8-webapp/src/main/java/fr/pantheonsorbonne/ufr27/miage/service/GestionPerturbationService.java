package fr.pantheonsorbonne.ufr27.miage.service;


import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;

public interface GestionPerturbationService {

	public void gererPerturbation(Voyage voyage, int idVoyage, int time);
	

}
