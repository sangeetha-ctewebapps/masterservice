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
@Table(name = "MASTER_LEAVE_ENCASHMENT_BENEFIT_TYPE")
public class LeaveEncashmentBenefitTypeMaster implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MASTER_LEAVE_ENCASHMENT_BENEFIT_TYPE_ID")
	private long leaveEncashmentBenefitTypeId;

	@Column(name = "MASTER_LEAVE_ENCASHMENT_BENEFIT_TYPE_CODE")
	private String leaveEncashmentBenefitTypeCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getLeaveEncashmentBenefitTypeId() {
		return leaveEncashmentBenefitTypeId;
	}

	public void setLeaveEncashmentBenefitTypeId(long leaveEncashmentBenefitTypeId) {
		this.leaveEncashmentBenefitTypeId = leaveEncashmentBenefitTypeId;
	}

	public String getLeaveEncashmentBenefitTypeCode() {
		return leaveEncashmentBenefitTypeCode;
	}

	public void setLeaveEncashmentBenefitTypeCode(String leaveEncashmentBenefitTypeCode) {
		this.leaveEncashmentBenefitTypeCode = leaveEncashmentBenefitTypeCode;
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
