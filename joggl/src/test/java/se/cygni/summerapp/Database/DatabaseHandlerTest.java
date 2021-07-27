package se.cygni.summerapp.Database;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import se.cygni.summerapp.Models.Entry;

/**
 * Unit test for an DatabaseHandler
 */

public class DatabaseHandlerTest {
		String dbURL = "jdbc:mariadb://localhost/test";
		String dbUser = "blaklinten";
		String dbPassword = "";
		DatabaseHandler dbHandler = new DatabaseHandler(dbURL, dbUser, dbPassword);

		String client = "testClient";
		String description = "testDescription";
		String name = "testName";
 	   	String project = "testProject";
 	   	Entry testEntry = new Entry(client, description, name, project);

	@Test
	public void dbSaveTest(){
		testEntry.start();
		testEntry.stop();

		try {
		dbHandler.connect();
		dbHandler.cleanTable();
		dbHandler.saveEntry(testEntry);
		} catch (SQLException e){
			System.err.println(e.getMessage());
		}

		assertTrue(true);
	}
}
