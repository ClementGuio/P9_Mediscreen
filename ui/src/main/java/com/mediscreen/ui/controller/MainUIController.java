package com.mediscreen.ui.controller;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//TODO: utiliser docker-compose
//TODO: externaliser le code JS
@CrossOrigin(origins = {"${url.patientapi}","${url.docnoteapi}"})   
@Controller
public class MainUIController {

	Logger logger = LoggerFactory.getLogger(MainUIController.class);
	
	@Value("${url.patientapi}")
	private String urlPatient;
		
	@Value("${url.docnoteapi}")
	private String urlDocnote;
	
	@GetMapping("/")
	public String home() {
		logger.info("GET /");
		return "home";
	} 
	
	//TODO : Affichage des erreurs + créer template dynamique pour les erreurs
	@GetMapping("/patient/error404")
	public String patient404() {
		logger.info("GET /patient/error404");
		return "404";
	} 
	
	@GetMapping("/patient")
	public String patientHome(Model model) {
		logger.info("GET /patient");
		model.addAttribute("urlPatientApi", urlPatient);
		return "patient/list";
	}
	
	@GetMapping("/patient/{id}")
	public String viewGetPatient(@PathVariable("id") Integer id, Model model) {
	 	logger.info("GET /patient/"+id);
	 	model.addAttribute("urlPatientApi", urlPatient);
		return "patient/get"; 
	}
	
	@GetMapping("/patient/add")
	public String viewAddPatient(Model model) {
		logger.info("GET /patient/add");
		
		model.addAttribute("urlPatientApi", urlPatient);
		return "patient/add";
	}
	
	@GetMapping("/patient/update/{id}")
	public String viewUpdatePatient(@PathVariable("id") Integer id, Model model) {
		logger.info("GET /patient/update/"+id);
		model.addAttribute("urlPatientApi", urlPatient);
		return "patient/update";
	}
	
	@GetMapping("/patient/delete/{id}")
	public String viewDeletePatient(@PathVariable("id") Integer id, Model model) {
		logger.info("GET /patient/delete/"+id);
		model.addAttribute("urlPatientApi", urlPatient);
		return "patient/delete";
		
	}
	
	@GetMapping("/docnote/history/{patientId}")
	public String viewNoteHistory(@PathVariable("patientId") Integer patientId, Model model) {
		logger.info("GET /docnote/history/"+patientId);
		System.out.println(urlDocnote);
		model.addAttribute("urlDocnoteApi", urlDocnote);
		return "docnote/history";
	}
	
	@GetMapping("/docnote/{id}")
	public String viewNote(@PathVariable("id") ObjectId id, Model model) {
		logger.info("GET /docnote/"+id);
		model.addAttribute("urlDocnoteApi", urlDocnote);
		return "docnote/get";
	}
	
	@GetMapping("/docnote/update/{id}")
	public String viewUpdateNote(@PathVariable("id") ObjectId id, Model model) {
		logger.info("GET /docnote/update/"+id);
		model.addAttribute("urlDocnoteApi", urlDocnote);
		return "docnote/update";
	}
	
	@GetMapping("/docnote/add")
	public String viewAddDocnote(Model model) {
		logger.info("GET /docnote/add");
		model.addAttribute("urlDocnoteApi", urlDocnote);
		return "docnote/add";
	}
	
	@GetMapping("/docnote/delete/{id}")
	public String viewDeleteDocnote(@PathVariable("id") ObjectId id, Model model) {
		logger.info("GET /docnote/delete/"+id);
		model.addAttribute("urlDocnoteApi", urlDocnote);
		return "docnote/delete";
	}
}
 