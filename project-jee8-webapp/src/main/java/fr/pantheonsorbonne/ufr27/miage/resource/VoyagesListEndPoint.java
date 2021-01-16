package fr.pantheonsorbonne.ufr27.miage.resource;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.FreeTrialPlan;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;
import fr.pantheonsorbonne.ufr27.miage.service.GestionPerturbationService;
import fr.pantheonsorbonne.ufr27.miage.service.GymService;
import fr.pantheonsorbonne.ufr27.miage.service.InformationVoyageService;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import fr.pantheonsorbonne.ufr27.miage.exception.NoSuchUserException;


@Path("/listVoyageDuTrain")
public class VoyagesListEndPoint {

	@Inject
	InformationVoyageService serviceInfoVoyage;
	@Inject
	GestionPerturbationService serviceGestionPerturbation; 
	
	@GET
	@Path("/{trainId}/{time}")
	@Produces(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getListVoyage(@PathParam("trainId") String trainId,@PathParam("time") int time ) {
		try {
			return Response.ok(serviceInfoVoyage.getListVoyage(trainId,time)).build();
		} catch (Exception e) {
			throw new WebApplicationException(404);
		}
	}

	/*@Produces(value = { MediaType.APPLICATION_XML})
	@Consumes(value = { MediaType.APPLICATION_XML})
	@Path("/{trainId/{time}}")
	@POST
	public Response gererPerturbation(@PathParam("trainId") String trainId,@PathParam("time") int time, Voyage voyage) throws URISyntaxException {
		if(voyage.getTrain().getPerturbation() != null) {
			serviceInfoVoyage.addPerturbationToBDD();
			//appeler service GestionPerturbation qui changera tt les voyage
			// si changer appeler notyInfoGare
		}

		return Response.created(new URI("/user/" + customerId)).build();

	}
	*/
}
