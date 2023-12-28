package com.lic.epgs.mst.exceptionhandling;

public class UnderwritingDecisionException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public UnderwritingDecisionException() {
		super();
	}

	public UnderwritingDecisionException(final String message) {
		super(message);
	}
}