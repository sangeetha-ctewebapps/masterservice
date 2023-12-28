package com.lic.epgs.mst.exceptionhandling;

public class RelationshipException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public RelationshipException() {
		super();
	}

	public RelationshipException(final String message) {
		super(message);
	}
}