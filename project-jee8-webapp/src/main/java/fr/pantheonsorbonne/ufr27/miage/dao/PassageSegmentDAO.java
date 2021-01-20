package fr.pantheonsorbonne.ufr27.miage.dao;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.pantheonsorbonne.ufr27.miage.jpa.PassageSegment;
import fr.pantheonsorbonne.ufr27.miage.jpa.SegmentJPA;

@ManagedBean
public class PassageSegmentDAO {
	@Inject
	EntityManager em;
	
	public void changerHeurePassage(List<PassageSegment> passages, int retard, boolean changeHeureDebut){
		em.getTransaction().begin();
		int i=0;
		for(PassageSegment passage: passages) {
			PassageSegment p = em.find(PassageSegment.class, passage.getId());
			if(!changeHeureDebut && i == 0) {
				p.setHeureArriveeModifie(passage.getHeureArriveeModifie()+retard);
			}
			else {
				p.setHeureArriveeModifie(passage.getHeureArriveeModifie()+retard);
				p.setHeuredepartModifie(passage.getHeuredepartModifie()+retard);
			}
			i++;
		}
		em.getTransaction().commit();
		
	}

	public void modifyPassage(PassageSegment p, List<SegmentJPA> listSegment) {
		em.getTransaction().begin();
		PassageSegment passage = em.find(PassageSegment.class, p.getId());
		passage.setSegments(listSegment);
		passage.setDistanceParcourue(//passagePrécédent);
		passage.setHeureArrivee(//calculheureArrivee);
		em.getTransaction().commit();
		
	}

}
