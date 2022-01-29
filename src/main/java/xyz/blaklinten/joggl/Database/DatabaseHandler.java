package xyz.blaklinten.joggl.Database;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.blaklinten.joggl.Models.Entry;

@Component
public class DatabaseHandler {

  private Logger log = LoggerFactory.getLogger(DatabaseHandler.class);

  @Autowired Repository repo;

  public CompletableFuture<Long> save(EntryDTO entryToSave) {
    log.info("Saving entry " + entryToSave.getName() + " to database");
    return CompletableFuture.supplyAsync(
        () -> {
          EntryDTO savedEntry = repo.save(entryToSave);

          log.info("Got ID " + savedEntry.getId() + " from database");
          return savedEntry.getId();
        });
  }

  public CompletableFuture<EntryDTO> getEntryByID(long id) throws NoSuchElementException {
    log.info("Searching for entry with ID " + id);

    return CompletableFuture.supplyAsync(
        () -> {
          Optional<EntryDTO> result = repo.findById(id);

          if (result.isEmpty()) {
            String errorMessage = "No entry with ID " + id + " exists.";

            log.error(errorMessage);
            throw new NoSuchElementException(errorMessage);
          } else {
            return result.get();
          }
        });
  }

  public CompletableFuture<List<EntryDTO>> getEntriesBy(Entry.Property prop, String value)
      throws NoSuchElementException {
    return CompletableFuture.supplyAsync(
        () -> {
          List<EntryDTO> result;
          switch (prop) {
            case NAME:
              result = repo.findByName(value);
              break;
            case CLIENT:
              result = repo.findByClient(value);
              break;
            case PROJECT:
              result = repo.findByProject(value);
              break;
            default:
              String errorMessage = "Invalid property";
              log.error(errorMessage);
              throw new NoSuchElementException(errorMessage);
          }
          if (result.isEmpty()) {
            String errorMessage = "No entry with " + prop.toString() + " " + value + " exists.";

            log.error(errorMessage);
            throw new NoSuchElementException(errorMessage);
          } else {
            return result;
          }
        });
  }
}
