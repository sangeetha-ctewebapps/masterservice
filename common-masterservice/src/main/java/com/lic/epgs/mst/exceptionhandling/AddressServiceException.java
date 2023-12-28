package com.lic.epgs.mst.exceptionhandling;

public class AddressServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public AddressServiceException() {
		super();
	}

	public AddressServiceException(final String message) {
		super(message);
	}
}