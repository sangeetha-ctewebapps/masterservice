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
@Table(name = "MASTER_RELATIONSHIP")
public class RelationshipMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RELATIONSHIP_MST_ID")
	private long id;

	@Column(name = "RELATIONSHIP_MST_CODE")
	private String realtionshipMstCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRealtionshipMstCode() {
		return realtionshipMstCode;
	}

	public void setRealtionshipMstCode(String realtionshipMstCode) {
		this.realtionshipMstCode = realtionshipMstCode;
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

	@Override
	public String toString() {
		return "RelationshipMst [id=" + id + ", realtionshipMstCode=" + realtionshipMstCode + ", description="
				+ description + ", createdOn=" + createdOn + ", createdBy=" + createdBy + "]";
	}
	
	

}
