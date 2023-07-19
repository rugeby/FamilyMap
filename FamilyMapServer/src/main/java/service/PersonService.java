package service;

import dataAccess.AuthtokenDAO;
import dataAccess.Database;
import dataAccess.PersonDAO;
import model.Authtoken;
import model.Person;
import request.PersonRequest;
import result.PersonResult;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * //Returns ALL family members of the current user.
 * The current user is determined by the provided authtoken.
 *
 */
public class PersonService {
  public PersonResult person(PersonRequest rq){
    Database db = new Database();
    PersonResult result = null;

    try{
      db.openConnection();
      String authtoken = rq.getAuthToken();
      // use DAOs to do requested operation
      PersonDAO pDao = new PersonDAO(db.getConnection());
      AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());

      Authtoken authtokenBool = aDao.retrieveAuthToken(authtoken);
      if(authtokenBool != null){
        String username = authtokenBool.getUsername();
        List<Person> people = pDao.getUserPersons(username);

        Person[] family = new Person[people.size()];
        family = people.toArray(family);

        result = new PersonResult(family, true );
      }
      else{
        result  = new PersonResult(false, "Error:authtoken not found");
      }
      db.closeConnection(true);
    }catch(Exception e){
      e.printStackTrace();
      db.closeConnection(false);
      result = new PersonResult(false, "Error occurred when accessing the database");
    }

    return result;

    //Returns ALL family members of the current user. The current user is determined by the provided authtoken.

  }
}
