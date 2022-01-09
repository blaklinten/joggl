package xyz.blaklinten.joggl;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.blaklinten.joggl.Models.Entry;
import xyz.blaklinten.joggl.Models.TimerStatus;

/** This is a class the encapsulate all the necessary functionality to act as a Timer. */
@Component
public class Timer {

  private Logger log = LoggerFactory.getLogger(Timer.class);

  private Entry entry = null;

  /**
   * A method that starts the timer, i.e. creates a new entry and records the start time.
   *
   * @param newEntry The entry that is to be used as the base for the running timer.
   * @throws TimerAlreadyRunningException When a user tries to start an entry and another one is
   *     already running.
   */
  public void start(Entry newEntry) throws TimerAlreadyRunningException {
    if (isNotRunning()) {
      entry = newEntry;

      entry.update(Entry.Property.STARTTIME, LocalDateTime.now());
      log.info("Starting new entry");
    } else {
      String errorMessage = "A timer is already running!";

      log.error(errorMessage);
      throw new TimerAlreadyRunningException(errorMessage);
    }
  }

  /**
   * This method stop the currently running timer, if any. This means it records the stop time
   * (now), resets the timer and returns the stopped entry.
   *
   * @return The previously running, now stopped, entry.
   * @throws NoActiveTimerException When there are no active entry, i.e. the timer is not running.
   */
  public Entry stop() throws NoActiveTimerException {

    if (isRunning()) {
      entry.update(Entry.Property.ENDTIME, LocalDateTime.now());

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

  /** This method resets the current Timer instance by making sure that its entry is null. */
  protected void reset() {
    entry = null;
  }

  /**
   * This method returns the status of the currently running timer. If the timer is not running, an
   * NoActiveTimerException is thrown.
   *
   * @return The status of the currently running timer.
   * @throws NoActiveTimerException If the timer is not currently running.
   */
  public TimerStatus getCurrentStatus() throws NoActiveTimerException {
    if (isRunning()) {
      return new TimerStatus(entry);
    } else {
      String errorMessage = "No Timer running...";

      log.error(errorMessage);
      throw new NoActiveTimerException(errorMessage);
    }
  }

  /**
   * This method translates the state of the internal entry-object to whether the timer is running
   * or not.
   *
   * @return True if the timer is running, False otherwise.
   */
  public Boolean isRunning() {
    return (entry != null) ? true : false;
  }

  /**
   * This method translates the state of the internal entry-object to whether the timer is running
   * or not.
   *
   * @return True if the timer is not running, False otherwise.
   */
  public Boolean isNotRunning() {
    return (entry == null) ? true : false;
  }

  /** This class is a wrapper class and defines a custom exception. */
  public class NoActiveTimerException extends Exception {
    /**
     * This exception communicates that the user has tried to access a running timer when the timer
     * in fact was not running, e.g. stopping a timer before starting it or getting the status when
     * no timer is running.
     *
     * @param errorMessage The message the use when creating the exception.
     */
    public NoActiveTimerException(String errorMessage) {
      super(errorMessage);
    }
  }

  /** This class is a wrapper class and defines a custom exception. */
  public class TimerAlreadyRunningException extends Exception {
    /**
     * This exception communicates that the user has tried to start a timer when the timer in fact
     * was already running.
     *
     * @param errorMessage The message the use when creating the exception.
     */
    public TimerAlreadyRunningException(String errorMessage) {
      super(errorMessage);
    }
  }
}
