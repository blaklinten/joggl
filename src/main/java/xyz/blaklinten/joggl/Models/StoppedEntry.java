package xyz.blaklinten.joggl.Models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is the internal representation of an Entry.
 * It contains not only information about an entry,
 * but also some logic.
 * */
public class StoppedEntry extends RunningEntry {

	private LocalDateTime endTime;

	/**
 	 * This method allows a user to create a StoppedEntry
 	 * by adding a start time to an existing RunningEntry.
 	 * @param entry The RunningEntry to use as basis for this new StoppedEntry.
 	 * @param endTime The start time of the entry.
 	 * */
	public StoppedEntry(RunningEntry entry, LocalDateTime endTime){
		super(
				entry,
				entry.getStartTime()
				);

 	   	this.endTime   = endTime;
	}

	/**
 	 * This method returns the end time of the current entry.
 	 * @return The end time of the current entry.
 	 * */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
 	 * This method returns the end time of the current entry, as a string.
 	 * @return The end time of the current entry.
 	 * */
	public String getEndTimeAsString() {
		return endTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	/**
 	 * This method sets the end time of the current entry.
 	 * @param endTime The end time of the current entry.
 	 * */
	private void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	
	/**
 	 * This method updates the end time of the
 	 * current entry with the given value.
 	 * @param value The value to update to.
 	 * */
	public void updateEndTime(LocalDateTime value) {
		setEndTime(value);
	}


	/**
 	 * This method returns the duration of the current entry.
 	 * @return The Duration of the current entry.
 	 * */
	public Duration getDuration() {
		return Duration.between(getStartTime(), getEndTime());
	}
}
