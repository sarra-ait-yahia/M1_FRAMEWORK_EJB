package fr.pantheonsorbonne.ufr27.miage.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.exception.NoDebtException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.jms.AccesJMS;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.VoyageDuJour;
import fr.pantheonsorbonne.ufr27.miage.service.GestionPerturbationService;
import fr.pantheonsorbonne.ufr27.miage.service.InformationVoyageService;
import fr.pantheonsorbonne.ufr27.miage.service.NotifyInfoGareService;
import fr.panthonsorbonne.ufr27.miage.repository.VoyageDuJourRepository;

public class InformationVoyageServiceImpl implements InformationVoyageService {

	@Inject
	VoyageDuJourRepository voyageRepo;

 	@Inject
 	AccesJMS jms;
	
	@Override
	public VoyageDuJour getListVoyage(String trainId, int time) {
		
		VoyageDuJour listVoyages = this.voyageRepo.getVoyageDuJour(trainId);
		for(Voyage v : listVoyages.getVoyages()) {
			jms.sendVoyage(v, time);
		}
		
         return listVoyages;
		
	}

	@Override
	public void addPerturbationToBDD() {
		//ajouter dans la BDD
	}

}
