package com.lic.epgs.mst.usermgmt.exceptionhandling;

public class FunctionalAreaServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public FunctionalAreaServiceException() {
		super();
	}

	public FunctionalAreaServiceException(final String message) {
		super(message);
	}
}
