package com.lic.epgs.mst.usermgmt.modal;

public class MandhanLocationModel {

	private String locationType;
	
	private String location;
	
	
	private String mandhanMenu;

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


	public String getMandhanMenu() {
		return mandhanMenu;
	}


	public void setMandhanMenu(String mandhanMenu) {
		this.mandhanMenu = mandhanMenu;
	}


	@Override
	public String toString() {
		return "MandhanLocationModel [locationType=" + locationType + ", location=" + location + ", mandhanMenu="
				+ mandhanMenu + ", getLocationType()=" + getLocationType() + ", getLocation()=" + getLocation()
				+ ", getMandhanMenu()=" + getMandhanMenu() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}


	public MandhanLocationModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	


}
