package com.lic.epgs.mst.exceptionhandling;

public class ReasonForClaimServiceException extends Exception {

	private static final long serialVersionUID = -471180507998010368L;

	public ReasonForClaimServiceException() {
		super();
	}

	public ReasonForClaimServiceException(final String message) {
		super(message);
	}
}
