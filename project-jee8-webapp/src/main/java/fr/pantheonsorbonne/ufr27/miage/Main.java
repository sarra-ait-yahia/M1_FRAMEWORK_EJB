package fr.pantheonsorbonne.ufr27.miage;

import java.io.IOException;
import java.net.URI;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;


import fr.pantheonsorbonne.ufr27.miage.conf.EMFFactory;
import fr.pantheonsorbonne.ufr27.miage.conf.EMFactory;
import fr.pantheonsorbonne.ufr27.miage.conf.PersistenceConf;
import fr.pantheonsorbonne.ufr27.miage.dao.PassageSegmentDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.IncidentImpactDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.VoyageDAO;
import fr.pantheonsorbonne.ufr27.miage.exception.ExceptionMapper;
import fr.pantheonsorbonne.ufr27.miage.jms.AccesJMS;
import fr.pantheonsorbonne.ufr27.miage.jms.conf.ConnectionFactorySupplier;
import fr.pantheonsorbonne.ufr27.miage.jms.conf.JMSProducer;
import fr.pantheonsorbonne.ufr27.miage.jms.conf.VoyageAckQueueSupplier;
import fr.pantheonsorbonne.ufr27.miage.jms.conf.VoyageQueueSupplier;
import fr.pantheonsorbonne.ufr27.miage.jms.utils.BrokerUtils;
import fr.pantheonsorbonne.ufr27.miage.jpa.jaxb.mapping.JaxbJpaMapper;
import fr.pantheonsorbonne.ufr27.miage.service.AjoutSuppressionVoyageService;
import fr.pantheonsorbonne.ufr27.miage.service.DataService;
import fr.pantheonsorbonne.ufr27.miage.service.GestionPerturbationService;
import fr.pantheonsorbonne.ufr27.miage.service.InformationVoyageService;
import fr.pantheonsorbonne.ufr27.miage.service.NotifyInfoGareService;
import fr.pantheonsorbonne.ufr27.miage.service.RetarderVoyageService;
import fr.pantheonsorbonne.ufr27.miage.service.impl.AjoutSuppressionVoyageServiceImpl;
import fr.pantheonsorbonne.ufr27.miage.service.impl.DataServiceImpl;
import fr.pantheonsorbonne.ufr27.miage.service.impl.GestionPerturbationServiceImpl;
import fr.pantheonsorbonne.ufr27.miage.service.impl.InformationVoyageServiceImpl;
import fr.pantheonsorbonne.ufr27.miage.service.impl.NotifyInfoGareServiceImpl;
import fr.pantheonsorbonne.ufr27.miage.service.impl.RetarderVoyageServiceImpl;
import fr.panthonsorbonne.ufr27.miage.repository.VoyageRepository;
import fr.panthonsorbonne.ufr27.miage.repository.IncidentRepository;
import fr.panthonsorbonne.ufr27.miage.repository.PassageSegmentRepository;
import fr.panthonsorbonne.ufr27.miage.repository.VoyageDuJourRepository;

/**
 * Main class.
 *
 */
public class Main {
	@Inject
	EntityManager em;

	public static final String BASE_URI = "http://localhost:8080/";

	public static HttpServer startServer() {

		final ResourceConfig rc = new ResourceConfig()//
				.packages(true, "fr.pantheonsorbonne.ufr27.miage")//
				.register(DeclarativeLinkingFeature.class)//
				.register(JMSProducer.class).register(ExceptionMapper.class).register(PersistenceConf.class)
				.register(new AbstractBinder() {

					@Override
					protected void configure() {

						bind(InformationVoyageServiceImpl.class).to(InformationVoyageService.class);
						bind(DataServiceImpl.class).to(DataService.class);
						bind(GestionPerturbationServiceImpl.class).to(GestionPerturbationService.class);
						bind(NotifyInfoGareServiceImpl.class).to(NotifyInfoGareService.class);
						bind(RetarderVoyageServiceImpl.class).to(RetarderVoyageService.class);
						bind(AjoutSuppressionVoyageServiceImpl.class).to(AjoutSuppressionVoyageService.class);
						
								
						
						bind(IncidentImpactDAO.class).to(IncidentImpactDAO.class);
						bind(PassageSegmentDAO.class).to(PassageSegmentDAO.class);
						bind(VoyageDAO.class).to(VoyageDAO.class);
						
						bind(VoyageDuJourRepository.class).to(VoyageDuJourRepository.class);
						bind(VoyageRepository.class).to(VoyageRepository.class);
						bind(PassageSegmentRepository.class).to(PassageSegmentRepository.class);
						bind(IncidentRepository.class).to(IncidentRepository.class);
						bind(JaxbJpaMapper.class).to(JaxbJpaMapper.class);
					    
						
						bindFactory(EMFFactory.class).to(EntityManagerFactory.class).in(Singleton.class);
						bindFactory(EMFactory.class).to(EntityManager.class).in(RequestScoped.class);
						bindFactory(ConnectionFactorySupplier.class).to(ConnectionFactory.class).in(Singleton.class);
						

						bindFactory(VoyageAckQueueSupplier.class).to(Queue.class).named("VoyageAckQueue")
						.in(Singleton.class);
				        bindFactory(VoyageQueueSupplier.class).to(Queue.class).named("VoyageQueue")
						.in(Singleton.class);
                        bind(AccesJMS.class).to(AccesJMS.class).in(Singleton.class);
				        
					}

				});

		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}

	/**
	 * Main method.beanbeanbeanbean
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Locale.setDefault(Locale.ENGLISH);
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		final HttpServer server = startServer();

		BrokerUtils.startBroker();

		PersistenceConf pc = new PersistenceConf();
		pc.getEM();
		pc.launchH2WS();
		
		DataService dataset = new DataServiceImpl(pc.getEM());
        dataset.createData();
        
		System.out.println(String.format(
				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
				BASE_URI));
		System.in.read();
		server.stop();

	}
}
