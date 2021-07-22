package se.cygni.summerapp;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class Entry {
	private String Client;
	private String Description;
	private String Name;
	private String Project;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public Entry(String client, String description,
				String name, String project) {
		this.Client = client;
		this.Description = description;
		this.Name = name;
		this.Project = project;
		this.startTime = LocalDateTime.now();
		this.endTime = null;
	}

	public String getName() {
		return Name;
	}

	private void setName(String name) {
		Name = name;
	}

	public String getClient() {
		return Client;
	}

	private void setClient(String client) {
		Client = client;
	}

	public String getDescription() {
		return Description;
	}

	private void setDescription(String description) {
		Description = description;
	}

	public String getProject() {
		return Project;
	}

	private void setProject(String project) {
		Project = project;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	private void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	private void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public LocalDateTime stopEntry() {
		LocalDateTime endTime = LocalDateTime.now();
		this.setEndTime(endTime);
		return endTime;
	}

	public void update(String property, String value) {
		switch (property) {
			case "Client": this.setClient(value);
			break;

			case "Project": this.setProject(value);
			break;

			case "Description": this.setDescription(value);
			break;

			case "Name": this.setName(value);
			break;

			default: throw new NoSuchElementException("No such property: \"" + property + "\"");
		}
	}

	public void update(String property, LocalDateTime value) {
		switch (property) {
			case "StartTime": this.setStartTime(value);
			break;

			case "EndTime": this.setEndTime(value);
			break;

			default: throw new NoSuchElementException("No such property: \"" + property + "\"");
		}
	}

	@Override
	public String toString() {
		return "Name:" + this.Name + "\n" +
			"@" + this.Project + "\n" +
			this.Description + "\n" +
			"for " + this.Client + "\n" +
			"Started at " + this.startTime;
	}
}

