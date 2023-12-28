package com.lic.epgs.mst.usermgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MASTER_ROLETYPE")
public class AppModuleRoleTypeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLETYPEID")
	private Long roleTypeId;

	@Column(name = "ROLETYPE_NAME")
	private String roleTypeName;

	@Column(name = "APPMODULEID")
	private Long appModuleId;

	
	@Column(name = "IS_ACTIVE")
	private String isActive;

	
	@Column(name = "IS_DELETE")
	private String isDelete;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "CREATED_ON")
	private String createdOn;

	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "MODIFIED_ON")
	private String modifiedOn;

	public AppModuleRoleTypeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(Long roleTypeId) {
		this.roleTypeId = roleTypeId;
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	
}
