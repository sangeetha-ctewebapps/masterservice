package com.lic.epgs.mst.usermgmt.exceptionhandling;

public class CronJobException extends Exception {
	private static final long serialVersionUID = 1L;

	public CronJobException() {
		super();
	}

	public CronJobException(final String message) {
		super(message);
	}
}
