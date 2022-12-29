package com.mediscreen.ui.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
public class MainUIControllerTest {

	@Autowired 
	private MockMvc mvc;
	
	@Test
	public void view404Test() throws Exception{
		
		mvc.perform(MockMvcRequestBuilders
				.get("/mediscreen/404"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void viewPatientTest() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders
				.get("/mediscreen/patient"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("urlPatientApi"));
	}
	
	@Test
	public void viewHistoryTest() throws Exception{
		
		mvc.perform(MockMvcRequestBuilders
				.get("/mediscreen/history/"+0))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("urlDocnoteApi","urlRiskReport"));
	}
	
}
