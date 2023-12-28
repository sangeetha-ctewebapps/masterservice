package com.lic.epgs.mst.usermgmt.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_APPLICAITON_MODULE")
public class MasterApplicationModule implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MODULEID")
	private long moduleId;

	@Column(name = "MODULENAME")
	private String appModule;

	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "CREATEDBY")
	private String createdBy;
	
	@Column(name = "MODIFIEDON")
	private Date modifiedOn;
	
	@Column(name = "IS_LOB")
	private String isLOB;
	
	
	public String getIsLOB() {
		return isLOB;
	}

	public void setIsLOB(String isLOB) {
		this.isLOB = isLOB;
	}

	public long getModuleId() {
		return moduleId;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

	public String getAppModule() {
		return appModule;
	}

	public void setAppModule(String appModule) {
		this.appModule = appModule;
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
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
