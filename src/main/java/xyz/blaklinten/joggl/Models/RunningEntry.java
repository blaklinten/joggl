package xyz.blaklinten.joggl.Models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is the internal representation of an Entry.
 * It contains not only information about an entry,
 * but also some logic.
 * */
public class RunningEntry extends NewEntry {

	private LocalDateTime startTime;

	/**
 	 * This constructor creates a RunningEntry with the given
 	 * NewEntry and start time as basis.
 	 * @param entry The NewEntry to use as basis for this RunningEntry.
 	 * @param startTime The start time of the entry.
 	 * */
	public RunningEntry(
			NewEntry entry,
			LocalDateTime startTime)
	{
		super(
				entry.getName(),
				entry.getClient(),
				entry.getProject(),
				entry.getDescription());

		this.startTime = startTime;
	}

	/**
 	 * This method returns the start time of the current entry.
 	 * @return Time The start time of the current entry.
 	 * */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
 	 * This method returns the start time of the current entry, as a string.
 	 * @return The start time of the current entry.
 	 * */
	public String getStartTimeAsString() {
		return startTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	/**
 	 * This method sets the start time of the current entry.
 	 * @param startTime The start time of the current entry.
 	 * */
	private void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}


	/**
 	 * This method returns the duration of the current entry.
 	 * @return The Duration of the current entry.
 	 * */
	public Duration getDuration() {
		return Duration.between(startTime, LocalDateTime.now());
	}

	/**
 	 * This method updates the start time of the
 	 * current entry with the given value.
 	 * @param value The value to update to.
 	 * */
	public void updateStartTime(LocalDateTime value) {
		setStartTime(value);
	}
}
