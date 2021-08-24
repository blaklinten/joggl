package xyz.blaklinten.joggl.Models;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * Unit test for an Entry
 */
public class EntryTest
{
	String client = "The Client";
	String name = "Test";
	String project = "Development";
	String description = "A bunch of tests";

	Entry anEntry = new Entry(name, client, project, description);

	@Test
	public void startEntryTest() {
		assertTrue(anEntry.getStartTime() == null);
		anEntry.start();

		assertTrue(anEntry.getClient() == client);
		assertTrue(anEntry.getDescription() == description);
		assertTrue(anEntry.getProject() == project);
		assertTrue(anEntry.getName() == name);
		assertTrue(anEntry.getStartTime() != null);
		assertTrue(LocalDateTime.now().isAfter(anEntry.getStartTime()));
		assertTrue(anEntry.getEndTime() == null);
	}

	@Test
	public void endEntryTest() {
		final CountDownLatch waiter = new CountDownLatch(1);

		anEntry.start();
		try{
		waiter.await(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		anEntry.stop();

		assertTrue(anEntry.getClient() == client);
		assertTrue(anEntry.getDescription() == description);
		assertTrue(anEntry.getProject() == project);
		assertTrue(anEntry.getName() == name);
		assertTrue(anEntry.getStartTime() != null);
		assertTrue(LocalDateTime.now().isAfter(anEntry.getStartTime()));
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

		anEntry.update(Entry.Property.CLIENT, newClient);
		assertTrue(anEntry.getClient() == newClient);
		assertTrue(anEntry.getClient() != client);

		anEntry.update(Entry.Property.DESCRIPTION, newDescription);
		assertTrue(anEntry.getDescription() == newDescription);
		assertTrue(anEntry.getDescription() != description);

		anEntry.update(Entry.Property.PROJECT, newProject);
		assertTrue(anEntry.getProject() == newProject);
		assertTrue(anEntry.getProject() != project);


		anEntry.update(Entry.Property.NAME, newName);
		assertTrue(anEntry.getName() == newName);
		assertTrue(anEntry.getName() != name);

		anEntry.update(Entry.Property.STARTTIME, newStartTime);
		assertTrue(anEntry.getStartTime() == newStartTime);
		assertTrue(anEntry.getStartTime() != oldStartTime);

		anEntry.update(Entry.Property.ENDTIME, newEndTime);
		assertTrue(anEntry.getEndTime() == newEndTime);
		assertTrue(anEntry.getEndTime() != oldEndTime);
	}
}

