package fr.pantheonsorbonne.ufr27.miage.jms;

import java.io.StringReader;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import fr.pantheonsorbonne.ufr27.miage.jms.classe.gare.AffichageVoyage;
import fr.pantheonsorbonne.ufr27.miage.jms.classe.gare.Gare;
import fr.pantheonsorbonne.ufr27.miage.jms.classe.gare.JaxbToAffichageMapper;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Ccinfo;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;

@ApplicationScoped
public class InfoGareBean {


	@Inject
	private ConnectionFactory connectionFactory;

	@Inject
	@Named("VoyageAckQueue")
	private Queue queueAck;

	@Inject
	@Named("VoyageQueue")
	private Queue queueVoyage;

	
	private Connection connection;

	private Session session;

	private MessageConsumer consumer;
	

	private MessageProducer producer;
	
	private Gare gare;

	public Gare getGare() {
		return gare;
	}

	public void setGare(Gare gare) {
		this.gare = gare;
	}
	
	@PostConstruct
	private void init() {

		try {

			connection = connectionFactory.createConnection("infoCentre", "infoGare");
			connection.start();
			session = connection.createSession();
			consumer = session.createConsumer(queueVoyage);

		} catch (JMSException e) {
			throw new RuntimeException("failed to create JMS Session", e);
		}

	}

	public void onMessage(TextMessage message) {
		try {

			JAXBContext context = JAXBContext.newInstance(Voyage.class);
			StringReader reader = new StringReader(message.getText());
			String gareDesservi = message.getStringProperty("GareDesservi");
			if(gareDesservi.contains(this.gare.getName())) {
				Voyage voyage = (Voyage) context.createUnmarshaller().unmarshal(reader);
				this.gare.setTime(message.getIntProperty("time"));
				JaxbToAffichageMapper mapper = new JaxbToAffichageMapper();
				List<AffichageVoyage> affichageVoyages = mapper.createAffichageVoyage(voyage, this.gare);
				if(affichageVoyages.size() != 0)
					this.gare.afficherVoyage(affichageVoyages);
			}
			
		} catch (JMSException | JAXBException e) {
			throw new RuntimeException("failed in receiving voyage", e);
		}

	}

	public void consume() throws JMSException {
		
			onMessage((TextMessage) consumer.receive());
	

	}
}
