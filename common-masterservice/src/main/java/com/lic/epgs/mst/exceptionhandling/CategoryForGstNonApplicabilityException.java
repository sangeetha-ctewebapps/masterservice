package com.lic.epgs.mst.exceptionhandling;

public class CategoryForGstNonApplicabilityException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public CategoryForGstNonApplicabilityException() {
		super();
	}

	public CategoryForGstNonApplicabilityException(final String message) {
		super(message);
	}
}