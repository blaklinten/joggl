package xyz.blaklinten.joggl.Database;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface Repository extends CrudRepository<EntrySchema, Long> {
	List<EntrySchema> findByName (String name);
	List<EntrySchema> findByClient (String client);
	List<EntrySchema> findByProject (String project);
}
