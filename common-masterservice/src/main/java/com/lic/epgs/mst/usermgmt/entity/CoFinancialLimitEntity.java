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
@Table(name = "CO_FINANCIAL_LIMIT ")
public class CoFinancialLimitEntity implements Serializable {

	private static final long serialVersionUID = 2264263195138765828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COFINLIMIT_ID")
	private long cofinlimitId;
	
	

	@Column(name = "FUNCTION_NAME")
	private String functionName;

	@Column(name = "DEPARTMENT")
	private String department;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "OFFICENAME")
	private String officeName;

	@Column(name = "HODFLAG")
	private String hodFlag;
	
	@Column(name = "INCHARGEFLAG")
	private String inchargeFlag;
	
	@Column(name = "FINANCIAL_LIMIT")
	private String financialLimit;
	
	
	@Column(name = "SANCTIONEDBUDGETLIMIT")
	private String sanctionedBudgetLimit;
	
	


	@Column(name = "IS_ACTIVE")
	private String isActive;

	
	@Column(name = "IS_DELETED")
	private String isDeleted;
	
	@Column(name = "ACTION")
	private String action;
  
	@Transient
	private String loggedInUserName;
	

	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	@Column(name = "MODIFIEDON")
	private Date modifiedOn;

	
	@Column(name = "CREATEDBY")
	private String createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;
	
	@Column(name = "WORKFLOW_STATUS")
	private Long workFlowStatus;
	
	@Column(name = "STATUS")
	private String status;
	
	
	@Column(name = "APPROVED_BY")
	private String approvedBy;
	
	
	@Column(name = "REMARKS")
	private String remarks;
	
	@Transient
	private String isEdited;
	
	@Transient 
	private String isDelete;

	@Column(name = "FUNCTIONALITY")
	private String functionality;
	
	@Column(name = "PRODUCT")
	private String product;
	
	@Column(name = "GROUP_VARIANT")
	private String groupVariant;

	public long getCofinlimitId() {
		return cofinlimitId;
	}


	public void setCofinlimitId(long cofinlimitId) {
		this.cofinlimitId = cofinlimitId;
	}


	public String getFunctionName() {
		return functionName;
	}


	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
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


	public String getHodFlag() {
		return hodFlag;
	}


	public void setHodFlag(String hodFlag) {
		this.hodFlag = hodFlag;
	}


	public String getInchargeFlag() {
		return inchargeFlag;
	}


	public void setInchargeFlag(String inchargeFlag) {
		this.inchargeFlag = inchargeFlag;
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


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public String getLoggedInUserName() {
		return loggedInUserName;
	}


	public void setLoggedInUserName(String loggedInUserName) {
		this.loggedInUserName = loggedInUserName;
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


	public Long getWorkFlowStatus() {
		return workFlowStatus;
	}


	public void setWorkFlowStatus(Long workFlowStatus) {
		this.workFlowStatus = workFlowStatus;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getApprovedBy() {
		return approvedBy;
	}


	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getIsEdited() {
		return isEdited;
	}


	public void setIsEdited(String isEdited) {
		this.isEdited = isEdited;
	}


	public String getIsDelete() {
		return isDelete;
	}


	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}


	public String getFunctionality() {
		return functionality;
	}


	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}


	public String getProduct() {
		return product;
	}


	public void setProduct(String product) {
		this.product = product;
	}


	public String getGroupVariant() {
		return groupVariant;
	}


	public void setGroupVariant(String groupVariant) {
		this.groupVariant = groupVariant;
	}


}
