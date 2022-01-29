package xyz.blaklinten.joggl;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import xyz.blaklinten.joggl.Database.EntryDTO;
import xyz.blaklinten.joggl.Models.AccumulatedTime;
import xyz.blaklinten.joggl.Models.TimerStatus;

@RestController
public class WebController {

  final String ORIGINS = "http://localhost:3000";

  @Autowired Joggl joggl;

  @CrossOrigin(origins = ORIGINS)
  @PostMapping("/start-timer")
  public Mono<EntryDTO> startTimer(@RequestBody EntryDTO entry) {
    try {
      return Mono.just(joggl.startTimer(entry));
    } catch (Timer.TimerAlreadyRunningException e) {
      // TODO Is this a "good" exception to throw? Is there a better way to react when an error
      // occurs?
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
    }
  }

  @CrossOrigin(origins = ORIGINS)
  @GetMapping("/stop-timer")
  public Mono<EntryDTO> stopTimer() {
    try {
      return Mono.fromFuture(joggl.stopTimer());
    } catch (Timer.NoActiveTimerException e) {
      // TODO Is this a "good" exception to throw? Is there a better way to react when an error
      // occurs?
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
    }
  }

  @CrossOrigin(origins = ORIGINS)
  @GetMapping("/get-status")
  public Mono<TimerStatus> GetStatus() {
    try {
      return Mono.just(joggl.getStatus());
    } catch (Timer.NoActiveTimerException e) {
      // TODO Is this a "good" exception to throw? Is there a better way to react when an error
      // occurs?
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
    }
  }

  @CrossOrigin(origins = ORIGINS)
  @GetMapping("/sum-entries-by-name")
  public Mono<AccumulatedTime> SumEntriesByName(@RequestParam String name) {
    try {
      return Mono.fromFuture(joggl.sumEntriesbyName(name));
    } catch (NoSuchElementException e) {
      // TODO Is this a "good" exception to throw? Is there a better way to react when an error
      // occurs?
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  @CrossOrigin(origins = ORIGINS)
  @GetMapping("/sum-entries-by-client")
  public Mono<AccumulatedTime> SumEntriesByClient(@RequestParam String client) {
    try {
      return Mono.fromFuture(joggl.sumEntriesbyClient(client));
    } catch (NoSuchElementException e) {
      // TODO Is this a "good" exception to throw? Is there a better way to react when an error
      // occurs?
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  @CrossOrigin(origins = ORIGINS)
  @GetMapping("/sum-entries-by-project")
  public Mono<AccumulatedTime> SumEntriesByProject(@RequestParam String project) {
    try {
      return Mono.fromFuture(joggl.sumEntriesbyProject(project));
    } catch (NoSuchElementException e) {
      // TODO Is this a "good" exception to throw? Is there a better way to react when an error
      // occurs?
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }
}
