package service;

import dataAccess.AuthtokenDAO;
import dataAccess.Database;
import dataAccess.EventDAO;
import model.Authtoken;
import model.Event;
import request.EventRequest;
import result.EventResult;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns ALL events for ALL family members of the current user.
 * The current user is determined from the provided auth token.
 */

public class EventService {
  public EventResult event(EventRequest rq){
    Database db = new Database();
    EventResult result = null;
    try{
      db.openConnection();

      String authtoken = rq.getAuthToken();

      EventDAO eDao = new EventDAO(db.getConnection());
      AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());
      //find the associate authtoken
      Authtoken authToken  = aDao.retrieveAuthToken(authtoken);
      if(authToken!=null){
        String username = authToken.getUsername();
        List <Event> events = eDao.getUserEvents(username);
        Event[]data = new Event[events.size()];

        data = events.toArray(data);

        result = new EventResult(data, true);

      }
      else{
        result = new EventResult(false, "Error: authToken not find");
      }
      db.closeConnection(true);
    }
    catch (Exception e){
      e.printStackTrace();
      db.closeConnection(false);
      result = new EventResult(false, "Error: database not accessed");
    }
    return result;
  }
}
