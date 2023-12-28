package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;

@Repository
public interface MasterUsersRepository extends JpaRepository <MasterUsersEntity,Long>{
	
	
	@Query(value ="SELECT * FROM MASTER_USERS WHERE LOCATION =:locationId and IS_ACTIVE='Y' and IS_DELETED='N' order by modifiedon DESC ", nativeQuery = true)
	public MasterUsersEntity getUserRoleByLocation(@Param("locationId") Long locationId);
	
	@Query(value ="select count(*) from master_users where LOCATION_CODE = :locationCode and isadmin = 'Y' and is_active = 'Y' and is_deleted = 'N' ", nativeQuery = true)
	public int getMaxAdminCount(@Param("locationCode") String locationCode);
	
	@Query(value = "select * from  master_users  where  lower(SRNUMBER)=lower(:srNumber) and IS_ACTIVE='Y' and IS_DELETED='N' ", nativeQuery = true)
	List<MasterUsersEntity> findBySrNumber(@Param("srNumber") String srNumber);
	
	@Query(value = "select * from  master_users  where  lower(SRNUMBER_MAIN)=lower(:SRNUMBER_MAIN) and IS_ACTIVE='Y' and IS_DELETED='N' ", nativeQuery = true)
	MasterUsersEntity findBySrNumberMain(@Param("SRNUMBER_MAIN") String SRNUMBER_MAIN);
	
	@Query(value ="SELECT * FROM MASTER_USERS WHERE EMAIL =:emailId and IS_ACTIVE='Y' and IS_DELETED='N'order by master_user_id desc", nativeQuery = true)	
	 public List<MasterUsersEntity> findByEmail(@Param("emailId") String emailId);
	
	@Query(value = "SELECT * FROM MASTER_USERS WHERE USERNAME=:userName AND IS_ACTIVE='N' AND IS_DELETED='Y' ", nativeQuery = true)
    public MasterUsersEntity findDisabledUserByUserName(@Param("userName") String userName);

		
	@Query(value ="SELECT * FROM MASTER_USERS WHERE lower(LOCATION) =lower(:location) and IS_ACTIVE='Y' and IS_DELETED='N' order by modifiedon DESC", nativeQuery = true)
	List<MasterUsersEntity> findByLocation(@Param("location") String location);	
	
	@Query(value = "select * from master_users where email = :emailId and is_active = 'Y' and is_deleted = 'N' order by modifiedon desc ", nativeQuery = true) 
	 public  MasterUsersEntity getAllLocationTypeByEmail(@Param("emailId") String emailId);
	
		
	@Query(value = "select * from master_users where lower(location_type) = lower(:locationType) and is_active = 'N' and is_deleted = 'Y' and isadmin ='N' order by modifiedon desc", nativeQuery = true) 
	 public  MasterUsersEntity getAllInActiveLocationType(@Param("locationType") String locationType);
	
	@Query(value = "select * from master_users where lower(location_type) = lower(:locationType) and is_active = 'Y' and is_deleted = 'N'  order by modifiedon desc", nativeQuery = true) 
	 public  List <MasterUsersEntity> getAllActiveLocationType(@Param("locationType") String locationType);
	
	@Query(value =" SELECT * FROM MASTER_USERS WHERE lower(LOCATION_CODE) =lower(:locationCode) and IS_ACTIVE='Y' and IS_DELETED='N' order by modifiedon DESC ", nativeQuery = true)
	 public List<MasterUsersEntity> findByLocationCode(@Param("locationCode") String locationCode);	
	
	@Query(value = "select MU.* from master_users MU, master_role MR, user_role_mapping URM "
			+ "where (MR.rolename = :roleName OR MR.role_type = :roleType) and MR.roleid = URM.roleid "
			+ "and URM.MASTER_USER_ID = MU.MASTER_USER_ID\r\n" 
			+ "and MU.IS_ACTIVE='Y' and MU.IS_DELETED='N'\n", nativeQuery = true)
	public List<MasterUsersEntity> getUserByRoleName(@Param("roleName") String roleName,@Param("roleType") String roleType);
	
	@Query(value = "SELECT * FROM (   \r\n" + 
			"								 SELECT  DISTINCT 'USER_MGMT' FROM MASTER_USERS MU, UO_ADMIN UO WHERE MU.MASTER_USER_ID = UO.MASTER_USER_ID     \r\n" + 
			"									 AND MU.EMAIL = :emailId AND UO.IS_DELETED='N' AND UO.IS_ACTIVE='Y' AND MU.IS_DELETED='N' AND MU.IS_ACTIVE='Y'\r\n" + 
			"									 UNION  \r\n" + 
			"									 SELECT  DISTINCT 'UO_ADMIN' FROM MASTER_USERS MU, UO_ADMIN UO WHERE MU.MASTER_USER_ID = UO.MASTER_USER_ID  \r\n" + 
			"									 AND MU.EMAIL = :emailId AND UO.IS_DELETED='N' AND UO.IS_ACTIVE='Y' AND MU.IS_DELETED='N' AND MU.IS_ACTIVE='Y'\r\n" + 
			"						            UNION \r\n" + 
			"									 SELECT  DISTINCT 'ZO_ADMIN' FROM MASTER_USERS MU, ZO_ADMIN ZO WHERE MU.MASTER_USER_ID = ZO.MASTER_USER_ID   \r\n" + 
			"									 AND MU.EMAIL = :emailId AND ZO.IS_DELETED='N' AND ZO.IS_ACTIVE='Y' AND MU.IS_DELETED='N' AND MU.IS_ACTIVE='Y'\r\n" + 
			"						              UNION  \r\n" + 
			"									 SELECT  DISTINCT 'CO_ADMIN' FROM MASTER_USERS MU, CO_ADMIN COO WHERE MU.MASTER_USER_ID = COO.MASTER_USER_ID    \r\n" + 
			"									 AND MU.EMAIL = :emailId  AND COO.IS_DELETED='N' AND COO.IS_ACTIVE='Y' AND MU.IS_DELETED='N' AND MU.IS_ACTIVE='Y'\r\n" + 
			"                                     UNION\r\n" + 
			"                                     SELECT  DISTINCT 'SO_ADMIN' FROM MASTER_USERS MU, SO_ADMIN SOO WHERE MU.MASTER_USER_ID = SOO.MASTER_USER_ID    \r\n" + 
			"									 AND MU.EMAIL = :emailId  AND SOO.IS_DELETED='N' AND SOO.IS_ACTIVE='Y' AND MU.IS_DELETED='N' AND MU.IS_ACTIVE='Y'\r\n" + 
			"            )", nativeQuery = true)     
	      public  List<String> checkAdminAccessByEmail(@Param("emailId") String emailId);
	
	@Modifying
	@Query(value ="update MASTER_USERS set LOCATION =:location, LOCATION_CODE=:locationCode WHERE MASTER_USER_ID =:masterUserId", nativeQuery = true)
	public void updateMstUserZO(@Param("masterUserId") Long masterUserId, @Param("location") String location, @Param("locationCode") String locationCode);
	
	@Modifying
	@Query(value ="update MASTER_USERS set LOCATION =:location, LOCATION_CODE=:locationCode, LOCATION_TYPE=:locationType WHERE MASTER_USER_ID =:masterUserId", nativeQuery = true)
	public void updateMstUserUO(@Param("masterUserId") Long masterUserId, @Param("location") String location, @Param("locationCode") String locationCode,  @Param("locationType") String locationType);
	
	@Query(value = "SELECT * FROM MASTER_USERS WHERE MASTER_USER_ID=:masterUserId ", nativeQuery = true)
	public  MasterUsersEntity getMasterUserDetail (@Param("masterUserId") Long masterUserId);
	
	@Query(value = "SELECT * FROM MASTER_USERS where lower(srnumber_main) = lower(:srNumber) and IS_ACTIVE='Y' and IS_DELETED='N' ", nativeQuery = true)
	public  MasterUsersEntity getAllMasterUserDetail (@Param("srNumber") String srNumber);
	
	@Query(value = "SELECT * FROM MASTER_USERS where lower(USERNAME) = lower(:loggedInUsername) and IS_ACTIVE='Y' and IS_DELETED='N' ", nativeQuery = true)
	public  MasterUsersEntity getAllMasterUserDetailByUserName (@Param("loggedInUsername") String loggedInUsername);
	
	
	
	@Query(value = "SELECT SRNUMBER_MAIN FROM MASTER_USERS where lower(USERNAME) = lower(:loggedInUsername) and IS_ACTIVE='Y' and IS_DELETED='N'", nativeQuery = true)
	public  String getAllMasterUserDetailByUserName1 (@Param("loggedInUsername") String loggedInUsername);
	
	@Modifying	
	@Query(value = "UPDATE MASTER_USERS SET IS_ACTIVE ='N' , IS_DELETED ='Y' WHERE MASTER_USER_ID= :masterUserId", nativeQuery = true) 
	Integer findDeletedById(@Param("masterUserId")Long masterUserId);
	
	
  
  /* @Modifying	
	@Query(value = "UPDATE MASTER_USERS SET IS_ACTIVE ='N',IS_DELETED='Y'  WHERE USERNAME= :username", nativeQuery = true) 
	public Integer findDisabledByUsername(@Param("username")String username);*/
   
   @Modifying	
	@Query(value = "UPDATE MASTER_USERS SET IS_ACTIVE ='N',IS_DELETED='Y',ISADMIN = :isAdmin WHERE USERNAME= :userName and LOCATION_TYPE = :locationType ", nativeQuery = true) 
	public int findDisabledByUsername(@Param("userName")String userName,@Param("locationType") String locationType,@Param("isAdmin") String isAdmin);
   @Transactional
   @Modifying	
	@Query(value = "UPDATE MASTER_USERS SET IS_ACTIVE ='N',IS_DELETED='Y' WHERE USERNAME= :userName  ", nativeQuery = true) 
	public int DisabledByUsername(@Param("userName")String userName);
  
  @Modifying	
	@Query(value = "UPDATE MASTER_USERS SET IS_ACTIVE ='Y',IS_DELETED='N' , ISADMIN = :isAdmin WHERE USERNAME= :userName and LOCATION_TYPE = :locationType ", nativeQuery = true) 
	public int findEnabledByUsername(@Param("userName")String userName,@Param("locationType") String locationType,@Param("isAdmin") String isAdmin);

	@Query(value = "SELECT COUNT(*) FROM MASTER_USERS WHERE lower(SRNUMBER)=lower(:srNumber)", nativeQuery = true)
	public Integer findDuplicate(@Param("srNumber") String string);

	@Query(value ="SELECT * FROM MASTER_USERS where IS_ACTIVE = 'Y' and IS_DELETED = 'N' order by createdon DESC", nativeQuery = true)
	public   List<MasterUsersEntity> findAllMasterUsersDetail();
	
	@Query(value = "SELECT * FROM MASTER_USERS WHERE USERNAME=:userName AND IS_ACTIVE='Y' AND IS_DELETED='N' ", nativeQuery = true)
    public MasterUsersEntity findUserByUserName(@Param("userName") String userName);
	
	@Query(value = "SELECT * FROM MASTER_USERS WHERE USERNAME=:userName AND IS_ACTIVE='Y' AND IS_DELETED='N' OR SRNUMBER_MAIN =:srNumber", nativeQuery = true)
    public MasterUsersEntity findUserByUserNameOrSrNumber(@Param("userName") String userName,@Param("srNumber") String srNumber);
	
	@Query(value = "select * from  master_users mu where mu.MASTER_USER_ID= mu.MASTER_USER_ID  and lower(mu.srNumber)=lower(:srNumber) and mu.IS_ACTIVE='Y' and mu.IS_DELETED='N'", nativeQuery = true)
		public  List<MasterUsersEntity> checkAdminroleBysrNumber(@Param("srNumber") String srNumber); 
	
	@Modifying
	 @Query(value = "UPDATE MASTER_USERS SET IS_LOGGEDIN = :loginFlag WHERE USERNAME= :userName ", nativeQuery = true)
	 public void updateLoggedInUserFlag(@Param("userName")String userName,@Param("loginFlag") String loginFlag);

	@Query(value = "select * from  master_users mu where mu.MASTER_USER_ID= mu.MASTER_USER_ID  and lower(mu.email)=lower(:email) and mu.IS_ACTIVE='Y' and mu.IS_DELETED='N'", nativeQuery = true)
	public  List<MasterUsersEntity> checkAdminroleByEmail(@Param("email") String email);
	
	@Modifying
    @Query(value ="update MASTER_USERS set LOCATION =:coAdminName, LOCATION_CODE=:locationCode WHERE MASTER_USER_ID =:masterUserId  ", nativeQuery = true)
    public void updateMstUserCO(@Param("masterUserId") Long masterUserId, @Param("coAdminName") String coAdminName,@Param("locationCode") String locationCode);

   
	@Query(value = "select * from  master_users mu where mu.MASTER_USER_ID= mu.MASTER_USER_ID  and lower(mu.srNumber)=lower(:srNumber) and mu.IS_ACTIVE='Y' and mu.IS_DELETED='N' ", nativeQuery = true)
	 public  MasterUsersEntity checkActiveStatusBySrNumber(@Param("srNumber") String srNumber);
	
	@Query(value ="SELECT * FROM MASTER_USERS WHERE USERNAME =:userName  order by modifiedon DESC ", nativeQuery = true)
	public MasterUsersEntity getUserByUserName(@Param("userName") String userName);
	
	@Query(value ="SELECT * FROM MASTER_USERS WHERE LOCATION_CODE =:locationCode and IS_ACTIVE='Y' and IS_DELETED='N' order by modifiedon DESC ", nativeQuery = true)
	public List<MasterUsersEntity> getUserDetailsByUnitCode(@Param("locationCode") String locationCode);
	
	@Query(value = "select * from MASTER_USERS where is_active ='Y' and lower(srNumber) = lower(:srNumber)", nativeQuery = true)
	public MasterUsersEntity checkUsersExistsinePGSBySRnumber(@Param("srNumber") String srNumber);
	
	@Query(value = "select * from MASTER_USERS where lower(srNumber) = lower(:srNumber) ", nativeQuery = true)
	public MasterUsersEntity checkUsersExistsinePGSBySRnumberwithOtherUnit(@Param("srNumber") String srNumber);
	
	@Query(value = "select * from MASTER_USERS where is_active ='N' and lower(srNumber) = lower(:srNumber) ", nativeQuery = true)
	public MasterUsersEntity checkUsersExistsinePGSBySRnumberNotActive(@Param("srNumber") String srNumber);
	
	@Query(value = "select * from MASTER_USERS where lower(srNumber) = lower(:srNumber)", nativeQuery = true)
	public MasterUsersEntity checkUsersExistsinePGS(@Param("srNumber") String srNumber);
	
	 @Query(value = "select * from master_users where (IS_ACTIVE='N' and IS_DELETED='Y' and ISADMIN = 'N')  and  location_type =:locationType and location =:location and ((UPPER(username) like UPPER ('%'||:userName||'%')or :userName is null) and (UPPER(srnumber_main) like UPPER('%'||:srNumber||'%')or :srNumber is null)) order by MODIFIEDON desc", nativeQuery = true)     
	    public  List<MasterUsersEntity> getAllDisabledOrdinaryUsers(@Param("locationType") String locationType,@Param("location") String location,@Param("userName") String userName,@Param("srNumber") String srNumber);
		
		@Query(value = "select * from master_users where (IS_ACTIVE='N' and IS_DELETED='Y' and ISADMIN = 'Y')  and  location_type =:locationType and location =:location and ((UPPER(username) like UPPER ('%'||:userName||'%')or :userName is null) and (UPPER(srnumber_main) like UPPER('%'||:srNumber||'%')or :srNumber is null)) order by MODIFIEDON desc", nativeQuery = true)     
	    public  List<MasterUsersEntity> getAllDisabledAdminUsers(@Param("locationType") String locationType,@Param("location") String location,@Param("userName") String userName,@Param("srNumber") String srNumber);
		
		@Query(value = "select * from master_users where (IS_ACTIVE='Y' and IS_DELETED = 'N' and ISADMIN = 'N') and  location_type =:locationType and location =:location and ((UPPER(username) like UPPER ('%'||:userName||'%')or :userName is null) and (UPPER(srnumber_main) like UPPER('%'||:srNumber||'%')or :srNumber is null)) order by MODIFIEDON desc", nativeQuery = true)     
	    public  List<MasterUsersEntity> getAllActiveOrdinaryUsers(@Param("locationType") String locationType,@Param("location") String location,@Param("userName") String userName,@Param("srNumber") String srNumber);
		
		@Query(value = "select * from master_users where (IS_ACTIVE='Y' and IS_DELETED = 'N' and ISADMIN = 'Y')  and  location_type =:locationType and location =:location and ((UPPER(username) like UPPER ('%'||:userName||'%')or :userName is null) and (UPPER(srnumber_main) like UPPER('%'||:srNumber||'%')or :srNumber is null)) order by MODIFIEDON desc", nativeQuery = true)     
	    public  List<MasterUsersEntity> getAllActiveAdminUsers(@Param("locationType") String locationType,@Param("location") String location,@Param("userName") String userName,@Param("srNumber") String srNumber);
	
		
		 @Query(value ="SELECT * FROM MASTER_USERS WHERE LOCATION_CODE =:locationCode and IS_ACTIVE='Y' and IS_DELETED='N'  order by modifiedon DESC", nativeQuery = true)
			List<MasterUsersEntity> findBySrNumberUserName(@Param("locationCode") String locationCode);	
		 
		 @Query(value = "select count(1) from master_users where lower(srnumber_main) = lower(:srNumber) and is_active =:isActiveFlag", nativeQuery = true)
			public int getvalidateSrNumber(@Param("srNumber") String srNumber,@Param("isActiveFlag") String isActiveFlag);
		 
		 @Query(value = "select count(1) from master_users where IS_ACTIVE='Y' and IS_DELETED = 'N' and lower(srnumber_main) = lower(:srNumber)", nativeQuery = true)
			public int checkActiveSrNumber(@Param("srNumber") String srNumber);
		 
		 @Query(value = "select mu.username,mu.LOCATION_TYPE,ms.STATE_CODE,ms.TYPE,ms.CODE from master_users mu ,\n"
		 		+ "master_unit muu , master_state ms where muu.UNIT_CODE=mu.LOCATION_CODE and\n"
		 		+ "  muu.STATE_NAME=ms.DESCRIPTION and mu.LOCATION_CODE=:unitCode and mu.username=:srNumber", nativeQuery = true)
			public List<Map<String, Object>> getUserDetailsBasedOnUnitCode(@Param("srNumber") String srNumber, @Param("unitCode") String unitCode);
		 
		 @Query(value = "SELECT IS_LOGGEDIN FROM MASTER_USERS where lower(SRNUMBER) = lower(:srNumber) ", nativeQuery = true)
		public String getLoginFlagStatus(@Param("srNumber") String srNumber);
		 
		 @Modifying
		    @Query(value ="update MASTER_USERS set isadmin = 'Y' WHERE MASTER_USER_ID =:masterUserId  ", nativeQuery = true)
		    public void updateisAdminFlag(@Param("masterUserId") Long masterUserId);
		 

		 @Query(value = "SELECT SRNUMBER_MAIN FROM MASTER_USERS where SRNUMBER_MAIN='444460'", nativeQuery = true)
		public List<String> getAllUsersFromDB();
		 
		 @Query(value = "select * from  master_users  where  lower(SRNUMBER)=lower(:SRNUMBER) and IS_ACTIVE='N' and IS_DELETED='Y' ", nativeQuery = true)
		public MasterUsersEntity findDisableUserUsingSrNumber(@Param("SRNUMBER") String SRNUMBER);
		 
		 @Query(value = "select * from  master_users  where  lower(USERNAME)=lower(:userName) and IS_ACTIVE='Y' and IS_DELETED='N' ", nativeQuery = true)
			public MasterUsersEntity findEnableUserUsingSrNumber(@Param("userName") String userName);
		 
		 @Modifying	
			@Query(value = "UPDATE MASTER_USERS SET CATEGORY=:category WHERE USERNAME= :userName ", nativeQuery = true) 
			public int updateCategoryByUsername(@Param("category")String category,@Param("userName") String userName);
}
