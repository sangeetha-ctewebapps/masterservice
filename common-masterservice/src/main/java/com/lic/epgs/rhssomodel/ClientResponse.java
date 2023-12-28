package com.lic.epgs.rhssomodel;

import java.util.List;

public class ClientResponse {
	private String id;
	private String clientId;
	private String rootUrl;
	private boolean surrogateAuthRequired;
	private boolean enabled;
	private boolean alwaysDisplayInConsole;
	private String clientAuthenticatorType;
	private List<String> redirectUris;
	private List<Object> webOrigins;
	private int notBefore;
	private boolean bearerOnly;
	private boolean consentRequired;
	private boolean standardFlowEnabled;
	private boolean implicitFlowEnabled;
	private boolean directAccessGrantsEnabled;
	private boolean serviceAccountsEnabled;
	private boolean publicClient;
	private boolean frontchannelLogout;
	private String protocol;
	private Attributes attributes;
	private AuthenticationFlowBindingOverrides authenticationFlowBindingOverrides;
	private boolean fullScopeAllowed;
	private int nodeReRegistrationTimeout;
	private List<String> defaultClientScopes;
	private List<String> optionalClientScopes;
	private Access access;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRootUrl() {
		return rootUrl;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public boolean isSurrogateAuthRequired() {
		return surrogateAuthRequired;
	}

	public void setSurrogateAuthRequired(boolean surrogateAuthRequired) {
		this.surrogateAuthRequired = surrogateAuthRequired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAlwaysDisplayInConsole() {
		return alwaysDisplayInConsole;
	}

	public void setAlwaysDisplayInConsole(boolean alwaysDisplayInConsole) {
		this.alwaysDisplayInConsole = alwaysDisplayInConsole;
	}

	public String getClientAuthenticatorType() {
		return clientAuthenticatorType;
	}

	public void setClientAuthenticatorType(String clientAuthenticatorType) {
		this.clientAuthenticatorType = clientAuthenticatorType;
	}

	public List<String> getRedirectUris() {
		return redirectUris;
	}

	public void setRedirectUris(List<String> redirectUris) {
		this.redirectUris = redirectUris;
	}

	public List<Object> getWebOrigins() {
		return webOrigins;
	}

	public void setWebOrigins(List<Object> webOrigins) {
		this.webOrigins = webOrigins;
	}

	public int getNotBefore() {
		return notBefore;
	}

	public void setNotBefore(int notBefore) {
		this.notBefore = notBefore;
	}

	public boolean isBearerOnly() {
		return bearerOnly;
	}

	public void setBearerOnly(boolean bearerOnly) {
		this.bearerOnly = bearerOnly;
	}

	public boolean isConsentRequired() {
		return consentRequired;
	}

	public void setConsentRequired(boolean consentRequired) {
		this.consentRequired = consentRequired;
	}

	public boolean isStandardFlowEnabled() {
		return standardFlowEnabled;
	}

	public void setStandardFlowEnabled(boolean standardFlowEnabled) {
		this.standardFlowEnabled = standardFlowEnabled;
	}

	public boolean isImplicitFlowEnabled() {
		return implicitFlowEnabled;
	}

	public void setImplicitFlowEnabled(boolean implicitFlowEnabled) {
		this.implicitFlowEnabled = implicitFlowEnabled;
	}

	public boolean isDirectAccessGrantsEnabled() {
		return directAccessGrantsEnabled;
	}

	public void setDirectAccessGrantsEnabled(boolean directAccessGrantsEnabled) {
		this.directAccessGrantsEnabled = directAccessGrantsEnabled;
	}

	public boolean isServiceAccountsEnabled() {
		return serviceAccountsEnabled;
	}

	public void setServiceAccountsEnabled(boolean serviceAccountsEnabled) {
		this.serviceAccountsEnabled = serviceAccountsEnabled;
	}

	public boolean isPublicClient() {
		return publicClient;
	}

	public void setPublicClient(boolean publicClient) {
		this.publicClient = publicClient;
	}

	public boolean isFrontchannelLogout() {
		return frontchannelLogout;
	}

	public void setFrontchannelLogout(boolean frontchannelLogout) {
		this.frontchannelLogout = frontchannelLogout;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public AuthenticationFlowBindingOverrides getAuthenticationFlowBindingOverrides() {
		return authenticationFlowBindingOverrides;
	}

	public void setAuthenticationFlowBindingOverrides(
			AuthenticationFlowBindingOverrides authenticationFlowBindingOverrides) {
		this.authenticationFlowBindingOverrides = authenticationFlowBindingOverrides;
	}

	public boolean isFullScopeAllowed() {
		return fullScopeAllowed;
	}

	public void setFullScopeAllowed(boolean fullScopeAllowed) {
		this.fullScopeAllowed = fullScopeAllowed;
	}

	public int getNodeReRegistrationTimeout() {
		return nodeReRegistrationTimeout;
	}

	public void setNodeReRegistrationTimeout(int nodeReRegistrationTimeout) {
		this.nodeReRegistrationTimeout = nodeReRegistrationTimeout;
	}

	public List<String> getDefaultClientScopes() {
		return defaultClientScopes;
	}

	public void setDefaultClientScopes(List<String> defaultClientScopes) {
		this.defaultClientScopes = defaultClientScopes;
	}

	public List<String> getOptionalClientScopes() {
		return optionalClientScopes;
	}

	public void setOptionalClientScopes(List<String> optionalClientScopes) {
		this.optionalClientScopes = optionalClientScopes;
	}

	public Access getAccess() {
		return access;
	}

	public void setAccess(Access access) {
		this.access = access;
	}

}
