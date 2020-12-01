package fr.pantheonsorbonne.ufr27.miage.conf;

import java.util.function.Supplier;

import org.glassfish.hk2.api.Factory;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMFFactory implements Supplier<EntityManagerFactory> {
    private final EntityManagerFactory emf;
    public EMFFactory (){
        emf = Persistence.createEntityManagerFactory("default");
    }
    
	@Override
	public EntityManagerFactory get() {
		return emf;
	}
	
    
}