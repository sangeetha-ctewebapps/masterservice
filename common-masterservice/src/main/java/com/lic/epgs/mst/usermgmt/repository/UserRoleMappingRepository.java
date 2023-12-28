package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.UserRoleMappingEntity;

@Repository
public interface UserRoleMappingRepository extends JpaRepository<UserRoleMappingEntity, Long> {
	
	@Query(value = "select * from User_Role_mapping where IS_ACTIVE = 'Y' and IS_DELETED = 'N' order by createdon desc", nativeQuery = true) 
	List<UserRoleMappingEntity> getAllUserRole();
	
	@Query(value = "SELECT * FROM USER_ROLE_MAPPING WHERE IS_ACTIVE = 'Y' and IS_DELETED= 'N' and MASTER_USER_ID = :masterUserId", nativeQuery = true)
	UserRoleMappingEntity getUserRoleByMasterUserId(@Param("masterUserId")Long masterUserId);
	
	@Query(value = "select * from MASTER_USERS where IS_ACTIVE = 'Y' and IS_DELETED = 'N' and SR_NUMBER=:srNumber", nativeQuery = true)
	UserRoleMappingEntity findUserRoleBySrNumber(@Param("srNumber")Long srNumber);
	
	@Query(value = "SELECT * FROM USER_ROLE_MAPPING WHERE IS_ACTIVE ='Y'", nativeQuery = true)
	public List<UserRoleMappingEntity> getAllActiveUser();

	@Query(value = "SELECT * FROM USER_ROLE_MAPPING WHERE IS_ACTIVE ='Y' and IS_DELETED='N' and USERROLEMAPID=:userRoleMappingId", nativeQuery = true)
	public UserRoleMappingEntity getUserRoleDetail (@Param("userRoleMappingId") Long userRoleMappingId);

	public List<UserRoleMappingEntity> findByMasterUserId(Long masterUserId);

	@Modifying	
	@Query(value = "UPDATE USER_ROLE_MAPPING SET IS_ACTIVE ='N' , IS_DELETED ='Y' WHERE USERROLEMAPID=:userRoleMappingId", nativeQuery = true) 
	public void findAndDeleteById(@Param("userRoleMappingId") Long userRoleMappingId);

	@Query(value = "SELECT * FROM USER_ROLE_MAPPING WHERE MASTER_USER_ID=:masterUserId and ROLEID=:roleId and IS_ACTIVE='Y' and IS_DELETED='N'", nativeQuery = true)
    public List<UserRoleMappingEntity> findDuplicate(@Param("masterUserId") Long masterUserId,@Param("roleId") Long roleId);
	
	@Query(value = "SELECT * FROM USER_ROLE_MAPPING WHERE IS_ACTIVE='Y' and IS_DELETED='N'", nativeQuery = true)
    public List<UserRoleMappingEntity> findAll(@Param("masterUserId") Long masterUserId,@Param("roleId") Long roleId);
}
