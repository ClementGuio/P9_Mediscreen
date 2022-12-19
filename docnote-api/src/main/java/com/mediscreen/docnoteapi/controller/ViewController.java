package com.mediscreen.docnoteapi.controller;

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

import com.mediscreen.docnoteapi.exception.UnknownDataException;
import com.mediscreen.docnoteapi.model.Note;

@Controller
@RequestMapping("/docnote")
@CrossOrigin("${url.ui}")
public class ViewController {

	Logger logger = LoggerFactory.getLogger(ViewController.class);
	
	@Value("${url.ui}")
	private String urlUI;
	
	@Autowired
	private NoteController noteController;
	
	@GetMapping("/add")
	public String viewAdd(Note note) {
		logger.info("GET /docnote/add");
		
		return "add";
	}
	
	@GetMapping("/patientHistory/{patientId}")
	public String viewHistory(@PathVariable("patientId") Integer patientId, Model model) {
		logger.info("GET /patientHistory/"+patientId);
		
		noteController.getNotesOfPatient(patientId, model);
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
	public String submitAdd(@Valid Note note, BindingResult bindingResult,Model model) {
		logger.info("POST /docnote/add/submit");

		if (!bindingResult.hasErrors()) {
			noteController.addNote(note);
			return viewHistory(note.getPatientId(),model); //TODO: redirect
		}
		return "add";
	} 
	
	@PostMapping("/update/{id}/submit")
	public String submitUpdate(@Valid Note note, @PathVariable("id") ObjectId id, BindingResult bindingResult, Model model) throws UnknownDataException {
		logger.info("POST /docnote/update/"+id+"/submit");
		
		if(!bindingResult.hasErrors()) {
			noteController.updateNote(note, id);
			return viewHistory(note.getPatientId(),model);
		}
		return "update";
	}
	
	@GetMapping("/delete/{patientId}/{id}/confirm")
	public String confirmDelete(@PathVariable("id") ObjectId id, @PathVariable("patientId") Integer patientId, Model model) {
		logger.info("GET /docnote/delete/"+id+"/confirm");
		
		noteController.deleteNote(id);
		return viewHistory(patientId,model);
	}
}
