package xyz.blaklinten.joggl;

import java.time.Duration;

import org.springframework.stereotype.Component;

import xyz.blaklinten.joggl.Models.Entry;

@Component
public class TimeCalculator {


	public Duration getDuration(Entry e1) {
		return Duration.between(e1.getStartTime(), e1.getEndTime());
	}
}
