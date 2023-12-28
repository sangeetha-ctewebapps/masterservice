package com.lic.epgs.mst.exceptionhandling;

public class DepartmentServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public DepartmentServiceException() {
		super();
	}

	public DepartmentServiceException(final String message) {
		super(message);
	}

}
