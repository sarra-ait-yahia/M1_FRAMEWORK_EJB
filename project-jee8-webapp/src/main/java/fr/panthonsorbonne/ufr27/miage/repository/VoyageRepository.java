package fr.panthonsorbonne.ufr27.miage.repository;

import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.dao.PerturbationDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.VoyageDAO;
import fr.pantheonsorbonne.ufr27.miage.jpa.PerturbationJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.jaxb.mapping.JaxbJpaMapper;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Perturbation;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.VoyageDuJour;

public class VoyageRepository {

	@Inject 
	VoyageDAO voyageDao;
	
	@Inject
	JaxbJpaMapper mapper;
	
	public void ajouterPerturbationJaxb(Perturbation perturbation,int idVoyage) {
		PerturbationJPA perturbationjpa = this.mapper.perturbationJaxbToJPA(perturbation);
		voyageDao.ajouterPerturbation(perturbationjpa,idVoyage);
	}
}
