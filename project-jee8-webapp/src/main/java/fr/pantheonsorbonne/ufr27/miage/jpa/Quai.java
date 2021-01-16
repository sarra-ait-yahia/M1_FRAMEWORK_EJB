package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Quai {

	@Id
	int numero;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Quai() {
	}
	public Quai(int numero) {
		super();
		this.numero = numero;
	}


}
