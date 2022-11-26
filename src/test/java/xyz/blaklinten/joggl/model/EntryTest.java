package xyz.blaklinten.joggl.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/** Unit test for an Entry */
class EntryTest {
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

  LocalDateTime startTime1 = LocalDateTime.of(2021, 9, 1, 12, 30, 0);
  LocalDateTime endTime1 = startTime1.plusMinutes(30);

  String startTimeString1 = startTime1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

  String endTimeString1 = endTime1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

  LocalDateTime startTime2 = LocalDateTime.of(2021, 11, 1, 12, 30, 0);
  LocalDateTime endTime2 = startTime2.plusHours(2);

  String startTimeString2 = startTime2.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

  String endTimeString2 = endTime2.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

  Entry entry1 =
      new Entry(id1, name1, client1, project1, description1, startTimeString1, endTimeString1);
  Entry entry2 =
      new Entry(id2, name2, client2, project2, description2, startTimeString2, endTimeString2);

  /* Single entry tests */
  @Test
  void startEntryTest() {
    Assertions.assertNull(anEntry.getStartTime());
    anEntry.update(Entry.Property.STARTTIME, LocalDateTime.now());

    Assertions.assertSame(anEntry.getClient(), client);
    Assertions.assertSame(anEntry.getDescription(), description);
    Assertions.assertSame(anEntry.getProject(), project);
    Assertions.assertSame(anEntry.getName(), name);
    Assertions.assertNotNull(anEntry.getStartTime());
    Assertions.assertNull(anEntry.getEndTime());
    Assertions.assertTrue(LocalDateTime.now().isAfter(anEntry.getStartTime()));
  }

  @Test
  void CalculateDurationOfRunningEntryTest() {
    anEntry.update(Entry.Property.STARTTIME, LocalDateTime.now());
    Assertions.assertNull(anEntry.getEndTime());
    Assertions.assertFalse(anEntry.getDuration().isZero());
  }

  @Test
  void endEntryTest() {
    final CountDownLatch waiter = new CountDownLatch(1);

    anEntry.update(Entry.Property.STARTTIME, LocalDateTime.now());
    try {
      waiter.await(1000, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      System.err.println(e.getMessage());
    }
    anEntry.update(Entry.Property.ENDTIME, LocalDateTime.now());

    Assertions.assertSame(anEntry.getClient(), client);
    Assertions.assertSame(anEntry.getDescription(), description);
    Assertions.assertSame(anEntry.getProject(), project);
    Assertions.assertSame(anEntry.getName(), name);
    Assertions.assertNotNull(anEntry.getStartTime());
    Assertions.assertTrue(LocalDateTime.now().isAfter(anEntry.getStartTime()));
    Assertions.assertNotNull(anEntry.getEndTime());
    Assertions.assertTrue(anEntry.getEndTime().isAfter(anEntry.getStartTime()));
    Assertions.assertTrue(anEntry.getEndTime().isBefore(LocalDateTime.now()));
  }

  @Test
  void updateEntryTest() {
    String newClient = "Another Client";
    String newName = "Updating test";
    String newProject = "Development 2.0";
    String newDescription = "Updating the entry";
    LocalDateTime oldStartTime = anEntry.getStartTime();
    LocalDateTime newStartTime = LocalDateTime.now();
    LocalDateTime oldEndTime = anEntry.getEndTime();
    LocalDateTime newEndTime = LocalDateTime.now();

    anEntry.update(Entry.Property.CLIENT, newClient);
    Assertions.assertSame(anEntry.getClient(), newClient);
    Assertions.assertNotSame(anEntry.getClient(), client);

    anEntry.update(Entry.Property.DESCRIPTION, newDescription);
    Assertions.assertSame(anEntry.getDescription(), newDescription);
    Assertions.assertNotSame(anEntry.getDescription(), description);

    anEntry.update(Entry.Property.PROJECT, newProject);
    Assertions.assertSame(anEntry.getProject(), newProject);
    Assertions.assertNotSame(anEntry.getProject(), project);

    anEntry.update(Entry.Property.NAME, newName);
    Assertions.assertSame(anEntry.getName(), newName);
    Assertions.assertNotSame(anEntry.getName(), name);
    anEntry.update(Entry.Property.STARTTIME, newStartTime);
    Assertions.assertSame(anEntry.getStartTime(), newStartTime);
    Assertions.assertNotSame(anEntry.getStartTime(), oldStartTime);

    anEntry.update(Entry.Property.ENDTIME, newEndTime);
    Assertions.assertSame(anEntry.getEndTime(), newEndTime);
    Assertions.assertNotSame(anEntry.getEndTime(), oldEndTime);
  }

  /* Multiple entries test */
  @Test
  void calculateDurationTest() {

    Assertions.assertEquals(entry1.getDuration(), Duration.ofMinutes(30));
  }

  @Test
  void sumEntriesTest() {
    ArrayList<Entry> list = new ArrayList();
    list.add(entry1);
    list.add(entry2);

    Duration sum = Entry.sum(list);
    Duration expected = Duration.ofMinutes(30).plus(Duration.ofHours(2));

    Assertions.assertEquals(sum, expected);
  }
}
