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
@Table(name = "MASTER_TYPE_TOPUP")
public class TypeOfTopupMaster implements Serializable{

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TYPE_TOPUP_ID")
	private long typeTopupId;

	@Column(name = "TYPE_TOPUP_CODE")
	private String typeTopupCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getTypeTopupId() {
		return typeTopupId;
	}

	public void setTypeTopupId(long typeTopupId) {
		this.typeTopupId = typeTopupId;
	}

	public String getTypeTopupCode() {
		return typeTopupCode;
	}

	public void setTypeTopupCode(String typeTopupCode) {
		this.typeTopupCode = typeTopupCode;
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
