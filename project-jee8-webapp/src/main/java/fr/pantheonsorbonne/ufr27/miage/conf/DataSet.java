package fr.pantheonsorbonne.ufr27.miage.conf;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.persistence.EntityManager;

import fr.pantheonsorbonne.ufr27.miage.dao.SegmentDAO;
import fr.pantheonsorbonne.ufr27.miage.jpa.Payment;
import fr.pantheonsorbonne.ufr27.miage.jpa.SegmentJPA;

@ApplicationScoped
@ManagedBean
public class DataSet {
	
	@Inject
	SegmentDAO segmentDao;
	
	@Inject
	EntityManager em;
	
	public int setDataProject(String stationDepart, String stationArrivee, double distance) {
		em.getTransaction().begin();
		try {
			SegmentJPA segment = new SegmentJPA();
			segment.setStationArrivee(stationArrivee);
			segment.setStationDepart(stationDepart);
			segment.setDistance(distance);
			em.persist(segment);
			em.getTransaction().commit();
			return segment.getId();

		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("failed to initiate payment", e);
		}	
		
	}
}
