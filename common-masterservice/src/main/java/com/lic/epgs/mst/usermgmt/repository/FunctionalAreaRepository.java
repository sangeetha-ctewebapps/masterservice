package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.usermgmt.entity.FunctionalAreaEntity;


public interface FunctionalAreaRepository extends JpaRepository<FunctionalAreaEntity, Long> {

	
	@Query(value = "SELECT * FROM MASTER_FUNCTION_NAME WHERE IS_ACTIVE='Y'", nativeQuery = true)
	List<FunctionalAreaEntity> findAll();
	
	@Query(value = "SELECT * FROM MASTER_FUNCTION_NAME WHERE FUNCTIONDESCRIPTION=:functionDescription", nativeQuery = true)
	public List<FunctionalAreaEntity> findDuplicate(@Param("functionDescription") String functionDescription);
	
	@Query(value = "SELECT * FROM MASTER_FUNCTION_NAME WHERE FUNCTION_ID=:functionId and IS_ACTIVE='Y' and IS_DELETED='N'", nativeQuery = true)
	public FunctionalAreaEntity getAllFunctionalAreaDetail(@Param("functionId") Long functionId);
	
	@Modifying
	@Query(value = "UPDATE MASTER_FUNCTION_NAME SET IS_ACTIVE='N',IS_DELETED='Y' WHERE FUNCTION_ID=:functionId", nativeQuery = true)
	public void deleteFunctionalArea(@Param("functionId") Long functionId);

}

