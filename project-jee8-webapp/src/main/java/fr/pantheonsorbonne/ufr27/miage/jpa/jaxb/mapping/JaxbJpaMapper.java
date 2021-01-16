package fr.pantheonsorbonne.ufr27.miage.jpa.jaxb.mapping;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.ufr27.miage.jpa.PassageSegment;
import fr.pantheonsorbonne.ufr27.miage.jpa.SegmentJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.TrajetJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.VoyageDuJour;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Passage;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Segment;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Train;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Trajet;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;

public class JaxbJpaMapper {
	
	public VoyageDuJour voyageDuJourFromJpa(List<VoyageJPA> voyages) {
		
		ObjectFactory factory = new ObjectFactory();
		VoyageDuJour voyageDuJour = factory.createVoyageDuJour();
		List<Voyage> voyagesJaxb = new ArrayList<Voyage>();
		Train t = factory.createTrain();
		if(voyages.size() != 0) {
			t.setIdTrain(voyages.get(0).getTrain().getId());
			t.setType(voyages.get(0).getTrain().getType());
			
			for(VoyageJPA v : voyages) {
				Voyage vJaxb = factory.createVoyage();
				vJaxb.setIdVoyage(v.getId());
				vJaxb.setDistance(v.getDistance());
				vJaxb.setHeureArrivee(v.getHeureArrivee());
				vJaxb.setHeureDepart(v.getHeureDepart());
				vJaxb.setStatut(v.getStatut());
				vJaxb.setTrain(t);
				vJaxb.setVitesse(v.getVitesse());
				Trajet trajet = factory.createTrajet();
				trajet.setIdTrajet(v.getTrajet().getId());
				List<Segment> segmentsTrajet = new ArrayList<Segment>();
				for(SegmentJPA se: v.getTrajet().getSegment()) {
					Segment seg = factory.createSegment();
					seg.setDistance(se.getDistance());
					seg.setStationA(se.getStationDepart());
					seg.setStationB(se.getStationArrivee());
					segmentsTrajet.add(seg);
				}
				trajet.setSegments(segmentsTrajet);
				vJaxb.setTrajet(trajet);
				List<Passage> passages = new ArrayList<Passage>();
				for(PassageSegment ps : v.getPassageSegments()) {
					Passage pa = factory.createPassage();
					pa.setHeureDepartModifie(ps.getHeuredepartModifie());
					pa.setHeureArriveeModifie(ps.getHeureArriveeModifie());
					pa.setHeureDepart(ps.getHeureDepart());
					pa.setHeureArrivee(ps.getHeureArrivee());
					pa.setIsPassageAjoutee(ps.isPassageAjoute());
					List<Segment> segmentsPassage = new ArrayList<Segment>();
					for(SegmentJPA se : ps.getSegments()) {
						Segment seg = factory.createSegment();
						seg.setDistance(se.getDistance());
						seg.setStationA(se.getStationDepart());
						seg.setStationB(se.getStationArrivee());
						segmentsPassage.add(seg);
					}
					pa.setSegments(segmentsPassage);
					passages.add(pa);
				}
				vJaxb.setPassages(passages);
				voyagesJaxb.add(vJaxb);
			}
		}
		voyageDuJour.setVoyages(voyagesJaxb);
		return voyageDuJour;
		
	}
	

}
