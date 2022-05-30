package xyz.blaklinten.joggl.Utils;

import xyz.blaklinten.joggl.Models.*;

public class JogglCLI {
  /*
  private static Boolean exiting = false;
  private static Scanner scanner = new Scanner(System.in);
  private static Entry entry = null;

  public static void main (String args[]) {
  	while (!exiting){
  		printInfo();
  		String userInput = scanner.nextLine();

  		switch (userInput) {
  			case "s":
  				startActivity();
  				break;

  			case "o":
  				stopActivity();
  				break;

  			case "a":
  				getStatusOfActivity();
  				break;

  			case "e":
  				exiting = true;
  				break;

  			default:
  				clearScreen();
  				System.out.println("No such choice, yet...");
  				continue;
  		}
  	}
  	scanner.close();
  }

  private static void printInfo() {
  	clearScreen();

  	System.out.println("======================");
  	System.out.println("What do you want to do?");
  	System.out.println("s: Start a new activity");
  	System.out.println("o: Stop an already running activity");
  	System.out.println("a: Get the status of a running activity");
  	System.out.println("e: Exit the program");
  	System.out.println("======================");
  }

  private static void clearScreen() {
  	System.out.print("\033[H\033[2J");
  	System.out.flush();
  }

  private static void startActivity() {

  	if (entry != null) {
  		clearScreen();
  		System.out.println("Already running a timer...");
  		askFor("Ok");
  		return;
  	}

  	String client = askFor("Client");
  	String project = askFor("Project");
  	String name = askFor("Name");
  	String description = askFor("Description");

  	entry = new Entry(client, description, name, project);
  	createNewEntryEvent(entry);
  	askFor("Ok");
  }

  private static void stopActivity() {
  	if (entry == null) {
  		clearScreen();
  		System.out.println("No running timer...");
  		askFor("Ok");
  		return;
  	}
  	clearScreen();
  	System.out.println(entry.toString() + "This entry is now ended");
  	createStoppedEntryEvent(entry);
  	entry = null;
  	askFor("Ok");
  }

  private static void getStatusOfActivity() {
  	clearScreen();

  	if (entry == null) {
  		System.out.println("No running activity...");
  		askFor("Ok");
  		return;
  	}

  	Long runningTime = Duration.between(entry.getStartTime(), LocalDateTime.now()).toSeconds();
  	System.out.println("Entry " + entry.getName() + " has been running for " + runningTime + " seconds");
  	askFor("Ok");
  }

  private static String askFor(String what) {
  	System.out.println(what + "?");
  	return scanner.nextLine();
  }

  private static void createStoppedEntryEvent(Entry entry) {
  	//Dummy placeholder function
  	clearScreen();
  	System.out.println(entry.toString());
  	exiting = true;
  }

  private static void createNewEntryEvent(Entry entry) {
  	//Dummy placeholder function
  	clearScreen();
  	System.out.println(entry.toString());
  }
  */
}
