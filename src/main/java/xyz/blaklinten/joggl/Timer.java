package xyz.blaklinten.joggl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import xyz.blaklinten.joggl.Models.Entry;

@Component
public class Timer {

	private Logger log = LoggerFactory.getLogger(Timer.class);

	private Entry entry = null;

	public void start(Entry newEntry) throws TimerAlreadyRunningException{
		if (entry != null) {
			String errorMessage = "A timer is already running!";
			log.error(errorMessage);
			throw new TimerAlreadyRunningException(errorMessage);
		} else {
			entry = newEntry;
			entry.update(Entry.Property.STARTTIME, LocalDateTime.now());
			log.info("Starting new entry");
		}
	}

	public Entry stop() throws NoActiveTimerException {
		Entry stoppedEntry;
		if (entry != null && entry.getEndTime() == null){
			entry.update(Entry.Property.ENDTIME, LocalDateTime.now());
			stoppedEntry = entry;
			entry = null;
			log.info("Stopping entry " + stoppedEntry.getName());
		} else {
			String errorMessage = "No Timer to stop!";
			log.error(errorMessage);
			throw new NoActiveTimerException(errorMessage);
		}
		return stoppedEntry;
	}

	public Boolean isRunning(){
		return (entry != null) ? true : false;
	}

	public TimerStatus getCurrentStatus() throws NoActiveTimerException {
		if (isRunning()) {
			return new TimerStatus(entry);
		} else {
			String errorMessage = "No Timer running...";
			log.error(errorMessage);
			throw new NoActiveTimerException(errorMessage);
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
