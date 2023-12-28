package com.lic.epgs.mst.modal;

import java.util.Date;

import javax.persistence.Column;

public class MasterPolicyDataModel {
	
	private String policyNumber;
	private Long policyId;
	private Long unitId;
	private String unitCode;
	private Long zoneId;
	private String zoneName;
	private String createdBy;
	private Date createdOn;
	private String stream;
	private String mphCode;
	private String mphName;
	
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public Long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}
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
	public Long getZoneId() {
		return zoneId;
	}
	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
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
	public String getStream() {
		return stream;
	}
	public void setStream(String stream) {
		this.stream = stream;
	}
	public String getMphCode() {
		return mphCode;
	}
	public void setMphCode(String mphCode) {
		this.mphCode = mphCode;
	}
	public String getMphName() {
		return mphName;
	}
	public void setMphName(String mphName) {
		this.mphName = mphName;
	}

}
