package fr.pantheonsorbonne.ufr27.miage.service.impl;

import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import javax.xml.bind.JAXBException;

import fr.pantheonsorbonne.ufr27.miage.dao.InvoiceDAO;
import fr.pantheonsorbonne.ufr27.miage.exception.NoDebtException;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.jpa.PassageSegment;
import fr.pantheonsorbonne.ufr27.miage.jpa.Payment;
import fr.pantheonsorbonne.ufr27.miage.jpa.PerturbationJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.Reservation;
import fr.pantheonsorbonne.ufr27.miage.jpa.SegmentJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.TrainJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.TrajetJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Ccinfo;
import fr.pantheonsorbonne.ufr27.miage.service.DataService;
import fr.pantheonsorbonne.ufr27.miage.service.PaymentService;

@ApplicationScoped
@ManagedBean
public class DataServiceImpl implements DataService {
	
	EntityManager em;
	List<Object> listData = new ArrayList<Object>();

	public DataServiceImpl(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public void createData() {
		em.getTransaction().begin();
		try {
			//createSegments
			SegmentJPA segment1 = new SegmentJPA("A","B",25);
			listData.add(segment1);
			SegmentJPA segment2 = new SegmentJPA("B","C",30);
			listData.add(segment2);
			SegmentJPA segment3 = new SegmentJPA("C","D",20);
			listData.add(segment3);
			SegmentJPA segment4 = new SegmentJPA("E","F",60);
			listData.add(segment4);
			SegmentJPA segment5 = new SegmentJPA("F","B",50);
			listData.add(segment5);
			SegmentJPA segment6 = new SegmentJPA("D","G",200);
			listData.add(segment6);
			SegmentJPA segment7 = new SegmentJPA("G","H",100);
			listData.add(segment7);
			
			//createTrajet
			TrajetJPA trajet1 = new TrajetJPA("AD", new ArrayList() {{ add(segment1); add(segment2); add(segment3);}}) ; 			
			listData.add(trajet1);
			TrajetJPA trajet2 = new TrajetJPA("EB", new ArrayList() {{ add(segment4); add(segment5);}}) ; 			
			listData.add(trajet2);
			TrajetJPA trajet3 = new TrajetJPA("DH", new ArrayList() {{ add(segment6); add(segment7);}}) ; 			
			listData.add(trajet3);
            
			//createTrains
			TrainJPA train1 = new TrainJPA("trainA", "TER");
			listData.add(train1);
			TrainJPA train2 = new TrainJPA("trainB", "TER");
			listData.add(train2);
			TrainJPA train3 = new TrainJPA("trainC", "TGV");
			listData.add(train3);
			TrainJPA train4 = new TrainJPA("trainD", "TGV");
			listData.add(train4);
			
			//createPassageSegment
			PassageSegment passage1 = new PassageSegment(5, 20, false, new ArrayList() {{ add(segment1);}});
			listData.add(passage1);
			PassageSegment passage2 = new PassageSegment(23, 41, false, new ArrayList() {{ add(segment2);}});
			listData.add(passage2);
			PassageSegment passage3 = new PassageSegment(44, 56, false, new ArrayList() {{ add(segment3);}});
			listData.add(passage3);
			PassageSegment passage4 = new PassageSegment(70, 100, false, new ArrayList() {{ add(segment3); add(segment2);}});
			listData.add(passage4);
			PassageSegment passage5 = new PassageSegment(103, 118, false, new ArrayList() {{ add(segment1);}});
			listData.add(passage5);
			PassageSegment passage6 = new PassageSegment(130, 145, false, new ArrayList() {{ add(segment1);}});
			listData.add(passage6);
			PassageSegment passage7 = new PassageSegment(148, 166, false, new ArrayList() {{ add(segment2);}});
			listData.add(passage7);
			PassageSegment passage8 = new PassageSegment(169, 181, false, new ArrayList() {{ add(segment3);}});
			listData.add(passage8);
			PassageSegment passage9 = new PassageSegment(200, 215, false, new ArrayList() {{ add(segment3);}});
			listData.add(passage9);
			PassageSegment passage10 = new PassageSegment(218, 236, false, new ArrayList() {{add(segment2);}});
			listData.add(passage10);
			PassageSegment passage11 = new PassageSegment(239, 251, false, new ArrayList() {{ add(segment1);}});
			listData.add(passage11);
			PassageSegment passage12 = new PassageSegment(40, 55, false, new ArrayList() {{ add(segment1);}});
			listData.add(passage12);
			PassageSegment passage13 = new PassageSegment(58, 88, false, new ArrayList() {{ add(segment2);add(segment3);}});
			listData.add(passage13);
			PassageSegment passage14 = new PassageSegment(100, 112, false, new ArrayList() {{ add(segment3);}});
			listData.add(passage14);
			PassageSegment passage15 = new PassageSegment(115, 133, false, new ArrayList() {{add(segment2);}});
			listData.add(passage15);
			PassageSegment passage16 = new PassageSegment(136, 151, false, new ArrayList() {{ add(segment1);}});
			listData.add(passage16);
			PassageSegment passage17 = new PassageSegment(2, 20, false, new ArrayList() {{add(segment4);}});
			listData.add(passage17);
			PassageSegment passage18 = new PassageSegment(30, 45, false, new ArrayList() {{ add(segment5);}});
			listData.add(passage18);
			PassageSegment passage19 = new PassageSegment(100, 160, false, new ArrayList() {{add(segment6);}});
			listData.add(passage19);
			PassageSegment passage20 = new PassageSegment(170, 200, false, new ArrayList() {{ add(segment7);}});
			listData.add(passage20);
			
			//createVoyage
			LocalDate date = LocalDate.of(2021, 01, 23);
			VoyageJPA voyage1 = new VoyageJPA(date, 5,56, (double)100, trajet1,train1, new ArrayList() {{ add(passage1); add(passage2); add(passage3);}} , (double)75, new ArrayList<PerturbationJPA>(), new ArrayList<Reservation>(), false) ;
			listData.add(voyage1);
			VoyageJPA voyage2 = new VoyageJPA(date, 70,118, (double)100, trajet1,train1, new ArrayList() {{ add(passage4); add(passage5);}} , (double)75, new ArrayList<PerturbationJPA>(), new ArrayList<Reservation>(), false) ;
			listData.add(voyage2);
			VoyageJPA voyage3 = new VoyageJPA(date, 130,181, (double)100, trajet1,train1, new ArrayList() {{ add(passage6); add(passage7); add(passage8);}} , (double)75, new ArrayList<PerturbationJPA>(), new ArrayList<Reservation>(), false) ;
			listData.add(voyage3);
			VoyageJPA voyage4 = new VoyageJPA(date, 200,251, (double)100, trajet1,train1, new ArrayList() {{ add(passage9); add(passage10); add(passage11);}} , (double)75, new ArrayList<PerturbationJPA>(), new ArrayList<Reservation>(), false) ;
			listData.add(voyage4);
			VoyageJPA voyage5 = new VoyageJPA(date, 40,88, (double)100, trajet1,train2, new ArrayList() {{ add(passage12); add(passage13); }} , (double)75, new ArrayList<PerturbationJPA>(), new ArrayList<Reservation>(), false) ;
			listData.add(voyage5);
			VoyageJPA voyage6 = new VoyageJPA(date, 100,151, (double)100, trajet1,train2, new ArrayList() {{ add(passage14); add(passage15); add(passage16);}} , (double)75, new ArrayList<PerturbationJPA>(), new ArrayList<Reservation>(), false) ;
			listData.add(voyage6);
			VoyageJPA voyage7 = new VoyageJPA(date, 2,45, (double)200, trajet2,train3, new ArrayList() {{ add(passage17); add(passage18); }} , (double)110, new ArrayList<PerturbationJPA>(), new ArrayList<Reservation>(), false) ;
			listData.add(voyage7);
			VoyageJPA voyage8 = new VoyageJPA(date, 100,200, (double)200, trajet3,train4, new ArrayList() {{ add(passage19); add(passage20);}} , (double)300, new ArrayList<PerturbationJPA>(), new ArrayList<Reservation>(), false) ;
			listData.add(voyage8);
			
			for(Object o : listData){
				em.persist(o);
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("failed to initiate data", e);
		}	
	}

}

