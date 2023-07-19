package passoff.ServiceTest;

import dataAccess.*;
import model.Authtoken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.ClearResult;
import service.ClearService;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class ClearServiceTest {
  private Database db;
  @BeforeEach
  public void setup() throws DataAccessException, SQLException{
    db = new Database();
    db.openConnection();
    Connection conn = db.getConnection();

    PersonDAO pDao = new PersonDAO(conn);
    EventDAO eDao = new EventDAO(conn);
    AuthtokenDAO aDao = new AuthtokenDAO(conn);
    UserDAO uDao = new UserDAO(conn);

    pDao.clear();
    eDao.clear();
    aDao.clear();
    uDao.clear();

    pDao.insert(new Person("mattissb08", "rugeby",
            "Matt", "Shumway", "m", "stu", "Becky", "ru"));
    eDao.insert(new Event("Biking_123A", "Gale", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016));
    aDao.insert(new Authtoken("1223345", "rugeby"));
    uDao.insert(new User("Joysong", "woshiJi", "joysong3gmailcom",
            "Joy", "Song", "f", "hvfuvhjnbc"));


    db.closeConnection(true);
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    //db.closeConnection(false);
  }

  @Test
  public void ClearServiceTestPositive()throws DataAccessException, SQLException{
    ClearService clearService = new ClearService();
    ClearResult result = clearService.clear();
    assertEquals(true, result.isSuccess());
  }
}
