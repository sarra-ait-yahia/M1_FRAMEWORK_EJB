package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import lombok.Getter;
import lombok.Setter;

@Entity
public class SegmentJPA  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String stationDepart;
	String stationArrivee;
	double distance;
	
	public SegmentJPA() {
	}
	public SegmentJPA(String stationDepart, String stationArrivee, double distance) {
		super();
		this.stationDepart = stationDepart;
		this.stationArrivee = stationArrivee;
		this.distance = distance;
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	

	public String getStationDepart() {
		return stationDepart;
	}
	public void setStationDepart(String stationDepart) {
		this.stationDepart = stationDepart;
	}
	public String getStationArrivee() {
		return stationArrivee;
	}
	public void setStationArrivee(String stationArrivee) {
		stationArrivee = stationArrivee;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
}
