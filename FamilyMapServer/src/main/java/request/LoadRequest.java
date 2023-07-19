package request;

import model.Event;
import model.Person;
import model.User;

public class LoadRequest {
  /**
   * User[] array
   */
  private User[] users;
  /**
   * Person[] array
   */
  private Person[] persons;
  /**
   * Event[] array
   */
  private Event[] events;

  public User[] getUsers() {
    return users;
  }

  public void setUsers(User[] users) {
    this.users = users;
  }

  public Person[] getPersons() {
    return persons;
  }

  public void setPersons(Person[] persons) {
    this.persons = persons;
  }

  public Event[] getEvents() {
    return events;
  }

  public void setEvents(Event[] events) {
    this.events = events;
  }
}
