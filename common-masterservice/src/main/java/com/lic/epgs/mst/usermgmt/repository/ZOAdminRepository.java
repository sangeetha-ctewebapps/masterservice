package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.ZOAdminEntity;

@Repository
public interface ZOAdminRepository extends JpaRepository<ZOAdminEntity, Long> {

	@Query(value = "SELECT * FROM ZO_Admin WHERE ZO_ADMIN_ID = :zoAdminId and IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true)
	public	ZOAdminEntity getZoAdminDetail(@Param("zoAdminId") Long zoAdminId);

	@Query(value = "SELECT * FROM ZO_Admin WHERE MASTER_USER_ID=:masterUserId and IS_ACTIVE ='Y' and IS_DELETED ='N'", nativeQuery = true)
	public List<ZOAdminEntity> findDuplicate(@Param("masterUserId") Long masterUserId);

	@Query(value = "SELECT * FROM ZO_Admin WHERE MASTER_USER_ID=:masterUserId and IS_ACTIVE ='Y' and IS_DELETED ='N'", nativeQuery = true)
	public ZOAdminEntity findDuplicateByMasterUserId(@Param("masterUserId") Long masterUserId);
	
	@Modifying	
	@Query(value = "UPDATE ZO_Admin SET IS_ACTIVE ='N' , IS_DELETED ='Y' WHERE  MASTER_USER_ID= :zoAdminId", nativeQuery = true) 
	public void findAndDeleteById(@Param("zoAdminId") Long zoAdminId);
	
	@Transactional
	@Modifying	
	@Query(value = "DELETE FROM ZO_Admin  WHERE  MASTER_USER_ID= :masterUserId", nativeQuery = true) 
	public void findAndDeleteByMasterUserId(@Param("masterUserId") Long masterUserId);
	
	@Query(value = "SELECT * FROM ZO_ADMIN WHERE MASTER_USER_ID=:zoAdminId and IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true)
    public ZOAdminEntity getZoAdminDetailByID(@Param("zoAdminId") Long zoAdminId);
		
}