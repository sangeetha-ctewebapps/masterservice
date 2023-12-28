package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "MASTER_ENDORSEMENT_SUB_TYPE")
public class EndorsementSubType implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ENDORSEMENTST_ID")
	private long id;

	@Column(name = "ENDORSEMENTST_CODE")
	private String endorsementst_code;

	@Column(name = "DESCRIPTION")
	private String description;

	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy = "System Admin";

	@Override
	public String toString() {
		return "EndorsementSubType [id=" + id + ", endorsementst_code=" + endorsementst_code + ", description="
				+ description + ", createdOn=" + createdOn + ", createdBy=" + createdBy + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEndorsementst_code() {
		return endorsementst_code;
	}

	public void setEndorsementst_code(String endorsementst_code) {
		this.endorsementst_code = endorsementst_code;
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