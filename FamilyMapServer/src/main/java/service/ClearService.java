package service;

import dataAccess.*;
import model.User;
import result.ClearResult;

import java.io.IOException;
import java.sql.ResultSet;

/**
 * Deletes ALL data from the database, including user, authtoken, person, and event data
 * clear dont have request
 */
public class ClearService {
  public ClearResult clear() {
    Database db = new Database();

    ClearResult result = null;
    try {
      db.openConnection();

      EventDAO eDao = new EventDAO(db.getConnection());
      eDao.clear();

      PersonDAO pDao = new PersonDAO(db.getConnection());
      pDao.clear();

      UserDAO uDao = new UserDAO(db.getConnection());
      uDao.clear();

      AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());
      aDao.clear();


      db.closeConnection(true);

      result = new ClearResult(true, "Clear succeeded.");


    } catch (DataAccessException e) {
      db.closeConnection(false);
      result = new ClearResult(false, "Error:");
      e.printStackTrace();


    }
    return result;
  }
}
