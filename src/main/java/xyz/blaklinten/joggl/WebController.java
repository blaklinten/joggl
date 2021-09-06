package xyz.blaklinten.joggl;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import xyz.blaklinten.joggl.Models.AccumulatedTime;
import xyz.blaklinten.joggl.Models.EntryModel;
import xyz.blaklinten.joggl.Models.TimerStatus;

@RestController
public class WebController{

	@Autowired
	Joggl joggl;

	@PostMapping("/start-timer")
	@ResponseBody
	public EntryModel startTimer(@RequestBody EntryModel entry){
		try{
			return joggl.startTimer(entry);
		}
		catch (Timer.TimerAlreadyRunningException e){
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
	}

	@GetMapping("/stop-timer")
	@ResponseBody
	public EntryModel stopTimer(){
		try {
			return joggl.stopTimer();
		}
		catch (Timer.NoActiveTimerException e) {
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
	}

	@GetMapping("/get-status")
	@ResponseBody
	public TimerStatus GetStatus(){
		try {
 			return joggl.getStatus();
		}
		catch (Timer.NoActiveTimerException e){
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
	}

	@GetMapping("/sum-entries-by-name")
	public AccumulatedTime SumEntriesByName(@RequestParam String name ){
		try {
 			return joggl.sumEntriesbyName(name);
		}
		catch (NoSuchElementException e) {
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/sum-entries-by-client")
	public AccumulatedTime SumEntriesByClient(@RequestParam String client){
		try {
 			return joggl.sumEntriesbyClient(client);
		}
		catch (NoSuchElementException e) {
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/sum-entries-by-project")
	public AccumulatedTime SumEntriesByProject(@RequestParam String project){
		try {
 			return joggl.sumEntriesbyProject(project);
 		} catch (NoSuchElementException e) {
			// TODO Is this a "good" exception to throw? Is there a better way to react when an error occurs?
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
