package fr.panthonsorbonne.ufr27.miage.repository;

import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.dao.VoyageDAO;
import fr.pantheonsorbonne.ufr27.miage.jpa.Gare;
import fr.pantheonsorbonne.ufr27.miage.jpa.PerturbationJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.TrajetJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.jaxb.mapping.JaxbJpaMapper;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Perturbation;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Train;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Trajet;
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
	
	public List<VoyageJPA> getVoyages(String trainId, int time) {
		List<VoyageJPA> voyagesDujour = this.voyageDao.getVoyagesDuJour(trainId,time);
		return voyagesDujour;	
	}

	public void changerHeureVoyage(List<VoyageJPA> voyageAChanger, int retard, boolean changeHeureDebut) {
		voyageDao.changerHeureVoyage(voyageAChanger, retard, changeHeureDebut);
		
	}

	public void delete(List<VoyageJPA> voyagesASupprimer) {
		voyageDao.delete(voyagesASupprimer);
		
	}

	public List<VoyageJPA> getVoyagesdumemeTrajetEtDirection(Trajet trajet, String direction, int time, int idVoyage) {
		return voyageDao.getVoyageDumemeTrajetEtDirection(trajet.getIdTrajet(), direction, time,idVoyage);
		
	}
	
	public List<VoyageJPA> getVoyagesSuivants(VoyageJPA voyage){
		voyageDao.getVoyagesSuivants(voyage.getHeureDepartModifie(),voyage.getTrain().getId());
		return null;
		
	}

	public void addGareToVoyage(VoyageJPA voyageJpa, Gare gare) {
		voyageDao.addGareToVoyage(voyageJpa, gare);
		
	}
	
}

