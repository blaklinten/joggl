package se.cygni.summerapp.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

public class Entry {
	private String client;
	private String description;
	private String name;
	private String project;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	private DateTimeFormatter dateTimeFormat;
	private String format = "yyyy-MM-dd HH:mm:ss a";

	public Entry(String client, String description,
				String name, String project) {
		this.client = client;
		this.description = description;
		this.name = name;
		this.project = project;
		this.startTime = null;
		this.endTime = null;
		this.dateTimeFormat = DateTimeFormatter.ofPattern(format);
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

	public String getStartTimeAsString() {
		return startTime.format(dateTimeFormat);
	}

	private void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public String getEndTimeAsString() {
		return endTime.format(dateTimeFormat);
	}

	private void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String start() {
		LocalDateTime startTime = LocalDateTime.now();
		this.setStartTime(startTime);
		return startTime.format(dateTimeFormat);
	}

	public String stop() {
		LocalDateTime endTime = LocalDateTime.now();
		this.setEndTime(endTime);
		return endTime.format(dateTimeFormat);
	}

	public void update(String property, String value) throws NoSuchElementException{
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

	public void update(String property, LocalDateTime value) throws NoSuchElementException{
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
			"Started at " + getStartTimeAsString();
	}
}

