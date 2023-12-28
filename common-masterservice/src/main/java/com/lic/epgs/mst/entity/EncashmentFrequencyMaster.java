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
@Table(name = "MASTER_ENCASHMENT_FREQUENCY")
public class EncashmentFrequencyMaster implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ENCASHMENT_FREQUENCY_ID")
	private long encashmentFrequencyId;

	@Column(name = "ENCASHMENT_FREQUENCY_CODE")
	private String encashmentFrequencyCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getEncashmentFrequencyId() {
		return encashmentFrequencyId;
	}

	public void setEncashmentFrequencyId(long encashmentFrequencyId) {
		this.encashmentFrequencyId = encashmentFrequencyId;
	}

	public String getEncashmentFrequencyCode() {
		return encashmentFrequencyCode;
	}

	public void setEncashmentFrequencyCode(String encashmentFrequencyCode) {
		this.encashmentFrequencyCode = encashmentFrequencyCode;
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
