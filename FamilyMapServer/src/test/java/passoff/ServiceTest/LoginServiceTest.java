package passoff.ServiceTest;


import com.google.gson.Gson;
import dataAccess.*;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import result.LoginResult;
import service.LoginService;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
  private Database db;
  private User user1;
  private User user2;
  Gson gson;

  private LoginRequest loginRequest = new LoginRequest();


  @BeforeEach
  void setUp() throws DataAccessException, SQLException, IOException {
    db = new Database();
    db.openConnection();

    gson = new Gson();

    Connection conn = db.getConnection();
    PersonDAO pDao = new PersonDAO(conn);
    EventDAO eDao = new EventDAO(conn);
    AuthtokenDAO aDao = new AuthtokenDAO(conn);
    UserDAO uDao = new UserDAO(conn);

    pDao.clear();
    eDao.clear();
    aDao.clear();
    uDao.clear();



    user1 = new User("joy", "song", "joysong@gmail.com",
            "joy", "song", "f", "joy1357");
    user2 = new User("matt", "asdasd", "matt@gmail.com",
            "matt", "shum", "m", "matt1357");

    uDao.insert(user1);
    uDao.insert(user2);
    db.closeConnection(true);

  }




  @Test
  public void loginServicePositive() throws SQLException, DataAccessException {
    LoginService loginService = new LoginService();
    loginRequest.setUsername("joy");
    loginRequest.setPassword("song");
    LoginResult loginResult = loginService.login(loginRequest);
    assertNotNull(loginResult.getAuthtoken());
    assertEquals("joy", loginResult.getUsername());
    assertEquals("joy1357", loginResult.getPersonID());
    assertTrue(loginResult.isSuccess());

    LoginService loginService2 = new LoginService();
    loginRequest.setUsername("matt");
    loginRequest.setPassword("asdasd");
    loginResult = loginService2.login(loginRequest);
    assertNotNull(loginResult.getAuthtoken());
    assertEquals("matt", loginResult.getUsername());
    assertEquals("matt1357", loginResult.getPersonID());
    assertTrue(loginResult.isSuccess());

  }

  @Test
  public void loginServiceNegative() throws SQLException, DataAccessException {
    LoginService loginService = new LoginService();
    loginRequest.setUsername(null);
    loginRequest.setPassword("asdasd");
    LoginResult result = loginService.login(loginRequest);
    assertFalse(result.isSuccess());

    loginRequest.setUsername("joy");
    loginRequest.setPassword("wrongpassword");
    LoginResult result2 = loginService.login(loginRequest);
    assertFalse(result2.isSuccess());


  }






}
