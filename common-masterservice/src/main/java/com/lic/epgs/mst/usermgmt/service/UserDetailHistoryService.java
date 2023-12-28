package com.lic.epgs.mst.usermgmt.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.UserDetailHistoryEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.MphUserServiceException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserDetailHistoryException;

public interface UserDetailHistoryService {

	public ResponseEntity<Object> saveUserDetailsHistory(UserDetailHistoryEntity userDetailHistoryEntity) throws UserDetailHistoryException;

	ResponseEntity<Object> getUserDetailsHistoryBasedOnUsername(String username, String startDate, String endDate, String loggedInUserUnitCode)
			throws UserDetailHistoryException;
	
//	public ResponseEntity<Object> getUsersHistoryDetail(String username) throws UserDetailHistoryException;
//	
}
