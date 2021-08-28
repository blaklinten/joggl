package xyz.blaklinten.joggl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import xyz.blaklinten.joggl.Models.Entry;

/**
 * Unit test for the Timer component.
 */
@SpringBootTest
public class TimerTest {

	@Autowired
	private Timer timer;

	@Test
	public void startTimerTest(){

		String client = "The Client";
		String name = "Test";
		String project = "Development";
		String description = "A bunch of tests";

		Entry anEntry = new Entry(name, client, project, description);

		try{
			timer.start(anEntry);
		}
		catch(Timer.TimerAlreadyRunningException e){
			System.err.println(e.getMessage());
		}
		
		assertThat(anEntry.getStartTime() != null);
		assertThat(anEntry.getStartTime().isAfter(LocalDateTime.now()));

		assertThrows(Timer.TimerAlreadyRunningException.class, () -> {
			timer.start(anEntry);
		});
	}
}

