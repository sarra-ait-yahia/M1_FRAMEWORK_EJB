package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Perturbation;

public interface GestionPerturbationService {

	public void gererPerturbation(Perturbation perturbation, int idVoyage, int time);
	

}
