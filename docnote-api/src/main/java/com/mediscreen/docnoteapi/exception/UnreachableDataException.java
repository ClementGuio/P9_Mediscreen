package com.mediscreen.docnoteapi.exception;

public class UnreachableDataException extends Exception{

	private static final long serialVersionUID = -6384457939091913097L;

	public UnreachableDataException(String errorMsg) {
		super(errorMsg);
	}
}
