package fr.pantheonsorbonne.ufr27.miage.jms;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
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
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import fr.pantheonsorbonne.ufr27.miage.jms.classe.gare.AffichageVoyage;
import fr.pantheonsorbonne.ufr27.miage.jms.classe.gare.Gare;
import fr.pantheonsorbonne.ufr27.miage.jms.classe.gare.JaxbToAffichageMapper;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;

@ApplicationScoped
public class MessageDispatcher {

	@Inject
	private ConnectionFactory connectionFactory;

	@Inject
	@Named("VoyageQueue")
	private Queue queueVoyage;

	private Topic voyageTopic;

	private Connection connection;

	private Session session;

	private MessageConsumer consumer;

	private MessageProducer messageProducer;

	@PostConstruct
	private void init() {

		try {

			connection = connectionFactory.createConnection("infoCentre", "infoGare");
			connection.start();
			session = connection.createSession();
			voyageTopic = session.createTopic("voyageTopic");
			consumer = session.createConsumer(queueVoyage);
			messageProducer = session.createProducer(voyageTopic);

		} catch (JMSException e) {
			throw new RuntimeException("failed to create JMS Session", e);
		}

	}

	public void onMessage(TextMessage message) {
		try {

			JAXBContext context = JAXBContext.newInstance(Voyage.class);
			StringReader reader = new StringReader(message.getText());
			String gareDesservi = message.getStringProperty("GareDesservi");
			char[] charList = gareDesservi.toCharArray();
			for (char g : charList) {
				SeContainerInitializer initializer = SeContainerInitializer.newInstance();

				try (SeContainer container = initializer.disableDiscovery().addPackages(true, InfoGareBean.class)
						.initialize()) {

					InfoGareBean processor = container.select(InfoGareBean.class).get();
					Gare gare = new Gare(Character.toString(g), processor);
					new Thread(gare).start();
					processor.setGare(gare);
				}

			}
			Thread.currentThread().join();

			// envoie du message
			messageProducer.send(message);

		} catch (JMSException | JAXBException | InterruptedException e) {
			throw new RuntimeException("failed in receiving voyage", e);
		}

	}

	public void consume() throws JMSException {

		onMessage((TextMessage) consumer.receive());

	}
}
