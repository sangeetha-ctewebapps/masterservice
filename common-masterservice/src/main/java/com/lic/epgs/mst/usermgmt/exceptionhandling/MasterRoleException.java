package com.lic.epgs.mst.usermgmt.exceptionhandling;

public class MasterRoleException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public MasterRoleException() {
		super();
	}

	public MasterRoleException(final String message) {
		super(message);
	}
}
