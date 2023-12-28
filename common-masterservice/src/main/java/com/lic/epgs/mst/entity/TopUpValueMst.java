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
@Table(name = "MASTER_TOP_UP_VALUE")
public class TopUpValueMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TOP_UP_VALUE_ID")
	private long TopupvalueId;

	@Column(name = "TOP_UP_VALUE_CODE")
	private String TopupvalueCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getTopYpValueId() {
		return TopupvalueId;
	}

	public void setTopUpValueId(long TopupvalueId) {
		this.TopupvalueId = TopupvalueId;
	}

	public String getTopUpValueCode() {
		return TopupvalueCode;
	}

	public void setTopUpLoanCode(String TopupvalueCode) {
		this.TopupvalueCode = TopupvalueCode;
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
