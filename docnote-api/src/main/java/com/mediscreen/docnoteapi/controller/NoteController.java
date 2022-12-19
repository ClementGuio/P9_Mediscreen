package com.mediscreen.docnoteapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.docnoteapi.exception.UnknownDataException;
import com.mediscreen.docnoteapi.model.Note;
import com.mediscreen.docnoteapi.service.NoteService;

@RequestMapping("/docnoteapi")
@RestController
public class NoteController {
	
	Logger logger = LoggerFactory.getLogger(NoteController.class);
	
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private NoteService service;
	
	@GetMapping("/get/{noteId}")
	public JsonNode getNote(@PathVariable("noteId") ObjectId noteId, Model model) throws UnknownDataException {
		logger.info("GET /docnote/get/"+noteId);
		Optional<Note> opt = service.getById(noteId);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This note doesn't exist.");
		}
		Note note = opt.get();
		model.addAttribute("note", note);
		return mapper.valueToTree(note);
	}
	
	@GetMapping("/get/patient/{patientId}")
	public JsonNode getNotesOfPatient(@PathVariable("patientId") Integer patientId, Model model) {
		logger.info("GET /docnote/get/patient/"+patientId);
		
		List<Note> notes = service.getNotesOfPatient(patientId);
		model.addAttribute("notes", notes);
		return mapper.valueToTree(notes);
	}

	@PostMapping("/add")
	public void addNote(@RequestBody @Valid Note note) {
		logger.info("POST /docnote/add?patientID="+note.getPatientId()+"&firstname="+note.getFirstname()+"&lastname="+note.getLastname()+"&comment="+note.getComment());
		
		note.setId(new ObjectId());
		Note saved = service.addOrUpdateNote(note);
		logger.info("Success while adding note(id="+saved.getId());
	}
	
	@PutMapping("/update/{id}")
	public void updateNote(@RequestBody Note note, @PathVariable("id") ObjectId id) throws UnknownDataException {
		logger.info("PUT /docnote/update/"+id+"?patientID="+note.getPatientId()+"&firstname="+note.getFirstname()+"&lastname="+note.getLastname()+"&comment="+note.getComment());
		
		Optional<Note> opt = service.getById(id);
		if (!opt.isPresent()) {
			throw new UnknownDataException("This note doesn't exist.");
		}
		note.setId(id);
		Note updated = service.addOrUpdateNote(note);
		logger.info("Success while updating note(id="+updated.getId());
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteNote(@PathVariable ObjectId id) {
		logger.info("DELETE /docnote/delete/"+id);
		
		service.deleteNote(id);
	}
	
}
