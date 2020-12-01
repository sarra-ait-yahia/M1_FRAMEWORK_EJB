package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Invoice {
	public void setPayed(boolean isPayed) {
		this.isPayed = isPayed;
	}

	@Override
	public String toString() {
		return "Invoice [id=" + id + ", date=" + date + ", contract=" + contract + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	@Temporal(TemporalType.TIMESTAMP)
	Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
	Contract contract;

	private boolean isPayed;

	public boolean isPayed() {
		return this.isPayed;
	}
}
