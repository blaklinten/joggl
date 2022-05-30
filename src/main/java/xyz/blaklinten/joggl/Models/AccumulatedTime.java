package xyz.blaklinten.joggl.Models;

import lombok.Getter;

import java.time.Duration;

@Getter
public class AccumulatedTime {

  private final String commonProperty;
  private final String commonValue;
  private final long hours;
  private final long minutes;
  private final long seconds;

  public AccumulatedTime(String prop, String commonValue, Duration total) {
    this.commonProperty = prop;
    this.commonValue = commonValue;
    this.hours = total.toHoursPart();
    this.minutes = total.toMinutesPart();
    this.seconds = total.toSecondsPart();
  }
}
