package com.lic.epgs.mst.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_CITY")
public class CitySearchEntity {
private static final long serialVersionUID = 2064263195138765828L;
	
	@Id
	@Column(name = "CITY_ID")
	private long cityId;
	@Column(name = "DISTRICT_ID")
	private long districtId;
	@Column(name = "CITY_CODE")
	private String cityCode;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "IS_ACTIVE")
	private String isActive;
	@Column(name = "IS_DELETED")
	private String isDeleted;
	@Column(name = "CREATED_ON")
	private Date createdOn;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
