package se.cygni.summerapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestController
public class App {
	public static void main(String args[]) {
		SpringApplication.run(App.class, args);
	}

	@GetMapping("/test")
	public String testSpring(){
		return "Hello Spring!";
	}
}
