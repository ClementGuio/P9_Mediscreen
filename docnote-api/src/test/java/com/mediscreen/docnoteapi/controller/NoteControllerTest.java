package com.mediscreen.docnoteapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mediscreen.docnoteapi.model.Note;
import com.mediscreen.docnoteapi.service.NoteService;

@AutoConfigureMockMvc
@SpringBootTest
public class NoteControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private NoteService service;
		
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Test
	public void addNoteTest_WhenDataIsUnavailable() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders
				.post("/docnoteapi/add?patientId="+0)
				.contentType(MediaType.APPLICATION_JSON)
				.content("Note")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void getNoteTest() throws Exception {
		
		LocalDate birthdate = LocalDate.now();
		Note note = new Note(0,"getNote","_Test",birthdate,"m","getNoteTest");
		service.addOrUpdateNote(note);
		
		String expectedResponse = "{\"id\": \""+note.getId()+"\",\"patientId\": "+note.getPatientId()+",\"firstname\": \""+note.getFirstname()
									+"\",\"lastname\": \""+note.getLastname()+"\",\"birthdate\": \""+formatter.format(birthdate)
									+"\",\"gender\": \""+note.getGender()+"\",\"comment\": \""+note.getComment()+"\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.get("/docnoteapi/get?noteId="+note.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpectAll(status().isOk())
			.andExpect(content().json(expectedResponse));
		
		service.deleteNote(note.getId());
	}
	
	@Test
	public void updateNoteTest() throws Exception {
		Note note = new Note(0,"updateNote","_Test",LocalDate.now(),"m","updateNoteTest");
		service.addOrUpdateNote(note);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/docnoteapi/update?noteId="+note.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("NEW")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		assertThat(service.getById(note.getId()).get().getComment().equals("NEW"));
		service.deleteNote(note.getId());
	}
	
	@Test
	public void deleteNoteTest() throws Exception{
		Note note = new Note(0,"deleteNote","_Test",LocalDate.now(),"m","updateNoteTest");
		service.addOrUpdateNote(note);
		
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/docnoteapi/delete?noteId="+note.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		assertThat(service.getById(note.getId()).isEmpty());
	}
	
}
