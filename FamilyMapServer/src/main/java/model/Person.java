package model;

import java.util.Objects;

/**
 * all variable and tools I will use related to Person
 */

public class Person {
  /**
   *Unique identifier for this person
   */
  private String personID;
  /**
   *Username of user to which this person belongs
   */
  private String associatedUsername;
  /**
   *Person’s first name
   */
  private String firstName;
  /**
   *Person’s last name
   */
  private String lastName;
  /**
   *Person’s gender
   */
  private String gender;
  /**
   *Person ID of person’s father
   */
  private String fatherID;
  /**
   *Person ID of person’s mother
   */
  private String motherID;
  /**
   *Person ID of person’s spouse
   */
  private String spouseID;

  public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID,
                String motherID, String spouseID){
    this.personID = personID;
    this.associatedUsername = associatedUsername;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.fatherID = fatherID;
    this.motherID = motherID;
    this.spouseID = spouseID;

  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID = personID;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername = associatedUsername;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getFatherID() {
    return fatherID;
  }

  public void setFatherID(String fatherID) {
    this.fatherID = fatherID;
  }

  public String getMotherID() {
    return motherID;
  }

  public void setMotherID(String motherID) {
    this.motherID = motherID;
  }

  public String getSpouseID() {
    return spouseID;
  }

  public void setSpouseID(String spouseID) {
    this.spouseID = spouseID;
  }



  @Override
  public String toString() {
    return "Person{" +
            "personID='" + personID + '\'' +
            ", associatedUsername='" + associatedUsername + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", gender='" + gender + '\'' +
            ", fatherID='" + fatherID + '\'' +
            ", motherID='" + motherID + '\'' +
            ", spouseID='" + spouseID + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return personID.equals(person.personID) &&
            associatedUsername.equals(person.associatedUsername) &&
            firstName.equals(person.firstName) &&
            lastName.equals(person.lastName) &&
            gender.equals(person.gender) &&
            Objects.equals(fatherID, person.fatherID) &&
            Objects.equals(motherID, person.motherID) &&
            Objects.equals(spouseID, person.spouseID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
  }
}
