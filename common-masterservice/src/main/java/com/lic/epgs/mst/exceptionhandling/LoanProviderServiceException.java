package com.lic.epgs.mst.exceptionhandling;

public class LoanProviderServiceException extends Exception  {
	private static final long serialVersionUID = -471180507998010368L;

	public LoanProviderServiceException() {
		super();
	}

	public LoanProviderServiceException(final String message) {
		super(message);
	}
}

