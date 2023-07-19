package result;
/**
 * give response after calling Fill
 */

public class FillResult {
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
   * @param message
   * @param success
   */

  public FillResult(boolean success, String message) {
    this.success = success;
    this.message = message;
  }
}
