package xyz.blaklinten.joggl;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.blaklinten.joggl.Database.DatabaseHandler;
import xyz.blaklinten.joggl.Database.EntryDTO;
import xyz.blaklinten.joggl.Models.AccumulatedTime;
import xyz.blaklinten.joggl.Models.Entry;
import xyz.blaklinten.joggl.Models.TimerStatus;

@Component
public class Joggl {
  private Logger log = LoggerFactory.getLogger(Joggl.class);

  @Autowired private Timer timer;

  @Autowired private DatabaseHandler dbHandler;

  public EntryDTO startTimer(EntryDTO entry) throws Timer.TimerAlreadyRunningException {
    log.info("Incoming start request");

    Entry newEntry =
        new Entry(entry.getName(), entry.getClient(), entry.getProject(), entry.getDescription());

    timer.start(newEntry);

    log.info("Started entry " + newEntry.getName());

    return entryToDTO(newEntry);
  }

  public CompletableFuture<EntryDTO> stopTimer() throws Timer.NoActiveTimerException {
    log.info("Incoming stop request");

    Entry stoppedEntry;
    EntryDTO stoppedEntryDTO;

    stoppedEntry = timer.stop();
    stoppedEntryDTO = entryToDTO(stoppedEntry);

    return dbHandler
        .save(stoppedEntryDTO)
        .thenApply(
            id -> {
              stoppedEntryDTO.updateID(id);
              log.info("Stopped entry " + stoppedEntry.getName());
              return stoppedEntryDTO;
            });
  }

  public void resetTimer() {
    timer.reset();
  }

  public TimerStatus getStatus() throws Timer.NoActiveTimerException {
    log.info("Incoming status request");

    return timer.getCurrentStatus();
  }

  public CompletableFuture<AccumulatedTime> sumEntriesbyName(String name)
      throws NoSuchElementException {
    log.info("Incoming sum-by-name request");

    return calculateSum(Entry.Property.NAME, name);
  }

  public CompletableFuture<AccumulatedTime> sumEntriesbyClient(String client)
      throws NoSuchElementException {
    log.info("Incoming sum-by-client request");

    return calculateSum(Entry.Property.CLIENT, client);
  }

  public CompletableFuture<AccumulatedTime> sumEntriesbyProject(String project)
      throws NoSuchElementException {
    log.info("Incoming sum-by-project request");

    return calculateSum(Entry.Property.PROJECT, project);
  }

  private CompletableFuture<AccumulatedTime> calculateSum(Entry.Property prop, String value) {
    CompletableFuture<List<EntryDTO>> result = dbHandler.getEntriesBy(prop, value);
    CompletableFuture<Duration> sum =
        result.thenApply(
            list -> {
              return list.stream()
                  .map(dto -> DTOToEntry(dto).getDuration())
                  .reduce(Duration.ZERO, (acc, current) -> acc = acc.plus(current));
            });
    CompletableFuture<AccumulatedTime> accumulatedTime =
        sum.thenApply(
            totalTime -> {
              return new AccumulatedTime(prop.toString(), value, totalTime);
            });

    return accumulatedTime;
  }

  public EntryDTO entryToDTO(Entry entry) {

    return new EntryDTO(
        entry.getName(),
        entry.getClient(),
        entry.getProject(),
        entry.getDescription(),
        entry.getStartTimeAsString(),
        entry.getEndTimeAsString());
  }

  public Entry DTOToEntry(EntryDTO entryDTO) {
    return new Entry(
        entryDTO.getId(),
        entryDTO.getName(),
        entryDTO.getClient(),
        entryDTO.getProject(),
        entryDTO.getDescription(),
        entryDTO.getStartTime(),
        entryDTO.getEndTime());
  }
}
