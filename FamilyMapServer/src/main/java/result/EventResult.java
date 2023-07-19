package result;


import model.Event;

/**
 * give results after response from Event
 */

public class EventResult {
  /**
   * a array stores all data related to Event
   */
  private Event[] data;

  public Event[] getData() {
    return data;
  }

  public void setData(Event[] data) {
    this.data = data;
  }

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
   * @param data
   * @param success
   */

  public EventResult(Event[] data, boolean success) {
    this.data = data;
    this.success = success;
  }

  /**
   * Error response
   * @param success
   * @param message
   */

  public EventResult(boolean success, String message) {
    this.success = success;
    this.message = message;
  }
}
