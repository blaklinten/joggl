package xyz.blaklinten.joggl.Database;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.blaklinten.joggl.Entry;
import xyz.blaklinten.joggl.Models.EntryModel;

@Component
public class DatabaseHandler {

	private Logger log = LoggerFactory.getLogger(DatabaseHandler.class);

	@Autowired
	Repository repo;

	public long save(EntryModel entryToSave){
		log.info("Saving entry " + entryToSave.getName() + " to database");

		EntryModel savedEntry = repo.save(entryToSave);

		log.info("Got ID " + savedEntry.getId() + " from database");
		return savedEntry.getId();
	}

	public EntryModel getEntryByID(long id) throws NoSuchElementException {
		log.info("Searching for entry with ID " + id);

		Optional<EntryModel> result = repo.findById(id);
		
		if (result.isEmpty()){
			String errorMessage = "No entry with ID " + id + " exists.";

			log.error(errorMessage);
			throw new NoSuchElementException(errorMessage);
		} else {
			return result.get();
		}
	}

	public List<EntryModel> getEntriesBy(Entry.Property prop, String value) throws NoSuchElementException {
	List<EntryModel> result;

		switch(prop){
			case NAME:
				result = repo.findByName(value);
			break;
			case CLIENT:
				result = repo.findByClient(value);
			break;
			case PROJECT:
				result = repo.findByProject(value);
			break;
			default:
				String errorMessage = "Invalid property";
				log.error(errorMessage);
				throw new NoSuchElementException(errorMessage);
		}
		if (result.isEmpty()){
			String errorMessage = "No entry with " + prop.toString() + " " + value + " exists.";

			log.error(errorMessage);
			throw new NoSuchElementException(errorMessage);
		} else {
			return result;
		}
	}
}
