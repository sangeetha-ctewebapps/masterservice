
package com.lic.epgs.mst.usermgmt.repository; 

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MasterUsersHistoryDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;

@Repository
public interface PortalMasterRepository extends JpaRepository<PortalMasterEntity, Long> {

	@Query(value = "select PORTAL_USER_ID,USERNAME,MOBILE,EMAIL,STATE,DISTRICT,CITY,OFFICE_NAME,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,REFRESH_TOKEN,MPH_NAME,IS_MPH_ADMIN,LOG_OUT,ASSIGN_ROLE_FLAG,FULLNAME,OFFICE_CODE,IS_NEW_USER,UNIT,ZONE,USERID,USERS_SEQUENCE,ROLE_KEY,POLICY_NUMBER,APPROVED_BY,APPROVED_ON,STATUS,WORKFLOW_STATUS,REMARKS,ACTION from master_portal_users where lower(USERNAME)= lower(:username)", nativeQuery = true)
	public PortalMasterEntity getMasterUserIdByUserName(@Param("username") String username);
 
	@Query(value = "select * from master_portal_users where PORTAL_USER_ID= :portalid", nativeQuery = true)
	public PortalMasterEntity getMasterUserIdByPortalId(@Param("portalid") Long portalid);
	
	@Modifying
	@Query(value ="update master_portal_users set log_out = 'N', refresh_token=:refreshToken, mph_name=:mphName where lower(username) = lower(:username)", nativeQuery = true)
	public void updateportalusersbyuserName( @Param("username") String username,@Param("refreshToken") String refreshToken,@Param("mphName") String mphName);
	
	
	@Query(value = "select PORTAL_USER_ID,USERNAME,MOBILE,EMAIL,STATE,DISTRICT,CITY,OFFICE_NAME,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,REFRESH_TOKEN,MPH_NAME,IS_MPH_ADMIN,LOG_OUT,ASSIGN_ROLE_FLAG,FULLNAME,OFFICE_CODE,IS_NEW_USER,UNIT,ZONE,USERID,USERS_SEQUENCE,ROLE_KEY,POLICY_NUMBER,APPROVED_BY,APPROVED_ON,STATUS,WORKFLOW_STATUS,REMARKS,ACTION from master_portal_users where PORTAL_USER_ID =:portalUserId", nativeQuery = true)
	public PortalMasterEntity getPortalMasterDetails(@Param("portalUserId") Long portalUserId);
	
	@Query(value = "select count(1) from master_portal_users where lower(mph_name) = lower(:mphName)", nativeQuery = true)     
	      public  List<String> getMphCountUsersByName(@Param("mphName") String mphName);
	
	@Query(value = "select count(1) from master_portal_users where email =:email and mobile=:mobile and username = :username", nativeQuery = true)
	public int getvalidateUser(@Param("mobile") String mobile,@Param("email") String email,@Param("username") String username);
	
	@Query(value = "select count(1) from master_sms_details msd where msd.user_name= :username  and msd.otp = :smsOtp order by created_on desc", nativeQuery = true)
	public int getvalidateOtp(@Param("smsOtp") Long smsOtp,@Param("username") String username);
	
	@Query(value = "select PORTAL_USER_ID,USERNAME,MOBILE,EMAIL,STATE,DISTRICT,CITY,OFFICE_NAME,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,REFRESH_TOKEN,MPH_NAME,IS_MPH_ADMIN,LOG_OUT,ASSIGN_ROLE_FLAG,FULLNAME,OFFICE_CODE,IS_NEW_USER,UNIT,ZONE,USERID,USERS_SEQUENCE,ROLE_KEY,POLICY_NUMBER,APPROVED_BY,APPROVED_ON,STATUS,WORKFLOW_STATUS,REMARKS,ACTION from master_portal_users where email =:email or mobile=:mobile or username = :username", nativeQuery = true)
	public PortalMasterEntity loginwithemailMobile(@Param("mobile") String mobile,@Param("email") String email,@Param("username") String username);
	
	@Modifying	
	@Query(value = "UPDATE MASTER_PORTAL_USERS SET IS_ACTIVE ='N', IS_DELETED ='Y' WHERE PORTAL_USER_ID= :portalUserId", nativeQuery = true)
    public void findAndDeleteById(@Param("portalUserId") Long portalUserId);
	
	@Modifying
	@Query(value ="update master_portal_users set log_out='Y' where lower(username) = lower(:username)", nativeQuery = true)
	public void updateUserDetailAfterLogout( @Param("username") String username);
	
	@Query(value = "select PORTAL_USER_ID,USERNAME,MOBILE,EMAIL,STATE,DISTRICT,CITY,OFFICE_NAME,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,REFRESH_TOKEN,MPH_NAME,IS_MPH_ADMIN,LOG_OUT,ASSIGN_ROLE_FLAG,FULLNAME,OFFICE_CODE,IS_NEW_USER,UNIT,ZONE,USERID,USERS_SEQUENCE,ROLE_KEY,POLICY_NUMBER,APPROVED_BY,APPROVED_ON,STATUS,WORKFLOW_STATUS,REMARKS,ACTION from master_portal_users where is_mph_admin = 'Y' and is_active='Y' and lower(mph_name) = lower(:mphName)", nativeQuery = true)     
     public  List<PortalMasterEntity> getAllMPHAdminByMPHName(@Param("mphName") String mphName);
	
	@Query(value = "select PORTAL_USER_ID,USERNAME,MOBILE,EMAIL,STATE,DISTRICT,CITY,OFFICE_NAME,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,REFRESH_TOKEN,MPH_NAME,IS_MPH_ADMIN,LOG_OUT,ASSIGN_ROLE_FLAG,FULLNAME,OFFICE_CODE,IS_NEW_USER,UNIT,ZONE,USERID,USERS_SEQUENCE,ROLE_KEY,POLICY_NUMBER,APPROVED_BY,APPROVED_ON,STATUS,WORKFLOW_STATUS,REMARKS,ACTION from master_portal_users where IS_DELETED='Y' and lower(mph_name) = lower(:mphName) order by MODIFIED_ON desc", nativeQuery = true)     
    public  List<PortalMasterEntity> getAllMphUsersByMphKey(@Param("mphName") String mphName);
	
	@Modifying
	@Query(value ="update master_portal_users set ASSIGN_ROLE_FLAG='Y', ROLE_KEY =:roleKey where  lower(USERNAME) = lower(:userName)", nativeQuery = true)
	public void updateAssignRolesByUsername(@Param("userName") String username, @Param("roleKey") String roleKey);
	
	@Modifying
	@Query(value ="update master_portal_users set LOG_OUT='Y' where  PORTAL_USER_ID = lower(:portalUserId)", nativeQuery = true)
	public void updateLogOutFlagBasedOnCroneJob( @Param("portalUserId") Long portalUserId); 
	
	
	@Query(value = "select count(1) from master_portal_users where lower(username) = lower(:username)", nativeQuery = true)
	public int getvalidateUser(@Param("username") String username);
	
	@Query(value = "select count(1) from master_portal_users where lower(email) = lower(:email)", nativeQuery = true)
	public int getValidateEmail(@Param("email") String email);
	
	
	@Query(value = "select count(1) from master_portal_users where lower(mobile) = lower(:mobile)", nativeQuery = true)
	public int getValidateMobile(@Param("mobile") String mobile);
	
	@Query(value = "select otp from master_sms_details msd where msd.user_name=:username order by CREATED_ON desc fetch first 1 rows only", nativeQuery = true)
	public String getMobileOTP(@Param("username") String username);  
	
	@Query(value = "select otp from master_email_details med where med.user_name=:username order by id desc fetch first 1 rows only", nativeQuery = true)
	public String getEmailOTP(@Param("username") String username);

	@Query(value = "select MPH_KEY from USERMGMT_INTERNET_MPH where lower(MPH_NAME)= lower(:mphName)", nativeQuery = true)
	public String getMPHKeyFromMPHName(@Param("mphName") String mphName);
	
	@Modifying
	@Query(value ="update master_portal_users set IS_ACTIVE='Y', IS_DELETED='N' where  lower(userName) = lower(:userName)", nativeQuery = true)
	public void enableUserByuserName( @Param("userName") String username);
	
	@Modifying
	@Transactional
	@Query(value ="update master_portal_users set IS_ACTIVE='N', IS_DELETED='Y' where  lower(userName) = lower(:userName)", nativeQuery = true)
	public void disableUserByuserName( @Param("userName") String username);

	@Query(value = "select username from master_portal_users where lower(username)= lower(:inputParam) or lower(mobile) = lower(:inputParam) or lower(email) = lower(:inputParam)", nativeQuery = true)
	public String getUsernameFromInputParam(@Param("inputParam") String inputParam);
	
	@Query(value = "select count(*) from master_portal_users where IS_MPH_ADMIN = 'Y' and IS_ACTIVE = 'Y' and IS_DELETED = 'N' and lower(mph_name) = lower(:mphName)", nativeQuery = true)
	public String getMPHAdminCount(@Param("mphName") String mphName);
	
	@Query(value = "select IS_NEW_USER from master_portal_users where lower(username) = lower(:username)", nativeQuery = true)
	public String checkIfUserIsNewUser(@Param("username") String username);
	
	@Modifying
	@Query(value ="update master_portal_users set IS_NEW_USER='N' where lower(userName) = lower(:userName)", nativeQuery = true)
	public void updateNewUserFlagAfterFirstLogin( @Param("userName") String username);
	
	@Modifying
	@Transactional
	@Query(value ="update master_portal_users set IS_MPH_ADMIN='N' where  lower(userName) = lower(:userName)", nativeQuery = true)
	public void disableMPHAdminUserByuserName( @Param("userName") String username);
  
    @Query(value = "select * from master_portal_users where IS_ACTIVE='N' and IS_DELETED='Y' and IS_MPH_ADMIN = 'N'  and lower(mph_name) = lower(:mphName) and lower(USERNAME) Not in(:loggedInUsername) and ((lower(username) like lower('%'||:username||'%') or :username is null) and (lower(email) like lower('%'||:email||'%') or :email is null) and (mobile = (:mobile) or :mobile is null))", nativeQuery = true)     
    public  List<PortalMasterEntity> getAllDisabledMPHOrdinaryUsers(@Param("mphName") String mphName,@Param("username") String username,@Param("email") String email,@Param("mobile") String mobile,@Param("loggedInUsername") String loggedInUsername);
	
	@Query(value = "select * from master_portal_users where IS_ACTIVE='N' and IS_DELETED='Y' and IS_MPH_ADMIN = 'Y'  and lower(mph_name) = lower(:mphName) and lower(USERNAME) Not in(:loggedInUsername) and ((lower(username) like lower('%'||:username||'%') or :username is null) and (lower(email) like lower('%'||:email||'%') or :email is null) and (mobile = (:mobile) or :mobile is null))", nativeQuery = true)     
    public  List<PortalMasterEntity> getAllDisabledMPHAdminUsers(@Param("mphName") String mphName,@Param("username") String username,@Param("email") String email,@Param("mobile") String mobile,@Param("loggedInUsername") String loggedInUsername);
	
//	@Query(value = "select * from master_portal_users where IS_ACTIVE='Y' and IS_DELETED = 'N' and IS_MPH_ADMIN = 'N'  and lower(mph_name) = lower(:mphName) and lower(USERNAME) Not in(:loggedInUsername) and ((lower(username) like lower('%'||:username||'%') or :username is null) and (lower(email) like lower('%'||:email||'%') or :email is null) and (mobile = ('%'||:mobile||'%')or :mobile is null)) order by MODIFIED_ON desc", nativeQuery = true)     
//    public  List<PortalMasterEntity> getAllActiveMPHOrdinaryUsers(@Param("mphName") String mphName,@Param("username") String username,@Param("email") String email,@Param("mobile") String mobile,@Param("loggedInUsername") String loggedInUsername);
	
	
	@Query(value = "select * from master_portal_users where IS_ACTIVE='Y' and IS_DELETED = 'N' and IS_MPH_ADMIN = 'N'  and lower(mph_name) = lower(:mphName) and lower(USERNAME) Not in(:loggedInUsername) and ((lower(username) like lower('%'||:username||'%') or :username is null) and (lower(email) like lower('%'||:email||'%') or :email is null) and (mobile = (:mobile) or :mobile is null)) order by MODIFIED_ON desc", nativeQuery = true)     
    public  List<PortalMasterEntity> getAllActiveMPHOrdinaryUsers(@Param("mphName") String mphName,@Param("username") String username,@Param("email") String email,@Param("mobile") String mobile,@Param("loggedInUsername") String loggedInUsername);
	
	@Query(value = "select * from master_portal_users where IS_ACTIVE='Y' and IS_DELETED = 'N' and IS_MPH_ADMIN = 'Y'  and lower(mph_name) = lower(:mphName) and lower(USERNAME) Not in(:loggedInUsername) and ((lower(username) like lower('%'||:username||'%') or :username is null) and (lower(email) like lower('%'||:email||'%') or :email is null) and (mobile = (:mobile) or :mobile is null)) order by MODIFIED_ON desc", nativeQuery = true)     
    public  List<PortalMasterEntity> getAllActiveMPHAdminUsers(@Param("mphName") String mphName,@Param("username") String username,@Param("email") String email,@Param("mobile") String mobile,@Param("loggedInUsername") String loggedInUsername);
	
	
	@Query(value = "select max(USERS_SEQUENCE) from master_portal_users ", nativeQuery = true)
	public int getMaxUserSequence();
	
	@Query(value = "select username from master_portal_users where lower(username) = lower(:inputParam) or mobile = (:inputParam) or lower(email) = lower(:inputParam)", nativeQuery = true)
	public String getUsernameFromMobileOrEmail(@Param("inputParam") String inputParam);
	
	@Query(value = "select USERNAME_SEQ.NEXTVAL from dual", nativeQuery = true)
	public int getUsernameNextSequenceValue();
	
	@Query(value = "select username from master_portal_users where mobile = (:mobileno) and lower(email) = lower(:emailid)", nativeQuery = true)
	public String getUsernameFromMobileAndEmail(@Param("mobileno") String mobileno,@Param("emailid") String emailid);
	
	@Modifying
	@Query(value ="update master_portal_users set OFFICE_NAME=:officeName, OFFICE_CODE=:officeCode, FULLNAME=:fullName where lower(EMAIL) = lower(:email) and MOBILE=:mobile", nativeQuery = true)
	public void updateportalusersbyEmailAndMobileFromCRON( @Param("officeName") String officeName, @Param("officeCode") String officeCode, @Param("fullName") String fullName, @Param("email") String email, @Param("mobile") String mobile);
	
	//@Query(value ="select PORTAL_USER_BULK_ID_SEQ.nextval from dual", nativeQuery = true)
	//public long getNextPortalUserIdValFromSeq(); 
	
	@Query(value = "select PORTAL_USER_ID,USERNAME,MOBILE,EMAIL,STATE,DISTRICT,CITY,OFFICE_NAME,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,REFRESH_TOKEN,MPH_NAME,IS_MPH_ADMIN,LOG_OUT,ASSIGN_ROLE_FLAG,FULLNAME,OFFICE_CODE,IS_NEW_USER,UNIT,ZONE,USERID,USERS_SEQUENCE,ROLE_KEY,POLICY_NUMBER,APPROVED_BY,APPROVED_ON,STATUS,WORKFLOW_STATUS,REMARKS,ACTION from master_portal_users where is_active = 'Y' and is_deleted = 'N' and lower(mph_name) = lower(:mphName) and lower(USERNAME) Not in(:loggedInUsername) and ((lower(ROLE_KEY) = :roleKey) OR (UPPER(ROLE_KEY) LIKE UPPER('%'||:roleKey||'%') )) and((UPPER(USERNAME) LIKE UPPER('%'||:username||'%') ) OR (:username =null)) and ((UPPER(EMAIL) LIKE UPPER('%'||:emailid||'%') ) OR (:emailid =null)) and ((UPPER(MOBILE) LIKE UPPER('%'||:mobileno||'%') ) OR (:mobileno ='')) order by  created_on desc", nativeQuery = true)
	public List<PortalMasterEntity> getAllUserDetailsUsingSearch(@Param("mphName") String mphName, @Param("mobileno") String mobileno,@Param("emailid") String emailid,@Param("username") String username,@Param("roleKey") String roleKey,@Param("loggedInUsername") String loggedInUsername);
	
	@Query(value = "select PORTAL_USER_ID,USERNAME,MOBILE,EMAIL,STATE,DISTRICT,CITY,OFFICE_NAME,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,REFRESH_TOKEN,MPH_NAME,IS_MPH_ADMIN,LOG_OUT,ASSIGN_ROLE_FLAG,FULLNAME,OFFICE_CODE,IS_NEW_USER,UNIT,ZONE,USERID,USERS_SEQUENCE,ROLE_KEY,POLICY_NUMBER,APPROVED_BY,APPROVED_ON,STATUS,WORKFLOW_STATUS,REMARKS,ACTION from master_portal_users where is_active = 'Y' and is_deleted = 'N' and ASSIGN_ROLE_FLAG = 'N' and lower(mph_name) = lower(:mphName) order by  created_on desc", nativeQuery = true)
	public List<PortalMasterEntity> getAllUnassignedRolesUserDetails(@Param("mphName") String mphName);
	
	@Query(value = "select PORTAL_USER_ID,USERNAME,MOBILE,EMAIL,STATE,DISTRICT,CITY,OFFICE_NAME,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,REFRESH_TOKEN,MPH_NAME,IS_MPH_ADMIN,LOG_OUT,ASSIGN_ROLE_FLAG,FULLNAME,OFFICE_CODE,IS_NEW_USER,UNIT,ZONE,USERID,USERS_SEQUENCE,ROLE_KEY,POLICY_NUMBER,APPROVED_BY,APPROVED_ON,STATUS,WORKFLOW_STATUS,REMARKS,ACTION from master_portal_users where is_active = 'Y' and is_deleted = 'N' and lower(mph_name) = lower(:mphName) and lower(USERNAME) Not in(:loggedInUsername) and UPPER(USERNAME) LIKE UPPER('%'||:username||'%') order by  created_on desc", nativeQuery = true)
	public List<PortalMasterEntity> getAllAssignedRolesUserDetails(@Param("mphName") String mphName, @Param("username") String username,@Param("loggedInUsername") String loggedInUsername);
	
	
	@Query(value = "select PORTAL_USER_ID,USERNAME,MOBILE,EMAIL,STATE,DISTRICT,CITY,OFFICE_NAME,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,REFRESH_TOKEN,MPH_NAME,IS_MPH_ADMIN,LOG_OUT,ASSIGN_ROLE_FLAG,FULLNAME,OFFICE_CODE,IS_NEW_USER,UNIT,ZONE,USERID,USERS_SEQUENCE,ROLE_KEY,POLICY_NUMBER,APPROVED_BY,APPROVED_ON,STATUS,WORKFLOW_STATUS,REMARKS,ACTION from master_portal_users where is_active = 'Y' and is_deleted = 'N' and ASSIGN_ROLE_FLAG = 'Y' and lower(mph_name) = lower(:mphName)  and UPPER(USERNAME) LIKE UPPER('%'||:username||'%') order by  created_on desc", nativeQuery = true)
	public List<PortalMasterEntity> getAllRolesUserDetails(@Param("mphName") String mphName, @Param("username") String username);
	
	@Query(value = "select * from  master_portal_users where lower(username) = lower(:username)",nativeQuery = true)
	public PortalMasterEntity getAllRolesDetails(@Param("username") String username);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update master_portal_users  set role_key =:roleToAssign where ((lower(ROLE_KEY) = :roleToDelete) OR (UPPER(ROLE_KEY) LIKE UPPER('%'||:roleToDelete||'%') )) and lower(username) = lower(:username)",nativeQuery = true)
	public int disableRolesByUsername(@Param("roleToDelete") String roleToDelete,@Param("username") String username, @Param("roleToAssign") String roleToAssign);
	
	@Modifying
	@Query(value ="update master_portal_users set ASSIGN_ROLE_FLAG=:assignroleflag, ROLE_KEY=:roles where lower(username) = lower(:username)", nativeQuery = true)
	public void updateAssignRoleFlagAndRoles(@Param("assignroleflag") String assignroleflag, @Param("username") String username, @Param("roles") String roles);
	
	@Modifying
	@Transactional
	@Query(value = "delete from master_portal_users where lower(email) = lower(:email)", nativeQuery = true)
	public void deleteRecordUsingEmail(@Param("email") String email);
	
	@Modifying
	@Query(value ="update master_portal_users set IS_MPH_ADMIN=:mphAdminFlag where lower(username) = lower(:username)", nativeQuery = true)
	public void updateMphAdminFlag(@Param("mphAdminFlag") String mphAdminFlag, @Param("username") String username);
	
	//All Below queries is for superadmin feature
	
	@Query(value = "select count(1) from  master_portal_users where lower(MPH_NAME) = lower(:mphName) and IS_MPH_ADMIN = 'N'",nativeQuery = true)
	public long getAllMPHOrdinaryUsersUnderThatMphName(@Param("mphName") String mphName);
			
	@Query(value = "select count(1) from  master_portal_users where lower(MPH_NAME) = lower(:mphName) and IS_MPH_ADMIN = 'Y'",nativeQuery = true)
	public long getAllMPHAdminUsersUnderThatMphName(@Param("mphName") String mphName);
				
				
	@Query(value = "select count(1) from  master_portal_users where lower(MPH_NAME) = lower(:mphName) and IS_ACTIVE = 'Y' and IS_DELETED = 'N'",nativeQuery = true)
	public long getAllUsersUnderThatMphName(@Param("mphName") String mphName);
				
	@Query(value = "select PORTAL_USER_ID,USERNAME,MOBILE,EMAIL,STATE,DISTRICT,CITY,OFFICE_NAME,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,REFRESH_TOKEN,MPH_NAME,IS_MPH_ADMIN,LOG_OUT,ASSIGN_ROLE_FLAG,FULLNAME,OFFICE_CODE,IS_NEW_USER,UNIT,ZONE,USERID,USERS_SEQUENCE,ROLE_KEY,POLICY_NUMBER,APPROVED_BY,APPROVED_ON,STATUS,WORKFLOW_STATUS,REMARKS,ACTION from  master_portal_users where lower(MPH_NAME) = lower(:mphName) and IS_ACTIVE = 'Y' and IS_DELETED = 'N'",nativeQuery = true)
	public List<PortalMasterEntity> getAllUsersInThatMphName(@Param("mphName") String mphName);
	
	@Query(value = "select PORTAL_USER_ID,USERNAME,MOBILE,EMAIL,STATE,DISTRICT,CITY,OFFICE_NAME,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,REFRESH_TOKEN,MPH_NAME,IS_MPH_ADMIN,LOG_OUT,ASSIGN_ROLE_FLAG,FULLNAME,OFFICE_CODE,IS_NEW_USER,UNIT,ZONE,USERID,USERS_SEQUENCE,ROLE_KEY,POLICY_NUMBER,APPROVED_BY,APPROVED_ON,STATUS,WORKFLOW_STATUS,REMARKS,ACTION from  master_portal_users where lower(MPH_NAME) = lower(:mphName) and IS_MPH_ADMIN =:is_MphAdmin and IS_ACTIVE=:is_Active and IS_DELETED=:is_Deleted",nativeQuery = true)
	public List<PortalMasterEntity> getAllAdminOrdinaryUsersInThatMphName(@Param("mphName") String mphName, @Param("is_MphAdmin") String is_MphAdmin, @Param("is_Active") String is_Active, @Param("is_Deleted") String is_Deleted);
	
	
	@Query(value = "select * from master_portal_users where created_by != :loggedInusername  and status='sentforapproval'  order by created_on desc", nativeQuery = true)
	public List<PortalMasterEntity> getUserCreationByLoggedInUser(@Param("loggedInusername") String loggedInusername);
	
	
	@Query(value = "select * from master_portal_users where created_by != :loggedInusername  and status='sentforapproval' and action= :action order by created_on desc", nativeQuery = true)
	public List<PortalMasterEntity> getUsersAssignmentByUsernameAndStatus(@Param("loggedInusername") String loggedInusername,@Param("action") String action);
	
	@Query(value = "select * from master_portal_users where created_by != :loggedInusername  and status='sentforapproval' and action= :action and trunc(created_on) between TO_DATE(:startDate,'YYYY-MM-DD') and TO_DATE(:endDate , 'YYYY-MM-DD')  order by created_on desc", nativeQuery = true)
	public List<PortalMasterEntity> getUsersAssignmentByUsernameAndStatusAndDate(@Param("loggedInusername") String loggedInusername,@Param("action") String action,@Param("startDate") String startDate,@Param("endDate") String endDate);

	@Query(value = "select * from master_portal_users where created_by != :loggedInusername  and status='sentforapproval' and action= :action and trunc(created_on) between TO_DATE(:startDate,'YYYY-MM-DD') and TO_DATE(:endDate , 'YYYY-MM-DD')  order by created_on desc", nativeQuery = true)
	public List<PortalMasterEntity> getUsersAssignmentByUsernameAndStatusAndStartDate(@Param("loggedInusername") String loggedInusername,@Param("action") String action,@Param("startDate") String startDate,@Param("endDate") String endDate);

	@Query(value = "select * from master_portal_users where created_by != :loggedInusername  and status='sentforapproval' and action= :action and trunc(created_on) <= TO_DATE(:endDate , 'YYYY-MM-DD')  order by created_on desc", nativeQuery = true)
	public List<PortalMasterEntity> getUsersAssignmentByUsernameAndStatusAndEndDate(@Param("loggedInusername") String loggedInusername,@Param("action") String action,@Param("endDate") String endDate);

	@Modifying
	@Query(value ="update master_portal_users set WORKFLOW_STATUS=:workflowStatus,STATUS=:status,REMARKS=:remarks,MODIFIED_BY=:modifiedBy,MODIFIED_ON=:modifiedOn where lower(username) = lower(:username)", nativeQuery = true)
	public int updateStatusOfCreatedUser(@Param("workflowStatus") Long workflowStatus, @Param("status") String status, @Param("username") String username, @Param("remarks") String remarks, @Param("modifiedBy") String modifiedBy, @Param("modifiedOn") Date modifiedOn);
	
	@Query(value = "select * from master_portal_users where UPPER(STATUS)=UPPER('Approved') and WORKFLOW_STATUS = 2 and IS_MPH_ADMIN = :is_MphAdmin and IS_ACTIVE=:is_Active and IS_DELETED=:is_Deleted and UPPER(UNIT)=UPPER(:loggedInUserUnitCode)",nativeQuery = true)
	public List<PortalMasterEntity> getAllAdminOrdinaryUsers(@Param("is_MphAdmin") String is_MphAdmin, @Param("is_Active") String is_Active, @Param("is_Deleted") String is_Deleted, @Param("loggedInUserUnitCode") String loggedInUserUnitCode);
	
	@Query(value = "select * from master_portal_users where IS_ACTIVE='Y' and IS_DELETED='N' and UPPER(STATUS) = UPPER(:status) and action= :action and trunc(created_on) between TO_DATE(:fromDate,'YYYY-MM-DD') and TO_DATE(:toDate , 'YYYY-MM-DD') and UPPER(UNIT)=UPPER(:loggedInUserUnitCode) order by created_on desc", nativeQuery = true) 
	public List<PortalMasterEntity> getAllUsersPendingForApproval(@Param("status") String status,@Param("action") String action,@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("loggedInUserUnitCode") String loggedInUserUnitCode);
	
	@Query(value = "select * from master_portal_users where IS_ACTIVE='Y' and IS_DELETED='N' and UPPER(STATUS) = UPPER(:status) and action= :action and UPPER(UNIT)=UPPER(:loggedInUserUnitCode) order by created_on desc", nativeQuery = true) 
	public List<PortalMasterEntity> getAllUsersPendingForApproval(@Param("status") String status,@Param("action") String action, @Param("loggedInUserUnitCode") String loggedInUserUnitCode);
	
	@Query(value = "select * from master_portal_users where IS_ACTIVE='Y' and IS_DELETED='N' and UPPER(STATUS) = UPPER(:status) and trunc(created_on) between TO_DATE(:fromDate,'YYYY-MM-DD') and TO_DATE(:toDate , 'YYYY-MM-DD') and UPPER(UNIT)=UPPER(:loggedInUserUnitCode) order by created_on desc", nativeQuery = true) 
	public List<PortalMasterEntity> getAllUsersPendingForApproval(@Param("status") String status,@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("loggedInUserUnitCode") String loggedInUserUnitCode);
	
	@Query(value = "select * from master_portal_users where IS_ACTIVE='Y' and IS_DELETED='N' and UPPER(STATUS) = UPPER(:status) and trunc(created_on) >= TO_DATE(:fromDate,'YYYY-MM-DD') and UPPER(UNIT)=UPPER(:loggedInUserUnitCode) order by created_on desc", nativeQuery = true) 
	public List<PortalMasterEntity> getAllUsersPendingForApprovalAfterFromDate(@Param("status") String status,@Param("fromDate") String fromDate, @Param("loggedInUserUnitCode") String loggedInUserUnitCode);
	
	@Query(value = "select * from master_portal_users where IS_ACTIVE='Y' and IS_DELETED='N' and UPPER(STATUS) = UPPER(:status) and trunc(created_on) <= TO_DATE(:toDate,'YYYY-MM-DD') and UPPER(UNIT)=UPPER(:loggedInUserUnitCode) order by created_on desc", nativeQuery = true) 
	public List<PortalMasterEntity> getAllUsersPendingForApprovalBeforeToDate(@Param("status") String status,@Param("toDate") String toDate, @Param("loggedInUserUnitCode") String loggedInUserUnitCode);
	
	@Query(value = "select * from master_portal_users where IS_ACTIVE='Y' and IS_DELETED='N' and UPPER(STATUS) = UPPER(:status) and UPPER(UNIT)=UPPER(:loggedInUserUnitCode) order by created_on desc", nativeQuery = true) 
	public List<PortalMasterEntity> getAllUsersPendingForApproval(@Param("status") String status, @Param("loggedInUserUnitCode") String loggedInUserUnitCode);
	
	@Query(value = "select * from master_portal_users where UPPER(STATUS) = UPPER(:status) and UPPER(UNIT)=UPPER(:loggedInUserUnitCode) order by created_on desc", nativeQuery = true) 
	public List<PortalMasterEntity> getAllUsersBasedOnStatus(@Param("status") String status, @Param("loggedInUserUnitCode") String loggedInUserUnitCode);
}


