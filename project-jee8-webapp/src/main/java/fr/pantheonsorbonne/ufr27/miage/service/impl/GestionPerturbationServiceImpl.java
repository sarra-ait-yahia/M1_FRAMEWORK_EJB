package fr.pantheonsorbonne.ufr27.miage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import fr.pantheonsorbonne.ufr27.miage.service.AjoutSuppressionVoyageService;
import fr.pantheonsorbonne.ufr27.miage.service.GestionPerturbationService;
import fr.pantheonsorbonne.ufr27.miage.service.RetarderVoyageService;
import fr.panthonsorbonne.ufr27.miage.repository.VoyageDuJourRepository;
import fr.panthonsorbonne.ufr27.miage.repository.VoyageRepository;

public class GestionPerturbationServiceImpl implements GestionPerturbationService{

	@Inject
	VoyageRepository voyageRepo;
	
	@Inject
	RetarderVoyageService retarderVoyageService;
	
	@Inject
	AjoutSuppressionVoyageService ajouterPassage;
	
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
					PassageSegment passageTer = getPassageOfGare(gareList.get(0),vo.getPassageSegments(),vo.getDirection(),true);
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
		if(retard>120) {
			//cherche le passage actuel 
			Passage passageActuelJaxb = voyage.getPassages().get(0);
			//liste des gares prochain à desservir 
			 Map<Gare,String> garesProchaines= getGaresProchain(passageActuelJaxb,voyagesDuTrain.get(0),voyagesDuTrain.get(0).getDirection()); 
			//je ramène voyage de meme trajet et meme direction et dont heureArrivé < time 
			List<VoyageJPA> listVoyagesTrajetDirection= voyageRepo.getVoyagesdumemeTrajetEtDirection(voyage.getTrajet(), voyage.getDirection(), time,idVoyage);
			// pour chaque train , je regarde sil dessert les gares
			for(VoyageJPA voyageJpa: listVoyagesTrajetDirection) {
				List<Gare> garesAGerer = new ArrayList<Gare>();
				for (Map.Entry<Gare,String> gareProchaine : garesProchaines.entrySet()) {
					if(gareProchaine.getValue() == "KO") {
						garesAGerer.add( gareProchaine.getKey());
					}
		        }
				
				List<Gare> garesADesservir = new ArrayList<Gare>();
				garesADesservir.addAll(garesAGerer);
				garesADesservir.retainAll(voyageJpa.getGaresAdesservir());
				// pour la gare qu'il dessert , je regarde si s'il est déja passé
				for (Gare gareAdesservir : garesADesservir) {
					PassageSegment passageGare = getPassageOfGare(gareAdesservir, voyageJpa.getPassageSegments(), voyageJpa.getDirection(), false); 
				    int diff = passageGare.getHeureArriveeModifie()  - passageActuelJaxb.getHeureArriveeModifie();
					if(passageGare.getHeureArriveeModifie() < time && diff < 60 ) {
						garesProchaines.put(gareAdesservir, "OK");
				    }
				}
				
				List<Gare> garesNonDesservir = new ArrayList<Gare>();
				for(Gare gare: garesAGerer) {
					if(!voyageJpa.getGaresAdesservir().contains(gare)) {
						garesNonDesservir.add(gare);
					}
				}
				
				//Pour les gares qui ne sont pas desservi par ce voyage 
				//Si voyage en cours
				PassageSegment passageActuelJPA = null;
				for(Gare gareNondesservi: garesNonDesservir) {
					if(voyageJpa.getHeureDepartModifie()<= time) {
						int distanceParcourue = 0;
						for(PassageSegment pa:voyageJpa.getPassageSegments()) {
							if((pa.getHeuredepartModifie()<=time && pa.getHeureArriveeModifie()>time) || (pa.getHeuredepartModifie()<time && pa.getHeureArriveeModifie()>=time)) {
								passageActuelJPA = pa;
								break;
							}
		                    distanceParcourue+= pa.getDistanceParcourue();
						}
					
						distanceParcourue += voyageJpa.getVitesse()* (time-passageActuelJPA.getHeuredepartModifie())/60;
						if(isTrainBeforeGare(voyageJpa,gareNondesservi,distanceParcourue)) {
							ajouterPassage.ajouterPassagePourVoyage(voyageJpa,gareNondesservi);
							List<VoyageJPA> listVoyageARetarder = voyageRepo.getVoyagesSuivants(voyageJpa);
							retarderVoyageService.RetarderVoyagesTrain(listVoyageARetarder,null,3, false);
							garesProchaines.put(gareNondesservi, "OK");
						}
						
					}else {//Si voyage pas encore entamé
						ajouterPassage.ajouterPassagePourVoyage(voyageJpa,gareNondesservi);
						List<VoyageJPA> listVoyageARetarder = voyageRepo.getVoyagesSuivants(voyageJpa);
						retarderVoyageService.RetarderVoyagesTrain(listVoyageARetarder,null,3, false);
						garesProchaines.put(gareNondesservi, "OK");
					}
				}
				
			}
		}
		
	}
		
		private PassageSegment getPassageOfGare(Gare gare, List<PassageSegment> passages,String direction, boolean depart) {
			for (PassageSegment passage:passages ) {
				int numSegments = passage.getSegments().size();
				if(depart && (direction == "retour" && passage.getSegments().get(numSegments-1).getStationArrivee() == gare.getNom())||
						direction == "aller" && passage.getSegments().get(0).getStationDepart() == gare.getNom()) {
						return passage;
					}
				else if(!depart && (direction == "aller" && passage.getSegments().get(numSegments-1).getStationArrivee() == gare.getNom())||
						direction == "retour" && passage.getSegments().get(0).getStationDepart() == gare.getNom()) {
						return passage;
					}
				}
			return null;
		}
		
        //méthode qui permet de ramener une map contenant les gares qui vont devraient etre desservi par le train après la perturbation
		private Map<Gare,String> getGaresProchain(Passage passageJaxb,VoyageJPA voyageJpa, String direction) {
			int idPassage = passageJaxb.getIdPassage();
			PassageSegment passage = voyageJpa.getPassageSegments().stream()
					  .filter(passageSegment -> idPassage == passageSegment.getId())
					  .findAny()
					  .orElse(null);
			int index = voyageJpa.getPassageSegments().indexOf(passage);
			Map<Gare,String> mapGare = new HashMap<Gare,String>();
			for(int j=index; j < voyageJpa.getPassageSegments().size();j++) {
				PassageSegment p = voyageJpa.getPassageSegments().get(j);
				int numSegments = p.getSegments().size();	
				String gareName = direction == "aller"? p.getSegments().get(numSegments-1).getStationArrivee() : p.getSegments().get(0).getStationDepart();
				mapGare.put(new Gare(gareName),"KO");
				}
		    return mapGare;
			
		}
		
		//Méthode qui permet de vérifier si le train est déjà passé par la gare ou pas encore
		private boolean isTrainBeforeGare(VoyageJPA voyageJpa, Gare gareNondesservi, double distanceParcourue) {
			double distanceToGare = 0;
			if(voyageJpa.getDirection() == "aller") {
				
				int i=0;
				SegmentJPA seg =  voyageJpa.getTrajet().getSegment().get(0);
				while(i < voyageJpa.getTrajet().getSegment().size() && seg.getStationDepart() != gareNondesservi.getNom()) {
					distanceToGare+= voyageJpa.getTrajet().getSegment().get(i).getDistance();
				    i++;
				}
				
			}else {
				
				int i=0;
				int numSegments =  voyageJpa.getTrajet().getSegment().size();
				SegmentJPA seg =  voyageJpa.getTrajet().getSegment().get(numSegments-1);
				while(i >= 0 && seg.getStationArrivee() != gareNondesservi.getNom()) {
					distanceToGare+= voyageJpa.getTrajet().getSegment().get(i).getDistance();
				    i--;
				}
				
				
			}
			return  distanceToGare > distanceParcourue;
			
		}
}
