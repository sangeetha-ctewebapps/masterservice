package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.usermgmt.entity.MasterRoleEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleIdEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleURMEntity;

public interface MasterRoleURMRepository extends JpaRepository<MasterRoleURMEntity, Long> {

//	
	
//	@Query(value = "select  mr.*,urm.USERROLEMAPID, urm.IS_ACTIVE as urmIsActive, urm.IS_DELETED as urmIsDeleted from master_role mr inner join user_role_mapping urm on urm.roleId = mr.roleId where urm.master_user_Id=:masterUserId and urm.IS_ACTIVE='Y'and urm.IS_DELETED='N'", nativeQuery = true)
	@Query(value = "select  mr.*,urtm.USERROLETYPEMAPID as userRoleMappingId, urtm.IS_ACTIVE as urmIsActive, urtm.IS_DELETED as urmIsDeleted from master_role mr, USER_ROLETYPE_MAPPING urtm, Master_Roletype_roles_mapping mrrm where urtm.ROLETYPEID = mrrm.ROLETYPEID and mrrm.roleId= mr.roleId and urtm.IS_ACTIVE='Y'and urtm.IS_DELETED='N' and urtm.master_user_Id=:masterUserId", nativeQuery = true)
	List<MasterRoleURMEntity> getMasterRoleEntity(@Param("masterUserId") Long masterUserId);
	

}  
