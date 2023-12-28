package com.lic.epgs.mst.usermgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.entity.LoggedInUserSessionDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterApplicationModule;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersLoginDetailsEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserRoleMappingException;
import com.lic.epgs.mst.usermgmt.service.ModuleService;

@CrossOrigin("*")
@RestController
public class ModuleController {

	private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

	@Autowired
	private ModuleService moduleService;

	String className = this.getClass().getSimpleName();
	
	/*
	 * Description: This function is used for getting all the data from MASTER_APPLICAITON_MODULE 
	 * Table Name- MASTER_APPLICAITON_MODULE
	 * Author- Nandini R
	 */

	 @GetMapping("usermgmt/getAllModule")
	public ResponseEntity<Map<String, Object>> getAllModule() throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<MasterApplicationModule> getallmodule = moduleService.getAllModule();

			if (getallmodule.isEmpty() || getallmodule == null) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", getallmodule);
			}
			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error(" get All Module  exception occured." + ex.getMessage());
		}
		return null;
	}
	 
	 @PostMapping(value = "usermgmt/saveLoggedinuserSessionDetails")
		public ResponseEntity<Map<String, Object>> saveLoggedinuserSessionDetails(@RequestBody LoggedInUserSessionDetailsEntity loggedInUserSessionDetailsEntity)
				throws UserRoleMappingException {

			try {
				return moduleService.saveLoggedinuserSessionDetails(loggedInUserSessionDetailsEntity);
			} catch (Exception e) {
				logger.error("Exception" + e.getMessage());
				throw new UserRoleMappingException ("Internal Server Error");
			}
		}

}
