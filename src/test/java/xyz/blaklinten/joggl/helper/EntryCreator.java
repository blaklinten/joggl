package xyz.blaklinten.joggl.helper;

import xyz.blaklinten.joggl.model.Entry;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Random;

public class EntryCreator {
  public static Entry createDefaultEntry() {
    var name = "Client AB";
    var client = "The Client";
    var project = "Test project";
    var description = "This is a test entry";
    var starTime = LocalDateTime.now();
    var endTime = LocalDateTime.now().minusHours(1);
    return new Entry(name, client, project, description, starTime, endTime);
  }

  public static Entry createRandomEntry() {
    byte[] randomNameBytes = new byte[8]; // length is bounded by 8
    new Random().nextBytes(randomNameBytes);
    String randomName = new String(randomNameBytes, StandardCharsets.UTF_8);

    byte[] randomClientBytes = new byte[8]; // length is bounded by 8
    new Random().nextBytes(randomClientBytes);
    String randomClient = new String(randomNameBytes, StandardCharsets.UTF_8);

    byte[] randomProjectBytes = new byte[8]; // length is bounded by 8
    new Random().nextBytes(randomProjectBytes);
    String randomProject = new String(randomNameBytes, StandardCharsets.UTF_8);

    byte[] randomDescriptionBytes = new byte[8]; // length is bounded by 8
    new Random().nextBytes(randomDescriptionBytes);
    String randomDescription = new String(randomNameBytes, StandardCharsets.UTF_8);

    var randomStartOffset = new Random().nextInt();
    var randomStartTime = LocalDateTime.now().minusHours(randomStartOffset);

    var randomEndOffset = new Random().nextInt();
    var randomEndTime = LocalDateTime.now().plusHours(randomEndOffset);

    return new Entry(randomName, randomClient, randomProject, randomDescription, randomStartTime, randomEndTime);
  }
}
