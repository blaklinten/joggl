package xyz.blaklinten.joggl;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import xyz.blaklinten.joggl.Database.DatabaseHandler;
import xyz.blaklinten.joggl.Models.EntryModel;
import xyz.blaklinten.joggl.Models.AccumulatedTime;
import xyz.blaklinten.joggl.Entry;
import xyz.blaklinten.joggl.Models.TimerStatus;

@Component
public class Joggl {
	private Logger log = LoggerFactory.getLogger(WebController.class);

	@Autowired
	private Timer timer;

	@Autowired
	private DatabaseHandler dbHandler;

	public EntryModel startTimer(EntryModel es){
		log.info("Incoming start request");
		Entry newEntry = new Entry(
				es.getName(),
				es.getClient(),
				es.getProject(),
				es.getDescription());
		try{
			timer.start(newEntry);
		}
		catch (Timer.TimerAlreadyRunningException e){
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
		log.info("Started entry " + newEntry.getName());
		return entryToSchema(newEntry);
	}

	public EntryModel stopTimer(){
		log.info("Incoming stop request");
		Entry stoppedEntry;
		EntryModel stoppedEntryModel;
		try {
			stoppedEntry = timer.stop();
			stoppedEntryModel = entryToSchema(stoppedEntry);
			dbHandler.save(stoppedEntryModel);
		} catch (Timer.NoActiveTimerException e) {
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
		log.info("Stopped entry " + stoppedEntry.getName());
		return stoppedEntryModel;
	}

	public TimerStatus getStatus(){
		log.info("Incoming status request");
		TimerStatus currentStatus;
		try{
			currentStatus = timer.getCurrentStatus();
		} catch (Timer.NoActiveTimerException e){
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
		return currentStatus;
	}

	public AccumulatedTime sumEntriesbyName(String name){
		AccumulatedTime result;
		Duration sum = Duration.ZERO;
		dbHandler.getEntriesBy(Entry.Property.NAME, name).forEach(es -> sum.plus(schemaToEntry(es).getDuration()));
		result = new AccumulatedTime(Entry.Property.NAME.toString(), name, sum);
		return result;
	}

	public AccumulatedTime sumEntriesbyClient(String client){
		AccumulatedTime result;
		Duration sum = Duration.ZERO;
		dbHandler.getEntriesBy(Entry.Property.CLIENT, client).forEach(es -> sum.plus(schemaToEntry(es).getDuration()));
		result = new AccumulatedTime(Entry.Property.CLIENT.toString(), client, sum);
		return result;
	}
	
	public AccumulatedTime sumEntriesbyProject(String project){
		AccumulatedTime result;
		Duration sum = Duration.ZERO;
		dbHandler.getEntriesBy(Entry.Property.PROJECT, project).forEach(es -> sum.plus(schemaToEntry(es).getDuration()));
		result = new AccumulatedTime(Entry.Property.PROJECT.toString(), project, sum);
		return result;
	}

	public EntryModel entryToSchema(Entry e){
		return new EntryModel(
				e.getName(),
				e.getClient(),
				e.getProject(),
				e.getDescription(),
				e.getStartTimeAsString(),
				e.getEndTimeAsString());
	}

	public Entry schemaToEntry (EntryModel es){
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
