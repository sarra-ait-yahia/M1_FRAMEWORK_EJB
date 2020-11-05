package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.Entity;

@Entity
public class ProCustomer extends Customer {

	public Address getProAddress() {
		return proAddress;
	}

	public void setProAddress(Address proAddress) {
		this.proAddress = proAddress;
	}

	Address proAddress;

}
