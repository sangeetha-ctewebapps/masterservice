package com.lic.epgs.mst.usermgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LOGGEDINUSER_SESSION_DETAILS")
public class LoggedInUserSessionDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "USERNAME")
	private String userName;
	
	@Column(name = "IP")
	private String ipAddress;
	
	@Column(name = "LOGGEDINUSER")
	private String loggedinUser;
	
	@Column(name = "LOGGEDINON")
	private Date loggedinOn;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	@Column(name = "CREATEDBY")
	private String createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	
	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	
	@Column(name = "MODIFIEDON")
	private Date modifiedOn;

	
	@Column(name = "BROWSER")
	private String browser;

	public String getBrowser() {
		return browser;
	}


	public void setBrowser(String browser) {
		this.browser = browser;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public String getLoggedinUser() {
		return loggedinUser;
	}


	public void setLoggedinUser(String loggedinUser) {
		this.loggedinUser = loggedinUser;
	}


	public Date getLoggedinOn() {
		return loggedinOn;
	}


	public void setLoggedinOn(Date loggedinOn) {
		this.loggedinOn = loggedinOn;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
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


	


	
	
	
	

}
