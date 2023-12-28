package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "MASTER_CO_OFFICE")
public class CoOffice implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;



	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CO_ID")
	private Long coId;



	@Column(name = "CO_CODE")
	private String co_code;



	@Column(name = "DESCRIPTION")
	private String description;



	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Date createdOn;



	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy = "System Admin";
	
	

	@Column(name = "STATE_CODE")
	private String stateCode;

	@Column(name = "PINCODE")
	private String pinCode;



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
	private String tin;

	@Column(name = "GSTIN")
	private String gstin;
	
	
	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	
	@Column(name = "IS_DELETED")
	private String isDeleted;


	public Long getCoId() {
		return coId;
	}


	public void setCoId(Long coId) {
		this.coId = coId;
	}


	public String getCo_code() {
		return co_code;
	}


	public void setCo_code(String co_code) {
		this.co_code = co_code;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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


	public String getStateCode() {
		return stateCode;
	}


	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}


	public String getPinCode() {
		return pinCode;
	}


	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
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


	public String getTin() {
		return tin;
	}


	public void setTin(String tin) {
		this.tin = tin;
	}


	public String getGstin() {
		return gstin;
	}


	public void setGstin(String gstin) {
		this.gstin = gstin;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	
	
}