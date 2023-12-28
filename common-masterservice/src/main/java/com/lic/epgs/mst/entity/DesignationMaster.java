package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "MASTER_DESIGNATION")
public class DesignationMaster implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@SequenceGenerator(name = "SEQ", sequenceName = "MASTER_DESIGNATION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
	@GenericGenerator(name = "designationId", strategy = "GenerationType.SEQUENCE")

	@Column(name = "DESIGNATION_ID")
	private long designationId;

	@Column(name = "DESIGNATION_CODE")
	private String designationCode;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "OFFICETYPE")
	private String officeType;

	@Column(name = "IS_ACTIVE")
	private char isActive;

	@Column(name = "IS_DELETED")
	private char isDeleted;

	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@CreatedBy
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@UpdateTimestamp
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;
	
	@Column(name = "ISINCHARGE")
	private String isIncharge;
	

	public long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(long designationId) {
		this.designationId = designationId;
	}

	public String getDesignationCode() {
		return designationCode;
	}

	public void setDesignationCode(String designationCode) {
		this.designationCode = designationCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public char getIsActive() {
		return isActive;
	}

	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}

	public char getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(char isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

	

	public String getIsIncharge() {
		return isIncharge;
	}

	public void setIsIncharge(String isIncharge) {
		this.isIncharge = isIncharge;
	}

	public String getOfficeType() {
		return officeType;
	}

	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}

	@Override
	public String toString() {
		return "DesignationMaster [designationId=" + designationId + ", designationCode=" + designationCode
				+ ", description=" + description + ", isActive=" + isActive + ", isDeleted=" + isDeleted
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + "]";
	}

}
