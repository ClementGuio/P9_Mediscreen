package com.mediscreen.patientapi.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
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
//TODO: Revoir le nom racine 
@RestController
@RequestMapping("/patientapi")
@CrossOrigin("${url.docnoteapi}")
public class PatientController {

	Logger logger = LoggerFactory.getLogger(PatientController.class);
	
	@Autowired
	private PatientService service;
	
	@Autowired
	ObjectMapper mapper;
	
	@GetMapping("/get")
	public JsonNode getPatient(@RequestParam Integer id, Model model) throws UnknownDataException {
		
		logger.debug("GET /patient/get?id="+id);
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
		logger.debug("GET /patient/getAll");
		List<Patient> allPatients = service.getAllPatients();
		model.addAttribute("patientlist", allPatients);
		return mapper.valueToTree(allPatients);
	}
	//TODO: pourquoi IllegalArgumentException
	@PostMapping("/add")
	public void addPatient(@RequestBody Patient patient) throws IllegalArgumentException{
		logger.debug("POST /patient/add?firstname="+patient.getFirstname()+"&lastname="+patient.getLastname()+"&birthdate="+patient.getBirthdate()+
				"&gender="+patient.getGender()+"&address="+patient.getAddress()+"&phone="+patient.getPhone());
		
		Patient saved = service.savePatient(patient);
		logger.debug("Success while adding patient(id="+saved.getId()+")");
	}
	
	@PutMapping("/update")
	public void updatePatient(@RequestBody Patient patient, @RequestParam Integer id) throws UnknownDataException,IllegalArgumentException {
		
		logger.debug("PUT /patient/update/"+id+"?firstname="+patient.getFirstname()+"&lastname="+patient.getLastname()+"&birthdate="+patient.getBirthdate()+
				"&gender="+patient.getGender()+"&address="+patient.getAddress()+"&phone="+patient.getPhone());
		
		Optional<Patient> opt = service.getPatient(id);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This patient does not exist."); //FIXME: Réecrire message partout !!
		}
		patient.setId(id);
		
		patient = service.savePatient(patient);
		logger.debug("Success while updating patient : "+patient);
	}
	
	@DeleteMapping("/delete")
	public void deletePatient(@RequestParam Integer id) throws UnknownDataException {
		logger.info("DELETE /patient/delete/"+id);
		if(service.getPatient(id).isEmpty()) {
			throw new UnknownDataException("This patient does not exist.");
		}
			service.deletePatient(id);
	}
}
