package com.lic.epgs.mst.usermgmt.repository; 

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MphNameEntity;

@Repository
public interface MphNameRepository extends JpaRepository<MphNameEntity, Long> {

	@Query(value = "select MPH_ID,MPH_NAME,MPH_CODE,MPH_KEY,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,MPH_ADMIN_MAXCOUNT,FIRST_ADMIN_CREATED,PORTALS_ASSIGNED,POLICY_NUMBER from usermgmt_internet_mph where lower(MPH_KEY) =lower(:mphKey)", nativeQuery = true)
	public MphNameEntity getMPHName(@Param("mphKey") String mphKey);
	
	@Query(value = "select MPH_ID,MPH_NAME,MPH_CODE,MPH_KEY,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,MPH_ADMIN_MAXCOUNT,FIRST_ADMIN_CREATED,PORTALS_ASSIGNED,POLICY_NUMBER from USERMGMT_INTERNET_MPH order by MPH_ID desc", nativeQuery = true)
	List<MphNameEntity> findAllMphName();
	
	@Query(value = "select MPH_NAME from usermgmt_internet_mph where lower(MPH_NAME) =lower(:mphKey)", nativeQuery = true)
	public MphNameEntity getAllDisabledUsersByMPHName(@Param("mphKey") String mphKey);

	@Query(value = "select MPH_ADMIN_MAXCOUNT from usermgmt_internet_mph where lower(MPH_NAME) =lower(:mphName)", nativeQuery = true)
	public int getMaxMPHAdminCount(@Param("mphName") String mphName);
	
  	@Query(value = "select MPH_NAME from usermgmt_internet_mph where lower(MPH_NAME) =lower(:mphName)", nativeQuery = true)
	public MphNameEntity getMPHKeyBasedOnMPHName(@Param("mphName") String mphName);
  	
  	@Query(value="select MPH_NAME from usermgmt_internet_mph where lower(MPH_NAME) =lower(:mphName)", nativeQuery = true)
	public MphNameEntity getvalidateMphNam(@Param("mphName") String mphName);
  	

	@Query(value="select * from usermgmt_internet_mph uim , master_portal_users mpu  where  (mpu.mph_name = uim.MPH_NAME or mpu.mph_name = uim.MPH_KEY) and lower(mpu.username) = lower(:username)", nativeQuery = true)
	public MphNameEntity getMphDetails(@Param("username") String username);
	 
	@Query(value = "select MPH_ID,MPH_NAME,MPH_CODE,MPH_KEY,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,MPH_ADMIN_MAXCOUNT,FIRST_ADMIN_CREATED,PORTALS_ASSIGNED,POLICY_NUMBER from USERMGMT_INTERNET_MPH where lower(MPH_NAME) =lower(:mphName) order by MPH_ID desc", nativeQuery = true)
	List<MphNameEntity> findMphDetailsUsingMphName(@Param("mphName") String mphName);
	
	@Query(value = "select MPH_ID,MPH_NAME,MPH_CODE,MPH_KEY,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,MPH_ADMIN_MAXCOUNT,FIRST_ADMIN_CREATED,PORTALS_ASSIGNED,POLICY_NUMBER from USERMGMT_INTERNET_MPH where lower(POLICY_NUMBER) =lower(:policyNumber) order by MPH_ID desc", nativeQuery = true)
	MphNameEntity findMphDetailsUsingPolicyNumber(@Param("policyNumber") String policyNumber);
	
	@Modifying
	@Query(value ="update USERMGMT_INTERNET_MPH set IS_ACTIVE='N', IS_DELETED='Y' where lower(MPH_NAME) =lower(:mphName)", nativeQuery = true)
	public void deleteBankNameByUpdatingItsFlag(@Param("mphName") String mphName);
	
	@Query(value = "select MPH_ID,MPH_NAME,MPH_CODE,MPH_KEY,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,MPH_ADMIN_MAXCOUNT,FIRST_ADMIN_CREATED,PORTALS_ASSIGNED,POLICY_NUMBER from USERMGMT_INTERNET_MPH where lower(MPH_NAME) =lower(:mphName) order by MPH_ID desc", nativeQuery = true)
	public MphNameEntity checkMphAvailability(@Param("mphName") String mphName);
	
	@Query(value = "select MPH_ID,MPH_NAME,MPH_CODE,MPH_KEY,IS_ACTIVE,IS_DELETED,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,MPH_ADMIN_MAXCOUNT,FIRST_ADMIN_CREATED,PORTALS_ASSIGNED,POLICY_NUMBER from USERMGMT_INTERNET_MPH where IS_ACTIVE = :isActive and IS_DELETED = :isDeleted order by MPH_ID desc", nativeQuery = true)
	List<MphNameEntity> findActiveInActiveMphDetails(@Param("isActive") String isActive, @Param("isDeleted") String isDeleted);
	
	@Modifying
	@Query(value ="update USERMGMT_INTERNET_MPH set FIRST_ADMIN_CREATED='Y' where lower(MPH_NAME) =lower(:mphName)", nativeQuery = true)
	public void updatingFirstAdminFlag(@Param("mphName") String mphName);
	
	@Query(value ="select max(MPH_ID) from usermgmt_internet_mph", nativeQuery = true)
	public String findMaxMPHID();
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update USERMGMT_INTERNET_MPH  set PORTALS_ASSIGNED =:portalsToAssign where ((lower(PORTALS_ASSIGNED) = :portalToDelete) OR (UPPER(PORTALS_ASSIGNED) LIKE UPPER('%'||:portalToDelete||'%') )) and lower(MPH_NAME) = lower(:mphName)",nativeQuery = true)
	public int deletePortalsByMphName(@Param("portalToDelete") String portalToDelete,@Param("mphName") String mphName, @Param("portalsToAssign") String portalsToAssign);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update USERMGMT_INTERNET_MPH  set PORTALS_ASSIGNED =:portalsToAssign where lower(MPH_NAME) = lower(:mphName)",nativeQuery = true)
	public int updatePortalsByMphName(@Param("mphName") String mphName, @Param("portalsToAssign") String portalsToAssign);
	
	@Query(value = "select * from usermgmt_internet_mph um,common_master_policy_data cmd where cmd.mph_name=um.mph_name and cmd.policy_number =:policyNumber", nativeQuery = true)
	public MphNameEntity checkMphAvailabilityBasedOnPolicyNumber(@Param("policyNumber") String policyNumber);
	
}
