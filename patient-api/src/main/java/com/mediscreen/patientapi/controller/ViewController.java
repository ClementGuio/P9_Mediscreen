package com.mediscreen.patientapi.controller;

import java.net.UnknownHostException;

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
import org.springframework.web.bind.annotation.RequestMapping;

import com.mediscreen.patientapi.exception.UnknownDataException;
import com.mediscreen.patientapi.model.Patient;
import com.mediscreen.patientapi.service.PatientService;
import com.mediscreen.patientapi.util.UrlResolver;

@Controller
@RequestMapping("/patient")
@CrossOrigin({"${url.ui}","${url.docnoteapi}"})
public class ViewController {

	Logger logger = LoggerFactory.getLogger(ViewController.class);
	
	@Autowired
	private UrlResolver resolver;
	
	@Value("${host.ui}")
	private String hostUi;
	
	@Value("${port.ui}")
	private String portUi;
	
	@Autowired
	private PatientController patientController;
	
	@Autowired
	private PatientService service;
	
	@GetMapping("/add")
	public String viewAdd(Patient patient) {
		logger.debug("GET /patient/add");
		return "add";
	}
	
	@GetMapping("/list")
	public String viewList(Model model) throws UnknownHostException {
		logger.debug("GET /patient/list");
		
		String resolvedUrlUi = resolver.buildResolvedUrl(hostUi, portUi);
		
		logger.info("resolvedUrlUi : "+resolvedUrlUi);

		patientController.getAllPatients(model);
		model.addAttribute("urlUI", resolvedUrlUi);
		return "list";
	}
	//TODO: Virer /get
	@GetMapping("/get/{id}")
	public String viewGet(Model model,@PathVariable("id") Integer id) throws UnknownDataException {
		logger.debug("GET /patient/get/"+id);
		
		patientController.getPatient(id,model);
		return "get";
	}
	
	@GetMapping("/update/{id}")
	public String viewUpdate(Model model, @PathVariable("id") Integer id) throws UnknownDataException{
		logger.debug("GET /patient/update/"+id);
		
		patientController.getPatient(id,model);
		return "update";
	}
	
	@PostMapping("/add/submit")
	public String submitAdd(@Valid Patient patient, BindingResult bindingResult,Model model) {
		logger.debug("POST /patient/submit?firstname="+patient.getFirstname()+"&lastname="+patient.getLastname()+"&birthdate="+patient.getBirthdate()+
				"&gender="+patient.getGender()+"&address="+patient.getAddress()+"&phone="+patient.getPhone());
		
		if (!bindingResult.hasErrors()) {
			patientController.addPatient(patient);
			model.addAttribute("patientlist", service.getAllPatients());
			return "redirect:/patient/list";
		}
		return "add";
	}
	
	@PostMapping("/update/{id}/submit")
	public String submitUpdate(@Valid Patient patient, @PathVariable("id") Integer id, BindingResult bindingResult, Model model) throws UnknownDataException, IllegalArgumentException{
		logger.info("POST /patient/submit?firstname="+patient.getFirstname()+"&lastname="+patient.getLastname()+"&birthdate="+patient.getBirthdate()+
				"&gender="+patient.getGender()+"&address="+patient.getAddress()+"&phone="+patient.getPhone());
		
		if (!bindingResult.hasErrors()) {
			patientController.updatePatient(patient,id);
			model.addAttribute("patientlist", service.getAllPatients());
			return "redirect:/patient/list";
		}
		return "update";
	}
	
	@GetMapping("/delete/{id}/confirm")
	public String confirmDelete(@PathVariable("id") Integer id, Model model) throws UnknownDataException{
		logger.info("GET /delete/"+id+"/confirm");
		
		patientController.deletePatient(id);
		model.addAttribute("patientlist", service.getAllPatients());
		return "redirect:/patient/list";
	}
	
	@GetMapping("/test")
	public String viewTest() {
		logger.info("GET /test");
		return "test";
	}
}
