package xyz.blaklinten.joggl.database;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.blaklinten.joggl.model.Entry;

/**
 * DatabaseHandler This class encapsulates the underlying database repository. An instance of a
 * DatabaseHandler provides methods to fetch and save entries to and from the database, as well as
 * logging and exception handling.
 */
@Component
public class DatabaseHandler {

  private final Logger log = LoggerFactory.getLogger(DatabaseHandler.class);

  @Autowired Repository repo;

  /**
   * These methods save a given entry to the database and returns the generated ID. This ID is
   * unique and can thus be used to reference this particular entry later.
   *
   * @param entryToSave The entry that is to be saved.
   * @return The unique ID given to the saved entry.
   */
  public CompletableFuture<Long> save(EntryDTO entryToSave) {
    log.info("Saving entry {}s to db", entryToSave.getName());
    return CompletableFuture.supplyAsync(
        () -> {
          EntryDTO savedEntry = repo.save(entryToSave);

          log.info("Got ID {} from db", savedEntry.getId());
          return savedEntry.getId();
        });
  }

  /**
   * This method fetches a specific entry from the database, based on a given ID. If no entry with
   * the specified ID exists, a {@link NoSuchElementException} is thrown.
   *
   * @param id The unique ID of the entry to be fetched.
   * @return If found, this is the entry with the given ID.
   * @throws NoSuchElementException If no entry with the specified ID is found.
   */
  public CompletableFuture<EntryDTO> getEntryByID(long id) throws NoSuchElementException {
    log.info("Searching for entry with ID {}", id);

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

  /**
   * Similar to getEntriesByID, this method fetches entries from the database based on some
   * property. Availiable properties are Entry.Property.NAME, Entry.Property.CLIENT and
   * Entry.Property.PROJECT. This method will translate its input parameters into a suitable call to
   * the underlying repository and return a list with the resulting entries. If no entries match the
   * query an {@link NoSuchElementException} is thrown.
   *
   * @param prop The property by wich entries are matched in the database query.
   * @param value The value of the property, i.e. the actual name if the property if of type
   *     Entry.Property.NAME.
   * @return A ist of entries representing the result of the database query.
   * @throws NoSuchElementException If the resulting list of entries is empty, i.e. no entries
   *     matched the query.
   */
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
