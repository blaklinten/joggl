package xyz.blaklinten.joggl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import xyz.blaklinten.joggl.Database.DatabaseHandler;
import xyz.blaklinten.joggl.Database.EntrySchema;
import xyz.blaklinten.joggl.Models.Entry;

@RestController
public class WebController{

	private Logger log = LoggerFactory.getLogger(WebController.class);

	@Autowired
	private Timer timer;

	@Autowired
	private DatabaseHandler dbHandler;

	@PostMapping(
		value = "/start-timer",
		consumes = {MediaType.APPLICATION_JSON_VALUE},
		produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseBody
	public EntrySchema StartTimer(@RequestBody Entry entry){
		try{
			log.info("Incoming start request");
			timer.start(entry);
		}
		catch (Timer.TimerAlreadyRunningException e){
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
		log.info("Started entry " + entry.getName());
		return dbHandler.entryToSchema(entry);
	}

	@GetMapping("/stop-timer")
	@ResponseBody
	public EntrySchema StopTimer(){
		Entry stoppedEntry;
		try {
			log.info("Incoming stop request");
			stoppedEntry = timer.stop();
			dbHandler.save(stoppedEntry);
		} catch (Timer.NoActiveTimerException e) {
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
		log.info("Stopped entry " + stoppedEntry.getName());
		return dbHandler.entryToSchema(stoppedEntry);
	}

	@GetMapping("/get-status")
	@ResponseBody
	public TimerStatus GetStatus(){
		log.info("Incoming status request");
		TimerStatus currentStatus;
		try{
			currentStatus = timer.getCurrentStatus();
		} catch (Timer.NoActiveTimerException e){
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
		return currentStatus;
	}

	@GetMapping("/sum-entries-by-name")
	public String SumEntriesByName(){
		return "Summed by name";
	}

	@GetMapping("/sum-entries-by-client")
	public String SumEntriesByClient(){
		return "Summed by client";
	}
	
	@GetMapping("/sum-entries-by-project")
	public String SumEntriesByProject(){
		return "Summed by project";
	}
}

