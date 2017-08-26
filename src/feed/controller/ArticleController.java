package feed.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import feed.beans.Subscription;
import feed.util.ConnectionManager;

@Path("/articles/")
public class ArticleController {

	@POST
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createArticle(Article article) {
		try {
			Connection c = ConnectionManager.getConnection();
			System.out.println(c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject response = new JSONObject();
		response.put("status", true);
		return Response.status(201).entity(response).build();
	}
	
	@GET
	@Path("/user/{userId}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getArticlesByUser(@PathParam("userId") int userId) {
		JSONObject response = new JSONObject();
		response.put("status", true);
		Map<String, List<Article>> map = new HashMap<String, List<Article>>();
		
		List<Article> articles = new ArrayList<Article>();
		Article a = new Article();
		a.setId(1);
		a.setContent("test");
		a.setFeedId(1);
		
		a.setAuthor("av");
		
		articles.add(a);
		
		Article b = new Article();
		b.setId(1);
		b.setContent("test1");
		b.setFeedId(1);
		
		b.setAuthor("av1");
		articles.add(b);
		map.put("techical", articles);
		response.put("articles", map);
		
		
		return Response.status(201).entity(response).build();
	}
}
