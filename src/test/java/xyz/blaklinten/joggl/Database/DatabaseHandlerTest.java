package xyz.blaklinten.joggl.Database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import xyz.blaklinten.joggl.EntryMapper;
import xyz.blaklinten.joggl.Models.NewEntry;
import xyz.blaklinten.joggl.Models.RunningEntry;
import xyz.blaklinten.joggl.Models.StoppedEntry;

/**
 * Unit test for the Databasehandler component.
 */
@SpringBootTest
public class DatabaseHandlerTest {

	@Autowired
	private DatabaseHandler dbHandler;

	private StoppedEntry stopped;
	private StoppedEntry stoppedWithSameName;
	private StoppedEntry stoppedWithDifferentName;

	@BeforeEach
	public void init(){
		dbHandler.repo.deleteAll();

		String client = "The Client";
		String name = "A name";
		String project = "Development";
		String description = "A bunch of tests";

		NewEntry anEntry = new NewEntry(name, client, project, description);
		RunningEntry running = EntryMapper.start(anEntry);
		stopped = EntryMapper.stop(running);

		String client1 = "The Client";
		String name1 = "A name";
		String project1 = "Development";
		String description1 = "This entry has the same name as anEntry";

		NewEntry anEntryWithSameName = new NewEntry(name1, client1, project1, description1);
		RunningEntry runningWithSameName = EntryMapper.start(anEntryWithSameName);
		stoppedWithSameName = EntryMapper.stop(runningWithSameName);

		String client2 = "The Client";
		String name2 = "Another name";
		String project2 = "Development";
		String description2 = "This entry has a different name";

		NewEntry anEntryWithDifferentName = new NewEntry(name2, client2, project2, description2);
		RunningEntry runningWithDifferentName = EntryMapper.start(anEntryWithDifferentName);
		stoppedWithDifferentName = EntryMapper.stop(runningWithDifferentName);
	}

	@Test
	public void saveOneEntryToAndGetByIDFromDatabaseTest(){
		long id = dbHandler.save(EntryMapper.prepareToSave(stopped));

		try{
			StoppedEntry fromDatabase = EntryMapper.entryFromDTO(dbHandler.getEntryByID(id));

			assertTrue(stopped.getName().equals(fromDatabase.getName()));
			assertTrue(stopped.getClient().equals(fromDatabase.getClient()));
			assertTrue(stopped.getProject().equals(fromDatabase.getProject()));
			assertTrue(stopped.getDescription().equals(fromDatabase.getDescription()));
			assertTrue(stopped.getStartTimeAsString().equals(fromDatabase.getStartTimeAsString()));
			assertTrue(stopped.getEndTimeAsString().equals(fromDatabase.getEndTimeAsString()));

		} catch (NoSuchElementException e){
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void saveEntriesToDatabaseAndGetByPropertyTest(){
		dbHandler.save(EntryMapper.prepareToSave(stopped));
		dbHandler.save(EntryMapper.prepareToSave(stoppedWithDifferentName));

		try{
			List<StoppedEntry> fromDatabaseWithName = dbHandler.getEntriesBy(StoppedEntry.Property.NAME, stopped.getName()).
				stream().map(dto -> EntryMapper.entryFromDTO(dto)).collect(Collectors.toList());

			List<StoppedEntry> fromDatabaseWithDifferentName = dbHandler.getEntriesBy(StoppedEntry.Property.NAME, stoppedWithDifferentName.getName()).
				stream().map(dto -> EntryMapper.entryFromDTO(dto)).collect(Collectors.toList());

			List<StoppedEntry> fromDatabaseByProject = dbHandler.getEntriesBy(StoppedEntry.Property.PROJECT, stopped.getProject()).
				stream().map(dto -> EntryMapper.entryFromDTO(dto)).collect(Collectors.toList());

			assertTrue(stopped.getName().equals(fromDatabaseWithName.get(0).getName()));
			assertTrue(stopped.getClient().equals(fromDatabaseWithName.get(0).getClient()));
			assertTrue(stopped.getProject().equals(fromDatabaseWithName.get(0).getProject()));
 		   	assertTrue(stopped.getDescription().equals(fromDatabaseWithName.get(0).getDescription()));
 		   	assertTrue(stopped.getStartTimeAsString().equals(fromDatabaseWithName.get(0).getStartTimeAsString()));
			assertTrue(stopped.getEndTimeAsString().equals(fromDatabaseWithName.get(0).getEndTimeAsString()));

			assertTrue(stoppedWithDifferentName.getName().equals(fromDatabaseWithDifferentName.get(0).getName()));
			assertTrue(stoppedWithDifferentName.getClient().equals(fromDatabaseWithDifferentName.get(0).getClient()));
			assertTrue(stoppedWithDifferentName.getProject().equals(fromDatabaseWithDifferentName.get(0).getProject()));
			assertTrue(stoppedWithDifferentName.getDescription().equals(fromDatabaseWithDifferentName.get(0).getDescription()));
			assertTrue(stoppedWithDifferentName.getStartTimeAsString().equals(fromDatabaseWithDifferentName.get(0).getStartTimeAsString()));
			assertTrue(stoppedWithDifferentName.getEndTimeAsString().equals(fromDatabaseWithDifferentName.get(0).getEndTimeAsString()));
			
			assertTrue(fromDatabaseByProject.size() == 2);

		} catch (NoSuchElementException e){
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void saveMultipleEntriesToDatabaseAndCountTest(){

		dbHandler.save(EntryMapper.prepareToSave(stopped));
		dbHandler.save(EntryMapper.prepareToSave(stoppedWithSameName));
		dbHandler.save(EntryMapper.prepareToSave(stoppedWithDifferentName));

		try{
			List<StoppedEntry> fromDatabaseByName = dbHandler.getEntriesBy(StoppedEntry.Property.NAME, stopped.getName()).
				stream().map(dto -> EntryMapper.entryFromDTO(dto)).collect(Collectors.toList());

			List<StoppedEntry> fromDatabaseAll = new ArrayList<StoppedEntry>();
 		   	dbHandler.repo.findAll().forEach( dto -> {
				fromDatabaseAll.add(EntryMapper.entryFromDTO(dto));
			});

			assertThat(fromDatabaseByName.size()).isEqualTo(2);
			assertThat(fromDatabaseAll.size()).isEqualTo(3);
			

		} catch (NoSuchElementException e){
			System.err.println(e.getMessage());
		}
	}
}
