package com.lic.epgs.mst.usermgmt.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MASTER_CADRE")
public class MasterCadre implements Serializable {

	private static final long serialVersionUID = 2264263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CADREID")
	private long cadreId;

	@NotNull
	@Column(name = "CADRE")
	private String cadre;

	@NotNull
	@Column(name = "CADRECODE")
	private String cadreCode;

	@NotNull
	@Column(name = "IS_ACTIVE")
	private String isActive;

	@NotNull
	@Column(name = "IS_DELETED")
	private String isDeleted;

	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	@Column(name = "MODIFIEDON")
	private Date modifiedOn;

	@NotBlank
	@Column(name = "CREATEDBY")
	private String createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;
	
	@Column(name = "DESIGNATION")
	private String designation;
	

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public long getCadreId() {
		return cadreId;
	}

	public void setCadreId(long cadreId) {
		this.cadreId = cadreId;
	}

	public String getCadreCode() {
		return cadreCode;
	}

	public void setCadreCode(String cadreCode) {
		this.cadreCode = cadreCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCadre() {
		return cadre;
	}

	public void setCadre(String cadre) {
		this.cadre = cadre;
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

}