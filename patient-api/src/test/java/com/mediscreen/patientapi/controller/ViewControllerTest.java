package com.mediscreen.patientapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mediscreen.patientapi.model.Patient;
import com.mediscreen.patientapi.service.PatientService;

@AutoConfigureMockMvc
@SpringBootTest
//@Sql(scripts = "../resource/cleanDB.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class ViewControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired 
	private PatientService service;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@AfterEach
	public void setup() {
		for (Patient patient : service.getAllPatients()) {
			if (patient.getLastname().equals("_Test"));
		}
	}
	
	@Test
	public void viewGetPatientTest() throws Exception {
		
		Patient patient = new Patient("viewGetPatient","_Test",LocalDate.now(),"m","address","0000000000");
		patient = service.savePatient(patient);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/patient/get/"+patient.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("patient"));
	}
	
	@Test
	public void viewAddPatientTest() throws Exception{
		
		mvc.perform(MockMvcRequestBuilders
				.get("/patient/add"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void viewListPatientTest() throws Exception{
		
		Patient patient = new Patient("viewListPatient","_Test",LocalDate.now(),"m","address","0000000000");
		patient = service.savePatient(patient);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/patient/list"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("patientlist","urlUI"));
	}
	
	@Test
	public void viewUpdatePatientTest() throws Exception{
		
		Patient patient = new Patient("viewUpdatePatient","_Test",LocalDate.now(),"m","address","0000000000");
		patient = service.savePatient(patient);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/patient/update/"+patient.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("patient"));
	}
	
	@Test
	public void viewUpdateSubmitTest() throws Exception {
		
		LocalDate birthdate = LocalDate.now();
		Patient patient = new Patient("viewUpdateSubmit","_Test",birthdate,"m","address","0000000000");
		patient = service.savePatient(patient);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/patient/update/"+patient.getId()+"/submit")
				.param("firstname",patient.getFirstname())
				.param("lastname",patient.getLastname())
				.param("birthdate", birthdate.toString())
				.param("gender",patient.getGender())
				.param("address", "NEW")
				.param("phone", patient.getPhone())
				.sessionAttr("patient", new Patient()))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/patient/list"));
		
		assertThat(service.getPatient(patient.getId()).get().getAddress().equals("NEW"));
	}
	
	@Test
	public void viewAddSubmitTest() throws Exception{
		
		mvc.perform(MockMvcRequestBuilders
				.post("/patient/add/submit")
				.param("firstname","viewAddSubmit")
				.param("lastname","_Test")
				.param("birthdate", LocalDate.now().toString())
				.param("gender","M")
				.param("address", "address")
				.param("phone", "0000000000")
				.sessionAttr("patient", new Patient()))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/patient/list"));
	}
	
	@Test
	public void viewDeleteConfirm() throws Exception{

		LocalDate birthdate = LocalDate.now();
		Patient patient = new Patient("viewUpdateSubmit","_Test",birthdate,"m","address","0000000000");
		patient = service.savePatient(patient);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/patient/delete/"+patient.getId()+"/confirm"))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/patient/list"));
		
		assertThat(service.getPatient(patient.getId()).isEmpty());
	}
	
	@Test
	public void viewUpdateSubmitAndFailTest() throws Exception {
		
		LocalDate birthdate = LocalDate.now();
		Patient patient = new Patient("viewUpdateSubmitAndFail","_Test",birthdate,"m","address","0000000000");
		patient = service.savePatient(patient);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/patient/update/"+patient.getId()+"/submit")
				.param("firstname",patient.getFirstname())
				.param("lastname",patient.getLastname())
				.param("birthdate", birthdate.toString())
				.param("gender",patient.getGender())
				.param("address", "")
				.param("phone", patient.getPhone())
				.sessionAttr("patient", new Patient()))
			.andExpect(status().isBadRequest());
		
		assertThat(service.getPatient(patient.getId()).get().getAddress().equals("address"));
	}
	
	@Test
	public void viewAddSubmitAndFailTest() throws Exception{
		
		mvc.perform(MockMvcRequestBuilders
				.post("/patient/add/submit")
				.param("firstname","viewAddSubmitAndFail")
				.param("lastname","_Test")
				.param("birthdate", LocalDate.now().toString())
				.param("gender","M")
				.param("address", "address")
				.param("phone", "")
				.sessionAttr("patient", new Patient()))
			.andExpect(status().isOk());
	}
} 
