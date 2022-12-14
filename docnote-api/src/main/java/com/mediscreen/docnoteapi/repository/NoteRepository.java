package com.mediscreen.docnoteapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mediscreen.docnoteapi.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

	
}
