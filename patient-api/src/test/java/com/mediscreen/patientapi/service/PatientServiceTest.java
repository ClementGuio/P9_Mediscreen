package com.mediscreen.patientapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mediscreen.patientapi.model.Patient;
import com.mediscreen.patientapi.repository.PatientRepository;

@SpringBootTest
public class PatientServiceTest {

	@Autowired
	private PatientService service;
	
	@Test
	public void patientRepositoryTest() {
		
		Patient pat1 = new Patient("Test1","repo",LocalDate.now(),"m","adresse","0102030405");
		Patient pat2 = new Patient("Test2","repo",LocalDate.now(),"m","adresse","0102030405");
		
		//save
		pat1 = service.savePatient(pat1);
		pat2 = service.savePatient(pat2);
		assertThat(service.getPatient(pat1.getId()).isPresent());
		assertThat(service.getPatient(pat2.getId()).isPresent());
		
		//update
		pat1.setGender("f");
		service.savePatient(pat1);
		assertThat(service.getPatient(pat1.getId()).get().getGender().equals("f"));
		
		//findAll
		assertThat(service.getAllPatients().contains(pat1));
		assertThat(service.getAllPatients().contains(pat2));
		
		//delete
		service.deletePatient(pat1.getId());
		service.deletePatient(pat2.getId());
		assertThat(service.getPatient(pat1.getId()).isEmpty());
		assertThat(service.getPatient(pat2.getId()).isEmpty());
	}

}
