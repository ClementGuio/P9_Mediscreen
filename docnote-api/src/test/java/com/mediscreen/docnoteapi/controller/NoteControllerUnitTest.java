package com.mediscreen.docnoteapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.mediscreen.docnoteapi.model.Note;
import com.mediscreen.docnoteapi.service.NoteService;
import com.mediscreen.docnoteapi.util.PatientFetcher;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class NoteControllerUnitTest {

	@Autowired
	NoteService service;
	
	@Value("${url.patientapi}")
	String urlPatientApi;

	@InjectMocks
	@Autowired
	private NoteController controller;

	@Mock
	@Autowired
	private PatientFetcher mockFetcher;
	
	@Test
	public void addNoteTest() throws Exception{
		LocalDate birthdate = LocalDate.now();
		Note note = new Note(0,"addNote","_Test",birthdate,"m",null);
		
		doReturn(note).when(mockFetcher).fetchPatient(urlPatientApi,0);
		
		controller.addNote(0, "addNoteTest");
		
		assertThat(service.getById(note.getId()).isPresent());
	}
}
