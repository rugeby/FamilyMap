package service;

import dataAccess.*;
import model.Event;
import model.Person;
import model.User;
import request.LoadRequest;
import result.ClearResult;
import result.LoadResult;

import java.util.Arrays;

/**
 * Clears all data from the database (just like the /clear API)
 * Loads the user, person, and event data from the request body into the database.
 */

public class LoadService {

  public LoadResult load(LoadRequest r){
    ClearService clearService = new ClearService();
    ClearResult clearResult = clearService.clear();

    Database db = new Database();
    LoadResult result = null;

    boolean success = false;
    try{
      db.openConnection();

      User[] users = r.getUsers();
      Person[] persons = r.getPersons();
      Event[] events = r. getEvents();

      UserDAO uDao = new UserDAO(db.getConnection());
      PersonDAO pDao = new PersonDAO(db.getConnection());
      EventDAO eDao = new EventDAO(db.getConnection());

      uDao.insertUsers(Arrays.asList(users));
      pDao.insertPersons(Arrays.asList(persons));
      eDao.insertEvents(Arrays.asList(events));

      String message = String.format("Successfully added "+ users.length + " users, " +
      persons.length + " persons, and " + events.length + "events to the database.");

      success = true;

      result = new LoadResult(success, message);

      db.closeConnection(true);


    }catch (DataAccessException e){
      String message = "Error: Invalid request data";
      e.printStackTrace();

      result = new LoadResult(success, message);
      db.closeConnection(false);
    }


    return result;
  }

}
