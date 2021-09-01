package xyz.blaklinten.joggl;

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
import xyz.blaklinten.joggl.Models.Entry;

@RestController
public class WebController{

	@Autowired
	private Timer timer;

	@Autowired
	private DatabaseHandler dbHandler;

	@PostMapping(
		value = "/start-timer",
		consumes = {MediaType.APPLICATION_JSON_VALUE},
		produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseBody
	public Entry StartTimer(@RequestBody Entry entry){
		try{
		timer.start(entry);
		}
		catch (Timer.TimerAlreadyRunningException e){
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
		return entry;
	}

	@GetMapping("/stop-timer")
	@ResponseBody
	public Entry StopTimer(){
		Entry stoppedEntry;
		try {
			stoppedEntry = timer.stop();
			dbHandler.save(stoppedEntry);
		} catch (Timer.NoActiveTimerException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
		}
		return stoppedEntry;
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

