package com.lic.epgs.mst.exceptionhandling;

public class LifeCoverageTypeException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public LifeCoverageTypeException() {
		super();
	}

	public LifeCoverageTypeException(final String message) {
		super(message);
	}
}