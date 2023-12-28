package com.lic.epgs.mst.exceptionhandling;

public class ClaimCancellationReasonServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public ClaimCancellationReasonServiceException() {
		super();
	}

	public ClaimCancellationReasonServiceException(final String message) {
		super(message);
	}
}