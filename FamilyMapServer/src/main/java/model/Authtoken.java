package model;

import java.util.Objects;

/**
 * all the data i will use related to Authtoken
 */

public class Authtoken {
  /**
   *Unique authtoken
   */
  private String authtoken;

  private String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
   *Username that is associated with the authtoken
   */

  public Authtoken(String authtoken, String username){
    this.authtoken = authtoken;
    this.username = username;
  }
  public String getAuthtoken() {
    return authtoken;
  }

  public void setAuthtoken(String authtoken) {
    this.authtoken = authtoken;
  }

  @Override
  public String toString() {
    return "Authtoken{" +
            "authtoken='" + authtoken + '\'' +
            ", username='" + username + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Authtoken authtoken1 = (Authtoken) o;
    return authtoken.equals(authtoken1.authtoken) &&
            username.equals(authtoken1.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authtoken, username);
  }
}
