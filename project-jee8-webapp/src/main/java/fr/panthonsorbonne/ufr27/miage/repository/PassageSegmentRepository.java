package fr.panthonsorbonne.ufr27.miage.repository;

import java.util.List;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.dao.PassageSegmentDAO;
import fr.pantheonsorbonne.ufr27.miage.jpa.PassageSegment;

public class PassageSegmentRepository {

	@Inject
	PassageSegmentDAO passageDao;
	
	public void changerHeurePassage(List<PassageSegment> passageAChanger, int retard, boolean changeHeureDebut) {
		passageDao.changerHeurePassage(passageAChanger, retard, changeHeureDebut);
		
	}

}
