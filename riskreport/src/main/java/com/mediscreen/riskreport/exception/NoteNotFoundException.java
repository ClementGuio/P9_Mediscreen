package com.mediscreen.riskreport.exception;

public class NoteNotFoundException extends Exception {

	private static final long serialVersionUID = -4962501207508685835L;
	
	public NoteNotFoundException(String errorMsg) {
		super(errorMsg);
	}

}
