package passoff.ServiceTest;


import com.google.gson.Gson;
import dataAccess.*;
import model.Authtoken;
import model.Event;
import model.Person;
import model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventRequest;

import request.PersonRequest;
import result.EventResult;

import result.PersonIDResult;
import result.PersonResult;
import service.EventService;
import service.PersonIDService;
import service.PersonService;


import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PersonIDServiceTest {
  private Database db;
  private String token;

  private Person person1;
  private Person person2;

  @BeforeEach
  public void setup() throws DataAccessException, SQLException {
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

    person1 = new Person("joy1357", "joy", "joysong", "Liu", "f", "joy135", "joy246", "matt135");
    person2 = new Person("matt135" , "joy", "Yu Hin", "Chau", "m", "Chau134", "Wong246", "joy246");
    // person3 = new Person("ASddd", "Ting", "ergrg", "wef", "m", "Chau134", "Wong246", "Ting246");
    pDao.insert(person1);
    pDao.insert(person2);
    // pDao.insert(person3);

    uDao.insert(new User("joy", "asdasd", "matt@gmail.com",
            "YH", "Chau", "m", "matt135"));


    aDao.insert(new Authtoken("567890","joy"));
    token = "567890";
    uDao.insert(new User("matt", "asdasd", "matt@gmail.com",
            "YH", "Chau", "m", "joy123"));
    aDao.insert(new Authtoken("12345","matt"));

    db.closeConnection(true);

  }


  @Test
  public void personIDServicePositive() throws SQLException, DataAccessException {
    PersonIDService service = new PersonIDService();
    PersonRequest request = new PersonRequest(token, "joy1357");
    PersonIDResult result = service.personID(request);


    assertEquals("joy1357",result.getPersonID());
    assertEquals("joy",result.getAssociatedUsername());
    assertEquals("joysong",result.getFirstName());
    assertEquals("Liu",result.getLastName());
    assertEquals("f",result.getGender());
    assertEquals("joy135",result.getFatherID());
    assertEquals("joy246",result.getMotherID());
    assertEquals("matt135",result.getSpouseID());
    assertEquals(true,result.isSuccess());



  }

  @Test
  public void personIDServiceNegative() throws SQLException, DataAccessException {
    PersonIDService service = new PersonIDService();
    PersonRequest request = new PersonRequest(token, "invalidId");
    PersonIDResult result = service.personID(request);

    assertFalse(result.isSuccess());
  }















}
