package com.lic.epgs.mst.exceptionhandling;

public class PinCodeServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public PinCodeServiceException() {
		super();
	}

	public PinCodeServiceException(final String message) {
		super(message);
	}
}