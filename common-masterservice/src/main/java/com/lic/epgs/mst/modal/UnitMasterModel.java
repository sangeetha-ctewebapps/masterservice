package com.lic.epgs.mst.modal;

public class UnitMasterModel {
	
	private Long unitId;
	private String unitCode;
	private String cityName;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	private char isActive;
	private char isDeleted;
	private Long zonalId;
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
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
	public Long getZonalId() {
		return zonalId;
	}
	public void setZonalId(Long zonalId) {
		this.zonalId = zonalId;
	}
	
		
	

}
