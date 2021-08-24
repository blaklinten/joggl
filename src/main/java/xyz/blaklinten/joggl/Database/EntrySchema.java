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
public class EntrySchema implements Serializable {

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

	protected EntrySchema(){
	}

	public EntrySchema(String name, String client,
				String project, String description,
				String startTime, String endTime) {
		this.client = client;
		this.description = description;
		this.name = name;
		this.project = project;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
