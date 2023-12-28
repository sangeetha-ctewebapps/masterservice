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
@Table(name = "MASTER_BULK")
public class BulkTypeMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BULK_WITHDRAWAL_TYPE_ID")
	private long bulkWithdrawalTypeId;

	@Column(name = "BULK_WITHDRAWAL_TYPE_CODE")
	private String bulkWithdrawalTypeCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getBulkWithdrawalTypeId() {
		return bulkWithdrawalTypeId;
	}

	public void setBulkWithdrawalTypeId(long bulkWithdrawalTypeId) {
		this.bulkWithdrawalTypeId = bulkWithdrawalTypeId;
	}

	public String getBulkWithdrawalTypeCode() {
		return bulkWithdrawalTypeCode;
	}

	public void setBulkWithdrawalTypeCode(String bulkWithdrawalTypeCode) {
		this.bulkWithdrawalTypeCode = bulkWithdrawalTypeCode;
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
