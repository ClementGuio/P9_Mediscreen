package com.mediscreen.docnoteapi.controller;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mediscreen.docnoteapi.exception.UnknownDataException;
import com.mediscreen.docnoteapi.exception.UnreachableDataException;

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
		return "Erreur! La note que vous demandez n'existe pas. Veuillez vérifier l'id du patient.";
	}
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(UnreachableDataException.class)
	public String handleUnreachableDataException(UnreachableDataException e) {
		return "Erreur! Le service que vous demandez n'est pas disponible actuellement. Veuillez verifier l'id du patient ou réessayez plus tard.";
	}
	
}
