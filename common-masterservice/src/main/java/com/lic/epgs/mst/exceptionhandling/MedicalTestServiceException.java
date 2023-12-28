package com.lic.epgs.mst.exceptionhandling;

public class MedicalTestServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public MedicalTestServiceException() {
		super();
	}

	public MedicalTestServiceException(final String message) {
		super(message);
	}
}