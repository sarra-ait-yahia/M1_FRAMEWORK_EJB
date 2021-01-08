package fr.pantheonsorbonne.ufr27.miage;

import java.net.URI;
import java.time.Month;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Train;

/**
 * Hello world!
 *
 */
public class RestTrainApp {

	public static void main(String[] args) throws InterruptedException {
		TrainClient train1 = new TrainClient("trainA");
		TrainClient train2 = new TrainClient("trainB");
		TrainClient train3 = new TrainClient("trainC");
		TrainClient train4 = new TrainClient("trainD");
		
		train1.getPerturbations().add(new Incident("Accident de personne", 35 , 150 , (double)0));
		train3.getPerturbations().add(new Retard(35,20));
		train4.getPerturbations().add(new Incident("Vent",180, 0,(double)-40 ));
		
		new Thread(train1).start();
		new Thread(train2).start();
		new Thread(train3).start();
		new Thread(train4).start();
		
		Thread.currentThread().join();
	}
}
