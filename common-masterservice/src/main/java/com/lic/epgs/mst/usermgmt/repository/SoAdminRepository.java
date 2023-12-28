package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.SOAdminEntity;

@Repository
public interface SoAdminRepository extends JpaRepository<SOAdminEntity, Long> {

	@Query(value = "SELECT * FROM SO_ADMIN WHERE MASTER_USER_ID=:masterUserId and IS_ACTIVE='Y' and IS_DELETED='N'", nativeQuery = true)
	public List<SOAdminEntity> findDuplicate(@Param("masterUserId") Long masterUserId);
	
	@Query(value = "SELECT * FROM SO_ADMIN WHERE MASTER_USER_ID=:masterUserId and IS_ACTIVE='Y' and IS_DELETED='N'", nativeQuery = true)
	public SOAdminEntity findDuplicateByMasterUserId(@Param("masterUserId") Long masterUserId);
	
	@Query(value = "SELECT * FROM SO_ADMIN WHERE MASTER_USER_ID=:soAdminId and IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true)
	public SOAdminEntity getSoAdminDetail(@Param("soAdminId") Long soAdminId);
	
	@Modifying
	@Query(value = "UPDATE SO_ADMIN SET IS_ACTIVE ='N' , IS_DELETED ='Y' WHERE MASTER_USER_ID= :soAdminId", nativeQuery = true)
	public void findAndDeleteById(@Param("soAdminId") Long soAdminId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM SO_ADMIN  WHERE MASTER_USER_ID= :masterUserId", nativeQuery = true)
	public void findAndDeleteByMasterUserId(@Param("masterUserId") Long masterUserId);
	
	
}
