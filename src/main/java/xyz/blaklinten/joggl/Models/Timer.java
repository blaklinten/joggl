package xyz.blaklinten.joggl.Models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.blaklinten.joggl.EntryMapper;

/**
 * This is a class that encapsulates all
 * the necessary functionality to act as a Timer.
 * */
@Component
public class Timer {

	private Logger log = LoggerFactory.getLogger(Timer.class);

	@Autowired
	private RunningEntry runningEntry = null;

	/**
 	 * A method that starts the timer, i.e. creates a new entry and records the start time.
 	 * @param newEntry The entry that is to be used as the base for the running timer.
 	 * @throws TimerAlreadyRunningException When a user tries to start an entry
 	 * and another one is already running.
 	 * */
	public RunningEntry start(NewEntry newEntry) throws TimerAlreadyRunningException{
		if (isNotRunning()) {
			
			runningEntry = EntryMapper.start(newEntry);

			log.info("Starting new entry");
			return runningEntry;
		}
		else {
			String errorMessage = "A timer is already running!";

			log.error(errorMessage);
			throw new TimerAlreadyRunningException(errorMessage);
		}
	}

	/**
 	 * This method stops the currently running timer, if any.
 	 * This means it records the stop time (now), resets the timer and returns the stopped entry.
 	 * @return The previously running, now stopped, entry.
 	 * @throws NoActiveTimerException When there are no active entry, i.e. the timer is not running.
 	 * */
	public StoppedEntry stop() throws NoActiveTimerException {

		if (isRunning()){
			StoppedEntry stoppedEntry = EntryMapper.stop(runningEntry);
			resetTimer();

			log.info("Stopping entry " + stoppedEntry.getName());
			return stoppedEntry;
		}
		else {
			String errorMessage = "No Timer to stop!";

			log.error(errorMessage);
			throw new NoActiveTimerException(errorMessage);
		}
	}

	/**
 	 * This method resets the current Timer instance
 	 * by making sure that its runningEntry is null.
 	 * */
	public void resetTimer(){
		runningEntry = null;
	}

	/**
 	 * This method returns the status of the currently running timer.
 	 * If the timer is not running, an NoActiveTimerException is thrown.
 	 * @return The status of the currently running timer.
 	 * @throws NoActiveTimerException If the timer is not currently running.
 	 * */
	public TimerStatus getCurrentStatus() throws NoActiveTimerException {
		if (isRunning()) {
			return new TimerStatus(runningEntry);
		}
		else {
			String errorMessage = "No Timer running...";

			log.error(errorMessage);
			throw new NoActiveTimerException(errorMessage);
		}
	}

	/**
 	 * This method translates the state of the internal
 	 * entry-object to whether the timer is running or not.
 	 * @return True if the timer is running, False otherwise.
 	 * */
	protected Boolean isRunning(){
		return (runningEntry != null) ? true : false;
	}

	/**
 	 * This method translates the state of the internal
 	 * entry-object to whether the timer is running or not.
 	 * @return True if the timer is not running, False otherwise.
 	 * */
	private Boolean isNotRunning(){
		return (runningEntry == null) ? true : false;
	}

	/**
 	 * This class is a wrapper class and defines a custom exception.
 	 * */
	public class NoActiveTimerException extends Exception {
		/**
 	 	 * This exception communicates that the user has tried to
 	 	 * access a running timer when the timer in fact was not running,
 	 	 * e.g. stopping a timer before starting it or getting
 	 	 * the status when no timer is running.
 	 	 * @param errorMessage The message the use when creating the exception.
 	 	 * */
		public NoActiveTimerException(String errorMessage){
			super(errorMessage);
		}
	}

	/**
 	 * This class is a wrapper class and defines a custom exception.
 	 * */
	public class TimerAlreadyRunningException extends Exception {
		/**
 	 	 * This exception communicates that the user has tried to
 	 	 * start a timer when the timer in fact was already running.
 	 	 * @param errorMessage The message the use when creating the exception.
 	 	 * */
		public TimerAlreadyRunningException(String errorMessage){
			super(errorMessage);
		}
	}
}
