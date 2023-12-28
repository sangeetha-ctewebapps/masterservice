package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "MASTER_COUNTRY")
public class CountryMaster implements Serializable {

	private static final long serialVersionUID = 9188345849284213681L;

	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@SequenceGenerator(name = "SEQ", sequenceName = "MASTER_COUNTRY_SEQ", allocationSize = 1)
	@Column(name = "country_id")
	private Long countryId;

	@Column(name = "COUNTRY_CODE")
	private String countryCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "IS_ACTIVE")
	private char isActive;

	@Column(name = "IS_DELETED")
	private char isDeleted = 'N';

	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "ISD_CODE")
	private String isdCode;
	


	public CountryMaster() {

	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public char getIsActive() {
		return isActive;
	}

	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}

	public char getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(char isDeleted) {
		this.isDeleted = isDeleted;
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
	
	public String getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(String isdCode) {
		this.isdCode = isdCode;
	}

	
}
