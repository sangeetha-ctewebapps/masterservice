package com.lic.epgs.mst.usermgmt.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.usermgmt.entity.UOAdminEntity;

public interface UoAdminService {

	public ResponseEntity<Map<String, Object>> addUoAdmin(UOAdminEntity uoAdminEntity) throws Exception ;

	public List<UOAdminEntity> getAllType() throws Exception;

	public ResponseEntity<Map<String, Object>> deleteUoAdmin(Long uoAdminId) throws Exception ;

	public ResponseEntity<Map<String, Object>> updateUoAdmin(UOAdminEntity uoAdminEntity) throws Exception ;



}