package fr.pantheonsorbonne.ufr27.miage.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;

import fr.pantheonsorbonne.ufr27.miage.jpa.Gare;
import fr.pantheonsorbonne.ufr27.miage.jpa.PassageSegment;
import fr.pantheonsorbonne.ufr27.miage.jpa.PerturbationJPA;
import fr.pantheonsorbonne.ufr27.miage.jpa.Quai;
import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@ManagedBean
public class VoyageDAO {
	@Inject
	EntityManager em;
	
	
	public List<VoyageJPA> getVoyagesDuJour(String idTrain, int time){
		em.getTransaction().begin();
		Query query = em.createQuery("SELECT v FROM VoyageJPA v WHERE v.train.id =:idTrain AND v.dateVoyage =:dateToday AND v.heureDepartModifie >=:time OR (v.heureDepartModifie <:time AND v.heureArriveeModifie >:time) ORDER BY v.heureDepartModifie ", VoyageJPA.class);
		query.setParameter("idTrain", idTrain);
		query.setParameter("dateToday", LocalDate.of(2021, 01, 23));
		query.setParameter("time", time);
		List<VoyageJPA> listVoyage= query.getResultList();
		em.getTransaction().commit();
		return listVoyage;
	}

	public void ajouterPerturbation(PerturbationJPA perturbation, int idVoyage){
		em.getTransaction().begin();
		VoyageJPA voyage = em.find(VoyageJPA.class,idVoyage);
		List<PerturbationJPA> listPerturbation = new ArrayList<PerturbationJPA>();
		listPerturbation.add(perturbation);
		voyage.setPerturbations(listPerturbation);
		em.getTransaction().commit();
		
	}

	public void changerHeureVoyage(List<VoyageJPA> voyageAChanger, int retard, boolean changeHeureDebut) {
		em.getTransaction().begin();
		int i=0;
		for(VoyageJPA voyage: voyageAChanger) {
			VoyageJPA v = em.find(VoyageJPA.class, voyage.getId());
			if(!changeHeureDebut && i == 0) {
				v.setHeureArriveeModifie(voyage.getHeureArriveeModifie()+retard);
			}
			else {
				v.setHeureArriveeModifie(voyage.getHeureArriveeModifie()+retard);
				v.setHeureDepartModifie(voyage.getHeureDepartModifie()+retard);
			}
			v.setStatut("retardé");
			i++;
		}
		em.getTransaction().commit();
		
	}

	public void delete(List<VoyageJPA> voyagesASupprimer) {
		
		em.getTransaction().begin();
		for(VoyageJPA voyage: voyagesASupprimer) {
			VoyageJPA v = em.find(VoyageJPA.class, voyage.getId());
			v.setStatut("supprimé");
		}
		em.getTransaction().commit();
		
	}

	public List<VoyageJPA> getVoyageDumemeTrajetEtDirection(String idTrajet, String direction, int time, int idVoyage) {
		em.getTransaction().begin();
		Query query = em.createQuery("SELECT v FROM VoyageJPA v WHERE v.id !=:idVoyage AND v.trajet.id =:idTrajet AND v.direction =:direction AND v.dateVoyage =:dateToday AND v.statut !=:statut AND v.heureArriveeModifie <:time ORDER BY v.heureDepartModifie ", VoyageJPA.class);
		query.setParameter("idVoyage", idVoyage);
		query.setParameter("idTrajet", idTrajet);
		query.setParameter("direction", direction);
		query.setParameter("statut", "supprimé");
		query.setParameter("dateToday", LocalDate.of(2021, 01, 23));
		query.setParameter("time", time);
		List<VoyageJPA> listVoyage= query.getResultList();
		em.getTransaction().commit();
		return listVoyage;
	}

	public List<VoyageJPA> getVoyagesSuivants(int heureDepartModifie, String idTrain) {
		em.getTransaction().begin();
		Query query = em.createQuery("SELECT v FROM VoyageJPA v WHERE v.train.id =:idTrain AND v.dateVoyage =:dateToday AND v.statut !=:statut  AND v.heureDepartModifie >=:time ORDER BY v.heureDepartModifie ", VoyageJPA.class);
		query.setParameter("idVoyage", idTrain);
		query.setParameter("statut", "supprimé");
		query.setParameter("dateToday", LocalDate.of(2021, 01, 23));
		query.setParameter("time", heureDepartModifie);
		List<VoyageJPA> listVoyage= query.getResultList();
		em.getTransaction().commit();
		return listVoyage;
		
	}

	public void addGareToVoyage(VoyageJPA voyageJpa, Gare gare) {
		em.getTransaction().begin();
		Gare newGare = em.find(Gare.class, gare.getNom());
		List<Gare> gares = voyageJpa.getGaresAdesservir();
		gares.add(newGare);
		Quai newQuai = em.find(Quai.class, gare.getNom()+"5");
		List<Quai> quais = voyageJpa.getQuaiAdesservir();
		quais.add(newQuai);
		VoyageJPA v = em.find(VoyageJPA.class, voyageJpa.getId());
		v.setGaresAdesservir(gares);
		v.setQuaiAdesservir(quais);
		em.getTransaction().commit();	
	}
}
