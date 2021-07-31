package se.cygni.summerapp.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import se.cygni.summerapp.Models.Entry;

public class DatabaseHandler {
	private String dbURL;
	private String dbUser;
	private String dbPassword;

	private String table = "entries";
	private String columns = "name, client, project, description, startTime, endTime";

	private Connection dbConnection;

	public DatabaseHandler (String dbURL, String dbUser, String dbPassword){
		this.dbURL = dbURL;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
	}

	public void connect() throws SQLException {
			dbConnection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
	}

	protected void saveEntry(Entry entryToSave) throws SQLException{
		final String SAVE_QUERY =
			"INSERT INTO " + table +
			" (" + columns + ") VALUES "+
			" (?, ?, ?, ?, ?, ?);";

		if (dbConnection == null){
			throw new SQLException("No active dbConnection!");
		}
		PreparedStatement statement = dbConnection.prepareStatement(SAVE_QUERY);
		statement.setString(1, entryToSave.getName());
		statement.setString(2, entryToSave.getClient());
		statement.setString(3, entryToSave.getProject());
		statement.setString(4, entryToSave.getDescription());
		statement.setString(5, entryToSave.getStartTimeAsString());
		statement.setString(6, entryToSave.getEndTimeAsString());
		statement.executeUpdate();
	}

	public void cleanTable() throws SQLException{
		dropTable();
		createTable();
	}

	private void createTable() throws SQLException {
		final String CREATE_QUERY = "CREATE TABLE " +  table + " " + 
 		    "(id INT PRIMARY KEY AUTO_INCREMENT , name VARCHAR(100), " + 
 		    "client VARCHAR(100), project VARCHAR(100), description VARCHAR(100), " + 
 		    "startTime VARCHAR(50), endTime VARCHAR(50))";
		
		Statement statement = dbConnection.createStatement();
		statement.executeUpdate(CREATE_QUERY);
	}

	private void dropTable() throws SQLException {
		final String DROP_QUERY = "DROP TABLE " +  table;
		
		 Statement statement = dbConnection.createStatement();
		statement.executeUpdate(DROP_QUERY);
	}
}
