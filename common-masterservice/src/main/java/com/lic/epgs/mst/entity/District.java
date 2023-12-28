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
@Table(name = "MASTER_DISTRICT")
public class District implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@SequenceGenerator(name = "SEQ", sequenceName = "MASTER_DISTRICT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
	@GenericGenerator(name = "id", strategy = "GenerationType.SEQUENCE")

	@Column(name = "DISTRICT_ID")
	private long id;

	@Column(name = "DISTRICT_CODE")
	private String districtCode;

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

	@CreatedBy
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@UpdateTimestamp
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "district")
	private Set<CityMaster> city = new HashSet<>();

	public District() {

	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "state_id")
	@JsonIgnore
	private StateMaster states;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
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

	public Set<CityMaster> getCity() {
		return city;
	}

	public void setCity(Set<CityMaster> city) {
		this.city = city;
	}

	public StateMaster getStates() {
		return states;
	}

	public void setStates(StateMaster states) {
		this.states = states;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "District [id=" + id + ", districtCode=" + districtCode + ", description=" + description + ", isActive="
				+ isActive + ", isDeleted=" + isDeleted + ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", modifiedBy=" + modifiedBy + ", modifiedOn=" + modifiedOn + ", city=" + city + ", states=" + states
				+ "]";
	}

	
}
