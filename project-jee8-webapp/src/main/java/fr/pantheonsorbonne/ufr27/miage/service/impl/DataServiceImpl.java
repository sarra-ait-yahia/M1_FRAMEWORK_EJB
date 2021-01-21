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

import fr.pantheonsorbonne.ufr27.miage.jpa.Gare;
import fr.pantheonsorbonne.ufr27.miage.jpa.IncidentImpact;
import fr.pantheonsorbonne.ufr27.miage.jpa.PassageSegment;
import fr.pantheonsorbonne.ufr27.miage.jpa.PerturbationJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.Quai;
import fr.pantheonsorbonne.ufr27.miage.jpa.Reservation;
import fr.pantheonsorbonne.ufr27.miage.jpa.SegmentJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.TrainJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.TrajetJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPAAvecRes;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPASansRes;
import fr.pantheonsorbonne.ufr27.miage.service.DataService;

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
		this.em.getTransaction().begin();
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
			PassageSegment passage1 = new PassageSegment(5,5, 20,20, false, new ArrayList() {{ add(segment1);}},25);
			listData.add(passage1);
			PassageSegment passage2 = new PassageSegment(23,23, 41,41, false, new ArrayList() {{ add(segment2);}},55);
			listData.add(passage2);
			PassageSegment passage3 = new PassageSegment(44,44, 56,56, false, new ArrayList() {{ add(segment3);}},75);
			listData.add(passage3);
			PassageSegment passage4 = new PassageSegment(70,70,100, 100, false, new ArrayList() {{ add(segment3); add(segment2);}},50);
			listData.add(passage4);
			PassageSegment passage5 = new PassageSegment(103,103,118, 118, false, new ArrayList() {{ add(segment1);}},75);
			listData.add(passage5);
			PassageSegment passage6 = new PassageSegment(130,130,145, 145, false, new ArrayList() {{ add(segment1);}},25);
			listData.add(passage6);
			PassageSegment passage7 = new PassageSegment(148,148,166, 166, false, new ArrayList() {{ add(segment2);}},55);
			listData.add(passage7);
			PassageSegment passage8 = new PassageSegment(169,169,181, 181, false, new ArrayList() {{ add(segment3);}},75);
			listData.add(passage8);
			PassageSegment passage9 = new PassageSegment(200,200,215,215, false, new ArrayList() {{ add(segment3);}},20);
			listData.add(passage9);
			PassageSegment passage10 = new PassageSegment(218,218,236, 236, false, new ArrayList() {{add(segment2);}},50);
			listData.add(passage10);
			PassageSegment passage11 = new PassageSegment(239,239,251, 251, false, new ArrayList() {{ add(segment1);}},75);
			listData.add(passage11);
			PassageSegment passage12 = new PassageSegment(40,40,55, 55, false, new ArrayList() {{ add(segment1);}},25);
			listData.add(passage12);
			PassageSegment passage13 = new PassageSegment(58,58,88, 88, false, new ArrayList() {{ add(segment2);add(segment3);}},75);
			listData.add(passage13);
			PassageSegment passage14 = new PassageSegment(100,100,112, 112, false, new ArrayList() {{ add(segment3);}},20);
			listData.add(passage14);
			PassageSegment passage15 = new PassageSegment(115,115,133, 133, false, new ArrayList() {{add(segment2);}},50);
			listData.add(passage15);
			PassageSegment passage16 = new PassageSegment(136,136,151, 151, false, new ArrayList() {{ add(segment1);}},75);
			listData.add(passage16);
			PassageSegment passage17 = new PassageSegment(2,2,20, 20, false, new ArrayList() {{add(segment4);}},60);
			listData.add(passage17);
			PassageSegment passage18 = new PassageSegment(30,30,45, 45, false, new ArrayList() {{ add(segment5);}},110);
			listData.add(passage18);
			PassageSegment passage19 = new PassageSegment(100,100,160, 160, false, new ArrayList() {{add(segment6);}},200);
			listData.add(passage19);
			PassageSegment passage20 = new PassageSegment(170,170,200, 200, false, new ArrayList() {{ add(segment7);}},300);
			listData.add(passage20);
			
			//create Gares
			Gare gareA = new Gare("A");
			listData.add(gareA);
			Gare gareB = new Gare("B");
			listData.add(gareB);
			Gare gareC = new Gare("C");
			listData.add(gareC);
			Gare gareD = new Gare("D");
			listData.add(gareD);
			Gare gareE = new Gare("E");
			listData.add(gareE);
			Gare gareF = new Gare("F");
			listData.add(gareF);
			Gare gareG = new Gare("G");
			listData.add(gareG);
			Gare gareH = new Gare("H");
			listData.add(gareH);
			
			
			//create Quai
			Quai quaiA1 = new Quai("A1");
			listData.add(quaiA1);
			Quai quaiA2 = new Quai("A2");
			listData.add(quaiA2);
			Quai quaiA5 = new Quai("A5");
			listData.add(quaiA5);
			Quai quaiB1 = new Quai("B1");
			listData.add(quaiB1);
			Quai quaiB2 = new Quai("B2");
			listData.add(quaiB2);
			Quai quaiB3 = new Quai("B3");
			listData.add(quaiB3);
			Quai quaiB5 = new Quai("B5");
			listData.add(quaiB5);
			Quai quaiC2 = new Quai("C2");
			listData.add(quaiC2);
			Quai quaiC3 = new Quai("C3");
			listData.add(quaiC3);
			Quai quaiC5 = new Quai("C5");
			listData.add(quaiC5);
			Quai quaiD1 = new Quai("D1");
			listData.add(quaiD1);
			Quai quaiD3 = new Quai("D3");
			listData.add(quaiD3);
			Quai quaiD4 = new Quai("D4");
			listData.add(quaiD4);
			Quai quaiD5 = new Quai("D5");
			listData.add(quaiD5);
			Quai quaiE1 = new Quai("E1");
			listData.add(quaiE1);
			Quai quaiE5 = new Quai("E5");
			listData.add(quaiE5);
			Quai quaiF2 = new Quai("F2");
			listData.add(quaiF2);
			Quai quaiF5 = new Quai("F5");
			listData.add(quaiF5);
			Quai quaiG1 = new Quai("G1");
			listData.add(quaiG1);
			Quai quaiG5 = new Quai("G5");
			listData.add(quaiG5);
			Quai quaiH2 = new Quai("H2");
			listData.add(quaiH2);
			Quai quaiH5 = new Quai("H5");
			listData.add(quaiH5);
			
			//createVoyage
			LocalDate date = LocalDate.of(2021, 01, 23);
			VoyageJPA voyage1 = new VoyageJPASansRes(date, 5,5,56,56, (double)100, trajet1,"aller",train1, new ArrayList() {{ add(passage1); add(passage2); add(passage3);}} , (double)75, new ArrayList<PerturbationJPA>(), "a faire",new ArrayList() {{ add(gareA); add(gareB); add(gareC);add(gareD);}},new ArrayList() {{ add(quaiA1); add(quaiB1); add(quaiC2);add(quaiD3);}}) ;
			listData.add(voyage1);
			VoyageJPA voyage2 = new VoyageJPASansRes(date, 70,70,118,118, (double)100, trajet1,"retour",train1, new ArrayList() {{ add(passage4); add(passage5);}} , (double)75, new ArrayList<PerturbationJPA>(),"a faire",new ArrayList() {{ add(gareD); add(gareB); add(gareA);}},new ArrayList() {{ add(quaiD3); add(quaiB1); add(quaiA1);}}) ;
			listData.add(voyage2);
			VoyageJPA voyage3 = new VoyageJPASansRes(date, 130,130,181,181, (double)100, trajet1,"aller",train1, new ArrayList() {{ add(passage6); add(passage7); add(passage8);}} , (double)75, new ArrayList<PerturbationJPA>(),  "a faire",new ArrayList() {{ add(gareA); add(gareB); add(gareC);add(gareD);}},new ArrayList() {{ add(quaiA1); add(quaiB1); add(quaiC2);add(quaiD3);}}) ;
			listData.add(voyage3);
			VoyageJPA voyage4 = new VoyageJPASansRes(date, 200,200,251,251, (double)100, trajet1,"retour",train1, new ArrayList() {{ add(passage9); add(passage10); add(passage11);}} , (double)75, new ArrayList<PerturbationJPA>(), "a faire",new ArrayList() {{ add(gareD); add(gareC); add(gareB);add(gareA);}},new ArrayList() {{ add(quaiD3); add(quaiC2); add(quaiB1);add(quaiA1);}}) ;
			listData.add(voyage4);
			VoyageJPA voyage5 = new VoyageJPASansRes(date, 40,40,88,88, (double)100, trajet1,"aller",train2, new ArrayList() {{ add(passage12); add(passage13); }} , (double)75, new ArrayList<PerturbationJPA>(),"a faire",new ArrayList() {{ add(gareA); add(gareB); add(gareD);}},new ArrayList() {{ add(quaiA2); add(quaiB2);add(quaiD1);}}) ;
			listData.add(voyage5);
			VoyageJPA voyage6 = new VoyageJPASansRes(date, 100,100,151,151, (double)100, trajet1,"retour",train2, new ArrayList() {{ add(passage14); add(passage15); add(passage16);}} , (double)75, new ArrayList<PerturbationJPA>(), "a faire",new ArrayList() {{ add(gareD); add(gareC); add(gareB);add(gareA);}},new ArrayList() {{ add(quaiD1); add(quaiC3); add(quaiB2);add(quaiA2);}}) ;
			listData.add(voyage6);
			VoyageJPA voyage7 = new VoyageJPAAvecRes(date, 2,2,45,45, (double)200, trajet2,"aller",train3, new ArrayList() {{ add(passage17); add(passage18); }} , (double)110, new ArrayList<PerturbationJPA>(), "a faire",new ArrayList() {{ add(gareE); add(gareF); add(gareB);}}, new ArrayList() {{ add(quaiE1); add(quaiF2); add(quaiB3);}}, new ArrayList<Reservation>(), new ArrayList() {{ add(voyage5);}}) ;
			listData.add(voyage7);
			VoyageJPA voyage8 = new VoyageJPAAvecRes(date, 100,100,200,200, (double)200, trajet3,"aller",train4, new ArrayList() {{ add(passage19); add(passage20);}} , (double)300, new ArrayList<PerturbationJPA>(),"a faire", new ArrayList() {{ add(gareD); add(gareG); add(gareH);}},new ArrayList() {{ add(quaiD4); add(quaiG1); add(quaiH2);}}, new ArrayList<Reservation>(),new ArrayList<VoyageJPASansRes>()) ;
			listData.add(voyage8);
			
			IncidentImpact incident1 = new IncidentImpact("incident naturel (vent)","retard");
			listData.add(incident1);
			IncidentImpact incident2 = new IncidentImpact("accident de personne","arret");
			listData.add(incident2);
			IncidentImpact incident3 = new IncidentImpact("incident technique (moteur en panne)","arret");
			listData.add(incident3);
			IncidentImpact incident4 = new IncidentImpact("incident technique (rails défectueux)","retard");
			listData.add(incident4);
			
			for(Object o : listData){
				this.em.persist(o);
			}
			this.em.getTransaction().commit();

		} catch (Exception e) {
			this.em.getTransaction().rollback();
			throw new RuntimeException("failed to initiate data", e);
		}	
	}

}

