package com.lic.epgs.mst.exceptionhandling;

public class RequirementCategoryException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public RequirementCategoryException() {
		super();
	}

	public RequirementCategoryException(final String message) {
		super(message);
	}
}