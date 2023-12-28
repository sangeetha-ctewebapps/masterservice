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
@Table(name = "MASTER_RENEWAL_PROCESSING")
public class RenewalProcessingType implements Serializable {

	private static final long serialVersionUID = 9100045849884213681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RENEWAL_ID")
	private long id;

	@Column(name = "RENEWAL_CODE")
	private String renewalCode;

	@Column(name = "DESCRIPTION")
	private String description;

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

	public String getRenewalCode() {
		return renewalCode;
	}

	public void setRenewalCode(String renewalCode) {
		this.renewalCode = renewalCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "RenewalProcessingType [id=" + id + ", renewalCode=" + renewalCode + ", description=" + description
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}

}