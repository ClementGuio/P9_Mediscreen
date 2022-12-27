package com.mediscreen.patientapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mediscreen.patientapi.model.Patient;

@Transactional
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
	
}
