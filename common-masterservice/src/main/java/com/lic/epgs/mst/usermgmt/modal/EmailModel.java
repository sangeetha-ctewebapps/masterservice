package com.lic.epgs.mst.usermgmt.modal;

public class EmailModel {

	private String username;
	
	private String source;
	
	private String email;
	
	private String logggedInUserUserName;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogggedInUserUserName() {
		return logggedInUserUserName;
	}

	public void setLogggedInUserUserName(String logggedInUserUserName) {
		this.logggedInUserUserName = logggedInUserUserName;
	}
}
