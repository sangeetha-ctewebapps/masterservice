package com.lic.epgs.mst.exceptionhandling;

public class LanguagePreferenceException extends Exception {

	private static final long serialVersionUID = -470180507998010368L;

	public LanguagePreferenceException() {
		super();
	}

	public LanguagePreferenceException(final String message) {
		super(message);
	}
}