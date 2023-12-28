package com.lic.epgs.mst.usermgmt.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.ZOAdminEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.ZOAdminException;

public interface ZOAdminService {

	ResponseEntity<Map<String, Object>> addZoAdmin(ZOAdminEntity zoAdminEntity) throws ZOAdminException;

	ResponseEntity<Map<String, Object>> updateZoAdmin(ZOAdminEntity zOAdminEntity) throws ZOAdminException;
	
	public ResponseEntity<Map<String, Object>> deleteZoAdmin(Long zoAdminId) throws ZOAdminException;
	
}
