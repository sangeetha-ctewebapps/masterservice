package com.lic.epgs.mst.usermgmt.exceptionhandling;

public class EpgsUserServiceException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EpgsUserServiceException() {
		super();
	}

	public EpgsUserServiceException(final String message) {
		super(message);
	}

}
