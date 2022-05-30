package xyz.blaklinten.joggl.Models;

import lombok.Getter;

@Getter
public class TimerStatus {

  private final Long id;
  private final String name;
  private final String client;
  private final String project;
  private final long hours;
  private final long minutes;
  private final long seconds;

  public TimerStatus(Entry currentEntry) {
    this.id = currentEntry.getId();
    this.name = currentEntry.getName();
    this.client = currentEntry.getClient();
    this.project = currentEntry.getProject();
    this.hours = currentEntry.getDuration().toHoursPart();
    this.minutes = currentEntry.getDuration().toMinutesPart();
    this.seconds = currentEntry.getDuration().toSecondsPart();
  }
}
