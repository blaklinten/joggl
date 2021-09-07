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

@Component
public class Joggl {
	private Logger log = LoggerFactory.getLogger(Joggl.class);

	@Autowired
	private Timer timer;

	@Autowired
	private DatabaseHandler dbHandler;

	public EntryModel startTimer(EntryModel es) throws Timer.TimerAlreadyRunningException {
		log.info("Incoming start request");

		Entry newEntry = new Entry(
				es.getName(),
				es.getClient(),
				es.getProject(),
				es.getDescription());

		timer.start(newEntry);

		log.info("Started entry " + newEntry.getName());

		return entryToModel(newEntry);
	}

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

	public void resetTimer(){
		timer.reset();
	}

	public TimerStatus getStatus() throws Timer.NoActiveTimerException{
		log.info("Incoming status request");

		return timer.getCurrentStatus();
	}

	public AccumulatedTime sumEntriesbyName(String name) throws NoSuchElementException{
		log.info("Incoming sum-by-name request");

		return calculateSum(Entry.Property.NAME, name);

	}

	public AccumulatedTime sumEntriesbyClient(String client) throws NoSuchElementException {
		log.info("Incoming sum-by-client request");

		return calculateSum(Entry.Property.CLIENT, client);

	}
	
	public AccumulatedTime sumEntriesbyProject(String project) throws NoSuchElementException {
		log.info("Incoming sum-by-project request");

		return calculateSum(Entry.Property.PROJECT, project);
	}

	private AccumulatedTime calculateSum(Entry.Property prop, String value){
			Duration sum = dbHandler.getEntriesBy(prop, value)
			.stream().map(em -> modelToEntry(em).getDuration())
			.reduce(Duration.ZERO, (acc, current) -> acc = acc.plus(current));

			return new AccumulatedTime(prop.toString(), value, sum);
	}

	public EntryModel entryToModel(Entry e){

		return new EntryModel(
				e.getName(),
				e.getClient(),
				e.getProject(),
				e.getDescription(),
				e.getStartTimeAsString(),
				e.getEndTimeAsString());
	}

	public Entry modelToEntry (EntryModel es){
		return new Entry(
				es.getId(),
				es.getName(),
				es.getClient(),
				es.getProject(),
				es.getDescription(),
				es.getStartTime(),
				es.getEndTime());
	}
}
