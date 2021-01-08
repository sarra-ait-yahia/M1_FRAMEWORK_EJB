package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

@Entity
public class SegmentJPA {
	
	@Id
	int id;
	@GeneratedValue(strategy = GenerationType.AUTO)
	String stationDepart;
	String StationArrivee;
	double distance;
	
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
		return StationArrivee;
	}
	public void setStationArrivee(String stationArrivee) {
		StationArrivee = stationArrivee;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
}
