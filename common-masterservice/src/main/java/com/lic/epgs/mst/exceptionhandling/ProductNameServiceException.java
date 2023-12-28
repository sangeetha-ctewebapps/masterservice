package com.lic.epgs.mst.exceptionhandling;

public class ProductNameServiceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public ProductNameServiceException() {
		super();
	}

	public ProductNameServiceException(final String message) {
		super(message);
	}
}