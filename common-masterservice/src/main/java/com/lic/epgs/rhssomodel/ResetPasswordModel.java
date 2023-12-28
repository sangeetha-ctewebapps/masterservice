package com.lic.epgs.rhssomodel;

public class ResetPasswordModel {
	private String type;
	private boolean temporary;
	private String value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isTemporary() {
		return temporary;
	}

	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ResetPasswordModel [type=" + type + ", temporary=" + temporary + ", value=" + value + "]";
	}

}
