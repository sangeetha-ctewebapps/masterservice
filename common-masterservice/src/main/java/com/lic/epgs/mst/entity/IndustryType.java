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
@Table(name = "MASTER_INDUSTRY")
public class IndustryType implements Serializable {

	private static final long serialVersionUID = 9108345849284213681L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INDUSTRY_ID")
	private long id;

	@Column(name = "INDUSTRY_CODE")
	private String industryCode;

	@Column(name = "RISK_GROUP")
	private String riskGroup;
	
	@Column(name = "DESCRIPTION")
	private String description;

	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	

	public String getRiskGroup() {
		return riskGroup;
	}

	public void setRiskGroup(String riskGroup) {
		this.riskGroup = riskGroup;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "IndustryType [id=" + id + ", industryCode=" + industryCode + ", riskGroup=" + riskGroup
				+ ", description=" + description + ", createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}
	

}