package com.mediscreen.patientapi.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.mediscreen.patientapi.exception.UnknownDataException;
import com.mediscreen.patientapi.model.Patient;

@Controller
@RequestMapping("/patient")
@CrossOrigin("${url.ui}")
public class TestUIController {

	Logger logger = LoggerFactory.getLogger(TestUIController.class);
	
	@Autowired
	PatientController patientController;
	
	@GetMapping("/add")
	public String viewAdd(Patient patient) {
		logger.info("GET /patient/add");
		return "add";
	}
	
	@GetMapping("/list")
	public String viewList(Model model) {
		logger.info("GET /patient/list");
		patientController.getAllPatients(model);
		return "list";
	}
	
	@GetMapping("/get/{id}")
	public String viewGet(Model model,@PathVariable("id") Integer id) throws UnknownDataException, IOException {
		logger.info("GET /patient/get");
		
		patientController.getPatient(id,model);
		return "get";
	}
	
	//NOTE : OK!
	@PostMapping("/submit")
	public String submitAdd(@Valid Patient patient, BindingResult bindingResult) {
		logger.info("POST /patient/submit?firstname="+patient.getFirstname()+"&lastname="+patient.getLastname()+"&birthdate="+patient.getBirthdate()+
				"&gender="+patient.getGender()+"&address="+patient.getAddress()+"&phone="+patient.getPhone());
		
		if (!bindingResult.hasErrors()) {
			patientController.addPatient(patient);
			return "redirect:/patient/loading"; //TODO: redirection vers rapport de succés
		}
		return "add";
	}
	
}
