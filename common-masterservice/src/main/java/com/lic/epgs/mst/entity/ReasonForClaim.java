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
@Table(name = "MASTER_REASON_FOR_CLAIM")
public class ReasonForClaim implements Serializable {

	private static final long serialVersionUID = 2268345849284213681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REASON_ID")
	private long id;

	@Column(name = "REASON_CODE")
	private String reasonCode;

	@Column(name = "DESCRIPTION")
	private String reasonDesc;

	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
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

	@Override
	public String toString() {
		return "ReasonForClaim [id=" + id + ", reasonCode=" + reasonCode + ", reasonDesc=" + reasonDesc + ", createdBy="
				+ createdBy + ", createdOn=" + createdOn + "]";
	}

}