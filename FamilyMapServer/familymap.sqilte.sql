drop table if exists User;
drop table if exists Person;
drop table if exists Event;
drop table if exists Authtoken;

create table User
(
    username varchar(255) not null unique,
    password varchar(255) not null,
    email varchar(255) not null,
    firstName varchar(255) not null,
    lastName varchar(255) not null,
    gender varchar(255) not null,
    personID varchar(255) not null

);

create table Person
(
    personID varchar(255) not null unique,
    associatedUsername varchar(255) not null,
    firstName varchar(255) not null,
    lastName varchar(255) not null,
    gender varchar(255) not null,
    fatherID varchar(255),
    motherID varchar(255),
    spouseID varchar(255)

);

create table Event
(
    eventID varchar(255) not null unique,
    associatedUsername varchar(255) not null,
    personID varchar(255) not null,
    latitude float not null,
    longitude float not null,
    country varchar(255) not null,
    city varchar(255) not null,
    eventType varchar(255) not null,
    year Integer not null

);

create table Authtoken
(
  authtoken varchar(255) not null unique,
  username varchar(255) not null
);

