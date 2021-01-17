package fr.pantheonsorbonne.ufr27.miage.dao;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import fr.pantheonsorbonne.ufr27.miage.jpa.PerturbationJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;

@ManagedBean
public class PerturbationDAO {
	@Inject
	EntityManager em;

	
}
