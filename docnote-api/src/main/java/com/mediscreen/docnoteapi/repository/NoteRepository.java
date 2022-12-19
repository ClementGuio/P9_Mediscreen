package com.mediscreen.docnoteapi.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mediscreen.docnoteapi.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

	public List<Note> findByPatientId(Integer patientId);
	
	public Optional<Note> findById(ObjectId id);
	
	public void deleteById(ObjectId id);
}
