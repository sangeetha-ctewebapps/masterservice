package com.lic.epgs.mst.usermgmt.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleAppEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.MasterRoleException;

public interface MasterRoleService {
	
	List<MasterRoleEntity> getAllRoles();
	public List<MasterRoleAppEntity> getAllRolesByApp();
	
	List<MasterRoleEntity> getAllActiveRoleByCondition() throws ResourceNotFoundException, MasterRoleException;
	
	public ResponseEntity<Map<String, Object>> addRole(MasterRoleEntity masterRoleEntity) throws Exception;
	
	public void removeRole(Long roleId) throws Exception;

	public ResponseEntity<Map<String, Object>> deleteMasterRole(Long roleId);

	public ResponseEntity<Map<String, Object>> updateMasterRole(MasterRoleEntity masterRoleEntity);

}
