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
@Table(name = "MASTER_ENCASH_PAY_MODE")
public class EncashPayModeMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ENCASH_PAY_MODE_ID")
	private long encashPayModeId;

	@Column(name = "ENCASH_PAY_MODE_CODE")
	private String encashPayModeCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getEncashPayModeId() {
		return encashPayModeId;
	}

	public void setEncashPayModeId(long encashPayModeId) {
		this.encashPayModeId = encashPayModeId;
	}

	public String getEncashPayModeCode() {
		return encashPayModeCode;
	}

	public void setEncashPayModeCode(String encashPayModeCode) {
		this.encashPayModeCode = encashPayModeCode;
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
