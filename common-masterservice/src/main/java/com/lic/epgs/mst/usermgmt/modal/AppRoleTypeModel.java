package com.lic.epgs.mst.usermgmt.modal;

import java.util.Date;

public class AppRoleTypeModel {
	
	private Long userRoleTypeMappingId;
	
	private Long roleTypeId;
	
	private Long appModuleId;
	
	private Long masterUserId;
	
	private String isActive;
	
	private String isDeleted;
	
	private String modifiedBy;
	
	private Date modifiedOn;
	
	private String createdBy;
	
	private Date createdOn;
	
	private String appModule;
	
	private String roleTypeName;

	public Long getUserRoleTypeMappingId() {
		return userRoleTypeMappingId;
	}

	public void setUserRoleTypeMappingId(Long userRoleTypeMappingId) {
		this.userRoleTypeMappingId = userRoleTypeMappingId;
	}

	public Long getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(Long roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public Long getAppModuleId() {
		return appModuleId;
	}

	public void setAppModuleId(Long appModuleId) {
		this.appModuleId = appModuleId;
	}

	public Long getMasterUserId() {
		return masterUserId;
	}

	public void setMasterUserId(Long masterUserId) {
		this.masterUserId = masterUserId;
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

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getAppModule() {
		return appModule;
	}

	public void setAppModule(String appModule) {
		this.appModule = appModule;
	}

	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}
	
	
	
	

}
