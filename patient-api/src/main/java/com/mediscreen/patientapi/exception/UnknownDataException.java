package com.mediscreen.patientapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.CONFLICT)
public class UnknownDataException extends Exception {

	public UnknownDataException(String errorMessage) {
		super(errorMessage);
	}
}
