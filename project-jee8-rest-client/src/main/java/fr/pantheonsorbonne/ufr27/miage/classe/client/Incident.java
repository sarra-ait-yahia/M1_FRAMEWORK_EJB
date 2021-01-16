package fr.pantheonsorbonne.ufr27.miage.classe.client;

public class Incident extends PerturbationTrain{
	
    public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	protected String type;
	
	
	public Incident(String type,int heure,int duree) {
		super(heure, duree);
		this.type = type;
	} 
	


	
}
