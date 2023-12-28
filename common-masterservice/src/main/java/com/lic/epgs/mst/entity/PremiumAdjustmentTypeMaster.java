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
@Table(name = "MASTER_PREMIUM_ADJUSTMENT_TYPE")

public class PremiumAdjustmentTypeMaster implements Serializable {
	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PREMIUM_TYPE_ID")
	private long premiumTypeId;

	@Column(name = "PREMIUM_TYPE_CODE")
	private String premiumTypeCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getPremiumTypeId() {
		return premiumTypeId;
	}

	public void setPremiumTypeId(long premiumTypeId) {
		this.premiumTypeId = premiumTypeId;
	}

	public String getPremiumTypeCode() {
		return premiumTypeCode;
	}

	public void setPremiumTypeCode(String premiumTypeCode) {
		this.premiumTypeCode = premiumTypeCode;
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
