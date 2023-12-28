package com.lic.epgs.mst.usermgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "USERS_DETAIL_HISTORY")
public class UserDetailHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="user_history_generator")
	@SequenceGenerator(name="user_history_generator", sequenceName ="USER_HISTORY_SEQ",allocationSize=1)
	@Column(name = "USER_DETAIL_HISTORY_ID")
	private Long userDetailHistoryId;
	
	
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "MPH_NAME")
	private String mphname;
	
	@Column(name = "ACTION")
	private String action;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_BY")
	private String createdby;
	
	
	@Column(name = "CREATED_ON")
	private Date createdon;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "UNITCODE")
	private String unitCode;
	
	@Transient
	private String loggedInUser;
	

	@Column(name = "SRNUMBER_MAIN")
	private String srNumber;
	
	public UserDetailHistoryEntity() {
		super();
	}

	
	
	

	public String getSrNumber() {
		return srNumber;
	}





	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}





	public UserDetailHistoryEntity(Long userDetailHistoryId, String username, String email, String mphname,
			String action, String status, String createdby, Date createdon, String modifiedBy, Date modifiedOn,
			String remark, String unitCode, String loggedInUser) {
		super();
		this.userDetailHistoryId = userDetailHistoryId;
		this.username = username;
		this.email = email;
		this.mphname = mphname;
		this.action = action;
		this.status = status;
		this.createdby = createdby;
		this.createdon = createdon;
		this.modifiedBy = modifiedBy;
		this.modifiedOn = modifiedOn;
		this.remark = remark;
		this.unitCode = unitCode;
		this.loggedInUser = loggedInUser;
	}





	public UserDetailHistoryEntity(Long userDetailHistoryId, String username, String email, String mphname,
			String action, String status, String createdby, Date createdon, String modifiedBy, Date modifiedOn,
			String remark, String loggedInUser) {
		super();
		this.userDetailHistoryId = userDetailHistoryId;
		this.username = username;
		this.email = email;
		this.mphname = mphname;
		this.action = action;
		this.status = status;
		this.createdby = createdby;
		this.createdon = createdon;
		this.modifiedBy = modifiedBy;
		this.modifiedOn = modifiedOn;
		this.remark = remark;
		this.loggedInUser = loggedInUser;
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





	public String getUnitCode() {
		return unitCode;
	}





	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}





	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}





	public Long getUserDetailHistoryId() {
		return userDetailHistoryId;
	}

	public void setUserDetailHistoryId(Long userDetailHistoryId) {
		this.userDetailHistoryId = userDetailHistoryId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMphname() {
		return mphname;
	}

	public void setMphname(String mphname) {
		this.mphname = mphname;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(String loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
	
	
	
}
