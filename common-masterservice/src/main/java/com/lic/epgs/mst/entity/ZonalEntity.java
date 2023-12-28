package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "MASTER_ZONAL")
public class ZonalEntity implements Serializable {
	
	private static final long serialVersionUID = 2064263195138765828L;
	
	@Id
	@Column(name = "ZONAL_ID")
	private long zonalId;
	@Column(name = "ZONAL_CODE")
	private String zonalCode;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "IS_ACTIVE")
	private char isActive;
	@Column(name = "IS_DELETED")
	private char isDeleted;
	@Column(name = "CREATED_ON")
	private Date createdOn;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	
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
	
	
	
	
	public long getZonalId() {
		return zonalId;
	}
	public void setZonalId(long zonalId) {
		this.zonalId = zonalId;
	}
	public String getZonalCode() {
		return zonalCode;
	}
	public void setZonalCode(String zonalCode) {
		this.zonalCode = zonalCode;
	}
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
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
	
	

	
}
