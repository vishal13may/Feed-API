package feed.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import feed.beans.Subscription;
import feed.service.SubscriberService;

import org.json.simple.JSONObject;

@Path("/")
public class SubscriberController {
	
	private SubscriberService subscriberService = new SubscriberService();

	@SuppressWarnings("unchecked")
	@POST
	@Path("/subscribe/")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUserSubScription(Subscription subscription) {
		boolean status = subscriberService.subscribe(subscription);
		JSONObject response = new JSONObject();
		response.put("status", status);
		if(status  == true){
			response.put("success","User is subscribed");
			return Response.status(201).entity(response).build();
		}else{
			response.put("error","Something went wrong");
			return Response.status(500).entity(response).build();
		}
	}
	
	@SuppressWarnings("unchecked")
	@PUT
	@Path("/unsubscribe/")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ubScribeUser(Subscription subscription) {
		boolean status = subscriberService.unsubscribe(subscription);
		JSONObject response = new JSONObject();
		response.put("status", status);
		if(status  == true){
			response.put("success","User is unsubscribed");
			return Response.status(200).entity(response).build();
		}else{
			response.put("error","Something went wrong");
			return Response.status(500).entity(response).build();
		}
	}
	
}
