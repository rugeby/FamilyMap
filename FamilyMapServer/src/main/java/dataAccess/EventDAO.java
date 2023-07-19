package dataAccess;

import model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dataAccess.DataAccessException;
import model.User;

/**
 * Get all the data related to event
 */


public class EventDAO {
    /**
     * an url
     */
    private final Connection conn;



    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * insert all the data related to this event
     * @param event
     * @throws DataAccessException
     */
    public void insert(Event event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Event (eventID, associatedUsername, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());//
            stmt.setString(2, event.getAssociatedUsername());//
            stmt.setString(3, event.getPersonID());//
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }


    /**
     * find all info connected to this eventID
     * @param eventID
     * @return
     * @throws DataAccessException
     */
    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }

    }


    /**
     * clear all the data
     * @throws DataAccessException
     */

    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Event";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }


    public void insertEvents(List<Event> events) throws DataAccessException {
        for (Event event : events) {
            insert(event);
        }
    }

    /**
     * delete related event by Username;
     * @param assUsername
     */


    public List<Event> getUserEvents(String username) throws DataAccessException {
        List<Event> events = new ArrayList<Event>();
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
        if (events.size() > 0) {
            return events;
        }
        return null;
    }


    public Event retrieveEvent(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    public void clearEventByUsername(String username)throws DataAccessException{
        String sql = "DELETE FROM Event WHERE associatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing user events");
        }


    }






    public Event findByUsername(String Username){
        return null;
    }



  //find
    //find
}
