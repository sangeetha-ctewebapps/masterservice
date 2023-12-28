package com.lic.epgs.mst.usermgmt.modal;

import java.util.Date;

public class SoAdminModel {
	
private Long masterUserId;
	
	private Long adminId;
	
	private String userName;
	
	private String srNumber;
	
	private String srNumber2;
	
	private String cadre;
	
	private String locationType;
	
	private String location;
	
	private String locationCode;
	
	private String modifiedBy;
	
	private Date modifiedOn;
	
	private String isActive;
	
	private String isDeleted;

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getMasterUserId() {
		return masterUserId;
	}

	public void setMasterUserId(Long masterUserId) {
		this.masterUserId = masterUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}

	public String getCadre() {
		return cadre;
	}

	public void setCadre(String cadre) {
		this.cadre = cadre;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
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

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getSrNumber2() {
		return srNumber2;
	}

	public void setSrNumber2(String srNumber2) {
		this.srNumber2 = srNumber2;
	}
	
	

}
