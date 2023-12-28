package com.lic.epgs.mst.modal;

public class UserHistoryInputModel {

	private String fromDate;
	private String toDate;
	private String adminSrNumber;
	private String loggedInUser;
	private String locationCode;
	private String locationType;
	
	
	
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getAdminSrNumber() {
		return adminSrNumber;
	}
	public void setAdminSrNumber(String adminSrNumber) {
		this.adminSrNumber = adminSrNumber;
	}
	public String getLoggedInUser() {
		return loggedInUser;
	}
	public void setLoggedInUser(String loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
	
}
