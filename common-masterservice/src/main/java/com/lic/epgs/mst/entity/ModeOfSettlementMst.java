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
@Table(name = "MASTER_MODE_OF_SETTLEMENT")
public class ModeOfSettlementMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MODE_OF_SETTLEMENT_ID")
	private long modeOfSettlementId;

	@Column(name = "MODE_OF_SETTLEMENT_CODE")
	private String modeOfSettlementCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getModeOfSettlementId() {
		return modeOfSettlementId;
	}

	public void setModeOfSettlementId(long modeOfSettlementId) {
		this.modeOfSettlementId = modeOfSettlementId;
	}

	public String getModeOfSettlementCode() {
		return modeOfSettlementCode;
	}

	public void setModeOfSettlementCode(String modeOfSettlementCode) {
		this.modeOfSettlementCode = modeOfSettlementCode;
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
