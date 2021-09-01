package xyz.blaklinten.joggl;

import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import xyz.blaklinten.joggl.Models.Entry;

/**
 * Unit test for the TimeCalculatorTest component.
 */
public class TimeCalculatorTest {

	private TimeCalculator calculator = new TimeCalculator();

	long id1 = 1, id2 = 2;
	String name1 = "e1", name2 = "e2";
	String client1 = "c1", client2 = "c2";
	String project1 = "p1", project2 = "p2";
	String description1 = "d1", description2 = "d2";

	@Test
	public void calculateDuration(){

		String startTime = LocalDateTime.of(2021,9,1,12,30,00)
			.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		
		String endTime = LocalDateTime.of(2021,9,1,12,30,30)
			.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		Entry e1 = new Entry(id1, name1, client1, project1,
				description1,startTime, endTime);

		assertTrue(calculator.getDuration(e1).equals(Duration.ofSeconds(30)));
	
	}
}
