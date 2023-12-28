package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "DYNAMIC_URL_CONFIG")
public class DynamicUrlConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8842051889763676091L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DYNAMIC_URL_CONFIG_ID")
	private long configId;
	@Column(name = "NAME")
	private String name;
	@Column(name = "URLVALUE")
	private String urlvalue;
	@Column(name = "PROVIDER")
	private String provider;
	@Column(name = "PROFILE")
	private String profile;
	@Column(name = "ISACTIVE")
	private String isactive;
	@Column(name = "ISDELETED")
	private String isdeleted;
	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@CreatedBy
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@UpdateTimestamp
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;

	public DynamicUrlConfig(String name, String urlvalue, String provider, String profile, String isactive,
			String isdeleted) {
		super();
		this.name = name;
		this.urlvalue = urlvalue;
		this.provider = provider;
		this.profile = profile;
		this.isactive = isactive;
		this.isdeleted = isdeleted;
	}

	public long getConfigId() {
		return configId;
	}

	public void setConfigId(long configId) {
		this.configId = configId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlvalue() {
		return urlvalue;
	}

	public void setUrlvalue(String urlvalue) {
		this.urlvalue = urlvalue;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public String getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

}
