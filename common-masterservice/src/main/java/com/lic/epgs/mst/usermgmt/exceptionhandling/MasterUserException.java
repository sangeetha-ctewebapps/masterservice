package com.lic.epgs.mst.usermgmt.exceptionhandling;

public class MasterUserException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public MasterUserException() {
		super();
	}

	public MasterUserException(final String message) {
		super(message);
	}
}
