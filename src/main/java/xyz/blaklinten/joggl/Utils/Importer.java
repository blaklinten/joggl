package xyz.blaklinten.joggl.Utils;


import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * This class is used to import already existing entries into the database.
 * The existing entries must be saved according to the schema used in the trackMe-project found at
 * https://github.com/blaklinten/trackme.
 * */
public class Importer {

	private static Path trackMeBaseDir = Paths.get("/home/blaklinten/importer/");

	/**
 	 * This is the starting point of the Importer-module
 	 * @param args No user argument supported at this time.
 	 * */
	public static void main (String[] args) {

	/*
		try {
			walkTheFileSystem(trackMeBaseDir);
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	*/
	}

	/**
 	 * This method will, given a starting point, traverse the file system. 
 	 * @param baseDir The starting point from which the program will traverse the file system.
 	 * @throws IOException If the path is not reachable.
 	 * */
	public static void walkTheFileSystem(Path baseDir) throws IOException {
		ListFiles lf = new ListFiles();
		Files.walkFileTree(baseDir, lf);
	}
	
}

/**
 * This class is used to list all files in the file system.
 * By listing all files (and folders) the program can gather the necessary information
 * to create EntryModel entries to save to the database.
 * */
class ListFiles extends SimpleFileVisitor<Path> {
	private final int indentionAmount = 3;
	private int indentionLevel;

	private String client;
	private String project;
	private String name;

	private Integer oldDirDepth = 0;
	private DirectoryLevel currentDirectoryLevel = DirectoryLevel.Base;

	/**
 	 * An enum that is used to keep track on what
 	 * EntryModel-property the current directory represent.
 	 * */
	private enum DirectoryLevel {
		Base,
		Client,
		Project
	}

	/**
 	 * Public constructor that returns a ListFiles-instance
 	 * with indentionLevel set to 0.
 	 * */
	public ListFiles() {
		indentionLevel = 0;
	}

	/**
 	 * A method to change the indent of the output.
 	 * */
	private void indent() {
		for (int i = 0; i < indentionLevel; i++) {
			System.out.print(' ');
		}
	}

	/**
 	 * This method is run for each file in the file system tree.
 	 * @param file The current file that are being handled.
 	 * @param attributes The file attributes of the current file
 	 * @return This result signals whether or not to continue with the traversal.
 	 * */
	public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
		indent();
		name = file.getFileName().toString();
		System.out.println("Adding entry " + project + "@" + client + ", " + name);
		return FileVisitResult.CONTINUE;
	}

	/**
 	 * This method is run whenever a directory is fully explored
 	 * and it is time to take a step up in the directory tree.
 	 * @param directory The directory that we are now leaving.
 	 * @param e Any error that has been thrown.
 	 * @return This result signals whether or not to continue with the traversal.
 	 * */
	public FileVisitResult postVisitDirectory(Path directory, IOException e)throws IOException {
		indentionLevel = indentionAmount;
		indent();
		System.out.println("Finished with " + project + "@" + client);
		return FileVisitResult.CONTINUE;
	}

	/**
 	 * This method is run before we start exploring a new directory.
 	 * @param directory The new directory that is to be explored.
 	 * @param attributes The attributes of the new directory.
 	 * @return This result signals whether or not to continue with the traversal.
 	 * */
	public FileVisitResult preVisitDirectory(Path directory,BasicFileAttributes attributes) throws IOException {
		indent();
		Integer newDirDepth = directory.getNameCount();
		if (currentDirectoryLevel == DirectoryLevel.Base){
			currentDirectoryLevel = DirectoryLevel.Client;
			oldDirDepth = newDirDepth;
			return FileVisitResult.CONTINUE;
		} else if (newDirDepth >= oldDirDepth && currentDirectoryLevel == DirectoryLevel.Client) {
			currentDirectoryLevel = DirectoryLevel.Project;
			client = directory.getFileName().toString();
			System.out.println("Setting client to " + client);
		} else if (newDirDepth >= oldDirDepth && currentDirectoryLevel == DirectoryLevel.Project) {
			project = directory.getFileName().toString();
			System.out.println("Setting project to " + project);
		} else if (directory.getNameCount() < oldDirDepth && currentDirectoryLevel == DirectoryLevel.Project) {
			client = directory.getFileName().toString();
			System.out.println("Setting Client to " + client);
		}

		oldDirDepth = newDirDepth;
		System.out.println();
		indentionLevel += indentionAmount;
		return FileVisitResult.CONTINUE;
	}

	/**
 	 * This metod is run if, for some reason, the exploring of a file or directory has failed.
 	 * @param file The file that caused the failure.
 	 * @param exc The exception that was caused.
 	 * @return This result signals whether or not to continue with the traversal.
 	 * */
	public FileVisitResult visitFileFailed(Path file, IOException exc)throws IOException {
		System.out.println("A file traversal error ocurred");
		return super.visitFileFailed(file, exc);
	}
}
