package fr.pantheonsorbonne.ufr27.miage.jms.classe.gare;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Passage;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Segment;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;

public class JaxbToAffichageMapper {
	
	
	public List<AffichageVoyage> createAffichageVoyage(Voyage voyage, Gare gare) {
		List<AffichageVoyage> affichageVoyages = new ArrayList<AffichageVoyage>();
		for(Passage p: voyage.getPassages()) {
			List<Segment> segments = p.getSegments();
			if(segments.size() == 1) {
				//déterminer la destination du train ou la gare d'ou il vient
				char[] charList = voyage.getTrajet().getIdTrajet().toCharArray();
				String destination = voyage.getDirection() == "aller" ? Character.toString(charList[1]): Character.toString(charList[0]);
				String arrivee = voyage.getDirection() == "retour" ? Character.toString(charList[1]): Character.toString(charList[0]);
				
				//extraire le numero de quai
				int i=0;
				int quai =0;
				for(fr.pantheonsorbonne.ufr27.miage.model.jaxb.Gare g: voyage.getGares()) {
					if(g.getNom() == gare.getName()) {
						quai = voyage.getQuais().get(i).getNumero();
						break;
					}
				  i++;
				}
				
				if(segments.get(0).getStationA() == gare.getName() && p.getHeureDepartModifie()>= gare.getTime() ) {
					AffichageVoyage affichageDepart = new AffichageVoyagedepart(voyage.getTrain().getIdTrain(),p.getHeureDepart(),p.getHeureDepartModifie(),voyage.getStatut(),quai, destination);
					affichageVoyages.add(affichageDepart);
				}
			    if(segments.get(0).getStationB() == gare.getName() && p.getHeureArriveeModifie()>= gare.getTime()) {
			    	AffichageVoyage affichageArrivee = new AffichageVoyageArrivee(voyage.getTrain().getIdTrain(),p.getHeureArrivee(),p.getHeureArriveeModifie(),voyage.getStatut(),quai, arrivee);
			    	affichageVoyages.add(affichageArrivee);
				}
			}
		}
		
		return affichageVoyages;
	}
}
