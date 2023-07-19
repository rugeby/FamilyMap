package passoff.ServiceTest;


import dataAccess.*;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.FillRequest;
import result.FillResult;
import service.FillService;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FillServiceTest {
  private Database db;
  @BeforeEach
  void setUp() throws DataAccessException, SQLException {
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


    uDao.insert(new User("joy", "asdasd", "matt@gmail.com",
            "matt", "shumway", "m", "matt1357"));
    db.closeConnection(true);

  }


  @Test
  public void fillServicePositive() throws SQLException, FileNotFoundException, DataAccessException {
    FillService fillService = new FillService();
    FillRequest request = new FillRequest("joy", 2);
    FillResult fillResult = fillService.fill(request);
    assertEquals(true, fillResult.getMessage().contains("added 7 persons"));
    assertEquals(true, fillResult.isSuccess());
  }


  @Test
  public void fillServiceNegative() throws SQLException, FileNotFoundException, DataAccessException {
    FillService fillService = new FillService();
    FillRequest request = new FillRequest("invalidusername", 4);
    FillResult fillResult = fillService.fill(request);

    assertFalse(fillResult.isSuccess());



  }






}
