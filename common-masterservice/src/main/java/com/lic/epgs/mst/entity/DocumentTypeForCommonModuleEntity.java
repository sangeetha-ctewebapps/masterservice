package com.lic.epgs.mst.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_COMMONMODULE_DOCUMENTS")
public class DocumentTypeForCommonModuleEntity {
	
	@Id
	@Column(name = "DOCUMENT_ID")
	private Long documentId;
	
	@Column(name = "DOCUMENT_NAME")
	private String documentName;
	
	@Column(name = "DOCUMENT_DESC")
	private String documentDesc;
	
	@Column(name = "COMMON_MODULE_NAME")
	private String commonModuleName;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	@Column(name = "IS_DELETED")
	private String isDeleted;
	
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;	

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentDesc() {
		return documentDesc;
	}

	public void setDocumentDesc(String documentDesc) {
		this.documentDesc = documentDesc;
	}

	public String getCommonModuleName() {
		return commonModuleName;
	}

	public void setCommonModuleName(String commonModuleName) {
		this.commonModuleName = commonModuleName;
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}	
	
}
