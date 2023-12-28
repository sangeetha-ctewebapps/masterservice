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
@Table(name = "MASTER_UNDERWRITING_DECISION")
public class UnderwritingDecisionMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UNDERWRITING_DECISION_ID")
	private long underwritingDecisionId;

	@Column(name = "UNDERWRITING_DECISION_CODE")
	private String underwritingDecisionCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getUnderwritingDecisionId() {
		return underwritingDecisionId;
	}

	public void setUnderwritingDecisionId(long underwritingDecisionId) {
		this.underwritingDecisionId = underwritingDecisionId;
	}

	public String getUnderwritingDecisionCode() {
		return underwritingDecisionCode;
	}

	public void setUnderwritingDecisionCode(String underwritingDecisionCode) {
		this.underwritingDecisionCode = underwritingDecisionCode;
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
