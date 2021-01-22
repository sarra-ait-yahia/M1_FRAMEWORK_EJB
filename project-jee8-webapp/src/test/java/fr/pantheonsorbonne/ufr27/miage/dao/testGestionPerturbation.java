package fr.pantheonsorbonne.ufr27.miage.dao;

import org.jboss.weld.junit5.EnableWeld;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.ufr27.miage.conf.PersistenceConf;
import fr.pantheonsorbonne.ufr27.miage.jpa.Gare;
import fr.pantheonsorbonne.ufr27.miage.jpa.IncidentImpact;
import fr.pantheonsorbonne.ufr27.miage.jpa.PassageSegment;
import fr.pantheonsorbonne.ufr27.miage.jpa.Passager;
import fr.pantheonsorbonne.ufr27.miage.jpa.PerturbationJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.Quai;
import fr.pantheonsorbonne.ufr27.miage.jpa.Reservation;
import fr.pantheonsorbonne.ufr27.miage.jpa.SegmentJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.TrainJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.TrajetJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPAAvecRes;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPASansRes;

@EnableWeld
public class testGestionPerturbation {

	@Inject
	EntityManager em;


	@BeforeEach
	public void setup() {

		List<Object> listData = new ArrayList<Object>();
		
		em.getTransaction().begin();
		try{//createSegments
			SegmentJPA segment1 = new SegmentJPA("A","B",10);
			listData.add(segment1);
			SegmentJPA segment2 = new SegmentJPA("B","C",5);
			listData.add(segment2);
			SegmentJPA segment3 = new SegmentJPA("C","D",15);
			listData.add(segment3);
			SegmentJPA segment4 = new SegmentJPA("E","F",100);
			listData.add(segment4);
			SegmentJPA segment5 = new SegmentJPA("F","B",150);
			listData.add(segment5);
		
			//createTrajet
			TrajetJPA trajet1 = new TrajetJPA("AD", new ArrayList() {{ add(segment1); add(segment2); add(segment3);}}) ; 			
			listData.add(trajet1);
			TrajetJPA trajet2 = new TrajetJPA("EB", new ArrayList() {{ add(segment4); add(segment5);}}) ; 			
			listData.add(trajet2);
			
			
			TrainJPA train1 = new TrainJPA("trainA", "TER");
			listData.add(train1);
			TrainJPA train2 = new TrainJPA("trainB", "TER");
			listData.add(train2);
			TrainJPA train3 = new TrainJPA("trainC", "TGV");
			listData.add(train3);
			
			PassageSegment passage1 = new PassageSegment(88,88, 100,100, false, new ArrayList() {{ add(segment1);}},10);
			listData.add(passage1);
			PassageSegment passage2 = new PassageSegment(103,103, 109,109, false, new ArrayList() {{ add(segment2);}},15);
			listData.add(passage2);
			PassageSegment passage3 = new PassageSegment(112,112, 130,130, false, new ArrayList() {{ add(segment3);}},30);
			listData.add(passage3);
			PassageSegment passage4 = new PassageSegment(0,0,30, 30, false, new ArrayList() {{ add(segment4);}},100);
			listData.add(passage4);
			PassageSegment passage5 = new PassageSegment(40,40,85, 85, false, new ArrayList() {{ add(segment5);}},250);
			listData.add(passage5);
			PassageSegment passage6 = new PassageSegment(105,105,117, 117, false, new ArrayList() {{ add(segment1);}},10);
			listData.add(passage6);
			PassageSegment passage7 = new PassageSegment(120,120,144, 144, false, new ArrayList() {{ add(segment2);add(segment3);}},30);
			listData.add(passage7);
			PassageSegment passage8 = new PassageSegment(140,140,158, 158, false, new ArrayList() {{ add(segment3);}},15);
			listData.add(passage8);
			PassageSegment passage9 = new PassageSegment(161,161,167,167, false, new ArrayList() {{ add(segment2);}},20);
			listData.add(passage9);
			PassageSegment passage10 = new PassageSegment(170,170,182, 182, false, new ArrayList() {{add(segment1);}},30);
			listData.add(passage10);
			
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
			
			String[] nom ={"Sarra","narjess","fatima","Sara","houda","Thomas","Xavier","Dalia","Jocelyn","François",
	                "Mathilde","Loic","Salah","Stéphane","Timothée","Katia","Benois","Mehdi","Anes","Hiba","Claude",
	                "Nelly","Rose","Mouna","Françoise","Jugurtha","Noé","Clément","Sofiane","Sihem","Ines","Malik","Samy",
	                "Daphné","Madou","Ahcene","Edouard","Pierre","Cloé","Haroun","david","Nina","Jeanne","Robert","Kamel",
	                "Issam","Moise","Yann","Zohra","Idir","Ilyana"};

			List<Reservation> listReservation = new ArrayList<Reservation>();
			for (String name:nom ) {
				Passager p = new Passager(name);
				listData.add(p);
				Reservation res = new Reservation(p);
				listData.add(res);
				listReservation.add(res);
			}
			LocalDate date = LocalDate.of(2021, 01, 23);
			VoyageJPA voyage1 = new VoyageJPASansRes(date, 88,88,130,130, (double)70, trajet1,"aller",train1, new ArrayList() {{ add(passage1); add(passage2); add(passage3);}} , (double)30, new ArrayList<PerturbationJPA>(), "a faire",new ArrayList() {{ add(gareA); add(gareB); add(gareC);add(gareD);}},new ArrayList() {{ add(quaiA1); add(quaiB1); add(quaiC2);add(quaiD3);}}) ;
			listData.add(voyage1);
			VoyageJPA voyage2 = new VoyageJPASansRes(date, 140,140,182,182, (double)70, trajet1,"retour",train1, new ArrayList() {{ add(passage8); add(passage9);add(passage10);}} , (double)30, new ArrayList<PerturbationJPA>(),"a faire",new ArrayList() {{ add(gareD);add(gareC); add(gareB); add(gareA);}},new ArrayList() {{ add(quaiD3);add(quaiC2); add(quaiB1); add(quaiA1);}}) ;
			listData.add(voyage2);
			VoyageJPA voyage3 = new VoyageJPAAvecRes(date, 0,0,85,85, (double)200, trajet2,"aller",train3, new ArrayList() {{ add(passage6); add(passage7);}} , (double)250, new ArrayList<PerturbationJPA>(),  "a faire",new ArrayList() {{ add(gareE); add(gareF);add(gareB);}},new ArrayList() {{ add(quaiE1);add(quaiF2);add(quaiB2);}},listReservation, new ArrayList() {{add(voyage1);}}) ;
			listData.add(voyage3);
			VoyageJPA voyage4 = new VoyageJPASansRes(date, 105,105,117,120, (double)70, trajet1,"aller",train2, new ArrayList() {{ add(passage8); add(passage9); add(passage10);}} , (double)30, new ArrayList<PerturbationJPA>(), "a faire",new ArrayList() {{ add(gareD); add(gareC); add(gareB);add(gareA);}},new ArrayList() {{ add(quaiD3); add(quaiC2); add(quaiB1);add(quaiA1);}}) ;
			listData.add(voyage4);
			
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
			
		}catch (Exception e) {
			this.em.getTransaction().rollback();
			throw new RuntimeException("failed to initiate data", e);
		}	

	}
	
	
	@Test
	public void testPaymentDAO() {


	}


}
