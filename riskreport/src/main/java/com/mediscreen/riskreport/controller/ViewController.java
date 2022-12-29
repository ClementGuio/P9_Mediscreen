package com.mediscreen.riskreport.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	ReportController controller;
	
	@GetMapping("/{patientId}")
	public String viewReport(@PathVariable("patientId") Integer id, Model model) throws UnreachableDataException, NoteNotFoundException {
		
		PatientReport report = controller.buildReport(id);
		System.out.println("firsname : "+report.getFirstname());
		model.addAttribute("report", report);
		return "report";
	}
	
	
}
