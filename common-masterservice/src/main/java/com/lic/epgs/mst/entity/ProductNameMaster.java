package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "MASTER_PRODUCT_NAME")
public class ProductNameMaster implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@SequenceGenerator(name = "SEQ", sequenceName = "PRODUCT_NAME_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
	@GenericGenerator(name = "ProductNameId", strategy = "GenerationType.SEQUENCE")

	@Column(name = "PRODUCT_NAME_ID")
	private long productNameId;

	@Column(name = "PRODUCT_NAME_CODE")
	private String productNameCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "IS_ACTIVE")
	private char isActive;
	
	@Column(name = "IS_DELETED")
	private char isDeleted = 'N';

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

	public long getProductNameId() {
		return productNameId;
	}

	public void setProductNameId(long productNameId) {
		this.productNameId = productNameId;
	}

	public String getProductNameCode() {
		return productNameCode;
	}

	public void setProductNameCode(String productNameCode) {
		this.productNameCode = productNameCode;
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

	@Override
	public String toString() {
		return "ProductNameMaster [productNameId=" + productNameId + ", productNameCode=" + productNameCode
				+ ", description=" + description + ", isActive=" + isActive + ", isDelete=" + isDeleted + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + "]";
	}

}
