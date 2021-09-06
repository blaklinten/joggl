package xyz.blaklinten.joggl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
		EntryModel startedEntry = joggl.startTimer(entry);
		return startedEntry;
	}

	@GetMapping("/stop-timer")
	@ResponseBody
	public EntryModel stopTimer(){
		EntryModel stoppedEntry = joggl.stopTimer();
		return stoppedEntry;
	}

	@GetMapping("/get-status")
	@ResponseBody
	public TimerStatus GetStatus(){
		TimerStatus status = joggl.getStatus();
		return status;
	}

	@GetMapping("/sum-entries-by-name")
	public AccumulatedTime SumEntriesByName(@RequestParam String name ){
		AccumulatedTime totalTime = joggl.sumEntriesbyName(name);
		return totalTime;
	}

	@GetMapping("/sum-entries-by-client")
	public AccumulatedTime SumEntriesByClient(@RequestParam String client){
		AccumulatedTime totalTime = joggl.sumEntriesbyClient(client);
		return totalTime;
	}
	
	@GetMapping("/sum-entries-by-project")
	public AccumulatedTime SumEntriesByProject(@RequestParam String project){
		AccumulatedTime totalTime = joggl.sumEntriesbyProject(project);
		return totalTime;
	}
}
