package com.lic.epgs.mst.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "MASTER_CAUSE_OF_EVENT")
public class CauseOfEventEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CAUSE_ID")
	private Long causeId;

	@Column(name = "CAUSE_OF_EVENT_CODE")
	private String causeOfEventCode;

	@Column(name = "DESCRIPTION")
	private String causeDesc;

	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	public CauseOfEventEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CauseOfEventEntity(Long id, String reasonCode, String reasonDesc, String createdBy, Date createdOn) {
		super();
		this.causeId = id;
		this.causeOfEventCode = reasonCode;
		this.causeDesc = reasonDesc;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
	}

	public Long getId() {
		return causeId;
	}

	public void setId(Long id) {
		this.causeId = id;
	}

	public String getReasonCode() {
		return causeOfEventCode;
	}

	public void setReasonCode(String reasonCode) {
		this.causeOfEventCode = reasonCode;
	}

	public String getReasonDesc() {
		return causeDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.causeDesc = reasonDesc;
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
		return "CauseOfEventEntity [id=" + causeId + ", reasonCode=" + causeOfEventCode + ", reasonDesc=" + causeDesc
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}
	
	

}
