package fr.pantheonsorbonne.ufr27.miage.jms.classe.gare;

public class AffichageVoyageArrivee extends AffichageVoyage {
	String arrivee;
	
	public AffichageVoyageArrivee(String idTrain, int heurePrevu, int heureModifie, String statut, String quai, String arrivee) {
		super(idTrain, heurePrevu, heureModifie, statut, quai);
		this.arrivee = arrivee;
	}

	
	
	@Override
	public String toString() {
		return "Ecran Arrivee: \n"+"Train: "+this.idTrain+" ,arrivé de: "+this.arrivee+" , heure prévue d'arrivé: "+this.heurePrevu+" , heure réelle d'arrivé: "+this.heureModifie+" , "+this.statut+" , N°quai: "+this.quai;
		
	}

}
