package fr.pantheonsorbonne.ufr27.miage;

public class TrainClientApp {

	public static void main(String args[]) throws InterruptedException {

		Train t1 = new Train(1);
		Train t2 = new Train(2);
		Train t3 = new Train(3);

		t1.getPerturbations().add(new Incident(3));
		
		t2.getPerturbations().add(new Retard(10, 10));
		t2.getPerturbations().add(new Retard(15, 10));
		
		
		new Thread(t1).start();
		new Thread(t2).start();
		new Thread(t3).start();
		
		Thread.currentThread().join();

	}

}
