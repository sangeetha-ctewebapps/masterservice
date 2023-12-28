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
@Table(name = "MASTER_PORTFOLIO_TYPE")
public class PortfolioTypeMaster implements Serializable {

	private static final long serialVersionUID = 2064263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PORTFOLIO_TYPE_ID")
	private long portfolioTypeId;

	@Column(name = "PORTFOLIO_TYPE_CODE")
	private String portfolioTypeCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public long getPortfolioTypeId() {
		return portfolioTypeId;
	}

	public void setPortfolioTypeId(long portfolioTypeId) {
		this.portfolioTypeId = portfolioTypeId;
	}

	public String getPortfolioTypeCode() {
		return portfolioTypeCode;
	}

	public void setPortfolioTypeCode(String portfolioTypeCode) {
		this.portfolioTypeCode = portfolioTypeCode;
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
