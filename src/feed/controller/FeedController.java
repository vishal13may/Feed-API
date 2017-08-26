package feed.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import feed.beans.Feed;
import feed.service.FeedService;

@Path("/feeds/")
public class FeedController {
	FeedService feedService = new FeedService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Feed> getFeeds() {
		Feed feed = new Feed();
		feed.setId(1);
		feed.setName("Technology");
		List<Feed> feeds = new ArrayList<Feed>();
		feeds.add(feed);
		Feed feed1 = new Feed();
		feed1.setId(2);
		feed1.setName("Technology");
		feeds.add(feed1);
		return feeds;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Feed getFeed(@PathParam("id") int id) {
		return new Feed();
	}

	@GET
	@Path("/user/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserFeeds(@PathParam("userId") int userId) {
		List<Feed> feeds = feedService.getUserFeeds(userId);
		JSONObject response = new JSONObject();
		if (feeds == null) {
			response.put("status", false);
			response.put("error", "Something went wrong");
			return Response.status(500).entity(response).build();
		}
		response.put("status", true);
		response.put("feeds", feeds);
		if (feeds.size() == 0) {
			// No content found
			return Response.status(204).entity(response).build();
		} else {
			return Response.status(200).entity(response).build();
		}
	}
}
