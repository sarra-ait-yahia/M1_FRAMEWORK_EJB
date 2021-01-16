package fr.pantheonsorbonne.ufr27.miage.service.impl;

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

import fr.pantheonsorbonne.ufr27.miage.dao.InvoiceDAO;
import fr.pantheonsorbonne.ufr27.miage.service.GestionPerturbationService;
import fr.pantheonsorbonne.ufr27.miage.service.InformationVoyageService;
import fr.pantheonsorbonne.ufr27.miage.service.NotifyInfoGareService;

@ApplicationScoped
@ManagedBean
public class NotifyInfoGareServiceImpl implements NotifyInfoGareService {

	@Inject
	EntityManager em;

	@Inject
	InvoiceDAO invoiceDao;

	@Inject
	private ConnectionFactory connectionFactory;

	@Inject
	@Named("VoyageQueue")
	private Queue queue;

	private Connection connection;

	private Session session;

	private MessageProducer messageProducer;
	
	@Override
	public void sendInfoVoyage() {
		
		
	}
	

	

}
