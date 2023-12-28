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
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.MasterUserException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.MphUserServiceException;
import com.lic.epgs.mst.usermgmt.modal.EmailModel;
import com.lic.epgs.mst.usermgmt.modal.SMSModel;
import com.lic.epgs.mst.usermgmt.service.UserService;
import com.lic.epgs.mst.usermgmt.service.UserManagementService;

@CrossOrigin("*")
@RestController
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserManagementService userManagementService;

	String className = this.getClass().getSimpleName();

	/*
	 * Description: This function is used for getting all data from MASTER USERS Module
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	 @GetMapping("usermgmt/getAllUsers")
	public ResponseEntity<Map<String, Object>> getAllMasterUsersDetail() {
		return userManagementService.getAllMasterUsersDetail();
	}

	 @PostMapping(value = "/usermgmt/sendEmail")
		public ResponseEntity<Object> sendEmail(@Valid @RequestBody EmailModel emailModel) throws MphUserServiceException {
			logger.debug("UserController : sendEmail : initiated");
		
				return userManagementService.sendEmail(emailModel);			
			
		}


	 @PostMapping(value = "/usermgmt/sendSMS")

	 		public ResponseEntity<Object> sendSMS(@Valid @RequestBody SMSModel smsModel) throws MphUserServiceException {

	 			logger.debug("UserController : sendEmail : initiated");


	 				return userManagementService.sendSMS(smsModel);			


	 	}
}
