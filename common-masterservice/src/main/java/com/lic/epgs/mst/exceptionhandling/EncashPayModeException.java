package com.lic.epgs.mst.exceptionhandling;

public class EncashPayModeException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public EncashPayModeException() {
		super();
	}

	public EncashPayModeException(final String message) {
		super(message);
	}
}