package xyz.blaklinten.joggl.Models;

import xyz.blaklinten.joggl.Entry;

public class TimerStatus{

	private long id;
	private String name;
	private String client;
	private String project;
	private long hours;
	private long minutes;
	private long seconds;

	public TimerStatus(Entry currentEntry){
		this.id = currentEntry.getID();
		this.name = currentEntry.getName();
		this.client = currentEntry.getClient();
		this.project = currentEntry.getProject();
		this.hours = currentEntry.getDuration().toHours();
		this.minutes = currentEntry.getDuration().toMinutes();
		this.seconds = currentEntry.getDuration().toSeconds();
	}

	public long getHours() {
		return hours;
	}

	public long getMinutes() {
		return minutes;
	}

	public long getSeconds() {
		return seconds;
	}

	public String getProject() {
		return project;
	}

	public String getClient() {
		return client;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}
}
