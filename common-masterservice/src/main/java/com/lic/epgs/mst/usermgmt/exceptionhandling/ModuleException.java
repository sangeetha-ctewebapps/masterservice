package com.lic.epgs.mst.usermgmt.exceptionhandling;

public class ModuleException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public ModuleException() {
		super();
	}

	public ModuleException(final String message) {
		super(message);
	}
}
