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
@Table(name = "MASTER_CLAIM_RECOMMENDATION")
public class ClaimRecommendation implements Serializable {

	private static final long serialVersionUID = 9148601844284213681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CLAIM_ID")
	private long id;

	@Column(name = "CLAIM_CODE")
	private String claimCode;

	@Column(name = "DESCRIPTION")
	private String claimDesc;

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

	public String getClaimCode() {
		return claimCode;
	}

	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}

	public String getClaimDesc() {
		return claimDesc;
	}

	public void setClaimDesc(String claimDesc) {
		this.claimDesc = claimDesc;
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
		return "ClaimRecommendation [id=" + id + ", claimCode=" + claimCode + ", claimDesc=" + claimDesc
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}

}