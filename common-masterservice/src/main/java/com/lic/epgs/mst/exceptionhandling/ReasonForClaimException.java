package com.lic.epgs.mst.exceptionhandling;

public class ReasonForClaimException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public ReasonForClaimException() {
		super();
	}

	public ReasonForClaimException(final String message) {
		super(message);
	}
}