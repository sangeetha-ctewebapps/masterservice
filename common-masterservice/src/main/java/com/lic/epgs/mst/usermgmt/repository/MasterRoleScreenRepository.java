package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MasterRoleScreenEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleMappingEntity;
@Repository
public interface MasterRoleScreenRepository extends JpaRepository<MasterRoleScreenEntity, Long>  {
	
	@Query(value = "select * from MASTER_ROLE_SCREEN WHERE IS_ACTIVE='Y' and IS_DELETED='N'", nativeQuery = true) 
	public List<MasterRoleScreenEntity> getAllScreens();

	@Query(value = "SELECT * FROM MASTER_ROLE_SCREEN WHERE ROLE_ID = :roleId and IS_ACTIVE='Y'", nativeQuery = true)
	public MasterRoleScreenEntity findByRoleId(@Param("roleId")Long roleId);
	
	
}