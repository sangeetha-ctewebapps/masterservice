package com.lic.epgs.rhssomodel;

public class SessionModel {
	
	private String client_id;

	private String userName;
	
	private String accessToken;

	private String refreshToken;

	
	private String client_secret;

	private String realms;
	
	
	private String userId;
	
	private String browser;


	public String getClient_id() {
		return client_id;
	}


	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getRefreshToken() {
		return refreshToken;
	}


	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}


	


	public String getClient_secret() {
		return client_secret;
	}


	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}


	public String getRealms() {
		return realms;
	}


	public void setRealms(String realms) {
		this.realms = realms;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getBrowser() {
		return browser;
	}


	public void setBrowser(String browser) {
		this.browser = browser;
	}
	
	

}
