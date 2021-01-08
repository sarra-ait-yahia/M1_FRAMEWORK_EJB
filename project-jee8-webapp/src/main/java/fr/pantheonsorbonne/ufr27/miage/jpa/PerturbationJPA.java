package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PerturbationJPA {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	int heure;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHeure() {
		return heure;
	}
	public void setHeure(int heure) {
		this.heure = heure;
	}
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
		this.duree = duree;
	}
	int duree;
	
	
}
