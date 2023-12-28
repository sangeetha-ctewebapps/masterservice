package com.lic.epgs.mst.usermgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleScreenEntity;
import com.lic.epgs.mst.usermgmt.service.MasterRoleScreenService;

@CrossOrigin("*")
@RestController

public class MasterRoleScreenController {
	
	private static final Logger logger = LoggerFactory.getLogger(MasterRoleScreenController.class);

	@Autowired
	private MasterRoleScreenService masterRoleScreenService;

	String className = this.getClass().getSimpleName();
	
	/*
	 * Description: This function is used for getting all the data from MASTER ROLE SCREEN Module
	 * Table Name- MASTER_ROLE_SCREEN
	 * Author- Nandini R
	 */

	 @GetMapping("/usermgmt/getAllScreens")
	public ResponseEntity<Map<String, Object>> getAllScreens()  {
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<MasterRoleScreenEntity> getallscreens = masterRoleScreenService.getAllScreens();

			if(getallscreens==null || getallscreens.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", getallscreens);
			}
			
			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error(" get All Screens  exception occured." + ex.getMessage());
		}
		return null;
	}
	
	/*
	 * Description: This function is used for getting data from MASTER ROLE SCREEN Module by using the role id
	 * Table Name- MASTER_ROLE_SCREEN
	 * Author- Nandini R
	 */
	
	@GetMapping(value = "/usermgmt/getAllScreensByRoleId/{roleId}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> getAllScreensByRoleId(@PathVariable Long roleId)
			throws Exception {
		logger.info("Method Start--getAllScreensByRoleId--");

		logger.info("roleId::" + roleId);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		MasterRoleScreenEntity objMasterRoleScreenEntity = masterRoleScreenService.getAllScreensByRoleId(roleId);

		try {
			if (objMasterRoleScreenEntity == null) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", objMasterRoleScreenEntity);
			}

			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error(" get All Screens By role id exception occured." + ex.getMessage());
		}
		return null;
	}

}
