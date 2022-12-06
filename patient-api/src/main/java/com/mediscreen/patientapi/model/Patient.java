package com.mediscreen.patientapi.model;
/*
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
*/


//@Entity
//@Table(name = "patient")
public class Patient {

	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="id")
	private Integer id;
	
	//@NotBlank
	//@Column(name="firstname")
	private String firstname;
	
	//@NotBlank
	//@Column(name="lastname")
	private String lastname;
	
	//@NotBlank
	//@Column(name="gender")
	private String gender;
	
	//@NotBlank
	//@Column(name="address")
	private String address;
	
	//@NotBlank
	//@Column(name="phone")
	private String phone;
	
}
