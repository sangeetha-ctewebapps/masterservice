package com.lic.epgs.rhssomodel;

public class ResponseModel {
	private String message;
	private String description;
	private int status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ResponseModel [message=" + message + ", description=" + description + ", status=" + status + "]";
	}

}
