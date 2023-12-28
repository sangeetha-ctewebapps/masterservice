package com.lic.epgs.mst.usermgmt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MASTER_USERS")
public class MasterUsersEntity {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="master_User_generator")
	@SequenceGenerator(name="master_User_generator", sequenceName ="MASTER_USER_SEQ",allocationSize=1)
	@Column(name = "MASTER_USER_ID")
	private Long masterUserId;

	@Column(name = "USERNAME")
	private String userName;
  
	@Column(name = "FULL_NAME")
	private String fullName;
	
	public String getFullName() {
		return fullName;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	//@NotNull
	@Column(name = "SRNUMBER")
	private String srNumber;

	//@NotNull
	@Column(name = "EMAILID")
	private String email;

	//@NotNull
	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "CADRE")
	private String cadre;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "LOCATION_TYPE")
	private  String locationType ;

	@Column(name = "LOCATION")
	private  String location ;

	@Column(name = "CLASS")
	private String className;

	@Column(name = "MODIFIEDBY")
	private String modifiedBy;

	@Column(name = "MODIFIEDON")
	private Date modifiedOn;

	//@NotNull
	@Column(name = "CREATEDBY")
	private String createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "DATEOFBIRTH")
	private Date dateOfBirth;

	@Column(name = "SEX")
	private String sex;


	@Column(name = "DATEOFAPPOINTMENT")
	private Date dateOfAppointment;

	@Column(name = "MARITAL_STATUS")
	private String maritalStatus;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "LOCATION_CODE")
	private String locationCode;

	@Column(name = "EMAIL")
	private String emailId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "IS_DELETED")
	private String isDeleted;

	@Column(name= "CATEGORY")
	private String category;
  
 	 @Column(name= "ISADMIN")
	private String isAdmin;
 	 
 	 /**
 	 * 
 	 */
 	@Column(name = "DEACTIVATEDDATE")
 	 private Date deActivatedDate;
 	
 	@Column(name = "DEACTIVATEDDAYS")
 	private String deactivatedDays;
 	
 	@Column(name = "USER_EXPIRY_ON")
 	private Date userexpiryon;

	public Date getUserexpiryon() {
		return userexpiryon;
	}



	public void setUserexpiryon(Date userexpiryon) {
		this.userexpiryon = userexpiryon;
	}



	public Date getDeActivatedDate() {
		return deActivatedDate;
	}



	public void setDeActivatedDate(Date deActivatedDate) {
		this.deActivatedDate = deActivatedDate;
	}
	
	@Column(name = "SRNUMBER_MAIN")
	private String srNumber2;
	
	@Column(name = "IS_LOGGEDIN")
 	private String isLoggedIn;


	public String getSrNumber2() {
		return srNumber2;
	}



	public String getIsLoggedIn() {
		return isLoggedIn;
	}



	public void setIsLoggedIn(String isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}



	public void setSrNumber2(String srNumber2) {
		this.srNumber2 = srNumber2;
	}
    
	@Transient
	private String loggedInUserLoacationType;
	
	@Transient
	private String loggedInUsername;

	@Transient
 	 private String typeOfUser;
	
	@Transient
	 private String remarks;
	
	@Column(name = "COFINANCIAL_DESIGNATION")
 	private String cofinancialDesignation;

	public MasterUsersEntity() {
		super();
		// TODO Auto-generated constructor stub
		this.srNumber = "admin"; 
		this.mobile = "9876543210";
		this.createdBy = "Admin";
		this.email = "admin@techmahindra.com";
	}



	public MasterUsersEntity(Long masterUserId, String userName, String srNumber, String email, String mobile,
			String cadre, String designation, String locationType, String location, String className, String modifiedBy,
			Date modifiedOn, String createdBy, Date createdOn, Date dateOfBirth, String sex, Date dateOfAppointment,
			String maritalStatus, String status, String ipAddress, String locationCode, String emailId,
			String firstName, String middleName, String lastName, String isActive, String isDeleted, String category) {
		super();
		this.masterUserId = masterUserId;
		this.userName = userName;
		this.srNumber = srNumber;
		this.email = email;
		this.mobile = mobile;
		this.cadre = cadre;
		this.designation = designation;
		this.locationType = locationType;
		this.location = location;
		this.className = className;
		this.modifiedBy = modifiedBy;
		this.modifiedOn = modifiedOn;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
		this.dateOfAppointment = dateOfAppointment;
		this.maritalStatus = maritalStatus;
		this.status = status;
		this.ipAddress = ipAddress;
		this.locationCode = locationCode;
		this.emailId = emailId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.category = category;
	}


	
public MasterUsersEntity(Long masterUserId, String userName, String fullName, @NotNull String srNumber,
			@NotNull String email, @NotNull String mobile, String cadre, String designation, String locationType,
			String location, String className, String modifiedBy, Date modifiedOn, @NotNull String createdBy,
			Date createdOn, Date dateOfBirth, String sex, Date dateOfAppointment, String maritalStatus, String status,
			String ipAddress, String locationCode, String emailId, String firstName, String middleName, String lastName,
			String isActive, String isDeleted, String category, String isAdmin) {
		super();
		this.masterUserId = masterUserId;
		this.userName = userName;
		this.fullName = fullName;
		this.srNumber = srNumber;
		this.email = email;
		this.mobile = mobile;
		this.cadre = cadre;
		this.designation = designation;
		this.locationType = locationType;
		this.location = location;
		this.className = className;
		this.modifiedBy = modifiedBy;
		this.modifiedOn = modifiedOn;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
		this.dateOfAppointment = dateOfAppointment;
		this.maritalStatus = maritalStatus;
		this.status = status;
		this.ipAddress = ipAddress;
		this.locationCode = locationCode;
		this.emailId = emailId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.category = category;
		this.isAdmin = isAdmin;
	}




public String getTypeOfUser() {
	return typeOfUser;
}



public void setTypeOfUser(String typeOfUser) {
	this.typeOfUser = typeOfUser;
}



public String getIsAdmin() {
		return isAdmin;
	}



	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}



public Long getMasterUserId() {
		return masterUserId;
	}


	public void setMasterUserId(Long masterUserId) {
		this.masterUserId = masterUserId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getSrNumber() {
		return srNumber;
	}


	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getCadre() {
		return cadre;
	}


	public void setCadre(String cadre) {
		this.cadre = cadre;
	}


	public String getRemarks() {
		return remarks;
	}



	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public String getLocationType() {
		return locationType;
	}


	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
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


	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public Date getDateOfAppointment() {
		return dateOfAppointment;
	}


	public void setDateOfAppointment(Date dateOfAppointment) {
		this.dateOfAppointment = dateOfAppointment;
	}


	public String getMaritalStatus() {
		return maritalStatus;
	}


	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public String getLocationCode() {
		return locationCode;
	}


	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}


	public String getEmailId() {
		return emailId;
	}


	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getMiddleName() {
		return middleName;
	}


	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
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


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}



	public String getDeactivatedDays() {
		return deactivatedDays;
	}



	public void setDeactivatedDays(String deactivatedDays) {
		this.deactivatedDays = deactivatedDays;
	}



	public String getLoggedInUserLoacationType() {
		return loggedInUserLoacationType;
	}



	public void setLoggedInUserLoacationType(String loggedInUserLoacationType) {
		this.loggedInUserLoacationType = loggedInUserLoacationType;
	}



	public String getLoggedInUsername() {
		return loggedInUsername;
	}



	public void setLoggedInUsername(String loggedInUsername) {
		this.loggedInUsername = loggedInUsername;
	}



	public String getCofinancialDesignation() {
		return cofinancialDesignation;
	}



	public void setCofinancialDesignation(String cofinancialDesignation) {
		this.cofinancialDesignation = cofinancialDesignation;
	}



	



	



	



}
