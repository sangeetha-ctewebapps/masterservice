package com.lic.epgs.mst.exceptionhandling;

public class GenderException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public GenderException() {
		super();
	}

	public GenderException(final String message) {
		super(message);
	}
}