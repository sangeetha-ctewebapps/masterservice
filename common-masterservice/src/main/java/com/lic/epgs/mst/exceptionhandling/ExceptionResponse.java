package com.lic.epgs.mst.exceptionhandling;

import java.util.Date;

public class ExceptionResponse {
	public ExceptionResponse(String errorMessage, String callUri, Date timestamp) {
		super();
		this.errorMessage = errorMessage;
		this.callUri = callUri;
		this.timestamp = timestamp;
	}

	private String errorMessage;

	public ExceptionResponse() {
		super();
	}

	private String callUri;
	private Date timestamp;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCallUri() {
		return callUri;
	}

	public void setCallUri(String callUri) {
		this.callUri = callUri;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
