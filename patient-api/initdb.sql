CREATE database patientDB;

USE patientDB;

CREATE TABLE IF NOT EXISTS patient (

	id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	firstname VARCHAR(64) NOT NULL,
	lastname VARCHAR(64) NOT NULL,
	birthdate DATE NOT NULL,
	gender VARCHAR(1) NOT NULL,
	address VARCHAR(256) NOT NULL,
	phone VARCHAR(10) NOT NULL

);
	
	
insert into patient(firstname,lastname,birthdate,gender,address,phone) values("Firstname", "Lastname","19990101", "F", "Address", "007");
	