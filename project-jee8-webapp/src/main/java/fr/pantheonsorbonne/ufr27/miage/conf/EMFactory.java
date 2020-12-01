package fr.pantheonsorbonne.ufr27.miage.conf;

import java.io.Closeable;
import java.util.function.Supplier;


import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.server.CloseableService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EMFactory implements Supplier<EntityManager> {
	private final EntityManagerFactory emf;
	private final CloseableService closeService;

	@Inject
	public EMFactory(EntityManagerFactory emf, CloseableService closeService) {
		this.emf = emf;
		this.closeService = closeService;
	}

	public EntityManager get() {
		final EntityManager em = emf.createEntityManager();
		return em;
	}

	

}