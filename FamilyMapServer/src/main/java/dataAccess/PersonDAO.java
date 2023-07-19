package dataAccess;

import model.Event;
import model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dataAccess.DataAccessException;
import model.User;

/**
 * Get all the data related to Person
 */

public class PersonDAO {
  /**
   * an URL
   */
  private final Connection conn;


  /**
   * @param conn
   */
  public PersonDAO(Connection conn) {
    this.conn = conn;
  }

  /**
   * insert all the data related to this person ID
   * @param person
   * @throws DataAccessException
   */

  public void insert(Person person) throws DataAccessException{
    String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName," +
            "gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
    try(PreparedStatement stmt = conn.prepareStatement(sql)){
      stmt.setString(1, person.getPersonID());
      stmt.setString(2, person.getAssociatedUsername());
      stmt.setString(3, person.getFirstName());
      stmt.setString(4, person.getLastName());
      stmt.setString(5, person.getGender());
      stmt.setString(6, person.getFatherID());
      stmt.setString(7, person.getMotherID());
      stmt.setString(8, person.getSpouseID());

      stmt.executeUpdate();
    }catch(SQLException e){
      e.printStackTrace();
      throw new DataAccessException("Error encountered while inserting a person into database");
    }
  }

  /**
   * find all the info related to this person ID
   * @param personID
   * @return
   * @throws DataAccessException
   */

  public Person find(String personID) throws DataAccessException{
    Person person;
    ResultSet rs;
    String sql = "SELECT * FROM Person WHERE PersonID = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, personID);
      rs = stmt.executeQuery();
      if (rs.next()) {
        person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
        return person;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding an person in the database");
    }
  }

  /**
   * clear all the data
   * @throws DataAccessException
   */
  public void clear() throws DataAccessException {
    String sql = "DELETE FROM Person";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing the person table");
    }
  }


  public void insertPersons(List<Person> persons) throws DataAccessException {
    for (Person person : persons) {
      insert(person);
    }
  }

  //public find array<string>assUsername;

  /**
   * clear Person data by username;
   * @param username
   */
  public void clearPerosnByUsername(String username)throws DataAccessException{
    String sql = "DELETE FROM Person WHERE associatedUsername = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while clearing person events");
    }
  }

  public List<Person>getUserPersons(String username) throws DataAccessException{
    List<Person> family = new ArrayList<Person>();
    Person person;
    ResultSet rs;
    String sql = "SELECT * FROM Person WHERE associatedUsername = ?";
    try(PreparedStatement stmt = conn.prepareStatement(sql)){
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      while (rs.next()) {
        person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
        family.add(person);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding a person in the database");
    }

    return family;

  }

  public Person retrievePerson(String personID) throws DataAccessException {
    Person person;
    ResultSet rs;
    String sql = "SELECT * FROM Person WHERE personID = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, personID);
      rs = stmt.executeQuery();
      if (rs.next()) {
        person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
        return person;
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding a person in the database");
    }
  }


  public List<Person> getAllPersons() throws DataAccessException {
    List<Person> allPersons = new ArrayList<>();
    Person person; // points to each newly created User object
    ResultSet rs;
    String sql = "SELECT * FROM Person;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      rs = stmt.executeQuery();
      while (rs.next()) {
        person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
        allPersons.add(person);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while fetching the Person table");
    }

    if (allPersons.size() > 0) {
      return allPersons;
    }
    return null;
  }





}
