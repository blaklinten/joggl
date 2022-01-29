package xyz.blaklinten.joggl.Models;

import java.time.Duration;

public class AccumulatedTime {

  private String commonProperty;
  private String commonValue;
  private long hours;
  private long minutes;
  private long seconds;

  public String getCommonProperty() {
    return commonProperty;
  }

  public String getCommonValue() {
    return commonValue;
  }

  public long getHours() {
    return hours;
  }

  public long getMinutes() {
    return minutes;
  }

  public long getSeconds() {
    return seconds;
  }

  public AccumulatedTime(String prop, String commonValue, Duration total) {
    this.commonProperty = prop;
    this.commonValue = commonValue;
    this.hours = total.toHoursPart();
    this.minutes = total.toMinutesPart();
    this.seconds = total.toSecondsPart();
  }
}
