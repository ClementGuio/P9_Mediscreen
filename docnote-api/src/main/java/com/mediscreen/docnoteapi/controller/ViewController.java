package com.mediscreen.docnoteapi.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
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

@Controller
@RequestMapping("/docnote")
@CrossOrigin({"${url.ui}","${url.patientapi}"})
public class ViewController {

	Logger logger = LoggerFactory.getLogger(ViewController.class);
	
	@Value("${url.ui}")
	private String urlUI;
	
	@Value("${url.patientapi}")
	private String urlPatientApi;
	
	@Autowired
	private NoteController noteController;
	
	@Autowired
	private NoteService service;
	
	@Autowired
	PatientFetcher fetcher;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@GetMapping("/add/{patientId}")
	public String viewAdd(@PathVariable("patientId") Integer patientId,  Model model) throws IOException, InterruptedException, ParseException {
		logger.info("GET /docnote/add");
		
		Note note = fetcher.fetchPatient(urlPatientApi, patientId); //TODO: traiter les exceptions
		model.addAttribute("note",note);
		return "add";
	}
	
	@GetMapping("/patientHistory/{patientId}")
	public String viewHistory(@PathVariable("patientId") Integer patientId, Note note, Model model) {
		logger.info("GET /patientHistory/"+patientId);
		
		noteController.getNotesOfPatient(patientId, model);
		model.addAttribute("urlUI", urlUI);
		return "history";
	}
	
	@GetMapping("/get/{id}")
	public String viewGet(@PathVariable("id") ObjectId id, Model model) throws UnknownDataException {
		logger.info("GET /docnote/get/"+id);
		
		noteController.getNote(id, model);
		return "get";
	}
	
	@GetMapping("/update/{id}")
	public String viewUpdate(@PathVariable("id") ObjectId id, Model model) throws UnknownDataException {
		logger.info("GET /docnote/update/"+id);
		
		noteController.getNote(id, model);
		return "update";
	}
	
	@GetMapping("/delete/{id}")
	public String viewDelete(@PathVariable("id") ObjectId id,Model model) throws UnknownDataException {
		logger.info("GET /docnote/delete/"+id); 
		
		noteController.getNote(id,model);
		return "delete";
	}
	
	@PostMapping("/add/submit")
	public String submitAdd(@Valid Note note, BindingResult bindingResult,Model model) throws JsonMappingException, JsonProcessingException, ParseException {
		logger.info("POST /docnote/add/submit");
		
		if (!bindingResult.hasErrors()) {
			service.addOrUpdateNote(note);
			noteController.getNotesOfPatient(note.getPatientId(), model);
			return "redirect:/docnote/patientHistory/"+note.getPatientId(); 
		}
		return "add";
	} 
	
	@PostMapping("/update/{id}/submit")
	public String submitUpdate(@Valid Note note, @PathVariable("id") ObjectId id, BindingResult bindingResult, Model model) throws UnknownDataException {
		logger.info("POST /docnote/update/"+id+"/submit");
		
		if(!bindingResult.hasErrors()) {
			noteController.updateNote(note.getComment(), id);
			noteController.getNotesOfPatient(note.getPatientId(), model);
			return "redirect:/docnote/patientHistory/"+note.getPatientId(); 
		}
		return "update";
	}
	
	@GetMapping("/delete/{id}/confirm")
	public String confirmDelete(@PathVariable("id") ObjectId id, Model model) throws UnknownDataException {
		logger.info("GET /docnote/delete/"+id+"/confirm");
		
		Optional<Note> opt = service.getById(id);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This note does not exist.");
		}
		Note note = opt.get();
		noteController.deleteNote(id);
		noteController.getNotesOfPatient(note.getPatientId(), model);
		return "redirect:/docnote/patientHistory/"+note.getPatientId(); 
	}
	
	@GetMapping("/test")
	public String viewTest() {
		logger.info("GET /test");
		return "test";
	}
}
