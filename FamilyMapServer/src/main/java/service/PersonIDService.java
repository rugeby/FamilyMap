package service;

import dataAccess.AuthtokenDAO;
import dataAccess.Database;
import dataAccess.PersonDAO;
import model.Authtoken;
import model.Person;
import request.PersonRequest;
import result.PersonIDResult;
import result.PersonResult;

/**
 Returns the single Person object with the specified ID
 (if the person is associated with the current user).
 The current user is determined by the provided authtoken.
 */

public class PersonIDService {
  public PersonIDResult personID(PersonRequest rq){

    Database db = new Database();
    PersonIDResult result = null;
    try{
      db.openConnection();
      String authtoken = rq.getAuthToken();
      String personID = rq.getPersonID();

      PersonDAO pDao = new PersonDAO(db.getConnection());
      AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());

      Authtoken authtokenBool = aDao.retrieveAuthToken(authtoken);
      if(authtokenBool != null) {
        String username  = authtokenBool.getUsername();
        Person person = pDao.retrievePerson(personID);

        if(person.getAssociatedUsername().equals(username)){
          result = new PersonIDResult(person.getAssociatedUsername(),person.getPersonID(), person.getFirstName(), person.getLastName(), person.getGender()
          , person.getFatherID(), person.getMotherID(), person.getSpouseID(), true);
        }
        else{
          result = new PersonIDResult(false, "Error: authtoken not found");
        }
        // Close database connection, COMMIT transaction
      }
      else{
        result = new PersonIDResult(false, "Error: authToken not find");
      }
      db.closeConnection(true);
    }catch(Exception e){
      e.printStackTrace();
      db.closeConnection(false);
      result = new PersonIDResult(false, "Error occurred when accessing the database");
    }
    return result;
  }

}
