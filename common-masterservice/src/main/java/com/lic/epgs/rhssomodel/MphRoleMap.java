package com.lic.epgs.rhssomodel;

public class MphRoleMap {
	private String roleType;
	private String roleName;
	private String mphName;
	
	public String getMphName() {
		return mphName;
	}

	public void setMphName(String mphName) {
		this.mphName = mphName;
	}

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
		return "RoleMap [roleType=" + roleType + ", roleName=" + roleName + ", mphName=" + mphName + "]";
	}

}
