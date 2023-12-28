package com.lic.epgs.mst.entity;

import java.util.List;

public class GetBankListResponseModel {
	public Object headers;
	public List<Object> body;
	public String statusCode;
	public int statusCodeValue;

	public Object getHeaders() {
		return headers;
	}

	public void setHeaders(Object headers) {
		this.headers = headers;
	}

	public List<Object> getBody() {
		return body;
	}

	public void setBody(List<Object> body) {
		this.body = body;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCodeValue() {
		return statusCodeValue;
	}

	public void setStatusCodeValue(int statusCodeValue) {
		this.statusCodeValue = statusCodeValue;
	}

}
