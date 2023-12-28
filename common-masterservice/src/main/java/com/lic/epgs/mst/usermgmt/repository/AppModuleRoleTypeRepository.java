package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.AppModuleRoleTypeEntity;

@Repository
public interface AppModuleRoleTypeRepository extends JpaRepository<AppModuleRoleTypeEntity, Long>{
	
	//@Query(value = "SELECT * FROM MASTER_ROLETYPE WHERE APPMODULEID=:appModuleId and IS_ACTIVE='Y'", nativeQuery = true)
	//public List<AppModuleRoleTypeEntity> getUserRoleTypeByappModuleId(@Param("appModuleId") Long appModuleId);

}
