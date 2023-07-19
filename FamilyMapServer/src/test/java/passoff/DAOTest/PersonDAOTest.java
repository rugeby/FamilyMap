package passoff.DAOTest;

import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDAO;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class PersonDAOTest {
  private Database db;
  private Person bestPerson;
  private Person bestPerson1;
  private PersonDAO pDao;

  @BeforeEach
  public void setUp() throws DataAccessException {
    // Here we can set up any classes or variables we will need for each test
    // lets create a new instance of the Database class
    db = new Database();
    // db.openConnection();
    // and a new user with random data
    bestPerson = new Person("mattwshumway", "shumbb", "Matthew",
            "Shumway", "m", "MrStu", "MrsBecky", "rugeby");
    bestPerson1 = new Person("rugeby", "ruge", "Ruby",
            "Lee", "f", "ZhenL", "Rosalind", "json");

    // Here, we'll open the connection in preparation for the test case to use it
    Connection conn = db.getConnection();

    pDao = new PersonDAO(conn);
    //Let's clear the database as well so any lingering data doesn't affect our tests
    pDao.clear();
  }

  @AfterEach
  public void tearDown() {
    db.closeConnection(false);
  }

  @Test
  public void insertPass() throws DataAccessException {
    pDao.insert(bestPerson);

    Person compareTest = pDao.find(bestPerson.getPersonID());

    assertNotNull(compareTest);

    assertEquals(bestPerson, compareTest);
  }

  @Test
  public void insertFail() throws DataAccessException {
    // Let's do this test again, but this time lets try to make it fail.
    // If we call the method the first time the user will be inserted successfully.
    pDao.insert(bestPerson);

    // However, our sql table is set up so that the column "userID" must be unique, so trying to insert
    // the same user again will cause the insert method to throw an exception, and we can verify this
    // behavior by using the assertThrows assertion as shown below.

    // Note: This call uses a lambda function. A lambda function runs the code that comes after
    // the "()->", and the assertThrows assertion expects the code that ran to throw an
    // instance of the class in the first parameter, which in this case is a DataAccessException.
    assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
  }

  @Test
  public void retrievalPass() throws DataAccessException{
    pDao.insert(bestPerson);
    pDao.insert(bestPerson1);
    Person retrievalTest = pDao.find("mattwshumway");

    assertEquals(retrievalTest, bestPerson);
  }
  @Test
  public void retrievalPass2() throws DataAccessException{
    pDao.insert(bestPerson);
    pDao.insert(bestPerson1);
    Person retrievalTest = pDao.find("rugeby");

    assertEquals(retrievalTest, bestPerson1);
  }

  @Test
  public void retrievalFail() throws DataAccessException{
    pDao.insert(bestPerson);
    pDao.insert(bestPerson1);
    Person retrievalTest = pDao.find("nobody");
    assertNull(retrievalTest);
  }

  @Test
  public void clearPass() throws DataAccessException{
    pDao.insert(bestPerson);
    pDao.insert(bestPerson1);
    pDao.clear();
    Person clearTest = pDao.find("rugeby");
    assertNull(clearTest);
  }

  @Test
  public void clearPass2() throws DataAccessException{
    pDao.insert(bestPerson);
    pDao.insert(bestPerson1);
    pDao.clear();
    Person clearTest = pDao.find("mattwshumway");
    assertNull(clearTest);
  }




}