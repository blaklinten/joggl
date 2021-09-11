package xyz.blaklinten.joggl.Models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the Timer component.
 */
public class TimerTest {

	private Timer timer = new Timer();

	private String client = "The Client";
	private String name = "Test";
	private String project = "Development";
	private String description = "A bunch of tests";

	private NewEntry anEntry = new NewEntry(
			name,
 		   	client,
 		   	project,
 		   	description);

	@AfterEach
	public void after(){
		timer.resetTimer();
	}

	@Test
	public void startTimerTest(){
		RunningEntry running;
		try{
			running = timer.start(anEntry);
		assertThat(running.getStartTime()).isNotNull();
		assertTrue(running.getStartTime().isBefore(LocalDateTime.now()));
		assertTrue(timer.isRunning());
		}
		catch(Timer.TimerAlreadyRunningException e){
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void startTimerTwiceTest(){
		try{
			timer.start(anEntry);
		}
		catch(Timer.TimerAlreadyRunningException e){
			System.err.println(e.getMessage());
		}
		
		assertThrows(Timer.TimerAlreadyRunningException.class, () -> {
			timer.start(anEntry);
		});
	}

	@Test
	public void stopRunningTimerTest(){
		StoppedEntry stopped;
		try{
			timer.start(anEntry);
			stopped = timer.stop();
			LocalDateTime endTime = stopped.getEndTime();
			LocalDateTime startTime = stopped.getStartTime();

			assertThat(endTime).isNotNull();
			assertThat(startTime).isNotNull();
			assertTrue(endTime.isAfter(startTime));
			assertTrue(endTime.isBefore(LocalDateTime.now()));
			assertThat(timer.isRunning()).isFalse();
		}
		catch(Timer.TimerAlreadyRunningException|Timer.NoActiveTimerException e){
			System.err.println(e.getMessage());
		}

	}

	@Test
	public void stopRunningTimerTwiceTest(){
		try{
			timer.start(anEntry);
			timer.stop();
		}
		catch(Timer.TimerAlreadyRunningException|Timer.NoActiveTimerException e){
			System.err.println(e.getMessage());
		}

		assertThrows(Timer.NoActiveTimerException.class, () -> {
			timer.stop();
		});

	}

	@Test
	public void stopNotRunningTimerTest(){
		assertThrows(Timer.NoActiveTimerException.class, () -> {
			timer.stop();
		});
	}
}
