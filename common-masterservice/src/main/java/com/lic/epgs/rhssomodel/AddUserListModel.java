package com.lic.epgs.rhssomodel;

import java.util.ArrayList;
import java.util.List;

public class AddUserListModel {
	
	
	private String username;
	//private String emailVerified;
	private boolean enabled;
	private String firstName;
	private String lastName;
	private ArrayList<String> groups;
	private String email;
	 private ArrayList<Credential> credentials;
	
	//private ArrayList<String> credentials;
	//private String type;
	//private String value;
	//public String realms;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	 public ArrayList<String> getGroups() {
		return groups;
	}
	public void setGroups(ArrayList<String> groups) {
		this.groups = groups;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ArrayList<Credential> getCredentials() {
		return credentials;
	}
	public void setCredentials(ArrayList<Credential> credentials) {
		this.credentials = credentials;
	}
	
	
	
	
	
	
	

	

}
