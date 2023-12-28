package com.lic.epgs.mst.usermgmt.entity;

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

public class CoFinancialLimitNameEntity {
	
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


    @Column(name = "WORKFLOW_STATUS")
    private Long workflowStatus;

    @Column(name = "STATUS")
    private String status;

 

    @Column(name = "IS_ACTIVE")
    private String isActive;

 

    
    @Column(name = "IS_DELETED")
    private String isDeleted;

 

    @Column(name = "MODIFIEDBY")
    private String modifiedBy;

 

    @Column(name = "MODIFIEDON")
    private Date modifiedOn;

 

    
    @Column(name = "CREATEDBY")
    private String createdBy;

 

    @Column(name = "CREATEDON")
    private Date createdOn;

    @Transient
    private String loggedInUserName;

    @Transient
    private String searchType;
    
    @Column(name = "FUNCTIONALITY")
    private String functionality;

    public String getSearchType() {
		return searchType;
	}



	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}





	@Column(name = "APPROVED_BY")
    private String approvedBy;


	@Column(name = "ACTION")
    private String action;
	
	@Column(name = "REMARKS")
	private String remarks;

 

    public CoFinancialLimitNameEntity() {
        super();
        // TODO Auto-generated constructor stub
    }

 

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

 

    public Long getWorkflowStatus() {
        return workflowStatus;
    }

 

    public void setWorkflowStatus(Long workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

 

    public String getStatus() {
        return status;
    }

 

    public void setStatus(String status) {
        this.status = status;
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

 

    public String getApprovedBy() {
        return approvedBy;
    }

 

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }



	public String getLoggedInUserName() {
		return loggedInUserName;
	}



	public void setLoggedInUserName(String loggedInUserName) {
		this.loggedInUserName = loggedInUserName;
	}



	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}



	public String getRemarks() {
		return remarks;
	}



	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	public String getFunctionality() {
		return functionality;
	}



	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}

 

    
    
}
