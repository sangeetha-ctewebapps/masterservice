package com.lic.epgs.mst.exceptionhandling;

public class CessionTypeServiceException extends Exception{
     private static final long serialVersionUID = -411180507018010368L;
	
	public CessionTypeServiceException() {
     super();
	}
	 
	public CessionTypeServiceException(final String message) {
	     super(message);
		}	  
}