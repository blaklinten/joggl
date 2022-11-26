package xyz.blaklinten.joggl.database;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class represents a model of an Entry, as seen from te perspective of a user and a database.
 * It is {@link Serializable} and can therefore be used as a template for the Repository as well as
 * the WebController.
 */
@Entity
@Table(name = "entries")
public class EntryDTO implements Serializable {

  /**
   * This method returns the unique ID of this entry.
   *
   * @return The unique ID of this entry.
   */
  public Long getId() {
    return id;
  }

  /**
   * This method returns the client of this entry.
   *
   * @return The client to which this entry belongs.
   */
  public String getClient() {
    return client;
  }

  /**
   * This method returns the description of this entry.
   *
   * @return The detailed description of this entry.
   */
  public String getDescription() {
    return description;
  }

  /**
   * This method returns the name of this entry.
   *
   * @return The name of this entry.
   */
  public String getName() {
    return name;
  }

  /**
   * This method returns the project of this entry.
   *
   * @return The project to which this entry belongs.
   */
  public String getProject() {
    return project;
  }

  /**
   * This method returns the start time of the entry.
   *
   * @return The start time of this entry.
   */
  public String getStartTime() {
    return startTime;
  }

  /**
   * This method returns the end time of the entry.
   *
   * @return The end time of this entry.
   */
  public String getEndTime() {
    return endTime;
  }

  /** The unique ID of this entry. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /** The name of this entry. */
  @Column(name = "name")
  private String name;

  /** The client this entry belongs to. */
  @Column(name = "client")
  private String client;

  /** The project this entry belongs to. */
  @Column(name = "project")
  private String project;

  /** The description of this entry. */
  @Column(name = "description")
  private String description;

  /** The start time of this entry. */
  @Column(name = "startTime")
  private String startTime;

  /** The end time of this entry. */
  @Column(name = "endTime")
  private String endTime;

  /** This is an empty constructor. */
  protected EntryDTO() {}

  /**
   * This constructor creates an EntryDTO based on the necessary information.
   *
   * @param name The name of the entry, can be null.
   * @param client The name of the entry, can be null.
   * @param project The name of the entry, can be null.
   * @param description The name of the entry, can be null.
   * @param startTime The name of the entry, can be null.
   * @param endTime The name of the entry, can be null.
   */
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
