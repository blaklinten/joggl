package xyz.blaklinten.joggl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import xyz.blaklinten.joggl.Models.TimerStatus;

@Component
public class Timer {

	private Logger log = LoggerFactory.getLogger(Timer.class);

	private Entry entry = null;

	public void start(Entry newEntry) throws TimerAlreadyRunningException{
		if (isNotRunning()) {
			entry = newEntry;

			entry.update(Entry.Property.STARTTIME, LocalDateTime.now());
			log.info("Starting new entry");
		}
		else {
			String errorMessage = "A timer is already running!";

			log.error(errorMessage);
			throw new TimerAlreadyRunningException(errorMessage);
		}
	}

	public Entry stop() throws NoActiveTimerException {

		if (isRunning()){
			entry.update(Entry.Property.ENDTIME, LocalDateTime.now());

			Entry stoppedEntry = entry;

			entry = null;

			log.info("Stopping entry " + stoppedEntry.getName());
			return stoppedEntry;
		}
		else {
			String errorMessage = "No Timer to stop!";

			log.error(errorMessage);
			throw new NoActiveTimerException(errorMessage);
		}
	}

	public void reset(){
		entry = null;
	}

	public TimerStatus getCurrentStatus() throws NoActiveTimerException {
		if (isRunning()) {
			return new TimerStatus(entry);
		}
		else {
			String errorMessage = "No Timer running...";

			log.error(errorMessage);
			throw new NoActiveTimerException(errorMessage);
		}
	}

	public Boolean isRunning(){
		return (entry != null) ? true : false;
	}

	public Boolean isNotRunning(){
		return (entry == null) ? true : false;
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
