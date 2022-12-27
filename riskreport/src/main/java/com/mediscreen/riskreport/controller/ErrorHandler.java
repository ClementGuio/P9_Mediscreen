package com.mediscreen.riskreport.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mediscreen.riskreport.exception.UnreachableDataException;

@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(UnreachableDataException.class)
	public String handleUnreachableDataException(UnreachableDataException e) {
		return "Erreur! Le service que vous demandez n'est pas disponible actuellement. Veuillez verifier l'id du patient ou réessayez plus tard.";
	}
}