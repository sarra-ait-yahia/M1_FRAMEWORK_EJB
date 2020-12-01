package fr.pantheonsorbonne.ufr27.miage.resource;

import java.time.ZoneId;
import java.util.Collection;

import jakarta.inject.Inject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import fr.pantheonsorbonne.ufr27.miage.dao.InvoiceDAO;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.jpa.Invoice;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.InvoiceWrapper;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.ObjectFactory;

@Path("/invoice")
public class InvoiceEndpoint {

	@Inject
	InvoiceDAO invoiceDAO;

	@GET
	@Path("/{userId}")
	@Produces(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getInvoice(@PathParam("userId") int userId) throws NoSuchUserException {

		Collection<Invoice> invoices = invoiceDAO.getUnpaiedInvoices(userId);
		InvoiceWrapper wrapper = new ObjectFactory().createInvoiceWrapper();
		for (Invoice invoiceEntity : invoices) {

			fr.pantheonsorbonne.ufr27.miage.model.jaxb.Invoice invoice = new ObjectFactory().createInvoice();
			invoice.setContractId(invoiceEntity.getContract().getId());

			invoice.setDate(invoiceEntity.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			invoice.setPaid(invoiceEntity.isPayed());
			wrapper.getInvoices().add(invoice);
		}
		return Response.ok(wrapper).build();

	}

}
