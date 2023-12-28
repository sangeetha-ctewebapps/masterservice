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

@Entity
@Table(name = "MASTER_MARITAL_STATUS")
public class MaritalStatus implements Serializable {

	private static final long serialVersionUID = 9148345844284213681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MARITAL_ID")
	private long id;

	@Column(name = "MARITAL_CODE")
	private String maritalCode;

	@Column(name = "DESCRIPTION")
	private String maritalDesc;

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

	public String getMaritalCode() {
		return maritalCode;
	}

	public void setMaritalCode(String maritalCode) {
		this.maritalCode = maritalCode;
	}

	public String getMaritalDesc() {
		return maritalDesc;
	}

	public void setMaritalDesc(String maritalDesc) {
		this.maritalDesc = maritalDesc;
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

	@Override
	public String toString() {
		return "MaritalStatus [id=" + id + ", maritalCode=" + maritalCode + ", maritalDesc=" + maritalDesc
				+ ", createdOn=" + createdOn + ", createdBy=" + createdBy + "]";
	}

}
