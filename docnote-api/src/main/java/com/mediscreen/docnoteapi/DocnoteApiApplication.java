package com.mediscreen.docnoteapi;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mediscreen.docnoteapi.model.Note;
import com.mediscreen.docnoteapi.service.NoteService;

@SpringBootApplication
public class DocnoteApiApplication implements CommandLineRunner{

	@Autowired
	NoteService service;
	
	public static void main(String[] args) {
		SpringApplication.run(DocnoteApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Création d'une note
		Note note = new Note();
		note.setId("1");
		note.setFirstname("firstname");
		note.setLastname("lastname");
		note.setComment("DON'T PANIC !!!!");
		//Sauvegard de la note
		note = service.addOrUpdateNote(note);
		System.out.println("Note sauvé : "+note);
		Optional<Note> opt = service.getById(note.getId());
		note = opt.isPresent() ? opt.get() : null;
		System.out.println("Note récupéré : "+note);
		//Modification de la note
		note.setComment("NEVER FORGET YOUR TOWEL !");
		//Sauvegarde de la modification
		service.addOrUpdateNote(note);
		opt = service.getById(note.getId());
		note = opt.isPresent() ? opt.get() : null;
		System.out.println("ModifNote : "+note);
		//Suppression de la note
		service.deleteNote("1");
		System.out.println("Note1 : "+service.getById("1").isPresent());
		System.out.println("AllNotes : "+service.getAllNotes());
	}

}
