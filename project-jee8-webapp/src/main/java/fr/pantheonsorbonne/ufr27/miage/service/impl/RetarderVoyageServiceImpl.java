package fr.pantheonsorbonne.ufr27.miage.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.jpa.PassageSegment;
import fr.pantheonsorbonne.ufr27.miage.jpa.TrainJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import fr.pantheonsorbonne.ufr27.miage.service.AjoutSuppressionVoyageService;
import fr.pantheonsorbonne.ufr27.miage.service.RetarderVoyageService;
import fr.panthonsorbonne.ufr27.miage.repository.PassageSegmentRepository;
import fr.panthonsorbonne.ufr27.miage.repository.VoyageRepository;

public class RetarderVoyageServiceImpl implements RetarderVoyageService {

	@Inject
	PassageSegmentRepository passageRepo;
	
	@Inject
	VoyageRepository voyageRepo;
	
	@Inject
	AjoutSuppressionVoyageService suppressionService;
	
	@Override
	public void RetarderVoyagesTrain(List<VoyageJPA> voyagesDuTrain, PassageSegment passage,int retard,boolean changeHeureDebut) {
	
		VoyageJPA voyageActuel = voyagesDuTrain.get(0);
		
		List<PassageSegment> passageAChangerUn = new ArrayList<PassageSegment>();
		List<VoyageJPA> voyageAChanger = new ArrayList<VoyageJPA>();
		if(passage != null) {
			int i=0;
		    for(PassageSegment p: voyageActuel.getPassageSegments()) {
		    	if(p.getId() == passage.getId()) {
		    		for(int j = i; j< voyageActuel.getPassageSegments().size(); j++ ){
		    			passageAChangerUn.add(p);
		    		}
		    		break;
		    	}
		    	i++;
		    }
		}
	    
	    passageRepo.changerHeurePassage(passageAChangerUn,retard,changeHeureDebut);
	    
	    voyageAChanger.add(voyageActuel);
	     
	    int heureArriveeModifie = voyageActuel.getHeureArriveeModifie() + retard;
	    
	    // Décider si voyage supprimé ou retardé
	    List<PassageSegment> passageAChangerDeux = new ArrayList<PassageSegment>();
	    
	    for ( int k=1; k< voyagesDuTrain.size(); k++) {
	    	VoyageJPA voyage = voyagesDuTrain.get(k);
	    	// si la différence entre la nouvelle heure d'arrivée du voyage retardé et l'heure de début du voyage suivant est inférieur à 15 , on ne fait que retarder ce dernier, on ne le supprime pas
	    	if(voyage.getStatut() != "supprimé" && ( heureArriveeModifie -voyage.getHeureDepartModifie()) <= 15 ) {
	    		for(int g=k; g< voyagesDuTrain.size(); g++) {
	    			voyageAChanger.add(voyagesDuTrain.get(g));
	    			passageAChangerDeux.addAll(voyagesDuTrain.get(g).getPassageSegments());
	    		}
	    		break;	
	    		
	    	} 
	    	// si la différence entre la nouvelle heure d'arrivée du voyage retardé et l'heure de début du voyage suivant est suppérieur à 15 , on supprime ce dernier et celui d'après car s'il n'y pas d'aller , il n'y pas de retour
	    	else if(voyage.getStatut() != "supprimé" && ( heureArriveeModifie -voyage.getHeureDepartModifie()) > 15 ) {
	    		List<VoyageJPA> voyagesASupprimer = new ArrayList<VoyageJPA>();
	    		voyage.setStatut("supprimé");
	    		voyagesASupprimer.add(voyage);
	    		if((k+1) < voyagesDuTrain.size()) {
	    			voyagesDuTrain.get(k+1).setStatut("supprimé");
	    			voyagesASupprimer.add(voyagesDuTrain.get(k+1));
	    		} 			
	    		suppressionService.deleteVoyages(voyagesASupprimer);
	    		
	    	}
	    }
	    passageRepo.changerHeurePassage(passageAChangerDeux,retard,true);
	 
	    voyageRepo.changerHeureVoyage(voyageAChanger,retard,changeHeureDebut);
	}

	@Override
	public void retarderPassage(List<PassageSegment> listPassageAmodifier) {
		passageRepo.changerHeurePassage(listPassageAmodifier,3,true);
	}

	
}
