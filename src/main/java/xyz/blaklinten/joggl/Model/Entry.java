package xyz.blaklinten.joggl.Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * This class is the internal representation of an Entry. It contains not only information about an
 * entry, but also some logic.
 */
@Component
public class Entry {

  /** This enum represents the different properties, or fields, an entry has. */
  public enum Property {
    /** The name of an Entry. */
    NAME,

    /** The client, to which an Entry belongs. */
    CLIENT,

    /** The project, to which an Entry belongs. */
    PROJECT,

    /** The description of an Entry. */
    DESCRIPTION,

    /** The start time of an Entry. */
    STARTTIME,

    /** The end time of an Entry. */
    ENDTIME;

    /**
     * This method allows a user to use the actual name, represented by a String, of a property
     * instead of the corresponding ordinal.
     */
    @Override
    public String toString() {
      String name;
      switch (ordinal()) {
        case 0:
          name = "name";
          break;
        case 1:
          name = "client";
          break;
        case 2:
          name = "project";
          break;
        case 3:
          name = "description";
          break;
        case 4:
          name = "start time";
          break;
        case 5:
          name = "end time";
          break;
        default:
          name = "";
          break;
      }
      return name;
    }
  }

  private Long id;
  private String name;
  private String client;
  private String project;
  private String description;
  private LocalDateTime startTime;
  private LocalDateTime endTime;

  /** A constructor used when creating an Entry without parameters. */
  public Entry() {
    super();
  }

  /**
   * This method allows a user to create a Entry when the ID is known and all information are
   * avaliable as Strings (exckluding the ID). This is very handy when creating Entries after a
   * succesful query to the database, as the database returns objects of type EntryDTO.
   *
   * @param id The ID of the entry.
   * @param name The name of the entry.
   * @param client The client of the entry.
   * @param project The project of the entry.
   * @param description The description of the entry.
   * @param startTime The start time of the entry.
   * @param endTime The end time of the entry.
   */
  public Entry(
      Long id,
      String name,
      String client,
      String project,
      String description,
      String startTime,
      String endTime) {
    this.id = id;
    this.name = name;
    this.client = client;
    this.project = project;
    this.description = description;
    this.startTime = LocalDateTime.parse(startTime);
    this.endTime = LocalDateTime.parse(endTime);
  }

  /**
   * This method allows a user to create an entry without having all the internal and final
   * information. This is useful when a user want to create an entry for the first time and only has
   * access to limited information.
   *
   * @param name The name of the entry.
   * @param client The client of the entry.
   * @param project The project of the entry.
   * @param description The description of the entry.
   */
  public Entry(String name, String client, String project, String description) {
    this.name = name;
    this.client = client;
    this.project = project;
    this.description = description;
    this.startTime = null;
    this.endTime = null;
  }

  /**
   * This method returns the ID of the current entry.
   *
   * @return The ID of the current entry.
   */
  public Long getID() {
    return id;
  }

  /**
   * This method returns the name of the current entry.
   *
   * @return The name of the current entry.
   */
  public String getName() {
    return name;
  }

  /**
   * This method sets the name of the current entry.
   *
   * @param String The name of the current entry.
   */
  private void setName(String name) {
    this.name = name;
  }

  /**
   * This method returns the client of the current entry.
   *
   * @return String The client of the current entry.
   */
  public String getClient() {
    return client;
  }

  /**
   * This method sets the client of the current entry.
   *
   * @param String The client of the current entry.
   */
  private void setClient(String client) {
    this.client = client;
  }

  /**
   * This method returns the description of the current entry.
   *
   * @return The description of the current entry.
   */
  public String getDescription() {
    return description;
  }

  /**
   * This method sets the description of the current entry.
   *
   * @param String The description of the current entry.
   */
  private void setDescription(String description) {
    this.description = description;
  }

  /**
   * This method returns the project of the current entry.
   *
   * @return The project of the current entry.
   */
  public String getProject() {
    return project;
  }

  /**
   * This method sets the project of the current entry.
   *
   * @param String The project of the current entry.
   */
  private void setProject(String project) {
    this.project = project;
  }

  /**
   * This method returns the start time of the current entry.
   *
   * @return Time The start time of the current entry.
   */
  public LocalDateTime getStartTime() {
    return startTime;
  }

  /**
   * This method returns the start time of the current entry, as a string.
   *
   * @return The start time of the current entry.
   */
  public String getStartTimeAsString() {
    if (startTime != null) {
      return startTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    } else {
      return "null";
    }
  }

  /**
   * This method sets the start time of the current entry.
   *
   * @param startTime The start time of the current entry.
   */
  private void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  /**
   * This method returns the end time of the current entry.
   *
   * @return The end time of the current entry.
   */
  public LocalDateTime getEndTime() {
    return endTime;
  }

  /**
   * This method returns the end time of the current entry, as a string.
   *
   * @return The end time of the current entry.
   */
  public String getEndTimeAsString() {
    if (endTime != null) {
      return endTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    } else {
      return "null";
    }
  }

  /**
   * This method sets the end time of the current entry.
   *
   * @param endTime The end time of the current entry.
   */
  private void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  /**
   * This method updates the given property of the current entry with the given value.
   *
   * @param prop The property to update.
   * @param value The value to update to.
   */
  public void update(Property prop, String value) {
    switch (prop) {
      case NAME:
        this.setName(value);
        break;

      case CLIENT:
        this.setClient(value);
        break;

      case PROJECT:
        this.setProject(value);
        break;

      case DESCRIPTION:
        this.setDescription(value);
        break;

      default:
        return;
    }
  }

  /**
   * This method updates the given property of the current entry with the given value.
   *
   * @param prop The property to update.
   * @param value The value to update to.
   */
  public void update(Property prop, LocalDateTime value) {
    switch (prop) {
      case STARTTIME:
        this.setStartTime(value);
        break;

      case ENDTIME:
        this.setEndTime(value);
        break;

      default:
        return;
    }
  }

  /** This method allows a user to get a nice string representation of the current entry. */
  @Override
  public String toString() {
    return "Name:"
        + this.name
        + "\n"
        + "@"
        + this.project
        + "\n"
        + this.description
        + "\n"
        + "for "
        + this.client
        + "\n"
        + "Started at "
        + getStartTimeAsString()
        + "\n"
        + "Ended at "
        + getEndTimeAsString()
        + "\n";
  }

  /**
   * This method returns the duration of the current entry. If the entry is still running the result
   * is the difference between the start time and now. If the entry is already stopped, the duration
   * is calculated as the difference between the start and end times.
   *
   * @return The Duration of the current entry.
   */
  public Duration getDuration() {
    if (endTime != null) {
      return Duration.between(startTime, endTime);
    } else {
      return Duration.between(startTime, LocalDateTime.now());
    }
  }

  /**
   * This method is used to sum the durations of all entries in a given list.
   *
   * @param entries The list of entries, which durations is to be accumulated.
   * @return The total duration of the entries in the given list.
   */
  public static Duration sum(List<Entry> entries) {
    Duration result = Duration.ZERO;
    for (Entry e : entries) {
      result = result.plus(e.getDuration());
    }
    return result;
  }
}
