package com.lic.epgs.mst.usermgmt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_PORTAL_USERS")
public class PortalMasterEntity implements Serializable{
	
	private static final long serialVersionUID = 2264263195138765828L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="portal_master_id_seq_Generator")
	@SequenceGenerator(name="portal_master_id_seq_Generator", sequenceName ="PORTAL_USER_BULK_ID_SEQ", allocationSize=1)
	@Column(name = "PORTAL_USER_ID")
	private Long portalUserId;
	
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "DISTRICT")
	private String district;
	
	@Column(name = "CITY")
	private String city;

	@Column(name = "OFFICE_CODE")
	private String officeCode;
	
	@Column(name = "OFFICE_NAME")
	private String officeName;
	
	@Column(name = "IS_ACTIVE")
	private String isActive;

	 @Column(name = "IS_DELETED")
	private String isDeleted;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "REFRESH_TOKEN")
	private String refreshToken;
	
	@Column(name = "MPH_NAME")
	private String mphName;
	
	@Column(name ="IS_MPH_ADMIN")
	private String isMphAdmin;
	
	@Column(name = "LOG_OUT")
	private String logOut;
	
	@Column(name = "FULLNAME")
	private String fullName;
	
	@Column(name = "ASSIGN_ROLE_FLAG")
	private String assignRoleFlag;
	
	@Column(name = "IS_NEW_USER")
	private String isNewUser;
	
	@Column(name = "USERID")
	private String userId;
	
	@Column(name = "USERS_SEQUENCE")
	private long userSequence;
	
	@Column(name = "ZONE")
	private String zone;
	
	@Column(name = "UNIT")
	private String unit;
	
	@Column(name = "ROLE_KEY")
	private String roleKey;
	
	@Column(name = "MOBILE")
	private Long mobile;
	
	@Column(name = "POLICY_NUMBER")
	private String policyNumber;
	
	@Column(name = "APPROVED_BY")
	private String approvedBy;
	
	@Column(name = "APPROVED_ON")
	private Date approvedOn;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "WORKFLOW_STATUS")
	private Long workflowStatus;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	@Column(name = "ACTION")
	private String action;

	public Long getPortalUserId() {
		return portalUserId;
	}

	public void setPortalUserId(Long portalUserId) {
		this.portalUserId = portalUserId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
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

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getMphName() {
		return mphName;
	}

	public void setMphName(String mphName) {
		this.mphName = mphName;
	}

	public String getIsMphAdmin() {
		return isMphAdmin;
	}

	public void setIsMphAdmin(String isMphAdmin) {
		this.isMphAdmin = isMphAdmin;
	}

	public String getLogOut() {
		return logOut;
	}

	public void setLogOut(String logOut) {
		this.logOut = logOut;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAssignRoleFlag() {
		return assignRoleFlag;
	}

	public void setAssignRoleFlag(String assignRoleFlag) {
		this.assignRoleFlag = assignRoleFlag;
	}

	public String getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(String isNewUser) {
		this.isNewUser = isNewUser;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getUserSequence() {
		return userSequence;
	}

	public void setUserSequence(long userSequence) {
		this.userSequence = userSequence;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
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

	public Long getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(Long workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	

}
