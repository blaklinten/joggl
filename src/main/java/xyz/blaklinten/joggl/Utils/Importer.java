package xyz.blaklinten.joggl.Utils;


import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Importer {

	private static Path trackMeBaseDir = Paths.get("/home/blaklinten/importer/");

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

	public static void walkTheFileSystem(Path baseDir) throws IOException {
		ListFiles lf = new ListFiles();
		Files.walkFileTree(trackMeBaseDir, lf);
	}
	
}

class ListFiles extends SimpleFileVisitor<Path> {
	private final int indentionAmount = 3;
	private int indentionLevel;

	private String client;
	private String project;
	private String name;

	private Integer oldDirDepth = 0;
	private DirectoryLevel currentDirectoryLevel = DirectoryLevel.Base;

	private enum DirectoryLevel {
		Base,
		Client,
		Project
	}

	public ListFiles() {
		indentionLevel = 0;
	}

	private void indent() {
		for (int i = 0; i < indentionLevel; i++) {
			System.out.print(' ');
		}
	}

	public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
		indent();
		name = file.getFileName().toString();
		System.out.println("Adding entry " + project + "@" + client + ", " + name);
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult postVisitDirectory(Path directory, IOException e)throws IOException {
		indentionLevel = indentionAmount;
		indent();
		System.out.println("Finished with " + project + "@" + client);
		return FileVisitResult.CONTINUE;
	}

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

	public FileVisitResult visitFileFailed(Path file, IOException exc)throws IOException {
		System.out.println("A file traversal error ocurred");
		return super.visitFileFailed(file, exc);
	}
}
