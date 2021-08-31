package xyz.blaklinten.joggl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import xyz.blaklinten.joggl.Models.Entry;

@Component
public class Timer {

	private Entry entry = null;

	public void start(Entry newEntry) throws TimerAlreadyRunningException{
		if (entry != null) {
			throw new TimerAlreadyRunningException("A timer is already running!");
		} else {
			entry = newEntry;
			entry.update(Entry.Property.STARTTIME, LocalDateTime.now());
		}
	}

	public void stop() throws NoActiveTimerException {
		if (entry != null && entry.getEndTime() == null){
			entry.update(Entry.Property.ENDTIME, LocalDateTime.now());
		} else {
			throw new NoActiveTimerException("No Timer to stop!");
		}
	}

	public class NoActiveTimerException extends Exception {
		public NoActiveTimerException(String errorMessage){
			super(errorMessage);
		}
	}

	public class TimerAlreadyRunningException extends Exception {
		public TimerAlreadyRunningException(String errorMessage){
			super(errorMessage);
		}
	}
}
