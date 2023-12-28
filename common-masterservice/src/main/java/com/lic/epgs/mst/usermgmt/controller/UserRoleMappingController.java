package com.lic.epgs.mst.usermgmt.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.modal.UnitMasterModel;
import com.lic.epgs.mst.modal.UserHistoryInputModel;
import com.lic.epgs.mst.usermgmt.entity.AppModuleRoleTypeEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterApplicationModule;
import com.lic.epgs.mst.usermgmt.entity.MasterAuditDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRolesDisplayRolesMappingEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersLoginDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.ModuleDescriptionRoleTypeEntity;
import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.UOAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleMappingEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleTypeMappingEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserRoleMappingException;
import com.lic.epgs.mst.usermgmt.modal.AppRoleTypeModel;
import com.lic.epgs.mst.usermgmt.modal.HistoryDetailsModel;
import com.lic.epgs.mst.usermgmt.modal.LocationModel;
import com.lic.epgs.mst.usermgmt.modal.LoggedInUserModel;
import com.lic.epgs.mst.usermgmt.modal.MandhanLocationModel;
import com.lic.epgs.mst.usermgmt.modal.MasterRoleModal;
import com.lic.epgs.mst.usermgmt.modal.RoleTypeModel;
import com.lic.epgs.mst.usermgmt.modal.UoZoAdminModel;
import com.lic.epgs.mst.usermgmt.modal.UpdateLoginDetailsModel;
import com.lic.epgs.mst.usermgmt.service.UserRoleMappingService;
import com.lic.epgs.mst.usermgmt.service.UserRoleTypeMappingService;

@CrossOrigin("*")
@RestController
public class UserRoleMappingController {

	private static final Logger logger = LoggerFactory.getLogger(UserRoleMappingController.class);

	@Autowired
	private UserRoleMappingService userRoleMappingService;
	
	@Autowired
	private UserRoleTypeMappingService userRoleTypeService;

	String className = this.getClass().getSimpleName();
	
	 @PostMapping(value = "/usermgmt/addUserType")
	public ResponseEntity<Map<String, Object>> addUserType(@Valid @RequestBody UserRoleTypeMappingEntity userRoleTypeEntity)
			throws UserRoleMappingException {

		try {
			return userRoleTypeService.addUserType(userRoleTypeEntity);
		} catch (Exception e) {
			logger.error("Exception" + e.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
		}
	}
	  
	 @PostMapping(value = "/usermgmt/saveLoginDetails")
		public ResponseEntity<Map<String, Object>> saveLoginDetails(@Valid @RequestBody MasterUsersLoginDetailsEntity masterUsersLoginDetailsEntity)
				throws UserRoleMappingException {

			try {
				return userRoleTypeService.saveLoginDetails(masterUsersLoginDetailsEntity);
			} catch (Exception e) {
				logger.error("Exception" + e.getMessage());
				throw new UserRoleMappingException ("Internal Server Error");
			}
		}
	 
	 @PostMapping(value = "/usermgmt/saveAuditDetails")
		public ResponseEntity<Map<String, Object>> saveActionDetailsForAudit(@Valid @RequestBody MasterAuditDetailsEntity masterAuditDetailsEntity)
				throws UserRoleMappingException {

			try {
				return userRoleTypeService.saveActionDetailsForAudit(masterAuditDetailsEntity);
			} catch (Exception e) {
				logger.error("Exception" + e.getMessage());
				throw new UserRoleMappingException ("Internal Server Error");
			}
		}
	 
	 
	 @PostMapping(value = "/usermgmt/updateLoginDetails")
	 public ResponseEntity<Map<String, Object>> updateLoginDetails(@Valid @RequestBody UpdateLoginDetailsModel updateLoginDetailsModel) throws Exception {
		 try {
				return userRoleTypeService.updateLoginDetails(updateLoginDetailsModel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new UserRoleMappingException ("Internal Server Error");
			}
		}
	 
	 @GetMapping(value = "/usermgmt/getLoginDetails/{username}")
	 public ResponseEntity<Map<String, Object>> getLoginDetails(@Valid @PathVariable ("username") String username) throws Exception {
		 try {
				return userRoleTypeService.getLoginDetails(username);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new UserRoleMappingException ("Internal Server Error");
			}
		}
	 
	 @PostMapping(value = "/usermgmt/addUserBasedOnUserName")
		public ResponseEntity<Map<String, Object>> addUsersBasedOnUserName(@RequestParam("appmoduleId") String appmoduleId,@RequestParam("roleTypeId") String roleTypeId,@RequestParam("masterUserId") String masterUserId,@RequestParam("locationType") String locationType )
				throws UserRoleMappingException {

			try {
				return userRoleTypeService.addUserBasedOnUserName(appmoduleId, roleTypeId, masterUserId, locationType);
			} catch (Exception e) {
				logger.error("Exception" + e.getMessage());
				throw new UserRoleMappingException ("Internal Server Error");
			}
		}
		
	
	 @DeleteMapping(value = "/usermgmt/deleteuserRoleTypeMapping/{userRoleTypeMappingId}")
	public ResponseEntity<Map<String, Object>> deleteuserRoleTypeMapping(@PathVariable("userRoleTypeMappingId") Long userRoleTypeMappingId)
	throws UserRoleMappingException {

	return userRoleTypeService.deleteuserRoleTypeMapping(userRoleTypeMappingId);

	}
	
	@PostMapping(("/usermgmt/getUserRoleTypeSearch"))
	ResponseEntity<Map<String, Object>> getUserRoleTypesearch(@RequestBody(required = false) UserRoleTypeMappingEntity mstUserEntity) throws UserRoleMappingException 
	{

		logger.info("Method Start--UserRoleTypesearch--");

		logger.info("MASTER_USER_ID:" + mstUserEntity.getMasterUserId());

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			
			List<AppRoleTypeModel> mstResponse = userRoleTypeService.userRoleTypeSearch(mstUserEntity.getMasterUserId());
			logger.info("UserRoleMapping.UserRoleTypesearch::" + mstResponse);
			if (mstResponse == null || mstResponse.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", mstResponse);
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
			
		}
		
	}
	
	
	
	@GetMapping("/usermgmt/getappRoleTypeByappModule/{appModuleId}/{locationType}")
	public ResponseEntity<Map<String, Object>> getappRoleTypeByappModule(@PathVariable Long appModuleId,@PathVariable String locationType) throws UserRoleMappingException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Start");
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			List<MasterRolesDisplayRolesMappingEntity> mstResponse = userRoleTypeService.getUserRoleTypeByAppModuleId(appModuleId, locationType);
			if (mstResponse == null || mstResponse.isEmpty())
			{                 
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);             }


			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
		}
	}
	
	
	@GetMapping("/usermgmt/getAllUserRole")
	public ResponseEntity<Map<String, Object>> getAllUserRole() throws UserRoleMappingException {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<UserRoleMappingEntity> userrole = userRoleMappingService.getAllUserRole();

			if (userrole.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", userrole);
			}

			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
		}
		
	}

	@GetMapping("/usermgmt/userRole/{id}")
	public ResponseEntity<Map<String, Object>> getUserRoleByMasterUserId(@PathVariable long masterUserId)
			throws UserRoleMappingException {
		logger.info("Method Start--getUserRoleByMasterUserId--");

		logger.info("masterUserId::" + masterUserId);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		UserRoleMappingEntity userRoleMapping;
		try {
			userRoleMapping = userRoleMappingService.getUserRoleByMasterUserId(masterUserId);
		
		logger.info("UserRoleMapping.getUserRoleByMasterUserId::" + userRoleMapping.getUserRoleMappingId());

			if (userRoleMapping == null) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", userRoleMapping);
			}

			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
		}
		
	}
	
	
	@GetMapping("/usermgmt/userRole/{srNumber}")
	public ResponseEntity<Map<String, Object>> getUserRoleBySrNumber(@PathVariable long srNumber) throws UserRoleMappingException {
		logger.info("Method Start--getUserRoleByMasterUserId--");

		logger.info("srNumber::" + srNumber);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		

		try {
			UserRoleMappingEntity userRoleMapping = userRoleMappingService.getUserRoleBySrNumber(srNumber);
			logger.info("UserRoleMapping.getUserRoleBySrNumber::" + userRoleMapping.getUserRoleMappingId());
			if (userRoleMapping == null) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", userRoleMapping);
			}

			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
		}
		
	}

	@PostMapping(("/usermgmt/getZOAdminSearch"))
	 ResponseEntity<Map<String, Object>> getZOAdminSearch(@RequestBody(required = false) MasterUsersEntity mstUserEntity) throws SQLException {

		logger.info("Method Start--getUsersWithCriteria--");

		logger.info("LOCATION, SRNUMBER, USERNAME:" + mstUserEntity.getLocation() + " " + mstUserEntity.getSrNumber() + " "
				+ mstUserEntity.getUserName());

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			List<UoZoAdminModel> mstResponse = userRoleMappingService.zoSearch(mstUserEntity.getLocation(),
					mstUserEntity.getSrNumber(), mstUserEntity.getUserName());
			logger.info("UserRoleMapping.getUserRoleBySrNumber::" + mstResponse);
			if (mstResponse == null || mstResponse.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", mstResponse);
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new SQLException ("Error in query");
		}
		
	}

	@PostMapping(("/usermgmt/getUOAdminSearch"))
	ResponseEntity<Map<String, Object>> getUOAdminSearch(
			@RequestBody(required = false) MasterUsersEntity mstUserEntity) throws SQLException {
		{

			logger.info("Method Start--getUsersWithCriteria2--");
		}

		logger.info("LOCATION, SRNUMBER, USERNAME:" + mstUserEntity.getLocation() + " " + mstUserEntity.getLocationType() + " " + mstUserEntity.getSrNumber() + " "
				+ mstUserEntity.getUserName());

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			
			List<UoZoAdminModel> mstResponse = userRoleMappingService.uoSearch(mstUserEntity.getLocationType(),mstUserEntity.getLocation(),
					mstUserEntity.getSrNumber(), mstUserEntity.getUserName());
			logger.info("UserRoleMapping.getUserRoleBySrNumber::" + mstResponse);
			if (mstResponse == null || mstResponse.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", mstResponse);
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new SQLException ("Error in query");
		}
		
	}
	
	@RequestMapping(value = "/usermgmt/getMasterRoleTypeSearch", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getMasterRoleTypeSearch(
			@RequestBody RoleTypeModel roleTypeModel) throws UserRoleMappingException  {

		logger.info("In delete Claimant service.");
		Map<String, Object> response = new HashMap<String, Object>();
		List<RoleTypeModel> memberDetailsView = new ArrayList<RoleTypeModel>();

		if (roleTypeModel != null) {
			try {
				memberDetailsView = userRoleTypeService.masterRoleTypeSearch(roleTypeModel.getAppModuleId(),roleTypeModel.getDisplayRoleType(),roleTypeModel.getLocationType());
			} catch (SQLException ex) {
				logger.info("Exception : " + ex.getMessage());
				throw new UserRoleMappingException ("Internal Server Error");
			}

			if (memberDetailsView != null) {

				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("memberDetails", memberDetailsView);
				return ResponseEntity.accepted().body(response);

			}

			else {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.FAILED);
				return ResponseEntity.accepted().body(response);
			}
		} else {
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.FAILED);
			return ResponseEntity.accepted().body(response);
		}

	}


	/*@PostMapping(("/usermgmt/checkAdminRoleBySrNo"))    
	public ResponseEntity<Map<String,Object>> checkAdminRoleBySrNo(@RequestBody(required = false) MasterUsersEntity mstUserEntity)             
			throws UserRoleMappingException {         


		logger.info("Method Start--checkAdminRoleBySrNo--");

		Map<String, Object> response = new HashMap<String, Object>();             
		response.put(Constant.STATUS, 0);             
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		try {
			List<MasterRoleModal> mstResponse = userRoleMappingService.checkAdminRoleBySrNo(mstUserEntity.getSrNumber());           
			logger.info("UserRoleMapping.checkAdminRoleBySrNo::" + mstResponse);

			if (mstResponse == null || mstResponse.isEmpty())
			{                 
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);             }


			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
		}
		
	}*/

	
//	@PostMapping(("/usermgmt/getRoleTypeByEmail"))    /usermgmt/checkAdminRoleBySrNo
	@PostMapping(("/usermgmt/checkAdminRoleBySrNo"))   
	public ResponseEntity<Map<String,Object>> checkAdminRoleBySrNo(@RequestBody(required = false) MasterUsersEntity mstUserEntity)             
			throws UserRoleMappingException {         
		logger.info("Method Start--checkRoleTypeByemail--");

		Map<String, Object> response = new HashMap<String, Object>();             
		response.put(Constant.STATUS, 0);             
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		try {
			List<MasterRoleModal> mstResponse = userRoleTypeService.checkAdminRoleBySrNo(mstUserEntity.getSrNumber());     
			logger.info("SRNUMBER::" + mstUserEntity.getSrNumber());
//			List<MasterRoleModal> mstResponse = userRoleMappingService.checkAdminRoleBySrNo(mstUserEntity.getSrNumber());
			
			logger.info("UserRoleMapping.checkRoleTypeByemail::" + mstResponse);

			if (mstResponse == null || mstResponse.isEmpty())
			{                 
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);             }


			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
		}
		
	}
	
	@PostMapping(("/usermgmt/checkAdminRoleBySrNoForAccounting"))   
	public ResponseEntity<Map<String,Object>> checkAdminRoleBySrNoForAccounting(@RequestBody(required = false) MasterUsersEntity mstUserEntity)             
			throws UserRoleMappingException {         
		logger.info("Method Start--checkRoleTypeByemail--");

		Map<String, Object> response = new HashMap<String, Object>();             
		response.put(Constant.STATUS, 0);             
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		try {
			List<MasterRoleModal> mstResponse = userRoleTypeService.checkAdminRoleBySrNoForAccounting(mstUserEntity.getSrNumber());     
			logger.info("SRNUMBER::" + mstUserEntity.getSrNumber());
//			List<MasterRoleModal> mstResponse = userRoleMappingService.checkAdminRoleBySrNo(mstUserEntity.getSrNumber());
			
			logger.info("UserRoleMapping.checkRoleTypeByemail::" + mstResponse);

			if (mstResponse == null || mstResponse.isEmpty())
			{                 
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);             }


			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
		}
		
	}
	
	
	
	@PostMapping(("/usermgmt/getRoleTypeByEmail"))   
	public ResponseEntity<Map<String,Object>> getRoleTypeByEmail(@RequestBody(required = false) MasterUsersEntity mstUserEntity)             
			throws UserRoleMappingException {         
		logger.info("Method Start--checkRoleTypeByemail--");

		Map<String, Object> response = new HashMap<String, Object>();             
		response.put(Constant.STATUS, 0);             
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		try {
			List<MasterRoleModal> mstResponse = userRoleTypeService.getRoleTypeByEmail(mstUserEntity.getEmail());     
			logger.info("SRNUMBER::" + mstUserEntity.getEmail());
//			List<MasterRoleModal> mstResponse = userRoleMappingService.checkAdminRoleBySrNo(mstUserEntity.getSrNumber());
			
			logger.info("UserRoleMapping.checkRoleTypeByemail::" + mstResponse);

			if (mstResponse == null || mstResponse.isEmpty())
			{                 
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);             }


			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
		}
		
	}

	
	
	
	@PostMapping(("/usermgmt/checkAdminRoleBySrNoForSwalamban"))    
	public ResponseEntity<Map<String,Object>> checkAdminRoleBySrNoForSwalamban(@RequestBody(required = false) MasterUsersEntity mstUserEntity)             
			throws UserRoleMappingException {         


		logger.info("Method Start--checkAdminRoleBySrNoForSwalamban--");

		Map<String, Object> response = new HashMap<String, Object>();             
		response.put(Constant.STATUS, 0);             
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		try {
			List<MasterRoleModal> mstResponse = userRoleTypeService.checkAdminRoleBySrNo(mstUserEntity.getSrNumber());           
			logger.info("UserRoleMapping.checkAdminRoleBySrNo::" + mstResponse);
			
			List<LocationModel> swalambanRes = userRoleMappingService.checkAdminRoleBySrNoForSwalamban(mstUserEntity.getSrNumber());       

			if (mstResponse == null || mstResponse.isEmpty())
			{                 
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			  else
		 	 {                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("SwalambanMenuData", swalambanRes);   
				response.put("Data", mstResponse);          
				}


			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
		}
		
	}

	
	 @GetMapping("/usermgmt/checkAdminRoleByEmail/{email}")
	public ResponseEntity<Map<String, Object>> checkAdminRoleByEmail(@PathVariable String email) throws UserRoleMappingException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Start");
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			List<MasterRoleModal> mstResponse = userRoleMappingService.checkAdminRoleByEmail(email);
			if (mstResponse == null || mstResponse.isEmpty())
			{                 
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);             }


			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
		}
	}
	
	   @PostMapping(("/usermgmt/getAdminRoleByEmailForSwalamban"))    
	public ResponseEntity<Map<String,Object>> getAdminRoleByEmailForSwalamban(@RequestBody(required = false) MasterUsersEntity mstUserEntity)             
			throws UserRoleMappingException {         


		logger.info("Method Start--checkAdminRoleBySrNoForSwalamban--");

		Map<String, Object> response = new HashMap<String, Object>();             
		response.put(Constant.STATUS, 0);             
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		 try {
			List<MasterRoleModal> mstResponse = userRoleTypeService.getRoleTypeByEmail(mstUserEntity.getEmail());           
			logger.info("UserRoleMapping.checkAdminRoleBySrNo::" + mstResponse);
			
			List<LocationModel> swalambanRes = userRoleTypeService.checkAdminRoleByEmailForSwalamban(mstUserEntity.getEmail());       

			if (mstResponse == null || mstResponse.isEmpty())
			{                 
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);   
				response.put("SwalambanMenuData", swalambanRes);   
				response.put("Data", mstResponse);             }


			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
		}
		
	}

	
	@GetMapping("/usermgmt/checkActiveStatusBySrNumber/{srNumber}")
	public ResponseEntity<Map<String, Object>> checkActiveStatusBySrNumber(@PathVariable String srNumber) throws UserRoleMappingException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Start");
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			MasterUsersEntity mstUserResponse = userRoleMappingService.checkUserNameActiveStatus(srNumber);
			if (mstUserResponse == null )
			{                 
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.USERNAME_NOT_FOUND);             
			} 
			  else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.ALLOWED);                
				response.put("Data", mstUserResponse);             }

				return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new UserRoleMappingException ("Internal Server Error");
		}
		//return null;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, Object> response = new HashMap<>();
	    if(ex.getBindingResult().getAllErrors() != null) {
	    	response.put(Constant.STATUS, 12);
	    	response.put(Constant.MESSAGE, Constant.INVALID_PAYLOAD);
	    }
	    return response;
	}





@PostMapping(("/usermgmt/checkAdminRoleBySrNoForMandhan"))    
public ResponseEntity<Map<String,Object>> checkAdminRoleBySrNoForMandhan(@RequestBody(required = false) MasterUsersEntity mstUserEntity)             
		throws UserRoleMappingException {         


	logger.info("Method Start--checkAdminRoleBySrNoForMandhan--");
	Map<String, Object> response = new HashMap<String, Object>();             
	response.put(Constant.STATUS, 0);             
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	try {
		List<MasterRoleModal> mstResponse = userRoleTypeService.checkAdminRoleBySrNo(mstUserEntity.getSrNumber());           
		logger.info("UserRoleMapping.checkAdminRoleBySrNo::" + mstResponse);
		
		List<MandhanLocationModel> mandhanRes = userRoleMappingService.checkAdminRoleBySrNoForMandhan(mstUserEntity.getSrNumber());       

		if (mstResponse == null || mstResponse.isEmpty())
		{                 
			response.put(Constant.STATUS, 10);                
			response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
		} 
		  else
	 	 {                 
			response.put(Constant.STATUS, 1);                 
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put("MandhanMenuData", mandhanRes);   
			response.put("Data", mstResponse);          
			}


		return ResponseEntity.ok().body(response);
	} catch (Exception ex) {
		logger.info("Exception : " + ex.getMessage());
		throw new UserRoleMappingException ("Internal Server Error");
	}
	//return null;
}



@PostMapping(("/usermgmt/getAllUoForZoLocation"))
ResponseEntity<Map<String, Object>> getAllUoForZoLocation(
		@RequestBody(required = false) MasterUsersEntity mstUserEntity) throws SQLException {
	{

		logger.info("Method Start--getAllUoForZoLocation--");
	}

	Map<String, Object> response = new HashMap<String, Object>();
	response.put(Constant.STATUS, 0);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

	try {
		
		List<UnitMasterModel> mstResponse = userRoleTypeService.getAllUoForZoLocation(mstUserEntity.getLocationType(),mstUserEntity.getLocationCode());
		logger.info("UserRoleMapping.getAllUoForZoLocation::" + mstResponse);
		//logger.info("UserRoleMapping.getAllUoForZoLocation::" + mstUserEntity.getLocationType()+""mstUserEntity.getLocationCode());
		if (mstResponse == null || mstResponse.isEmpty()) {
			response.put(Constant.STATUS, 10);
			response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
		} else {
			response.put(Constant.STATUS, 1);
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put("Data", mstResponse);
		}

		return ResponseEntity.ok().body(response);
	} catch (Exception ex) {
		logger.info("Exception : " + ex.getMessage());
		throw new SQLException ("Error in query");
	}
	
}

@PostMapping("/usermgmt/getAllHistoryDetails")
public ResponseEntity<Map<String, Object>> getAllHistoryDetails(@RequestBody(required = false) UserHistoryInputModel userHistoryInputModel) throws Exception {

	Map<String, Object> response = new HashMap<String, Object>();
	response.put(Constant.STATUS, 0);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	LoggingUtil.logInfo(className, methodName, "Started");

	try {
		List<HistoryDetailsModel> getHistoryDetails = userRoleTypeService.getHistoryDetails(userHistoryInputModel);

		if (getHistoryDetails.isEmpty() || getHistoryDetails == null) {
			response.put(Constant.STATUS, 201);
			response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
		} else {
			response.put(Constant.STATUS, 200);
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put("Data", getHistoryDetails);
		}
		return ResponseEntity.accepted().body(response);
	} catch (Exception ex) {
		logger.error(" getAllHistoryDetails exception occured." + ex.getMessage());
	}
	return null;
}

@GetMapping("/usermgmt/getMasterUserIdByUserName/{username}")
public ResponseEntity<Map<String, Object>> getMasterUserIdByUserName(@PathVariable String username)  throws UserRoleMappingException{
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	LoggingUtil.logInfo(className, methodName, "Start");
	
	Map<String, Object> response = new HashMap<String, Object>();
	response.put(Constant.STATUS, 0);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

	try {
		PortalMasterEntity mstUserResponse = userRoleMappingService.getMasterUserIdByUserName(username);
		if (mstUserResponse == null )
		{                 
			response.put(Constant.STATUS, 10);                
			response.put(Constant.MESSAGE, Constant.USERNAME_NOT_FOUND);             
		} 
		  else 
		{                 
			response.put(Constant.STATUS, 1);                 
			response.put(Constant.MESSAGE, Constant.SUCCESS);                
			response.put("Data", mstUserResponse);             }

			return ResponseEntity.ok().body(response);
	} catch (Exception ex) {
		logger.info("Exception : " + ex.getMessage());
		throw new UserRoleMappingException ("Internal Server Error");
	}
	
}

@GetMapping("/usermgmt/getDescriptionByRoleTypeName/{roleTypeName}")
public ResponseEntity<Map<String, Object>> getDescriptionByRoleTypeName(@PathVariable String roleTypeName)  throws UserRoleMappingException{
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	LoggingUtil.logInfo(className, methodName, "Start");
	
	Map<String, Object> response = new HashMap<String, Object>();
	response.put(Constant.STATUS, 0);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

	try {
		ModuleDescriptionRoleTypeEntity mstUserResponse = userRoleMappingService.getDescriptionByRoleTypeName(roleTypeName);
		if (mstUserResponse == null )
		{                 
			response.put(Constant.STATUS, 10);                
			response.put(Constant.MESSAGE, Constant.USERNAME_NOT_FOUND);             
		} 
		  else 
		{                 
			response.put(Constant.STATUS, 1);                 
			response.put(Constant.MESSAGE, Constant.SUCCESS);                
			response.put("Data", mstUserResponse);             }

			return ResponseEntity.ok().body(response);
	} catch (Exception ex) {
		logger.info("Exception : " + ex.getMessage());
		throw new UserRoleMappingException ("Internal Server Error");
	}
	
}
@PutMapping(value = "/usermgmt/updateUserDetailAfterLogin")
public ResponseEntity<Map<String, Object>> updateUserDetailAfterLogin(@Valid @RequestBody PortalMasterEntity portalMasterEntity)
        throws UserRoleMappingException {

    try {
		return userRoleMappingService.updateUserDetailAfterLogin(portalMasterEntity);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		throw new UserRoleMappingException ("Internal Server Error");
	}
}

}
