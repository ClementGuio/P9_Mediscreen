package com.mediscreen.riskreport.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
public class ReportControllerTest {

	@Autowired
	MockMvc mvc;
	/*
	@Test
	public void reportRiskTest_WhenDataIsUnavailable() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders
				.get("/riskreport/assess?patientId="+0))
			.andExpect(status().isNotFound());
	}
	*/
}
