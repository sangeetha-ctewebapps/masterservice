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
@Table(name = "MASTER_CATEGORY_FOR_GST_NON_APPLICABILITY")
public class CategoryForGstNonApplicabilityMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CATEGORY_FOR_GST_NON_APPLICABILITY_ID")
	private long categoryForGstNonApplicabilityId;

	@Column(name = "CATEGORY_FOR_GST_NON_APPLICABILITY_CODE")
	private String categoryForGstNonApplicabilityCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getCategoryForGstNonApplicabilityId() {
		return categoryForGstNonApplicabilityId;
	}

	public void setCategoryForGstNonApplicabilityId(long categoryForGstNonApplicabilityId) {
		this.categoryForGstNonApplicabilityId = categoryForGstNonApplicabilityId;
	}

	public String getCategoryForGstNonApplicabilityCode() {
		return categoryForGstNonApplicabilityCode;
	}

	public void setCategoryForGstNonApplicabilityCode(String categoryForGstNonApplicabilityCode) {
		this.categoryForGstNonApplicabilityCode = categoryForGstNonApplicabilityCode;
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
