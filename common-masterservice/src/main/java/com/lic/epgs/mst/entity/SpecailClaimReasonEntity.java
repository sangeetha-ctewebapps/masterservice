package com.lic.epgs.mst.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SPECIAL_CLAIM_REASON")
public class SpecailClaimReasonEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SPECIAL_CLAIM_REASON_ID")
	private Long specialClaimReasonId;
	
	@Column(name = "SPECIAL_CLAIM_REASON_CODE")
	private String specialClaimReasonCode;
	
	@Column(name = "DESCRIPTION")
	private String description;

	

	public Long getSpecialClaimReasonId() {
		return specialClaimReasonId;
	}

	public void setSpecialClaimReasonId(Long specialClaimReasonId) {
		this.specialClaimReasonId = specialClaimReasonId;
	}

	public String getSpecialClaimReasonCode() {
		return specialClaimReasonCode;
	}

	public void setSpecialClaimReasonCode(String specialClaimReasonCode) {
		this.specialClaimReasonCode = specialClaimReasonCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "SpecailClaimReasonEntity [specialClaimReasonId=" + specialClaimReasonId + ", specialClaimReasonCode="
				+ specialClaimReasonCode + ", description=" + description + "]";
	}
}
