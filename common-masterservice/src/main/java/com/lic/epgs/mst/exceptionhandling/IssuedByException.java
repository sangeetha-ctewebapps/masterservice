package com.lic.epgs.mst.exceptionhandling;

public class IssuedByException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public IssuedByException() {
		super();
	}

	public IssuedByException(final String message) {
		super(message);
	}
}