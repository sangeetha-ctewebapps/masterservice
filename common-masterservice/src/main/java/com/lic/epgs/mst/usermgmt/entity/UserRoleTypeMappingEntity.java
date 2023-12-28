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

@Entity
@Table(name = "USER_ROLETYPE_MAPPING")
public class UserRoleTypeMappingEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="user_role_type_generator")
	@SequenceGenerator(name="user_role_type_generator", sequenceName ="USER_ROLE_TYPE_SEQ",allocationSize=1)
	@Column(name = "USERROLETYPEMAPID")
	private Long userRoleTypeMappingId;
	
	@NotNull
	@Column(name = "APPMODULEID")
	private Long appModuleId;
	
	@NotNull
	@Column(name = "ROLETYPEID")
	private Long roleTypeId;
	
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

	public UserRoleTypeMappingEntity() {
		super();
		// TODO Auto-generated constructor stub
		this.appModuleId = 0L;
		this.roleTypeId = 0L;
		this.masterUserId = 0L;
		this.isActive = "N";
		this.isDeleted = "N";
		this.createdBy = "Admin";
	}

	public UserRoleTypeMappingEntity(Long userRoleTypeMappingId, @NotNull Long appModuleId, @NotNull Long roleTypeId,
			@NotNull Long masterUserId, @NotNull String isActive, @NotNull String isDeleted, String modifiedBy,
			Date modifiedOn, @NotBlank String createdBy, Date createdOn) {
		super();
		this.userRoleTypeMappingId = userRoleTypeMappingId;
		this.appModuleId = appModuleId;
		this.roleTypeId = roleTypeId;
		this.masterUserId = masterUserId;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.modifiedBy = modifiedBy;
		this.modifiedOn = modifiedOn;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
	}

	public Long getUserRoleTypeMappingId() {
		return userRoleTypeMappingId;
	}

	public void setUserRoleTypeMappingId(Long userRoleTypeMappingId) {
		this.userRoleTypeMappingId = userRoleTypeMappingId;
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

	
	
}
