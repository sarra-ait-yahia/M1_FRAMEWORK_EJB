package fr.pantheonsorbonne.ufr27.miage.dao;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.pantheonsorbonne.ufr27.miage.jpa.PassageSegment;

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

}
