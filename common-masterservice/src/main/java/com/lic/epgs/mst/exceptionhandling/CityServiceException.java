package com.lic.epgs.mst.exceptionhandling;

public class CityServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public CityServiceException() {
		super();
	}

	public CityServiceException(final String message) {
		super(message);
	}

}
