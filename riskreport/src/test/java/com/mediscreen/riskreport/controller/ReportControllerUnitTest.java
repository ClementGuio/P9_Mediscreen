package com.mediscreen.riskreport.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.mediscreen.riskreport.model.PatientReport;
import com.mediscreen.riskreport.util.NotesFetcher;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ReportControllerUnitTest {

	@Value("${url.docnoteapi}")
	String urlPatientApi;

	@InjectMocks
	@Autowired
	private ReportController controller;

	@Mock
	@Autowired
	private NotesFetcher mockFetcher;
	
	@Test
	public void reportRiskTest() throws Exception{
		List<String> notes = new ArrayList<String>();
		notes.add("Le patient va bien");
		PatientReport report = new PatientReport("RiskReport","_Test",LocalDate.now(),"m",notes);
		
		doReturn(report).when(mockFetcher).fetchNote(urlPatientApi,0);

		JsonNode node = controller.reportRisk(0,null);
		
		assertThat(node.findValue("RiskLevel").textValue()).isEqualTo("None");
	}
	
}
