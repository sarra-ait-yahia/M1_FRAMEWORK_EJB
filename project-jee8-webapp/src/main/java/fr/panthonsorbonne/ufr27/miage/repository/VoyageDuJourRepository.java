package fr.panthonsorbonne.ufr27.miage.repository;

import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.dao.VoyageDAO;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.jaxb.mapping.JaxbJpaMapper;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.VoyageDuJour;

public class VoyageDuJourRepository {

	@Inject 
	VoyageDAO voyageDao;
	
	@Inject
	JaxbJpaMapper mapper;
	
	public VoyageDuJour getVoyageDuJour(String trainId) {
		List<VoyageJPA> voyagesDujour = this.voyageDao.getVoyagesDuJour(trainId);
		return this.mapper.voyageDuJourFromJpa(voyagesDujour);	
	}

	
}
