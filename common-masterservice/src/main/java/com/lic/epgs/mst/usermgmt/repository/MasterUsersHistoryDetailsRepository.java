package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.usermgmt.entity.MasterUsersHistoryDetailsEntity;

public interface MasterUsersHistoryDetailsRepository extends JpaRepository<MasterUsersHistoryDetailsEntity, Long>{
	
	
	@Query(value = "select * from MASTER_USERS_HISTORY order by CREATED_ON desc", nativeQuery = true) 
	List<MasterUsersHistoryDetailsEntity> getAllHistoryDetails();
	
	@Query(value = "select * from MASTER_USERS_HISTORY where ADMINUSER_LOCATION_TYPE in (:locationCriteria) order by CREATED_ON desc", nativeQuery = true) 
	List<MasterUsersHistoryDetailsEntity> getAllHistoryDetailsBasedOnLoggedInUserLocationType(@Param("locationCriteria") List<String> locationCriteria );
	
	@Query(value = "select * from MASTER_USERS_HISTORY where ADMINUSER_LOCATION_TYPE in (:locationCriteria) and (:adminSrNo is null or ADMINUSER_SRNO = :adminSrNo) order by CREATED_ON desc", nativeQuery = true) 
	List<MasterUsersHistoryDetailsEntity> getAllHistoryDetailsBasedOnLoggedInUserLocationTypeAndAdminSrNo(@Param("locationCriteria") List<String> locationCriteria,@Param("adminSrNo") String adminSrNo);
	
	@Query(value = "select * from MASTER_USERS_HISTORY where ADMINUSER_LOCATION_TYPE in (:locationCriteria) and (:adminSrNo is null or ADMINUSER_SRNO = :adminSrNo) and trunc(CREATED_ON) between TO_DATE(:fromDate,'YYYY-MM-DD') and TO_DATE(:toDate , 'YYYY-MM-DD')  order by CREATED_ON desc", nativeQuery = true) 
	List<MasterUsersHistoryDetailsEntity> getAllHistoryDetailsBasedOnLoggedInUserLocationTypeAndAdminSrNoAndDate(@Param("locationCriteria") List<String> locationCriteria,@Param("adminSrNo") String adminSrNo,@Param("fromDate") String fromDate,@Param("toDate") String toDate );
	
	@Query(value = "select * from MASTER_USERS_HISTORY where (:locationCriteria is null or ADMINUSER_LOCATION_TYPE = :locationCriteria)  and (:locationCode is null or ADMINUSER_LOCATION_CODE = :locationCode)  order by CREATED_ON desc", nativeQuery = true) 
	List<MasterUsersHistoryDetailsEntity> getAllHistoryDetailsBasedOnLocationCode(@Param("locationCriteria") List<String> locationCriteria,@Param("locationCode") String locationCode);
	
	
	//@Query(value = "select * from MASTER_USERS_HISTORY where ADMINUSER_LOCATION_TYPE in :locationCriteria and trunc(CREATED_ON) between TO_DATE(:fromDate,'YYYY-MM-DD') and TO_DATE(:toDate , 'YYYY-MM-DD')  order by CREATED_ON desc", nativeQuery = true) 
	//List<MasterUsersHistoryDetailsEntity> getAllHistoryDetailsBasedOnLoggedInUserLocationTypeAndDate(@Param("locationCriteria") String[] locationCriteria,@Param("fromDate") String fromDate,@Param("toDate") String toDate  );

	

}
