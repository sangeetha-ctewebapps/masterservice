package com.lic.epgs.rhssomodel;

public class RoleMap {
	private String roleType;
	private String roleName;

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "RoleMap [roleType=" + roleType + ", roleName=" + roleName + "]";
	}

}
