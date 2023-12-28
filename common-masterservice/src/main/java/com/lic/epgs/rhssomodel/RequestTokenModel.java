package com.lic.epgs.rhssomodel;

public class RequestTokenModel {
	private String refreshToken;
	private String accessToken;
	private String client_secret;
	private String client_id;
	private String realms;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getRealms() {
		return realms;
	}

	public void setRealms(String realms) {
		this.realms = realms;
	}

	@Override
	public String toString() {
		return "RequestTokenModel [refreshToken=" + refreshToken + ", accessToken=" + accessToken + ", client_secret="
				+ client_secret + ", client_id=" + client_id + ", realms=" + realms + "]";
	}

}
