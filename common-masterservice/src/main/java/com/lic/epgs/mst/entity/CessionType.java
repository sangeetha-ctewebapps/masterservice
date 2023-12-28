package com.lic.epgs.mst.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;

//public class CessionType {
//
//}

@Entity
@Table(name = "MASTER_CESSION_TYPE")
public class CessionType implements Serializable {

	private static final long serialVersionUID = 9148801199284213681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CESSION_ID")
	private long id;

	@Column(name = "CESSION_CODE")
	private String cessionCode;

	@Column(name = "DESCRIPTION")
	private String cessionDesc;

	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCessionCode() {
		return cessionCode;
	}

	public void setCessionCode(String cessionCode) {
		this.cessionCode = cessionCode;
	}

	public String getCessionDesc() {
		return cessionDesc;
	}

	public void setCessionDesc(String cessionDesc) {
		this.cessionDesc = cessionDesc;
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

	@Override
	public String toString() {
		return "CessionType [id=" + id + ", cessionCode=" + cessionCode + ", cessionDesc=" + cessionDesc
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}


 
}
