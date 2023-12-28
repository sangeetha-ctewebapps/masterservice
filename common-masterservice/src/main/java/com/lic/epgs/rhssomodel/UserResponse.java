package com.lic.epgs.rhssomodel;

import java.util.List;

public class UserResponse {
	private String id;
	private long createdTimestamp;
	private String username;
	private boolean enabled;
	private boolean totp;
	private boolean emailVerified;
	private String firstName;
	private String lastName;
	private String email;
	private long moblile;
	private List<Object> disableableCredentialTypes;
	private List<Object> requiredActions;
	private int notBefore;
	private AccessModel access;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(long createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

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

	public boolean isTotp() {
		return totp;
	}

	public void setTotp(boolean totp) {
		this.totp = totp;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public List<Object> getDisableableCredentialTypes() {
		return disableableCredentialTypes;
	}

	public void setDisableableCredentialTypes(List<Object> disableableCredentialTypes) {
		this.disableableCredentialTypes = disableableCredentialTypes;
	}

	public List<Object> getRequiredActions() {
		return requiredActions;
	}

	public void setRequiredActions(List<Object> requiredActions) {
		this.requiredActions = requiredActions;
	}

	public int getNotBefore() {
		return notBefore;
	}

	public void setNotBefore(int notBefore) {
		this.notBefore = notBefore;
	}

	public AccessModel getAccess() {
		return access;
	}

	public void setAccess(AccessModel access) {
		this.access = access;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
