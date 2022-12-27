package com.mediscreen.patientapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mediscreen.patientapi.model.Patient;

@SpringBootTest
public class PatientRepositoryTest {

	@Autowired
	private PatientRepository repo;
	
	@Test
	public void patientRepositoryTest() {
		
		Patient pat1 = new Patient("Test1","repo",LocalDate.now(),"m","adresse","0102030405");
		Patient pat2 = new Patient("Test2","repo",LocalDate.now(),"m","adresse","0102030405");
		
		//save
		pat1 = repo.save(pat1);
		pat2 = repo.save(pat2);
		assertThat(repo.findById(pat1.getId()).isPresent());
		assertThat(repo.findById(pat2.getId()).isPresent());
		
		//update
		pat1.setGender("f");
		repo.save(pat1);
		assertThat(repo.findById(pat1.getId()).get().getGender().equals("f"));
		
		//findAll
		assertThat(repo.findAll().contains(pat1));
		assertThat(repo.findAll().contains(pat2));
		
		//delete
		repo.deleteById(pat1.getId());
		repo.deleteById(pat2.getId());
		assertThat(repo.findById(pat1.getId()).isEmpty());
		assertThat(repo.findById(pat2.getId()).isEmpty());
	}
	
}
