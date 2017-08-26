package feed.controller;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import feed.beans.Article;
import feed.service.ArticleService;

@Path("/articles/")
public class ArticleController {
	private ArticleService articleService = new ArticleService();

	@SuppressWarnings("unchecked")
	@POST
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createArticle(Article article) {
		boolean status = articleService.createArticle(article);
		JSONObject response = new JSONObject();
		response.put("status", status);
		if (status == true) {
			response.put("success", "Article created.");
			return Response.status(201).entity(response).build();
		} else {
			response.put("error", "Something went wrong.");
			return Response.status(500).entity(response).build();
		}
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/user/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArticlesByUser(@PathParam("userId") int userId) {
		JSONObject response = new JSONObject();
		Map<String, List<Article>> map = articleService
				.getArticlesByUser(userId);
		if (map == null) {
			response.put("status", false);
			response.put("error", "Something went wrong.");
			return Response.status(500).entity(response).build();
		}
		response.put("status", true);
		response.put("articles", map);
		if (map.size() == 0) {
			return Response.status(204).entity(response).build();
		} else {
			return Response.status(200).entity(response).build();
		}
	}
}
