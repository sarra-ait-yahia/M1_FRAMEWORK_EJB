package fr.pantheonsorbonne.ufr27.miage.jms;

import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageListener;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.persistence.EntityManager;

import fr.pantheonsorbonne.ufr27.miage.jpa.Invoice;
import fr.pantheonsorbonne.ufr27.miage.jpa.Payment;

@ManagedBean
public class PaymentValidationAckownledgerBean implements MessageListener {

	@ApplicationScoped
	@Inject
	EntityManager em;

	@Inject
	private ConnectionFactory connectionFactory;

	@Inject
	@Named("PaymentAckQueue")
	private Queue queueAck;

	private Connection connection;

	private Session session;

	private MessageConsumer consumer;

	@PostConstruct
	private void init() {

		try {
			connection = connectionFactory.createConnection("nicolas", "nicolas");
			connection.start();
			session = connection.createSession();
			consumer = session.createConsumer(queueAck);

			MessageListener listener = this;

			new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
							Message message = consumer.receive();
							listener.onMessage(message);
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			}).start();

		} catch (JMSException e) {
			throw new RuntimeException("failed to create JMS Session", e);
		}

	}

	@Override
	public void onMessage(Message message) {
		em.getTransaction().begin();
		try {
			int paymentId = message.getIntProperty("paymentId");
			boolean b = message.getBooleanProperty("validated");
			if (b) {
				Payment payment = em.find(Payment.class, paymentId);
				payment.setValidated(true);
				
				
				em.merge(payment);
				for (Invoice invoice : payment.getInvoices()) {
					invoice.setPayed(true);
					em.merge(invoice);
				}
				em.getTransaction().commit();
			}
		} catch (JMSException e) {
			em.getTransaction().rollback();
			throw new RuntimeException("failed to validate payment ", e);
		}

	}

}
