package com.lic.epgs.mst.usermgmt.exceptionhandling;

public class CoFinancialLimitException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public CoFinancialLimitException() {
		super();
	}

	public CoFinancialLimitException(final String message) {
		super(message);
	}

}
