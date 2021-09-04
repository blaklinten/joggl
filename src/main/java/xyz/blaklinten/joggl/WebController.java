package xyz.blaklinten.joggl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.blaklinten.joggl.Database.EntrySchema;

@RestController
public class WebController{

	@Autowired
	Joggl joggl;

	@PostMapping(
		value = "/start-timer",
		consumes = {MediaType.APPLICATION_JSON_VALUE},
		produces = {MediaType.APPLICATION_JSON_VALUE})

	@ResponseBody
	public EntrySchema startTimer(@RequestBody EntrySchema entry){
		return joggl.startTimer(entry);
	}

	@GetMapping("/stop-timer")
	@ResponseBody
	public EntrySchema stopTimer(){
		return joggl.stopTimer();
	}

	@GetMapping("/get-status")
	@ResponseBody
	public TimerStatus GetStatus(){
		return joggl.getStatus();
	}

	@GetMapping("/sum-entries-by-name")
	public String SumEntriesByName(@RequestParam String name ){
		// return Joggl.sumEntriesby(Entry.Property.NAME, name);
		return "Summed by name";
	}

	@GetMapping("/sum-entries-by-client")
	public String SumEntriesByClient(@RequestParam String client){
		// return Joggl.sumEntriesby(Entry.Property.CLIENT, client);
		return "Summed by client";
	}
	
	@GetMapping("/sum-entries-by-project")
	public String SumEntriesByProject(@RequestParam String project){
		// return Joggl.sumEntriesby(Entry.Property.PROJECT, project);
		return "Summed by project";
	}
}
