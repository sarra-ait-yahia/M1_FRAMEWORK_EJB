package fr.pantheonsorbonne.ufr27.miage;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.jms.JMSException;

import fr.pantheonsorbonne.ufr27.miage.jms.InfoGareBean;
import fr.pantheonsorbonne.ufr27.miage.jms.classe.gare.Gare;

public class InfoGareApp {

	public static void main(String[] args) throws InterruptedException, JMSException {
		
		
		String [] gareNames =  {"A","B","C","D","E","F","G","H"};
		
		for(String name: gareNames) {
			
			SeContainerInitializer initializer = SeContainerInitializer.newInstance();

			try (SeContainer container = initializer.disableDiscovery().addPackages(true, InfoGareBean.class)
					.initialize()) {

				InfoGareBean processor = container.select(InfoGareBean.class).get();
				
				Gare gare = new Gare(name,processor);
				processor.setGare(gare);
				
				new Thread(gare).start();

			}
			
			
		}
		Thread.currentThread().join();	
		
	}
}
