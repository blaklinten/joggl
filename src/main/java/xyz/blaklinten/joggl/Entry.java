package xyz.blaklinten.joggl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Entry {

	public enum Property {
		NAME,
		CLIENT,
		PROJECT,
		DESCRIPTION,
		STARTTIME,
		ENDTIME;

		@Override
		public String toString(){
			String name;
			switch (ordinal()){
				case 0:
					name = "name";
					break;
				case 1:
					name = "client";
					break;
				case 2:
					name = "project";
					break;
				case 3:
					name = "description";
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

	private long          id;
	private String        name;
	private String        client;
	private String        project;
	private String        description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public Entry(){
		 super();
	 }

	public Entry(
			long id,
 		   	String name,
 		   	String client,
			String project,
 		   	String description,
			String startTime,
 		   	String endTime){
		this.id          = id;
		this.name        = name;
		this.client      = client;
		this.project     = project;
		this.description = description;
		this.startTime   = LocalDateTime.parse(startTime);
		this.endTime     = LocalDateTime.parse(endTime);
	}

	public Entry(
			String name,
 		   	String client,
			String project,
 		   	String description){
		this.name        = name;
		this.client      = client;
		this.project     = project;
		this.description = description;
		this.startTime   = null;
		this.endTime     = null;
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
			case NAME:
 		   		this.setName(value);
			break;

			case CLIENT:
				this.setClient(value);
			break;

			case PROJECT:
				this.setProject(value);
			break;

			case DESCRIPTION:
 		   		this.setDescription(value);
			break;

			default:
 		   		return;
		}
	}

	public void update(Property prop, LocalDateTime value) {
		switch (prop) {
			case STARTTIME:
 			   	this.setStartTime(value);
			break;

			case ENDTIME:
 		   		this.setEndTime(value);
			break;

			default:
 		   		return;
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

	public Duration getDuration() {
		if (endTime != null){
			return Duration.between(startTime, endTime);
		} else {
			return Duration.between(startTime, LocalDateTime.now());
		}
	}

	public static Duration sum(List<Entry> entries) {
		Duration result = Duration.ZERO;
		for(Entry e : entries) {
			result = result.plus(e.getDuration());
		}
		return result;
	}
}
