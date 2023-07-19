package result;

/**
 * get two responses after clear
 */
public class ClearResult {
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
   * @param message
   * @param success
   */
  public ClearResult(boolean success, String message) {
    this.success = success;
    this.message = message;
  }
}
