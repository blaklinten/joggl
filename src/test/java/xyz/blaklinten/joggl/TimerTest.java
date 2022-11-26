package xyz.blaklinten.joggl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.blaklinten.joggl.model.Entry;

/** Unit test for the Timer component. */
class TimerTest {

  private final Timer timer = new Timer();
  private Entry anEntry;

  @BeforeEach
  public void init() {
    String client = "The Client";
    String name = "Test";
    String project = "Development";
    String description = "A bunch of tests";

    anEntry = new Entry(name, client, project, description);
  }

  @Test
  void startTimerTest() {
    try {
      timer.start(anEntry);
    } catch (Timer.TimerAlreadyRunningException e) {
      System.err.println(e.getMessage());
    }

    assertThat(anEntry.getStartTime()).isNotNull();
    Assertions.assertTrue(anEntry.getStartTime().isBefore(LocalDateTime.now()));
    Assertions.assertTrue(timer.isRunning());
  }

  @Test
  void startTimerTwiceTest() {
    try {
      timer.start(anEntry);
    } catch (Timer.TimerAlreadyRunningException e) {
      System.err.println(e.getMessage());
    }

    assertThrows(
        Timer.TimerAlreadyRunningException.class,
        () -> {
          timer.start(anEntry);
        });
  }

  @Test
  void stopRunningTimerTest() {
    try {
      timer.start(anEntry);
    } catch (Timer.TimerAlreadyRunningException e) {
      System.err.println(e.getMessage());
    }

    try {
      timer.stop();
    } catch (Timer.NoActiveTimerException e) {
      System.err.println(e.getMessage());
    }

    LocalDateTime endTime = anEntry.getEndTime();
    LocalDateTime startTime = anEntry.getStartTime();

    Assertions.assertNotEquals(null, endTime);
    Assertions.assertNotEquals(null, startTime);
    Assertions.assertTrue(endTime.isAfter(startTime));
    Assertions.assertFalse(timer.isRunning());
  }

  @Test
  void stopRunningTimerTwiceTest() {
    try {
      timer.start(anEntry);
    } catch (Timer.TimerAlreadyRunningException e) {
      System.err.println(e.getMessage());
    }

    try {
      timer.stop();
    } catch (Timer.NoActiveTimerException e) {
      System.err.println(e.getMessage());
    }

    assertThrows(
        Timer.NoActiveTimerException.class,
            timer::stop);
  }

  @Test
  void stopNotRunningTimerTest() {
    assertThrows(
        Timer.NoActiveTimerException.class,
            timer::stop);
  }
}
