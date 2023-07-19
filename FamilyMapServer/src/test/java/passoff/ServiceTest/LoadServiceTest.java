package passoff.ServiceTest;


import com.google.gson.Gson;
import dataAccess.*;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import result.LoadResult;
import service.LoadService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadServiceTest {
  private Database db;
  private LoadRequest loadRequest = new LoadRequest();

  private LoadResult loadResult;
  Gson gson;
  private String Text2;
  private String Text3;
  private UserDAO uDao;
  private PersonDAO pDao;

  private EventDAO eDao;
  private AuthtokenDAO aDao;
  @BeforeEach
  public void setup() throws DataAccessException, SQLException, IOException {
    db = new Database();
//    db.openConnection();
//    Connection conn = db.getConnection();

    gson  =new Gson();

//    this.pDao = new PersonDAO(conn);
//    this.eDao = new EventDAO(conn);
//    this.aDao = new AuthtokenDAO(conn);
//    this.uDao = new UserDAO(conn);

    Text2 = "{\n" +
            "\t\"users\":[\n      {\n" +
            "         \"username\":\"patrick\",\n" +
            "         \"password\":\"spencer\",\n" +
            "         \"email\":\"patrick@spencer.com\",\n" +
            "         \"firstName\":\"Patrick\",\n" +
            "         \"lastName\":\"Spencer\",\n" +
            "         \"gender\":\"m\",\n" +
            "         \"personID\":\"Patrick_Spencer\"\n" +
            "      }" +
            "\t\t\n" +
            "\t],\n" +
            "\t\"persons\":[\n{\n" +
            "         \"firstName\":\"Sheila\",\n" +
            "         \"lastName\":\"Parker\",\n" +
            "         \"gender\":\"f\",\n" +
            "         \"personID\":\"Sheila_Parker\",\n" +
            "         \"spouseID\":\"Davis_Hyer\",\n" +
            "         \"fatherID\":\"Blaine_McGary\",\n" +
            "         \"motherID\":\"Betty_White\",\n" +
            "         \"associatedUsername\":\"sheila\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"firstName\":\"Davis\",\n" +
            "         \"lastName\":\"Hyer\",\n" +
            "         \"gender\":\"m\",\n" +
            "         \"personID\":\"Davis_Hyer\",\n" +
            "         \"spouseID\":\"Sheila_Parker\",\n" +
            "         \"associatedUsername\":\"sheila\"\n" +
            "      }" +
            "\t\t\n" +
            "\t],\n" +
            "\t\"events\":[\n{\n" +
            "         \"eventType\":\"birth\",\n" +
            "         \"personID\":\"Sheila_Parker\",\n" +
            "         \"city\":\"Melbourne\",\n" +
            "         \"country\":\"Australia\",\n" +
            "         \"latitude\":-36.1833,\n" +
            "         \"longitude\":144.9667,\n" +
            "         \"year\":1970,\n" +
            "         \"eventID\":\"Sheila_Birth\",\n" +
            "         \"associatedUsername\":\"sheila\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"eventType\":\"marriage\",\n" +
            "         \"personID\":\"Sheila_Parker\",\n" +
            "         \"city\":\"Los Angeles\",\n" +
            "         \"country\":\"United States\",\n" +
            "         \"latitude\":34.0500,\n" +
            "         \"longitude\":-117.7500,\n" +
            "         \"year\":2012,\n" +
            "         \"eventID\":\"Sheila_Marriage\",\n" +
            "         \"associatedUsername\":\"sheila\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"eventType\":\"completed asteroids\",\n" +
            "         \"personID\":\"Sheila_Parker\",\n" +
            "         \"city\":\"Qaanaaq\",\n" +
            "         \"country\":\"Denmark\",\n" +
            "         \"latitude\":77.4667,\n" +
            "         \"longitude\":-68.7667,\n" +
            "         \"year\":2014,\n" +
            "         \"eventID\":\"Sheila_Asteroids\",\n" +
            "         \"associatedUsername\":\"sheila\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"eventType\":\"COMPLETED ASTEROIDS\",\n" +
            "         \"personID\":\"Sheila_Parker\",\n" +
            "         \"city\":\"Qaanaaq\",\n" +
            "         \"country\":\"Denmark\",\n" +
            "         \"latitude\":74.4667,\n" +
            "         \"longitude\":-60.7667,\n" +
            "         \"year\":2014,\n" +
            "         \"eventID\":\"Other_Asteroids\",\n" +
            "         \"associatedUsername\":\"sheila\"\n" +
            "      }" +
            "\t\t\n" +
            "\t]\n" +
            "}";

     Text3 = "{\n" +
            "\t\"users\":[\n      {\n" +
            "         \"username\":\"patrick\",\n" +
            "         \"password\":\"spencer\",\n" +
            "         \"email\":\"patrick@spencer.com\",\n" +
            "         \"firstName\":\"Patrick\",\n" +
            "         \"lastName\":\"Spencer\",\n" +
            "         \"gender\":\"m\",\n" +
            "         \"personID\":\"Patrick_Spencer\"\n" +
            "      }" +
            "\t\t\n" +
            "\t],\n" +
            "\t\"persons\":[\n{\n" +
            "         \"firstName\":\"Sheila\",\n" +
            "         \"gender\":\"f\",\n" +
            "         \"personID\":\"Sheila_Parker\",\n" +
            "         \"spouseID\":\"Davis_Hyer\",\n" +
            "         \"fatherID\":\"Blaine_McGary\",\n" +
            "         \"motherID\":\"Betty_White\",\n" +
            "         \"associatedUsername\":\"sheila\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"firstName\":\"Davis\",\n" +
            "         \"lastName\":\"Hyer\",\n" +
            "         \"gender\":\"m\",\n" +
            "         \"personID\":\"Davis_Hyer\",\n" +
            "         \"spouseID\":\"Sheila_Parker\",\n" +
            "         \"associatedUsername\":\"sheila\"\n" +
            "      }" +
            "\t\t\n" +
            "\t],\n" +
            "\t\"events\":[\n{\n" +
            "         \"eventType\":\"birth\",\n" +
            "         \"personID\":\"Sheila_Parker\",\n" +
            "         \"city\":\"Melbourne\",\n" +
            "         \"country\":\"Australia\",\n" +
            "         \"latitude\":-36.1833,\n" +
            "         \"longitude\":144.9667,\n" +
            "         \"year\":1970,\n" +
            "         \"eventID\":\"Sheila_Birth\",\n" +
            "         \"associatedUsername\":\"sheila\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"eventType\":\"marriage\",\n" +
            "         \"personID\":\"Sheila_Parker\",\n" +
            "         \"city\":\"Los Angeles\",\n" +
            "         \"country\":\"United States\",\n" +
            "         \"latitude\":34.0500,\n" +
            "         \"longitude\":-117.7500,\n" +
            "         \"year\":2012,\n" +
            "         \"eventID\":\"Sheila_Marriage\",\n" +
            "         \"associatedUsername\":\"sheila\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"eventType\":\"completed asteroids\",\n" +
            "         \"personID\":\"Sheila_Parker\",\n" +
            "         \"city\":\"Qaanaaq\",\n" +
            "         \"country\":\"Denmark\",\n" +
            "         \"latitude\":77.4667,\n" +
            "         \"longitude\":-68.7667,\n" +
            "         \"year\":2014,\n" +
            "         \"eventID\":\"Sheila_Asteroids\",\n" +
            "         \"associatedUsername\":\"sheila\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"eventType\":\"COMPLETED ASTEROIDS\",\n" +
            "         \"personID\":\"Sheila_Parker\",\n" +
            "         \"city\":\"Qaanaaq\",\n" +
            "         \"country\":\"Denmark\",\n" +
            "         \"latitude\":74.4667,\n" +
            "         \"longitude\":-60.7667,\n" +
            "         \"year\":2014,\n" +
            "         \"eventID\":\"Other_Asteroids\",\n" +
            "         \"associatedUsername\":\"sheila\"\n" +
            "      }" +
            "\t\t\n" +
            "\t]\n" +
            "}";
   // db.closeConnection(true);

  }


  @AfterEach
  public void tearDown() throws DataAccessException {
    db.closeConnection(true);
  }



  @Test
  public void LoadServiceTestPositive()throws DataAccessException, SQLException{
    boolean findalluser = true;
    LoadService loadService =new LoadService();
    loadRequest = gson.fromJson(Text2, LoadRequest.class);
    loadService.load(loadRequest);

    db.openConnection();
    uDao = new UserDAO(db.getConnection());

    User[] user = loadRequest.getUsers();
    for(int  i = 0; i < user.length; i++){
      if(uDao.retrieveUser(user[i].getUsername())== null){
        findalluser = false;
      }

    }
    assertTrue(findalluser);
  }

  @Test
  public void LoadServiceTestNegative()throws DataAccessException, SQLException{

    LoadService loadService =new LoadService();
    loadRequest = gson.fromJson(Text3, LoadRequest.class);
    loadRequest.setEvents(null);
    loadResult = loadService.load(loadRequest);

    db.openConnection();

    assertEquals("Error: Invalid request data",loadResult.getMessage());
    assertEquals(false,loadResult.isSuccess());

  }

}
