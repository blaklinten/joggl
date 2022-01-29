package xyz.blaklinten.joggl.Database;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "entries")
public class EntryDTO implements Serializable {

  public Long getId() {
    return id;
  }

  public String getClient() {
    return client;
  }

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  public String getProject() {
    return project;
  }

  public String getStartTime() {
    return startTime;
  }

  public String getEndTime() {
    return endTime;
  }

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

  protected EntryDTO() {}

  public EntryDTO(
      String name,
      String client,
      String project,
      String description,
      String startTime,
      String endTime) {
    this.name = name;
    this.client = client;
    this.project = project;
    this.description = description;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public void updateID(Long newID) {
    this.id = newID;
  }
}
