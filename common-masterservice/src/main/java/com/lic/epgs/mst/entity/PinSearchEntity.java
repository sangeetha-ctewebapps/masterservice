package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;

public class PinSearchEntity implements Serializable {
	
	private static final long serialVersionUID = 2064263195138765828L;
	
	private long pinId;
	private String pinCode;
	private String isActive;
	private String isDeleted;
	private String modifiedBy;
	private Date modifiedOn;
	
	private long cityId;
	private String cityCode;
	private String cityDescription;
	
	private long districtId;
	private String districtCode;
	private String districtDescription;
	
	private long stateId;
	private String stateCode;
	private String stateDescription;
	
	private long countryId;
	private String countryCode;
	private String countryDescription;
	public long getPinId() {
		return pinId;
	}
	public void setPinId(long pinId) {
		this.pinId = pinId;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
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
	public void setModifiedOn(Date timestamp) {
		this.modifiedOn = timestamp;
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityDescription() {
		return cityDescription;
	}
	public void setCityDescription(String cityDescription) {
		this.cityDescription = cityDescription;
	}
	public long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getDistrictDescription() {
		return districtDescription;
	}
	public void setDistrictDescription(String districtDescription) {
		this.districtDescription = districtDescription;
	}
	public long getStateId() {
		return stateId;
	}
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getStateDescription() {
		return stateDescription;
	}
	public void setStateDescription(String stateDescription) {
		this.stateDescription = stateDescription;
	}
	public long getCountryId() {
		return countryId;
	}
	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryDescription() {
		return countryDescription;
	}
	public void setCountryDescription(String countryDescription) {
		this.countryDescription = countryDescription;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
