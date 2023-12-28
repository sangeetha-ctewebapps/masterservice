package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.UOAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.ZOAdminEntity;

@Repository
public interface UoAdminRepository extends JpaRepository<UOAdminEntity, Long> {

	@Query(value = "SELECT * FROM UO_ADMIN WHERE UO_ADMIN_ID=:uoAdminId and IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true)
	public UOAdminEntity getUoAdminDetail(@Param("uoAdminId") Long uoAdminId);

	/*@Query(value = "SELECT COUNT(*) FROM UO_ADMIN WHERE MASTER_USER_ID=:masterUserId", nativeQuery = true)
	public Integer findDuplicate(@Param("masterUserId") Long masterUserId);
	*/
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM UO_ADMIN  WHERE MASTER_USER_ID= :masterUserId", nativeQuery = true)
	public void findAndDeleteByMasterUserId(@Param("masterUserId") Long masterUserId);
	
	@Modifying
	@Query(value = "UPDATE UO_ADMIN SET IS_ACTIVE ='N' , IS_DELETED ='Y' WHERE MASTER_USER_ID= :uoAdminId", nativeQuery = true)
	public void findAndDeleteById(@Param("uoAdminId") Long uoAdminId);
	
	@Query(value = "SELECT * FROM UO_ADMIN WHERE MASTER_USER_ID=:masterUserId and IS_ACTIVE='Y' and IS_DELETED='N'", nativeQuery = true)
	public List<UOAdminEntity> findDuplicate(@Param("masterUserId") Long masterUserId);
	
	@Query(value = "SELECT * FROM UO_ADMIN WHERE MASTER_USER_ID=:masterUserId and IS_ACTIVE='Y' and IS_DELETED='N'", nativeQuery = true)
	public UOAdminEntity findDuplicateByMasterUserId(@Param("masterUserId") Long masterUserId);
	
	@Query(value = "SELECT * FROM UO_ADMIN WHERE MASTER_USER_ID=:uoAdminId and IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true)
    public UOAdminEntity getUoAdminDetailByID(@Param("uoAdminId") Long uoAdminId);

}
