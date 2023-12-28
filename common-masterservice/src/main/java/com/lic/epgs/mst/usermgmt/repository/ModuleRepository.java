package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.MasterApplicationModule;

@Repository
public interface ModuleRepository extends JpaRepository<MasterApplicationModule, Long> {
	
	@Query(value = "select * from MASTER_APPLICAITON_MODULE order by MODULEID desc", nativeQuery = true) 
	List<MasterApplicationModule> findAllModule();
	
	@Query(value = "select MODULENAME from MASTER_APPLICAITON_MODULE where MODULEID = :moduleId", nativeQuery = true) 
	String findModuleNameById(@Param("moduleId") Long moduleId);


}
