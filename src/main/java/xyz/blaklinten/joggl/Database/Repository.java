package xyz.blaklinten.joggl.Database;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface Repository extends CrudRepository<EntryDTO, Long> {
  List<EntryDTO> findByName(String name);

  List<EntryDTO> findByClient(String client);

  List<EntryDTO> findByProject(String project);
}
