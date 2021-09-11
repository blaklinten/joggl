package xyz.blaklinten.joggl.Models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.blaklinten.joggl.EntryMapper;


/**
 * Unit test for an Entry
 */
public class EntryTest
{
	/* For testing with single Entry */
	String client = "The Client";
	String name = "Test";
	String project = "Development";
	String description = "A bunch of tests";

	NewEntry anEntry = new NewEntry(name, client, project, description);

	/* For testing with multiple entries */ 
	long id1 = 1, id2 = 2;
	String name1 = "e1", name2 = "e2";
	String client1 = "c1", client2 = "c2";
	String project1 = "p1", project2 = "p2";
	String description1 = "d1", description2 = "d2";

	LocalDateTime startTime1 = LocalDateTime.of(2021,9,1,12,30,00);
	LocalDateTime endTime1 = startTime1.plusMinutes(30);

	LocalDateTime startTime2 = LocalDateTime.of(2021,11,1,12,30,00);
	LocalDateTime endTime2 = startTime2.plusHours(2);

	StoppedEntry entry1 = EntryMapper.stop(
			EntryMapper.start(
				new NewEntry(
					name1,
 				   	client1,
 				   	project1,
					description1)
				)
			);


	StoppedEntry entry2 = EntryMapper.stop(
			EntryMapper.start(
				new NewEntry(
					name2,
 				   	client2,
 				   	project2,
					description2)
				)
			);

	@BeforeEach
	public void init(){
		entry1.updateStartTime(startTime1);
		entry1.updateEndTime(endTime1);
		entry2.updateStartTime(startTime2);
		entry2.updateEndTime(endTime2);
	}

	/* Single entry tests */
	@Test
	public void startEntryTest() {
		RunningEntry running = EntryMapper.start(anEntry);

		assertTrue(running.getClient() == client);
		assertTrue(running.getDescription() == description);
		assertTrue(running.getProject() == project);
		assertTrue(running.getName() == name);
		assertTrue(running.getStartTime() != null);
		assertTrue(LocalDateTime.now().isAfter(running.getStartTime()));
	}

	@Test
	public void CalculateDurationOfRunningEntryTest(){
		RunningEntry running = EntryMapper.start(anEntry);

		assertFalse(running.getDuration().isZero());
	}

	@Test
	public void endEntryTest() {
		final CountDownLatch waiter = new CountDownLatch(1);

		RunningEntry running = EntryMapper.start(anEntry);

		try{
		waiter.await(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		 StoppedEntry stopped = EntryMapper.stop(running);

		assertTrue(stopped.getClient() == client);
		assertTrue(stopped.getDescription() == description);
		assertTrue(stopped.getProject() == project);
		assertTrue(stopped.getName() == name);
		assertTrue(stopped.getStartTime() != null);
		assertTrue(LocalDateTime.now().isAfter(stopped.getStartTime()));
		assertTrue(stopped.getEndTime() != null);
		assertTrue(stopped.getEndTime().isAfter(stopped.getStartTime()));
		assertTrue(stopped.getEndTime().isBefore(LocalDateTime.now()));
	}

	@Test
	public void updateEntryTest() {
		final CountDownLatch waiter = new CountDownLatch(1);

		RunningEntry running = EntryMapper.start(anEntry);

		try{
		waiter.await(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}

		StoppedEntry stopped = EntryMapper.stop(running);

		String newClient = "Another Client";
		String newName = "Updating test";
		String newProject = "Development 2.0";
		String newDescription = "Updating the entry";

		LocalDateTime oldStartTime = stopped.getStartTime();
		LocalDateTime newStartTime = LocalDateTime.now();
		LocalDateTime oldEndTime = stopped.getEndTime();
		LocalDateTime newEndTime = LocalDateTime.now();

		stopped.update(StoppedEntry.Property.NAME, newName);
		stopped.update(StoppedEntry.Property.CLIENT, newClient);
		stopped.update(StoppedEntry.Property.DESCRIPTION, newDescription);
		stopped.update(StoppedEntry.Property.PROJECT, newProject);
		stopped.updateStartTime(newStartTime);
		stopped.updateEndTime(newEndTime);

		assertTrue(stopped.getName() == newName);
		assertTrue(stopped.getName() != name);
		assertTrue(stopped.getClient() == newClient);
		assertTrue(stopped.getClient() != client);
		assertTrue(stopped.getDescription() == newDescription);
		assertTrue(stopped.getDescription() != description);
		assertTrue(stopped.getProject() == newProject);
		assertTrue(stopped.getProject() != project);
		assertTrue(stopped.getStartTime() == newStartTime);
		assertTrue(stopped.getStartTime() != oldStartTime);
		assertTrue(stopped.getEndTime() == newEndTime);
		assertTrue(stopped.getEndTime() != oldEndTime);
	}

	@Test
	public void calculateDurationTest(){

		assertTrue(entry1.getDuration()
				.equals(Duration.ofMinutes(30)));
	}

	/* Multiple entries test */

	@Test
	public void sumEntriesTest(){
		ArrayList<StoppedEntry> list = new ArrayList<StoppedEntry>();
		list.add(entry1);
		list.add(entry2);

		Duration sum = list.stream()
			.map(e -> e.getDuration())
			.reduce(Duration.ZERO, (acc, current) ->
					acc = acc.plus(current));

		Duration expected = Duration.ofMinutes(30).plus(Duration.ofHours(2));

		assertTrue(sum.equals(expected));
	}
}
