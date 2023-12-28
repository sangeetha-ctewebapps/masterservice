package com.lic.epgs.mst.usermgmt.modal;

import java.util.Date;

public class HistoryDetailsModel {
	
	private String adminUserSrnumber;
	
	private String adminUserLocationCode;
	
	private String userSrnumber;
	
	private String userLocationCode;
	
	private String adminUserActivityPerformed;
	
	
	private String userModule;
	
	private String isUserAdmin;
	
	private String isUserMarketingNonMarketing;
	
	private String userOldRoles;
	
	private String userNewRoles;
	
	private Date createdOn;

	public String getAdminUserSrnumber() {
		return adminUserSrnumber;
	}

	public void setAdminUserSrnumber(String adminUserSrnumber) {
		this.adminUserSrnumber = adminUserSrnumber;
	}

	public String getAdminUserLocationCode() {
		return adminUserLocationCode;
	}

	public void setAdminUserLocationCode(String adminUserLocationCode) {
		this.adminUserLocationCode = adminUserLocationCode;
	}

	public String getUserSrnumber() {
		return userSrnumber;
	}

	public void setUserSrnumber(String userSrnumber) {
		this.userSrnumber = userSrnumber;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	} 
	
	
	

}
