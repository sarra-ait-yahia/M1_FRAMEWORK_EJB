package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	Passager passager;
	

	public Reservation(Passager passager) {
		super();
		this.passager = passager;
	}

	public Reservation() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Passager getPassager() {
		return passager;
	}

	public void setPassager(Passager passager) {
		this.passager = passager;
	}



	
}
