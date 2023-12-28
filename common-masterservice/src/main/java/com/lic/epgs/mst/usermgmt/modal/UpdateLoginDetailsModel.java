package com.lic.epgs.mst.usermgmt.modal;

import java.sql.Timestamp;
import java.util.Date;

public class UpdateLoginDetailsModel {
	
	private Long loginId;
	
	private String userName;
	
	private Timestamp loggedOutTime;

	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getLoggedOutTime() {
		return loggedOutTime;
	}

	public void setLoggedOutTime(Timestamp loggedOutTime) {
		this.loggedOutTime = loggedOutTime;
	}

	
	
	
	

}
