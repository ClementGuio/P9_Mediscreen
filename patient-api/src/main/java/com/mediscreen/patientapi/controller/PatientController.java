package com.mediscreen.patientapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

	@GetMapping("/")
	public String hello() {
		return "Hello world!!";
	}
}
