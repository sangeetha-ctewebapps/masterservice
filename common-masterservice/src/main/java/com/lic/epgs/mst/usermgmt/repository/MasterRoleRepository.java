package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.usermgmt.entity.MasterRoleEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;

public interface MasterRoleRepository extends JpaRepository<MasterRoleEntity, Long> {

	@Query(value = "select * from MASTER_ROLE WHERE IS_ACTIVE='Y'", nativeQuery = true) 
	List<MasterRoleEntity> getAllRoles();
	
	@Query(value = "select * from MASTER_ROLE WHERE IS_ACTIVE='Y'", nativeQuery = true) 
	public List<MasterRoleEntity> getAllActiveRoleByCondition();

	@Modifying
	@Query(value ="UPDATE MASTER_ROLE SET IS_ACTIVE ='Y' AND IS_DELETED ='N' WHERE ROLEID= :roleId", nativeQuery = true)
	public void updateByroleId(@Param("roleId")Long roleId);
	
	@Modifying	
	@Query(value = "UPDATE MASTER_ROLE SET IS_ACTIVE ='N' , IS_DELETED ='Y' WHERE ROLEID= :roleId", nativeQuery = true) 
	public void deleteByroleId(@Param("roleId")Long roleId);

	@Query(value = "select mr.*,urm.USERROLEMAPID from master_role mr inner join user_role_mapping urm on urm.roleId = mr.roleId where urm.master_user_Id=:masterUserId", nativeQuery = true)
	List<MasterRoleEntity> getMasterRoleEntity(@Param("masterUserId") Long masterUserId);
	
	@Query(value = "SELECT * FROM MASTER_ROLE WHERE ROLEID=:roleId and IS_ACTIVE='Y'", nativeQuery = true)
	public MasterRoleEntity getMasterRoleDetail (@Param("roleId") Long roleId);
	
	@Query(value = "SELECT * FROM MASTER_ROLE WHERE ROLENAME=:roleName AND APP_MODULE=:appModule AND IS_ACTIVE='Y' AND IS_DELETED='N'", nativeQuery = true)
    public List<MasterRoleEntity> findDuplicate(@Param("roleName") String roleName, @Param("appModule") String appModule);
	
	@Query(value = "select * from USER_ROLETYPE_MAPPING urtm, master_users mu,master_role mr,master_ROLETYPE_ROLES_MAPPING mar,MASTER_ROLETYPE amr, MASTER_APPLICAITON_MODULE mam  where mu.MASTER_USER_ID= urtm.MASTER_USER_ID and urtm.roletypeid = amr.roletypeid and amr.roletypeid = mar.roletypeid "
			+ " and mar.roleid = mr.roleid and urtm.APPMODULEID = mam.MODULEID and urtm.APPMODULEID = amr.APPMODULEID and amr.APPMODULEID = mar.APP_MODULE_ID "
			+ " AND lower(MU.EMAIL)  = lower(:srNumber) and mu.IS_ACTIVE= 'Y' and mu.IS_DELETED = 'N'  and urtm.IS_ACTIVE= 'Y' and urtm.IS_DELETED = 'N'", nativeQuery = true)
	public  List<MasterRoleEntity> checkAdminroleBySrNo(@Param("srNumber") String srNumber);

	
	@Query(value= "select * from user_role_mapping urm, master_users mu, master_role mr " + 
			"where mu.MASTER_USER_ID= urm.MASTER_USER_ID  and mr.ROLEID= urm.ROLEID and lower(mu.USERNAME)=lower(:email)" + 
			" and urm.IS_ACTIVE='Y' and urm.IS_DELETED='N' and mu.IS_ACTIVE='Y' and mu.IS_DELETED='N'", nativeQuery = true)
	public List<MasterRoleEntity> checkAdminRoleByEmail(@Param("email") String email);
	
	
}
