package xyz.blaklinten.joggl;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import xyz.blaklinten.joggl.Models.AccumulatedTime;
import xyz.blaklinten.joggl.Models.NewEntry;
import xyz.blaklinten.joggl.Models.RunningEntry;
import xyz.blaklinten.joggl.Models.StoppedEntry;
import xyz.blaklinten.joggl.Models.Timer;
import xyz.blaklinten.joggl.Models.TimerStatus;

/**
 * This is the web layer of the app.
 * This class handles the REST-communication
 * and makes sure to translate any incoming JSON data
 * into POJO and serialize any results from POJO to JSON.
 * */
@RestController
public class WebController{

	@Autowired
	Joggl joggl;

	/**
 	 * This method is called whenever the controller 
 	 * receives a POST request to the start-timer
 	 * endpoint.
 	 * It translates the JSON data and tries to start a timer
 	 * with the resulting entry.
 	 * @param entry The user data that represents the entry that is to be started.
 	 * @return The resulting entry that was started.
 	 * @throws ResponseStatusException If there already was a timer running.
 	 * */
	@PostMapping("/start-timer")
	public ResponseEntity<RunningEntry> startTimer(@RequestBody NewEntry newEntry){
		try{
			RunningEntry started = joggl.startTimer(newEntry);
			return new ResponseEntity<RunningEntry>(started, HttpStatus.OK);
		}
		catch (Timer.TimerAlreadyRunningException e){
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
	}

	/**
 	 * This method is called whenever the controller 
 	 * receives a GET request to the stop-timer
 	 * endpoint.
 	 * It tries to stop the currently running timer, if any,
 	 * and returns the resulting entry as JSON.
 	 * @return The resulting entry that was stopped.
 	 * @throws ResponseStatusException If there were no timer running.
 	 * */
	@GetMapping("/stop-timer")
	public ResponseEntity<StoppedEntry> stopTimer(){
		try {
			StoppedEntry stopped = joggl.stopTimer();
			return new ResponseEntity<StoppedEntry>(stopped, HttpStatus.OK);
		}
		catch (Timer.NoActiveTimerException e) {
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
	}

	/**
 	 * This method is called whenever the controller 
 	 * receives a GET request to the get-status
 	 * endpoint.
 	 * It fetches the status of the currently running timer, if any,
 	 * and returns the resulting entry as JSON.
 	 * @return The status of the currently running tiemr.
 	 * @throws ResponseStatusException If there were no timer running.
 	 * */
	@GetMapping("/get-status")
	public ResponseEntity<TimerStatus> GetStatus(){
		try {
			TimerStatus status = joggl.getStatus();
 			return new ResponseEntity<TimerStatus>(status, HttpStatus.OK);
		}
		catch (Timer.NoActiveTimerException e){
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
	}

	/**
 	 * This method is called whenever the controller 
 	 * receives a GET request to the sum-entries-by-name
 	 * endpoint.
 	 * It fetches all the entries with the provided name from 
 	 * the database, accumulates their durations 
 	 * and returns the resulting accumulated time as a AccumulatedTime JSON object.
 	 * @param name The name to sum entries by
 	 * @return Time The total accumulated time over all entries with the provided name.
 	 * @throws ResponseStatusException If there were no entries found with the provided name.
 	 * */
	@GetMapping("/sum-entries-by-name")
	public ResponseEntity<AccumulatedTime> SumEntriesByName(@RequestParam String name ){
		try {
			AccumulatedTime totalTime = joggl.sumEntriesbyName(name);
 			return new ResponseEntity<AccumulatedTime>(totalTime, HttpStatus.OK);
		}
		catch (NoSuchElementException e) {
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	/**
 	 * This method is called whenever the controller 
 	 * receives a GET request to the sum-entries-by-client
 	 * endpoint.
 	 * It fetches all the entries with the provided client from 
 	 * the database, accumulates their durations 
 	 * and returns the resulting accumulated time as a AccumulatedTime JSON object.
 	 * @param client The client to sum entries by
 	 * @return Time The total accumulated time over all entries with the provided client.
 	 * @throws ResponseStatusException If there were no entries found with the provided client.
 	 * */
	@GetMapping("/sum-entries-by-client")
	public ResponseEntity<AccumulatedTime> SumEntriesByClient(@RequestParam String client){
		try {
			AccumulatedTime totalTime = joggl.sumEntriesbyClient(client);
 			return new ResponseEntity<AccumulatedTime>(totalTime, HttpStatus.OK);
		}
		catch (NoSuchElementException e) {
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	/**
 	 * This method is called whenever the controller 
 	 * receives a GET request to the sum-entries-by-project
 	 * endpoint.
 	 * It fetches all the entries with the provided project from 
 	 * the database, accumulates their durations 
 	 * and returns the resulting accumulated time as a AccumulatedTime JSON object.
 	 * @param project The project to sum entries by
 	 * @return Time The total accumulated time over all entries with the provided project.
 	 * @throws ResponseStatusException If there were no entries found with the provided project.
 	 * */
	@GetMapping("/sum-entries-by-project")
	public ResponseEntity<AccumulatedTime> SumEntriesByProject(@RequestParam String project){
		try {
			AccumulatedTime totalTime = joggl.sumEntriesbyProject(project);
 			return new ResponseEntity<AccumulatedTime>(totalTime, HttpStatus.OK);
 		} catch (NoSuchElementException e) {
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
