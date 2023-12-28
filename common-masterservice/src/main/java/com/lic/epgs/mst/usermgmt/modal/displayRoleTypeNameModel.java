package com.lic.epgs.mst.usermgmt.modal;

import java.util.List;

public class displayRoleTypeNameModel {
	
	private String appModuleId;
	
	private String appModuleName;
	
	private List<String> displayRoleTypes;
	
	private List<String> applicableRoleTypes;

	public String getAppModuleId() {
		return appModuleId;
	}

	public void setAppModuleId(String appModuleId) {
		this.appModuleId = appModuleId;
	}

	public List<String> getDisplayRoleTypes() {
		return displayRoleTypes;
	}

	public void setDisplayRoleTypes(List<String> displayRoleTypes) {
		this.displayRoleTypes = displayRoleTypes;
	}

	public String getAppModuleName() {
		return appModuleName;
	}

	public void setAppModuleName(String appModuleName) {
		this.appModuleName = appModuleName;
	}

	public List<String> getApplicableRoleTypes() {
		return applicableRoleTypes;
	}

	public void setApplicableRoleTypes(List<String> applicableRoleTypes) {
		this.applicableRoleTypes = applicableRoleTypes;
	}
	
	
	

}
