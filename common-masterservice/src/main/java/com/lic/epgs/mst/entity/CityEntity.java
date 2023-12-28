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
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "MASTER_CITY")
public class CityEntity implements Serializable {
	
	private static final long serialVersionUID = 2064263195138765828L;
	
	@Id
	@Column(name = "CITY_ID")
	private long cityId;
	@Column(name = "DISTRICT_ID")
	private long districtId;
	@Column(name = "CITY_CODE")
	private String cityCode;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "IS_ACTIVE")
	private char isActive;
	@Column(name = "IS_DELETED")
	private char isDeleted;
	@Column(name = "CREATED_ON")
	private Date createdOn;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	
	
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(long districtId) {
		this.districtId = districtId;
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
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	

	
}
