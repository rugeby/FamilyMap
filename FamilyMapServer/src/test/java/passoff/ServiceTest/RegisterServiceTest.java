package passoff.ServiceTest;


import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.FillRequest;
import request.RegisterRequest;
import result.FillResult;
import result.RegisterResult;
import service.FillService;
import service.RegisterService;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
  private Database db;

  private RegisterRequest request;

  private List<Person> personArrayList;
  private List<Event> eventArrayList;

  @BeforeEach
  public void setup() throws SQLException, DataAccessException {
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


    request = new RegisterRequest();

    request.setUsername("matt");
    request.setPassword("12345");
    request.setFirstName("Chau");
    request.setLastName("Chau");
    request.setGender("m");
    request.setEmail("matt@gmail.com");

    db.closeConnection(true);
  }

  @Test
  public void registerServicePositive() throws SQLException, FileNotFoundException, DataAccessException {
    RegisterService registerService = new RegisterService();
    RegisterResult registerResult = registerService.register(request);
    assertNotNull(registerResult.getAuthtoken());
    assertEquals("matt", registerResult.getUsername());
    assertNotNull(registerResult.getPersonID());
    assertNull(registerResult.getMessage());
    assertEquals(true,registerResult.isSuccess());


    db.openConnection();

    Connection conn = db.getConnection();
    PersonDAO pDao = new PersonDAO(conn);
    EventDAO eDao = new EventDAO(conn);
    AuthtokenDAO aDao = new AuthtokenDAO(conn);
    UserDAO uDao = new UserDAO(conn);
    personArrayList = pDao.getUserPersons("matt");
    eventArrayList = eDao.getUserEvents("matt");
    db.closeConnection(false);
    //persons created including user
    assertEquals(31, personArrayList.size(),"4 generations: 1+2+4+8+16");
    //events that didn't add user death date
    assertEquals(93,eventArrayList.size(),"all person have a birthdate(31), all person besides user have a death date(30)," +
            "parents both have a marriage event(31) ");

  }

  @Test
  public void registerServiceNegative() throws SQLException, FileNotFoundException, DataAccessException {
    RegisterService registerService = new RegisterService();
    RegisterResult registerResult = registerService.register(request);
    registerResult = registerService.register(request);
    assertEquals("Error: username already exist",registerResult.getMessage());
    assertFalse(registerResult.isSuccess());
    //check for invalid names
    request.setUsername(null);
    registerResult = registerService.register(request);
    assertEquals("Error: access database failed",registerResult.getMessage());
    assertFalse(registerResult.isSuccess());

  }





}
