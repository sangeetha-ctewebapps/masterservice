package com.lic.epgs.rhssomodel;

public class TokenStatus {
	private int exp;
	private int iat;
	private String jti;
	private String iss;
//	public List<String> aud;
	private String sub;
	private String typ;
	private String azp;
	private String session_state;
	private String name;
	private String given_name;
	private String preferred_username;
	private boolean email_verified;
	private String acr;
	private RealmAccess realm_access;
	private ResourceAccess resource_access;
	private String scope;
	private String client_id;
	private String username;
	private boolean active;

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getIat() {
		return iat;
	}

	public void setIat(int iat) {
		this.iat = iat;
	}

	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

//	public List<String> getAud() {
//		return aud;
//	}
//
//	public void setAud(List<String> aud) {
//		this.aud = aud;
//	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getAzp() {
		return azp;
	}

	public void setAzp(String azp) {
		this.azp = azp;
	}

	public String getSession_state() {
		return session_state;
	}

	public void setSession_state(String session_state) {
		this.session_state = session_state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getPreferred_username() {
		return preferred_username;
	}

	public void setPreferred_username(String preferred_username) {
		this.preferred_username = preferred_username;
	}

	public boolean isEmail_verified() {
		return email_verified;
	}

	public void setEmail_verified(boolean email_verified) {
		this.email_verified = email_verified;
	}

	public String getAcr() {
		return acr;
	}

	public void setAcr(String acr) {
		this.acr = acr;
	}

	public RealmAccess getRealm_access() {
		return realm_access;
	}

	public void setRealm_access(RealmAccess realm_access) {
		this.realm_access = realm_access;
	}

	public ResourceAccess getResource_access() {
		return resource_access;
	}

	public void setResource_access(ResourceAccess resource_access) {
		this.resource_access = resource_access;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
