package fr.pantheonsorbonne.ufr27.miage.jpa.jaxb.mapping;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.ufr27.miage.jpa.VoyageJPA;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.VoyageDuJour;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Train;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;

public class JaxbJpaMapper {
	
	public VoyageDuJour voyageDuJourFromJpa(List<VoyageJPA> voyages) {
		
		ObjectFactory factory = new ObjectFactory();
		VoyageDuJour voyageDuJour = factory.createVoyageDuJour();
		List<Voyage> voyagesJaxb = new ArrayList<Voyage>();
		Train t = factory.createTrain();
		if(voyages.size() != 0) {
			t.setIdTrain(voyages.get(0).getTrain().getId());
			t.setType(voyages.get(0).getTrain().getType());
			
			for(VoyageJPA v : voyages) {
				Voyage vJaxb = factory.createVoyage();
				vJaxb.setIdVoyage(v.getId());
				vJaxb.setDistance(Double.toString(v.getDistance()));
				vJaxb.setHeureArrivee(v.getHeureArrivee());
				vJaxb.setHeureDepart(v.getHeureDepart());
				vJaxb.setIsVoyageSupprime(v.isVoyageSupprime());
				vJaxb.setTrain(t);
				vJaxb.setVitesse(Double.toString(v.getVitesse()));
				//ajout de trajet , passage et segment
				
				voyagesJaxb.add(vJaxb);
			}
		}
		voyageDuJour.setVoyages(voyagesJaxb);
		return voyageDuJour;
		
	}
	

}
