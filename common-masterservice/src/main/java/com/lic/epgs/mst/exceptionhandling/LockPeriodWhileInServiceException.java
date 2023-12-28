package com.lic.epgs.mst.exceptionhandling;

public class LockPeriodWhileInServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public LockPeriodWhileInServiceException() {
		super();
	}

	public LockPeriodWhileInServiceException(final String message) {
		super(message);
	}
}