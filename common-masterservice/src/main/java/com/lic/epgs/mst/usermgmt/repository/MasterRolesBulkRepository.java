package com.lic.epgs.mst.usermgmt.repository; 

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MasterRolesBulkEntity;


@Repository
public interface MasterRolesBulkRepository extends JpaRepository<MasterRolesBulkEntity,Long>
{
	@Query(value ="select role_key from master_roles_bulk where lower(role_key)=lower(:role)", nativeQuery = true)
	public String validateRoleForBulkUser(@Param("role") String role);
	
	@Query(value ="select ID,ROLE_KEY,ROLE_NAME,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,ROLE_ID,CLIENT_ROLE,COMPOSITE,RHSSO_REALM,CLIENT_ID,MAKERPRESENT,CHECKERPRESENT,APPROVERPRESENT,ROLE_DISPLAY_NAME from master_roles_bulk where lower(role_key)=lower(:role)", nativeQuery = true)
	public MasterRolesBulkEntity getRoleNameFromRoleKey(@Param("role") String role);
	
	@Query(value ="select ID,ROLE_KEY,ROLE_NAME,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,ROLE_ID,CLIENT_ROLE,COMPOSITE,RHSSO_REALM,CLIENT_ID,MAKERPRESENT,CHECKERPRESENT,APPROVERPRESENT,ROLE_DISPLAY_NAME from master_roles_bulk ", nativeQuery = true)
	public List<MasterRolesBulkEntity> getAllModulesBulk();
	
	@Query(value ="select ID,ROLE_KEY,ROLE_NAME,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,ROLE_ID,CLIENT_ROLE,COMPOSITE,RHSSO_REALM,CLIENT_ID,MAKERPRESENT,CHECKERPRESENT,APPROVERPRESENT,ROLE_DISPLAY_NAME from master_roles_bulk where ROLE_NAME NOT LIKE '%Maker%'", nativeQuery = true)
	public List<MasterRolesBulkEntity> getAllModulesBulkWithoutRoleType();
	
	@Query(value ="select ID,ROLE_KEY,ROLE_NAME,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,ROLE_ID,CLIENT_ROLE,COMPOSITE,RHSSO_REALM,CLIENT_ID,MAKERPRESENT,CHECKERPRESENT,APPROVERPRESENT,ROLE_DISPLAY_NAME from master_roles_bulk where lower(ROLE_NAME)=lower(:roleName)", nativeQuery = true)
	public MasterRolesBulkEntity getRoleDetailsFromRoleName(@Param("roleName") String roleName);
	
	@Query(value ="select ID,ROLE_KEY,ROLE_NAME,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,ROLE_ID,CLIENT_ROLE,COMPOSITE,RHSSO_REALM,CLIENT_ID,MAKERPRESENT,CHECKERPRESENT,APPROVERPRESENT,ROLE_DISPLAY_NAME from master_roles_bulk where UPPER(ROLE_NAME) LIKE UPPER(:roleName||'%')", nativeQuery = true)
	public List<MasterRolesBulkEntity> getRoleTypesFromRoleName(@Param("roleName") String roleName);
	
	@Query(value ="select distinct(CLIENT_ID) from master_roles_bulk", nativeQuery = true)
	public List<String> getAllUniqueClients();
	
	@Query(value ="select ID,ROLE_KEY,ROLE_NAME,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,ROLE_ID,CLIENT_ROLE,COMPOSITE,RHSSO_REALM,CLIENT_ID,MAKERPRESENT,CHECKERPRESENT,APPROVERPRESENT,ROLE_DISPLAY_NAME from master_roles_bulk where UPPER(STREAM)=UPPER(:stream)", nativeQuery = true)
	public List<MasterRolesBulkEntity> getAllRolesUnderThatStream(@Param("stream") String stream); 
	
}
