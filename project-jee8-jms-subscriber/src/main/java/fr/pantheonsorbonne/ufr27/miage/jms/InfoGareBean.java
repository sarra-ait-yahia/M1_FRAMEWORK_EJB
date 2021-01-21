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
import javax.jms.Topic;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import fr.pantheonsorbonne.ufr27.miage.jms.classe.gare.AffichageVoyage;
import fr.pantheonsorbonne.ufr27.miage.jms.classe.gare.Gare;
import fr.pantheonsorbonne.ufr27.miage.jms.classe.gare.JaxbToAffichageMapper;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;

@ApplicationScoped
public class InfoGareBean {

	@Inject
	private ConnectionFactory connectionFactory;

	private Topic voyageTopic;

	private Connection connection;

	private Session session;

	private MessageConsumer consumer;

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
			voyageTopic = session.createTopic("voyageTopic");
			consumer = session.createConsumer(voyageTopic);

		} catch (JMSException e) {
			throw new RuntimeException("failed to create JMS Session", e);
		}

	}

	public void onMessage(TextMessage message) {
		try {

			JAXBContext context = JAXBContext.newInstance(Voyage.class);
			StringReader reader = new StringReader(message.getText());

			Voyage voyage = (Voyage) context.createUnmarshaller().unmarshal(reader);
			this.gare.setTime(message.getIntProperty("time"));
			JaxbToAffichageMapper mapper = new JaxbToAffichageMapper();
			List<AffichageVoyage> affichageVoyages = mapper.createAffichageVoyage(voyage, this.gare);
			if (affichageVoyages.size() != 0)
				this.gare.afficherVoyage(affichageVoyages);
			if(message.getStringProperty("idVoyage") != null) {
				this.gare.afficherMessagePerturbation(voyage.getTrain().getIdTrain(),voyage.getTrajet().getIdTrajet(),message.getIntProperty("time"), message.getStringProperty("typePerturbation"),
						message.getStringProperty("impactPerturbation"),message.getStringProperty("RetardArret"));
			}
			this.gare.terminate();

		} catch (JMSException | JAXBException e) {
			throw new RuntimeException("failed in receiving voyage", e);
		}

	}

	public void consume() throws JMSException {

		onMessage((TextMessage) consumer.receive());

	}
}
