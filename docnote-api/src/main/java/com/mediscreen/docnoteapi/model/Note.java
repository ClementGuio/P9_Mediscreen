package com.mediscreen.docnoteapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notes")
public class Note {
	
	@Id
	private String id;
	
	private String firstname;
	
	private String lastname;
	
	private String comment;

	
	@Override
	public String toString() {
		return "Note [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", comment=" + comment + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
