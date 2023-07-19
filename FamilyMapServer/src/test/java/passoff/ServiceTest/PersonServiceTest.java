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

import result.PersonResult;
import service.EventService;
import service.PersonService;


import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PersonServiceTest {
  private Database db;
  private String token;
  private String token2;
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


    person1 = new Person("joy1357", "joy", "joysong", "Liu", "f", "liu135", "liu246", "matt135");
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
    token2 = "12345";
    db.closeConnection(true);
  }


  @Test
  public void eventServicePositive() throws DataAccessException, SQLException {
    PersonService service = new PersonService();
    PersonRequest  request = new PersonRequest(token);
    PersonResult result = service.person(request);
    assertEquals(2, result.getData().length);
    for(Person person: result.getData())
    {
      if(person.equals(person1))
      {
        assertEquals("joy1357",person.getPersonID());
        assertEquals("joy",person.getAssociatedUsername());
        assertEquals("joysong",person.getFirstName());
        assertEquals("Liu",person.getLastName());
        assertEquals("f",person.getGender());
        assertEquals("liu135",person.getFatherID());
        assertEquals("liu246",person.getMotherID());
        assertEquals("matt135",person.getSpouseID());
      }
      if(person.equals(person2))
      {
        assertEquals("matt135",person.getPersonID());
        assertEquals("joy",person.getAssociatedUsername());
        assertEquals("Yu Hin",person.getFirstName());
        assertEquals("Chau",person.getLastName());
        assertEquals("m",person.getGender());
        assertEquals("Chau134",person.getFatherID());
        assertEquals("Wong246",person.getMotherID());
        assertEquals("joy246",person.getSpouseID());
      }
    }
  }




  @Test
  public void eventServiceNegative() throws DataAccessException, SQLException {
    PersonService service = new PersonService();
    PersonRequest  request = new PersonRequest("invalidtoken");
    PersonResult result = service.person(request);
    assertFalse(result.isSuccess());

  }








}
