package com.lic.epgs.mst.usermgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "MASTER_USERS_HISTORY")
public class MasterUsersHistoryDetailsEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "HISTORY_ID")
	private Long historyId;
	
	@Column(name = "ADMINUSER_NAME")
	private String adminUserName;
  
	@Column(name = "ADMINUSER_SRNO")
	private String adminUserSrnumber;
	
	
	@Column(name = "ADMINUSER_LOCATION_TYPE")
	private String adminUserLocationType;
	
	@Column(name = "ADMINUSER_LOCATION_CODE")
	private String adminUserLocationCode;
	
	
	@Column(name = "USER_NAME")
	private String userName;
	
	
	@Column(name = "USER_SRNO")
	private String userSrnumber;
	
	
	@Column(name = "USER_LOCATIONTYPE")
	private String userLocationType;
	
	
	@Column(name = "USER_LOCATIONCODE")
	private String userLocationCode;
	
	@Column(name = "ADMINUSER_ACTIVITY_PERFORMED")
	private String adminUserActivityPerformed;
	
	
	@Column(name = "USER_MODULE")
	private String userModule;
	
	@Column(name = "IS_USER_ADMIN")
	private String isUserAdmin;
	
	@Column(name = "IS_USER_MARKETING_NONMARKETING")
	private String isUserMarketingNonMarketing;
	
	
	@Column(name = "USER_OLD_ROLES")
	private String userOldRoles;
	
	@Column(name = "USER_NEW_ROLES")
	private String userNewRoles;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn; 
	
	@Column(name ="REMARKS")
	private String remarks;
	
	@Column(name ="CATEGORY")
	private String category;

	@Column(name = "USER_EXPIRY_ON")
 	private Date userexpiryon;

	public Date getUserexpiryon() {
		return userexpiryon;
	}



	public void setUserexpiryon(Date userexpiryon) {
		this.userexpiryon = userexpiryon;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public String getAdminUserSrnumber() {
		return adminUserSrnumber;
	}

	public void setAdminUserSrnumber(String adminUserSrnumber) {
		this.adminUserSrnumber = adminUserSrnumber;
	}

	public String getAdminUserLocationType() {
		return adminUserLocationType;
	}

	public void setAdminUserLocationType(String adminUserLocationType) {
		this.adminUserLocationType = adminUserLocationType;
	}

	public String getAdminUserLocationCode() {
		return adminUserLocationCode;
	}

	public void setAdminUserLocationCode(String adminUserLocationCode) {
		this.adminUserLocationCode = adminUserLocationCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSrnumber() {
		return userSrnumber;
	}

	public void setUserSrnumber(String userSrnumber) {
		this.userSrnumber = userSrnumber;
	}

	public String getUserLocationType() {
		return userLocationType;
	}

	public void setUserLocationType(String userLocationType) {
		this.userLocationType = userLocationType;
	}

	public String getUserLocationCode() {
		return userLocationCode;
	}

	public void setUserLocationCode(String userLocationCode) {
		this.userLocationCode = userLocationCode;
	}

	public String getAdminUserActivityPerformed() {
		return adminUserActivityPerformed;
	}

	public void setAdminUserActivityPerformed(String adminUserActivityPerformed) {
		this.adminUserActivityPerformed = adminUserActivityPerformed;
	}

	public String getUserModule() {
		return userModule;
	}

	public void setUserModule(String userModule) {
		this.userModule = userModule;
	}

	public String getIsUserAdmin() {
		return isUserAdmin;
	}

	public void setIsUserAdmin(String isUserAdmin) {
		this.isUserAdmin = isUserAdmin;
	}

	public String getIsUserMarketingNonMarketing() {
		return isUserMarketingNonMarketing;
	}

	public void setIsUserMarketingNonMarketing(String isUserMarketingNonMarketing) {
		this.isUserMarketingNonMarketing = isUserMarketingNonMarketing;
	}

	public String getUserOldRoles() {
		return userOldRoles;
	}

	public void setUserOldRoles(String userOldRoles) {
		this.userOldRoles = userOldRoles;
	}

	public String getUserNewRoles() {
		return userNewRoles;
	}

	public void setUserNewRoles(String userNewRoles) {
		this.userNewRoles = userNewRoles;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	

}
