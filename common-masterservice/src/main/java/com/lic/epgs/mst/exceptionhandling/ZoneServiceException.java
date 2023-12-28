package com.lic.epgs.mst.exceptionhandling;

public class ZoneServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public ZoneServiceException() {
		super();
	}

	public ZoneServiceException(final String message) {
		super(message);
	}
}