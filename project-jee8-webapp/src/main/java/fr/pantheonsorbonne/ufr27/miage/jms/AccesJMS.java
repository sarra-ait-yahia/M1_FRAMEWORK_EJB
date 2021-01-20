package fr.pantheonsorbonne.ufr27.miage.jms;

import java.io.StringWriter;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import fr.pantheonsorbonne.ufr27.miage.exception.NoDebtException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Ccinfo;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Gare;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.VoyageDuJour;

@ApplicationScoped
@ManagedBean

public class AccesJMS {

	@Inject
	EntityManager em;

	@Inject
	private ConnectionFactory connectionFactory;

	@Inject
	@Named("VoyageQueue")
	private Queue queue;
	

	private Connection connection;

	private Session session;

	private MessageProducer messageProducer;

	@PostConstruct
	private void init() {

		try {
			connection = connectionFactory.createConnection("infoCentre", "infoGare");
			connection.start();
			session = connection.createSession();
			messageProducer = session.createProducer(queue);
		} catch (JMSException e) {
			throw new RuntimeException("failed to create JMS Session", e);
		}
	}

	
	public void sendVoyage(Voyage voyage, int time) {
		try {

			TextMessage message = session.createTextMessage();
            
			
			StringWriter stringWriter = new StringWriter();
			JAXBContext jaxbContext = JAXBContext.newInstance(Voyage.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			  // format the XML output
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			QName qName = new QName("fr.pantheonsorbonne.ufr27.miage.model.jaxb", "voyage");
			JAXBElement<Voyage> root = new JAXBElement<>(qName, Voyage.class, voyage);

			jaxbMarshaller.marshal(root, stringWriter);
			  
			message.setText(stringWriter.toString());
			String gareDesservi = "";
			for(Gare g : voyage.getGares()) {
				gareDesservi+= g.getNom();
				}
			message.setStringProperty("GareDesservi", gareDesservi);
			message.setIntProperty("time", time);
			messageProducer.send(message);

		} catch (JMSException | JAXBException e) {
			throw new RuntimeException("failed to send voyage", e);
		}
	}



}
