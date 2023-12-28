package com.lic.epgs.mst.usermgmt.modal;

import java.util.List;

public class AssignRolesModel {
	
	private String srNumber;
	
	private String loggedInUsername;
	
	private Long appModuleId;
	
	private String appModuleName;
	
	private List<String> displayRoleType;

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}
	
	public String getLoggedInUsername() {
		return loggedInUsername;
	}

	public void setLoggedInUsername(String loggedInUsername) {
		this.loggedInUsername = loggedInUsername;
	}

	public Long getAppModuleId() {
		return appModuleId;
	}

	public String getAppModuleName() {
		return appModuleName;
	}

	public void setAppModuleName(String appModuleName) {
		this.appModuleName = appModuleName;
	}

	public void setAppModuleId(Long appModuleId) {
		this.appModuleId = appModuleId;
	}

	public List<String> getDisplayRoleType() {
		return displayRoleType;
	}

	public void setDisplayRoleType(List<String> displayRoleType) {
		this.displayRoleType = displayRoleType;
	}
	
	
}
