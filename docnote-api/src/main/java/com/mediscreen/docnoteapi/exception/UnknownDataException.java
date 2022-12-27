package com.mediscreen.docnoteapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.CONFLICT)
public class UnknownDataException extends Exception {

	private static final long serialVersionUID = -9020966876958945893L;

	public UnknownDataException(String errorMessage) {
		super(errorMessage);
	}
}
