package xyz.blaklinten.joggl.Database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.blaklinten.joggl.Models.Entry;

@Component
public class DatabaseHandler {

	@Autowired
	private Repository repo;

	public Entry getEntryByName(String name) throws NoSuchEntryException {
		List<EntrySchema> result = repo.findByName(name);
		
		if (result.isEmpty()){
			throw new NoSuchEntryException("No entry with name " + name + " exists.");
		} else {
			return result.get(0).toEntry();
		}
	}

	public class NoSuchEntryException extends Exception{
		public NoSuchEntryException(String errorMessage){
			super(errorMessage);
		}
	}
}
