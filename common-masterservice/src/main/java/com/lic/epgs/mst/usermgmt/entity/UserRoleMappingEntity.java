package com.lic.epgs.mst.usermgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author SK00611397
 *
 */
@Entity
@Table(name = "USER_ROLE_MAPPING")

public class UserRoleMappingEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="user_role_generator")
	@SequenceGenerator(name="user_role_generator", sequenceName ="USER_ROLE_SEQ",allocationSize=1)
	@Column(name = "USERROLEMAPID")
	private Long userRoleMappingId;
	
	@NotNull
	@Column(name = "MODULEID")
	private Long moduleId;
	
	@NotNull
	@Column(name = "ROLEID")
	private Long roleId;
	
	@NotNull
	@Column(name = "MASTER_USER_ID")
	private Long masterUserId;
	
	@NotNull
	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	@NotNull
	@Column(name = "IS_DELETED")
	private String isDeleted;
	
	@Column(name = "MODIFIEDBY")
	private String modifiedBy;
	
	@Column(name = "MODIFIEDON")
	private Date modifiedOn;
	
	@NotBlank
	@Column(name = "CREATEDBY")
	private String createdBy;
	
	@Column(name = "CREATEDON")
	private Date createdOn;

	public UserRoleMappingEntity() {
		super();
		// TODO Auto-generated constructor stub
		this.userRoleMappingId = 0L;
		this.moduleId = 0L;
		this.roleId = 0L;
		this.masterUserId = 0L;
		this.isActive = "N";
		this.isDeleted = "N";
		this.createdBy = "Admin";
	}

	public UserRoleMappingEntity(Long userRoleMappingId, Long moduleId, Long roleId, Long masterUserId, String isActive,
			String isDeleted, String modifiedBy, Date modifiedOn, String createdBy, Date createdOn) {
		super();
		this.userRoleMappingId = userRoleMappingId;
		this.moduleId = moduleId;
		this.roleId = roleId;
		this.masterUserId = masterUserId;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.modifiedBy = modifiedBy;
		this.modifiedOn = modifiedOn;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
	}

	public Long getUserRoleMappingId() {
		return userRoleMappingId;
	}

	public void setUserRoleMappingId(Long userRoleMappingId) {
		this.userRoleMappingId = userRoleMappingId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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

	@Override
	public String toString() {
		return "UserRoleMappingEntity [userRoleMappingId=" + userRoleMappingId + ", moduleId=" + moduleId + ", roleId="
				+ roleId + ", masterUserId=" + masterUserId + ", isActive=" + isActive + ", isDeleted=" + isDeleted
				+ ", modifiedBy=" + modifiedBy + ", modifiedOn=" + modifiedOn + ", createdBy=" + createdBy
				+ ", createdOn=" + createdOn + "]";
	}
	
	

}
