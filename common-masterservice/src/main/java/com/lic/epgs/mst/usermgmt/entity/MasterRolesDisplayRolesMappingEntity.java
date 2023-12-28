package com.lic.epgs.mst.usermgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MASTER_ROLES_DISPLAYROLES_MAPPING")
public class MasterRolesDisplayRolesMappingEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MASTER_ROLES_DISPLAYROLES_MAPPING_ID")
	private Long masterRolesDisplayRolesMappingId;
	
	@Column(name = "APP_MODULE_NAME")
	private String appModuleName;
	
	
	@Column(name = "ROLE_TYPE_NAME")
	private String roleTypeName;
	
	@NotNull
	@Column(name = "APP_MODULE_ID")
	private Long appModuleId;
	
	@NotNull
	@Column(name = "ROLE_TYPE_ID")
	private Long roleTypeId;
	
	
	@Column(name = "DISPLAY_ROLE_TYPE_NAME")
	private String displayRoleTypeName;
	
	@Column(name = "USER_TYPE")
	private String userType;
	
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;
	
	@NotBlank
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	
	@Column(name = "REMARKS")
	private String remarks;

	public Long getMasterRolesDisplayRolesMappingId() {
		return masterRolesDisplayRolesMappingId;
	}

	public void setMasterRolesDisplayRolesMappingId(Long masterRolesDisplayRolesMappingId) {
		this.masterRolesDisplayRolesMappingId = masterRolesDisplayRolesMappingId;
	}

	public String getAppModuleName() {
		return appModuleName;
	}

	public void setAppModuleName(String appModuleName) {
		this.appModuleName = appModuleName;
	}

	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}

	public Long getAppModuleId() {
		return appModuleId;
	}

	public void setAppModuleId(Long appModuleId) {
		this.appModuleId = appModuleId;
	}

	public Long getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(Long roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public String getDisplayRoleTypeName() {
		return displayRoleTypeName;
	}

	public void setDisplayRoleTypeName(String displayRoleTypeName) {
		this.displayRoleTypeName = displayRoleTypeName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}
