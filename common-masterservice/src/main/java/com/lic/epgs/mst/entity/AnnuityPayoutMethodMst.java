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
@Table(name = "MASTER_ANNUITY_PAYOUT_METHOD")
public class AnnuityPayoutMethodMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ANNUITY_PAYOUT_METHOD_ID")
	private long annuityPayoutMethodId;

	@Column(name = "ANNUITY_PAYOUT_METHOD_CODE")
	private String annuityPayoutMethodCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getAnnuityPayoutMethodId() {
		return annuityPayoutMethodId;
	}

	public void setAnnuityPayoutMethodId(long annuityPayoutMethodId) {
		this.annuityPayoutMethodId = annuityPayoutMethodId;
	}

	public String getAnnuityPayoutMethodCode() {
		return annuityPayoutMethodCode;
	}

	public void setAnnuityPayoutMethodCode(String annuityPayoutMethodCode) {
		this.annuityPayoutMethodCode = annuityPayoutMethodCode;
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
