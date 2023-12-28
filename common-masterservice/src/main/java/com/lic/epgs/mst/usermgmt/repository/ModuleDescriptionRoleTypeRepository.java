package com.lic.epgs.mst.usermgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.usermgmt.entity.ModuleDescriptionRoleTypeEntity;


@Repository
public interface ModuleDescriptionRoleTypeRepository  extends JpaRepository<ModuleDescriptionRoleTypeEntity, Long>{

	@Query(value = "select * from module_roletype_description where roletype_name =:roleTypeName", nativeQuery = true)
	public ModuleDescriptionRoleTypeEntity getDescriptionByRoleTypeName(@Param("roleTypeName")String roleTypeName);
	
}
