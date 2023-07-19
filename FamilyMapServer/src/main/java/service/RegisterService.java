package service;

import dataAccess.*;
import model.Authtoken;
import model.User;
import request.RegisterRequest;
import result.RegisterResult;

import java.util.UUID;

/**
 * Creates a new user account (user row in the database)
 * Generates 4 generations of ancestor data for the new user (just like the /fill endpoint if called with a generations value of 4 and this new user’s username as parameters)
 * Logs the user in
 * Returns an authtoken
 */

public class RegisterService {
  public RegisterResult register(RegisterRequest rq){
    Database db = new Database();
    RegisterResult result = null;

    try{
      db.openConnection();
      String username = rq.getUsername();
      String password = rq.getPassword();
      String email = rq.getEmail();
      String firstName = rq.getFirstName();
      String lastName = rq.getLastName();
      String gender = rq.getGender();
      //String personID = UUID.randomUUID().toString();


      //connect database
      UserDAO uDao = new UserDAO(db.getConnection());
      PersonDAO pDao = new PersonDAO(db.getConnection());
      EventDAO eDao = new EventDAO(db.getConnection());
      AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());

      User user = uDao.retrieveUser(username);

      //need to make sure user not in database
      if(user == null){
        String personID = UUID.randomUUID().toString();
        String authToken = UUID.randomUUID().toString();

        user = new User(username, password, email, firstName, lastName, gender, personID);
        uDao.insert(user);

        Authtoken authtoken = new Authtoken(authToken, username);
        aDao.insert(authtoken);

        GenTreeService genTree= new GenTreeService(user, pDao, eDao);
        //default generate 4
        genTree.generate(4);

        result = new RegisterResult(authToken, username, personID,true);

      }

      else{
        result = new RegisterResult(false, "Error: username already exist");
      }
      db.closeConnection(true);

    }catch (Exception e){
      db.closeConnection(false);
      e.printStackTrace();
      result = new RegisterResult(false, "Error: access database failed");
    }
    return result;
  }

}
