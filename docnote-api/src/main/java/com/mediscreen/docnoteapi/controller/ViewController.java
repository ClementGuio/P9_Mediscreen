package com.mediscreen.docnoteapi.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Optional;

import javax.validation.Valid;

import org.bson.types.ObjectId;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mediscreen.docnoteapi.exception.UnknownDataException;
import com.mediscreen.docnoteapi.model.Note;
import com.mediscreen.docnoteapi.service.NoteService;
import com.mediscreen.docnoteapi.util.PatientFetcher;
import com.mediscreen.docnoteapi.util.UrlResolver;

@Controller
@RequestMapping("/docnote")
@CrossOrigin({"${url.ui}","${url.patientapi}"})
public class ViewController {

	Logger logger = LoggerFactory.getLogger(ViewController.class);
	
	@Autowired
	UrlResolver resolver;
	
	@Value("${host.ui}")
	private String hostUi;
	
	@Value("${port.ui}")
	private String portUi;
	
	@Value("${url.patientapi}")
	private String urlPatientApi;
	
	@Autowired
	private NoteController noteController;
	
	@Autowired
	private NoteService service;
	
	@Autowired
	PatientFetcher fetcher;
	
	@GetMapping("/add/{patientId}")
	public String viewAdd(@PathVariable("patientId") Integer patientId,  Model model) throws IOException, InterruptedException, ParseException {
		logger.debug("GET /docnote/add");
		
		Note note = fetcher.fetchPatient(urlPatientApi, patientId);
		model.addAttribute("note",note);
		return "add";
	}
	
	@GetMapping("/patientHistory/{patientId}")
	public String viewHistory(@PathVariable("patientId") Integer patientId, Note note, Model model) throws UnknownHostException {
		logger.debug("GET /patientHistory/"+patientId);
		
		String resolvedUrlUi = resolver.buildResolvedUrl(hostUi, portUi); 
		logger.info("resolvedUrlUI : "+resolvedUrlUi);
		noteController.getNotesOfPatient(patientId, model);
		model.addAttribute("urlUI", resolvedUrlUi);
		return "history";
	}
	
	@GetMapping("/get/{id}")
	public String viewGet(@PathVariable("id") ObjectId id, Model model) throws UnknownDataException {
		logger.debug("GET /docnote/get/"+id);
		
		noteController.getNote(id, model);
		return "get";
	}
	
	@GetMapping("/update/{id}")
	public String viewUpdate(@PathVariable("id") ObjectId id, Model model) throws UnknownDataException {
		logger.debug("GET /docnote/update/"+id);
		
		noteController.getNote(id, model);
		return "update";
	}
	
	@GetMapping("/delete/{id}")
	public String viewDelete(@PathVariable("id") ObjectId id,Model model) throws UnknownDataException {
		logger.debug("GET /docnote/delete/"+id); 
		
		noteController.getNote(id,model);
		return "delete";
	}
	
	@PostMapping("/add/submit")
	public String submitAdd(@Valid Note note, BindingResult bindingResult,Model model) throws JsonMappingException, JsonProcessingException, ParseException {
		logger.debug("POST /docnote/add/submit");
		
		if (!bindingResult.hasErrors()) {
			service.addOrUpdateNote(note);
			noteController.getNotesOfPatient(note.getPatientId(), model);
			return "redirect:/docnote/patientHistory/"+note.getPatientId(); 
		}
		return "add";
	} 
	
	@PostMapping("/update/{id}/submit")
	public String submitUpdate(@Valid Note note, @PathVariable("id") ObjectId id, BindingResult bindingResult, Model model) throws UnknownDataException {
		logger.debug("POST /docnote/update/"+id+"/submit");
		
		if(!bindingResult.hasErrors()) {
			noteController.updateNote(note.getComment(), id);
			noteController.getNotesOfPatient(note.getPatientId(), model);
			return "redirect:/docnote/patientHistory/"+note.getPatientId(); 
		}
		return "update";
	}
	
	@GetMapping("/delete/{id}/confirm")
	public String confirmDelete(@PathVariable("id") ObjectId id, Model model) throws UnknownDataException {
		logger.debug("GET /docnote/delete/"+id+"/confirm");
		
		Optional<Note> opt = service.getById(id);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This note does not exist.");
		}
		Note note = opt.get();
		noteController.deleteNote(id);
		noteController.getNotesOfPatient(note.getPatientId(), model);
		return "redirect:/docnote/patientHistory/"+note.getPatientId(); 
	}
	
}
