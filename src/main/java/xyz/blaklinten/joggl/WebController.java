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

/**
 * This is the web layer of the app. This class handles the REST-communication and makes sure to
 * translate any incoming JSON data into POJO and serialize any results from POJO to JSON.
 */
@RestController
public class WebController {

    final String ORIGINS = "http://localhost:3000";

  @Autowired Joggl joggl;

  /**
   * This method is called whenever the controller receives a POST request to the start-timer
   * endpoint. It translates the JSON data and tries to start a timer with the resulting entry.
   *
   * @param entry The user data that represents the entry that is to be started.
   * @return The resulting entry that was started.
   * @throws ResponseStatusException If there already was a timer running.
   */
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

  /**
   * This method is called whenever the controller receives a GET request to the stop-timer
   * endpoint. It tries to stop the currently running timer, if any, and returns the resulting entry
   * as JSON.
   *
   * @return The resulting entry that was stopped.
   * @throws ResponseStatusException If there were no timer running.
   */
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

  /**
   * This method is called whenever the controller receives a GET request to the get-status
   * endpoint. It fetches the status of the currently running timer, if any, and returns the
   * resulting entry as JSON.
   *
   * @return The status of the currently running tiemr.
   * @throws ResponseStatusException If there were no timer running.
   */
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

  /**
   * This method is called whenever the controller receives a GET request to the sum-entries-by-name
   * endpoint. It fetches all the entries with the provided name from the database, accumulates
   * their durations and returns the resulting accumulated time as a AccumulatedTime JSON object.
   *
   * @param name The name to sum entries by
   * @return Time The total accumulated time over all entries with the provided name.
   * @throws ResponseStatusException If there were no entries found with the provided name.
   */
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

  /**
   * This method is called whenever the controller receives a GET request to the
   * sum-entries-by-client endpoint. It fetches all the entries with the provided client from the
   * database, accumulates their durations and returns the resulting accumulated time as a
   * AccumulatedTime JSON object.
   *
   * @param client The client to sum entries by
   * @return Time The total accumulated time over all entries with the provided client.
   * @throws ResponseStatusException If there were no entries found with the provided client.
   */
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

  /**
   * This method is called whenever the controller receives a GET request to the
   * sum-entries-by-project endpoint. It fetches all the entries with the provided project from the
   * database, accumulates their durations and returns the resulting accumulated time as a
   * AccumulatedTime JSON object.
   *
   * @param project The project to sum entries by
   * @return Time The total accumulated time over all entries with the provided project.
   * @throws ResponseStatusException If there were no entries found with the provided project.
   */
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
