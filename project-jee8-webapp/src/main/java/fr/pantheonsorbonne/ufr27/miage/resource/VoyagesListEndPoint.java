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

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;
import fr.pantheonsorbonne.ufr27.miage.service.GestionPerturbationService;
import fr.pantheonsorbonne.ufr27.miage.service.InformationVoyageService;
import fr.pantheonsorbonne.ufr27.miage.service.NotifyInfoGareService;

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


@Path("listVoyageDuTrain/")
public class VoyagesListEndPoint {

	@Inject
	InformationVoyageService serviceInfoVoyage;
	
	@Inject
	GestionPerturbationService serviceGestionPerturbation; 
	
	@Inject 
	NotifyInfoGareService notifyInfoGareService;
	
	@Produces(value = { MediaType.APPLICATION_XML })
	@Path("/{trainId}/{time}")
	@GET
	public Response getListVoyage(@PathParam("trainId") String trainId,@PathParam("time") int time ) {
		System.out.println("helllo");
		if (serviceInfoVoyage.getListVoyage(trainId,time) != null) {
			return Response.ok(serviceInfoVoyage.getListVoyage(trainId,time)).build();
		}
		return Response.noContent().build();
	}

	@Produces(value = { MediaType.APPLICATION_XML})
	@Consumes(value = { MediaType.APPLICATION_XML})
	@Path("/{trainId/{time}")
	@POST
	public Response gererPerturbation(@PathParam("trainId") String trainId,@PathParam("time") int time, Voyage voyage) throws URISyntaxException {
		serviceInfoVoyage.addPerturbationToBDD(voyage.getTrain().getPerturbation(), voyage.getIdVoyage());
		serviceGestionPerturbation.gererPerturbation(voyage,voyage.getIdVoyage(), time);
		notifyInfoGareService.sendMessagePerturbationEtVoyages(voyage,time);
		return Response.ok().build();
	}
}

