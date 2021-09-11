package xyz.blaklinten.joggl;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.blaklinten.joggl.Database.DatabaseHandler;
import xyz.blaklinten.joggl.Models.AccumulatedTime;
import xyz.blaklinten.joggl.Models.EntryDTO;
import xyz.blaklinten.joggl.Models.NewEntry;
import xyz.blaklinten.joggl.Models.RunningEntry;
import xyz.blaklinten.joggl.Models.StoppedEntry;
import xyz.blaklinten.joggl.Models.Timer;
import xyz.blaklinten.joggl.Models.TimerStatus;

/**
 * This is the top lever representation 
 * of a Joggl instance, i.e. the full application.
 * */
@Component
public class Joggl {
	private Logger log = LoggerFactory.getLogger(Joggl.class);

	@Autowired
	private Timer timer;

	@Autowired
	private DatabaseHandler dbHandler;

	/**
 	 * This method accesses the internal timer object and starts it from a given
 	 * entry.
 	 * @param entry The entry which to use as a base whe starting the timer.
 	 * @return A representation of the started entry.
 	 * @throws Timer.TimerAlreadyRunningException If a timer was already running and thus preventing a new one to be started.
 	 * */
	public RunningEntry startTimer(NewEntry newEntry) throws Timer.TimerAlreadyRunningException {
		log.info("Incoming start request");

		RunningEntry running = timer.start(newEntry);

		log.info("Started entry " + running.getName());

		return running;
	}

	/**
 	 * This method tries to stop a running timer and save the resulting entry to the database.
 	 * @return The recently stopped and saved entry.
 	 * @throws Timer.NoActiveTimerException If there were no running timer.
 	 * */
	public StoppedEntry stopTimer() throws Timer.NoActiveTimerException {
		log.info("Incoming stop request");

		StoppedEntry stopped = timer.stop();

		EntryDTO entryToSave = EntryMapper.prepareToSave(stopped);
		dbHandler.save(entryToSave);

		log.info("Stopped entry " + stopped.getName());

		return stopped;
	}

	/**
 	 * This method is mostly used by Tests to reset the timer in between runs.
 	 * */
	public void resetTimer(){
		timer.resetTimer();
	}

	/**
 	 * This method tries to get the status of the currently running timer.
 	 * If the timer is not running, an exception is thrown.
 	 * @return The status of the currently running timer.
 	 * @throws Timer.NoActiveTimerException If there are no active timer to get status from.
 	 * */
	public TimerStatus getStatus() throws Timer.NoActiveTimerException{
		log.info("Incoming status request");

		TimerStatus status = timer.getCurrentStatus();

		return status;
	}

	/**
 	 * A method that returns the accumulated time of all entries
 	 * in the database with a specific name.
 	 * This is done by calling an internal helper method called
 	 * calculateSum.
 	 * @param name The name to use when searching for entries in the database.
 	 * @return The result of calling calculateSum with the provided name
 	 * */
	public AccumulatedTime sumEntriesbyName(String name) throws NoSuchElementException{
		log.info("Incoming sum-by-name request");

		return calculateSum(NewEntry.Property.NAME, name);

	}

	/**
 	 * A method that returns the accumulated time of all entries
 	 * in the database that belongs to a specific client.
 	 * This is done by calling an internal helper method called
 	 * calculateSum.
 	 * @param client The client to use when searching for entries in the database.
 	 * @return The result of calling calculateSum with the provided client
 	 * */
	public AccumulatedTime sumEntriesbyClient(String client) throws NoSuchElementException {
		log.info("Incoming sum-by-client request");

		return calculateSum(NewEntry.Property.CLIENT, client);

	}
	
	/**
 	 * A method that returns the accumulated time of all entries
 	 * in the database that belongs to a specific project.
 	 * This is done by calling an internal helper method called
 	 * calculateSum.
 	 * @param project The project to use when searching for entries in the database.
 	 * @return The result of calling calculateSum with the provided project
 	 * @throws NoSuchElementException If no element with that property value was found in the database.
 	 * */
	public AccumulatedTime sumEntriesbyProject(String project) throws NoSuchElementException {
		log.info("Incoming sum-by-project request");

		return calculateSum(NewEntry.Property.PROJECT, project);
	}

	/**
 	 * This helper method tries to fetch all entries that matches a specific property
 	 * from the database and accumulate their durations.
 	 * It returns the accumulated duration as a AccumulatedTime object.
 	 * @param prop The property to use as key when searching the database.
 	 * @param value The value of the property to use as key when searching the database.
 	 * @return The resulting duration of all the entries with matching prop values.
 	 * */
	private AccumulatedTime calculateSum(NewEntry.Property prop, String value){
			Duration sum = dbHandler.getEntriesBy(prop, value)
			.stream().map(dto -> EntryMapper.entryFromDTO(dto).getDuration())
			.reduce(Duration.ZERO, (acc, current) -> acc = acc.plus(current));

			return new AccumulatedTime(prop.toString(), value, sum);
	}
}
