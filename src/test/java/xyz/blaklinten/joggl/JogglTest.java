package xyz.blaklinten.joggl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.blaklinten.joggl.database.EntryDTO;
import xyz.blaklinten.joggl.model.TimerStatus;

@SpringBootTest
public class JogglTest {

  private Logger log = LoggerFactory.getLogger(JogglTest.class);

  @Autowired private Joggl joggl;

  private EntryDTO testEntry;

  @BeforeEach
  public void initEntry() {
    String name = "A name";
    String client = "The Client";
    String project = "Development";
    String description = "A bunch of tests";
    String startTime = null;
    String endTime = null;

    testEntry = new EntryDTO(name, client, project, description, startTime, endTime);
  }

  @AfterEach
  public void clearEntry() {
    testEntry = null;
    joggl.resetTimer();
  }

  @Test
  void startTimerTest() {
    try {
      EntryDTO startedEntry = joggl.startTimer(testEntry);

      assertThat(startedEntry.getName()).isEqualTo(testEntry.getName());
      assertThat(startedEntry.getClient()).isEqualTo(testEntry.getClient());
      assertThat(startedEntry.getProject()).isEqualTo(testEntry.getProject());
      assertThat(startedEntry.getDescription()).isEqualTo(testEntry.getDescription());

      assertThat(startedEntry.getStartTime()).isNotNull();
      assertThat(startedEntry.getEndTime()).isEqualTo("null");
    } catch (Timer.TimerAlreadyRunningException e) {
      log.error(e.getMessage());
    }
  }

  @Test
  void stopTimerTest() {
    try {
      assertThrows(
          Timer.NoActiveTimerException.class,
          joggl::stopTimer);

      joggl.startTimer(testEntry);

      EntryDTO stoppedEntry = joggl.stopTimer().get();
      assertThat(stoppedEntry.getName()).isEqualTo(testEntry.getName());
      assertThat(stoppedEntry.getClient()).isEqualTo(testEntry.getClient());
      assertThat(stoppedEntry.getProject()).isEqualTo(testEntry.getProject());
      assertThat(stoppedEntry.getDescription()).isEqualTo(testEntry.getDescription());

      assertThat(stoppedEntry.getStartTime()).isNotNull();
      assertThat(stoppedEntry.getEndTime()).isNotNull();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  @Test
  void getStatusTest() {
    assertThrows(
        Timer.NoActiveTimerException.class,
        () -> {
          joggl.getStatus();
        });

    try {
      joggl.startTimer(testEntry);

      TimerStatus status = joggl.getStatus();

      assertThat(status.getName()).isEqualTo(testEntry.getName());
    } catch (Timer.TimerAlreadyRunningException | Timer.NoActiveTimerException e) {
      log.error(e.getMessage());
    }
  }

  @Test
  void sumEntriesByNameTest() {}
}
