package com.mediscreen.docnoteapi.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mongodb.lang.NonNull;

@Document(collection = "notes")
@JsonPropertyOrder({"Id","PatientId","Firstname","Lastname","Comment"})
public class Note {

	@Id
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
	@NotBlank
	@Field(name="Comment")
	private String comment;

	public Note() {
		super();
	}
	
	public Note(@NotNull Integer patientId, @NotNull @NotBlank String firstname, @NotNull @NotBlank String lastname,
			@NotNull @NotBlank String comment) {
		super();
		this.patientId = patientId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Note [id = " + id +", patientId = " + patientId+ ", firstname = " + firstname + ", lastname = " + lastname + ", comment = " + comment + "]";
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
