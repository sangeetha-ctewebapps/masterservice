package com.lic.epgs.mst.usermgmt.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptandDecryptAES;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptionModel;
import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.UserDetailHistoryEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleMappingEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.RolesAssignmentException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserManagementException;
import com.lic.epgs.mst.usermgmt.modal.MasterRoleModal;
import com.lic.epgs.mst.usermgmt.service.UserDetailHistoryServiceImpl;
import com.lic.epgs.mst.usermgmt.service.UserManagementService;

/**
 * @author SK00611397
 *
 */
@CrossOrigin("*")
@RestController
public class UserManagementController {

	@Autowired
	private UserManagementService userManagementService;
	
	@Autowired
	private UserDetailHistoryServiceImpl userDetailHistoryServiceImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);
	
	/*
	 * Description: This function is used for getting all the active data from USER ROLE MAPPING Module
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */

	  @GetMapping(value = "/usermgmt/getAllActiveUserRole")
	public ResponseEntity<Map<String, Object>> getAllActiveUserRole() throws UserManagementException{

		return userManagementService.getAllActiveUserRole();
	}
	
	/*
	 * Description: This function is used for adding the data into USER ROLE MAPPING Module
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */

	@PostMapping(value = "/usermgmt/addUserRole")
	public ResponseEntity<Map<String, Object>> addUserRole(
			@Valid @RequestBody UserRoleMappingEntity userRoleMappingEntity) throws UserManagementException{

		return userManagementService.addUserRole(userRoleMappingEntity);
	}

	/*
	 * Description: This function is used for updating the data in USER ROLE MAPPING Module
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	
	 @PutMapping(value = "/usermgmt/updateUserRole")
	public ResponseEntity<Map<String, Object>> updateUserRole(
			@Valid @RequestBody UserRoleMappingEntity userRoleMappingEntity) throws UserManagementException {

		return userManagementService.updateUserRole(userRoleMappingEntity);

	}
	
	/*
	 * Description: This function is used for soft deleting the data in USER ROLE MAPPING Module by using primary key
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */

	@DeleteMapping(value = "/usermgmt/deleteUserRole/{userRoleMapId}")
	public ResponseEntity<Map<String, Object>> deleteUserRole(@PathVariable("userRoleMapId") Long userRoleMapId) throws UserManagementException{

		return userManagementService.deleteUserRole(userRoleMapId);

	}
	
	/*
	 * Description: This function is used for getting all the MasterUserRole data from USER ROLE MAPPING Module by srNumber
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */

	  @GetMapping(value = "/usermgmt/getAllMasterUserRole/{srNumber}")
	   public ResponseEntity<Map<String, Object>> getAllMasterUserRole(@PathVariable("srNumber") String srNumber) throws UserManagementException {
		return userManagementService.getAllMasterUserRole(srNumber);
	}
	
	/*
	 * Description: This function is used for getting all the MasterUserRole data from USER ROLE MAPPING Module by emailId
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	
	@GetMapping(value = "/usermgmt/getAllMasterUserRoleByEmail/{emailId}")
	public ResponseEntity<Map<String, Object>> getAllMasterUserRoleByEmail(@PathVariable("emailId") String emailId) throws UserManagementException{
		return userManagementService.getAllMasterUserRoleByEmail(emailId);
	}
	
	/*
	 * Description: This function is used for checkAdminAccess from USER ROLE MAPPING Module by emailId
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	
	 @GetMapping(value = "/usermgmt/checkAdminAccessByEmail/{emailId}")
	public ResponseEntity<Map<String, Object>> checkAdminAccessByEmail(@PathVariable("emailId") String emailId) throws UserManagementException {
		return userManagementService.checkAdminAccessByEmail(emailId);
	}
	 
		/*
		 * Description: This function is used for getting all the Role data from USER ROLE MAPPING Module 
		 * Table Name- USER_ROLE_MAPPING
		 * Author- Nandini R
		 */

	@GetMapping(value = "/usermgmt/getAllUserRoles")
	public ResponseEntity<Map<String, Object>> getAllUserRoles() throws UserManagementException{
		 return userManagementService.getAllUserRoles();
	}

	/*
	 * Description: This function is used for getting all the UserRole data from USER ROLE MAPPING Module by masterUserId
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	
	@GetMapping(value = "/usermgmt/getUserRoleById/{masterUserId}")
	public ResponseEntity<Map<String, Object>> getUserRoleById(@PathVariable("masterUserId") Long masterUserId) throws UserManagementException{
		return userManagementService.getUserRoleById(masterUserId);
	}

	/*
	 * Description: This function is used for getting all the data from MASTER_APPLICAITON_MODULE 
	 * Table Name- MASTER_APPLICAITON_MODULE
	 * Author- Nandini R
	 */
	
	@GetMapping(value = "/usermgmt/getAllModules")
	public ResponseEntity<Map<String, Object>> getAllMasterUsersDetail() throws UserManagementException{
		return userManagementService.getAllModule();
	}

	/*
	 * Description: This function is used for getting all the MasterUserRole data from USER ROLE MAPPING Module by location
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	
	@GetMapping(value = "/usermgmt/getAllMasterUserRoleByLocation/{location}")
	public ResponseEntity<Map<String, Object>> getAllMasterUserRoleByLocation(
			@PathVariable("location") String location) throws UserManagementException{
		return userManagementService.getAllMasterUserRoleByLocation(location);
		}
	
	/*
	 * Description: This function is used for getting all the MasterUserRole data from USER ROLE MAPPING Module by locationCode
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	
	@GetMapping(value = "/usermgmt/getAllMasterUserRoleByLocationCode/{locationCode}")
	public ResponseEntity<Map<String, Object>> getAllMasterUserRoleByLocationCode(@PathVariable("locationCode") String locationCode) throws UserManagementException{
		return userManagementService.getAllMasterUserRoleByLocationCode(locationCode);
		}
	
	/*
	 * Description: This function is used for getting all the MasterUserRole data from USER ROLE MAPPING Module by RoleName
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */

	@PostMapping(value = "/usermgmt/getUserByRoleName")
	public ResponseEntity<Map<String, Object>> getUserByRoleName(@RequestBody MasterRoleModal masterRoleModal) throws UserManagementException{

		return userManagementService.getUserByRoleName(masterRoleModal);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, Object> response = new HashMap<>();
		if (ex.getBindingResult().getAllErrors() != null) {
			response.put("Status", 12);
			response.put("Message", Constant.INVALID_PAYLOAD);
		}
		return response;
	}
	
	@PostMapping(value = "/usermgmt/saveUserHistoryDetails")
	public ResponseEntity<Object> saveUserHistoryDetails(@RequestBody EncryptionModel encryptionModel)
			throws UserManagementException {

		try {
			JSONObject plainJSONObject = EncryptandDecryptAES
					.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());

			Date today = new Date();
			UserDetailHistoryEntity userHistoryEntity = new UserDetailHistoryEntity();
			String date1 = plainJSONObject.getString("createdOn");
			String date2 = plainJSONObject.getString("modifiedOn");
			if (!plainJSONObject.getString("action").equalsIgnoreCase("add")) {
				DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date modifiedOn = inputFormat.parse(plainJSONObject.getString("modifiedOn"));
				userHistoryEntity.setModifiedOn(modifiedOn);
				userHistoryEntity.setModifiedBy(plainJSONObject.getString("loggedInUser"));
			} else {
				DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date createdOn = inputFormat.parse(plainJSONObject.getString("createdOn"));
				userHistoryEntity.setCreatedon(createdOn);
				userHistoryEntity.setCreatedby(plainJSONObject.getString("loggedInUser"));
				
			}
			userHistoryEntity.setUnitCode(plainJSONObject.getString("unitCode"));
			userHistoryEntity.setUsername(plainJSONObject.getString("username").toLowerCase());
			userHistoryEntity.setEmail(plainJSONObject.getString("email").toLowerCase());
			userHistoryEntity.setMphname(plainJSONObject.getString("mphName"));
			userHistoryEntity.setAction(plainJSONObject.getString("action"));
			userHistoryEntity.setRemark(plainJSONObject.getString("remark"));
			userHistoryEntity.setStatus(plainJSONObject.getString("status"));

			return userDetailHistoryServiceImpl.saveUserDetailsHistory(userHistoryEntity);

		} catch (Exception e) {
			logger.error(" add Mph User admin exception occured." + e.getMessage());
			throw new UserManagementException("Internal Server Error");
		}
	}	
	
//	@PostMapping(value = "/usermgmt/getUserHistoryDetails")
//	public ResponseEntity<Object> getUserHistoryDetails(@RequestBody EncryptionModel encryptionModel)
//			throws UserManagementException{
//			
//		try {
//			JSONObject plainJSONObject = EncryptandDecryptAES
//					.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
//			Date today = new Date();
//			UserDetailHistoryEntity userHistoryEntity = new UserDetailHistoryEntity();
////			Date Startdate = new SimpleDateFormat("yyyy-MM-dd").parse(plainJSONObject.getString("startDate"));
////			Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(plainJSONObject.getString("endDate"));
//			String username=plainJSONObject.getString("username").toLowerCase();
//			String fromDate=plainJSONObject.getString("fromDate").toLowerCase();
//			String toDate=plainJSONObject.getString("toDate").toLowerCase();
//			
//			
//			return  userDetailHistoryServiceImpl.getUserDetailsHistoryBasedOnUsername(username,fromDate, toDate);   
//			
//		}catch(Exception e) {
//			logger.error(" add Mph User admin exception occured." + e.getMessage());
//			throw new UserManagementException("Internal Server Error");
//		}
//	}
}