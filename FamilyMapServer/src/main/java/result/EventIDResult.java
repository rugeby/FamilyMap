package result;

/**
 *give results after response from EventID
 */

public class EventIDResult {
  /**
   * user's associated name;
   */
  private String associatedUsername;
  /**
   * event ID;
   */
  private String eventID;
  /**
   * user's person ID;
   */
  private String personID;
  /**
   * location's latitude;
   */
  private float latitude;
  /**
   * location's longitude;
   */
  private float longitude;
  /**
   * country
   */
  private String country;

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername = associatedUsername;
  }

  public String getEventID() {
    return eventID;
  }

  public void setEventID(String eventID) {
    this.eventID = eventID;
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

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  /**
   * city name
   */
  private String city;
  /**
   * integar year
   */
  private int year;
  private String eventType;
  /**
   * message tell user the Error
   */
  private String message;
  /**
   * a boolean to check whether successfully respond
   */
  private boolean success;

  /**
   * successfully response
   * @param associatedUsername
   * @param eventID
   * @param personID
   * @param latitude
   * @param longitude
   * @param country
   * @param city
   * @param year
   * @param success
   */
  public EventIDResult(String associatedUsername, String eventID, String personID, float latitude, float longitude, String country, String city, String eventType, int year, boolean success) {
    this.associatedUsername = associatedUsername;
    this.eventID = eventID;
    this.personID = personID;
    this.latitude = latitude;
    this.longitude = longitude;
    this.country = country;
    this.city = city;
    this.eventType = eventType;
    this.year = year;
    this.success = success;
  }


  /**
   * Error response
   * @param message
   * @param success
   */
  public EventIDResult(String message, boolean success) {
    this.message = message;
    this.success = success;
  }
}
