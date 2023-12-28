package com.lic.epgs.rhssomodel;

import java.util.List;

public class UserSessionResponse {
	
	//private List<Object> clients;
	private String id;
	private String ipAddress;
	private long lastAccess;
	private long start;
	private String userId;
	private String username;
	
	/*public List<Object> getClients() {
		return clients;
	}
	public void setClients(List<Object> clients) {
		this.clients = clients;
	}*/
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public long getLastAccess() {
		return lastAccess;
	}
	public void setLastAccess(long lastAccess) {
		this.lastAccess = lastAccess;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
