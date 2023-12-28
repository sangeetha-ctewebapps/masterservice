package com.lic.epgs.mst.usermgmt.exceptionhandling;

public class UserRoleMappingException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public UserRoleMappingException() {
		super();
	}

	public UserRoleMappingException(final String message) {
		super(message);
	}
}
