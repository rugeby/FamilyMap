package dataAccess;

import model.Event;
import model.User;

import java.sql.*;
import java.util.List;

import dataAccess.DataAccessException;

/**
 * get all the related data from user
 */
public class UserDAO {
  /**
   *an url
   */
  private final Connection conn;

  /**
   * constructor
   * @param conn
   */
  public UserDAO(Connection conn){
    this.conn = conn;
  }


  /**
   * create user
   * @param user
   */


 public void insert(User user) throws DataAccessException{
    String sql = "INSERT INTO User (username, password, email, firstName, lastName," +
            "gender, personID) VALUES(?,?,?,?,?,?,?)";
    try(PreparedStatement stmt = conn.prepareStatement(sql)){
      stmt.setString(1,user.getUsername());
      stmt.setString(2, user.getPassword());
      stmt.setString(3, user.getEmail());
      stmt.setString(4, user.getFirstName());
      stmt.setString(5, user.getLastName());
      stmt.setString(6, user.getGender());
      stmt.setString(7, user.getPersonID());

      stmt.executeUpdate();
    }catch(SQLException e){
      e.printStackTrace();
      throw new DataAccessException("Error encountered while inserting a user into database");
    }

  };


  public User find(String username) throws DataAccessException {
    User user;
    ResultSet rs;
    String sql = "SELECT * FROM User WHERE username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = new User(rs.getString("username"), rs.getString("password"),
                rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                rs.getString("gender"), rs.getString("personID"));
        return user;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an user in the database");
    }

  }


  public void clear() throws DataAccessException {
    String sql = "DELETE FROM User";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing the user table");
    }
  }


  public void insertUsers(List<User> users) throws DataAccessException {
    for (User user : users) {
      insert(user);
    }
  }


  /**
   * check if username and password is validate
   * @param username
   * @param password
   * @return
   */
  boolean validate(String username, String password){ return false;}

  User getUserById(String userID){return null;}

  public User retrieveUser(String username) throws DataAccessException {
    User user;
    ResultSet rs;
    String sql = "SELECT * FROM User WHERE username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = new User(rs.getString("username"), rs.getString("password"),
                rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                rs.getString("gender"), rs.getString("personID"));
        return user;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding a user in the database");
    }
  }



}
