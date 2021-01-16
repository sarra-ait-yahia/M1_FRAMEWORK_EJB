package fr.pantheonsorbonne.ufr27.miage.jms.classe.gare;

public class AffichageVoyage {

	String idTrain;
	int heurePrevu;
	int heureModifie;
	String statut;
	int quai;
	public String getIdTrain() {
		return idTrain;
	}
	public void setIdTrain(String idTrain) {
		this.idTrain = idTrain;
	}
	public int getHeurePrevu() {
		return heurePrevu;
	}
	public void setHeurePrevu(int heurePrevu) {
		this.heurePrevu = heurePrevu;
	}
	public int getHeureModifie() {
		return heureModifie;
	}
	public void setHeureModifie(int heureModifie) {
		this.heureModifie = heureModifie;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public int getQuai() {
		return quai;
	}
	public void setQuai(int quai) {
		this.quai = quai;
	}
	
	public AffichageVoyage(String idTrain, int heurePrevu, int heureModifie, String statut, int quai) {
		super();
		this.idTrain = idTrain;
		this.heurePrevu = heurePrevu;
		this.heureModifie = heureModifie;
		this.statut = statut;
		this.quai = quai;
	}
	

}
