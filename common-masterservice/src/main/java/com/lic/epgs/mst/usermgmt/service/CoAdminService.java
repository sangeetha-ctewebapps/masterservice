package com.lic.epgs.mst.usermgmt.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.usermgmt.entity.CoAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.COAdminException;
import com.lic.epgs.mst.usermgmt.modal.CoAdminModel;
import com.lic.epgs.mst.usermgmt.modal.UoZoAdminModel;

public interface CoAdminService {

	List<CoAdminEntity> getAllCoAdmin();
	
	public ResponseEntity<Map<String, Object>> addCoAdmin(CoAdminEntity coAdminEntity) throws COAdminException;
	
	 public ResponseEntity<Map<String, Object>> deleteCoAdmin(Long coAdminId) throws COAdminException;



	public ResponseEntity<Map<String, Object>> updateCoAdmin(CoAdminEntity coAdminEntity) throws COAdminException;
	 
   
	
	List<CoAdminModel> coSearch(String locationType,String location, String srNumber, String userName) throws SQLException;

}
