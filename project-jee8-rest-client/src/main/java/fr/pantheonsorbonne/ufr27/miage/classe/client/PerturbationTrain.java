package fr.pantheonsorbonne.ufr27.miage.classe.client;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlElement;

public class PerturbationTrain {

    protected int heure;
    protected int duree;
    
    public PerturbationTrain(int heure, int duree) {
    	
    	this.heure = heure;
    	this.duree = duree;
	}
    
	public int getHeure() {
		return heure;
	}
	public void setHeure(int heure) {
		this.heure = heure;
	}
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
		this.duree = duree;
	}
}
