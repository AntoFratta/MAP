package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbAccess {

	String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	final String DBMS = "jdbc:mysql";
	final String SERVER = "localhost";
	final String DATABASE = "MapDB";
	final String PORT = "3306";
	final String USER_ID = "MapUser";
	final String PASSWORD = "map";
	Connection conn;

	public void initConnection() throws DatabaseConnectionException {
		try {
			Class.forName(DRIVER_CLASS_NAME);
			String url = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE;
			conn = DriverManager.getConnection(url, USER_ID, PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {
			throw new DatabaseConnectionException();
		}
	}

	Connection getConnection() {
		return conn;
	}

	void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
		}
	}

}
