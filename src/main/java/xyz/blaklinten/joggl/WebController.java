package xyz.blaklinten.joggl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.blaklinten.joggl.Models.Entry;

@RestController
public class WebController{

	@Autowired private Timer timer;

	@PostMapping(
		value = "/start-timer",
		consumes = {MediaType.APPLICATION_JSON_VALUE})

	public String StartTimer(@RequestBody Entry entry){
		try{
		timer.start(entry);
		}
		catch (Timer.TimerAlreadyRunningException e){
			return e.getMessage();
		}
		return "Started " + entry.getName() + " at time " + entry.getStartTimeAsString();
	}

	@GetMapping("/stop-timer")
	public String StopTimer(){
		return "Stopped";
	}

	@GetMapping("/get-status")
	public String GetStatus(){
		return "Maybe running";
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

