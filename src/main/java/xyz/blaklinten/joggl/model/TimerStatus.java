package xyz.blaklinten.joggl.model;

/**
 * This class is used to represent the status of a running timer. From this, a user can get all the
 * necessary information about the entry that is currently running on the timer.
 */
public class TimerStatus {

  private Long id;
  private String name;
  private String client;
  private String project;
  private long hours;
  private long minutes;
  private long seconds;

  /**
   * This constructor will create a Timerstatus from a currently running entry by extracting the
   * necessary information and calculating the current duration in a more presentable way.
   *
   * @param currentEntry The entry that will serve as a base for the status.
   */
  public TimerStatus(Entry currentEntry) {
    this.id = currentEntry.getID();
    this.name = currentEntry.getName();
    this.client = currentEntry.getClient();
    this.project = currentEntry.getProject();
    this.hours = currentEntry.getDuration().toHoursPart();
    this.minutes = currentEntry.getDuration().toMinutesPart();
    this.seconds = currentEntry.getDuration().toSecondsPart();
  }

  /**
   * This method returns the hours-portion of the duration of the currently running entry.
   *
   * @return The hours-portion of the duration of the currently running entry.
   */
  public long getHours() {
    return hours;
  }

  /**
   * This method returns the minutes-portion of the duration of the currently running entry.
   *
   * @return The minutes-portion of the duration of the currently running entry.
   */
  public long getMinutes() {
    return minutes;
  }

  /**
   * This method returns the seconds-portion of the duration of the currently running entry.
   *
   * @return The seconds-portion of the duration of the currently running entry.
   */
  public long getSeconds() {
    return seconds;
  }

  /**
   * This method returns the project of the currently running entry.
   *
   * @return The project of the currently running entry.
   */
  public String getProject() {
    return project;
  }

  /**
   * This method returns the client of the currently running entry.
   *
   * @return The client of the currently running entry.
   */
  public String getClient() {
    return client;
  }

  /**
   * This method returns the name of the currently running entry.
   *
   * @return The name of the currently running entry.
   */
  public String getName() {
    return name;
  }

  /**
   * This method returns the ID of the currently running entry.
   *
   * @return The ID of the currently running entry.
   */
  public Long getId() {
    return id;
  }
}
