package com.lic.epgs.mst.exceptionhandling;

public class AnnuityPayoutMethodException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public AnnuityPayoutMethodException() {
		super();
	}

	public AnnuityPayoutMethodException(final String message) {
		super(message);
	}
}
