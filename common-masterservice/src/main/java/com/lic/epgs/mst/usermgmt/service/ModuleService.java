package com.lic.epgs.mst.usermgmt.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.usermgmt.entity.LoggedInUserSessionDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterApplicationModule;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersLoginDetailsEntity;

public interface ModuleService {
	
	List<MasterApplicationModule> getAllModule();
	
	public ResponseEntity<Map<String, Object>> saveLoggedinuserSessionDetails(LoggedInUserSessionDetailsEntity loggedInUserSessionDetailsEntity)
			throws Exception;

}
