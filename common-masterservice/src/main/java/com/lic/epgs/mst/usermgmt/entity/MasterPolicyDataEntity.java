package com.lic.epgs.mst.usermgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity 
@Table(name = "COMMON_MASTER_POLICY_DATA") 
public class MasterPolicyDataEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="master_policy_id_seq_Generator")
	@SequenceGenerator(name="master_policy_id_seq_Generator", sequenceName ="COMMON_MASTER_POLICY_SEQ", allocationSize=1)
	@Column(name = "MASTER_POLICY_ID")
	private Long masterpolicyid;
	
	@Column(name = "POLICY_NUMBER")
	private String policyNumber;
	
	@Column(name = "POLICY_ID")
	private Long policyId;
	
	@Column(name = "UNIT_ID")
	private Long unitId;
	
	@Column(name = "UNIT_CODE")
	private String unitCode;
	
	@Column(name = "ZONAL_ID")
	private Long zoneId;
	
	@Column(name = "ZONE_NAME")
	private String zoneName;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;
	
	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	@Column(name = "STREAM")
	private String stream;
	
	@Column(name = "MPH_CODE")
	private String mphCode;
	
	
	@Column(name = "MPH_NAME")
	private String mphName;


	public Long getMasterpolicyid() {
		return masterpolicyid;
	}


	public void setMasterpolicyid(Long masterpolicyid) {
		this.masterpolicyid = masterpolicyid;
	}


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


	public String getIsActive() {
		return isActive;
	}


	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
