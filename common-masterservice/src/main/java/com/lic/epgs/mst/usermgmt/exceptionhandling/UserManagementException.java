package com.lic.epgs.mst.usermgmt.exceptionhandling;

public class UserManagementException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public UserManagementException() {
		super();
	}

	public UserManagementException(final String message) {
		super(message);
	}
}
