package com.mediscreen.riskreport.controller;

import java.io.IOException;
import java.text.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.riskreport.exception.NoteNotFoundException;
import com.mediscreen.riskreport.exception.UnreachableDataException;
import com.mediscreen.riskreport.model.PatientReport;
import com.mediscreen.riskreport.service.RiskAnalyzer;
import com.mediscreen.riskreport.util.NotesFetcher;

@RestController
@CrossOrigin("${url.docnoteapi}")
@RequestMapping("/riskreport")
public class ReportController {
	
	Logger logger = LoggerFactory.getLogger(ReportController.class);
	
	@Value("${url.docnoteapi}")
	private String urlDocnoteApi;
	
	@Autowired
	RiskAnalyzer riskAnalyzer;
	
	@Autowired
	NotesFetcher fetcher;
	
	@Autowired
	ObjectMapper mapper;
	
	@GetMapping("/assess")
	public JsonNode reportRisk(@RequestParam Integer patientId, Model model) throws UnreachableDataException, NoteNotFoundException {
		logger.debug("GET /assess?patientId="+patientId);
		
		PatientReport report = buildReport(patientId);
		riskAnalyzer.report(report);
		model.addAttribute("report", report);
		
		return mapper.valueToTree(report);
	}
	
	public PatientReport buildReport(Integer patientId) throws NoteNotFoundException, UnreachableDataException {
		PatientReport report;
		try {
			report = fetcher.fetchNote(urlDocnoteApi, patientId);
		}catch(IOException e1) {
			e1.printStackTrace();
			throw new UnreachableDataException("This data is note available. Please try again later or verify the patientId.");
		}catch(InterruptedException e2){
			e2.printStackTrace();
			throw new UnreachableDataException("This data is note available. Please try again later.");
		}catch(ParseException e3) {
			e3.printStackTrace();
			throw new UnreachableDataException("This data is note available. Please try again later.");
		}
		
		return report;
	}

}
