package xyz.blaklinten.joggl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import xyz.blaklinten.joggl.Models.NewEntry;
import xyz.blaklinten.joggl.Models.RunningEntry;
import xyz.blaklinten.joggl.Models.StoppedEntry;
import xyz.blaklinten.joggl.Models.Timer;
import xyz.blaklinten.joggl.Models.TimerStatus;

@SpringBootTest
public class JogglTest {

	private Logger log = LoggerFactory.getLogger(JogglTest.class);

	@Autowired
	private Joggl joggl;

	private String name = "A name";
	private String client = "The Client";
	private String project = "Development";
	private String description = "A bunch of tests";

	private NewEntry testEntry = new NewEntry(
			name,
			client,
			project,
			description);


	@AfterEach
	public void clearEntry(){
		joggl.resetTimer();
	}

	@Test
	public void startTimerTest(){
		try {
			RunningEntry running = joggl.startTimer(testEntry);
			
			assertThat(running.getName()).isEqualTo(testEntry.getName());
			assertThat(running.getClient()).isEqualTo(testEntry.getClient());
			assertThat(running.getProject()).isEqualTo(testEntry.getProject());
			assertThat(running.getDescription()).isEqualTo(testEntry.getDescription());
			assertThat(running.getStartTime()).isNotNull();
			assertTrue(running.getStartTime().isAfter(LocalDateTime.now()));
			assertThat(joggl.getStatus()).isNotNull();
		}
		catch (Timer.TimerAlreadyRunningException|Timer.NoActiveTimerException e) {
			log.error(e.getMessage());
		}
	}

	@Test
	public void stopRunningTimerTest(){
		try{
			RunningEntry running = joggl.startTimer(testEntry);

			StoppedEntry stopped = joggl.stopTimer();

			assertThat(stopped.getName()).isEqualTo(testEntry.getName());
			assertThat(stopped.getClient()).isEqualTo(testEntry.getClient());
			assertThat(stopped.getProject()).isEqualTo(testEntry.getProject());
			assertThat(stopped.getDescription()).isEqualTo(testEntry.getDescription());

			assertThat(stopped.getName()).isEqualTo(running.getName());
			assertThat(stopped.getClient()).isEqualTo(running.getClient());
			assertThat(stopped.getProject()).isEqualTo(running.getProject());
			assertThat(stopped.getDescription()).isEqualTo(running.getDescription());

			assertThat(stopped.getStartTime()).isNotNull();
			assertTrue(stopped.getStartTime().isBefore(LocalDateTime.now()));
			assertTrue(stopped.getStartTime().isEqual(running.getStartTime()));

			assertThat(stopped.getEndTime()).isNotNull();
			assertTrue(stopped.getEndTime().isBefore(LocalDateTime.now()));
			assertTrue(stopped.getEndTime().isAfter(running.getStartTime()));
			assertTrue(stopped.getEndTime().isAfter(stopped.getStartTime()));
		}
		catch (Timer.NoActiveTimerException|Timer.TimerAlreadyRunningException e){
			log.error(e.getMessage());
		}
	}

	@Test
	public void stopNotRunningTimerTest(){
			assertThrows(Timer.NoActiveTimerException.class, () -> {
				joggl.stopTimer();
			});
	}

	@Test
	public void getStatusTest(){
		try {
			joggl.startTimer(testEntry);

			TimerStatus status = joggl.getStatus();

			assertThat(status).isNotNull();
			assertThat(status.getName()).isEqualTo(testEntry.getName());
			assertTrue(status.getSeconds() > 0);
		}
		catch (Timer.TimerAlreadyRunningException|Timer.NoActiveTimerException e){
			log.error(e.getMessage());
		}
	}

	@Test
	public void getStatusOfNotRunningTimerTest(){
		assertThrows(Timer.NoActiveTimerException.class, () -> {
			joggl.getStatus();
		});
	}

	@Test
	public void sumEntriesbyNameTest(){

	}
}
