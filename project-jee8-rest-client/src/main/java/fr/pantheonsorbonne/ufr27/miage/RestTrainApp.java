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
		ObjectFactory factory = new ObjectFactory();
		TrainClient train1 = new TrainClient("A");
		TrainClient train2 = new TrainClient("B");
		TrainClient train3 = new TrainClient("C");
		
		train1.getPerturbations().add(factory.createPerturbation("Retard",10,(double)0, 10 ));
		train2.getPerturbations().add(factory.createPerturbation("Malaise Voyagaeur",30,(double)0, 120 ));
		train3.getPerturbations().add(factory.createPerturbation("Vent",40,(double)-20, 0 ));
		
		new Thread(train1).start();
		new Thread(train2).start();
		new Thread(train3).start();
		
		Thread.currentThread().join();
	}
}
