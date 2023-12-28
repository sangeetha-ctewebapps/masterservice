package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_TYPE_SWITCH")
public class TypeOfSwitchMaster implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TYPE_SWITCH_ID")
	private long typeSwitchId;

	@Column(name = "TYPE_SWITCH_CODE")
	private String typeSwitchCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getTypeSwitchId() {
		return typeSwitchId;
	}

	public void setTypeSwitchId(long typeSwitchId) {
		this.typeSwitchId = typeSwitchId;
	}

	public String getTypeSwitchCode() {
		return typeSwitchCode;
	}

	public void setTypeSwitchCode(String typeSwitchCode) {
		this.typeSwitchCode = typeSwitchCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
