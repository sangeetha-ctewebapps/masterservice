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
@Table(name = "MASTER_ANNUITY_PURCHASED")
public class AnnuityPurchasedBy implements Serializable {

	private static final long serialVersionUID = 9140345844284213681L;

	@Id          
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ANNUITY_ID")
	private long id;

	@Column(name = "ANNUITY_CODE")
	private String annuityCode;

	@Column(name = "DESCRIPTION")
	private String annuityDesc;

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

	public String getAnnuityCode() {
		return annuityCode;
	}

	public void setAnnuityCode(String annuityCode) {
		this.annuityCode = annuityCode;
	}

	public String getAnnuityDesc() {
		return annuityDesc;
	}

	public void setAnnuityDesc(String annuityDesc) {
		this.annuityDesc = annuityDesc;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AnnuityPurchasedBy [id=" + id + ", annuityCode=" + annuityCode + ", annuityDesc=" + annuityDesc
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}

}
