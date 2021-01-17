package fr.pantheonsorbonne.ufr27.miage.jms.classe.gare;

public class AffichageVoyagedepart extends AffichageVoyage{
	
	String destination;

	public AffichageVoyagedepart(String idTrain, int heurePrevu, int heureModifie, String statut, String quai,String destination) {
		super(idTrain, heurePrevu, heureModifie, statut, quai);
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "Ecran Départ: \n"+"Train: "+this.idTrain+", à destination de: "+this.destination+" , heure prévue de départ: "+this.heurePrevu+" , heure réelle de départ: "+this.heureModifie+" , "+this.statut+" , N°quai: "+this.quai;
		
	}
}
