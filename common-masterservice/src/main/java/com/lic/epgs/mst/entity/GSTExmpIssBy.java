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
@Table(name = "MASTER_GST_EXEMPTION")
public class GSTExmpIssBy implements Serializable {

	private static final long serialVersionUID = 9168345849284213681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GST_ID")
	private long id;

	@Column(name = "GST_CODE")
	private String gstCode;

	@Column(name = "DESCRIPTION")
	private String gstDesc;

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

	public String getGstDesc() {
		return gstDesc;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setGstDesc(String gstDesc) {
		this.gstDesc = gstDesc;
	}

	public String getGstCode() {
		return gstCode;
	}

	public void setGstCode(String gstCode) {
		this.gstCode = gstCode;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "GSTExmpIssBy [id=" + id + ", gstDesc=" + gstDesc + ", gstCode=" + gstCode + ", createdOn=" + createdOn
				+ ", createdBy=" + createdBy + "]";
	}
}
