package fr.pantheonsorbonne.ufr27.miage.jms.classe.gare;

public class AffichageVoyageArrivee extends AffichageVoyage {
	String arrivee;
	
	public AffichageVoyageArrivee(String idTrain, int heurePrevu, int heureModifie, String statut, String quai, String arrivee) {
		super(idTrain, heurePrevu, heureModifie, statut, quai);
		this.arrivee = arrivee;
	}

	
	
	@Override
	public String toString() {
		String message= "";
		String statut = this.statut == "à faire" ? "":this.statut;
		if(this.statut =="supprimé") {
			message = "Ecran Arrivee: \n"+"Train: "+this.idTrain+" ,arrivé de: "+this.arrivee+" , heure d'arrivé: "+this.heureModifie+" , "+statut;
		}else {
		 message ="Ecran Arrivee: \n"+"Train: "+this.idTrain+" ,arrivé de: "+this.arrivee+" , heure prévue d'arrivé: "+this.heurePrevu+" , heure réelle d'arrivé: "+this.heureModifie+" , "+statut+" , N°quai: "+this.quai;
		}
		return message;
	}

}
