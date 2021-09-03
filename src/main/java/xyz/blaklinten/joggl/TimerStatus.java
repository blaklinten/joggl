package xyz.blaklinten.joggl;

import xyz.blaklinten.joggl.Models.Entry;

public class TimerStatus{

	private long id;
	private String name;
	private String client;
	private String project;
	private String duration;

	protected TimerStatus(Entry currentEntry){
		this.id = currentEntry.getID();
		this.name = currentEntry.getName();
		this.client = currentEntry.getClient();
		this.project = currentEntry.getProject();
		this.duration = currentEntry.getDuration().toString();
	}

	public String getDuration() {
		return duration;
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
