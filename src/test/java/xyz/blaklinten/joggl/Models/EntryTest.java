package xyz.blaklinten.joggl.Models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/** Unit test for an Entry */
public class EntryTest {
  /* For testing with single Entry */
  String client = "The Client";
  String name = "Test";
  String project = "Development";
  String description = "A bunch of tests";

  Entry anEntry = new Entry(name, client, project, description);

  /* For testing with multiple entries */
  long id1 = 1, id2 = 2;
  String name1 = "e1", name2 = "e2";
  String client1 = "c1", client2 = "c2";
  String project1 = "p1", project2 = "p2";
  String description1 = "d1", description2 = "d2";

  LocalDateTime startTime1 = LocalDateTime.of(2021, 9, 1, 12, 30, 00);
  LocalDateTime endTime1 = startTime1.plusMinutes(30);

  String startTimeString1 = startTime1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

  String endTimeString1 = endTime1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

  LocalDateTime startTime2 = LocalDateTime.of(2021, 11, 1, 12, 30, 00);
  LocalDateTime endTime2 = startTime2.plusHours(2);

  String startTimeString2 = startTime2.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

  String endTimeString2 = endTime2.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

  Entry entry1 =
      new Entry(id1, name1, client1, project1, description1, startTimeString1, endTimeString1);
  Entry entry2 =
      new Entry(id2, name2, client2, project2, description2, startTimeString2, endTimeString2);

  /* Single entry tests */
  @Test
  public void startEntryTest() {
    assertTrue(anEntry.getStartTime() == null);
    anEntry.update(Entry.Property.STARTTIME, LocalDateTime.now());

    assertTrue(anEntry.getClient() == client);
    assertTrue(anEntry.getDescription() == description);
    assertTrue(anEntry.getProject() == project);
    assertTrue(anEntry.getName() == name);
    assertTrue(anEntry.getStartTime() != null);
    assertTrue(LocalDateTime.now().isAfter(anEntry.getStartTime()));
    assertTrue(anEntry.getEndTime() == null);
  }

  @Test
  public void CalculateDurationOfRunningEntryTest() {
    anEntry.update(Entry.Property.STARTTIME, LocalDateTime.now());
    assertTrue(anEntry.getEndTime() == null);
    assertFalse(anEntry.getDuration().isZero());
  }

  @Test
  public void endEntryTest() {
    final CountDownLatch waiter = new CountDownLatch(1);

    anEntry.update(Entry.Property.STARTTIME, LocalDateTime.now());
    try {
      waiter.await(1000, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      System.err.println(e.getMessage());
    }
    anEntry.update(Entry.Property.ENDTIME, LocalDateTime.now());

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

  /* Multiple entries test */
  @Test
  public void calculateDurationTest() {

    assertTrue(entry1.getDuration().equals(Duration.ofMinutes(30)));
  }

  @Test
  public void sumEntriesTest() {
    ArrayList<Entry> list = new ArrayList<Entry>();
    list.add(entry1);
    list.add(entry2);

    Duration sum = Entry.sum(list);
    Duration expected = Duration.ofMinutes(30).plus(Duration.ofHours(2));

    assertTrue(sum.equals(expected));
  }
}
