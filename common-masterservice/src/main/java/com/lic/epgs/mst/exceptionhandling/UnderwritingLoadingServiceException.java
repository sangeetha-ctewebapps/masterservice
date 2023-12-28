package com.lic.epgs.mst.exceptionhandling;

public class UnderwritingLoadingServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public UnderwritingLoadingServiceException() {
		super();
	}

	public UnderwritingLoadingServiceException(final String message) {
		super(message);
	}
}