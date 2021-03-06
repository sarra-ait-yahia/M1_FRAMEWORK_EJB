package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

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
