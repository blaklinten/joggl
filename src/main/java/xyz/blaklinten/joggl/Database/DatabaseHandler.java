package xyz.blaklinten.joggl.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.blaklinten.joggl.Models.Entry;

@Component
public class DatabaseHandler {

	private Logger log = LoggerFactory.getLogger(DatabaseHandler.class);

	@Autowired
	Repository repo;

	public EntrySchema entryToSchema(Entry e){
		return new EntrySchema(
				e.getName(),
				e.getClient(),
				e.getProject(),
				e.getDescription(),
				e.getStartTimeAsString(),
				e.getEndTimeAsString());
	}

	public Entry schemaToEntry (EntrySchema es){
		return new Entry(
				es.getId(),
				es.getName(),
				es.getClient(),
				es.getProject(),
				es.getDescription(),
				es.getStartTime(),
				es.getEndTime());
	}

	public long save(Entry e){
		log.info("Saving entry " + e.getName() + " to database");
		EntrySchema es = repo.save(entryToSchema(e));
		log.info("Got ID " + es.getId() + " from database");
		return es.getId();
	}

	public Entry getEntryByID(long id) throws NoSuchElementException {
		log.info("Searching for entry with ID " + id);
		Optional<EntrySchema> result = repo.findById(id);
		
		if (result.isEmpty()){
			String errorMessage = "No entry with ID " + id + " exists.";
			log.error(errorMessage);
			throw new NoSuchElementException(errorMessage);
		} else {
			EntrySchema es = result.get();
			Entry e = schemaToEntry(es);
			return e;
		}
	}

	public List<Entry> getEntriesBy(Entry.Property prop, String value) throws NoSuchElementException {
	List<EntrySchema> result;
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
			ArrayList<Entry> e = new ArrayList<Entry>();
 		   result.forEach( es -> {
				e.add(schemaToEntry(es));
			});
			return e;
		}
	}
}
