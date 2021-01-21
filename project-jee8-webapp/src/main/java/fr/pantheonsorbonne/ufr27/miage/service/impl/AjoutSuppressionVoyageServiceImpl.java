package fr.pantheonsorbonne.ufr27.miage.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.jpa.Gare;
import fr.pantheonsorbonne.ufr27.miage.jpa.PassageSegment;
import fr.pantheonsorbonne.ufr27.miage.jpa.SegmentJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import fr.pantheonsorbonne.ufr27.miage.service.AjoutSuppressionVoyageService;
import fr.pantheonsorbonne.ufr27.miage.service.RetarderVoyageService;
import fr.panthonsorbonne.ufr27.miage.repository.PassageSegmentRepository;
import fr.panthonsorbonne.ufr27.miage.repository.VoyageRepository;

public class AjoutSuppressionVoyageServiceImpl implements AjoutSuppressionVoyageService {

	@Inject
	VoyageRepository voyageRepo;

	@Inject
	PassageSegmentRepository passageRepo;
	
	@Inject
	RetarderVoyageService retarderService;

	@Override
	public void deleteVoyages(List<VoyageJPA> voyagesASupprimer) {
		voyageRepo.delete(voyagesASupprimer);
	}


	@Override
	public void ajouterPassagePourVoyage(VoyageJPA voyageJpa, Gare gare) {
		// chercher le passage qui passe par la gare
	    SegmentJPA segmentGare = new SegmentJPA();
		PassageSegment passageAmodifie = new PassageSegment();
		String direction = voyageJpa.getDirection();
		for (SegmentJPA seg : voyageJpa.getTrajet().getSegment()) {
			if (seg.getStationArrivee() == gare.getNom() && direction == "aller") {
				segmentGare = seg;
				break;
			}
			if (seg.getStationDepart() == gare.getNom() && direction != "aller") {
				segmentGare = seg;
				break;
			}
		}
		double distanceModifie = 0;
		for(PassageSegment pa:voyageJpa.getPassageSegments()) {
			if(pa.getSegments().contains(segmentGare)) {
				passageAmodifie = pa;
				break;
			}
			distanceModifie = pa.getDistanceParcourue();
		}
		
		List<SegmentJPA> listSegment = new ArrayList<SegmentJPA>();
		List<SegmentJPA> listSegmentNewPassge = new ArrayList<SegmentJPA>();
		
		int index = direction == "aller" ? 0 : passageAmodifie.getSegments().size();
		int limit = direction == "aller" ? passageAmodifie.getSegments().size() : 0;
		int indexSegment = index;
		double distancePassage=0;
		double distanceNouveauPassage=0;
		int heureArriveeModifie = 0;
		if (direction == "aller") {
			while (index < limit) {
				SegmentJPA seg = passageAmodifie.getSegments().get(index);
				if (indexSegment > index) {
					listSegmentNewPassge.add(seg);
					distanceNouveauPassage+=seg.getDistance();
				}
				if (indexSegment <= index) {
					listSegment.add(seg);
					distancePassage+=seg.getDistance();
				}
				if (seg.getStationArrivee() == gare.getNom()) {
					indexSegment = limit;
					heureArriveeModifie = passageAmodifie.getHeuredepartModifie() + (int) Math. round(distancePassage/voyageJpa.getVitesse());
					passageRepo.modifyPassage(passageAmodifie, listSegment,distanceModifie+distancePassage,heureArriveeModifie);
				}
				index++;
			}
			int heureArriveeNewPassage = heureArriveeModifie + 3 + (int) Math. round(distanceNouveauPassage/voyageJpa.getVitesse());
            double distanceTotalNewPassage = distanceNouveauPassage + distanceModifie + distancePassage; 
            passageRepo.createPassage(listSegmentNewPassge, voyageJpa,distanceTotalNewPassage, heureArriveeModifie,heureArriveeNewPassage);

		} else {
			while (index > limit) {
				SegmentJPA seg = passageAmodifie.getSegments().get(index);
				if (indexSegment < index) {
					listSegmentNewPassge.add(seg);
					distanceNouveauPassage+=seg.getDistance();
				}
				if (indexSegment >= index) {
					listSegment.add(seg);
					distancePassage+=seg.getDistance();
				}
				if (seg.getStationDepart() == gare.getNom()) {
					indexSegment = limit;
					heureArriveeModifie = passageAmodifie.getHeuredepartModifie() + (int) Math. round(distancePassage/voyageJpa.getVitesse());
					passageRepo.modifyPassage(passageAmodifie, listSegment,distanceModifie+distancePassage,heureArriveeModifie);
				}
				index--;
			}
			int heureArriveeNewPassage = heureArriveeModifie + 3 + (int) Math. round(distanceNouveauPassage/voyageJpa.getVitesse());
            double distanceTotalNewPassage = distanceNouveauPassage + distanceModifie + distancePassage; 
			passageRepo.createPassage(listSegmentNewPassge, voyageJpa,distanceTotalNewPassage, heureArriveeModifie,heureArriveeNewPassage);

		}
		// retarder les passages suivant
		final PassageSegment passage = passageAmodifie;
		List<PassageSegment> listPassageAmodifier = voyageJpa.getPassageSegments().stream()
				  .filter(c -> c.getHeuredepartModifie() > passage.getHeureArrivee())
				  .collect(Collectors.toList());
		voyageRepo.addGareToVoyage(voyageJpa,gare);
		retarderService.retarderPassage(listPassageAmodifier);
	}

}
