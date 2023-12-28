package com.lic.epgs.mst.exceptionhandling;

public class GroupCategoryException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public GroupCategoryException() {
		super();
	}

	public GroupCategoryException(final String message) {
		super(message);
	}
}