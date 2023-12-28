package com.lic.epgs.mst.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_CAUSE_OF_SUB_EVENT")
public class CauseOfSubEventEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SUB_CAUSE_ID")
	private Long subcauseId;

	@Column(name = "SUB_CAUSE_NAME")
	private String subCauseName;

	@Column(name = "SUB_CAUSE_INDICATOR")
	private String subCauseIndicator;

	
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	@Column(name = "IS_DELETED")
	private String isDeleted;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;

	public Long getSubcauseId() {
		return subcauseId;
	}

	public void setSubcauseId(Long subcauseId) {
		this.subcauseId = subcauseId;
	}

	public String getSubCauseName() {
		return subCauseName;
	}

	public void setSubCauseName(String subCauseName) {
		this.subCauseName = subCauseName;
	}

	public String getSubCauseIndicator() {
		return subCauseIndicator;
	}

	public void setSubCauseIndicator(String subCauseIndicator) {
		this.subCauseIndicator = subCauseIndicator;
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

	

}
