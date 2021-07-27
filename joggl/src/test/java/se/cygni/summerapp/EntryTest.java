package se.cygni.summerapp;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Unit test for an Entry
 */
public class EntryTest
{
	String client = "The Client";
	String name = "Test";
	String project = "Development";
	String description = "A bunch of tests";

	Entry anEntry = new Entry(client, description, name, project);

	@Test
	public void createEntryTest() {
		assertTrue(anEntry.getClient() == client);
		assertTrue(anEntry.getDescription() == description);
		assertTrue(anEntry.getProject() == project);
		assertTrue(anEntry.getName() == name);
		assertTrue(LocalDateTime.now().isAfter(anEntry.getStartTime()));
		assertTrue(anEntry.getEndTime() == null);
	}

	@Test
	public void endEntryTest() {
		anEntry.stopEntry();
		assertTrue(anEntry.getEndTime() != null);
		assertTrue(anEntry.getEndTime().isAfter(anEntry.getStartTime()));
		assertTrue(anEntry.getEndTime().isBefore(LocalDateTime.now()));
	}

	@Test
	public void updateEntryTest() {
		String newClient = "Another Client";
		String newName = "Updating test";
		String newProject = "Development 2.0";
		String newDescription = "Updating the entry";
		LocalDateTime oldStartTime = anEntry.getStartTime();
		LocalDateTime newStartTime = LocalDateTime.now();
		LocalDateTime oldEndTime = anEntry.getEndTime();
		LocalDateTime newEndTime = LocalDateTime.now();

		try {
		anEntry.update("Client", newClient);
		}
		catch (NoSuchElementException e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
		assertTrue(anEntry.getClient() == newClient);
		assertTrue(anEntry.getClient() != client);

		try {
		anEntry.update("Description", newDescription);
		}
		catch (NoSuchElementException e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
		assertTrue(anEntry.getDescription() == newDescription);
		assertTrue(anEntry.getDescription() != description);

		try {
		anEntry.update("Project", newProject);
		}
		catch (NoSuchElementException e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
		assertTrue(anEntry.getProject() == newProject);
		assertTrue(anEntry.getProject() != project);


		try {
		anEntry.update("Name", newName);
		}
		catch (NoSuchElementException e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
		assertTrue(anEntry.getName() == newName);
		assertTrue(anEntry.getName() != name);

		try {
		anEntry.update("StartTime", newStartTime);
		}
		catch (NoSuchElementException e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
		assertTrue(anEntry.getStartTime() == newStartTime);
		assertTrue(anEntry.getStartTime() != oldStartTime);

		try {
		anEntry.update("EndTime", newEndTime);
		}
		catch (NoSuchElementException e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
		assertTrue(anEntry.getEndTime() == newEndTime);
		assertTrue(anEntry.getEndTime() != oldEndTime);
	}
}

