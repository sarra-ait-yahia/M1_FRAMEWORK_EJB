package fr.pantheonsorbonne.ufr27.miage.dao;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ManagedBean
public class ReservationDAO {
	@Inject
	EntityManager em;

}
