package com.mediscreen.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//TODO : create controllers to expose url in application.properties
@CrossOrigin("${url.patientapi}")   
@Controller
public class MainUIController {

	Logger logger = LoggerFactory.getLogger(MainUIController.class);
	
	@GetMapping("/")
	public String home() {
		logger.info("GET /");
		return "home";
	} 
	//@CrossOrigin("http://localhost:8888")
	@GetMapping("/patient")
	public String patientHome() {
		logger.info("GET /patient");
		return "patient";
	}
	
	@GetMapping("/patient/error404")
	public String patient404() {
		logger.info("GET /patient/error404");
		return "patient404";
	} 
	
	@GetMapping("/patient/{id}")
	public String viewGetPatient(@PathVariable("id") Integer id) {
	 	logger.info("GET /patient/"+id);
		return "patientDetails"; 
	}
	
	@GetMapping("/patient/add")
	public String viewAddPatient() {
		logger.info("GET /patient/add");
		return "addpatient";
	}
}
 