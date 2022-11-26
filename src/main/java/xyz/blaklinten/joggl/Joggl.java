package xyz.blaklinten.joggl;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.blaklinten.joggl.database.DatabaseHandler;
import xyz.blaklinten.joggl.database.EntryDTO;
import xyz.blaklinten.joggl.model.AccumulatedTime;
import xyz.blaklinten.joggl.model.Entry;
import xyz.blaklinten.joggl.model.TimerStatus;

/** This is the top lever representation of a Joggl instance, i.e. the full application. */
@Component
public class Joggl {
  private final Logger log = LoggerFactory.getLogger(Joggl.class);

  @Autowired private Timer timer;

  @Autowired private DatabaseHandler dbHandler;

  /**
   * This method accesses the internal timer object and starts it from a given entry.
   *
   * @param entry The entry which to use as a base whe starting the timer.
   * @return A representation of the started entry.
   * @throws Timer.TimerAlreadyRunningException If a timer was already running and thus preventing a
   *     new one to be started.
   */
  public EntryDTO startTimer(EntryDTO entry) throws Timer.TimerAlreadyRunningException {
    log.info("Incoming start request");

    Entry newEntry = fromDTO(entry);

    timer.start(newEntry);

    log.info("Started entry {}", newEntry.getName());

    return fromEntry(newEntry);
  }

  /**
   * This method tries to stop a running timer and save the resulting entry to the database.
   *
   * @return The recently stopped and saved entry.
   * @throws Timer.NoActiveTimerException If there were no running timer.
   */
  public CompletableFuture<EntryDTO> stopTimer() throws Timer.NoActiveTimerException {
    log.info("Incoming stop request");

    EntryDTO stoppedEntry = fromEntry(timer.stop());

    return dbHandler
        .save(stoppedEntry)
        .thenApply(
            id -> {
              stoppedEntry.updateID(id);
              log.info("Stopped entry {}", stoppedEntry.getName());
              return stoppedEntry;
            });
  }

  /** This method is mostly used by Tests to reset the timer in between runs. */
  public void resetTimer() {
    timer.reset();
  }

  /**
   * This method tries to get the status of the currently running timer. If the timer is not
   * running, an exception is thrown.
   *
   * @return The status of the currently running timer.
   * @throws Timer.NoActiveTimerException If there are no active timer to get status from.
   */
  public TimerStatus getStatus() throws Timer.NoActiveTimerException {
    log.info("Incoming status request");

    return timer.getCurrentStatus();
  }

  /**
   * A method that returns the accumulated time of all entries in the database with a specific name.
   * This is done by calling an internal helper method called calculateSum.
   *
   * @param name The name to use when searching for entries in the database.
   * @return The result of calling calculateSum with the provided name
   */
  public CompletableFuture<AccumulatedTime> sumEntriesByName(String name)
      throws NoSuchElementException {
    log.info("Incoming sum-by-name request");

    return calculateSum(Entry.Property.NAME, name);
  }

  /**
   * A method that returns the accumulated time of all entries in the database that belongs to a
   * specific client. This is done by calling an internal helper method called calculateSum.
   *
   * @param client The client to use when searching for entries in the database.
   * @return The result of calling calculateSum with the provided client
   */
  public CompletableFuture<AccumulatedTime> sumEntriesByClient(String client)
      throws NoSuchElementException {
    log.info("Incoming sum-by-client request");

    return calculateSum(Entry.Property.CLIENT, client);
  }

  /**
   * A method that returns the accumulated time of all entries in the database that belongs to a
   * specific project. This is done by calling an internal helper method called calculateSum.
   *
   * @param project The project to use when searching for entries in the database.
   * @return The result of calling calculateSum with the provided project
   * @throws NoSuchElementException If no element with that property value was found in the
   *     database.
   */
  public CompletableFuture<AccumulatedTime> sumEntriesByProject(String project)
      throws NoSuchElementException {
    log.info("Incoming sum-by-project request");

    return calculateSum(Entry.Property.PROJECT, project);
  }

  /**
   * This helper method tries to fetch all entries that matches a specific property from the
   * database and accumulate their durations. It returns the accumulated duration as a
   * AccumulatedTime object.
   *
   * @param prop The property to use as key when searching the database.
   * @param value The value of the property to use as key when searching the database.
   * @return The resulting duration of all the entries with matching prop values.
   */
  private CompletableFuture<AccumulatedTime> calculateSum(Entry.Property prop, String value) {
    CompletableFuture<List<EntryDTO>> result = dbHandler.getEntriesBy(prop, value);
    CompletableFuture<Duration> sum =
        result.thenApply(
            entries ->
              entries.stream()
                  .map(entry -> fromDTO(entry).getDuration())
                  .reduce(Duration.ZERO, (totalDuration, currentDuration) -> totalDuration = totalDuration.plus(currentDuration))
            );
    return sum.thenApply(
            totalTime ->
              new AccumulatedTime(prop.toString(), value, totalTime)
            );
  }

  /**
   * This method is used to convert an entry into something more convenient for serialization (i.e.
   * database and JSON usage); an entryDTO.
   *
   * @param entry The entry to convert
   * @return The entryDTO representation of the entry.
   */
  public EntryDTO fromEntry(Entry entry) {

    return new EntryDTO(
        entry.getName(),
        entry.getClient(),
        entry.getProject(),
        entry.getDescription(),
        entry.getStartTimeAsString(),
        entry.getEndTimeAsString());
  }

  /**
   * This method is used to convert an entryDTO into something more convenient for internal use,
   * i.e. an Entry.
   *
   * @param entryDTO The entryDTO to convert
   * @return The Entry representation of the entryDTO.
   */
  public Entry fromDTO(EntryDTO entryDTO) {
    if (hasDatabaseId(entryDTO)) {
      return new Entry(
              entryDTO.getId(),
              entryDTO.getName(),
              entryDTO.getClient(),
              entryDTO.getProject(),
              entryDTO.getDescription(),
              entryDTO.getStartTime(),
              entryDTO.getEndTime());
    } else {
      return new Entry(
              entryDTO.getName(),
              entryDTO.getClient(),
              entryDTO.getProject(),
              entryDTO.getDescription());
    }
  }
  private boolean hasDatabaseId(EntryDTO entryDTO) {
    return entryDTO.getId() != null;
  }
}
