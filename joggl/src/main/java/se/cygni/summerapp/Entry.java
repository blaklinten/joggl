package se.cygni.summerapp;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class Entry {
	private String client;
	private String description;
	private String name;
	private String project;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public Entry(String client, String description,
				String name, String project) {
		this.client = client;
		this.description = description;
		this.name = name;
		this.project = project;
		this.startTime = LocalDateTime.now();
		this.endTime = null;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getClient() {
		return client;
	}

	private void setClient(String client) {
		this.client = client;
	}

	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	public String getProject() {
		return project;
	}

	private void setProject(String project) {
		this.project = project;
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
		return "Name:" + this.name + "\n" +
			"@" + this.project + "\n" +
			this.description + "\n" +
			"for " + this.client + "\n" +
			"Started at " + this.startTime;
	}
}

