package fr.pantheonsorbonne.ufr27.miage.appel.rest;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;
import fr.pantheonsorbonne.ufr27.miage.model.jaxb.VoyageDuJour;

public class AccesRest {

	String idTrainToAccess;
	int time;
	
	
	public String getIdTrainToAccess() {
		return idTrainToAccess;
	}

	public void setIdTrainToAccess(String idTrainToAccess) {
		this.idTrainToAccess = idTrainToAccess;
	}

	public AccesRest(String idTrainToAccess, int time) {
		super();
		this.idTrainToAccess = idTrainToAccess;
		this.time = time;
	}

	public VoyageDuJour getListVoyages() {
		Client client = ClientBuilder.newClient();
		WebTarget webtarget = client.target("http://localhost:8080");
		Response resp =  client.target(webtarget.path("listVoyageDuTrain").path(""+this.idTrainToAccess).path(""+this.time).getUri()).request().get(Response.class);
		if (resp.getStatusInfo().getFamily().equals(Family.SUCCESSFUL)) {
			
			return resp.readEntity(VoyageDuJour.class);
			
		} else {

			throw new RuntimeException("failed to get voyage du jour" + resp.getStatusInfo().toString());
		}
		
		
	}
	
	public void postInfoVoyage(Voyage voyageActuel ) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
        Response resp = target.path("listVoyageDuTrain").queryParam("trainId", this.idTrainToAccess).queryParam("time", this.time).request().accept(MediaType.APPLICATION_XML)
				.post(Entity.xml(voyageActuel));
        if (resp.getStatusInfo().getFamily().equals(Family.SUCCESSFUL)) {
			System.out.println("info voyage sent Successfully");
		} else {

			throw new RuntimeException("failed to sent info voyage" + resp.getStatusInfo().toString());
		}
     }
}
