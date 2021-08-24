package xyz.blaklinten.joggl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.blaklinten.joggl.Database.Repository;
import xyz.blaklinten.joggl.Models.Entry;

@RestController
public class WebController{

	@Autowired
	private Repository repository;

	@GetMapping("/test")
	public String test(){
		String name = "Lucas", client = "Cygni", description = "Ett test", project = "SummerApp";
		Entry testEntry = new Entry(name, client, project, description);
		testEntry.start();
		testEntry.stop();
		repository.save(testEntry.toEntrySchema());
		return "Hello Spring!";
	}

}

