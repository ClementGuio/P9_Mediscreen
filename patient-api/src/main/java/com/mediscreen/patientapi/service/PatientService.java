package com.mediscreen.patientapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediscreen.patientapi.model.Patient;
import com.mediscreen.patientapi.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository repo;
	
	public Optional<Patient> getPatient(Integer id) {
		return repo.findById(id);
	}
	
	public Optional<Patient> getPatient(String firstname, String lastname){
		return repo.findByFirstnameAndLastname(firstname, lastname);
	}
	
	public List<Patient> getAllPatients(){
		return repo.findAll();
	}
	
	public Patient savePatient(Patient patient) {
		return repo.save(patient);
	}
	
	public void deletePatient(Integer id) {
		repo.deleteById(id);
	}
	
	public void deletePatient(String firstname, String lastname) {
		repo.deleteByFirstnameAndLastname(firstname, lastname);
	}
}
