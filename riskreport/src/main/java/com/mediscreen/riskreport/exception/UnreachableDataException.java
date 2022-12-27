package com.mediscreen.riskreport.exception;

public class UnreachableDataException extends Exception{

	private static final long serialVersionUID = 4984010829122351062L;
	
	public UnreachableDataException(String errorMsg) {
		super(errorMsg);
	}

}
