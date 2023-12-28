package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_LIFE_COVERAGE_TYPE")
public class LifeCoverageTypeMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LIFE_COVERAGE_TYPE_ID")
	private long lifeCoverageTypeId;

	@Column(name = "LIFE_COVERAGE_TYPE_CODE")
	private String lifeCoverageTypeCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getLifeCoverageTypeId() {
		return lifeCoverageTypeId;
	}

	public void setLifeCoverageTypeId(long lifeCoverageTypeId) {
		this.lifeCoverageTypeId = lifeCoverageTypeId;
	}

	public String getLifeCoverageTypeCode() {
		return lifeCoverageTypeCode;
	}

	public void setLifeCoverageTypeCode(String lifeCoverageTypeCode) {
		this.lifeCoverageTypeCode = lifeCoverageTypeCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}