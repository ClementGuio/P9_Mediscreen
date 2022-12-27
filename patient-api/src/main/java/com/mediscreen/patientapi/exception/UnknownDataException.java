package com.mediscreen.patientapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UnknownDataException extends Exception {

	private static final long serialVersionUID = -6258146484746908622L;

	public UnknownDataException(String errorMessage) {
		super(errorMessage);
	}
}
