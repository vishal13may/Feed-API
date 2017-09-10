package feed.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	public static final String databaseName = "feed_api";
	public static final String userName = "root";
	public static final String password = "";

	public static Connection getConnection() throws SQLException {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			return null;
		}
		String url = String.format("jdbc:mysql://localhost:3306/%s", databaseName);
		Connection connection = DriverManager.getConnection(  
				url, userName, password);  
		return connection;
	}

}
