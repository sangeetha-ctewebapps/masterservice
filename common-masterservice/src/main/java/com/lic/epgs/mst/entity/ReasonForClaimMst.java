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
@Table(name = "MASTER_REASON_FOR_CLAIM")
public class ReasonForClaimMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REASON_FOR_CLAIM_ID")
	private long reasonForClaimId;

	@Column(name = "REASON_FOR_CLAIM_CODE")
	private String reasonForClaimCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getReasonForClaimId() {
		return reasonForClaimId;
	}

	public void setReasonForClaimId(long reasonForClaimId) {
		this.reasonForClaimId = reasonForClaimId;
	}

	public String getReasonForClaimCode() {
		return reasonForClaimCode;
	}

	public void setReasonForClaimCode(String reasonForClaimCode) {
		this.reasonForClaimCode = reasonForClaimCode;
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