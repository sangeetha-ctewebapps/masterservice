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
@Table(name = "MODULE_ROLETYPE_DESCRIPTION")
public class ModuleDescriptionRoleTypeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DESC_ID")
	private Long descId;
	
	@Column(name = "MODULE_NAME")
	private String moduleName;
	
	@Column(name = "MODULE_ID")
	private Long moduleId;
	
	
	@Column(name = "ROLETYPE_ID")
	private Long roleTypeId;
	
	@Column(name = "ROLETYPE_NAME")
	private String roletypeName;
	
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@NotNull
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;

	public Long getDescId() {
		return descId;
	}

	public void setDescId(Long descId) {
		this.descId = descId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(Long roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public String getRoletypeName() {
		return roletypeName;
	}

	public void setRoletypeName(String roletypeName) {
		this.roletypeName = roletypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	
	
	

}
