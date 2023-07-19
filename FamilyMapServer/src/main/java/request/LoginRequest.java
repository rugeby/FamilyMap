package request;


/**
 * send a request when user using username and password
 */
public class LoginRequest {
  /**
   * string username
   */
  private  String username;
  /**
   * string user's password
   */
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
