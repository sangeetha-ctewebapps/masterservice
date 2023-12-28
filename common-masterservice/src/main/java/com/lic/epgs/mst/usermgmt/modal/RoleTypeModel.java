package com.lic.epgs.mst.usermgmt.modal;

import java.util.Date;

public class RoleTypeModel {
	
	
	private Long roleId;
	
	private String appModule;
	
	private String roleName;
	
	private String isActive;
	
	private String isDeleted;
	
	private String modifiedBy;
	
	private Date createdOn;
	
	private String createdBy;
	
	private Date modifiedOn;
	
	private String roleType;
	
	private Long appModuleId;
	
	private String moduleName;
	
	private Long roleTypeId;
	
	private String roleTypeName;
	
	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}

	private String displayRoleType;
	
	private String locationType;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getAppModule() {
		return appModule;
	}

	public void setAppModule(String appModule) {
		this.appModule = appModule;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Long getAppModuleId() {
		return appModuleId;
	}

	public void setAppModuleId(Long appModuleId) {
		this.appModuleId = appModuleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Long getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(Long roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public String getDisplayRoleType() {
		return displayRoleType;
	}

	public void setDisplayRoleType(String displayRoleType) {
		this.displayRoleType = displayRoleType;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	
	

}
