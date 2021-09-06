package xyz.blaklinten.joggl.Models;

import java.time.Duration;

public class AccumulatedTime {

	private String commonID;
	private String commonValue;
	private long hours;
	private long minutes;
	private long seconds;

	public String getCommon() {
		return commonID;
	}
	public String getCommonValue() {
		return commonValue;
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

	public AccumulatedTime(String id, String commonValue, Duration total){
		this.commonID = id;
		this.commonValue = commonValue;
		this.hours = total.toHours();
		this.minutes = total.toMinutes();
		this.seconds = total.toSeconds();
	}
}
