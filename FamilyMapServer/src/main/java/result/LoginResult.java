package result;


/**
 * give response after logining
 */

public class LoginResult {
  /**
   * a boolean to check whether successfully respond
   */
  private boolean success;
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
  public LoginResult(boolean success, String authtoken, String username, String personID) {
    this.success = success;
    this.authtoken = authtoken;
    this.username = username;
    this.personID = personID;
  }

  /**
   * Error response
   * @param message
   * @param success
   */
  public LoginResult(String message, boolean success) {
    this.message = message;
    this.success = success;
  }
}
