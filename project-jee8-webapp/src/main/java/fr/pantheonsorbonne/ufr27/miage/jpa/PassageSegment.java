package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;

@Entity
public class PassageSegment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
    int heureDepart;
	
	int heureArrivee;
	
	boolean isPassageAjoute;
	
	@ManyToOne
	SegmentJPA segment;
	
	public boolean isPassageAjoute() {
		return isPassageAjoute;
	}

	public void setIsPassageAjoute(boolean isPassageAjoute) {
		this.isPassageAjoute = isPassageAjoute;
	}

	boolean isPassageSupprimee;
	
	public boolean isPassageSupprimee() {
		return isPassageSupprimee;
	}

	public void setIsPassageSupprimee(boolean isPassageSupprimee) {
		this.isPassageSupprimee = isPassageSupprimee;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SegmentJPA getSegment() {
		return segment;
	}

	public void setSegment(SegmentJPA segment) {
		this.segment = segment;
	}

	public int getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(int heureDepart) {
		this.heureDepart = heureDepart;
	}

	public int getHeureArrivee() {
		return heureArrivee;
	}

	public void setHeureArrivee(int heureArrivee) {
		this.heureArrivee = heureArrivee;
	}

	
}
