package fr.pantheonsorbonne.ufr27.miage.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.exception.NoDebtException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.jms.AccesJMS;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Perturbation;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.VoyageDuJour;
import fr.pantheonsorbonne.ufr27.miage.service.GestionPerturbationService;
import fr.pantheonsorbonne.ufr27.miage.service.InformationVoyageService;
import fr.pantheonsorbonne.ufr27.miage.service.NotifyInfoGareService;
import fr.panthonsorbonne.ufr27.miage.repository.VoyageRepository;
import fr.panthonsorbonne.ufr27.miage.repository.VoyageDuJourRepository;

public class InformationVoyageServiceImpl implements InformationVoyageService {

	@Inject
	VoyageDuJourRepository voyageDuJourRepo;
	
	@Inject 
	VoyageRepository voyageRepo;

	@Inject 
	NotifyInfoGareService notifyIngoGareService;
 	
	
	@Override
	public VoyageDuJour getListVoyage(String trainId, int time) {
		VoyageDuJour listVoyages = voyageDuJourRepo.getVoyageDuJour(trainId,0);
		notifyIngoGareService.sendInfoVoyage(listVoyages.getVoyages(), time,null);
		return listVoyages;	
	}

	@Override
	public void addPerturbationToBDD(Perturbation perturbation, int idVoyage) {
		voyageRepo.ajouterPerturbationJaxb(perturbation, idVoyage);
	}

}
