package xyz.blaklinten.joggl.Models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "entries")
public class EntryModel implements Serializable {

	public long getId() {
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
	private long id;


	@Column(name = "client")
	private String client;

	@Column(name = "description")
	private String description;

	@Column(name = "name")
	private String name;

	@Column(name = "project")
	private String project;

	@Column(name = "startTime")
	private String startTime;

	@Column(name = "endTime")
	private String endTime;

	protected EntryModel(){
	}

	public EntryModel(
			String name,
 		   	String client,
			String project,
 		   	String description,
			String startTime,
 		   	String endTime){
		this.name        = name;
		this.client      = client;
		this.project     = project;
		this.description = description;
		this.startTime   = startTime;
		this.endTime     = endTime;
	}
}
