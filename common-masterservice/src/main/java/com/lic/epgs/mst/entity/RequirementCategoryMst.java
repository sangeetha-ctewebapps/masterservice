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
@Table(name = "MASTER_REQUIREMENT_CATEGORY")
public class RequirementCategoryMst implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REQUIREMENT_CATEGORY_ID")
	private long id;

	@Column(name = "REQUIREMENT_CATEGORY_CODE")
	private String requirementCategoryCode;

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

	public String getRequirementCategoryCode() {
		return requirementCategoryCode;
	}

	public void setRequirementCategoryCode(String requirementCategoryCode) {
		this.requirementCategoryCode = requirementCategoryCode;
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
		return "RequirementCategoryMst [id=" + id + ", requirementCategoryCode=" + requirementCategoryCode
				+ ", description=" + description + ", createdOn=" + createdOn + ", createdBy=" + createdBy + "]";
	}

}
