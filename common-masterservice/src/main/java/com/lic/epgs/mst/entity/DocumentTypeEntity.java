package com.lic.epgs.mst.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_CLAIM_REQUIREMENT")
public class DocumentTypeEntity {
	
	@Id
	@Column(name = "CLAIM_ID")
	private Long claimId;
	@Column(name = "CLAIM_CODE")
	private String claimCode;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "CREATED_ON")
	private Date createdOn;
	@Column(name = "CREATED_BY")
	private String createdBy;
	public Long getClaimId() {
		return claimId;
	}
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}
	public String getClaimCode() {
		return claimCode;
	}
	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
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
	

}
