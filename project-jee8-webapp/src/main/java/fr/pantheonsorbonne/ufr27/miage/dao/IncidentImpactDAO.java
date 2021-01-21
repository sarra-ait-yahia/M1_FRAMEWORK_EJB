package fr.pantheonsorbonne.ufr27.miage.dao;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import fr.pantheonsorbonne.ufr27.miage.jpa.IncidentImpact;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;

@ManagedBean
public class IncidentImpactDAO {
	@Inject
	EntityManager em;

	public String getImpactIncident(String nameIncident) {
		em.getTransaction().begin();
		Query query = em.createQuery("SELECT i FROM IncidentImpact i WHERE i.type =:type", IncidentImpact.class);
		query.setParameter("type", nameIncident);
		IncidentImpact impact = (IncidentImpact) query.getResultList().get(0);
		em.getTransaction().commit();
		return impact.getImpact();
	}
}
