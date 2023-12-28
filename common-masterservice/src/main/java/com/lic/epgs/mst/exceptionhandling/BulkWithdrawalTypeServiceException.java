package com.lic.epgs.mst.exceptionhandling;

public class BulkWithdrawalTypeServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public BulkWithdrawalTypeServiceException() {
		super();
	}

	public BulkWithdrawalTypeServiceException(final String message) {
		super(message);
	}
}