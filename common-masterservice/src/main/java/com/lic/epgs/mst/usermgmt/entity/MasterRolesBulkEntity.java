package com.lic.epgs.mst.usermgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_ROLES_BULK")
public class MasterRolesBulkEntity  
{
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="master_Bulk_Upload_Generator")
	@SequenceGenerator(name="master_Bulk_Upload_Generator", sequenceName ="MASTER_ROLES_BULK_ID_SEQ",allocationSize=1)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "ROLE_KEY") 
	private String roleKey;
	
	@Column(name = "ROLE_NAME")
	private String roleName;
	
	@Column(name = "CREATED_BY")
	private String createBy;
	
	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;
	
	@Column(name = "ROLE_ID")
	private String roleId;
	
	@Column(name = "CLIENT_ROLE")
	private String clientRole;
	
	@Column(name = "COMPOSITE")
	private String composite;
	
	@Column(name = "RHSSO_REALM")
	private String rhssoRealm;
	
	@Column(name = "CLIENT_ID")
	private String clientId;
	
	@Column(name = "MAKERPRESENT")
	private String makerPresent;
	
	@Column(name = "CHECKERPRESENT")
	private String checkerPresent;
	
	@Column(name = "APPROVERPRESENT")
	private String approverPresent;
	
	@Column(name = "ROLE_DISPLAY_NAME")
	private String roleDisplayName;

	public String getRoleDisplayName() {
		return roleDisplayName;
	}

	public void setRoleDisplayName(String roleDisplayName) {
		this.roleDisplayName = roleDisplayName;
	}

	public String getMakerPresent() {
		return makerPresent;
	}

	public void setMakerPresent(String makerPresent) {
		this.makerPresent = makerPresent;
	}

	public String getCheckerPresent() {
		return checkerPresent;
	}

	public void setCheckerPresent(String checkerPresent) {
		this.checkerPresent = checkerPresent;
	}

	public String getApproverPresent() {
		return approverPresent;
	}

	public void setApproverPresent(String approverPresent) {
		this.approverPresent = approverPresent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getClientRole() {
		return clientRole;
	}

	public void setClientRole(String clientRole) {
		this.clientRole = clientRole;
	}

	public String getComposite() {
		return composite;
	}

	public void setComposite(String composite) {
		this.composite = composite;
	}

	public String getRhssoRealm() {
		return rhssoRealm;
	}

	public void setRhssoRealm(String rhssoRealm) {
		this.rhssoRealm = rhssoRealm;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
