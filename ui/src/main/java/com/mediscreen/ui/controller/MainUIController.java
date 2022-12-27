package com.mediscreen.ui.controller;

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
	
	@Value("${url.riskreport}")
	private String urlRiskReport;
	
	//FIXME : Affichage des erreurs + créer template dynamique pour les erreurs
	@GetMapping("/404")
	public String patient404() {
		logger.debug("GET /patient/404");
		return "404";
	} 
	
	@GetMapping("/patient")
	public String patientHome(Model model) {
		logger.info("GET /patient");
		model.addAttribute("urlPatientApi", urlPatient);
		return "patientList";
	}
	
	@GetMapping("/history/{patientId}")
	public String viewNoteHistory(@PathVariable("patientId") Integer patientId, Model model) {
		logger.info("GET /docnote/history/"+patientId);

		model.addAttribute("urlDocnoteApi", urlDocnote);
		model.addAttribute("urlRiskReport", urlRiskReport);
		return "patientHistory";
	}
}
 