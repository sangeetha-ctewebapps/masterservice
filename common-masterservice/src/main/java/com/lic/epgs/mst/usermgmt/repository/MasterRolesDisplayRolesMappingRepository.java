package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.usermgmt.entity.AppModuleRoleTypeEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRolesDisplayRolesMappingEntity;

public interface MasterRolesDisplayRolesMappingRepository extends JpaRepository<MasterRolesDisplayRolesMappingEntity, Long>{
	
	@Query(value = "select * from master_roles_displayroles_mapping where  app_module_id =:appModuleId and (DISPLAY_ROLE_TYPE_NAME = null or DISPLAY_ROLE_TYPE_NAME =:displayRoleTypeName) and (User_type is null or User_type =:locationType)", nativeQuery = true)
	MasterRolesDisplayRolesMappingEntity getMasterRoleTypeDetails(@Param("appModuleId") Long appModuleId,@Param("displayRoleTypeName") String displayRoleTypeName,@Param("locationType") String locationType);
	
	@Query(value = "select * from master_roles_displayroles_mapping where  app_module_id =:appModuleId  and (User_type is null or User_type =:locationType) order by role_Type_Id asc", nativeQuery = true)
	public List<MasterRolesDisplayRolesMappingEntity> getUserRoleTypeByappModuleId(@Param("appModuleId") Long appModuleId,@Param("locationType") String locationType);
	
	@Query(value = "select * from master_roles_displayroles_mapping where  role_type_id =:roleTypeId ", nativeQuery = true)
	public MasterRolesDisplayRolesMappingEntity getDisplayRoleNameBasedOnRoleType(@Param("roleTypeId") Long roleTypeId);

}
