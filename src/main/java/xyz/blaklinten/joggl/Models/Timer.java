package xyz.blaklinten.joggl.Models;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Timer {

  private Entry entry = null;

  public void start(Entry newEntry) throws TimerAlreadyRunningException {
    if (isNotRunning()) {
      entry = newEntry;

      entry.update(Entry.Property.START_TIME, LocalDateTime.now());
      log.info("Starting new entry");
    } else {
      String errorMessage = "A timer is already running!";

      log.error(errorMessage);
      throw new TimerAlreadyRunningException(errorMessage);
    }
  }

  public Entry stop() throws NoActiveTimerException {

    if (isRunning()) {
      entry.update(Entry.Property.END_TIME, LocalDateTime.now());

      Entry stoppedEntry = entry;

      reset();

      log.info("Stopping entry " + stoppedEntry.getName());
      return stoppedEntry;
    } else {
      String errorMessage = "No Timer to stop!";

      log.error(errorMessage);
      throw new NoActiveTimerException(errorMessage);
    }
  }

  protected void reset() {
    entry = null;
  }

  public TimerStatus getCurrentStatus() throws NoActiveTimerException {
    if (isRunning()) {
      return new TimerStatus(entry);
    } else {
      String errorMessage = "No Timer running...";

      log.error(errorMessage);
      throw new NoActiveTimerException(errorMessage);
    }
  }

  public Boolean isRunning() {
    return entry != null;
  }

  public Boolean isNotRunning() {
    return entry == null;
  }

  public static class NoActiveTimerException extends Exception {
    public NoActiveTimerException(String errorMessage) {
      super(errorMessage);
    }
  }

  public static class TimerAlreadyRunningException extends Exception {
    public TimerAlreadyRunningException(String errorMessage) {
      super(errorMessage);
    }
  }
}
