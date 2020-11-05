package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class VIPCustomer extends Customer {

	public Date getVipStatusDeaadline() {
		return vipStatusDeaadline;
	}

	public void setVipStatusDeaadline(Date vipStatusDeaadline) {
		this.vipStatusDeaadline = vipStatusDeaadline;
	}

	Date vipStatusDeaadline;

}
