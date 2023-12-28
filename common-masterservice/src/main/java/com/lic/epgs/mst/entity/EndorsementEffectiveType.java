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
@Table(name = "MASTER_ENDORSEMENT_EFFECTIVE_TYPE")
public class EndorsementEffectiveType implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ENDORSEMENTET_ID")
	private long id;

	@Column(name = "ENDORSEMENTET_CODE")
	private String endorsementet_code;

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
		return "EndorsementEffectiveType [id=" + id + ", endorsementet_code=" + endorsementet_code + ", description="
				+ description + ", createdOn=" + createdOn + ", createdBy=" + createdBy + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEndorsementet_code() {
		return endorsementet_code;
	}

	public void setEndorsementet_code(String endorsementet_code) {
		this.endorsementet_code = endorsementet_code;
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