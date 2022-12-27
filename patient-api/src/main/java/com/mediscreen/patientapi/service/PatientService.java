package com.mediscreen.patientapi.service;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

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
	
	public List<Patient> getAllPatients(){
		return repo.findAll();
	}
	
	public Patient savePatient(Patient patient){
		return repo.save(patient);
	}
	
	public void deletePatient(Integer id) {
		repo.deleteById(id);
	}
	
}
