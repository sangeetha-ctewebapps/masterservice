package com.lic.epgs.mst.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "MASTER_PRODUCT_VARIANT_DETAILS")
public class ProductVariantEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VARIANT_ID")
	private long variantId;
	
	
	@Column(name = "PRODUCT_NAME_ID")
	private long productNameId;

	@Column(name = "LOB")
	private String lob;
	
	@Column(name = "CATAGEORY")
	private String catageory;
	
	@Column(name = "VARIANT_TYPE")
	private String variantType;
	
	@Column(name = "VARIANT_CODE")
	private String variantCode;
	
	@Column(name = "PRODUCT")
	private String product;

	@Column(name = "DESCRIPTION")
	private String description;

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

	public long getVariantId() {
		return variantId;
	}

	public void setVariantId(long variantId) {
		this.variantId = variantId;
	}

	public long getProductNameId() {
		return productNameId;
	}

	public void setProductNameId(long productNameId) {
		this.productNameId = productNameId;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getCatageory() {
		return catageory;
	}

	public void setCatageory(String catageory) {
		this.catageory = catageory;
	}

	public String getVariantType() {
		return variantType;
	}

	public void setVariantType(String variantType) {
		this.variantType = variantType;
	}

	public String getVariantCode() {
		return variantCode;
	}

	public void setVariantCode(String variantCode) {
		this.variantCode = variantCode;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
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
	
	
	

}
