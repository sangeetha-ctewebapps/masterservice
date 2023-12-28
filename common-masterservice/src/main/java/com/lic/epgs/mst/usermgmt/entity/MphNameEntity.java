package com.lic.epgs.mst.usermgmt.entity;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERMGMT_INTERNET_MPH")
public class MphNameEntity implements Serializable  {

	private static final long serialVersionUID = 2264263195138765828L;

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MPH_ID")
	private String mphId;
	
	@Column(name = "MPH_NAME")
	private String mphName;

	@Column(name = "MPH_CODE")
	private String mphCode;
	
	@Column(name = "MPH_KEY")
	private String mphKey;

	@Column(name = "IS_ACTIVE")
	private char isActive;

	@Column(name = "IS_DELETED")
	private char isDeleted;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "MPH_ADMIN_MAXCOUNT")
	private long mphAdminMaxCOunt;
	
	@Column(name = "FIRST_ADMIN_CREATED")
	private char firstAdminCreated;
	
	@Column(name = "PORTALS_ASSIGNED")
	private String portalsAssigned;
	
	@Column(name = "POLICY_NUMBER")
	private String policyNumber;
	

	public String getPortalsAssigned() {
		return portalsAssigned;
	}

	public void setPortalsAssigned(String portalsAssigned) {
		this.portalsAssigned = portalsAssigned;
	}

	public char getFirstAdminCreated() {
		return firstAdminCreated;
	}

	public void setFirstAdminCreated(char firstAdminCreated) {
		this.firstAdminCreated = firstAdminCreated;
	}

	public long getMphAdminMaxCOunt() {
		return mphAdminMaxCOunt;
	}

	public void setMphAdminMaxCOunt(long mphAdminMaxCOunt) {
		this.mphAdminMaxCOunt = mphAdminMaxCOunt;
	}

	public String getMphId() {
		return mphId;
	}

	public void setMphId(String mphId) {
		this.mphId = mphId;
	}

	public String getMphName() {
		return mphName;
	}

	public void setMphName(String mphName) {
		this.mphName = mphName;
	}

	public String getMphCode() {
		return mphCode;
	}

	public void setMphCode(String mphCode) {
		this.mphCode = mphCode;
	}

	public String getMphKey() {
		return mphKey;
	}

	public void setMphKey(String mphKey) {
		this.mphKey = mphKey;
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

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	
	
	

	
	

}
