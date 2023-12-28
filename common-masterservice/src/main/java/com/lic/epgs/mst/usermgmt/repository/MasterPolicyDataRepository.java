package com.lic.epgs.mst.usermgmt.repository; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.usermgmt.entity.MasterPolicyDataEntity;
import com.lic.epgs.mst.usermgmt.entity.MphNameEntity;

public interface MasterPolicyDataRepository  extends JpaRepository<MasterPolicyDataEntity,Long> {
	
	@Query(value = "select count(1) from COMMON_MASTER_POLICY_DATA where lower(policy_number)=lower(:policyNumber)", nativeQuery = true)
	public int checkIfPolicyNumberIsValid(@Param("policyNumber") String policyNumber);
	
	@Query(value = "select cmpd.master_policy_id,cmpd.policy_number,cmpd.policy_id,cmpd.unit_code,cmpd.unit_id,cmpd.zonal_id,cmpd.zone_name,cmpd.created_by,cmpd.created_on,cmpd.modified_by,cmpd.modified_on,cmpd.is_active,cmpd.stream,cmpd.mph_code,cmpd.mph_name from COMMON_MASTER_POLICY_DATA CMPD where lower(policy_number)=lower(:policyNumber)", nativeQuery = true)
	public MasterPolicyDataEntity getUnitAndZoneFromPolicyNo(@Param("policyNumber") String policyNumber);
	
	@Query(value = "select cmpd.master_policy_id,cmpd.policy_number,cmpd.policy_id,cmpd.unit_code,cmpd.unit_id,cmpd.zonal_id,cmpd.zone_name,cmpd.created_by,cmpd.created_on,cmpd.modified_by,cmpd.modified_on,cmpd.is_active,cmpd.stream,cmpd.mph_code,cmpd.mph_name from COMMON_MASTER_POLICY_DATA CMPD where lower(policy_number)=lower(:policyNumber)", nativeQuery = true)
	public MasterPolicyDataEntity checkPolicyAvailability(@Param("policyNumber") String policyNumber);
	
	

}
