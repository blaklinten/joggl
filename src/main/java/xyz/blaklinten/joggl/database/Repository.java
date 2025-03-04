package xyz.blaklinten.joggl.database;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * This interface provides an API to the MariaDB-instance in use. By using the functions declared
 * here, a user can fetch entries from the database by calling a method and providing a suitable
 * value.
 */
@Service
public interface Repository extends CrudRepository<EntryDTO, Long> {
  /**
   * This method fetches entries from the database where the NAME-property matches the
   * name-parameter.
   *
   * @param name The name that is to be used as key when fetching entries from the database.
   * @return A (possibly empty) list of entries with a matching name.
   */
  List<EntryDTO> findByName(String name);

  /**
   * This method fetches entries from the database where the CLIENT-property matches the
   * client-parameter.
   *
   * @param client The client that is to be used as key when fetching entries from the database.
   * @return A (possibly empty) list of entries with a matching client.
   */
  List<EntryDTO> findByClient(String client);

  /**
   * This method fetches entries from the database where the PROJECT-property matches the
   * project-parameter.
   *
   * @param project The project that is to be used as key when fetching entries from the database.
   * @return A (possibly empty) list of entries with a matching project.
   */
  List<EntryDTO> findByProject(String project);
}
