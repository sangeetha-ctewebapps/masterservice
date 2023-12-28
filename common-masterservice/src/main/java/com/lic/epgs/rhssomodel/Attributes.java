package com.lic.epgs.rhssomodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attributes {
	@JsonProperty("saml.assertion.signature")
	private String samlAssertionSignature;
	@JsonProperty("saml.force.post.binding")
	private String samlForcePostBinding;
	@JsonProperty("saml.multivalued.roles")
	private String samlMultivaluedRoles;
	@JsonProperty("saml.encrypt")
	private String samlEncrypt;
	@JsonProperty("saml.server.signature")
	private String samlServerSignature;
	@JsonProperty("saml.server.signature.keyinfo.ext")
	private String samlServerSignatureKeyinfoExt;
	@JsonProperty("exclude.session.state.from.auth.response")
	private String excludeSessionStateFromAuthResponse;
	@JsonProperty("client_credentials.use_refresh_token")
	private String clientCredentialsUseRefreshToken;
	private String saml_force_name_id_format;
	@JsonProperty("saml.client.signature")
	private String samlClientSignature;
	@JsonProperty("tls.client.certificate.bound.access.tokens")
	private String tlsClientCertificateBoundAccessTokens;
	@JsonProperty("saml.authnstatement")
	private String samlAuthnstatement;
	@JsonProperty("display.on.consent.screen")
	private String displayOnConsentScreen;
	@JsonProperty("saml.onetimeuse.condition")
	private String samlOnetimeuseCondition;

	public String getSamlAssertionSignature() {
		return samlAssertionSignature;
	}

	public void setSamlAssertionSignature(String samlAssertionSignature) {
		this.samlAssertionSignature = samlAssertionSignature;
	}

	public String getSamlForcePostBinding() {
		return samlForcePostBinding;
	}

	public void setSamlForcePostBinding(String samlForcePostBinding) {
		this.samlForcePostBinding = samlForcePostBinding;
	}

	public String getSamlMultivaluedRoles() {
		return samlMultivaluedRoles;
	}

	public void setSamlMultivaluedRoles(String samlMultivaluedRoles) {
		this.samlMultivaluedRoles = samlMultivaluedRoles;
	}

	public String getSamlEncrypt() {
		return samlEncrypt;
	}

	public void setSamlEncrypt(String samlEncrypt) {
		this.samlEncrypt = samlEncrypt;
	}

	public String getSamlServerSignature() {
		return samlServerSignature;
	}

	public void setSamlServerSignature(String samlServerSignature) {
		this.samlServerSignature = samlServerSignature;
	}

	public String getSamlServerSignatureKeyinfoExt() {
		return samlServerSignatureKeyinfoExt;
	}

	public void setSamlServerSignatureKeyinfoExt(String samlServerSignatureKeyinfoExt) {
		this.samlServerSignatureKeyinfoExt = samlServerSignatureKeyinfoExt;
	}

	public String getExcludeSessionStateFromAuthResponse() {
		return excludeSessionStateFromAuthResponse;
	}

	public void setExcludeSessionStateFromAuthResponse(String excludeSessionStateFromAuthResponse) {
		this.excludeSessionStateFromAuthResponse = excludeSessionStateFromAuthResponse;
	}

	public String getClientCredentialsUseRefreshToken() {
		return clientCredentialsUseRefreshToken;
	}

	public void setClientCredentialsUseRefreshToken(String clientCredentialsUseRefreshToken) {
		this.clientCredentialsUseRefreshToken = clientCredentialsUseRefreshToken;
	}

	public String getSaml_force_name_id_format() {
		return saml_force_name_id_format;
	}

	public void setSaml_force_name_id_format(String saml_force_name_id_format) {
		this.saml_force_name_id_format = saml_force_name_id_format;
	}

	public String getSamlClientSignature() {
		return samlClientSignature;
	}

	public void setSamlClientSignature(String samlClientSignature) {
		this.samlClientSignature = samlClientSignature;
	}

	public String getTlsClientCertificateBoundAccessTokens() {
		return tlsClientCertificateBoundAccessTokens;
	}

	public void setTlsClientCertificateBoundAccessTokens(String tlsClientCertificateBoundAccessTokens) {
		this.tlsClientCertificateBoundAccessTokens = tlsClientCertificateBoundAccessTokens;
	}

	public String getSamlAuthnstatement() {
		return samlAuthnstatement;
	}

	public void setSamlAuthnstatement(String samlAuthnstatement) {
		this.samlAuthnstatement = samlAuthnstatement;
	}

	public String getDisplayOnConsentScreen() {
		return displayOnConsentScreen;
	}

	public void setDisplayOnConsentScreen(String displayOnConsentScreen) {
		this.displayOnConsentScreen = displayOnConsentScreen;
	}

	public String getSamlOnetimeuseCondition() {
		return samlOnetimeuseCondition;
	}

	public void setSamlOnetimeuseCondition(String samlOnetimeuseCondition) {
		this.samlOnetimeuseCondition = samlOnetimeuseCondition;
	}

}
