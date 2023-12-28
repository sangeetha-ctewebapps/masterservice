package com.lic.epgs.mst.usermgmt.exceptionhandling;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

	private int customerId;
	private int id;
	private String status;
	private String message;
	private Date current_time;
	private String validationKey;
	
	public ApiResponse(String status, String message, int customerId, int id ) {
		super();
		this.status = status;
		this.message = message;
		this.customerId = customerId;
		this.id = id;
		
	}
	public ApiResponse(String status, String message ,int id ) {
		super();
		this.status = status;
		this.message = message;
		this.id = id;
		
	}
	

	public ApiResponse(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ApiResponse(String status, String message, Date current_time) {
		super();
		this.status = status;
		this.message = message;
		this.current_time = current_time;

	}

	public ApiResponse(String status, String message, String validationKey) {
		super();
		this.status = status;
		this.message = message;
		this.validationKey = validationKey;
	}
	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Date getCurrent_time() {
		return current_time;
	}

	public int getCustomerId() {
		return customerId;
	}

	public int getId() {
		return id;
	}
	
	public String getValidationKey() {
		return validationKey;
	}

}
