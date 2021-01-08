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
import fr.pantheonsorbonne.ufr27.miage.service.GymService;
import fr.pantheonsorbonne.ufr27.miage.service.UpdateListVoyageService;

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
	UpdateListVoyageService service;
	
	@GET
	@Path("/{trainId}")
	@Produces(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getListVoyage(@PathParam("trainId") int trainId) {
		try {
			return Response.ok(service.getListVoyage(trainId)).build();
		} catch (Exception e) {
			throw new WebApplicationException(404);
		}
	}

}

