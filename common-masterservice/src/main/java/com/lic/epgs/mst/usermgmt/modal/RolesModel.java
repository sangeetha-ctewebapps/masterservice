package com.lic.epgs.mst.usermgmt.modal;

public class RolesModel {
	 String id;
	 String name;
	  boolean composite;
	 boolean clientRole;
	
	
	
	 public boolean isComposite() {
		return composite;
	}

	public void setComposite(boolean composite) {
		this.composite = composite;
	}

	public boolean isClientRole() {
		return clientRole;
	}

	public void setClientRole(boolean clientRole) {
		this.clientRole = clientRole;
	}

	//public AccessModel access;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
/*
	public AccessModel getAccess() {
		return access;
	}

	public void setAccess(AccessModel access) {
		this.access = access;
	
	}
	*/

}
