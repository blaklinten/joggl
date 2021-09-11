package xyz.blaklinten.joggl.Models;

/**
 * This class is the internal representation of a NewEntry.
 * It contains not only information about an entry,
 * but also some logic.
 * */
public class NewEntry {

	private long          id;
	private String        name;
	private String        client;
	private String        project;
	private String        description;

	/**
 	 * This enum represents the different properties,
 	 * or fields, an entry has.
 	 * */
	public static enum Property {
		/**
 		 * The name of an Entry.
 		 * */
		NAME,

		/**
 		 * The client, to which an Entry belongs.
 		 * */
		CLIENT,

		/**
 		 * The project, to which an Entry belongs.
 		 * */
		PROJECT,

		/**
 		 * The description of an Entry.
 		 * */
		DESCRIPTION,

		/**
 		 * The start time of an Entry.
 		 * */
		STARTTIME,

		/**
 		 * The end time of an Entry.
 		 * */
		ENDTIME;

		/**
 		 * This method allows a user to use the actual name, represented by a String,
 		 * of a property instead of the corresponding ordinal.
 		 * */
		@Override
		public String toString(){
			String name;
			switch (ordinal()){
				case 0:
					name = "name";
					break;
				case 1:
					name = "client";
					break;
				case 2:
					name = "project";
					break;
				case 3:
					name = "description";
					break;
				case 4:
					name = "start time";
					break;
				case 5:
					name = "end time";
					break;
				default:
					name = "";
					break;
			}
			return name;
		}
	}

	/**
 	 * A constructor
 	 * */
	public NewEntry(){
		super();
	}

	/**
 	 * This constructor allows a user to create an entry.
 	 * It is used a user want to create an entry for the first time
 	 * and only has access to limited information.
 	 * @param name The name of the entry.
 	 * @param client The client of the entry.
 	 * @param project The project of the entry.
 	 * @param description The description of the entry.
 	 * */
	public NewEntry(
			String name,
 		   	String client,
			String project,
 		   	String description){
		this.name        = name;
		this.client      = client;
		this.project     = project;
		this.description = description;
	}

	/**
 	 * This method returns the ID of the current entry.
 	 * @return The ID of the current entry.
 	 * */
	public long getID() {
		return id;
	}

	/**
 	 * This method returns the name of the current entry.
 	 * @return The name of the current entry.
 	 * */
	public String getName() {
		return name;
	}

	/**
 	 * This method sets the name of the current entry.
 	 * @param String The name of the current entry.
 	 * */
	private void setName(String name) {
		this.name = name;
	}

	/**
 	 * This method returns the client of the current entry.
 	 * @return String The client of the current entry.
 	 * */
	public String getClient() {
		return client;
	}

	/**
 	 * This method sets the client of the current entry.
 	 * @param String The client of the current entry.
 	 * */
	private void setClient(String client) {
		this.client = client;
	}

	/**
 	 * This method returns the description of the current entry.
 	 * @return The description of the current entry.
 	 * */
	public String getDescription() {
		return description;
	}

	/**
 	 * This method sets the description of the current entry.
 	 * @param String The description of the current entry.
 	 * */
	private void setDescription(String description) {
		this.description = description;
	}

	/**
 	 * This method returns the project of the current entry.
 	 * @return The project of the current entry.
 	 * */
	public String getProject() {
		return project;
	}

	/**
 	 * This method sets the project of the current entry.
 	 * @param String The project of the current entry.
 	 * */
	private void setProject(String project) {
		this.project = project;
	}

	/**
 	 * This method updates the given property of the
 	 * current entry with the given value.
 	 * @param prop The property to update.
 	 * @param value The value to update to.
 	 * */
	public void update(Property prop, String value) {
		switch (prop) {
			case NAME:
 		   		this.setName(value);
			break;

			case CLIENT:
				this.setClient(value);
			break;

			case PROJECT:
				this.setProject(value);
			break;

			case DESCRIPTION:
 		   		this.setDescription(value);
			break;

			default:
 		   		return;
		}
	}

	/**
 	 * This method allows a user to get a nice string representation
 	 * of the current entry.
 	 * */
	@Override
	public String toString() {
		return "Name: " + this.name + "\n" +
			"@" + this.project + "\n" +
			this.description + "\n" +
			"for " + this.client + "\n";
	}
}
