package fr.pantheonsorbonne.ufr27.miage.service.impl;

import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import fr.pantheonsorbonne.ufr27.miage.service.AjoutSuppressionVoyageService;
import fr.panthonsorbonne.ufr27.miage.repository.VoyageRepository;

public class AjoutSuppressionVoyageServiceImpl implements AjoutSuppressionVoyageService {

	@Inject 
	VoyageRepository voyageRepo;
	
	@Override
	public void deleteVoyages(List<VoyageJPA> voyagesASupprimer) {
		voyageRepo.delete(voyagesASupprimer);
		
	}

}
