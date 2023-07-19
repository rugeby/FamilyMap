package passoff.ServiceTest;


import com.google.gson.Gson;
import dataAccess.*;
import model.Authtoken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventRequest;
import request.LoadRequest;
import result.EventIDResult;
import result.EventResult;
import result.LoadResult;
import service.EventIDService;
import service.EventService;
import service.LoadService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class EventIDServiceTest {

  private Database db;
  private String token;

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
    eDao.insert(new Event("Biking_123A", "matt", "Gale123A",
            35.9f, 140.1f, "Japan", "Ushiku",
            "Biking_Around", 2016));
    uDao.insert(new User("matt", "asdasd", "matt@gmail.com",
            "YH", "Chau", "m", "matt1357"));
    aDao.insert(new Authtoken("12345","matt"));
    token = "12345";
    db.closeConnection(true);
  }


  @Test
  public void eventIDServicePositive() throws SQLException, DataAccessException {
    EventIDService eventIDService = new EventIDService();
    EventRequest request = new EventRequest(token, "Biking_123A");
    EventIDResult eventIDResult = eventIDService.eventID(request);
    assertEquals("Biking_123A", eventIDResult.getEventID());
    assertEquals("matt", eventIDResult.getAssociatedUsername());
    assertEquals("Gale123A", eventIDResult.getPersonID());
    assertEquals(35.9f, eventIDResult.getLatitude());
    assertEquals(140.1f, eventIDResult.getLongitude());
    assertEquals("Japan", eventIDResult.getCountry());
    assertEquals("Ushiku", eventIDResult.getCity());
    assertEquals(2016, eventIDResult.getYear());
  }

  @Test
  public void eventIDServiceNegative() throws SQLException, DataAccessException {
    EventIDService service = new EventIDService();
    EventRequest request = new EventRequest(token, "invalidID");
    EventIDResult result = service.eventID(request);

    assertFalse(result.isSuccess());



  }



}
