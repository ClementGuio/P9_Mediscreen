package com.mediscreen.patientapi.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "patient")
@JsonPropertyOrder({"Id","Firstname","Lastname","Birthdate","Gender","Address","Phone"})
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@NotBlank(message = "Le prénom doit être renseigné")
	@Column(name="firstname")
	private String firstname;
	
	@NotBlank(message = "Le nom doit être renseigné")
	@Column(name="lastname")
	private String lastname;
	
	@NotNull(message = "La date de naissance doit être renseignée")
	@Column(name="birthdate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="dd/MM/yyyy")
	LocalDate birthdate;
	
	@NotBlank(message = "Le sexe doit être renseigné")
	@Pattern(regexp ="([MmFf])", message="Pour renseigner le sexe, entrez M (Male) ou F (Female)")
	@Column(name="gender")
	private String gender;
	
	@NotBlank(message = "L\'adresse doit être renseignée")
	@Column(name="address")
	private String address;
	
	@NotBlank(message = "Le n° de téléphone doit être renseigné")
	@Column(name="phone")
	private String phone;
	

	public Patient() {}
	
	public Patient(@NotBlank String firstname, @NotBlank String lastname, @NotNull LocalDate birthdate,
			@NotBlank String gender, @NotBlank String address, @NotBlank String phone) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", birthdate=" + birthdate
				+ ", gender=" + gender + ", address=" + address + ", phone=" + phone + "]";
	}

	@JsonGetter("Id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	@JsonGetter("Birthdate")
	public LocalDate getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	@JsonGetter("Gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	@JsonGetter("Address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@JsonGetter("Phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
