package com.mediscreen.patientapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mediscreen.patientapi.model.Patient;

@Transactional
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
	//TODO: ajouter birthdate
	public Optional<Patient> findByFirstnameAndLastname(String firstname, String lastname);
	
	public void deleteByFirstnameAndLastname(String firstname, String lastname);
}
