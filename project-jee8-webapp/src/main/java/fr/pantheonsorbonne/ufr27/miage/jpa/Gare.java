package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Gare {

	@Id
	String nom;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Gare() {
	}
	
	public Gare(String nom) {
		super();
		this.nom = nom;
	}
}
