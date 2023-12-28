package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.UserRoleTypeMappingEntity;

@Repository
public interface UserRoleTypeRepository extends JpaRepository <UserRoleTypeMappingEntity , Long>{

	
	@Query(value = "SELECT * FROM USER_ROLETYPE_MAPPING WHERE MASTER_USER_ID=:masterUserId and ROLETYPEID=:roleTypeId and APPMODULEID =:appModuleId and IS_ACTIVE = 'Y' and IS_DELETED='N'", nativeQuery = true)
    public List<UserRoleTypeMappingEntity> findDuplicate(@Param("masterUserId") Long masterUserId,@Param("roleTypeId") Long roleId,@Param("appModuleId") Long appModuleId);
	
	
	@Query(value = "SELECT * FROM USER_ROLETYPE_MAPPING WHERE MASTER_USER_ID= :masterUserId order by appmoduleid", nativeQuery = true)
    public List<UserRoleTypeMappingEntity> getUserRoleTypeByRoleMapId(@Param("masterUserId") Long masterUserId);
	
	@Query(value = "select count(*) from user_roletype_mapping where appmoduleid =:appModuleId and roletypeid = :roleTypeId and master_user_id =:masterUserId", nativeQuery = true)
    public int duplicateUserRoleCheck(@Param("masterUserId") Long masterUserId,@Param("appModuleId") Long appModuleId,@Param("roleTypeId") Long roleTypeId);
	
	@Modifying
	@Query(value = "UPDATE USER_ROLETYPE_MAPPING SET IS_ACTIVE ='N' , IS_DELETED ='Y' WHERE USERROLETYPEMAPID= :userRoleTypeMappingId", nativeQuery = true)
	public void findAndDeleteById(@Param("userRoleTypeMappingId") Long userRoleTypeMappingId);
	
	@Modifying	
	@Query(value = "UPDATE USER_ROLETYPE_MAPPING SET IS_ACTIVE ='N' , IS_DELETED ='Y' WHERE MASTER_USER_ID= :masterUserId ", nativeQuery = true)
    public void findAndDeleteBymasterId(@Param("masterUserId") Long masterUserId);
	
	@Modifying	
	@Query(value = "UPDATE USER_ROLETYPE_MAPPING SET IS_ACTIVE ='Y' , IS_DELETED ='N' WHERE MASTER_USER_ID= :masterUserId and appmoduleid = '1' and roletypeid = '11' ", nativeQuery = true)
    public void updateAdminBymasterId(@Param("masterUserId") Long masterUserId);
	
	@Transactional
	@Modifying	
	@Query(value = "DELETE FROM USER_ROLETYPE_MAPPING WHERE MASTER_USER_ID= :masterUserId ", nativeQuery = true)
    public void DeleteBymasterId(@Param("masterUserId") Long masterUserId);
	
	@Modifying	
	@Query(value = "UPDATE USER_ROLETYPE_MAPPING  WHERE MASTER_USER_ID= :masterUserId and appmoduleid = '1' and roletypeid = '11' ", nativeQuery = true)
    public void findAndDeleteAdminBymasterId(@Param("masterUserId") Long masterUserId);
	 
	@Query(value = "SELECT * FROM USER_ROLETYPE_MAPPING  WHERE MASTER_USER_ID= :masterUserId and appmoduleid = '1' and roletypeid = '11' ", nativeQuery = true)
    public UserRoleTypeMappingEntity findAdminBymasterId(@Param("masterUserId") Long masterUserId);
	
	
	@Transactional
	@Modifying	
	@Query(value = "DELETE FROM USER_ROLETYPE_MAPPING  WHERE MASTER_USER_ID= :masterUserId and appmoduleid = '1' and roletypeid = '11' ", nativeQuery = true)
    public void DeleteAdminBymasterId(@Param("masterUserId") Long masterUserId);
	//@Query(value = "SELECT URTM.*,MAM.modulename,amr.roletype_name FROM USER_ROLE_TYPE_MAPPING URTM ,MASTER_APPLICAITON_MODULE MAM ,APP_MODULE_ROLETYPE AMR WHERE MAM.MODULEID = URTM.appmoduleid AND amr.roletypeid = urtm.roletypeid AND URTM.MASTER_USER_ID = :masterUserId order by URTM.CREATEDON desc", nativeQuery = true)
  //  public List<UserRoleTypeMappingEntity> findUserRoleByMasterUserId(@Param("masterUserId") Long masterUserId);
	
	@Query(value = "SELECT * FROM USER_ROLETYPE_MAPPING WHERE MASTER_USER_ID= :masterUserId and appmoduleid in (select moduleid from master_applicaiton_module where modulename = 'TDS') order by appmoduleid", nativeQuery = true)
    public List<UserRoleTypeMappingEntity> checkIfUserHasTDSRoles(@Param("masterUserId") Long masterUserId);
	
}
