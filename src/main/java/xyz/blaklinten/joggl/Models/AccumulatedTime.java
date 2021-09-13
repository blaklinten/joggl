package xyz.blaklinten.joggl.Models;

import java.time.Duration;

/**
 * This class holds the result of adding the duration of several entries. It is a convenient way of
 * presenting the relevant data to the next layer. The entries has been chosen with respect to one
 * property they have in common, for instance Entry.Property.CLIENT if the entries belong to the
 * same client.
 */
public class AccumulatedTime {

  private String commonProperty;
  private String commonValue;
  private long hours;
  private long minutes;
  private long seconds;

  /**
   * This method returns the common property (ID) that the entries used to accumulate this duration
   * share.
   *
   * @return The name of the common property.
   */
  public String getCommonProperty() {
    return commonProperty;
  }

  /**
   * This method returns the value of the common property (ID) that the entries used to accumulate
   * this duration share.
   *
   * @return The value of the common property.
   */
  public String getCommonValue() {
    return commonValue;
  }

  /**
   * This method returns the number of hours in this accumulated time.
   *
   * @return The number of hours in this accumulated time.
   */
  public long getHours() {
    return hours;
  }

  /**
   * This method returns the number of minutes in this accumulated time.
   *
   * @return The number of minutes in this accumulated time.
   */
  public long getMinutes() {
    return minutes;
  }

  /**
   * This method returns the number of seconds in this accumulated time.
   *
   * @return The number of seconds in this accumulated time.
   */
  public long getSeconds() {
    return seconds;
  }

  /**
   * This constructor creates a valid accumulated time from a common property, its actual value and
   * a total duration.
   *
   * @param prop The common property shared by the corresponding entries.
   * @param commonValue The value of the common property shared by the corresponding entries.
   * @param total The Duration-object containing the total time for the entries.
   */
  public AccumulatedTime(String prop, String commonValue, Duration total) {
    this.commonProperty = prop;
    this.commonValue = commonValue;
    this.hours = total.toHoursPart();
    this.minutes = total.toMinutesPart();
    this.seconds = total.toSecondsPart();
  }
}
