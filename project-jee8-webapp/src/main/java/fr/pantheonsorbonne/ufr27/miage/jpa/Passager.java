package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

@Entity
public class Passager {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	String nom;

	public Passager( String nom) {
		super();
		this.nom = nom;
	}

	public Passager() {
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
