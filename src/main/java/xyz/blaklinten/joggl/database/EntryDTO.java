package xyz.blaklinten.joggl.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@Table(name = "entries")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntryDTO implements Serializable {

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
}
