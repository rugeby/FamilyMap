package model;

import java.util.Objects;

/**
 * all variable I will use related to Event
 */

public class Event {
  /**
   * Unique identifier for this event
   */
  private String eventID;
  /**
   * Username of user to which this event belongs
   */
  private String associatedUsername;
  /**
   * ID of person to which this event belongs
   */
  private String personID;
  /**
   * Latitude of event’s location
   */
  private float latitude;
  /**
   *Longitude of event’s location
   */
  private float longitude;
  /**
   *Country in which event occurred
   */
  private String country;
  /**
   *City in which event occurred
   */
  private String city;
  /**
   *Type of event
   */
  private String eventType;
  /**
   *Year in which event occurred
   */
  private int year;

  public Event(String eventID, String associatedUsername, String personID, float latitude,
               float longitude, String country, String city, String eventType, int year){
    this.eventID = eventID;
    this.associatedUsername = associatedUsername;
    this.personID = personID;
    this.latitude = latitude;
    this.longitude = longitude;
    this.country = country;
    this.city = city;
    this.eventType = eventType;
    this.year = year;
  }

  public String getEventID() {
    return eventID;
  }

  public void setEventID(String eventID) {
    this.eventID = eventID;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername = associatedUsername;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID = personID;
  }

  public float getLatitude() {
    return latitude;
  }

  public void setLatitude(float latitude) {
    this.latitude = latitude;
  }

  public float getLongitude() {
    return longitude;
  }

  public void setLongitude(float longitude) {
    this.longitude = longitude;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return "Event{" +
            "eventID='" + eventID + '\'' +
            ", associatedUsername='" + associatedUsername + '\'' +
            ", personID='" + personID + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", country='" + country + '\'' +
            ", city='" + city + '\'' +
            ", eventType='" + eventType + '\'' +
            ", year=" + year +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Event event = (Event) o;
    return Float.compare(event.latitude, latitude) == 0 &&
            Float.compare(event.longitude, longitude) == 0 &&
            year == event.year && eventID.equals(event.eventID) &&
            associatedUsername.equals(event.associatedUsername) &&
            personID.equals(event.personID) && country.equals(event.country) &&
            city.equals(event.city) && eventType.equals(event.eventType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);
  }
}
