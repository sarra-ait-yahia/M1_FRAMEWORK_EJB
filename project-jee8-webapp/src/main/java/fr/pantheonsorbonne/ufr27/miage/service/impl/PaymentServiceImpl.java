package fr.pantheonsorbonne.ufr27.miage.service.impl;

import java.io.StringWriter;

import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.persistence.EntityManager;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import fr.pantheonsorbonne.ufr27.miage.dao.InvoiceDAO;
import fr.pantheonsorbonne.ufr27.miage.exception.NoDebtException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.jpa.Payment;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Ccinfo;
import fr.pantheonsorbonne.ufr27.miage.service.PaymentService;

@ApplicationScoped
@ManagedBean
public class PaymentServiceImpl implements PaymentService {

	@Inject
	EntityManager em;

	@Inject
	InvoiceDAO invoiceDao;

	@Inject
	private ConnectionFactory connectionFactory;

	@Inject
	@Named("PaymentQueue")
	private Queue queue;

	private Connection connection;

	private Session session;

	private MessageProducer messageProducer;

	@PostConstruct
	private void init() {

		try {
			connection = connectionFactory.createConnection("nicolas", "nicolas");
			connection.start();
			session = connection.createSession();
			messageProducer = session.createProducer(queue);
		} catch (JMSException e) {
			throw new RuntimeException("failed to create JMS Session", e);
		}
	}

	@Override
	public int initiatePayAllDebts(Ccinfo info, int userId) throws NoDebtException, NoSuchUserException {
		em.getTransaction().begin();
		try {

			double amount = invoiceDao.getUserDebt(userId);
			if (amount <= 0) {
				throw new NoDebtException();
			}
			Payment p = new Payment();
			p.setAmount(amount);
			p.setValidated(false);
			p.getInvoices().addAll(invoiceDao.getUnpaiedInvoices(userId));
			em.persist(p);

			TextMessage message = session.createTextMessage();

			JAXBContext context = JAXBContext.newInstance(Ccinfo.class);
			StringWriter writer = new StringWriter();
			context.createMarshaller().marshal(info, writer);

			message.setText(writer.toString());
			message.setIntProperty("paymentId", p.getId());
			message.setDoubleProperty("amount", p.getAmount());
			messageProducer.send(message);
			em.getTransaction().commit();
			return p.getId();

		} catch (JMSException | JAXBException e) {
			em.getTransaction().rollback();
			throw new RuntimeException("failed to initiate payment", e);
		}
	}

	@Override
	public int initiatePayment(Ccinfo info, int userId, int invoiceId, double amount) {
		em.getTransaction().begin();
		try {

			Payment p = new Payment();
			p.setAmount(amount);
			p.setValidated(false);
			em.persist(p);

			Message message = session.createMessage();
			message.setStringProperty("ccnumber", info.getNumber());
			message.setStringProperty("date", info.getValidityDate());
			message.setIntProperty("ccv", info.getCcv());
			message.setDoubleProperty("amount", amount);
			message.setIntProperty("userId", userId);
			message.setIntProperty("paymentId", p.getId());

			em.getTransaction().commit();

			messageProducer.send(message);
			return p.getId();

		} catch (JMSException e) {
			em.getTransaction().rollback();
			throw new RuntimeException("failed to initiate payment", e);
		}

	}

}
