package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "REASON_FOR_REFUND")

public class ReasonForRefundEntity implements Serializable {
	
	private static final long serialVersionUID = 2268345849284213681L;

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REFUND_ID")
	private long refundId;
	
	@Column(name = "REFUND_CODE")
	private long refundCode;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;

	public long getRefundId() {
		return refundId;
	}

	public void setRefundId(long refundId) {
		this.refundId = refundId;
	}

	public long getRefundCode() {
		return refundCode;
	}

	public void setRefundCode(long refundCode) {
		this.refundCode = refundCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReasonForRefundEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ReasonForRefundEntity [refundId=" + refundId + ", refundCode=" + refundCode + ", description="
				+ description + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", isActive=" + isActive
				+ ", modifiedBy=" + modifiedBy + ", modifiedOn=" + modifiedOn + ", getRefundId()=" + getRefundId()
				+ ", getRefundCode()=" + getRefundCode() + ", getDescription()=" + getDescription()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getCreatedOn()=" + getCreatedOn() + ", getIsActive()="
				+ getIsActive() + ", getModifiedBy()=" + getModifiedBy() + ", getModifiedOn()=" + getModifiedOn()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	

	
	
	
	
	
	
	

	
	
	

}
