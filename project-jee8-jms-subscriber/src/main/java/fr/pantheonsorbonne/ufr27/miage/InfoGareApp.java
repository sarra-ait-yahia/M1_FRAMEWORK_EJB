package fr.pantheonsorbonne.ufr27.miage;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.jms.JMSException;

import fr.pantheonsorbonne.ufr27.miage.jms.MessageDispatcher;

public class InfoGareApp {

	public static void main(String[] args) throws InterruptedException, JMSException {
		
			
			SeContainerInitializer initializer = SeContainerInitializer.newInstance();

			try (SeContainer container = initializer.disableDiscovery().addPackages(true, MessageDispatcher.class)
					.initialize()) {

				MessageDispatcher processor = container.select(MessageDispatcher.class).get();
				
			
				while (true) {
					processor.consume();
					Thread.sleep(2000);
				}

			
			
		}
			
		
	}
}
