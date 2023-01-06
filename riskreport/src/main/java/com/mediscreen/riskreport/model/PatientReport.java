package com.mediscreen.riskreport.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"Firstname","Lastname","Birthdate","Gender","RiskLevel"})
public class PatientReport {

	private String firstname;
	
	private String lastname;

	@JsonIgnore
	private LocalDate birthdate;
	
	@JsonIgnore
	private String gender;
	
	@JsonIgnore
	private List<String> notes;

	private String riskLevel;

	public PatientReport(String firstname, String lastname, LocalDate birthdate, String gender, List<String> notes) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.gender = gender;
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "PatientReport [firstname=" + firstname + ", lastname=" + lastname + ", birthdate=" + birthdate
				+ ", gender=" + gender + ", notes=" + notes + ", riskLevel=" + riskLevel + "]";
	}
	
	@JsonGetter("Firstname")
	public String getFirstname() { 
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@JsonGetter("Lastname")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
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

	@JsonGetter("RiskLevel")
	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
}
