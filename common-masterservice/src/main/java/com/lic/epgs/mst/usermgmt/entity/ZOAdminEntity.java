package com.lic.epgs.mst.usermgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "ZO_Admin")
public class ZOAdminEntity implements Serializable {

	private static final long serialVersionUID = -3018084515166893237L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ZO_ADMIN_ID")
	private Long zoAdminId;

	@Column(name = "LOCATION")
	private String location;

	@NotNull
	@Column(name = "MASTER_USER_ID")
	private Long masterUserId;
	
	@Column(name = "LOCATION_CODE")
	private String locationCode;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@UpdateTimestamp
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;

	@NotBlank
	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@NotNull
	@Column(name = "IS_ACTIVE")
	private String isActive;

	@NotNull
	@Column(name = "IS_DELETED")
	private String isDeleted;

	public ZOAdminEntity() {

	}

	public Long getZoAdminId() {
		return zoAdminId;
	}

	public void setZoAdminId(Long zoAdminId) {
		this.zoAdminId = zoAdminId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public ZOAdminEntity(Long zoAdminId, String location, Long masterUserId, String locationCode, String modifiedBy,
			Date modifiedOn, String createdBy, Date createdOn, String isDeleted, String isActive) {
		super();
		this.zoAdminId = zoAdminId;
		this.location = location;
		this.masterUserId = masterUserId;
		this.locationCode = locationCode;
		this.modifiedBy = modifiedBy;
		this.modifiedOn = modifiedOn;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "ZOAdminEntity [zoAdminId=" + zoAdminId + ", location=" + location + ", masterUserId=" + masterUserId
				+ ", locationCode=" + locationCode + ", modifiedBy=" + modifiedBy + ", modifiedOn=" + modifiedOn
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn 
				+ ", isActive=" + isActive + ", isDeleted=" + isDeleted + "]";
	}
	
	
}