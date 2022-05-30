package xyz.blaklinten.joggl.Database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import xyz.blaklinten.joggl.Models.Entry;
import xyz.blaklinten.joggl.Models.Joggl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseHandlerTest {
  @Mock Repository repository;
  DatabaseHandler dbHandler = new DatabaseHandler(repository);

  @Test
  public void GetByIDTest() {
    Entry anEntry = defaultEntry();
    try {
      Long id = dbHandler.save(Joggl.entryToDTO(anEntry)).get();
      Entry fromDatabase = dbHandler.getEntryByID(id).thenApply(Joggl::DTOToEntry).get();

      Assertions.assertTrue(isEqual(anEntry, fromDatabase));

    } catch (InterruptedException | ExecutionException | NoSuchElementException e) {
      System.err.println(e.getMessage());
    }
  }

  @Test
  public void GetByPropertyTest() {
    String aName = "a name";
    String anotherName = "another name";

    Entry anEntry = entryWithName(aName);
    Entry anEntryWithDifferentName = entryWithName(anotherName);

    dbHandler.save(Joggl.entryToDTO(anEntry)).join();
    dbHandler.save(Joggl.entryToDTO(anEntryWithDifferentName)).join();

    try {
      List<Entry> fromDatabaseWithName =
          dbHandler
              .getEntriesBy(Entry.Property.NAME, aName)
              .thenApply(list -> list.stream().map(Joggl::DTOToEntry).collect(Collectors.toList()))
              .get();

      Assertions.assertTrue(isEqual(anEntry, fromDatabaseWithName.get(0)));

      List<Entry> fromDatabaseWithDifferentName =
          dbHandler
              .getEntriesBy(Entry.Property.NAME, anotherName)
              .thenApply(list -> list.stream().map(Joggl::DTOToEntry).collect(Collectors.toList()))
              .get();

      Assertions.assertTrue(
          isEqual(anEntryWithDifferentName, fromDatabaseWithDifferentName.get(0)));

      List<Entry> fromDatabaseByProject =
          dbHandler
              .getEntriesBy(Entry.Property.PROJECT, anEntry.getProject())
              .thenApply(list -> list.stream().map(Joggl::DTOToEntry).collect(Collectors.toList()))
              .get();

      Assertions.assertEquals(2, fromDatabaseByProject.size());

    } catch (InterruptedException | ExecutionException | NoSuchElementException e) {
      System.err.println(e.getMessage());
    }
  }

  @Test
  public void saveMultipleEntriesToDatabaseAndCountTest() {

    dbHandler.save(Joggl.entryToDTO(anEntry)).join();
    dbHandler.save(Joggl.entryToDTO(anEntryWithSameName)).join();
    dbHandler.save(Joggl.entryToDTO(anEntryWithDifferentName)).join();

    try {
      List<Entry> fromDatabaseByName =
          dbHandler.getEntriesBy(Entry.Property.NAME, anEntry.getName()).get().stream()
              .map(Joggl::DTOToEntry)
              .collect(Collectors.toList());
      List<Entry> fromDatabaseAll = new ArrayList<Entry>();
      repo.findAll()
          .forEach(
              e -> {
                fromDatabaseAll.add(Joggl.DTOToEntry(e));
              });

      assertThat(fromDatabaseByName.size()).isEqualTo(2);
      assertThat(fromDatabaseAll.size()).isEqualTo(3);

    } catch (InterruptedException | ExecutionException | NoSuchElementException e) {
      System.err.println(e.getMessage());
    }
  }

  private boolean isEqual(Entry entry1, Entry entry2) {
    return entry1.getName().equals(entry2.getName())
        && entry1.getClient().equals(entry2.getClient())
        && entry1.getProject().equals(entry2.getProject())
        && entry1.getDescription().equals(entry2.getDescription())
        && entry1.getStartTime().isEqual(entry2.getStartTime())
        && entry1.getEndTime().isEqual(entry2.getEndTime());
  }

  private Entry entryWithName(String name) {
    String client = "tester";
    String project = "a-test";
    String description = "testing";
    return new Entry(name, client, project, description);
  }

  private Entry defaultEntry() {
    String name = "test-entry";
    String client = "tester";
    String project = "a-test";
    String description = "testing";
    return new Entry(name, client, project, description);
  }
}
