package xyz.blaklinten.joggl.Database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import xyz.blaklinten.joggl.Models.Entry;
import xyz.blaklinten.joggl.Database.EntrySchema;

/**
 * Unit test for the Database handler component.
 */
@SpringBootTest
public class DatabaseHandlerTest {

	@Autowired
	private DatabaseHandler dbHandler;

	@Autowired
	private Repository repo;

	private Entry anEntry;

	@BeforeEach
	public void init(){
		String client = "The Client";
		String name = "A very unique name";
		String project = "Development";
		String description = "A bunch of tests";

		anEntry = new Entry(name, client, project, description);
		anEntry.update(Entry.Property.STARTTIME, LocalDateTime.now());
		anEntry.update(Entry.Property.ENDTIME, LocalDateTime.now());
	}

	@AfterEach
	public void dropEntries(){
		repo.deleteAll();
	}

	@Test
	public void saveToAndGetFromDatabaseTest(){
		dbHandler.save(anEntry);

		try{
			Entry fromDatabase = dbHandler.getEntryByName(anEntry.getName());

			assertTrue(anEntry.getName().equals(fromDatabase.getName()));
			assertTrue(anEntry.getClient().equals(fromDatabase.getClient()));
			assertTrue(anEntry.getProject().equals(fromDatabase.getProject()));
			assertTrue(anEntry.getDescription().equals(fromDatabase.getDescription()));
			assertTrue(anEntry.getStartTimeAsString().equals(fromDatabase.getStartTimeAsString()));
			assertTrue(anEntry.getEndTimeAsString().equals(fromDatabase.getEndTimeAsString()));
		} catch (DatabaseHandler.NoSuchEntryException e){
			System.err.println(e.getMessage());
		}
	}
}
