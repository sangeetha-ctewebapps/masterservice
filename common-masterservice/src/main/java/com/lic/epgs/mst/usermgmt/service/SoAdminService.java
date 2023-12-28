package com.lic.epgs.mst.usermgmt.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.usermgmt.entity.SOAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.UOAdminEntity;
import com.lic.epgs.mst.usermgmt.modal.SoAdminModel;
import com.lic.epgs.mst.usermgmt.modal.UoZoAdminModel;

public interface SoAdminService {
	public ResponseEntity<Map<String, Object>> addSoAdmin(SOAdminEntity soAdminEntity) throws Exception ;

	public List<SOAdminEntity> getAllType() throws Exception;

	public ResponseEntity<Map<String, Object>> updateSoAdmin(SOAdminEntity soAdminEntity) throws Exception ;

	public ResponseEntity<Map<String, Object>> deleteSoAdmin(Long soAdminId) throws Exception ;
	
	List<SoAdminModel> soSearch(String locationType,String location, String srNumber, String userName) throws SQLException;
}
