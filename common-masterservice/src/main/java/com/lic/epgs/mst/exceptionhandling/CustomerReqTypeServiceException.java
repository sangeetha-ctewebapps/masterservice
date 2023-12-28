package com.lic.epgs.mst.exceptionhandling;

public class CustomerReqTypeServiceException extends Exception {
	
	private static final long serialVersionUID = -470188507998010368L;

	public CustomerReqTypeServiceException() {
		super();
	}

	public CustomerReqTypeServiceException(final String message) {
		super(message);
	}
}