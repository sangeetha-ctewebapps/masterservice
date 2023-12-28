package com.lic.epgs.rhssomodel;

public class AddUserModel {
	
	
	private String username;
	//private String emailVerified;
	 private boolean enabled;
	private String firstName;
	private String lastName;
	 private String groups;
	private String email;
	 private String pwdd;
	 String realm;
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
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwdd() {
		return pwdd;
	}
	public void setPwdd(String pwdd) {
		this.pwdd = pwdd;
	}
	public String getRealm() {
		return realm;
	}
	public void setRealm(String realm) {
		this.realm = realm;
	}
	
	
	
	
	
	

}
