package com.lic.epgs.mst.utils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionStatusUtil {

	private String status;
	private String message;
	private Date currentTime;

	public TransactionStatusUtil(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public TransactionStatusUtil(String status, String message, Date currentTime) {
		super();
		this.status = status;
		this.message = message;
		this.currentTime = currentTime;

	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

}
