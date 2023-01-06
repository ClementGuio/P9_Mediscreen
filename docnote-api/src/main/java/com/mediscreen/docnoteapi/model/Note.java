package com.mediscreen.docnoteapi.model;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mongodb.lang.NonNull;

import com.mediscreen.docnoteapi.configuration.ObjectIdSerializer;

@Document(collection = "notes")
@JsonPropertyOrder({"Id","PatientId","Firstname","Lastname","Birthdate","Gender","Comment"})
public class Note {
	
	@Id
	@JsonSerialize(using = ObjectIdSerializer.class)
	@Field(name="Id")
	private ObjectId id;
	
	@NonNull
	@Field(name="PatientId")
	private Integer patientId;
	
	@NonNull
	@NotBlank
	@Field(name="Firstname")
	private String firstname;
	
	@NonNull	
	@NotBlank
	@Field(name="Lastname")
	private String lastname;
	
	@NonNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Field(name="Birhtdate")
	private LocalDate birthdate;
	
	@NotNull
	@NotBlank
	@Field(name="Gender")
	private String gender;
	
	@NonNull
	@NotBlank
	@Field(name="Comment")
	private String comment;

	public Note(Integer patientId, @NotBlank String firstname, @NotBlank String lastname,
			@NotNull @NotBlank LocalDate birthdate, @NotNull @NotBlank String gender, @NotBlank String comment) {
		super();
		this.id = new ObjectId();
		this.patientId = patientId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.gender = gender;
		this.comment = comment;
	}

	public Note() {
		super();
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
