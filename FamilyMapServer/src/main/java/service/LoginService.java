package service;

import dataAccess.AuthtokenDAO;
import dataAccess.Database;
import dataAccess.UserDAO;
import model.Authtoken;
import model.User;
import request.LoginRequest;
import result.LoginResult;

import java.util.UUID;

/**
 * Logs the user in
 * Returns an authtoken.
 */

public class LoginService {
  public LoginResult login(LoginRequest rq){
    Database db = new Database();
    LoginResult result = null;
    try{
      db.openConnection();

      String username = rq.getUsername();
      String password = rq.getPassword();

      UserDAO uDao = new UserDAO(db.getConnection());
      AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());

      User user = uDao.retrieveUser(username);
      if(user != null){

        if (user.getPassword().equals(password)) {
          // generate a new authtoken every time signed in
          String authtoken = UUID.randomUUID().toString();
          Authtoken authToken = new Authtoken(authtoken, username);

          if (aDao.findAuthToken(username) != null) {
            // if the user was registered or logged in before
            aDao.updateAuthToken(authToken);
          } else {
            // if this is the first time the user logs in (no authtoken found)
            aDao.insert(authToken);
          }

          // Create and return success Result object
          result = new LoginResult(true, authtoken, username, user.getPersonID());
        }
        else {
          result = new LoginResult("Error: incorrect password", false);
        }


      }
      else{
        result = new LoginResult("Error: user not find", false);
      }

      db.closeConnection(true);


    }catch(Exception e){
      e.printStackTrace();
      db.closeConnection(false);
      result = new LoginResult("Error: database failed", false);

    }


    return result;
  }
}
