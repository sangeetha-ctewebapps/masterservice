package com.lic.epgs.mst.exceptionhandling;

public class DistrictServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public DistrictServiceException() {
		super();
	}

	public DistrictServiceException(final String message) {
		super(message);
	}
}