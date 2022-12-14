package com.mediscreen.patientapi.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/patientapi")
public class PatientController {

	Logger logger = LoggerFactory.getLogger(PatientController.class);
	
	@Autowired
	private PatientService service;
	
	@Autowired
	ObjectMapper mapper;
	
	
	
	//@GetMapping("/update")
	//public String viewUpdate()
	/*
	@GetMapping("/get")
	public JsonNode getPatient(@RequestParam String firstname, @RequestParam String lastname, Model model) throws UnknownDataException {
		
		logger.info("GET /patient/get?firstname="+firstname+"&lastname="+lastname);
		Optional<Patient> opt = service.getPatient(firstname, lastname);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This patient does not exist.");
		}
		Patient patient = opt.get();
		model.addAttribute("patient",patient);
		return mapper.valueToTree(patient);
	}
	*/
	
	//TODO: virer Model
	@GetMapping("/get")
	public JsonNode getPatient(@RequestParam Integer id, Model model) throws UnknownDataException {
		
		logger.info("GET /patient/get?id="+id);
		Optional<Patient> opt = service.getPatient(id);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This patient does not exist.");
		}
		Patient patient = opt.get();
		model.addAttribute("patient",patient);
		return mapper.valueToTree(patient);
	}
	
	@GetMapping("/getAll")
	public JsonNode getAllPatients(Model model) {
		logger.info("GET /patient/getAll");
		List<Patient> allPatients = service.getAllPatients();
		model.addAttribute("patientlist", allPatients);
		return mapper.valueToTree(allPatients);
	}
	
	//TODO: vérifier que le patient n'existe pas déjà
	//NOTE : OK!!
	@PostMapping("/add")
	public void addPatient(@RequestBody Patient patient) throws IllegalArgumentException{
		logger.info("POST /patient/add?firstname="+patient.getFirstname()+"&lastname="+patient.getLastname()+"&birthdate="+patient.getBirthdate()+
				"&gender="+patient.getGender()+"&address="+patient.getAddress()+"&phone="+patient.getPhone());
		
		Patient saved = service.savePatient(patient);
		logger.info("Success while adding patient(id="+saved.getId()+")");
	}
/*	
	@PutMapping("/update")
	public void updatePatient(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String birthdate, 
			@RequestParam String gender, @RequestParam String address, @RequestParam String phone) throws UnknownDataException,IllegalArgumentException {
		
		logger.info("PUT /patient/update?firstname="+firstname+"&lastname="+lastname+"&birthdate="+birthdate+"&gender="+gender+"&address="+address+"&phone="+phone);
		Optional<Patient> opt = service.getPatient(firstname, lastname);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This patient does not exist.");
		}
		Integer id = opt.get().getId();
		Patient patient = new Patient(firstname, lastname, Date.parse(birthdate), gender, address, phone);
		patient.setId(id);
		service.savePatient(patient);	
	}
	*/
	@DeleteMapping("/delete")
	public void deletePatient(@RequestParam String firstname, @RequestParam String lastname) {
		
		logger.info("DELETE /patient/delete?firsname="+firstname+"&lastname="+lastname);
		service.deletePatient(firstname, lastname);
	}
}
