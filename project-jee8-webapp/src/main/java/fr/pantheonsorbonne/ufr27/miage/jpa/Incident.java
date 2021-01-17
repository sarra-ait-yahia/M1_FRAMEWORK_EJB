package fr.pantheonsorbonne.ufr27.miage.jpa;


import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name="Incident")
public class Incident extends PerturbationJPA{
	String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Incident() {
		
	}
	public Incident(int heure, int duree, String type) {
		super(heure, duree);
		this.type = type;
	}

}
