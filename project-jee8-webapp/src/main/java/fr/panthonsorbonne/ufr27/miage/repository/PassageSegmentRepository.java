package fr.panthonsorbonne.ufr27.miage.repository;

import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.dao.PassageSegmentDAO;
import fr.pantheonsorbonne.ufr27.miage.jpa.PassageSegment;
import fr.pantheonsorbonne.ufr27.miage.jpa.SegmentJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;

public class PassageSegmentRepository {

	@Inject
	PassageSegmentDAO passageDao;
	
	public void changerHeurePassage(List<PassageSegment> passageAChanger, int retard, boolean changeHeureDebut) {
		passageDao.changerHeurePassage(passageAChanger, retard, changeHeureDebut);
		
	}

	public void modifyPassage(PassageSegment p, List<SegmentJPA> listSegment) {
		passageDao.modifyPassage(PassageSegment p, List<SegmentJPA> listSegment);
		
	}

	public void createPassage(List<SegmentJPA> listSegmentNewPassge, VoyageJPA voyageJpa, PassageSegment p) {
		// TODO Auto-generated method stub
		
	}

}
