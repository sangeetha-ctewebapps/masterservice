package com.lic.epgs.mst.usermgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MPHSuperAdminUsersEntity;

@Repository
public interface MPHSuperAdminUsersRepository  extends JpaRepository<MPHSuperAdminUsersEntity, Long>
{
	@Query(value = "select ID,USERNAME,MOBILENO,EMAILID,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON from MPH_SUPERADMIN_USERS where lower(USERNAME) =lower(:username)", nativeQuery = true)
	public MPHSuperAdminUsersEntity getSuperAdminUserDetails(@Param("username") String username);
}
