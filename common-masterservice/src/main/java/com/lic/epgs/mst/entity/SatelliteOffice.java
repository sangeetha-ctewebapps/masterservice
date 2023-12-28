package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MASTER_SATELLITE")
public class SatelliteOffice implements Serializable {

	private static final long serialVersionUID = 9188345849284213681L;

	@Id
	@SequenceGenerator(name = "SEQ", sequenceName = "MASTER_SATELLITE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
	@GenericGenerator(name = "satelliteId", strategy = "GenerationType.SEQUENCE")

	@Column(name = "SATELLITE_ID")
	private Long satelliteId;

	@Column(name = "SATELLITE_CODE")
	private String satelliteCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "IS_ACTIVE")
	private char isActive;

	@Column(name = "IS_DELETED")
	private char isDeleted = 'N';

	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@CreatedBy
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@UpdateTimestamp
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UNIT_ID")
	@JsonIgnore
	private UnitOffice unit;

	public Long getSatelliteId() {
		return satelliteId;
	}

	public void setSatelliteId(Long satelliteId) {
		this.satelliteId = satelliteId;
	}

	public String getSatelliteCode() {
		return satelliteCode;
	}

	public void setSatelliteCode(String satelliteCode) {
		this.satelliteCode = satelliteCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public UnitOffice getUnit() {
		return unit;
	}

	public void setUnit(UnitOffice unit) {
		this.unit = unit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	@Column(name = "PINCODE")
	private String pincode;   
	@Column(name = "CITY_NAME")
	private String cityName;      
	@Column(name = "DISTRICT_NAME")
	private String districtName;
	@Column(name = "STATE_NAME")
	private String stateName;     
	@Column(name = "ADDRESS1")
	private String address1;               
	@Column(name = "ADDRESS2")
	private String address2; 
	@Column(name = "ADDRESS3")
	private String address3; 
	@Column(name = "ADDRESS4")
	private String address4;  
	@Column(name = "EMAIL_ID")
	private String emailId; 
	@Column(name = "TELEPHONE_NO")
	private String telephoneNo; 
	@Column(name = "TIN")
	private Long tin;   
	@Column(name = "GSTIN")
	private String gstIn;
	
	
	

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public Long getTin() {
		return tin;
	}

	public void setTin(Long tin) {
		this.tin = tin;
	}

	public String getGstIn() {
		return gstIn;
	}

	public void setGstIn(String gstIn) {
		this.gstIn = gstIn;
	}

	@Override
	public String toString() {
		return "SatelliteOffice [satelliteId=" + satelliteId + ", satelliteCode=" + satelliteCode + ", description="
				+ description + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + ", unit="
				+ unit + "]";
	}

}
