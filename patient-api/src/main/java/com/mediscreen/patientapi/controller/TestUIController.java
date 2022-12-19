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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.mediscreen.patientapi.exception.UnknownDataException;
import com.mediscreen.patientapi.model.Patient;

@Controller
@RequestMapping("/patient")
@CrossOrigin("${url.ui}")
public class TestUIController {

	Logger logger = LoggerFactory.getLogger(TestUIController.class);
	
	@Value("${url.ui}")
	private String urlUI;
	
	@Autowired
	private PatientController patientController;
	
	@GetMapping("/add")
	public String viewAdd(Patient patient) {
		logger.info("GET /patient/add");
		return "add";
	}
	
	@GetMapping("/list")
	public String viewList(Model model) {
		logger.info("GET /patient/list");
		patientController.getAllPatients(model);
		model.addAttribute("urlUI", urlUI);
		return "list";
	}
	
	@GetMapping("/get/{id}")
	public String viewGet(Model model,@PathVariable("id") Integer id) throws UnknownDataException {
		logger.info("GET /patient/get/"+id);
		
		patientController.getPatient(id,model);
		return "get";
	}
	
	@GetMapping("/update/{id}")
	public String viewUpdate(Model model, @PathVariable("id") Integer id) throws UnknownDataException{
		logger.info("GET /patient/update/"+id);
		
		patientController.getPatient(id,model);
		return "update";
	}
	
	@PostMapping("/add/submit")
	public String submitAdd(@Valid Patient patient, BindingResult bindingResult,Model model) {
		logger.info("POST /patient/submit?firstname="+patient.getFirstname()+"&lastname="+patient.getLastname()+"&birthdate="+patient.getBirthdate()+
				"&gender="+patient.getGender()+"&address="+patient.getAddress()+"&phone="+patient.getPhone());
		
		if (!bindingResult.hasErrors()) {
			patientController.addPatient(patient);
			return viewList(model); //TODO: redirect
		}
		return "add";
	}
	
	@PostMapping("/update/{id}/submit")
	public String submitUpdate(@Valid Patient patient, @PathVariable("id") Integer id, BindingResult bindingResult, Model model) throws UnknownDataException{
		logger.info("PUT /patient/submit?firstname="+patient.getFirstname()+"&lastname="+patient.getLastname()+"&birthdate="+patient.getBirthdate()+
				"&gender="+patient.getGender()+"&address="+patient.getAddress()+"&phone="+patient.getPhone());
		
		if (!bindingResult.hasErrors()) {
			patientController.updatePatient(patient,id);
			return viewList(model); //TODO: redirect
		}
		return "update";
	}
	
	@GetMapping("/delete/{id}")
	public String viewDelete(@PathVariable("id") Integer id, Model model) throws UnknownDataException {
		logger.info("GET /patient/delete/"+id);
		
		patientController.getPatient(id, model);
		return "delete";
	}
	
	@GetMapping("/delete/{id}/confirm")
	public String confirmDelete(@PathVariable("id") Integer id, Model model) throws UnknownDataException{
		logger.info("GET /delete/"+id+"/confirm");
		
		patientController.deletePatient(id);
		return viewList(model); //TODO: redirect
	}
}
