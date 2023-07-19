package result;

import model.Person;

/**
 * give responses from calling Person
 */

public class PersonResult {
  /**
   * stored all the data related to the Person
   */
  private Person[] data;

  public Person[] getData() {
    return data;
  }

  public void setData(Person[] data) {
    this.data = data;
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
   * a boolean to check whether successfully respond
   */
  private boolean success;

  /**
   * message tell user the Error
   */
  private String message;

  /**
   * successfully response
   * @param data
   * @param success
   */

  public PersonResult(Person[] data, boolean success) {
    this.data = data;
    this.success = success;
  }

  /**
   * error response
   * @param success
   * @param message
   */

  public PersonResult(boolean success, String message) {
    this.success = success;
    this.message = message;
  }
}
