package se.cygni.summerapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {
	private String dbURL;
	private String dbUser;
	private String dbPassword;

	private Connection dbConnection;

	public DatabaseHandler (String dbURL, String dbUser, String dbPassword){
		this.dbURL = dbURL;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
	}

	protected void connect() throws SQLException {
			dbConnection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
	}
}

/*
		try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery("SELECT * FROM entries")) {
					rs.first();
					System.out.println(rs.getString(2));
				}
			}
*/

