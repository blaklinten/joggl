package xyz.blaklinten.joggl.Database;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import xyz.blaklinten.joggl.Models.EntryModel;

@Service
public interface Repository extends CrudRepository<EntryModel, Long> {
	List<EntryModel> findByName (String name);
	List<EntryModel> findByClient (String client);
	List<EntryModel> findByProject (String project);
}
