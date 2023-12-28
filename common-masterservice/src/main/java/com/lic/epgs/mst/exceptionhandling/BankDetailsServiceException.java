package com.lic.epgs.mst.exceptionhandling;

public class BankDetailsServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public BankDetailsServiceException() {
		super();
	}

	public BankDetailsServiceException(final String message) {
		super(message);
	}
}