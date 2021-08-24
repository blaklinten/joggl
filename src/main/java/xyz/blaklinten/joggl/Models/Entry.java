package xyz.blaklinten.joggl.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import xyz.blaklinten.joggl.Database.*;

public class Entry {

	public enum Property {
		CLIENT,
		DESCRIPTION,
		NAME,
		PROJECT,
		STARTTIME,
		ENDTIME
	}

	private String client;
	private String description;
	private String name;
	private String project;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	private DateTimeFormatter dateTimeFormat;
	private String format = "yyyy-MM-dd HH:mm:ss a";

	public Entry(String name, String client,
				String project, String description) {
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
		if (startTime != null){
		return startTime.format(dateTimeFormat);
		} else {
			return "null";
		}
	}

	private void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public String getEndTimeAsString() {
		if (endTime != null){
			return endTime.format(dateTimeFormat);
		} else {
			return "null";
		}
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

	public void update(Property prop, String value) {
		switch (prop) {
			case CLIENT: this.setClient(value);
			break;

			case PROJECT: this.setProject(value);
			break;

			case DESCRIPTION: this.setDescription(value);
			break;

			case NAME: this.setName(value);
			break;

			default: return;
		}
	}

	public void update(Property prop, LocalDateTime value) {
		switch (prop) {
			case STARTTIME: this.setStartTime(value);
			break;

			case ENDTIME: this.setEndTime(value);
			break;

			default: return;
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

	public EntrySchema toEntrySchema(){
		return new EntrySchema(
				this.name,
				this.client,
				this.project,
				this.description,
				this.getStartTimeAsString(),
				this.getEndTimeAsString());
	}
}

