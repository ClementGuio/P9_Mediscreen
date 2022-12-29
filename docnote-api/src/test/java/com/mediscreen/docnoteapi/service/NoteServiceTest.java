package com.mediscreen.docnoteapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mediscreen.docnoteapi.model.Note;

@SpringBootTest
public class NoteServiceTest {

	@Autowired
	private NoteService service;
	
	@Test
	public void noteServiceTest() {

		//Save
		Note note1 = new Note(0,"firstname","lastname",LocalDate.now(),"m","note1Test");
		Note note2 = new Note(0,"firstname","lastname",LocalDate.now(),"m","note2Test");
		Note note3 = new Note(-1,"firstname","lastname",LocalDate.now(),"f","note3Test");
		service.addOrUpdateNote(note1);
		service.addOrUpdateNote(note2);
		service.addOrUpdateNote(note3);		
		assertThat(service.getById(note1.getId()).isPresent());
		assertThat(service.getById(note2.getId()).isPresent());
		assertThat(service.getById(note3.getId()).isPresent());

		assertThat(service.getNotesOfPatient(0).size()==2);
		
		//update
		note3.setComment("NEW");
		service.addOrUpdateNote(note3);
		assertThat(service.getById(note3.getId()).isPresent());

		//delete
		service.deleteNote(note1.getId());;
		service.deleteNote(note2.getId());
		service.deleteNote(note3.getId());
		assertThat(service.getById(note1.getId()).isEmpty());
		assertThat(service.getById(note2.getId()).isEmpty());
		assertThat(service.getById(note3.getId()).isEmpty());
	}
}
