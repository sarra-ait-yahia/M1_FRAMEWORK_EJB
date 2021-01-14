package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Id;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class TrajetJPA {
	@Id
	String id;
	
	@OneToMany
	List<SegmentJPA> segments;

	
	public TrajetJPA() {
	}

	public TrajetJPA(String id, List<SegmentJPA> segments) {
		super();
		this.id = id;
		this.segments = segments;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<SegmentJPA> getSegment() {
		return segments;
	}

	public void setSegment(List<SegmentJPA> segments) {
		this.segments = segments;
	}

	
	
}
