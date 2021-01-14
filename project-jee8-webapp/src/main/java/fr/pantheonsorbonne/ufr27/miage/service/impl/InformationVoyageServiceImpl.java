package fr.pantheonsorbonne.ufr27.miage.service.impl;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.VoyageDuJour;
import fr.pantheonsorbonne.ufr27.miage.service.InformationVoyageService;
import fr.panthonsorbonne.ufr27.miage.repository.VoyageDuJourRepository;

public class InformationVoyageServiceImpl implements InformationVoyageService {

	@Inject
	VoyageDuJourRepository voyageRepo;
	
	@Override
	public VoyageDuJour getListVoyage(String trainId) {
		
		return this.voyageRepo.getVoyageDuJour(trainId);
		
	}

}
