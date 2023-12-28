package com.lic.epgs.mst.usermgmt.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MASTER_FUNCTION_NAME")
public class FunctionalAreaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FUNCTION_ID")
	private Long functionId;

	@Column(name = "FUNCTIONDESCRIPTION")
	private String functionDescription;

	
	@Column(name = "CREATEDBY")
	private String createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	@Column(name = "MODIFIEDON")
	private Date modifiedOn;

	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	@Column(name = "IS_DELETED")
	private String isDeleted;

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public String getFunctionDescription() {
		return functionDescription;
	}

	public void setFunctionDescription(String functionDescription) {
		this.functionDescription = functionDescription;
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
	
	

	

	


}