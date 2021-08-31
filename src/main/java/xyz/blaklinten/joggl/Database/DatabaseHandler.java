package xyz.blaklinten.joggl.Database;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.blaklinten.joggl.Models.Entry;

@Component
public class DatabaseHandler {

	@Autowired
	private Repository repo;

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
		Entry result = new Entry(
				es.getId(),
				es.getName(),
				es.getClient(),
				es.getProject(),
				es.getDescription(),
				es.getStartTime(),
				es.getEndTime());
		return result;
	}

	public void save(Entry e){
		repo.save(entryToSchema(e));
	}

	public Entry getEntryByName(String name) throws NoSuchEntryException {
		List<EntrySchema> result = repo.findByName(name);
		
		if (result.isEmpty()){
			throw new NoSuchEntryException("No entry with name " + name + " exists.");
		} else {
			EntrySchema es = result.get(0);
			Entry e = schemaToEntry(es);
			return e;
		}
	}

	public class NoSuchEntryException extends Exception{
		public NoSuchEntryException(String errorMessage){
			super(errorMessage);
		}
	}
}
