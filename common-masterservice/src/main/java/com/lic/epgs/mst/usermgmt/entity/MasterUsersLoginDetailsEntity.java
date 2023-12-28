package com.lic.epgs.mst.usermgmt.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@Entity
@Table(name = "MASTER_USERS_LOGIN_DETAILS")
public class MasterUsersLoginDetailsEntity {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="master_User_Login_Details_generator")
	@SequenceGenerator(name="master_User_Login_Details_generator", sequenceName ="MASTER_USER_LOGIN_DETAILS_SEQ",allocationSize=1)
	@Column(name = "LOGINID")
	private Long loginId;

	@Column(name = "USERNAME")
	private String userName;

//	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss a",timezone = "Asia/Kolkata")
	//@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	//@JsonSerialize
	@Column(name = "LOGGEDINTIME")
	private Date loggedINTime;
	
	
	
//	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss a",timezone = "Asia/Kolkata")
	//@JsonSerialize
	@Column(name = "LOGGEDOUTTIME")
	private Date loggedOutTime;
	
	
	@Column(name = "CREATEDBY")
	private String createdBy;

//	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss a",timezone = "Asia/Kolkata")
	@Column(name = "CREATEDON")
	private Date createdOn;

	
	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	
//	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
//	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss a",timezone = "Asia/Kolkata")
	@Column(name = "MODIFIEDON")
	private Date modifiedOn;
	
	@Column(name = "SRNUMBER")
	private String srNumber;
	
	@Column(name = "ISACTIVE")
	private String isActive;

	@Column(name = "ISDELETED")
	private String isDeleted;

	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLoggedINTime() {
		return loggedINTime;
	}

	public void setLoggedINTime(Date loggedINTime) {
		this.loggedINTime = loggedINTime;
	}

	public Date getLoggedOutTime() {
		return loggedOutTime;
	}

	public void setLoggedOutTime(Date loggedOutTime) {
		this.loggedOutTime = loggedOutTime;
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

	public String getSrNumber() {
		return srNumber;
	}

	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
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
	
	
	

	
	
	
	

}
