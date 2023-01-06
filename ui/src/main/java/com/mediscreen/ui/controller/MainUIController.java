package com.mediscreen.ui.controller;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mediscreen.ui.util.UrlResolver;

@RequestMapping("/mediscreen")
@CrossOrigin(origins = {"${url.patientapi}","${url.docnoteapi}","${url.riskreport}"})   
@Controller
public class MainUIController {

	Logger logger = LoggerFactory.getLogger(MainUIController.class);
	
	@Autowired
	UrlResolver resolver;
	
	@Value("${host.patientapi}")
	private String hostPatient;
	
	@Value("${port.patientapi}")
	private String portPatient;
	
	@Value("${host.docnoteapi}")
	private String hostDocnote;
	
	@Value("${port.docnoteapi}")
	private String portDocnote;
	
	@Value("${host.riskreport}")
	private String hostRiskreport;
	
	@Value("${port.riskreport}")
	private String portRiskreport;
	
	@GetMapping("/404")
	public String patient404() {
		logger.debug("GET /patient/404");
		return "404";
	} 
	
	@GetMapping("/patient")
	public String patientHome(Model model) throws UnknownHostException {
		logger.debug("GET /patient");
		
		String urlPatient = resolver.buildResolvedUrl(hostPatient, portPatient);
		
		model.addAttribute("urlPatientApi", urlPatient);
		return "patientList";
	}
	
	@GetMapping("/history/{patientId}")
	public String viewNoteHistory(@PathVariable("patientId") Integer patientId, Model model) throws UnknownHostException {
		logger.debug("GET /docnote/history/"+patientId);
		
		String urlDocnote = resolver.buildResolvedUrl(hostDocnote, portDocnote);
		String urlRiskreport = resolver.buildResolvedUrl(hostRiskreport, portRiskreport);
		
		model.addAttribute("urlDocnoteApi", urlDocnote);
		model.addAttribute("urlRiskReport", urlRiskreport);
		return "patientHistory";
	}
}
 