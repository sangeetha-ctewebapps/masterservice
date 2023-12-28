package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MasterRoleAppEntity;

@Repository
public interface MasterRoleAppRepository  extends JpaRepository<MasterRoleAppEntity,Long>{
	
	@Query(value = "select MR.*, MAM.MODULENAME from MASTER_ROLE MR inner join master_applicaiton_module  MAM ON MR.APP_MODULE = TO_CHAR(MAM.MODULEID) order by ROLEID desc", nativeQuery = true) 
	 public List<MasterRoleAppEntity> getAllRolesByApp();

}
