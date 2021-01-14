package fr.pantheonsorbonne.ufr27.miage.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@ManagedBean
public class VoyageDAO {
	@Inject
	EntityManager em;
	
	
	public List<VoyageJPA> getVoyagesDuJour(String idTrain){
		Query query = em.createQuery("SELECT v FROM VoyageJPA v WHERE v.train.id =:idTrain AND v.dateVoyage =:dateToday", VoyageJPA.class);
		query.setParameter("idTrain", idTrain);
		query.setParameter("dateToday", LocalDate.of(2021, 01, 23));
		return query.getResultList();
	}

}
