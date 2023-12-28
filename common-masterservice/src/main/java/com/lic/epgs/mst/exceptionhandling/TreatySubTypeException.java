package com.lic.epgs.mst.exceptionhandling;

public class TreatySubTypeException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public TreatySubTypeException() {
		super();
	}

	public TreatySubTypeException(final String message) {
		super(message);
	}
}