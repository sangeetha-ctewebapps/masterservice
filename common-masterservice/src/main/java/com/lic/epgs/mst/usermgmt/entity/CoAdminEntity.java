package com.lic.epgs.mst.usermgmt.entity;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CO_ADMIN")
public class CoAdminEntity implements Serializable  {

	private static final long serialVersionUID = 2264263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CO_ADMIN_ID")
	private Long coAdminId;
	
	@Column(name = "MASTER_USER_ID")
	private Long masterUserId;

	@Column(name = "CO_NAME")
	private String coAdminName;
	
	@Column(name = "LOCATION_CODE")
	private String locationCode;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "IS_DELETED")
	private String isDeleted;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;

	

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	
	

	public Long getCoAdminId() {
		return coAdminId;
	}

	public void setCoAdminId(Long coAdminId) {
		this.coAdminId = coAdminId;
	}

	public String getCoAdminName() {
		return coAdminName;
	}

	public void setCoAdminName(String coAdminName) {
		this.coAdminName = coAdminName;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public Long getMasterUserId() {
		return masterUserId;
	}

	public void setMasterUserId(Long masterUserId) {
		this.masterUserId = masterUserId;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	

}
