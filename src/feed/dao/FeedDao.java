package feed.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import feed.beans.Feed;
import feed.util.ConnectionManager;

public class FeedDao {
	public List<Feed> getUserFeeds(int userId) {
		Connection connection = null;
		List<Feed> feeds = new ArrayList<Feed>();
		try {
			connection = ConnectionManager.getConnection();
			if (connection == null) {
				return null;
			}
			String sql = "SELECT feed.id as feed_id, feed.name as feed_name from subscription join "
					+ "feed on subscription.feedId = feed.id where subscription.userId = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rows = stmt.executeQuery();

			while (rows.next()) {
				Feed feed = new Feed();
				feed.setId(rows.getInt("feed_id"));
				feed.setName(rows.getString("feed_name"));
				feeds.add(feed);
			}
			return feeds;
		} catch (SQLException e) {
			return null;
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
