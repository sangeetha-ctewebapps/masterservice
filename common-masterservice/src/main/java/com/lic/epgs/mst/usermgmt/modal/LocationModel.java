package com.lic.epgs.mst.usermgmt.modal;

public class LocationModel {
	
	
	private String locationType;
	
	private String location;
	
	
	private String swalambanMenu;

	public LocationModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LocationModel(String locationType, String location, String roleName, String roleType, String swalambanMenu) {
		super();
		this.locationType = locationType;
		this.location = location;
		
		this.swalambanMenu = swalambanMenu;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSwalambanMenu() {
		return swalambanMenu;
	}

	public void setSwalambanMenu(String swalambanMenu) {
		this.swalambanMenu = swalambanMenu;
	}

	

	
}
