package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class PassageSegment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
    int heureDepart;
	
	int heureArrivee;
	
	boolean isPassageAjoute;
	
	@ManyToMany
	List<SegmentJPA> segments;
	
	
	public PassageSegment() {
	}

	public PassageSegment(int heureDepart, int heureArrivee, boolean isPassageAjoute, List<SegmentJPA> segments) {
		super();
		this.heureDepart = heureDepart;
		this.heureArrivee = heureArrivee;
		this.isPassageAjoute = isPassageAjoute;
		this.segments = segments;
	}

	public boolean isPassageAjoute() {
		return isPassageAjoute;
	}

	public void setIsPassageAjoute(boolean isPassageAjoute) {
		this.isPassageAjoute = isPassageAjoute;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<SegmentJPA> getSegments() {
		return segments;
	}

	public void setSegments(List<SegmentJPA>  segments) {
		this.segments = segments;
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
