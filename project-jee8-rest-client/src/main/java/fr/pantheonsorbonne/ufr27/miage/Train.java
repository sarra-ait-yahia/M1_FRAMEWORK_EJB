package fr.pantheonsorbonne.ufr27.miage;

import java.util.ArrayList;
import java.util.Collection;

public class Train implements Runnable {

	@Override
	public String toString() {
		return "Train [id=" + id + ", delay=" + delay + "]";
	}

	Train(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Collection<Perturbation> getPerturbations() {
		return perturbations;
	}

	public void setPerturbations(Collection<Perturbation> perturbations) {
		this.perturbations = perturbations;
	}

	int id;
	int time = 0;
	int delay=0;

	Collection<Perturbation> perturbations = new ArrayList<Perturbation>();

	@Override
	public void run() {
		while (true) {

			//System.out.println(this.toString()+" get its info from infocentre");

			for (Perturbation p : perturbations) {
				if (p.getOccurenceTime() == time) {
					System.out.println(
							 this.toString() + "We should notify infocentre for perturbation " + p.toString());
					this.delay+=p.getDelay();
				}
			}

			time++;
			System.out.println(this.toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
