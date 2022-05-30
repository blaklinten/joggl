package xyz.blaklinten.joggl.Database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "entries")
public class EntryDTO implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "client")
  private String client;

  @Column(name = "project")
  private String project;

  @Column(name = "description")
  private String description;

  @Column(name = "startTime")
  private String startTime;

  @Column(name = "endTime")
  private String endTime;

  public EntryDTO(
      String name,
      String client,
      String project,
      String description,
      String startTimeAsString,
      String endTimeAsString) {
    EntryDTO.builder()
        .name(name)
        .client(client)
        .project(project)
        .description(description)
        .startTime(startTimeAsString)
        .endTime(endTimeAsString)
        .build();
  }

  // TODO is this needed?
  // protected EntryDTO() {}

  public void updateID(Long newID) {
    this.id = newID;
  }
}
