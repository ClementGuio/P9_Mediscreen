package com.mediscreen.riskreport.model;

import java.util.Date;
import java.util.List;

public class PatientInfo {

	private String Firstname;
	
	private String lastname;

	private Date birthdate;
	
	private String gender;
	
	private List<String> notes;

	public PatientInfo(String firstname, String lastname, Date birthdate, String gender, List<String> notes) {
		super();
		Firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.gender = gender;
		this.notes = notes;
	}
	
	public String getFirstname() {
		return Firstname;
	}

	public void setFirstname(String firstname) {
		Firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}
	
}
