package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IncidentImpact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	String impact;

	public String getImpact() {
		return impact;
	}
	public void setImpact(String impact) {
		this.impact = impact;
	}
	
	
	public IncidentImpact() {
	}
	
	public IncidentImpact(String type, String impact) {
		super();
		this.type = type;
		this.impact = impact;
	}
	

}
