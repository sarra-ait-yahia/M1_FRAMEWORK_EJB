package fr.pantheonsorbonne.ufr27.miage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.persistence.EntityManager;
import fr.pantheonsorbonne.ufr27.miage.jms.AccesJMS;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.VoyageDuJour;
import fr.pantheonsorbonne.ufr27.miage.service.GestionPerturbationService;
import fr.pantheonsorbonne.ufr27.miage.service.InformationVoyageService;
import fr.pantheonsorbonne.ufr27.miage.service.NotifyInfoGareService;
import fr.panthonsorbonne.ufr27.miage.repository.IncidentRepository;

@ApplicationScoped
@ManagedBean
public class NotifyInfoGareServiceImpl implements NotifyInfoGareService {


	@Inject
 	AccesJMS jms;
	
	@Inject
	InformationVoyageService serviceInfoVoyage;
	
	@Inject
	IncidentRepository incidentRepo;
	
	@Override
	public void sendInfoVoyage(List<Voyage> voyagesJaxb, int time,Map<String, String> infoPerturbation) {
		Map<String, String> infoPerturbationVoyage = null;
		for(Voyage v : voyagesJaxb) {
			if(String.valueOf(v.getIdVoyage()).equals(infoPerturbation.get("idVoyage"))){
				infoPerturbationVoyage = infoPerturbation;
			}else {
				infoPerturbationVoyage = null;
			}
			jms.sendVoyage(v, time,infoPerturbation);
		}	
	}

	@Override
	public void sendMessagePerturbationEtVoyages(Voyage voyage, int time) {
		VoyageDuJour voyageDuJour= serviceInfoVoyage.getListVoyage(voyage.getTrain().getIdTrain(), time);
		String TypePerturbation = voyage.getTrain().getPerturbation().getType();
		int RetardArret = voyage.getTrain().getPerturbation().getDuree();
		String impactPerturbation  = null;
		if(TypePerturbation != "Retard") {
			impactPerturbation = incidentRepo.getImpactIncident(TypePerturbation);
		}
		Map<String, String> infoPerturbation = new HashMap<String, String>();
		infoPerturbation.put("idVoyage", String.valueOf(voyage.getIdVoyage()));
		infoPerturbation.put("typePerturbation", TypePerturbation);
		infoPerturbation.put("impactPerturbation", impactPerturbation);
		infoPerturbation.put("RetardArret", String.valueOf(RetardArret));
     	sendInfoVoyage(voyageDuJour.getVoyages(), time,infoPerturbation);
		
	}
	

}
