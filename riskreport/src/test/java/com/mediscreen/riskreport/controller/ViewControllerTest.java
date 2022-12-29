package com.mediscreen.riskreport.controller;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mediscreen.riskreport.exception.NoteNotFoundException;
import com.mediscreen.riskreport.exception.UnreachableDataException;
import com.mediscreen.riskreport.model.PatientReport;
import com.mediscreen.riskreport.util.NotesFetcher;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ViewControllerTest {

	@Autowired
	private MockMvc mvc;
	/*
	@Test
	public void viewReportTest_WhenDataIsUnavailable() throws Exception{
		
		mvc.perform(MockMvcRequestBuilders
				.get("/riskreport/assess?patientId="+0))
			.andExpect(status().isNotFound());
	}
	*/
}
