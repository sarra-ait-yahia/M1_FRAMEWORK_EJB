package fr.pantheonsorbonne.ufr27.miage;

public class Incident extends PerturbationTrain{
    protected String type;
    protected double modificationVitesse;
	
	
	public Incident(String type,int heure,int duree,double modificationVitesse) {
		super(heure, duree);
		this.type = type;
		this.modificationVitesse = modificationVitesse;
	} 
	


	
}
