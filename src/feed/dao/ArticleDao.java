package feed.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import feed.beans.Article;
import feed.beans.Feed;
import feed.beans.Subscription;
import feed.util.ConnectionManager;

public class ArticleDao {

	public Map<String, List<Article>> getArticlesByUser(int userId) {
		Connection connection;
		Map<String, List<Article>> map = new HashMap<String, List<Article>>();
		try {
			connection = ConnectionManager.getConnection();
			if (connection == null) {
				return null;
			}
			String sql = "SELECT feed.name as feedName,article.id as articleId,article.author as author,article.content as content,article.title as title from"
					+ " feed, article where "
					+ "article.Id in (select feed_article.articleId from feed_article "
					+ "where feed_article.feedId in (select subscription.feedId from subscription where subscription.userId = ? and subscription.isActive = true))"
					+ " and feed.id in (select feed_article.feedId from feed_article where feed_article.articleId = article.id)";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rows = stmt.executeQuery();
			while (rows.next()) {
				String feedName = rows.getString("feedName");
				Article article = new Article();
				article.setId(rows.getInt("articleId"));
				article.setAuthor(rows.getString("author"));
				article.setContent(rows.getString("content"));
				article.setTitle(rows.getString("title"));
				if (map.containsKey(feedName)) {
					List<Article> articles = map.get(feedName);
					articles.add(article);
					map.put(feedName,articles);
				}else{
					List<Article> articles = new ArrayList<Article>();
					articles.add(article);
					map.put(feedName,articles);
				}
			}
			return map;
		} catch (SQLException e) {
			return null;
		}
	}

	public boolean createArticle(Article article) {
		Connection connection;
		try {
			connection = ConnectionManager.getConnection();
			if (connection == null) {
				return false;
			}
			connection.setAutoCommit(false);
			String insertArticle = "INSERT INTO article (title, content, author) "
					+ "VALUES (?, ?, ?)";
			PreparedStatement stmt1 = connection.prepareStatement(
					insertArticle, Statement.RETURN_GENERATED_KEYS);
			stmt1.setString(1, article.getTitle());
			stmt1.setString(2, article.getContent());
			stmt1.setString(3, article.getAuthor());
			int rows = stmt1.executeUpdate();
			if (rows == 0) {
				return false;
			}
			ResultSet generatedKeys = stmt1.getGeneratedKeys();
			if (generatedKeys.next()) {
				article.setId(generatedKeys.getInt(1));
			}
			String insertFeedArticle = "INSERT INTO feed_article (feedId, articleId) "
					+ "VALUES (?, ?)";
			PreparedStatement stmt2 = connection
					.prepareStatement(insertFeedArticle);
			stmt2.setInt(1, article.getFeedId());
			stmt2.setInt(2, article.getId());
			rows = stmt2.executeUpdate();
			if (rows == 0) {
				return false;
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

}
