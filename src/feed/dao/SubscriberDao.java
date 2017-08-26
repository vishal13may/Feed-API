package feed.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import feed.beans.Subscription;
import feed.util.ConnectionManager;

public class SubscriberDao {
	public boolean subscribe(Subscription subscription) {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			if (connection == null) {
				return false;
			}
			String sql = "INSERT INTO subscription (feedId, userId, startDate, isActive) "
					+ "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE startDate = ?, isActive = ?, "
					+ "endDate = NULL";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, subscription.getFeedId());
			stmt.setInt(2, subscription.getUserId());
			stmt.setTimestamp(3, new java.sql.Timestamp(subscription
					.getStartDate().getTime()));
			stmt.setBoolean(4, subscription.isActive());
			stmt.setTimestamp(5, new java.sql.Timestamp(subscription
					.getStartDate().getTime()));
			stmt.setBoolean(6, subscription.isActive());
			int rows = stmt.executeUpdate();
			if (rows == 0) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			return false;
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

	public boolean unsubscribe(Subscription subscription) {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
			if (connection == null) {
				return false;
			}
			String sql = "update subscription set isActive = ?, endDate = ? where userId = ? and feedId = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setBoolean(1, subscription.isActive());
			stmt.setTimestamp(2, new java.sql.Timestamp(subscription
					.getEndDate().getTime()));
			stmt.setInt(3, subscription.getUserId());
			stmt.setInt(4, subscription.getFeedId());
			int rows = stmt.executeUpdate();
			if (rows == 0) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			return false;
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
