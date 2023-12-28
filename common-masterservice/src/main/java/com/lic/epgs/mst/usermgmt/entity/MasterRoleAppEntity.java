package com.lic.epgs.mst.usermgmt.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_ROLE")
public class MasterRoleAppEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLEID")
	private Long roleId;

	@Column(name = "APP_MODULE")
	private String appModule;
	
	@Column(name = "MODULENAME")
	private String appModuleName;


	@Column(name = "ROLENAME")
	private String roleName;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "IS_DELETED")
	private String isDeleted;

	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "CREATEDBY")
	private String createdBy;

	@Column(name = "MODIFIEDON")
	private Date modifiedOn;

	@Column(name = "ROLE_TYPE")
	private String roleType;

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

	public String getAppModuleName() {
		return appModuleName;
	}

	public void setAppModuleName(String appModuleName) {
		this.appModuleName = appModuleName;
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

	
	

}
