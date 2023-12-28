package com.lic.epgs.mst.exceptionhandling;

import java.io.Serializable;

public class ReinsurerServiceException extends Exception implements Serializable 
{
	private static final long serialVersionUID = 101L;

	public  ReinsurerServiceException() 
	{
		super();
	}

	public ReinsurerServiceException(final String message) 
	{
		super(message);
	}
}

