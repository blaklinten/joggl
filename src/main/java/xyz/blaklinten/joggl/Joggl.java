package xyz.blaklinten.joggl;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.blaklinten.joggl.Database.DatabaseHandler;
import xyz.blaklinten.joggl.Models.EntryModel;
import xyz.blaklinten.joggl.Models.AccumulatedTime;
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
	public EntryModel startTimer(EntryModel entry) throws Timer.TimerAlreadyRunningException {
		log.info("Incoming start request");

		Entry newEntry = new Entry(
				entry.getName(),
				entry.getClient(),
				entry.getProject(),
				entry.getDescription());

		timer.start(newEntry);

		log.info("Started entry " + newEntry.getName());

		return entryToModel(newEntry);
	}

	/**
 	 * This method tries to stop a running timer and save the resulting entry to the database.
 	 * @return The recently stopped and saved entry.
 	 * @throws Timer.NoActiveTimerException If there were no running timer.
 	 * */
	public EntryModel stopTimer() throws Timer.NoActiveTimerException {
		log.info("Incoming stop request");

		Entry stoppedEntry;
		EntryModel stoppedEntryModel;

		stoppedEntry = timer.stop();
		stoppedEntryModel = entryToModel(stoppedEntry);
		dbHandler.save(stoppedEntryModel);

		log.info("Stopped entry " + stoppedEntry.getName());

		return stoppedEntryModel;
	}

	/**
 	 * This method is mostly used by Tests to reset the timer in between runs.
 	 * */
	public void resetTimer(){
		timer.reset();
	}

	/**
 	 * This method tries to get the status of the currently running timer.
 	 * If the timer is not running, an exception is thrown.
 	 * @return The status of the currently running timer.
 	 * @throws Timer.NoActiveTimerException If there are no active timer to get status from.
 	 * */
	public TimerStatus getStatus() throws Timer.NoActiveTimerException{
		log.info("Incoming status request");

		return timer.getCurrentStatus();
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

		return calculateSum(Entry.Property.NAME, name);

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

		return calculateSum(Entry.Property.CLIENT, client);

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

		return calculateSum(Entry.Property.PROJECT, project);
	}

	/**
 	 * This helper method tries to fetch all entries that matches a specific property
 	 * from the database and accumulate their durations.
 	 * It returns the accumulated duration as a AccumulatedTime object.
 	 * @param prop The property to use as key when searching the database.
 	 * @param value The value of the property to use as key when searching the database.
 	 * @return The resulting duration of all the entries with matching prop values.
 	 * */
	private AccumulatedTime calculateSum(Entry.Property prop, String value){
			Duration sum = dbHandler.getEntriesBy(prop, value)
			.stream().map(em -> modelToEntry(em).getDuration())
			.reduce(Duration.ZERO, (acc, current) -> acc = acc.plus(current));

			return new AccumulatedTime(prop.toString(), value, sum);
	}

	/**
 	 * This method is used to convert an entry into something 
 	 * more convenient for serialization (i.e. database and JSON usage); an entryModel.
 	 * @param entry The entry to convert
 	 * @return The entryModel representation of the entry.
 	 * */
	public EntryModel entryToModel(Entry entry){

		return new EntryModel(
				entry.getName(),
				entry.getClient(),
				entry.getProject(),
				entry.getDescription(),
				entry.getStartTimeAsString(),
				entry.getEndTimeAsString());
	}

	/**
 	 * This method is used to convert an entryToModel into something 
 	 * more convenient for internal use, i.e. an Entry.
 	 * @param entryModel The entryModel to convert
 	 * @return The Entry representation of the entryModel.
 	 * */
	public Entry modelToEntry (EntryModel entryModel){
		return new Entry(
				entryModel.getId(),
				entryModel.getName(),
				entryModel.getClient(),
				entryModel.getProject(),
				entryModel.getDescription(),
				entryModel.getStartTime(),
				entryModel.getEndTime());
	}
}
