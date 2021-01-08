package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

@Entity
public class TrajetJPA {
	@Id
	int id;
	
	@ManyToOne
	SegmentJPA segment;

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

	
	
}
