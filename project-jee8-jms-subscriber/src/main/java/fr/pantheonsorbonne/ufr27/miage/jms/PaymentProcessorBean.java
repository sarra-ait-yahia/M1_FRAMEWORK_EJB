package fr.pantheonsorbonne.ufr27.miage.jms;

import java.io.StringReader;
import java.util.Random;

import javax.annotation.PostConstruct;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.sun.tools.xjc.model.CClassInfo;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Ccinfo;

@ApplicationScoped
public class PaymentProcessorBean {

	@Inject
	private ConnectionFactory connectionFactory;

	@Inject
	@Named("PaymentAckQueue")
	private Queue queueAck;

	@Inject
	@Named("PaymentQueue")
	private Queue queuePayment;

	private Connection connection;

	private Session session;

	private MessageConsumer consumer;
	private MessageProducer producer;

	@PostConstruct
	private void init() {

		try {

			connection = connectionFactory.createConnection("nicolas", "nicolas");
			connection.start();
			session = connection.createSession(true, Session.SESSION_TRANSACTED);
			consumer = session.createConsumer(queuePayment);
			producer = session.createProducer(queueAck);

		} catch (JMSException e) {
			throw new RuntimeException("failed to create JMS Session", e);
		}

	}

	public void onMessage(TextMessage message) {
		try {

			JAXBContext context = JAXBContext.newInstance(Ccinfo.class);
			StringReader reader = new StringReader(message.getText());
			double amount = message.getDoubleProperty("amount");
			Ccinfo ccinfo = (Ccinfo) context.createUnmarshaller().unmarshal(reader);

			if (isPaymentValdated(ccinfo.getNumber(), ccinfo.getValidityDate(), ccinfo.getCcv(), amount)) {
				Message outgoingMessage = this.session.createMessage();
				outgoingMessage.setIntProperty("paymentId", message.getIntProperty("paymentId"));
				outgoingMessage.setBooleanProperty("validated", true);
				producer.send(outgoingMessage);
			}

		} catch (JMSException | JAXBException e) {
			throw new RuntimeException("failed to process payment", e);
		}

	}

	private boolean isPaymentValdated(String ccnumber, String date, int ccv, double amount) {
		try {
			System.out.println("processing payment");
			Thread.sleep(19);
			System.out.println("processing payment [done]");
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		return true;

	}

	public void consume() throws JMSException {
		try (JMSContext context = connectionFactory.createContext(JMSContext.SESSION_TRANSACTED);) {

			onMessage((TextMessage) consumer.receive());
			context.commit();
		} catch (RuntimeException rte) {
			System.out.println("unknown exception, client will retry automatically " + rte.getMessage());
		}

	}

}
