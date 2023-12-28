package com.lic.epgs.rhssomodel;

import java.util.List;

public class RoleResponseModel_old {
	private String message;
	private int status;
	private List<RoleMap> data;

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

	public List<RoleMap> getData() {
		return data;
	}

	public void setData(List<RoleMap> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RoleResponseModel [message=" + message + ", status=" + status + ", data=" + data + "]";
	}

}
