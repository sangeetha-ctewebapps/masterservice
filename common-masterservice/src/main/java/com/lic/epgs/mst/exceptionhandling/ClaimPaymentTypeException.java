package com.lic.epgs.mst.exceptionhandling;

public class ClaimPaymentTypeException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public ClaimPaymentTypeException() {
		super();
	}

	public ClaimPaymentTypeException(final String message) {
		super(message);
	}
}