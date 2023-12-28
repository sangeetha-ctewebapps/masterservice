package com.lic.epgs.mst.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lic.epgs.mst.usermgmt.entity.MasterRoleEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;

public interface UserRepository extends JpaRepository<MasterUsersEntity, Long>  {
	
	@Query(value = "select * from Master_users where IS_ACTIVE = 'Y' and IS_DELETED = 'N'", nativeQuery = true) 
	List<MasterUsersEntity> getAllUser();

}
