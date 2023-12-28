package com.lic.epgs.mst.exceptionhandling;

public class TypeOfLoanException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public TypeOfLoanException() {
		super();
	}

	public TypeOfLoanException(final String message) {
		super(message);
	}
}