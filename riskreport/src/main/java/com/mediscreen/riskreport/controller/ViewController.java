package com.mediscreen.riskreport.controller;

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

import com.mediscreen.riskreport.exception.NoteNotFoundException;
import com.mediscreen.riskreport.exception.UnreachableDataException;
import com.mediscreen.riskreport.model.PatientReport;

@Controller
@RequestMapping("/riskreport")
@CrossOrigin({"${url.ui}","${url.docnoteapi}"})
public class ViewController {

	Logger logger = LoggerFactory.getLogger(ViewController.class);
	
	@Autowired
	ReportController controller;
	
	@GetMapping("/patient/{patientId}")
	public String viewReport(@PathVariable("patientId") Integer id, Model model) throws UnreachableDataException, NoteNotFoundException {
		
		controller.reportRisk(id,model);
		
		return "report";
	}
	
	@GetMapping("/test")
	public String viewTest() {
		logger.info("GET /test");
		return "test";
	}
}
