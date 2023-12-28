package com.lic.epgs.rhssomodel;

import java.util.List;

public class UserResponseModel {
	private String message;
	private int status;
	private List<UserResponse> userlist;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<UserResponse> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<UserResponse> userlist) {
		this.userlist = userlist;
	}

	@Override
	public String toString() {
		return "UserResponseModel [message=" + message + ", status=" + status + ", userlist=" + userlist + "]";
	}

}
