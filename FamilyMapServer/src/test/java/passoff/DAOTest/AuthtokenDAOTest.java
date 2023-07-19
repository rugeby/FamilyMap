package passoff.DAOTest;

import dataAccess.AuthtokenDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import model.Authtoken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthtokenDAOTest {

  private Database db;
  private Authtoken firstAuthToken;
  private Authtoken secondAuthToken;
  private AuthtokenDAO aDao;

  @BeforeEach
  public void setUp() throws DataAccessException {

    db = new Database();
    // and a new AuthToken with random data
    firstAuthToken = new Authtoken("abc123&987ZYX", "rugeby");

    secondAuthToken = new Authtoken("def456&654WVU", "rugebyby");

    Connection conn = db.getConnection();
    aDao = new AuthtokenDAO(conn);
    aDao.clear();
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    db.closeConnection(false);
  }

  @Test
  public void insertPass() throws DataAccessException {
    aDao.insert(firstAuthToken);
    Authtoken compareTest = aDao.retrieveAuthToken(firstAuthToken.getAuthtoken());
    assertNotNull(compareTest);
    assertEquals(firstAuthToken, compareTest);
  }

  @Test
  public void insertFail() throws DataAccessException {
    aDao.insert(firstAuthToken);
    assertThrows(DataAccessException.class, () -> aDao.insert(firstAuthToken));
  }

  @Test
  public void retrievePass() throws DataAccessException {
    // insertAuthToken some AuthTokens into the database
    aDao.insert(firstAuthToken);
    aDao.insert(secondAuthToken);

    // retrieve the second AuthToken
    Authtoken compareTest = aDao.retrieveAuthToken(secondAuthToken.getAuthtoken());

    // make sure the retrieved AuthToken is not null
    assertNotNull(compareTest);
    assertEquals(secondAuthToken, compareTest);
  }

  @Test
  public void retrieveFail() throws DataAccessException {
    aDao.insert(firstAuthToken);
    // retrieve the data that is not in the table
    Authtoken compareTest = aDao.retrieveAuthToken(secondAuthToken.getAuthtoken());
    assertNull(compareTest);
  }

  @Test
  public void clearPass() throws DataAccessException {
    aDao.insert(firstAuthToken);
    aDao.insert(secondAuthToken);
    // clearAuthTokens all tables
    aDao.clear();
    List<Authtoken> compareTest = aDao.getAllAuthTokens();
    assertNull(compareTest);
  }
}
