package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

@Entity
@NamedQueries(

		value = {
				@NamedQuery(name = "getPaymentForCustomer", query = "SELECT p FROM Payment p JOIN p.invoices i JOIN i.contract c JOIN c.customer customer WHERE customer.id=:id") })

public class Payment {

	public Set<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	double amount;

	boolean isValidated;

	@OneToMany()
	Set<Invoice> invoices = new HashSet<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

}
