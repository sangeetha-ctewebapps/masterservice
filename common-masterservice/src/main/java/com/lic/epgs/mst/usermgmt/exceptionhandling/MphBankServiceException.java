package com.lic.epgs.mst.usermgmt.exceptionhandling;

public class MphBankServiceException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public MphBankServiceException() {
		super();
	}

	public MphBankServiceException(final String message) {
		super(message);
	}


}
