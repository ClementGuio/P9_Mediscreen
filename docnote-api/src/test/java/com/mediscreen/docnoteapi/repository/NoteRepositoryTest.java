package com.mediscreen.docnoteapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mediscreen.docnoteapi.model.Note;

@SpringBootTest
public class NoteRepositoryTest {

	@Autowired
	private NoteRepository repo;
	
	@Test
	public void noteRepositoryTest() {

		//Save
		Note note1 = new Note(0,"firstname","lastname",LocalDate.now(),"m","note1Test");
		Note note2 = new Note(0,"firstname","lastname",LocalDate.now(),"m","note2Test");
		Note note3 = new Note(-1,"firstname","lastname",LocalDate.now(),"f","note3Test");
		repo.save(note1);
		repo.save(note2);
		repo.save(note3);
		assertThat(repo.findById(note1.getId()).isPresent());
		assertThat(repo.findById(note2.getId()).isPresent());
		assertThat(repo.findById(note3.getId()).isPresent());
		
		assertThat(repo.findByPatientId(0).size()==2);
		
		//update
		note3.setComment("NEW");
		repo.save(note3);
		assertThat(repo.findById(note3.getId()).isPresent());
		
		//delete
		repo.delete(note1);
		repo.delete(note2);
		repo.delete(note3);
		assertThat(repo.findById(note1.getId()).isEmpty());
		assertThat(repo.findById(note2.getId()).isEmpty());
		assertThat(repo.findById(note3.getId()).isEmpty());
	}
	
}
