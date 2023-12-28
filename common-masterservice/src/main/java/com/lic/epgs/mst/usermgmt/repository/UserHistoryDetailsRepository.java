package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MasterRoleScreenEntity;
import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.UOAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.UserDetailHistoryEntity;

@Repository
public interface UserHistoryDetailsRepository extends JpaRepository<UserDetailHistoryEntity, Long>{

	@Query(value = "SELECT * FROM ( SELECT * FROM USERS_DETAIL_HISTORY where username = :username ORDER BY created_on desc ) WHERE rownum <= 1 ORDER BY CREATED_ON", nativeQuery = true)
	public UserDetailHistoryEntity getLatestrecordBasedOnUserName(@Param("username")String username);
	
	@Query(value = "SELECT * FROM MASTER_ROLE_SCREEN WHERE ROLE_ID = :roleId and IS_ACTIVE='Y'", nativeQuery = true)
	public MasterRoleScreenEntity findByRoleId(@Param("roleId")Long roleId);
	
	
	
//	@Query(value = "select * from USERS_DETAIL_HISTORY  order by created_on desc", nativeQuery = true) 
//	public List<UserDetailHistoryEntity> getAllUsersHistory();
//
//	
//	@Query(value = "select * from USERS_DETAIL_HISTORY where  UPPER(USERNAME) = UPPER(:username) order by created_on desc", nativeQuery = true) 
//	public List<UserDetailHistoryEntity> getAllUserInfoBasedOnUsername(@Param("username") String username);
//	
////	@Query(value = "select * from USERS_DETAIL_HISTORY where  UPPER(USERNAME) = UPPER(:username) and action= :action order by created_on desc", nativeQuery = true) 
////	public List<UserDetailHistoryEntity> getAllUsersPendingForApproval(@Param("username") String username,@Param("action") String action);
////	
//	@Query(value = "select * from USERS_DETAIL_HISTORY where  trunc(created_on) between TO_DATE(:fromDate,'YYYY-MM-DD') and TO_DATE(:toDate , 'YYYY-MM-DD')  order by created_on desc", nativeQuery = true) 
//	public List<UserDetailHistoryEntity> getAllUserInfoBasedOnDate(@Param("fromDate") String fromDate,@Param("toDate") String toDate );
//	
//    @Query(value = "select * from USERS_DETAIL_HISTORY where UPPER(USERNAME) = UPPER(:username)  and trunc(created_on) between TO_DATE(:fromDate,'YYYY-MM-DD') and TO_DATE(:toDate , 'YYYY-MM-DD')  order by created_on desc", nativeQuery = true) 
//	public List<UserDetailHistoryEntity> getAllUserInfoBasedOnUsernameAndDate(@Param("username") String username,@Param("fromDate") String fromDate,@Param("toDate") String toDate );
//	
//	@Query(value = "select * from USERS_DETAIL_HISTORY where trunc(created_on) >= TO_DATE(:fromDate,'YYYY-MM-DD') order by created_on desc", nativeQuery = true) 
//	public List<UserDetailHistoryEntity> getAllUsersHistoryBasedOnFromDate(@Param("fromDate") String fromDate);
//	
//	@Query(value = "select * from USERS_DETAIL_HISTORY where  trunc(created_on) <= TO_DATE(:toDate,'YYYY-MM-DD') order by created_on desc", nativeQuery = true) 
//	public List<UserDetailHistoryEntity> getAllUsersHistoryBasedOnToDate(@Param("toDate") String toDate);
//	
	
	@Query(value = "select * from USERS_DETAIL_HISTORY where UPPER(UNITCODE)=UPPER(:loggedInUserUnitCode) order by USER_DETAIL_HISTORY_ID desc", nativeQuery = true) 
	public List<UserDetailHistoryEntity> getAllUsersHistory(@Param("loggedInUserUnitCode") String loggedInUserUnitCode);

	
	@Query(value = "select * from USERS_DETAIL_HISTORY where  UPPER(SRNUMBER_MAIN) = UPPER(:srnumber) and UPPER(UNITCODE)=UPPER(:loggedInUserUnitCode) order by USER_DETAIL_HISTORY_ID desc", nativeQuery = true) 
	public List<UserDetailHistoryEntity> getAllUserInfoBasedOnUsername(@Param("srnumber") String srnumber, @Param("loggedInUserUnitCode") String loggedInUserUnitCode);
	
//	@Query(value = "select * from USERS_DETAIL_HISTORY where  UPPER(USERNAME) = UPPER(:username) and action= :action order by created_on desc", nativeQuery = true) 
//	public List<UserDetailHistoryEntity> getAllUsersPendingForApproval(@Param("username") String username,@Param("action") String action);
//	
	@Query(value = "select * from USERS_DETAIL_HISTORY where  trunc(created_on) between TO_DATE(:fromDate,'YYYY-MM-DD') and TO_DATE(:toDate , 'YYYY-MM-DD') and UPPER(UNITCODE)=UPPER(:loggedInUserUnitCode) order by USER_DETAIL_HISTORY_ID desc", nativeQuery = true) 
	public List<UserDetailHistoryEntity> getAllUserInfoBasedOnDate(@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("loggedInUserUnitCode") String loggedInUserUnitCode);
	
    @Query(value = "select * from USERS_DETAIL_HISTORY where UPPER(SRNUMBER_MAIN) = UPPER(:srnumber)  and trunc(created_on) between TO_DATE(:fromDate,'YYYY-MM-DD') and TO_DATE(:toDate , 'YYYY-MM-DD') and UPPER(UNITCODE)=UPPER(:loggedInUserUnitCode) order by USER_DETAIL_HISTORY_ID desc", nativeQuery = true) 
	public List<UserDetailHistoryEntity> getAllUserInfoBasedOnUsernameAndDate(@Param("srnumber") String srnumber,@Param("fromDate") String fromDate,@Param("toDate") String toDate, @Param("loggedInUserUnitCode") String loggedInUserUnitCode);
	
	@Query(value = "select * from USERS_DETAIL_HISTORY where trunc(created_on) >= TO_DATE(:fromDate,'YYYY-MM-DD') and UPPER(UNITCODE)=UPPER(:loggedInUserUnitCode) order by USER_DETAIL_HISTORY_ID desc", nativeQuery = true) 
	public List<UserDetailHistoryEntity> getAllUsersHistoryBasedOnFromDate(@Param("fromDate") String fromDate, @Param("loggedInUserUnitCode") String loggedInUserUnitCode);
	
	@Query(value = "select * from USERS_DETAIL_HISTORY where  trunc(created_on) <= TO_DATE(:toDate,'YYYY-MM-DD') and UPPER(UNITCODE)=UPPER(:loggedInUserUnitCode) order by USER_DETAIL_HISTORY_ID desc", nativeQuery = true) 
	public List<UserDetailHistoryEntity> getAllUsersHistoryBasedOnToDate(@Param("toDate") String toDate, @Param("loggedInUserUnitCode") String loggedInUserUnitCode);

}
