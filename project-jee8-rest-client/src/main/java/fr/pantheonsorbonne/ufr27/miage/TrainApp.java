package fr.pantheonsorbonne.ufr27.miage;

import java.net.URI;
import java.time.Month;

import fr.pantheonsorbonne.ufr27.miage.classe.client.Incident;
import fr.pantheonsorbonne.ufr27.miage.classe.client.Retard;
import fr.pantheonsorbonne.ufr27.miage.classe.client.TrainClient;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Train;

/**
 * Hello world!
 *
 */
public class TrainApp {

	public static void main(String[] args) throws InterruptedException {
		TrainClient train1 = new TrainClient("trainA");
		TrainClient train2 = new TrainClient("trainB");
		TrainClient train3 = new TrainClient("trainC");
		TrainClient train4 = new TrainClient("trainD");
		
		train1.getPerturbations().add(new Incident("accident de personne", 35 , 150 ));
		train3.getPerturbations().add(new Retard(35,20));
		train4.getPerturbations().add(new Incident("incident naturel (vent)",180, 20));
		
		new Thread(train1).start();
		new Thread(train2).start();
		new Thread(train3).start();
		new Thread(train4).start();
		
		Thread.currentThread().join();
	}
}
