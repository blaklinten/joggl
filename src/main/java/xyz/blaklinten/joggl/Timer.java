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

	public String stop() throws NoActiveTimerException {
		LocalDateTime endTime = LocalDateTime.now();
		if (entry != null && entry.getEndTime() == null){
			entry.update(Entry.Property.ENDTIME, endTime);
			entry = null;
		} else {
			throw new NoActiveTimerException("No Timer to stop!");
		}
		return endTime.toString();
	}

	public Boolean isRunning(){
		return (entry != null) ? true : false;
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
