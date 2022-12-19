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
//TODO: 
@RestController
@RequestMapping("/patientapi")
public class PatientController {

	Logger logger = LoggerFactory.getLogger(PatientController.class);
	
	@Autowired
	private PatientService service;
	
	@Autowired
	ObjectMapper mapper;
	
	@GetMapping("/get")
	public JsonNode getPatient(@RequestParam Integer id, Model model) throws UnknownDataException {
		
		logger.info("GET /patient/get?id="+id);
		Optional<Patient> opt = service.getPatient(id);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This patient does not exist.");
		}
		Patient patient = opt.get();
		model.addAttribute("patient",patient);
		System.out.println("patient : "+patient);
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

	@PutMapping("/update/{id}")
	public void updatePatient(@RequestBody Patient patient, @PathVariable Integer id) throws UnknownDataException,IllegalArgumentException {
		
		logger.info("PUT /patient/update/"+id+"?firstname="+patient.getFirstname()+"&lastname="+patient.getLastname()+"&birthdate="+patient.getBirthdate()+
				"&gender="+patient.getGender()+"&address="+patient.getAddress()+"&phone="+patient.getPhone());
		
		Optional<Patient> opt = service.getPatient(id);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This patient does not exist."); //TODO: Réecrire message partout !!
		}
		patient.setId(id);
		Patient updated = service.savePatient(patient);
		logger.info("Success while updating patient(id="+updated.getId()+")");
	}
	
	@DeleteMapping("/delete/{id}")
	public void deletePatient(@PathVariable("id") Integer id) {
		
		logger.info("DELETE /patient/delete/"+id);
		service.deletePatient(id);
	}
}
