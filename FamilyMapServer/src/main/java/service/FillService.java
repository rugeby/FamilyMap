package service;

import dataAccess.*;
import model.Person;
import model.User;
import request.FillRequest;
import result.FillResult;

/**
 * Populates the server's database with generated data for the specified username. The required "username" parameter must be a user already registered with the server. If there is any data in the database already associated with the given username, it is deleted.
 * The optional "generations" parameter lets the caller specify the number of generations of ancestors to be generated, and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
 * More details can be found in the earlier section titled “Family History Information Generation”
 */
public class FillService {
  public FillResult fill(FillRequest rq) throws DataAccessException{

    Database db = new Database();
    FillResult result = null;

    try{
      db.openConnection();
      //generated data for the specified username.
      String username = rq.getUsername();
      int generation   = rq.getGeneration();
      //open database
      UserDAO uDao = new UserDAO(db.getConnection());
      EventDAO eDao = new EventDAO(db.getConnection());
      PersonDAO pDao = new PersonDAO(db.getConnection());
      AuthtokenDAO aDao = new AuthtokenDAO(db.getConnection());

      //get request user info in database
      User user = uDao.retrieveUser(username);

      //generate family tree
      if(user!= null){
        GenTreeService genTree = new GenTreeService(user, pDao, eDao);
        //
        genTree.clearFamily();
        //generate info of the user first;
        //and inside generate function, it will generate user's family treenautomatically
        genTree.generate(generation);

        String message = String.format("Successfully added %d persons and %d events to the database.",
                genTree.getPersonCount(), genTree.getEventCount());
        result = new FillResult(true, message);
      }
      else{
        result = new FillResult(false, "Error: i did not find the user");
      }
      db.closeConnection(true);

    }catch(Exception e){
      e.printStackTrace();
      db.closeConnection(false);
      result = new FillResult(false, "Error: access data failed");
    }
    return result;
  }




}
