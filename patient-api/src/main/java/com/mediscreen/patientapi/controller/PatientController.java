package com.mediscreen.patientapi.controller;

import java.sql.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patientapi.exception.UnknownDataException;
import com.mediscreen.patientapi.model.Patient;
import com.mediscreen.patientapi.service.PatientService;
//TODO: renvoyer ResponnseEntity pour spécifier les codes HTTP
@RestController
@RequestMapping("/patient")
public class PatientController {

	Logger logger = LoggerFactory.getLogger(PatientController.class);
	
	@Autowired
	private PatientService service;
	
	@Autowired
	ObjectMapper mapper;
	
	@GetMapping("/")
	public String hello() {
		return "Hello world!!!";
	}
	
	@GetMapping("/get")
	public JsonNode getPatient(@RequestParam String firstname, @RequestParam String lastname) throws UnknownDataException {
		
		logger.info("GET /patient/get?firstname="+firstname+"&lastname="+lastname);
		Optional<Patient> opt = service.getPatient(firstname, lastname);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This patient does not exist.");
		}
		Patient patient = opt.get();
		return mapper.valueToTree(patient);
	}
	
	@GetMapping("/getAll")
	public JsonNode getAllPatients() {
		
		logger.info("GET /patient/getAll");
		return mapper.valueToTree(service.getAllPatients());
	}
	
	//TODO: gérer la validation
	//TODO: @RequestBody
	//TODO: vérifier que le patient n'existe pas déjà
	@PostMapping("/add")
	public void addPatient(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String birthdate, 
			@RequestParam String gender, @RequestParam String address, @RequestParam String phone) throws IllegalArgumentException{
		
		logger.info("POST /patient/add?firstname="+firstname+"&lastname="+lastname+"&birthdate="+birthdate+"&gender="+gender+"&address="+address+"&phone="+phone);
		
		service.savePatient(new Patient(firstname, lastname, Date.valueOf(birthdate), gender, address, phone));
	}
	
	@PutMapping("/update")
	public void updatePatient(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String birthdate, 
			@RequestParam String gender, @RequestParam String address, @RequestParam String phone) throws UnknownDataException,IllegalArgumentException {
		
		logger.info("PUT /patient/update?firstname="+firstname+"&lastname="+lastname+"&birthdate="+birthdate+"&gender="+gender+"&address="+address+"&phone="+phone);
		Optional<Patient> opt = service.getPatient(firstname, lastname);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This patient does not exist.");
		}
		Integer id = opt.get().getId();
		Patient patient = new Patient(firstname, lastname, Date.valueOf(birthdate), gender, address, phone);
		patient.setId(id);
		service.savePatient(patient);
		
	}
	
	@DeleteMapping("/delete")
	public void deletePatient(@RequestParam String firstname, @RequestParam String lastname) {
		
		logger.info("DELETE /patient/delete?firsname="+firstname+"&lastname="+lastname);
		service.deletePatient(firstname, lastname);
	}
}
