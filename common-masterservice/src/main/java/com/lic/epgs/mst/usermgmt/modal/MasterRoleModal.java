package com.lic.epgs.mst.usermgmt.modal;

public class MasterRoleModal {

	private String roleName;
	private String roleType;

	public MasterRoleModal() {
		super();
	}

	public MasterRoleModal(String roleName, String roleType) {
		super();
		this.roleName = roleName;
		this.roleType = roleType;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

}
