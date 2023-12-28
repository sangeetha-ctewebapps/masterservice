package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lic.epgs.mst.usermgmt.entity.MasterRoleEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleIdEntity;

public interface MasterRoleIdRepository extends JpaRepository<MasterRoleIdEntity, Long> {

	@Query(value = "select  mr.*,urm.USERROLEMAPID from master_role mr inner join user_role_mapping urm on urm.roleId = mr.roleId where urm.master_user_Id=:masterUserId and mr.IS_ACTIVE='Y'", nativeQuery = true)
	List<MasterRoleIdEntity> getMasterRoleEntity(@Param("masterUserId") Long masterUserId);

}
