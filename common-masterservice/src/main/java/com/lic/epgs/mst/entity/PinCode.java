package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MASTER_PIN_CODE")
public class PinCode implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@SequenceGenerator(name = "SEQ", sequenceName = "MASTER_PIN_CODE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
	@GenericGenerator(name = "id", strategy = "GenerationType.SEQUENCE")

	@Column(name = "PIN_ID")
	private long id;

	@Column(name = "PIN_CODE")
	private String pinCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "IS_ACTIVE")
	private char isActive;

	@Column(name = "IS_DELETED")
	private char isDeleted ;

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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CITY_ID")
	@JsonIgnore
	private CityMaster city;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
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

	public CityMaster getCity() {
		return city;
	}

	public void setCity(CityMaster city) {
		this.city = city;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "PinCode [id=" + id + ", pinCode=" + pinCode + ", description=" + description + ", isActive=" + isActive
				+ ", isDeleted=" + isDeleted + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", modifiedBy="
				+ modifiedBy + ", modifiedOn=" + modifiedOn + ", city=" + city + "]";
	}

}
