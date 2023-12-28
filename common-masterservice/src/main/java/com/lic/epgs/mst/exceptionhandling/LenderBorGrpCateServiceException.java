package com.lic.epgs.mst.exceptionhandling;

public class LenderBorGrpCateServiceException extends RuntimeException {

	
	private static final long serialVersionUID = 4717375705006580746L;

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
	
	public LenderBorGrpCateServiceException(final String message) 
	{
		super(message);
	}
}
