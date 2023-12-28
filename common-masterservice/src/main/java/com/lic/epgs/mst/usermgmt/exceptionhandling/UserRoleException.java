package com.lic.epgs.mst.usermgmt.exceptionhandling;

public class UserRoleException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public UserRoleException() {
		super();
	}

	public UserRoleException(final String message) {
		super(message);
	}
}
