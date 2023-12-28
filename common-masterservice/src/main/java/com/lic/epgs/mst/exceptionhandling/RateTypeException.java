package com.lic.epgs.mst.exceptionhandling;

public class RateTypeException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public RateTypeException() {
		super();
	}

	public RateTypeException(final String message) {
		super(message);
	}
}