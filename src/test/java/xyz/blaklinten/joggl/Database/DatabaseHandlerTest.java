package xyz.blaklinten.joggl.Database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.blaklinten.joggl.Joggl;
import xyz.blaklinten.joggl.Model.Entry;

/** Unit test for the Databasehandler component. */
@SpringBootTest
public class DatabaseHandlerTest {

  @Autowired private DatabaseHandler dbHandler;

  @Autowired private Joggl joggl;

  private Entry anEntry;
  private Entry anEntryWithSameName;
  private Entry anEntryWithDifferentName;

  @BeforeEach
  public void init() {
    dbHandler.repo.deleteAll();

    String client = "The Client";
    String name = "A name";
    String project = "Development";
    String description = "A bunch of tests";

    anEntry = new Entry(name, client, project, description);
    anEntry.update(Entry.Property.STARTTIME, LocalDateTime.now());
    anEntry.update(Entry.Property.ENDTIME, LocalDateTime.now());

    String client1 = "The Client";
    String name1 = "A name";
    String project1 = "Development";
    String description1 = "This entry has the same name as anEntry";

    anEntryWithSameName = new Entry(name1, client1, project1, description1);
    anEntryWithSameName.update(Entry.Property.STARTTIME, LocalDateTime.now());
    anEntryWithSameName.update(Entry.Property.ENDTIME, LocalDateTime.now());
    String client2 = "The Client";
    String name2 = "Another name";
    String project2 = "Development";
    String description2 = "This entry has a different name";

    anEntryWithDifferentName = new Entry(name2, client2, project2, description2);
    anEntryWithDifferentName.update(Entry.Property.STARTTIME, LocalDateTime.now());
    anEntryWithDifferentName.update(Entry.Property.ENDTIME, LocalDateTime.now());
  }

  @Test
  public void saveOneEntryToAndGetByIDFromDatabaseTest() {

    try {
      Long id = dbHandler.save(joggl.entryToDTO(anEntry)).get();
      Entry fromDatabase =
          dbHandler
              .getEntryByID(id)
              .thenApply(
                  dto -> {
                    return joggl.DTOToEntry(dto);
                  })
              .get();

      assertTrue(anEntry.getName().equals(fromDatabase.getName()));
      assertTrue(anEntry.getClient().equals(fromDatabase.getClient()));
      assertTrue(anEntry.getProject().equals(fromDatabase.getProject()));
      assertTrue(anEntry.getDescription().equals(fromDatabase.getDescription()));
      assertTrue(anEntry.getStartTimeAsString().equals(fromDatabase.getStartTimeAsString()));
      assertTrue(anEntry.getEndTimeAsString().equals(fromDatabase.getEndTimeAsString()));

    } catch (InterruptedException | ExecutionException | NoSuchElementException e) {
      System.err.println(e.getMessage());
    }
  }

  @Test
  public void saveEntriesToDatabaseAndGetByPropertyTest() {
    dbHandler.save(joggl.entryToDTO(anEntry)).join();
    dbHandler.save(joggl.entryToDTO(anEntryWithDifferentName)).join();

    try {
      List<Entry> fromDatabaseWithName =
          dbHandler
              .getEntriesBy(Entry.Property.NAME, anEntry.getName())
              .thenApply(
                  list -> {
                    return list.stream()
                        .map(es -> joggl.DTOToEntry(es))
                        .collect(Collectors.toList());
                  })
              .get();

      assertTrue(anEntry.getName().equals(fromDatabaseWithName.get(0).getName()));
      assertTrue(anEntry.getClient().equals(fromDatabaseWithName.get(0).getClient()));
      assertTrue(anEntry.getProject().equals(fromDatabaseWithName.get(0).getProject()));
      assertTrue(anEntry.getDescription().equals(fromDatabaseWithName.get(0).getDescription()));
      assertTrue(
          anEntry
              .getStartTimeAsString()
              .equals(fromDatabaseWithName.get(0).getStartTimeAsString()));
      assertTrue(
          anEntry.getEndTimeAsString().equals(fromDatabaseWithName.get(0).getEndTimeAsString()));

      List<Entry> fromDatabaseWithDifferentName =
          dbHandler
              .getEntriesBy(Entry.Property.NAME, anEntryWithDifferentName.getName())
              .get()
              .stream()
              .map(es -> joggl.DTOToEntry(es))
              .collect(Collectors.toList());

      assertTrue(
          anEntryWithDifferentName
              .getName()
              .equals(fromDatabaseWithDifferentName.get(0).getName()));
      assertTrue(
          anEntryWithDifferentName
              .getClient()
              .equals(fromDatabaseWithDifferentName.get(0).getClient()));
      assertTrue(
          anEntryWithDifferentName
              .getProject()
              .equals(fromDatabaseWithDifferentName.get(0).getProject()));
      assertTrue(
          anEntryWithDifferentName
              .getDescription()
              .equals(fromDatabaseWithDifferentName.get(0).getDescription()));
      assertTrue(
          anEntryWithDifferentName
              .getStartTimeAsString()
              .equals(fromDatabaseWithDifferentName.get(0).getStartTimeAsString()));
      assertTrue(
          anEntryWithDifferentName
              .getEndTimeAsString()
              .equals(fromDatabaseWithDifferentName.get(0).getEndTimeAsString()));

      List<Entry> fromDatabaseByProject =
          dbHandler.getEntriesBy(Entry.Property.PROJECT, anEntry.getProject()).get().stream()
              .map(es -> joggl.DTOToEntry(es))
              .collect(Collectors.toList());

      assertTrue(fromDatabaseByProject.size() == 2);

    } catch (InterruptedException | ExecutionException | NoSuchElementException e) {
      System.err.println(e.getMessage());
    }
  }

  @Test
  public void saveMultipleEntriesToDatabaseAndCountTest() {

    dbHandler.save(joggl.entryToDTO(anEntry)).join();
    dbHandler.save(joggl.entryToDTO(anEntryWithSameName)).join();
    dbHandler.save(joggl.entryToDTO(anEntryWithDifferentName)).join();

    try {
      List<Entry> fromDatabaseByName =
          dbHandler.getEntriesBy(Entry.Property.NAME, anEntry.getName()).get().stream()
              .map(es -> joggl.DTOToEntry(es))
              .collect(Collectors.toList());
      List<Entry> fromDatabaseAll = new ArrayList<Entry>();
      dbHandler
          .repo
          .findAll()
          .forEach(
              e -> {
                fromDatabaseAll.add(joggl.DTOToEntry(e));
              });

      assertThat(fromDatabaseByName.size()).isEqualTo(2);
      assertThat(fromDatabaseAll.size()).isEqualTo(3);

    } catch (InterruptedException | ExecutionException | NoSuchElementException e) {
      System.err.println(e.getMessage());
    }
  }
}
