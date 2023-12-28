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
@Table(name = "MASTER_TREATY_TYPE")
public class TreatyTypeMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TREATY_TYPE_ID")
	private long treatyTypeId;

	@Column(name = "TREATY_TYPE_CODE")
	private String treatyTypeCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getTreatyTypeId() {
		return treatyTypeId;
	}

	public void setTreatyTypeId(long treatyTypeId) {
		this.treatyTypeId = treatyTypeId;
	}

	public String getTreatyTypeCode() {
		return treatyTypeCode;
	}

	public void setTreatyTypeCode(String treatyTypeCode) {
		this.treatyTypeCode = treatyTypeCode;
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
