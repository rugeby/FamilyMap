package passoff.DAOTest;

import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.UserDAO;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class UserDAOTest {
  private Database db;
  private User bestUser;
  private User bestUser1;
  private UserDAO uDao;

  @BeforeEach
  public void setUp() throws DataAccessException {
    // Here we can set up any classes or variables we will need for each test
    // lets create a new instance of the Database class
    db = new Database();
    // db.openConnection();
    // and a new user with random data
    bestUser = new User("rugeby", "secret", "rugeby@gmail.com",
            "ruby", "lee", "f", "rugebygo");
    bestUser1 = new User("mattwshumway", "lalala", "matt@gmail.com",
            "Matthew", "Shumway", "m", "mattwshumway");

    // Here, we'll open the connection in preparation for the test case to use it
    Connection conn = db.getConnection();
    //Then we pass that connection to the UserDAO, so it can access the database.
    uDao = new UserDAO(conn);
    //Let's clear the database as well so any lingering data doesn't affect our tests
    uDao.clear();
  }

  @AfterEach
  public void tearDown() {
    db.closeConnection(false);
  }

  @Test
  public void insertPass() throws DataAccessException {
    uDao.insert(bestUser);

    User compareTest = uDao.find(bestUser.getUsername());

    assertNotNull(compareTest);

    assertEquals(bestUser, compareTest);
  }

  @Test
  public void insertFail() throws DataAccessException {
    // Let's do this test again, but this time lets try to make it fail.
    // If we call the method the first time the user will be inserted successfully.
    uDao.insert(bestUser);

    // However, our sql table is set up so that the column "userID" must be unique, so trying to insert
    // the same user again will cause the insert method to throw an exception, and we can verify this
    // behavior by using the assertThrows assertion as shown below.

    // Note: This call uses a lambda function. A lambda function runs the code that comes after
    // the "()->", and the assertThrows assertion expects the code that ran to throw an
    // instance of the class in the first parameter, which in this case is a DataAccessException.
    assertThrows(DataAccessException.class, () -> uDao.insert(bestUser));
  }

  @Test
  public void retrievalPass() throws DataAccessException{
    uDao.insert(bestUser);
    uDao.insert(bestUser1);
    User retrievalTest = uDao.find("rugeby");

    assertEquals(retrievalTest, bestUser);
  }
  @Test
  public void retrievalPass2() throws DataAccessException{
    uDao.insert(bestUser);
    uDao.insert(bestUser1);
    User retrievalTest = uDao.find("mattwshumway");

    assertEquals(retrievalTest, bestUser1);
  }

  @Test
  public void retrievalFail() throws DataAccessException{
    uDao.insert(bestUser);
    uDao.insert(bestUser1);
    User retrievalTest = uDao.find("nobody");
    assertNull(retrievalTest);
  }

  @Test
  public void clearPass() throws DataAccessException{
    uDao.insert(bestUser);
    uDao.insert(bestUser1);
    uDao.clear();
    User clearTest = uDao.find("rugeby");
    assertNull(clearTest);
  }

  @Test
  public void clearPass2() throws DataAccessException{
    uDao.insert(bestUser);
    uDao.insert(bestUser1);
    uDao.clear();
    User clearTest = uDao.find("mattwshumway");
    assertNull(clearTest);
  }


}