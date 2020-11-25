package fr.pantheonsorbonne.ufr27.miage;

public abstract class Perturbation {

	private int occurenceTime;
	private int delay;

	public int getDelay() {
		return delay;
	}

	public Perturbation(int occurenceTime,int delay) {
		this.occurenceTime = occurenceTime;
		this.delay=delay;
		
	}

	public int getOccurenceTime() {
		return this.occurenceTime;
	}

}
