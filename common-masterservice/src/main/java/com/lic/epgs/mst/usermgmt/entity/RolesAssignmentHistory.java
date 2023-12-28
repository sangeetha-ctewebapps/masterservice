
package com.lic.epgs.mst.usermgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_PORTAL_ROLES")
public class RolesAssignmentHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PORTAL_ROLES_ID")
	private Long portalRolesId;
	
	@Column(name = "MPH_ID")
	private Long mphId;

	@Column(name = "MPH_NAME")
	private String mphName;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;
	
	@Column(name = "APPROVED_BY")
	private String approvedBy;
	
	
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "WORKFLOW_STATUS")
	private Long workFlowStatus;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	@Column(name = "APPROVED_ON")
	private Date approvedOn;
	
	@Column(name = "ROLENAME")
	private String roleName;

	public Long getPortalRolesId() {
		return portalRolesId;
	}

	public void setPortalRolesId(Long portalRolesId) {
		this.portalRolesId = portalRolesId;
	}

	

	public Long getMphId() {
		return mphId;
	}

	public void setMphId(Long mphId) {
		this.mphId = mphId;
	}

	public String getMphName() {
		return mphName;
	}

	public void setMphName(String mphName) {
		this.mphName = mphName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getWorkFlowStatus() {
		return workFlowStatus;
	}

	public void setWorkFlowStatus(Long workFlowStatus) {
		this.workFlowStatus = workFlowStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	
}