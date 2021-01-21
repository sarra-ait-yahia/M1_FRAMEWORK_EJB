package fr.pantheonsorbonne.ufr27.miage.jms.classe.gare;

import java.util.List;

import javax.jms.JMSException;

import fr.pantheonsorbonne.ufr27.miage.jms.InfoGareBean;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;

public class Gare implements Runnable{
	
	InfoGareBean processor;
	
	String name;

	int time; 
	
	private volatile boolean running = true;
	
	
	
	String messageIncident;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
	
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getMessageIncident() {
		return messageIncident;
	}

	public void setMessageIncident(String messageIncident) {
		this.messageIncident = messageIncident;
	}

	public Gare(String name,InfoGareBean processor) {
		super();
		this.name = name;
		this.processor = processor;
	}

	@Override
	public void run() {
		while (this.running) {
			try {
				this.processor.consume();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}

	public void terminate() {
        this.running = false;
    }
	
	public void afficherVoyage(List<AffichageVoyage> affichageVoyages) {
		for(AffichageVoyage affichage: affichageVoyages) {
			System.out.println(affichage.toString());
		}
	}

	public void afficherMessagePerturbation(String IdTrain, String trajet,int time, String typePerturbation, String impactPerturbation,
			String RetardArret) {
		System.out.println("Affichage Perturbation: \n Le train : "+IdTrain+" qui roule sur le trajet "+trajet+" a eu une perturbation de type: "+typePerturbation+
				" qui a causé un "+impactPerturbation+" de "+RetardArret+"à "+time+" minutes.");
		
	}

}
