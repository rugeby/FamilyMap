package result;

/**
 * give response after calling register
 */

public class RegisterResult {
  public String getAuthtoken() {
    return authtoken;
  }

  public void setAuthtoken(String authtoken) {
    this.authtoken = authtoken;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID = personID;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * a unique string
   */
  private String authtoken;
  /**
   * user's name
   */
  private String username;
  /**
   * user's personID
   */
  private String personID;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

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
   * @param authtoken
   * @param username
   * @param personID
   * @param success
   */

  public RegisterResult(String authtoken, String username, String personID, boolean success) {
    this.authtoken = authtoken;
    this.username = username;
    this.personID = personID;
    this.success = success;
  }

  /**
   * Error response
   * @param message
   * @param success
   */
  public RegisterResult(boolean success, String message) {
    this.success = success;
    this.message = message;
  }
}
