package com.mediscreen.docnoteapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mediscreen.docnoteapi.model.Note;
import com.mediscreen.docnoteapi.service.NoteService;

@AutoConfigureMockMvc
@SpringBootTest
public class ViewControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private NoteService service;
	
	@AfterEach
	public void clean() {
		for(Note note:service.getNotesOfPatient(0)) {
			service.deleteNote(note.getId());
		}
	}

	@Test
	public void viewHistoryTest() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders
				.get("/docnote/patientHistory/"+0))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("urlUI"))
			.andExpect(model().attributeExists("notes"));
	}
	
	@Test
	public void viewGetTest() throws Exception{
		
		Note note = new Note(0,"viewGet","_Test",LocalDate.now(),"m","viewGetTest");
		service.addOrUpdateNote(note);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/docnote/get/"+note.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("note"));
		
		service.deleteNote(note.getId());
	}
	
	@Test
	public void viewUpdateTest() throws Exception{
		
		Note note = new Note(0,"viewGet","_Test",LocalDate.now(),"m","viewGetTest");
		service.addOrUpdateNote(note);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/docnote/update/"+note.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("note"));
	
		service.deleteNote(note.getId());
	}
	
	@Test
	public void addSubmitTest() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders
				.post("/docnote/add/submit")
				.param("patientId", "0")
				.param("firstname", "addSubmit")
				.param("lastname", "_Test")
				.param("birthdate", LocalDate.now().toString())
				.param("gender", "m")
				.param("comment","viewAddSubmit")
				.sessionAttr("note", new Note()))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/docnote/patientHistory/"+0));
	}
	
	@Test
	public void updateSubmitTest() throws Exception{
		
		Note note = new Note(0,"updateSubmit","_Test",LocalDate.now(),"m","viewGetTest");
		service.addOrUpdateNote(note);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/docnote/update/"+note.getId()+"/submit")
				.param("patientId", "0")
				.param("firstname", "viewAddSubmit")
				.param("lastname", "_Test")
				.param("birthdate", LocalDate.now().toString())
				.param("gender", "m")
				.param("comment","viewAddSubmit")
				.sessionAttr("note", new Note()))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/docnote/patientHistory/"+0));
	}
	
	@Test
	public void deleteConfirmTest() throws Exception {
		
		Note note = new Note(0,"updateSubmit","_Test",LocalDate.now(),"m","viewGetTest");
		service.addOrUpdateNote(note);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/docnote/delete/"+note.getId()+"/confirm"))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/docnote/patientHistory/"+0));
		
		assertThat(service.getById(note.getId()).isEmpty());
	}
}
