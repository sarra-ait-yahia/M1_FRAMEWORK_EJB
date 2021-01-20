package fr.pantheonsorbonne.ufr27.miage.service.impl;

import java.util.List;

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
import fr.pantheonsorbonne.ufr27.miage.service.GestionPerturbationService;
import fr.pantheonsorbonne.ufr27.miage.service.InformationVoyageService;
import fr.pantheonsorbonne.ufr27.miage.service.NotifyInfoGareService;

@ApplicationScoped
@ManagedBean
public class NotifyInfoGareServiceImpl implements NotifyInfoGareService {


	@Inject
 	AccesJMS jms;
	
	@Override
	public void sendInfoVoyage(List<Voyage> voyagesJaxb, int time) {
		for(Voyage v : voyagesJaxb) {
			jms.sendVoyage(v, time);
		}	
	}

	@Override
	public void sendMessagePerturbationEtVoyages(Voyage voyage, int time) {
		// TODO Auto-generated method stub
		
	}
	

}
