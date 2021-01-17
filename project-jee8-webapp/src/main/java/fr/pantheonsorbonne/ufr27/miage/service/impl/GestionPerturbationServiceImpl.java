package fr.pantheonsorbonne.ufr27.miage.service.impl;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Perturbation;
import fr.pantheonsorbonne.ufr27.miage.service.GestionPerturbationService;

public class GestionPerturbationServiceImpl implements GestionPerturbationService{

	@Override
	public void gererPerturbation(Perturbation perturbation, int idVoyage, int time) {
		
		//sil s'agit dun voyage avec Resservation 
		// je vérifie que le nombre de réservation > 50
		// si c'est le cas 
		//je ramène le voyage TER associé au TGV 
		//je cherche la gare commune en utilisant les list gare 
		//au niveau du voyage associé je cherche le passage ou stationB = la gare en question 
		//si passage.getHeuredepart < heureArrivee du TGV à cette gare + retard+ 10 
		//  le voyage du ter , on modifie lheure de départ + retard et on modifie tt les heures 
		// de passage ainsi que les voyages suivants du train
	
		
		// je ramène tout les voyages
		//les les ordonnes
		// je cherche l'indicevoyage en cours
		
		//Pour le voyages en question , je ramènes les passages
		// organiser les passages
		//boucler sur les passages et trouver l'indice de celui ou  heuredebut<time<heurefin
		//pour le passage(indice) mettre heurearrive+perturbation.getduree
		//for(i=indice+1, i<nombre de passage, i++) on rajoute pour chaque heuredepart et heurearrivee + perturbation.getduree 
		//update BDD
		
		//for(i= indiceVoyage, i < nombre voyage , i++)
		// si voyage nest pas déja supprimé et voyage précédent. get heure d'arrivée-heure de départ du voyage actuel < 10 
		// je garde le voyage je modifie tt les heures du voygae et passage + (voyage précédent. get heure d'arrivée-heure de départ du voyage actuel)+5
		//deuxième boucle pour modifier les voyage
		// break;
		
		//si voyage nest pas déja supprimé et voyage précédent. get heure d'arrivée-heure de départ du voyage suivant > 10
		// je supprime le voygage, mettre le statut à "supprime" et celui d'après 
		
		
	
		// si retard >= 120 
		//cherche le passage actuel 
		//liste des gares prochain à desservir 
		 
		//je ramène voyage de meme trajet et meme direction et dont heureArrivé < time 
		// je les organise selon le temps 
		// pour chaque train , je regarde sil dessert les gares, 
		 // pour la gare qu'il dessert , je regarde si s'il est déja passé
		// sil n'est pas encore passé gareOK
		// sil l'est déja passé gareKO
		//Pour les gare qu'il dessert pas 
		// je regarde sa position actuel par rapport à la gare qu'il dessert pas 
		//sil est après gareKo
		//S'il est avant, je crée un nouveau passage 
		// et je met à jour tout les voyages de ce train 
		
		
		
	}

}
