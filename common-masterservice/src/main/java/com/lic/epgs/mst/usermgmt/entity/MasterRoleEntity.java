package com.lic.epgs.mst.usermgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MASTER_ROLE")
public class MasterRoleEntity implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLEID")
	private Long roleId;

	@Column(name = "APP_MODULE")
	private String appModule;

	@Column(name = "ROLENAME")
	private String roleName;

	@NotNull
	@Column(name = "IS_ACTIVE")
	private String isActive;

	@NotNull
	@Column(name = "IS_DELETED")
	private String isDeleted;

	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@NotNull
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MasterRoleEntity() {
		super();
		// TODO Auto-generated constructor stub
		this.isActive = "N";
		this.isDeleted = "N"; 
		this.createdBy = "Admin";
	}

	public MasterRoleEntity(Long roleId, String appModule, String roleName, Long userRoleMappingId, String isActive,
			String isDeleted, String modifiedBy, Date createdOn, String createdBy, Date modifiedOn, String roleType) {
		super();
		this.roleId = roleId;
		this.appModule = appModule;
		this.roleName = roleName;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.modifiedBy = modifiedBy;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.modifiedOn = modifiedOn;
		this.roleType = roleType;
	}

}
