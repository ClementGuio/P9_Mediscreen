package com.mediscreen.docnoteapi.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediscreen.docnoteapi.model.Note;
import com.mediscreen.docnoteapi.repository.NoteRepository;

@Service
public class NoteService {
	
	Logger logger = LoggerFactory.getLogger(NoteService.class);
	
	@Autowired
	private NoteRepository repo;
	
	
	//TODO: supprimer
	public void deleteAll() {
		repo.deleteAll();
	}
	
	public List<Note> getAllNotes(){
		logger.info("getAllNotes");
		return repo.findAll();
	}
	
	public Optional<Note> getById(ObjectId noteId){
		return repo.findById(noteId);
	}
	
	public Note addOrUpdateNote(Note note) {
		logger.info("addNote : "+note);
		return repo.save(note);
	}
	
	public void deleteNote(ObjectId id) {
		logger.info("deleteNote : "+id);
		repo.deleteById(id);
	}
	
	public List<Note> getNotesOfPatient(Integer patientId){
		logger.info("getNotesOfPatient : "+patientId);
		return repo.findByPatientId(patientId);
	}
	
}
