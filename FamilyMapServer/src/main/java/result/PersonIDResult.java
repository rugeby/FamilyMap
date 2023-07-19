package result;

import model.Person;

/**
 * Returns the single Person object with the specified ID (if the person is associated with the current user). The current user is determined by the provided authtoken.
 */

public class PersonIDResult {
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

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getFatherID() {
    return fatherID;
  }

  public void setFatherID(String fatherID) {
    this.fatherID = fatherID;
  }

  public String getMotherID() {
    return motherID;
  }

  public void setMotherID(String motherID) {
    this.motherID = motherID;
  }

  public String getSpouseID() {
    return spouseID;
  }

  public void setSpouseID(String spouseID) {
    this.spouseID = spouseID;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * user's associated username;
   */
  private String associatedUsername;
  /**
   * user's personID;
   */
  private String personID;
  /**
   * user's first name;
   */
  private String firstName;
  /**
   * user's last name;
   */
  private String lastName;
  /**
   * user's gender, f/m
   */
  private String gender;
  /**
   * user father's ID
   */
  private String fatherID;
  /**
   * user mother's ID
   */
  private String motherID;
  /**
   * user spouse's ID
   */
  private String spouseID;
  /**
   * a boolean to check whether successfully respond
   */
  private boolean success;
  /**
   * message tell user the Error
   */
  private String message;

  /**
   * successfully response
   * @param associatedUsername
   * @param personID
   * @param firstName
   * @param lastName
   * @param gender
   * @param fatherID
   * @param motherID
   * @param spouseID
   * @param success
   */
  public PersonIDResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, boolean success) {
    this.associatedUsername = associatedUsername;
    this.personID = personID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.fatherID = fatherID;
    this.motherID = motherID;
    this.spouseID = spouseID;
    this.success = success;
  }

  /**
   * error response
   * @param success
   * @param message
   */
  public PersonIDResult(boolean success, String message) {
    this.success = success;
    this.message = message;
  }
}
