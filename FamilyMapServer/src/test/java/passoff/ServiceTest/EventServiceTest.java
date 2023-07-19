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

import result.EventResult;

import service.EventService;


import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EventServiceTest {

  private Database db;
  private EventResult eventResult;

  private String token;
  private String token2;
  private Event event1;
  private Event event2;

  @BeforeEach
  public void setup() throws DataAccessException, SQLException, IOException {
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

    pDao.insert(new Person("Gale123A", "matt", "TingTing", "Liu", "f", "liu135", "liu246", "Chris135"));
    event1 = new Event("Biking_123A", "matt", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    eDao.insert(event1);
    event2 = new Event("Hiking-123A", "matt", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016);
    eDao.insert(event2);
    uDao.insert(new User("matt", "asdasd", "matt@gmail.com",
            "YH", "Chau", "m", "matt1357"));
    aDao.insert(new Authtoken("12345","matt"));
    uDao.insert(new User("Ting", "asdasd", "matt@gmail.com",
            "YH", "Chau", "m", "Ting123"));
    aDao.insert(new Authtoken("67890","Ting"));
    token = "12345";
    token2 = "67890";

    db.closeConnection(true);
  }


  @Test
  public void eventServicePositive() throws DataAccessException
  {

    EventService service = new EventService();
    EventRequest request = new EventRequest(token);
    eventResult = service.event(request);
    assertEquals(2, eventResult.getData().length);
    for(Event event: eventResult.getData())
    {
      if(event.equals(event1))
      {
        assertEquals("Biking_123A",event.getEventID());
        assertEquals("matt",event.getAssociatedUsername());
        assertEquals("Gale123A",event.getPersonID());
        assertEquals(35.9f,event.getLatitude());
        assertEquals(140.1f,event.getLongitude());
        assertEquals("Japan",event.getCountry());
        assertEquals("Ushiku",event.getCity());
        assertEquals("Biking_Around",event.getEventType());
        assertEquals(2016,event.getYear());
      }
      if(event.equals(event2))
      {
        assertEquals("Hiking-123A",event.getEventID());
        assertEquals("matt",event.getAssociatedUsername());
        assertEquals("Gale123A",event.getPersonID());
        assertEquals(35.9f,event.getLatitude());
        assertEquals(140.1f,event.getLongitude());
        assertEquals("Japan",event.getCountry());
        assertEquals("Ushiku",event.getCity());
        assertEquals("Biking_Around",event.getEventType());
        assertEquals(2016,event.getYear());
      }
    }


  }


  @Test
  public void eventServiceNegative() throws DataAccessException {
    EventService service = new EventService();
    EventRequest request = new EventRequest("invalid");
    eventResult = service.event(request);
    assertEquals(false,eventResult.isSuccess());
  }

}
