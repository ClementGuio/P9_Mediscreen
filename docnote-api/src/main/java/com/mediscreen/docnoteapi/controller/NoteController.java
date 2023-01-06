package com.mediscreen.docnoteapi.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.docnoteapi.exception.UnknownDataException;
import com.mediscreen.docnoteapi.exception.UnreachableDataException;
import com.mediscreen.docnoteapi.model.Note;
import com.mediscreen.docnoteapi.service.NoteService;
import com.mediscreen.docnoteapi.util.PatientFetcher;

@RequestMapping("/docnoteapi")
@CrossOrigin("${url.riskreport}")
@RestController
public class NoteController {
	
	Logger logger = LoggerFactory.getLogger(NoteController.class);
	
	@Value("${url.patientapi}")
	String urlPatientApi;
	
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private NoteService service;

	@Autowired
	private PatientFetcher fetcher;
	
	@GetMapping("/get")
	public JsonNode getNote(@RequestParam ObjectId noteId, Model model) throws UnknownDataException {
		logger.debug("GET /docnote/get/"+noteId);
		
		Optional<Note> opt = service.getById(noteId);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This note doesn't exist.");
		}
		Note note = opt.get();
		model.addAttribute("note", note);
		return mapper.valueToTree(note);
	}

	@GetMapping("/get/patient")
	public JsonNode getNotesOfPatient(@RequestParam Integer patientId, Model model) {
		logger.debug("GET /docnote/get/patient?patientId="+patientId);
		
		List<Note> notes = service.getNotesOfPatient(patientId);
		model.addAttribute("notes", notes);
		return mapper.valueToTree(notes);
	}
	
	@PostMapping("/add")
	public void addNote(@RequestParam Integer patientId, @RequestBody String comment) throws UnreachableDataException {
		logger.debug("POST /docnote/add?patientId="+patientId+"&comment="+comment);
		Note note;
		try {
			note = fetcher.fetchPatient(urlPatientApi,patientId);
		}catch(IOException e1) {
			throw new UnreachableDataException("This data is note available. Please try again later or verify the patientId.");
		}catch(InterruptedException e2) {
			throw new UnreachableDataException("This data is note available. Please try again later.");
		}catch(ParseException e3) {
			throw new UnreachableDataException("This data is note available. Please try again later.");
		}
		note.setComment(comment);
		Note saved = service.addOrUpdateNote(note);
		logger.info("Success while adding note(id="+saved.getId());
	}
	
	@PutMapping("/update")
	public void updateNote(@RequestBody String comment, @RequestParam ObjectId noteId) throws UnknownDataException {
		logger.debug("PUT /docnote/update?="+noteId+"&comment="+comment);
		
		Optional<Note> opt = service.getById(noteId);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This note doesn't exist.");
		}
		Note note = opt.get();
		note.setComment(comment);
		Note updated = service.addOrUpdateNote(note);
		logger.info("Success while updating note(id="+updated.getId());
	}

	@DeleteMapping("/delete")
	public void deleteNote(@RequestParam ObjectId noteId) {
		logger.debug("DELETE /docnote/delete?noteId="+noteId);
		
		service.deleteNote(noteId);
	}	
}