package result;

/**
 * give responses after calling Loading
 */
public class LoadResult {



  /**
   * a boolean to check whether successfully respond
   */
  private boolean success;
  /**
   * message tell user the Error
   */
  private String message;

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public void setMessage(String message) {
    this.message = message;
  }



  /**
   * successfully response
   * @param message
   * @param success
   */


  public LoadResult(boolean success, String message) {
    this.success = success;
    this.message = message;
  }
}
