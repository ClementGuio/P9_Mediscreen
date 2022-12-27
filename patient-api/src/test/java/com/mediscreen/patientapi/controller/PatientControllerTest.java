package com.mediscreen.patientapi.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.mediscreen.patientapi.model.Patient;
import com.mediscreen.patientapi.service.PatientService;

@AutoConfigureMockMvc
@SpringBootTest
@Sql(scripts = "../resource/cleanDB.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class PatientControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private PatientService service; 
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Test
	public void addPatientTest() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders
				.post("/patientapi/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"Firstname\": \"addPatient\", \"Lastname\": \"_Test\", \"Birthdate\": \"01/01/2000\","
						+ " \"Gender\": \"m\", \"Address\": \"address\", \"Phone\": \"0000000000\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void updatePatientTest() throws Exception{
		LocalDate birthdate = LocalDate.now();
		Patient patient = new Patient("updatePatient","_Test",birthdate,"m","address","0000000000");
		patient = service.savePatient(patient);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/patientapi/update?id="+patient.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"Firstname\": \"updatePatient\", \"Lastname\": \"_Test\", \"Birthdate\": \""+formatter.format(birthdate)+"\","
						+ " \"Gender\": \"m\", \"Address\": \"NEW\", \"Phone\": \"0000000000\"}")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
		assertThat(service.getPatient(patient.getId()).get().getAddress().equals("NEW"));
	}
	
	@Test
	public void getPatientTest() throws Exception{
		
		LocalDate birthdate = LocalDate.now();
		Patient patient = new Patient("getPatient","_Test",birthdate,"m","address","0000000000");
		patient = service.savePatient(patient);
		
		String expectedResponse = "{\"Id\":"+patient.getId()+",\"Firstname\":\"getPatient\",\"Lastname\":\"_Test\",\"Birthdate\":\""+formatter.format(birthdate)+"\",\"Gender\":\"m\",\"Address\":\"address\",\"Phone\":\"0000000000\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.get("/patientapi/get?id="+patient.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(expectedResponse));
	}
	
	@Test
	public void deletePatientTest() throws Exception {
		Patient patient = new Patient("getPatient","_Test",LocalDate.now(),"m","address","0000000000");
		patient = service.savePatient(patient);
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/patientapi/delete?id="+patient.getId()))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
		assertThat(service.getPatient(patient.getId()).isEmpty());
		
	}
	
	@Test
	public void addUnvalidPatientTest() throws Exception{
		
		mvc.perform(MockMvcRequestBuilders
				.post("/patientapi/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"Firstname\": \"\", \"Lastname\": \"_Test\", \"Birthdate\": \"\","
						+ " \"Gender\": \"\", \"Address\": \"\", \"Phone\": \"\"}")
				.accept(MediaType.TEXT_HTML_VALUE))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void updateUnvalidPatientTest() throws Exception{
		
		Patient patient = new Patient("updateUnvalidPatient","_Test",LocalDate.now(),"m","address","0000000000");
		patient = service.savePatient(patient);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/patientapi/update?id="+patient.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"Firstname\": \"\", \"Lastname\": \"_Test\", \"Birthdate\": \"\","
						+ " \"Gender\": \"\", \"Address\": \"\", \"Phone\": \"\"}")
				.accept(MediaType.TEXT_HTML_VALUE))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void updateUnknownPatientTest() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders
				.put("/patientapi/update?id="+0)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"Firstname\": \"updateUnknownPatient\", \"Lastname\": \"_Test\", \"Birthdate\": \"01/01/2000\","
						+ " \"Gender\": \"f\", \"Address\": \"address\", \"Phone\": \"0000000000\"}")
				.accept(MediaType.TEXT_HTML_VALUE))
			.andExpect(MockMvcResultMatchers.status().isConflict());
	}
	
	@Test
	public void getUnknownPatientTest() throws Exception{
		
		mvc.perform(MockMvcRequestBuilders
				.get("/patientapi/get?id="+0)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"Firstname\": \"getUnknownPatient\", \"Lastname\": \"_Test\", \"Birthdate\": \"01/01/2000\","
						+ " \"Gender\": \"f\", \"Address\": \"address\", \"Phone\": \"0000000000\"}")
				.accept(MediaType.TEXT_HTML_VALUE))
			.andExpect(MockMvcResultMatchers.status().isConflict());
	}
	
	@Test
	public void deleteUnknownPatientTest() throws Exception{
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/patientapi/delete?id="+0)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"Firstname\": \"deleteUnknownPatient\", \"Lastname\": \"_Test\", \"Birthdate\": \"01/01/2000\","
						+ " \"Gender\": \"f\", \"Address\": \"address\", \"Phone\": \"0000000000\"}")
				.accept(MediaType.TEXT_HTML_VALUE))
			.andExpect(MockMvcResultMatchers.status().isConflict());
	}
}
