package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.CoAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.ZOAdminEntity;

@Repository
public interface CoAdminRepository extends JpaRepository<CoAdminEntity, Long> {

	@Query(value = "select * from CO_ADMIN WHERE IS_ACTIVE='Y' and IS_DELETED='N'", nativeQuery = true)
	public List<CoAdminEntity> getAllCoAdmin();
	
	
	@Query(value = "SELECT * FROM CO_ADMIN WHERE MASTER_USER_ID=:masterUserId and IS_ACTIVE ='Y' and IS_DELETED ='N'", nativeQuery = true)
	public List<CoAdminEntity> findDuplicate(@Param("masterUserId") Long masterUserId);
	
	@Query(value = "SELECT * FROM CO_ADMIN WHERE MASTER_USER_ID=:masterUserId and IS_ACTIVE ='Y' and IS_DELETED ='N'", nativeQuery = true)
	public CoAdminEntity findDuplicateByMasterUserId(@Param("masterUserId") Long masterUserId);
	
	@Modifying	
	@Query(value = "UPDATE CO_ADMIN SET IS_ACTIVE ='N' , IS_DELETED ='Y' WHERE MASTER_USER_ID= :coAdminId", nativeQuery = true)
    public void findAndDeleteById(@Param("coAdminId") Long coAdminId);
	
	@Transactional
	@Modifying	
	@Query(value = "DELETE FROM CO_ADMIN  WHERE MASTER_USER_ID= :masterUserId", nativeQuery = true)
    public void findAndDeleteByMasterUserId(@Param("masterUserId") Long masterUserId);
	
	@Query(value = "SELECT * FROM CO_ADMIN WHERE CO_ADMIN_ID=:coAdminId and IS_ACTIVE='Y'", nativeQuery = true)
    public CoAdminEntity getCoAdminDetail(@Param("coAdminId") Long coAdminId);

	
	@Query(value = "SELECT * FROM CO_ADMIN WHERE  MASTER_USER_ID =:coAdminId and IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true)
    public CoAdminEntity getCoAdminDetailByID(@Param("coAdminId") Long coAdminId);


}
