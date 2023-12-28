package com.lic.epgs.mst.usermgmt.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptandDecryptAES;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptionModel;
import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.RolesAssignmentHistory;
import com.lic.epgs.mst.usermgmt.entity.UserDetailHistoryEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.MphBankServiceException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.MphUserServiceException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.RolesAssignmentException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserManagementException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserRoleMappingException;
import com.lic.epgs.mst.usermgmt.modal.EmailModel;
import com.lic.epgs.mst.usermgmt.modal.LoggedInUserModel;
import com.lic.epgs.mst.usermgmt.modal.MasterRoleModal;
import com.lic.epgs.mst.usermgmt.modal.SMSModel;
import com.lic.epgs.mst.usermgmt.service.MphUserService;
import com.lic.epgs.mst.usermgmt.service.RolesAssignmentHistoryService;
import com.lic.epgs.mst.usermgmt.service.UserDetailHistoryServiceImpl;
import com.lic.epgs.mst.usermgmt.service.UserManagementService;
import com.lic.epgs.mst.usermgmt.service.UserRoleTypeMappingService;
import com.lic.epgs.rhssomodel.RolesAssignmnetHistoryModel;
import com.lic.epgs.rhssomodel.UserCreationModel;
import org.json.JSONArray;

@CrossOrigin("*")
@RestController

public class SuperAdminController {
	
	@Autowired
	MphUserService  mphUserService;
	
	@Autowired
	private UserManagementService userManagementService;
	
	@Autowired
	RolesAssignmentHistoryService  rolesAssignmentHistoryservice;
	
	@Autowired
	UserRoleTypeMappingService userRoleTypeMappingService;
	
	@Autowired
	private EncryptandDecryptAES encryptandDecryptAES;

	@Autowired
	private UserDetailHistoryServiceImpl userDetailHistoryServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(SuperAdminController.class);
	
	@PostMapping(value="/usermgmt/addSuperAdminUserInDB")
	public ResponseEntity<Object> addSuperAdminUserInDb(@RequestBody EncryptionModel encryptionModel) throws MphUserServiceException{
		try {
       logger.debug("encryptionModel:"+encryptionModel.getEncryptedPayload());
			
			// Decryption technique
			JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
			
			Date today = new Date();
			PortalMasterEntity portalMasterEntity = new PortalMasterEntity();
			
			portalMasterEntity.setUsername(plainJSONObject.getString("username").toLowerCase());
			portalMasterEntity.setMobile(Long.parseLong(plainJSONObject.getString("mobile")));
			portalMasterEntity.setEmail(plainJSONObject.getString("email"));
			portalMasterEntity.setState(plainJSONObject.getString("state"));
			portalMasterEntity.setCity(plainJSONObject.getString("city"));
			portalMasterEntity.setDistrict(plainJSONObject.getString("district"));
			portalMasterEntity.setCreatedBy(plainJSONObject.getString("createdBy"));
			portalMasterEntity.setCreatedOn(today);
			portalMasterEntity.setIsActive(plainJSONObject.getString("isActive"));
			portalMasterEntity.setIsDeleted(plainJSONObject.getString("isDeleted"));
			portalMasterEntity.setRefreshToken(plainJSONObject.getString("refreshToken"));
			portalMasterEntity.setMphName(plainJSONObject.getString("mphName"));
			portalMasterEntity.setIsMphAdmin(plainJSONObject.getString("isMphAdmin"));
			portalMasterEntity.setLogOut(plainJSONObject.getString("logOut"));
			portalMasterEntity.setFullName(plainJSONObject.getString("fullName"));
			portalMasterEntity.setAssignRoleFlag(plainJSONObject.getString("assignRoleFlag"));
			portalMasterEntity.setOfficeName(plainJSONObject.getString("mphOfficeName"));
			portalMasterEntity.setOfficeCode(plainJSONObject.getString("mphOfficeCode"));
			portalMasterEntity.setIsNewUser(plainJSONObject.getString("isNewUser"));
			portalMasterEntity.setRemarks(plainJSONObject.getString("remarks"));
			portalMasterEntity.setStatus("SentForApproval");
			portalMasterEntity.setWorkflowStatus(1L);
			portalMasterEntity.setPolicyNumber(plainJSONObject.getString("policyNumber"));
			portalMasterEntity.setAction("ADD");
			portalMasterEntity.setUnit(plainJSONObject.getString("loggedInUserUnitCode"));
			
			
			return  mphUserService.addMphUser(portalMasterEntity);
		} catch (Exception e) {
			logger.error(" add Mph User admin exception occured." + e.getMessage());
			throw new MphUserServiceException ("Internal Server Error");
		}
	}
	 
	 @PostMapping(value = "/usermgmt/deleteAdminOrdinaryUser")
		public ResponseEntity<Object> deleteAdminOrdinaryUser(@RequestBody EncryptionModel encryptionModel) throws MphUserServiceException {

			try {
	       logger.debug("encryptionModel:"+encryptionModel.getEncryptedPayload());
				
				// Decryption technique
				JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
				
				Date today = new Date();
				PortalMasterEntity portalMasterEntity = new PortalMasterEntity();
				
				portalMasterEntity.setPortalUserId(plainJSONObject.getLong("portalUserId"));
				portalMasterEntity.setModifiedBy(plainJSONObject.getString("modifiedBy"));
				portalMasterEntity.setRemarks(plainJSONObject.getString("remarks"));
				
				
				
				return  mphUserService.deleteMphUser(portalMasterEntity);
			} catch (Exception e) {
				logger.error(" add Mph User admin exception occured." + e.getMessage());
				throw new MphUserServiceException ("Internal Server Error");
			}
		}
	 
	 @PostMapping(value = "/usermgmt/addRolesToUserInDb")
		public ResponseEntity<Object> addRolesToUserInDb(@RequestBody EncryptionModel encryptionModel) throws RolesAssignmentException {

			try {
	       logger.debug("encryptionModel:"+encryptionModel.getEncryptedPayload());
				
				// Decryption technique
				JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
				
				Date today = new Date();
				RolesAssignmentHistory portalMasterEntity = new RolesAssignmentHistory();
				
				portalMasterEntity.setMphId(plainJSONObject.getLong("mphId"));
				portalMasterEntity.setMphName(plainJSONObject.getString("mphName"));
				portalMasterEntity.setCreatedBy(plainJSONObject.getString("createdBy"));
				portalMasterEntity.setRoleName(plainJSONObject.getString("roleName"));
				portalMasterEntity.setRemarks(plainJSONObject.getString("remarks"));
				return  rolesAssignmentHistoryservice.rolesAssigmentHistory(portalMasterEntity);
			} catch (Exception e) {
				logger.error(" add Mph User admin exception occured." + e.getMessage());
				throw new RolesAssignmentException ("Internal Server Error");
			}
		}
	 
	 
//	 @GetMapping(value = "/usermgmt/getRolesAssignmentStatus")
//		public ResponseEntity<Object> getRolesAssignmentStatus(
//				@RequestBody EncryptionModel encryptionModel) throws RolesAssignmentException {
//
//			Map<String, Object> response = new HashMap<String, Object>();
//
//			try {
//				logger.debug("encryptionModel:" + encryptionModel.getEncryptedPayload());
//
//				// Decryption technique
//				JSONObject plainJSONObject = EncryptandDecryptAES
//						.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
//
//				Date today = new Date();
//				RolesAssignmentHistory portalMasterEntity = new RolesAssignmentHistory();
//
//				portalMasterEntity.setCreatedBy(plainJSONObject.getString("loggedInUserName"));
//				portalMasterEntity.setStatus(plainJSONObject.getString("status"));
//
//					return  rolesAssignmentHistoryservice
//						.getrolesAssigmentHistory(portalMasterEntity);
//				
//			} catch (Exception e) {
//				logger.error(" add Mph User admin exception occured." + e.getMessage());
//				throw new RolesAssignmentException("Internal Server Error");
//			}
//			
//
//		}
	 
	 
	 @GetMapping(value = "/usermgmt/getRolesAssignmentStatus")
		public ResponseEntity<Object> getRolesAssignmentStatus(
				@RequestBody EncryptionModel encryptionModel) throws RolesAssignmentException {
		 
		    
			RolesAssignmnetHistoryModel rolesAssignmnetHistoryModel=new RolesAssignmnetHistoryModel();
			UserCreationModel  UserCreationModel=new UserCreationModel();
			JSONObject plainJSONObject = EncryptandDecryptAES
					.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
		String	action=plainJSONObject.getString("action");
		
			try {
				logger.debug("encryptionModel:" + encryptionModel.getEncryptedPayload());

				if (action.equalsIgnoreCase("addRoles") || action.equalsIgnoreCase("deleteRoles")) {

					rolesAssignmnetHistoryModel.setLoggedInUserName(plainJSONObject.getString("loggedInUserName"));
					if (action.equalsIgnoreCase("addRoles")) {
						rolesAssignmnetHistoryModel.setAction("ADD");
					} else {
						rolesAssignmnetHistoryModel.setAction("DELETE");
					}
					rolesAssignmnetHistoryModel.setStartDate(plainJSONObject.getString("startDate"));
					rolesAssignmnetHistoryModel.setEndDate(plainJSONObject.getString("endDate"));
					
				} else if (action.equalsIgnoreCase("addUsers") || action.equalsIgnoreCase("deleteUsers")) {
					UserCreationModel.setLoggedInUserName(plainJSONObject.getString("loggedInUserName"));
					if (action.equalsIgnoreCase("addUsers")) {
						UserCreationModel.setAction("ADD");
					} else {
						UserCreationModel.setAction("DELETE");
					}

					UserCreationModel.setStartDate(plainJSONObject.getString("startDate"));
					UserCreationModel.setEndDate(plainJSONObject.getString("endDate"));

				}
				else {
				rolesAssignmnetHistoryModel.setLoggedInUserName(plainJSONObject.getString("loggedInUserName"));
				UserCreationModel.setLoggedInUserName(plainJSONObject.getString("loggedInUserName"));
				}
				
				
				return rolesAssignmentHistoryservice.getrolesAndUsesAssigmentHistory(rolesAssignmnetHistoryModel,
						UserCreationModel);

			} catch (Exception e) {
				logger.error(" add Mph User admin exception occured." + e.getMessage());
				throw new RolesAssignmentException("Internal Server Error");
			}
			

		}

	 
	 @PostMapping(value = "/usermgmt/deleteRolesForSuperAdmin")
		public ResponseEntity<Object> deleteRolesForSuperAdmin(@RequestBody EncryptionModel encryptionModel) throws MphUserServiceException {

			try {
	       logger.debug("encryptionModel:"+encryptionModel.getEncryptedPayload());
				
				// Decryption technique
				JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
				
				Date today = new Date();
				RolesAssignmentHistory rolesAssignmentEntity = new RolesAssignmentHistory();
				
				rolesAssignmentEntity.setPortalRolesId(plainJSONObject.getLong("portalRolesId"));
				rolesAssignmentEntity.setModifiedBy(plainJSONObject.getString("modifiedBy"));
				
				
				
				return  mphUserService.deleteRolesForSuperAdmin(rolesAssignmentEntity);
			} catch (Exception e) {
				logger.error(" add Mph User admin exception occured." + e.getMessage());
				throw new MphUserServiceException ("Internal Server Error");
			}
		}
	 
	 @PostMapping(value = "/usermgmt/approveRejectUser")
		public ResponseEntity<Object> approveRejectUser(@RequestBody EncryptionModel encryptionModel) throws MphUserServiceException {

			try {
	       logger.debug("encryptionModel:"+encryptionModel.getEncryptedPayload());
				
				// Decryption technique
				JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
				return  mphUserService.approveRejectUser(plainJSONObject.getJSONArray("userArray"));
			} catch (Exception e) {
				logger.error(" add Mph User admin exception occured." + e.getMessage());
				throw new MphUserServiceException ("Internal Server Error");
			}
		}
	
	 
	 @PostMapping(value = "/usermgmt/getAllAdminOrdinaryUsers")
		public ResponseEntity<Object> getAllAdminOrdinaryUsers(@RequestBody EncryptionModel encryptionModel) throws MphUserServiceException 
		{
			logger.info("SuperAdminController : getAllAdminOrdinaryUsers : initiated");
			logger.info("encryptionModel::"+encryptionModel.getEncryptedPayload());
			try {
				JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());

				return mphUserService.getAllAdminOrdinaryUsers(plainJSONObject.getString("isMphAdmin"), plainJSONObject.getString("isActive"), plainJSONObject.getString("loggedInUserUnitCode"));

			} catch (Exception e) {
				logger.error("getAllAdminOrdinaryUsers exception occured." + e.getMessage());
				throw new MphUserServiceException("Internal Server Error");
			}
		}
	 
	 @PostMapping(value = "/usermgmt/getAllUsersPendingForApproval")
		public ResponseEntity<Object> getAllUsersPendingForApproval(@RequestBody EncryptionModel encryptionModel) throws MphUserServiceException 
		{
			logger.info("SuperAdminController : getAllUsersPendingForApproval : initiated");
			logger.info("encryptionModel::"+encryptionModel.getEncryptedPayload());
			try {
				JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());

				return mphUserService.getAllUsersPendingForApproval(plainJSONObject.getString("action"), plainJSONObject.getString("fromDate"), plainJSONObject.getString("toDate"), plainJSONObject.getString("loggedInUserUnitCode"));

			} catch (Exception e) {
				logger.error("getAllUsersPendingForApproval exception occured." + e.getMessage());
				throw new MphUserServiceException("Internal Server Error");
			}
		}
	 
	 @PostMapping(value = "/usermgmt/getAllUsersBasedOnStatus")
		public ResponseEntity<Object> getAllUsersBasedOnStatus(@RequestBody EncryptionModel encryptionModel) throws MphBankServiceException 
		{
			logger.info("SuperAdminController : getAllUsersBasedOnStatus : initiated");
			logger.info("encryptionModel::"+encryptionModel.getEncryptedPayload());
			try {
				JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());

				return mphUserService.getAllUsersBasedOnStatus(plainJSONObject.getString("status"), plainJSONObject.getString("loggedInUserUnitCode"));

			} catch (Exception e) {
				logger.error("getAllUsersBasedOnStatus exception occured." + e.getMessage());
				throw new MphBankServiceException("Internal Server Error");
			}
		}
	 
	 @PostMapping(value = "/usermgmt/getValidateUserName")
		public ResponseEntity<Object> getValidateUserName(@RequestBody EncryptionModel encryptionModel) throws MphUserServiceException
		{
		try {
			logger.debug("encryptionModel::"+encryptionModel.getEncryptedPayload());
			// Decryption technique
			JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
			String username = plainJSONObject.getString("username");
			
		return mphUserService.getValidateUserName(username.toLowerCase());
		} catch (MphUserServiceException e) {
		throw new MphUserServiceException ("Internal Server Error");
		}
	}
		
	 @PostMapping(value = "/usermgmt/getValidateEmail")
		public ResponseEntity<Object> getValidateEmail(@RequestBody EncryptionModel encryptionModel) throws MphUserServiceException
		{
		try {
			logger.debug("encryptionModel::"+encryptionModel.getEncryptedPayload());
			// Decryption technique
			JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
			String email = plainJSONObject.getString("email");
			
		return mphUserService.getValidateEmail(email);
		} catch (MphUserServiceException e) {
		throw new MphUserServiceException ("Internal Server Error");
		}
	}
	 
	 @PostMapping(value = "/usermgmt/getValidateMobile")
		public ResponseEntity<Object> getValidateMobile(@RequestBody EncryptionModel encryptionModel) throws MphUserServiceException
		{
		try {
			logger.debug("encryptionModel::"+encryptionModel.getEncryptedPayload());
			// Decryption technique
			JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
			String mobile = plainJSONObject.getString("mobile");
			
		return mphUserService.getValidateMobile(mobile);
		} catch (MphUserServiceException e) {
		throw new MphUserServiceException ("Internal Server Error");
		}
	}
	 
	 @PostMapping(value = "/usermgmt/updateLoggedInUserFlagDetails")
	 public ResponseEntity<Object> updateLoggedInUserFlagDetails(@RequestBody EncryptionModel encryptionModel) throws Exception {
		 try {
			 logger.info("Entering updateLoggedInUserFlagDetails method");
		 		logger.debug("encryptionModel:"+encryptionModel.getEncryptedPayload());
		 		
		 		// Decryption technique
		 		JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
				return mphUserService.updateLoggedInUserFlagDetails(plainJSONObject.getString("userName"), plainJSONObject.getString("loginFlag"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new MphUserServiceException ("Internal Server Error");
			}
		}
	 
	 @PostMapping(value = "/usermgmt/checkSuperAdminRoleBySrNo")
	 public ResponseEntity<Object> checkSuperAdminRoleBySrNo(@RequestBody EncryptionModel encryptionModel) throws Exception {
		 try {
			 logger.info("Entering checkSuperAdminRoleBySrNo method");
		 		logger.debug("encryptionModel:"+encryptionModel.getEncryptedPayload());
		 		Map<String, Object> response = new HashMap<String, Object>();
			   	Map<String, Object> response1 = new HashMap<String, Object>();
			   	ObjectMapper Obj = new ObjectMapper();
		 		// Decryption technique
		 		JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
		 		 
		 			List<MasterRoleModal> mstResponse = userRoleTypeMappingService.checkAdminRoleBySrNo(plainJSONObject.getString("srNumber"));     

		 			if (mstResponse == null || mstResponse.isEmpty())
		 			{                 
		 				response.put(Constant.STATUS, 201);                
		 				response.put(Constant.MESSAGE, Constant.FAILED);    
		 				response.put("Data", Constant.NO_DATA_FOUND);
		 				
		 				String jsonStr = Obj.writeValueAsString(response);
		 				  //ENcryption Technique
		 				 	   String encaccessResponse = EncryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
		 				response1.put(Constant.STATUS, 200);                 
		 				response1.put(Constant.MESSAGE, Constant.SUCCESS);                
		 				response1.put("body", encaccessResponse); 
		 			} 
		 			else 
		 			{                 
		 				response.put(Constant.STATUS, 200);                 
		 				response.put(Constant.MESSAGE, Constant.SUCCESS);                
		 				response.put("Data", mstResponse); 
		 				
		 				String jsonStr = Obj.writeValueAsString(response);
		 				  //ENcryption Technique
		 				String encaccessResponse = EncryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
		 				response1.put(Constant.STATUS, 200);                 
		 				response1.put(Constant.MESSAGE, Constant.SUCCESS);                
		 				response1.put("body", encaccessResponse);
		 			}
		 			return new ResponseEntity<Object>(response1, HttpStatus.OK);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new UserRoleMappingException ("Internal Server Error");
			}
		}
	 
	 @PostMapping(value = "/usermgmt/getUserDetailsInSuperAdmin")
	 public ResponseEntity<Object> getUserDetailsInSuperAdmin(@RequestBody EncryptionModel encryptionModel) throws Exception {
		 try {
			 logger.info("Entering getUserDetailsInSuperAdmin method");
		 		logger.debug("encryptionModel:"+encryptionModel.getEncryptedPayload());
		 		// Decryption technique
		 		JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
		 		 
		 		return mphUserService.getUserDetailsInSuperAdmin(plainJSONObject.getString("userName"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new MphUserServiceException ("Internal Server Error");
			}
		}
	 
	 @PostMapping(value = "/usermgmt/getUserHistoryDetails")
		public ResponseEntity<Object> getUserHistoryDetails(@RequestBody EncryptionModel encryptionModel)
				throws UserManagementException{
				
			try {
				JSONObject plainJSONObject = EncryptandDecryptAES
						.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
				Date today = new Date();
				UserDetailHistoryEntity userHistoryEntity = new UserDetailHistoryEntity();
//				Date Startdate = new SimpleDateFormat("yyyy-MM-dd").parse(plainJSONObject.getString("startDate"));
//				Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(plainJSONObject.getString("endDate"));
				String srnumber=plainJSONObject.getString("srnumber").toLowerCase();
				String fromDate=plainJSONObject.getString("fromDate").toLowerCase();
				String toDate=plainJSONObject.getString("toDate").toLowerCase();
				String loggedInUserUnitCode = plainJSONObject.getString("loggedInUserUnitCode");
				
				
				return  userDetailHistoryServiceImpl.getUserDetailsHistoryBasedOnUsername(srnumber,fromDate, toDate, loggedInUserUnitCode);   
				
			}catch(Exception e) {
				logger.error(" add Mph User admin exception occured." + e.getMessage());
				throw new UserManagementException("Internal Server Error");
			}
		}
	 
	 @PostMapping(value = "/usermgmt/sendEmails")
		public ResponseEntity<Object> sendEmail( @RequestBody EncryptionModel encryptionModel) throws MphUserServiceException {
			logger.debug("SuperAdminController : sendEmail : initiated");
			HashMap<String, Object> response = new HashMap<String, Object>();
			Map<String, Object> response1 = new HashMap<String, Object>();
			EmailModel emailModel=new EmailModel();
		    try {
		    	JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
		    	String userName = plainJSONObject.getString("username");
				String source = plainJSONObject.getString("source");
				String logggedInUserUserName = plainJSONObject.getString("logggedInUserUserName");
				String email = plainJSONObject.getString("email");
				emailModel.setUsername(userName);
				emailModel.setSource(source);
				emailModel.setLogggedInUserUserName(logggedInUserUserName);
				emailModel.setEmail(email);
				ResponseEntity<Object> resEntity= userManagementService.sendEmail(emailModel);	
		    	ObjectMapper Obj = new ObjectMapper();
				String responseJsonStr = Obj.writeValueAsString(resEntity);
				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(responseJsonStr);
				response1.put(Constant.STATUS, 200);
				response1.put("body", encaccessResponse);
				return new ResponseEntity<Object>(response1, HttpStatus.OK);
		    }
		    catch(Exception e) {
		    	logger.error(" add Mph User admin exception occured." + e.getMessage());
		    	e.printStackTrace();
		    }
			return null;
		     
		    
		}


	 @PostMapping(value = "/usermgmt/sendSMSs")

	 		public ResponseEntity<Object> sendSMS(@RequestBody EncryptionModel encryptionModel) throws MphUserServiceException {
		 logger.debug("SuperAdminController : sendEmail : initiated");
			HashMap<String, Object> response = new HashMap<String, Object>();
			Map<String, Object> response1 = new HashMap<String, Object>();
			SMSModel smsModel=new SMSModel();
                    try {
                    	JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
                    	String userName = plainJSONObject.getString("username");
        				String source = plainJSONObject.getString("source");
        				String logggedInUserUserName = plainJSONObject.getString("logggedInUserUserName");
        				String mobile = plainJSONObject.getString("mobile");
        				smsModel.setUsername(userName);
        				smsModel.setSource(source);
        				smsModel.setLogggedInUserUserName(logggedInUserUserName);
        				smsModel.setMobile(mobile);
        				ResponseEntity<Object> resEntity= userManagementService.sendSMS(smsModel);
        				ObjectMapper Obj = new ObjectMapper();
        				String responseJsonStr = Obj.writeValueAsString(resEntity);
        				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(responseJsonStr);
        				response1.put(Constant.STATUS, 200);
        				response1.put("body", encaccessResponse);
        				return new ResponseEntity<Object>(response1, HttpStatus.OK);
                    }catch(Exception e) {
                    	logger.error(" add Mph User admin exception occured." + e.getMessage());
        		    	e.printStackTrace();
                    }
					return null;
	 	}
	 
	 
	 @PostMapping(value = "/usermgmt/sendEmailAndSmsToMPHUser")
		public ResponseEntity<Object> sendEmailAndSmsToBulkUser(@RequestBody EncryptionModel encryptionModel) throws MphUserServiceException {
		   try {
		 JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
		 return  userManagementService.sendEmailAndSmsToBulkUser(plainJSONObject.getJSONArray("userArray"));
		   }catch(Exception e) {
			   logger.error("Could not send Email and SMS :"+e.getMessage());
		   }
		return null;
					
		}
	 
	 @PostMapping(value = "/usermgmt/checkPolicyAvailability")
		public ResponseEntity<Object> checkPolicyAvailability(@RequestBody EncryptionModel encryptionModel) throws MphUserServiceException {

			try {
				JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(encryptionModel.getEncryptedPayload());
				return  mphUserService.checkPolicyAvailability(plainJSONObject.getString("policyNumber"),plainJSONObject.getString("loggedInUserUnitCode"));
			} catch (Exception e) {
				logger.error(" insertUserDataAtLoginTime exception occured." + e.getMessage());
				throw new MphUserServiceException ("Internal Server Error");
			}
		}
	 
}
