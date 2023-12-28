package com.lic.epgs.mst.usermgmt.modal;

import java.util.List;

public class UserRoleModel {
	
	private String userName;
	private Long masterUserId;
	
	private String srNumber;
	private String locationType;
	private String location;
	private String isUserAdmin;
	private String userCategory;
	private List<displayRoleTypeNameModel> roleType;
	 private String emailId;

	
	public Long getMasterUserId() {
		return masterUserId;
	}
	public void setMasterUserId(Long masterUserId) {
		this.masterUserId = masterUserId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<displayRoleTypeNameModel> getRoleType() {
		return roleType;
	}
	public void setRoleType(List<displayRoleTypeNameModel> roleType) {
		this.roleType = roleType;
	}
	public String getSrNumber() {
		return srNumber;
	}
	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}
	public String getIsUserAdmin() {
		return isUserAdmin;
	}
	public void setIsUserAdmin(String isUserAdmin) {
		this.isUserAdmin = isUserAdmin;
	}
	public String getUserCategory() {
		return userCategory;
	}
	public void setUserCategory(String userCategory) {
		this.userCategory = userCategory;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
	
	
	

}
