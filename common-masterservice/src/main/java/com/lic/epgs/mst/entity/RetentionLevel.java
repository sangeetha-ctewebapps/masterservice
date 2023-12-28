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
@Table(name = "MASTER_RETENTION_LEVEL")
public class RetentionLevel implements Serializable {
	private static final long serialVersionUID = 938345849222213681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RETENTION_ID")
	private long id;

	@Column(name = "RETENTION_CODE")
	private String retentionCode;

	@Column(name = "DESCRIPTION")
	private String retentionDesc;

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

	public String getRetentionCode() {
		return retentionCode;
	}

	public void setRetentionCode(String retentionCode) {
		this.retentionCode = retentionCode;
	}

	public String getRetentionDesc() {
		return retentionDesc;
	}

	public void setRetentionDesc(String retentionDesc) {
		this.retentionDesc = retentionDesc;
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
		return "RetentionLevel [id=" + id + ", retentionCode=" + retentionCode + ", retentionDesc=" + retentionDesc
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}

	
	
}