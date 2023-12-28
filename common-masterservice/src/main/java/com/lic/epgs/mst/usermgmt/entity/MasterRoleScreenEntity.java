package com.lic.epgs.mst.usermgmt.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_ROLE_SCREEN")
public class MasterRoleScreenEntity implements Serializable {


	private static final long serialVersionUID = 2264263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_SCREEN_ID")
	private Long roleScreenId;

	@Column(name = "ROLE_ID")
	private Long roleId;
	
	@Column(name = "SCREEN_NAME")
	private String screenName;
	
	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	@Column(name = "IS_DELETED")
	private String isDeleted;
	
	@Column(name = "MODIFIEDBY")
	private String modifiedBy;
	
	@Column(name = "MODIFIEDON")
	private Date modifiedOn;

	@Column(name = "CREATEDBY")
	private String createdBy;
	
	@Column(name = "CREATEDON")
	private Date createdOn;
	
	@Column(name = "ROLENAME")
	private String roleName;

	public long getRoleScreenId() {
		return roleScreenId;
	}

	public void setRoleScreenId(long roleScreenId) {
		this.roleScreenId = roleScreenId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
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
	
	

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setRoleScreenId(Long roleScreenId) {
		this.roleScreenId = roleScreenId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
	