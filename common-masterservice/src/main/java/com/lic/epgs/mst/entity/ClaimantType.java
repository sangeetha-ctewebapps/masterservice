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
@Table(name = "MASTER_CLAIMANT_TYPE")
public class ClaimantType implements Serializable {

	private static final long serialVersionUID = 9148801844284213681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CLAIMANT_ID")
	private long id;

	@Column(name = "CLAIMANT_CODE")
	private String claimantCode;

	@Column(name = "DESCRIPTION")
	private String claimantDesc;

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

	public String getClaimantCode() {
		return claimantCode;
	}

	public void setClaimantCode(String claimantCode) {
		this.claimantCode = claimantCode;
	}

	public String getClaimantDesc() {
		return claimantDesc;
	}

	public void setClaimantDesc(String claimantDesc) {
		this.claimantDesc = claimantDesc;
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
		return "ClaimantType [id=" + id + ", claimantCode=" + claimantCode + ", claimantDesc=" + claimantDesc
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}	
}

