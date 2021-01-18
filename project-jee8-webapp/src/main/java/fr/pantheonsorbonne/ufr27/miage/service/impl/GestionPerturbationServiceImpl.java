package fr.pantheonsorbonne.ufr27.miage.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.jpa.Gare;
import fr.pantheonsorbonne.ufr27.miage.jpa.PassageSegment;
import fr.pantheonsorbonne.ufr27.miage.jpa.SegmentJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPAAvecRes;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPASansRes;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Passage;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Perturbation;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;
import fr.pantheonsorbonne.ufr27.miage.service.GestionPerturbationService;
import fr.pantheonsorbonne.ufr27.miage.service.RetarderVoyageService;
import fr.panthonsorbonne.ufr27.miage.repository.VoyageDuJourRepository;
import fr.panthonsorbonne.ufr27.miage.repository.VoyageRepository;

public class GestionPerturbationServiceImpl implements GestionPerturbationService{

	@Inject
	VoyageRepository voyageRepo;
	
	@Inject
	RetarderVoyageService retarderVoyageService;
	
	@Override
	public void gererPerturbation(Voyage voyage, int idVoyage, int time) {
		List<VoyageJPA> voyagesDuTrain = voyageRepo.getVoyages(voyage.getTrain().getIdTrain(), time);
		Perturbation perturbation = voyage.getTrain().getPerturbation();
		int retard = perturbation.getDuree();
		
		//Gerer le cas ou le train en retrad est un TGV => on retarde le voyage du ter lie à au voyage du TGV
		if(voyage.getTrain().getType() == "TGV" ){
			VoyageJPAAvecRes voyageAvecRes = (VoyageJPAAvecRes) voyagesDuTrain.get(0);
			if(voyageAvecRes.getReservations().size() > 50) {
				List<VoyageJPASansRes> voyagesAssociee = voyageAvecRes.getTerLie();
				for (VoyageJPASansRes vo :voyagesAssociee ) {
					List<Gare> gareList = new ArrayList<Gare>();
					gareList.addAll(vo.getGaresAdesservir());
					gareList.retainAll(voyageAvecRes.getGaresAdesservir());
					PassageSegment passageTer = getPassageOfGare(gareList.get(0),vo.getPassageSegments(),vo.getDirection());
					Passage passageTGV = voyage.getPassages().get(0);
					int heureEstimeDemarrageTer = passageTGV.getHeureArriveeModifie()+retard+10;
					if(passageTer.getHeuredepartModifie()< heureEstimeDemarrageTer){
						List<VoyageJPA> voyagesDuTrainTER = voyageRepo.getVoyages(vo.getTrain().getId(), passageTer.getHeuredepartModifie());
						retarderVoyageService.RetarderVoyagesTrain(voyagesDuTrainTER,passageTer,retard,true);
					}
				}
			}
		} 
		
		//Gerer le retard du train ter ou tgv
		PassageSegment passageActuel = null;
		for(PassageSegment p :voyagesDuTrain.get(0).getPassageSegments()) {
			 if(p.getId() == voyage.getPassages().get(0).getIdPassage())
				 passageActuel = p;
		}
		retarderVoyageService.RetarderVoyagesTrain(voyagesDuTrain,passageActuel,retard, false);
	   
	
		// si retard >= 120 
		//cherche le passage actuel 
		//liste des gares prochain à desservir 
		 
		//je ramène voyage de meme trajet et meme direction et dont heureArrivé < time 
		// je les organise selon le temps 
		// pour chaque train , je regarde sil dessert les gares, 
		 // pour la gare qu'il dessert , je regarde si s'il est déja passé
		// sil n'est pas encore passé gareOK
		// sil l'est déja passé gareKO
		//Pour les gare qu'il dessert pas 
		// je regarde sa position actuel par rapport à la gare qu'il dessert pas 
		//sil est après gareKo
		//S'il est avant, je crée un nouveau passage 
		// et je met à jour tout les voyages de ce train 
		
		
		
	}
		
		private PassageSegment getPassageOfGare(Gare gare, List<PassageSegment> passages,String direction) {
			for (PassageSegment passage:passages ) {
				int numSegments = passage.getSegments().size();
				if((direction == "retour" && passage.getSegments().get(numSegments-1).getStationArrivee() == gare.getNom())||
						direction == "aller" && passage.getSegments().get(0).getStationDepart() == gare.getNom()) {
						return passage;
					}
				}
			return null;
		}
		

}
