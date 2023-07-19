package service;

import dataAccess.AuthtokenDAO;
import dataAccess.Database;
import dataAccess.EventDAO;
import model.Authtoken;
import model.Event;
import request.EventRequest;
import result.EventIDResult;
import result.EventResult;
import result.PersonIDResult;

/**
 * Returns the single Event object with the specified ID
 * (if the event is associated with the current user).
 * The current user is determined by the provided authtoken.
 */
public class EventIDService {
  public EventIDResult eventID(EventRequest rq){
    Database db = new Database();
    EventIDResult result = null;
    try{
      db.openConnection();

      String authtoken = rq.getAuthToken();
      String eventID = rq.getEventID();

      EventDAO eDao = new EventDAO(db.getConnection());
      AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());

      Authtoken authToken = aDao.retrieveAuthToken(authtoken);
      if(authToken != null){
        String username = authToken.getUsername();
        Event event = eDao.retrieveEvent(eventID);
        if(event.getAssociatedUsername().equals(username)){
          result = new EventIDResult(event.getAssociatedUsername(), event.getEventID(), event.getPersonID()
                  , event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType()
          ,event.getYear(), true);
        }
        else{
          result = new EventIDResult("Error: this eventid dont have associated with user", false);
        }
      }
      else{
        result = new EventIDResult("Error: related authtoken not find", false);
      }
      db.closeConnection(true);

    }catch (Exception e){
      e.printStackTrace();
      db.closeConnection(false);
      result = new EventIDResult("Error occurred when accessing the database", false);

    }

    return result;
  }
}
