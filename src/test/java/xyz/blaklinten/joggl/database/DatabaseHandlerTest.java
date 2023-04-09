package xyz.blaklinten.joggl.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.blaklinten.joggl.Joggl;
import xyz.blaklinten.joggl.helper.EntryCreator;
import xyz.blaklinten.joggl.model.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/** Unit test for the Database handler component. */
@SpringBootTest
class DatabaseHandlerTest {

  @Autowired private DatabaseHandler dbHandler;

  @Autowired private Joggl joggl;

  @BeforeEach
  public void init() {
    dbHandler.repo.deleteAll();
  }

  @Test
  void saveEntriesToDatabaseAndGetByPropertyTest() {
    var anEntry = EntryCreator.createDefaultEntry();

    var anEntryWithDifferentName = EntryCreator.createRandomEntry();
    anEntryWithDifferentName.update(Entry.Property.CLIENT, anEntry.getClient());
    anEntryWithDifferentName.update(Entry.Property.PROJECT, anEntry.getProject());
    anEntryWithDifferentName.update(Entry.Property.DESCRIPTION, anEntry.getDescription());
    anEntryWithDifferentName.update(Entry.Property.STARTTIME, anEntry.getStartTime());
    anEntryWithDifferentName.update(Entry.Property.ENDTIME, anEntry.getEndTime());

    dbHandler.save(anEntry.toDTO()).join();
    dbHandler.save(anEntryWithDifferentName.toDTO()).join();

    try {
      List<Entry> fromDatabaseWithName =
          dbHandler
              .getEntriesBy(Entry.Property.NAME, anEntry.getName())
              .thenApply(
                  list -> {
                    return list.stream().map(Entry::from).collect(Collectors.toList());
                  })
              .get();

      Assertions.assertEquals(anEntry.getName(), fromDatabaseWithName.get(0).getName());
      Assertions.assertEquals(anEntry.getClient(), fromDatabaseWithName.get(0).getClient());
      Assertions.assertEquals(anEntry.getProject(), fromDatabaseWithName.get(0).getProject());
      Assertions.assertEquals(
          anEntry.getDescription(), fromDatabaseWithName.get(0).getDescription());
      Assertions.assertEquals(
          anEntry.getStartTimeAsString(), fromDatabaseWithName.get(0).getStartTimeAsString());
      Assertions.assertEquals(
          anEntry.getEndTimeAsString(), fromDatabaseWithName.get(0).getEndTimeAsString());

      List<Entry> fromDatabaseWithDifferentName =
          dbHandler
              .getEntriesBy(Entry.Property.NAME, anEntryWithDifferentName.getName())
              .get()
              .stream()
              .map(Entry::from)
              .collect(Collectors.toList());

      Assertions.assertEquals(
          anEntryWithDifferentName.getName(), fromDatabaseWithDifferentName.get(0).getName());
      Assertions.assertEquals(
          anEntryWithDifferentName.getClient(), fromDatabaseWithDifferentName.get(0).getClient());
      Assertions.assertEquals(
          anEntryWithDifferentName.getProject(), fromDatabaseWithDifferentName.get(0).getProject());
      Assertions.assertEquals(
          anEntryWithDifferentName.getDescription(),
          fromDatabaseWithDifferentName.get(0).getDescription());
      Assertions.assertEquals(
          anEntryWithDifferentName.getStartTimeAsString(),
          fromDatabaseWithDifferentName.get(0).getStartTimeAsString());
      Assertions.assertEquals(
          anEntryWithDifferentName.getEndTimeAsString(),
          fromDatabaseWithDifferentName.get(0).getEndTimeAsString());

      List<Entry> fromDatabaseByProject =
          dbHandler.getEntriesBy(Entry.Property.PROJECT, anEntry.getProject()).get().stream()
              .map(Entry::from)
              .collect(Collectors.toList());

      Assertions.assertEquals(2, fromDatabaseByProject.size());

    } catch (InterruptedException | ExecutionException | NoSuchElementException e) {
      System.err.println(e.getMessage());
    }
  }

  @Test
  void saveMultipleEntriesToDatabaseAndCountTest() {
    var anEntry = EntryCreator.createRandomEntry();
    var anEntryWithSameName = EntryCreator.createRandomEntry();
    anEntryWithSameName.update(Entry.Property.NAME, anEntry.getName());

    var anEntryWithDifferentName = EntryCreator.createRandomEntry();
    anEntryWithDifferentName.update(Entry.Property.CLIENT, anEntry.getClient());
    anEntryWithDifferentName.update(Entry.Property.PROJECT, anEntry.getProject());
    anEntryWithDifferentName.update(Entry.Property.DESCRIPTION, anEntry.getDescription());
    anEntryWithDifferentName.update(Entry.Property.STARTTIME, anEntry.getStartTime());
    anEntryWithDifferentName.update(Entry.Property.ENDTIME, anEntry.getEndTime());

    dbHandler.save(anEntry.toDTO()).join();
    dbHandler.save(anEntryWithSameName.toDTO()).join();
    dbHandler.save(anEntryWithDifferentName.toDTO()).join();

    try {
      List<Entry> fromDatabaseByName =
          dbHandler.getEntriesBy(Entry.Property.NAME, anEntry.getName()).get().stream()
              .map(Entry::from)
              .collect(Collectors.toList());
      List<Entry> fromDatabaseAll = new ArrayList<Entry>();
      dbHandler
          .repo
          .findAll()
          .forEach(
              e -> {
                fromDatabaseAll.add(Entry.from(e));
              });

      assertThat(fromDatabaseByName).hasSize(2);
      assertThat(fromDatabaseAll).hasSize(3);

    } catch (InterruptedException | ExecutionException | NoSuchElementException e) {
      System.err.println(e.getMessage());
    }
  }
}
