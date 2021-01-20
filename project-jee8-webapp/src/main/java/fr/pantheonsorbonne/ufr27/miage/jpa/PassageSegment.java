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
    
    int heuredepartModifie;
	
	int heureArrivee;
	
	int heureArriveeModifie;
	
	double distanceParcourue;
	
	
	public double getDistanceParcourue() {
		return distanceParcourue;
	}

	public void setDistanceParcourue(double distanceParcourue) {
		this.distanceParcourue = distanceParcourue;
	}

	public int getHeuredepartModifie() {
		return heuredepartModifie;
	}

	public void setHeuredepartModifie(int heuredepartModifie) {
		this.heuredepartModifie = heuredepartModifie;
	}

	public int getHeureArriveeModifie() {
		return heureArriveeModifie;
	}

	public void setHeureArriveeModifie(int heureArriveeModifie) {
		this.heureArriveeModifie = heureArriveeModifie;
	}

	public void setPassageAjoute(boolean isPassageAjoute) {
		this.isPassageAjoute = isPassageAjoute;
	}

	boolean isPassageAjoute;
	
	@ManyToMany
	List<SegmentJPA> segments;
	
	
	public PassageSegment() {
	}

	public PassageSegment(int heureDepart,int heuredepartModifie , int heureArrivee, int heureArriveeModifie,boolean isPassageAjoute, List<SegmentJPA> segments, double distanceParcourue) {
		super();
		this.heureDepart = heureDepart;
		this.heuredepartModifie = heuredepartModifie;
		this.heureArrivee = heureArrivee;
		this.heureArriveeModifie = heureArriveeModifie;
		this.isPassageAjoute = isPassageAjoute;
		this.segments = segments;
		this.distanceParcourue = distanceParcourue;
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
