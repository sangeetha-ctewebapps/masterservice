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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MASTER_STATE")
public class StateMaster implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;
	
	@Id
	@SequenceGenerator(name = "SEQ", sequenceName = "MASTER_STATE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
	@GenericGenerator(name = "stateId", strategy = "GenerationType.SEQUENCE")
	
	@Column(name = "STATE_ID")
	private Long stateId;

	@Column(name = "STATE_CODE")
	private String stateCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "IS_ACTIVE")
	private char isActive;

	@Column(name = "IS_DELETED")
	private char isDeleted = 'N';

	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@CreatedBy
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@CreationTimestamp
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@UpdateTimestamp
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COUNTRY_ID	")
	@JsonIgnore
	private CountryMaster country;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "states")
	private Set<District> district = new HashSet<>();

	public StateMaster() {

	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public CountryMaster getCountry() {
		return country;
	}

	public void setCountry(CountryMaster country) {
		this.country = country;
	}

	public Set<District> getDistrict() {
		return district;
	}

	public void setDistrict(Set<District> district) {
		this.district = district;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "StateMaster [stateId=" + stateId + ", stateCode=" + stateCode + ", description=" + description
				+ ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdBy=" + createdBy + ", modifiedBy="
				+ modifiedBy + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + ", country=" + country
				+ ", district=" + district + "]";
	}
	
	
}
