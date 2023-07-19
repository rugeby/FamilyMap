package dataAccess;

import model.Authtoken;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dataAccess.DataAccessException;
import model.Event;

/**
 *get all the data related to Authtoken
 */

public class AuthtokenDAO {
  /**
   * an url
   */


  private final Connection conn;
  public AuthtokenDAO(Connection conn){
    this.conn = conn;
  }

  /**
   * insert all the data related to this unique authtoken
   * @param authtoken
   * @throws DataAccessException
   */
  public void insert(Authtoken authtoken) throws  DataAccessException{
    String sql = "INSERT INTO Authtoken (authtoken, username) VALUES(?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, authtoken.getAuthtoken());//
      stmt.setString(2, authtoken.getUsername());//

      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while inserting an AuthToken into the database");
    }
  }

  /**
   * find all the info related to this unique authtoken
   * @param authtoken
   * @return
   * @throws DataAccessException
   */

  public Authtoken find(String authtoken) throws DataAccessException{
    Authtoken authToken;
    ResultSet rs;
    String sql = "SELECT * FROM Authtoken WHERE authtoken = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, authtoken);
      rs = stmt.executeQuery();
      if (rs.next()) {
        authToken= new Authtoken(rs.getString("authtoken"), rs.getString("username"));
        return authToken;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an authtoken in the database");
    }
  }

  /**
   * clear all the data
   * @throws DataAccessException
   */
  public void clear() throws DataAccessException{
    String sql = "DELETE FROM Authtoken";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing the authtoken table");
    }

  }


   public Authtoken retrieveAuthToken(String authtoken) throws DataAccessException {
    Authtoken authToken;
    ResultSet rs;
    String sql = "SELECT * FROM AuthToken WHERE authtoken = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, authtoken);
      rs = stmt.executeQuery();
      if (rs.next()) {
        authToken = new Authtoken(rs.getString("authtoken"), rs.getString("username"));
        return authToken;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an authToken in the database");
    }
  }


  public String findAuthToken(String username) throws DataAccessException {
    String authtoken;
    ResultSet rs;
    String sql = "SELECT * FROM AuthToken WHERE username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        authtoken = rs.getString("authtoken");
        return authtoken;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an authToken in the database");
    }
  }

  public void updateAuthToken(Authtoken authToken) throws DataAccessException {
    String sql = "UPDATE AuthToken SET authtoken = ? WHERE username = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, authToken.getAuthtoken());
      stmt.setString(2, authToken.getUsername());

      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while updating an authtoken in the database");
    }
  }

  public List<Authtoken> getAllAuthTokens() throws DataAccessException {
    List<Authtoken> allAuthTokens = new ArrayList<>();
    Authtoken authToken; // points to each newly created AuthToken object
    ResultSet rs;
    String sql = "SELECT * FROM AuthToken;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      rs = stmt.executeQuery();
      while (rs.next()) {
        authToken = new Authtoken(rs.getString("authtoken"), rs.getString("username"));
        allAuthTokens.add(authToken);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while fetching the AuthToken table");
    }

    if (allAuthTokens.size() > 0) {
      return allAuthTokens;
    }
    return null;
  }







}
