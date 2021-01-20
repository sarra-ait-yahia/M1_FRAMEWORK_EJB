package fr.pantheonsorbonne.ufr27.miage.dao;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.ManagedBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.jpa.SegmentJPA;

@ManagedBean
public class SegmentDAO {
	@Inject
	EntityManager em;
	

}
