package com.lic.epgs.rhssomodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResourceAccess {
	@JsonProperty("realm-management")
	private RealmManagement realmManagement;
	private Account account;

	public RealmManagement getRealmManagement() {
		return realmManagement;
	}

	public void setRealmManagement(RealmManagement realmManagement) {
		this.realmManagement = realmManagement;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
