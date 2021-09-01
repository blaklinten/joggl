package xyz.blaklinten.joggl.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Entry {

	public enum Property {
		CLIENT,
		DESCRIPTION,
		NAME,
		PROJECT,
		STARTTIME,
		ENDTIME;

		@Override
		public String toString(){
			String name = "";
			switch (ordinal()){
				case 0:
					name = "client";
					break;
				case 1:
					name = "description";
					break;
				case 2:
					name = "name";
					break;
				case 3:
					name = "project";
					break;
				case 4:
					name = "start time";
					break;
				case 5:
					name = "end time";
					break;
				default:
					name = "";
					break;
			}
			return name;
		}
	}

	private long id;
	private String client;
	private String description;
	private String name;
	private String project;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public Entry(long id, String name, String client,
				String project, String description,
				String startTime, String endTime) {
		this.id = id;
		this.client = client;
		this.description = description;
		this.name = name;
		this.project = project;
		this.startTime = LocalDateTime.parse(startTime);
		this.endTime = LocalDateTime.parse(endTime);
	}

	public Entry(String name, String client,
				String project, String description) {
		this.client = client;
		this.description = description;
		this.name = name;
		this.project = project;
		this.startTime = null;
		this.endTime = null;
	}

	public long getID() {
		return id;
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
		return startTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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
			return endTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		} else {
			return "null";
		}
	}

	private void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
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
			"Started at " + getStartTimeAsString() + "\n" + 
			"Ended at " + getEndTimeAsString() + "\n";
	}
}

