package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Quai {

	@Id
	String idQuai;

	

	public String getIdQuai() {
		return idQuai;
	}



	public void setIdQuai(String idQuai) {
		this.idQuai = idQuai;
	}



	public Quai() {
	}



	public Quai(String idQuai) {
		super();
		this.idQuai = idQuai;
	}
	
   

}
