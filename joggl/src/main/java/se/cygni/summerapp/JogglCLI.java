package se.cygni.summerapp;

import java.util.Scanner;

public class JogglCLI
{
	private static Boolean runningTimer = false;
	private static Boolean exiting = false;
	private static Scanner scanner = new Scanner(System.in);

	public static void main (String args[])
	{
		while (!exiting){
			printInfo();
			String nextLine = scanner.nextLine();

			switch (nextLine)
			{
				case "s":
					startActivity();
					break;

				case "e":
					exiting = true;
					break;

				default:
					continue;
			}
		}
		scanner.close();
	}

	private static void printInfo()
	{
		clearScreen();

		System.out.println("======================");
		System.out.println("What do you want to do?");
		System.out.println("(S)tart a new activity");
		System.out.println("St(o)p an already running activity");
		System.out.println("Get the st(a)tus of a running activity");
		System.out.println("======================");
	}

	private static void clearScreen()
	{
		System.out.print("\033[H\033[2J");  
		System.out.flush();
	}

	private static void startActivity()
	{
		
		if (runningTimer)
		{
			clearScreen();
			System.out.println("Already running a timer...");
			return;
		}

		String client = askFor("Client");
		String project = askFor("Project");
		String name = askFor("Name");
		String description = askFor("Description");

		Entry newEntry = new Entry(client, description, name, project);
		createNewEntryEvent(newEntry);
		runningTimer = true;
	}
	
	private static String askFor(String what)
	{
		clearScreen();
		System.out.println(what + "?");
		return scanner.nextLine();
	}

	private static void createNewEntryEvent(Entry entry)
	{
		//Dummy placeholder function
		System.out.println(entry.toString());
		exiting = true;
	}
}
