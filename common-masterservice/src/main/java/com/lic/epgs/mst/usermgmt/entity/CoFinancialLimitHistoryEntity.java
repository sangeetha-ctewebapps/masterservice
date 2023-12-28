package com.lic.epgs.mst.usermgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CO_FINANCIAL_LIMIT_HISTORY")
public class CoFinancialLimitHistoryEntity implements Serializable {

	private static final long serialVersionUID = 2264263195138765828L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COFINLIMITHIST_ID")
	private long cofinlimithistId;
	
	
	
	@Column(name = "FUNCTION_NAME")
	private String functionName;
	
	@Column(name = "DESIGNATION")
	private String designation;
	
	@Column(name = "DEPARTMENT")
	private String department;

	

	@Column(name = "OFFICENAME")
	private String officeName;
	
	@Column(name = "FINANCIAL_LIMIT")
	private String financialLimit;
	
	
	@Column(name = "SANCTIONEDBUDGETLIMIT")
	private String sanctionedBudgetLimit;
	
	@Column(name = "IS_ACTIVE")
	private String isActive;

	
	@Column(name = "IS_DELETED")
	private String isDeleted;
	
	@Column(name = "CREATEDBY")
	private String createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;
	
	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	@Column(name = "MODIFIEDON")
	private Date modifiedOn;
	
	@Column(name = "APPROVED_BY")
	private String approvedBy;
	
	@Column(name = "APPROVED_ON")
	private Date approvedOn;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "LOGGEDINUSER_SRNUMBER")
	private String loggedInUserSrNumber;
	
	@Column(name = "ACTION")
	private String action;
	
	public String getDepartment() {
		return department;
	}

	public String getLoggedInUserSrNumber() {
		return loggedInUserSrNumber;
	}

	public void setLoggedInUserSrNumber(String loggedInUserSrNumber) {
		this.loggedInUserSrNumber = loggedInUserSrNumber;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public long getCofinlimithistId() {
		return cofinlimithistId;
	}

	public void setCofinlimithistId(long cofinlimithistId) {
		this.cofinlimithistId = cofinlimithistId;
	}

	

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getFinancialLimit() {
		return financialLimit;
	}

	public void setFinancialLimit(String financialLimit) {
		this.financialLimit = financialLimit;
	}

	public String getSanctionedBudgetLimit() {
		return sanctionedBudgetLimit;
	}

	public void setSanctionedBudgetLimit(String sanctionedBudgetLimit) {
		this.sanctionedBudgetLimit = sanctionedBudgetLimit;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
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

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
