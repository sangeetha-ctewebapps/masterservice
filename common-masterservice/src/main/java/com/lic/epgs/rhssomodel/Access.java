package com.lic.epgs.rhssomodel;

public class Access {
	private boolean view;
	private boolean configure;
	private boolean manage;

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public boolean isConfigure() {
		return configure;
	}

	public void setConfigure(boolean configure) {
		this.configure = configure;
	}

	public boolean isManage() {
		return manage;
	}

	public void setManage(boolean manage) {
		this.manage = manage;
	}

}
