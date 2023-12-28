package com.lic.epgs.mst.exceptionhandling;

public class TopUpValueException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public TopUpValueException() {
		super();
	}

	public TopUpValueException(final String message) {
		super(message);
	}
}