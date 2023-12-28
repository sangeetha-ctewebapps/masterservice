package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_EXTRACTION_PARAMETER")
public class ExtractionParameterMaster implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EXTRACTION_PARAMETER_ID")
	private long extractionParameterId;

	@Column(name = "EXTRACTION_PARAMETER_CODE")
	private String extractionParameterCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getExtractionParameterId() {
		return extractionParameterId;
	}

	public void setExtractionParameterId(long extractionParameterId) {
		this.extractionParameterId = extractionParameterId;
	}

	public String getExtractionParameterCode() {
		return extractionParameterCode;
	}

	public void setExtractionParameterCode(String extractionParameterCode) {
		this.extractionParameterCode = extractionParameterCode;
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
