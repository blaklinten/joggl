package xyz.blaklinten.joggl.Models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Component
@Builder
@Getter
@Setter
public class Entry {

  public enum Property {
    NAME,

    CLIENT,

    PROJECT,

    DESCRIPTION,

    START_TIME,

    END_TIME;
  }

  private Long id;
  private String name;
  private String client;
  private String project;
  private String description;
  private LocalDateTime startTime;
  private LocalDateTime endTime;

  /** TODO is this needed? public Entry() { super(); } */
  public Entry(
      Long id,
      String name,
      String client,
      String project,
      String description,
      String startTime,
      String endTime) {
    Entry.builder()
        .id(id)
        .name(name)
        .client(client)
        .project(project)
        .description(description)
        .startTime(LocalDateTime.parse(startTime))
        .endTime(LocalDateTime.parse(endTime))
        .build();
  }

  public Entry(String name, String client, String project, String description) {
    Entry.builder()
        .name(name)
        .client(client)
        .project(project)
        .description(description)
        .startTime(null)
        .endTime(null)
        .build();
  }

  public String getStartTimeAsString() {
    if (startTime != null) {
      return startTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    } else {
      return "null";
    }
  }

  public String getEndTimeAsString() {
    if (endTime != null) {
      return endTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    } else {
      return "null";
    }
  }

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
    }
  }

  public void update(Property prop, LocalDateTime value) {
    switch (prop) {
      case START_TIME:
        this.setStartTime(value);
        break;

      case END_TIME:
        this.setEndTime(value);
        break;

      default:
    }
  }

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

  public Duration getDuration() {
    return Duration.between(startTime, Objects.requireNonNullElseGet(endTime, LocalDateTime::now));
  }

  public static Duration sum(List<Entry> entries) {
    Duration result = Duration.ZERO;
    for (Entry e : entries) {
      result = result.plus(e.getDuration());
    }
    return result;
  }
}
