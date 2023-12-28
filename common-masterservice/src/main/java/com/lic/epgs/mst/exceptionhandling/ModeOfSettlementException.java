package com.lic.epgs.mst.exceptionhandling;

public class ModeOfSettlementException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public ModeOfSettlementException() {
		super();
	}

	public ModeOfSettlementException(final String message) {
		super(message);
	}
}