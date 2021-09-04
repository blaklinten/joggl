package xyz.blaklinten.joggl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import xyz.blaklinten.joggl.Database.DatabaseHandler;
import xyz.blaklinten.joggl.Database.EntrySchema;
import xyz.blaklinten.joggl.Models.Entry;

@Component
public class Joggl {
	private Logger log = LoggerFactory.getLogger(WebController.class);

	@Autowired
	private Timer timer;

	@Autowired
	private DatabaseHandler dbHandler;

	public EntrySchema startTimer(EntrySchema es){
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

	public EntrySchema stopTimer(){
		log.info("Incoming stop request");
		Entry stoppedEntry;
		EntrySchema stoppedEntrySchema;
		try {
			stoppedEntry = timer.stop();
			stoppedEntrySchema = entryToSchema(stoppedEntry);
			dbHandler.save(stoppedEntrySchema);
		} catch (Timer.NoActiveTimerException e) {
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
		log.info("Stopped entry " + stoppedEntry.getName());
		return stoppedEntrySchema;
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

	public EntrySchema entryToSchema(Entry e){
		return new EntrySchema(
				e.getName(),
				e.getClient(),
				e.getProject(),
				e.getDescription(),
				e.getStartTimeAsString(),
				e.getEndTimeAsString());
	}

	public Entry schemaToEntry (EntrySchema es){
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
