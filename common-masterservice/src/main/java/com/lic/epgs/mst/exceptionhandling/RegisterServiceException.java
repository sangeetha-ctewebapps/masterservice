package com.lic.epgs.mst.exceptionhandling;

public class RegisterServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public RegisterServiceException() {
		super();
	}

	public RegisterServiceException(final String message) {
		super(message);
	}
}