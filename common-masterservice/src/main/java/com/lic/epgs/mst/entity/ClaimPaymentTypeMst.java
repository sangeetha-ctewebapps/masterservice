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
@Table(name = "MASTER_CLAIM_PAYMENT_TYPE")
public class ClaimPaymentTypeMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CLAIM_PAYMENT_TYPE_ID")
	private long claimPaymentTypeId;

	@Column(name = "CLAIM_PAYMENT_TYPE_CODE")
	private String claimPaymentTypeCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getClaimPaymentTypeId() {
		return claimPaymentTypeId;
	}

	public void setClaimPaymentTypeId(long claimPaymentTypeId) {
		this.claimPaymentTypeId = claimPaymentTypeId;
	}

	public String getClaimPaymentTypeCode() {
		return claimPaymentTypeCode;
	}

	public void setClaimPaymentTypeCode(String claimPaymentTypeCode) {
		this.claimPaymentTypeCode = claimPaymentTypeCode;
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
