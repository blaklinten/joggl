package xyz.blaklinten.joggl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController{

	@PostMapping("/start-timer")
	public String StartTimer(){
		return "Started";
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

