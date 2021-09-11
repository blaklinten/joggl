package xyz.blaklinten.joggl;

import xyz.blaklinten.joggl.Models.NewEntry;
import xyz.blaklinten.joggl.Models.RunningEntry;
import xyz.blaklinten.joggl.Models.StoppedEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import xyz.blaklinten.joggl.Models.EntryDTO;

/**
 * This class is used to convert between the different
 * Entry classes.
 * */
public class EntryMapper {


	/**
 	 * This method prepares an EntrtyDTO to be used
 	 * internally by creating a StoppedEntry instance.
 	 * @return The savable Data Transfer Object
 	 * */
	public static StoppedEntry entryFromDTO(EntryDTO dto){
		NewEntry stage1 = new NewEntry(
							dto.getName(),
							dto.getClient(),
							dto.getProject(),
							dto.getDescription());

		RunningEntry stage2 = new RunningEntry(
				stage1,
				LocalDateTime.parse(
					dto.getStartTime(),
 					DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		StoppedEntry stage3 = new StoppedEntry(
				stage2,
				LocalDateTime.parse(
					dto.getEndTime(),
 				   	DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		return stage3;
	}

	/**
 	 * This method prepares a StoppedEntry to be saved
 	 * by creating an EntryDTO instance.
 	 * @return The savable Data Transfer Object
 	 * */
	public static EntryDTO prepareToSave(StoppedEntry entry){
		return new EntryDTO(
				entry.getName(),
				entry.getClient(),
				entry.getProject(),
				entry.getDescription(),
				entry.getStartTimeAsString(),
				entry.getEndTimeAsString());
	}

	/**
 	 * This method implements the action of starting
 	 * a NewEntry by creating a RunningEntry from the given NewEntry.
 	 * */
	public static RunningEntry start(NewEntry entry){
		return new RunningEntry(
				entry,
				LocalDateTime.now());
	}
				
	/**
 	 * This method implements the action of stopping
 	 * a RunningEntry by creating a StoppedEntry from the given RunningEntry.
 	 * */
	public static StoppedEntry stop(RunningEntry entry){
		return new StoppedEntry(
				entry,
				LocalDateTime.now());

	}
}
