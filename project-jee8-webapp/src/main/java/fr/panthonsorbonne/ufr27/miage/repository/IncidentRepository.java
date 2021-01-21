package fr.panthonsorbonne.ufr27.miage.repository;

import javax.inject.Inject;

import fr.pantheonsorbonne.ufr27.miage.dao.IncidentImpactDAO;

public class IncidentRepository {
	
	@Inject
	IncidentImpactDAO incidentImpact;
	
	public String getImpactIncident(String nameIncident) {
		return incidentImpact.getImpactIncident(nameIncident);
	}

}
