package com.mediscreen.patientapi.controller;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.JsonNode;
import com.mediscreen.patientapi.exception.UnknownDataException;

@RestControllerAdvice
public class ErrorHandler {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public String handleConstraintViolationException(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> errors = e.getConstraintViolations();
		String msgs = "Erreur! Veuillez respecter les spécifications suivantes:\n";
		for (ConstraintViolation<?> error : errors) {
			msgs += "\t- "+error.getMessage()+"\n";
		}
		return msgs;
	}
	
	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler(UnknownDataException.class)
	public String handleUnknownDataException(UnknownDataException e) {
		return "Le patient que vous demandez n'existe pas. Veuillez vérifier l'id via /getAll .";
	}
	
}
