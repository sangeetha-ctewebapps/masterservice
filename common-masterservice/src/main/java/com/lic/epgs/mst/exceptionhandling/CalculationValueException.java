package com.lic.epgs.mst.exceptionhandling;

public class CalculationValueException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public CalculationValueException() {
		super();
	}

	public CalculationValueException(final String message) {
		super(message);
	}
}
