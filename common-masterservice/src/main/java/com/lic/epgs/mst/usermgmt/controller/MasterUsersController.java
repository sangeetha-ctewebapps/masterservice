package com.lic.epgs.mst.usermgmt.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONArray;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.entity.CoOffice;
import com.lic.epgs.mst.entity.SatelliteOffice;
import com.lic.epgs.mst.entity.UnitOffice;
import com.lic.epgs.mst.entity.ZonalEntity;
import com.lic.epgs.mst.entity.ZonalOffice;
import com.lic.epgs.mst.exceptionhandling.UnitCodeServiceException;
import com.lic.epgs.mst.modal.COModel;
import com.lic.epgs.mst.modal.CoOfficeModel;
import com.lic.epgs.mst.modal.OfficeCodeModel;
import com.lic.epgs.mst.modal.UOModel;
import com.lic.epgs.mst.modal.UnitModel;
import com.lic.epgs.mst.modal.UsersModal;
import com.lic.epgs.mst.modal.ZOModel;
import com.lic.epgs.mst.repository.CoOfficeRepository;
import com.lic.epgs.mst.repository.SatelliteOfficeRepository;
import com.lic.epgs.mst.repository.UnitOfficeRepository;
import com.lic.epgs.mst.repository.ZonalEntityRepository;
import com.lic.epgs.mst.repository.ZonalOfficeRepository;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptandDecryptAES;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptionModel;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserRoleMappingException;
import com.lic.epgs.mst.usermgmt.modal.AssignRolesModel;
import com.lic.epgs.mst.usermgmt.modal.AssignRolesRemarkModel;
import com.lic.epgs.mst.usermgmt.modal.LoggedInUserModel;
import com.lic.epgs.mst.usermgmt.modal.MasterUnitForGC;
import com.lic.epgs.mst.usermgmt.modal.SrnumberUsernameModel;
import com.lic.epgs.mst.usermgmt.modal.UserRoleModel;
import com.lic.epgs.mst.usermgmt.modal.getAllUserRoleDetailsModel;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersRepository;
import com.lic.epgs.mst.usermgmt.repository.SoAdminRepository;
import com.lic.epgs.mst.usermgmt.service.MasterUsersService;
import com.lic.epgs.mst.usermgmt.service.RedhatUserGenerationService;
import com.lic.epgs.rhssomodel.UpdateUserModel;
import com.lic.epgs.rhssomodel.UserDetailsResponse;
import com.lic.epgs.rhssomodel.UserResponseModel;

@RestController
@CrossOrigin("*")
public class MasterUsersController {
	
	
	@Autowired
	MasterUsersService masterUsersService;
	
	@Autowired
	SatelliteOfficeRepository satelliteOfficeRepository;
	
	@Autowired
	MasterUsersRepository masterUserRepository;
	
	@Autowired
	ZonalOfficeRepository zonalOfficeRepository;
	
	@Autowired
	private CoOfficeRepository  coOfficeRepository;
	
	@Autowired
	private UnitOfficeRepository unitOfficeRepository;
	
	@Autowired
	private EncryptandDecryptAES encryptandDecryptAES;
	
	
	
	@Autowired
	private RedhatUserGenerationService redhatUserGenerationService;
	
	@Autowired
	ZonalEntityRepository zonalEntityRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(MasterUsersController.class);

	/*
	 * Description: This function is used for getting data from MASTER USERS Module by using locationId
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
  
	@PutMapping(value = "/auth/disableUserInRhsso")
	public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token,String realms,String userid,UpdateUserModel updateUserModel) {
		logger.info("SSOController : disable user : initiated");
		//logger.info("encryptionModel::" + encryptionModel.getEncryptedPayload())  
		ResponseEntity<Object> rm = redhatUserGenerationService.updateUser(token, realms, userid, updateUserModel);
		return new ResponseEntity<Object>(rm, HttpStatus.OK);
	}
	
	
	@PutMapping(value = "/auth/enableUserInRhsso")
	public ResponseEntity<?> enableUser(@RequestHeader("Authorization") String token,String realms,String userid,UpdateUserModel updateUserModel) {
		logger.info("SSOController : disable user : initiated");
		//logger.info("encryptionModel::" + encryptionModel.getEncryptedPayload())  
		ResponseEntity<Object> rm = redhatUserGenerationService.updateUser(token, realms, userid, updateUserModel);
		return new ResponseEntity<Object>(rm, HttpStatus.OK);
	}
	/*@GetMapping(value = "/auth/disableUser/{username}")
	public ResponseEntity<?> disableUser(@PathVariable("username") String username) {
		logger.info("SSOController : disableuser : initiated");
	   Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		UpdateUserModel updateUserModel = new UpdateUserModel();
		// Decryption technique
		try {
			if (username != null) {
				masterUsersService.findDisabledByUsername(username);    
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", " disabled user" + username);
			} else {
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			}
		} catch (Exception ex) {
			logger.info(" " + ex.getMessage());
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}*/
	
	
	@PutMapping(value = "/usermgmt/disableUser")
	public ResponseEntity<Map<String, Object>> disableUser(@RequestBody MasterUsersEntity masterUserEntity)
	        throws UserRoleMappingException {

	    try {
			return masterUsersService.findDisabledByUsername(masterUserEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new UserRoleMappingException ("Internal Server Error");
		}
	}
	
	@PutMapping(value = "/usermgmt/enableUser")
	public ResponseEntity<Map<String, Object>> enableUser(@RequestBody MasterUsersEntity masterUserEntity)
	        throws UserRoleMappingException {

	    try {
			return masterUsersService.findEnabledByUsername(masterUserEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new UserRoleMappingException ("Internal Server Error");
		}
	}
	
	
	@PostMapping(value = "/usermgmt/updateLoggedInUserFlag")
	 public ResponseEntity<Map<String, Object>> updateLoggedInUserFlag(@Valid @RequestBody LoggedInUserModel loggedInUserModel) throws Exception {
		 try {
				return masterUsersService.updateLoggedInUserFlag(loggedInUserModel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new UserRoleMappingException ("Internal Server Error");
			}
		}
	
	@PostMapping(value = "/usermgmt/assignRoleToUser")
	 public ResponseEntity<Map<String, Object>> assignRoleToUser(@RequestBody AssignRolesRemarkModel assignRolesRemarkModel) throws Exception {
		 try {
			 String remarks=assignRolesRemarkModel.getRemarks();
			 ArrayList<AssignRolesModel> assignRolesModel= assignRolesRemarkModel.getRolesList();
				return masterUsersService.assignRolesToUser(assignRolesModel,remarks);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new UserRoleMappingException ("Internal Server Error");
			}
		}
	
	
	@GetMapping(value = "/auth/getAllUsers")
	public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String token,String realm) throws Exception {
		logger.info("SSOController : getRoleByUsername : initiated  ");
		
		UserResponseModel rrm = redhatUserGenerationService.getUsers(token, realm);

		return new ResponseEntity<Object>(rrm, HttpStatus.OK);

	}
	
	
	  @GetMapping("/usermgmt/getAllUserBasedOnSrNumber/{locationCode}")
			public ResponseEntity<Map<String, Object>> getAllUserBasedOnSrNumber(@PathVariable String locationCode)  {

				Map<String, Object> response = new HashMap<String, Object>();
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
				String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				

				try {
					List<SrnumberUsernameModel> getAllCoAdmin = masterUsersService.getAllUsersBySrNumber(locationCode);

					if (getAllCoAdmin==null) {
						response.put(Constant.STATUS, 10);
						response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
					} else {
						response.put(Constant.STATUS, 1);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", getAllCoAdmin);
					}

					return ResponseEntity.accepted().body(response);
				} catch (Exception ex) {
					logger.error(" get All Co admin  exception occured." + ex.getMessage());
				}
				return null;
			}
	
	 @GetMapping(value = "/usermgmt/getUserRoleByLocation/{locationId}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> getUserRoleByLocation(@PathVariable Long locationId) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		logger.info("Method Start--getUserRoleByLocation--");
		
		try {
			MasterUsersEntity objMasterUsersEntity = masterUsersService.getUserRoleByLocation(locationId);
			logger.info("MasterUserEntity.getUserRoleByLocation::"+objMasterUsersEntity.getMasterUserId());
			if(objMasterUsersEntity==null) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", objMasterUsersEntity);
			}
			logger.info("Method End--getUserRoleByLocation--");
			return ResponseEntity.accepted().body(response);
		}
		catch (Exception ex) {
			logger.info("Exception"+ ex.getMessage());
		}
		return null;
	}
	
	/*
	 * Description: This function is used for searching the data in MASTER USERS Module using the filters like LOCATION, SRNUMBER, USERNAME
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	/*@PostMapping(("/usermgmt/getMasterUsersSearch"))
	ResponseEntity<Map<String, Object>> getMasterUsersearch(@RequestBody(required = false) MasterUsersEntity mstUserEntity) throws Exception {

		logger.info("Method Start--masterUsersSearch--");

		logger.info("LOCATION, SRNUMBER, USERNAME:" + mstUserEntity.getLocation() + " " + mstUserEntity.getSrNumber() + " "
				+ mstUserEntity.getUserName());

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		ResponseEntity<Map<String, Object>> objMasterUsersEntity = masterUsersService.masterUserSearch (mstUserEntity.getLocation(),mstUserEntity.getLocationType(),
				mstUserEntity.getSrNumber(), mstUserEntity.getUserName(),mstUserEntity.getTypeOfUser());
		logger.info("UserRoleMapping.getUserRoleBySrNumber::" + objMasterUsersEntity);

		try {
			if (objMasterUsersEntity == null) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", objMasterUsersEntity);
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
		}
		return null;

	}*/
	
	
	
	@PostMapping(value = "/usermgmt/getMasterUsersSearch")
	public ResponseEntity<Map<String, Object>> getMasterUserSearch(@RequestBody(required = false) MasterUsersEntity mstUserEntity) throws Exception {

		return masterUsersService.masterUserSearch (mstUserEntity.getLocation(),mstUserEntity.getLocationType(),
				mstUserEntity.getSrNumber(), mstUserEntity.getUserName(),mstUserEntity.getTypeOfUser());
		//logger.info("UserRoleMapping.getUserRoleBySrNumber::" + objMasterUsersEntity);
	}
	
	
	
	
	@PostMapping(value = "/usermgmt/getMasterUsersSearchBySrNumber")
	public ResponseEntity<Map<String, Object>> getMasterUserSearchBySrNumber(@RequestBody(required = false) MasterUsersEntity mstUserEntity)  throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			String loggedInUserUsername = mstUserEntity.getLoggedInUsername(); 
			MasterUsersEntity loggedInMasterUsersEntity = masterUsersService.getUserByUserName(loggedInUserUsername);			
			List<MasterUsersEntity> mstResponse = masterUsersService.getMasterUserSearchBySrNumber(mstUserEntity.getSrNumber());
			MasterUsersEntity masterUsersEntity = mstResponse.get(0);
			
			response.put(Constant.STATUS, 10);                
			response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);  
			
			if(loggedInMasterUsersEntity != null && loggedInMasterUsersEntity.getLocationType().equalsIgnoreCase("CO")) {
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);     
			}else if(loggedInMasterUsersEntity != null && masterUsersEntity != null){
				if(loggedInMasterUsersEntity.getLocationType().equalsIgnoreCase("ZO") && masterUsersEntity.getLocationType().equalsIgnoreCase("ZO") && loggedInMasterUsersEntity.getLocationCode().equalsIgnoreCase(masterUsersEntity.getLocationCode())) {
					response.put(Constant.STATUS, 1);                 
					response.put(Constant.MESSAGE, Constant.SUCCESS);                
					response.put("Data", mstResponse);     
				}else if(loggedInMasterUsersEntity.getLocationType().equalsIgnoreCase("UO") && masterUsersEntity.getLocationType().equalsIgnoreCase("UO") && loggedInMasterUsersEntity.getLocationCode().equalsIgnoreCase(masterUsersEntity.getLocationCode())) {
					response.put(Constant.STATUS, 1);                 
					response.put(Constant.MESSAGE, Constant.SUCCESS);                
					response.put("Data", mstResponse);     
				}else if(loggedInMasterUsersEntity.getLocationType().equalsIgnoreCase("ZO") && masterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {
					ZonalEntity zoneEntity= zonalEntityRepository.getAllZoneDetailsByUnitCode(masterUsersEntity.getLocationCode());
					if(zoneEntity.getZonalCode() != null && zoneEntity.getZonalCode().equalsIgnoreCase(loggedInMasterUsersEntity.getLocationCode())) {
						response.put(Constant.STATUS, 1);                 
						response.put(Constant.MESSAGE, Constant.SUCCESS);                
						response.put("Data", mstResponse); 
					}
				}      
			}
			/*if (mstResponse == null || mstResponse.isEmpty())
			{                 	
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);           
			}*/

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
		}
		return null;
	}
	
	/*
	 * Description: This function is used for adding the data into MASTER USERS Module
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@PostMapping(value = "/usermgmt/addMasterUser")
	public ResponseEntity<Map<String, Object>> addMasterUser(@Valid @RequestBody MasterUsersEntity masterUsersEntity ) throws Exception {

		return masterUsersService.addMasterUser(masterUsersEntity);
	}
	
	/*
	 * Description: This function is used for updating the data in MASTER USERS Module
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@PutMapping(value = "/usermgmt/editMasterUser")
	public ResponseEntity<Map<String, Object>> editMasterUser(@RequestBody MasterUsersEntity masterUsersEntity) throws Exception {
		 return masterUsersService.editMasterUser(masterUsersEntity);
	}
	
	/*
	 * Description: This function is used for soft deleting the data in MASTER USERS Module by using primary key
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@DeleteMapping (value = "/usermgmt/deleteUser/{masterUserId}") 
	public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("masterUserId") Long masterUserId) throws Exception {
			return masterUsersService.deleteUser(masterUserId);
	}
	
	
	
	@GetMapping(value = "/usermgmt/getValidateSrNumber/{srNumber}/{isActiveFlag}")
	public ResponseEntity<Map<String, Object>> getValidateSrNumber(@PathVariable String srNumber,@PathVariable String isActiveFlag) throws Exception {

		return masterUsersService.getValidateSrNumber(srNumber, isActiveFlag);
	}
	
	@GetMapping(value = "/usermgmt/checkActiveSrNumber/{srNumber}")
	public ResponseEntity<Map<String, Object>> checkActiveSrNumber(@PathVariable String srNumber) throws Exception {

		return masterUsersService.checkActiveSrNumber(srNumber);
	}
	
	@GetMapping(value = "/usermgmt/getUserDetailsByEmployeeCode/{srNumber}")
	public ResponseEntity<Map<String, Object>> getUserDetailsByEmployeeCode(@PathVariable String srNumber) throws Exception {

		  return masterUsersService.getUserDetailsByEmployeeCode(srNumber);
	}
	
	/*
	 * Description: This function is used for getting data from MASTER USERS Module by using srnumber for identifying the admin presence
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@GetMapping(value = "/usermgmt/getAllAdminOfficeDetails/{userName}")
	public ResponseEntity<Map<String, Object>> getAllAdminOfficeDetails(@PathVariable("userName") String userName)  {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			List<COModel> mstResponse = masterUsersService.getAllAdminOfficeDetails(userName);
			if (mstResponse == null || mstResponse.isEmpty())
			{                 	
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);           
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
		}
		return null;
	}
	
	/*
	 * Description: This function is used for getting data from MASTER USERS Module by using emailId for identifying the users
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@GetMapping(value = "/usermgmt/getAllUsersByLocationType/{emailId}")
	public ResponseEntity<Map<String, Object>> getAllUsersByLocationType(@PathVariable("emailId") String emailId) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			List<MasterUsersEntity> mstResponse = masterUsersService.getAllUsersByLocationType(emailId);
			if (mstResponse == null || mstResponse.isEmpty())
			{                 	
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);           
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
		}
		return null;
	}
	
	@GetMapping(value = "/usermgmt/getAllUsersByParticularLocationType/{locationType}")
	public ResponseEntity<Map<String, Object>> getAllUsersByParticularLocationType(@PathVariable("locationType") String locationType) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			List<MasterUsersEntity> mstResponse = masterUsersService.getAllUsersByParticularLocationType(locationType);
			if (mstResponse == null || mstResponse.isEmpty())
			{                 	
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);           
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
		}
		return null;
	}
	
	@GetMapping(value = "/usermgmt/getAllInActiveUsersByLocationType/{locationType}")
	public ResponseEntity<Map<String, Object>> getAllInActiveUsersByLocationType(@PathVariable("locationType") String locationType) throws SQLException {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			List<MasterUsersEntity> mstResponse = masterUsersService.getAllInActiveUsersByLocationType(locationType);
			if (mstResponse == null || mstResponse.isEmpty())
			{                 	
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);           
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new SQLException ("Internal server error");
		}
	}
	
	/*
	 * Description: This function is used for getting data from MASTER USERS Module by using srNumber for getting UnitDetails
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@GetMapping(value = "/usermgmt/getAllMasterUserRoleForGC/{srNumber}")
	public ResponseEntity<Map<String, Object>> getAllMasterUserRoleForGC(@PathVariable("srNumber") String srNumber) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			List<MasterUnitForGC> mstResponse = masterUsersService.getStateDetailsForGC(srNumber);
			if (mstResponse == null || mstResponse.isEmpty())
			{                 
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);           
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
		}
		return null;
	}
	
	@PostMapping(value = "/usermgmt/getAllUserRoleDetailsBySrNumber")
	public ResponseEntity<Map<String, Object>> getAllUserRoleDetailsBySrNumber(@RequestBody getAllUserRoleDetailsModel masterUsersEntity) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			List<UserRoleModel> mstResponse = masterUsersService.getUserRoleDetails(masterUsersEntity.getSrNumber(),masterUsersEntity.getLoggedInUsername());
			if (mstResponse == null || mstResponse.isEmpty())
			{                 
				response.put(Constant.STATUS, 201);                
				response.put(Constant.MESSAGE, Constant.ERROR); 
				response.put(Constant.DATA, Constant.NO_DATA_FOUND);
			} 
			else 
			{         
				if (mstResponse!=null) {
					
					MasterUsersEntity getSrNumberDetails =  masterUserRepository.getAllMasterUserDetail(masterUsersEntity.getSrNumber());
					MasterUsersEntity getUnitDetails =  masterUserRepository.getAllMasterUserDetailByUserName(masterUsersEntity.getLoggedInUsername());
					
					String  loggedInUsername = getUnitDetails.getUserName();
               	  String userName = getSrNumberDetails.getUserName();
               	if(loggedInUsername.equalsIgnoreCase(userName))  {
               		  
               		  response.put(Constant.STATUS, 201);
							response.put(Constant.MESSAGE, Constant.FAILED);
							response.put(Constant.DATA, "Cannot assign roles as logged in user is the same as user for which roles are being assigned.");
               	  }
               	  else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("CO") && getUnitDetails.getLocationType().equalsIgnoreCase("CO") && loggedInUsername!=userName) {
					response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", mstResponse);
					}
					else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("ZO") && getUnitDetails.getLocationType().equalsIgnoreCase("CO")&& loggedInUsername!=userName) {
						response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", mstResponse);
					}
					else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("UO") && getUnitDetails.getLocationType().equalsIgnoreCase("CO")) {
						response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", mstResponse);	
					}
					else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("SO") && getUnitDetails.getLocationType().equalsIgnoreCase("CO")) {
						response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", mstResponse);	
					}
					else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("CO") && getUnitDetails.getLocationType().equalsIgnoreCase("ZO")) {
						response.put(Constant.STATUS, 201);
						response.put(Constant.DATA, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user location Type");	
					}
					
					else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("ZO") && getUnitDetails.getLocationType().equalsIgnoreCase("ZO")) {
						
						ZonalOffice zoOfficeDetails=zonalOfficeRepository.getZonalOfficeDetails(getSrNumberDetails.getLocationCode());
						ZonalOffice zoDetails=zonalOfficeRepository.getZonalOfficeDetails(getUnitDetails.getLocationCode());
						
						String srNumberUnitCode = zoOfficeDetails.getZonalCode();
						String userNameUnitCode = zoDetails.getZonalCode();
						if(getSrNumberDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode) && getUnitDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode) && loggedInUsername!=userName) {
							response.put(Constant.STATUS, 200);
							response.put(Constant.MESSAGE, Constant.SUCCESS);
							response.put("Data", mstResponse);
						}else {
							response.put(Constant.STATUS, 201);
							response.put(Constant.DATA, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user zonal code");
						}
					}
						else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("UO") && getUnitDetails.getLocationType().equalsIgnoreCase("ZO")&& loggedInUsername!=userName) {
							ZonalOffice zoOffice=zonalOfficeRepository.getZonalOfficeDetails(getUnitDetails.getLocationCode());
							List<UnitOffice>  Units=unitOfficeRepository.getUnnitsFromZonalCode(zoOffice.getZonalId());
							
							boolean unitPresent = false;
							
							 for (UnitOffice unitOffice : Units) {
								 if (unitOffice.getUnitCode().equalsIgnoreCase(getSrNumberDetails.getLocationCode())) {
									 unitPresent = true;
									 
									  } 
								 try {
								  if (unitPresent) {
			                    	  response.put(Constant.STATUS, 200);
										response.put(Constant.MESSAGE, Constant.SUCCESS);
										response.put("Data", mstResponse);
										
									}
								 else  {
			                    	  response.put(Constant.STATUS, 201);
										response.put(Constant.DATA, "This unit does not belong to the particular Zonal Unit");
			                      }
								 }catch (Exception ex) {
										logger.error(" get Unit Code search  exception occured." + ex.getMessage());
										throw new UnitCodeServiceException ("Internal server error");
									}
							 }
						}
						else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("SO") && getUnitDetails.getLocationType().equalsIgnoreCase("ZO")) {
							response.put(Constant.STATUS, 201);
							response.put(Constant.DATA, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user location Type");	
						}
						else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("CO") && getUnitDetails.getLocationType().equalsIgnoreCase("UO")) {
							response.put(Constant.STATUS, 201);
							response.put(Constant.DATA, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user location Type");	
						}
						else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("ZO") && getUnitDetails.getLocationType().equalsIgnoreCase("UO")) {
							response.put(Constant.STATUS, 201);
							response.put(Constant.DATA, "Invalid SrNumber, The srNumber which you are trying to search does not belong to logged in user location Type");	
						}
						else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("UO") && getUnitDetails.getLocationType().equalsIgnoreCase("UO")&& loggedInUsername!=userName) {
							UnitOffice unit = unitOfficeRepository.getAllUnitDetails(getSrNumberDetails.getLocationCode());	
							UnitOffice unitDetails = unitOfficeRepository.getAllUnitDetails(getUnitDetails.getLocationCode());
							
							String srNumberUnitCode = unit.getUnitCode();
							String userNameUnitCode = unitDetails.getUnitCode();
							if(getSrNumberDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode) && getUnitDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode)) {
								response.put(Constant.STATUS, 200);
								response.put(Constant.MESSAGE, Constant.SUCCESS);
								response.put("Data", mstResponse);
							}else {
								response.put(Constant.STATUS, 201);
								response.put(Constant.DATA, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user zonal code");
							}
						}else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("SO") && getUnitDetails.getLocationType().equalsIgnoreCase("UO")&& loggedInUsername!=userName) {
							SatelliteOffice satellite = satelliteOfficeRepository.findAllSateliteDetails(getSrNumberDetails.getLocationCode());
							UnitOffice unitDetails = unitOfficeRepository.getAllUnitDetails(getUnitDetails.getLocationCode());
							String srNumberUnitCode = satellite.getSatelliteCode();
							String userNameUnitCode = unitDetails.getUnitCode();
							if(getSrNumberDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode) && getUnitDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode)) {
								response.put(Constant.STATUS, 200);
								response.put(Constant.MESSAGE, Constant.SUCCESS);
								response.put("Data", mstResponse);
							}else {
								response.put(Constant.STATUS, 201);
								response.put(Constant.DATA, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user zonal code");
							}
						}else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("CO") && getUnitDetails.getLocationType().equalsIgnoreCase("SO")) {
							response.put(Constant.STATUS, 201);
							response.put(Constant.DATA, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user location Type");	
						}
						else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("ZO") && getUnitDetails.getLocationType().equalsIgnoreCase("SO")) {
							response.put(Constant.STATUS, 201);
							response.put(Constant.DATA, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user location Type");	
						}
						else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("UO") && getUnitDetails.getLocationType().equalsIgnoreCase("SO")) {
							response.put(Constant.STATUS, 201);
							response.put(Constant.DATA, "Invalid SrNumber, The srNumber which you are trying to search does not belong to logged in user location Type");	
						}else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("ZO") && getUnitDetails.getLocationType().equalsIgnoreCase("UO")&& loggedInUsername!=userName) {
							SatelliteOffice satellite = satelliteOfficeRepository.findAllSateliteDetails(getSrNumberDetails.getLocationCode());
							SatelliteOffice satelliteDetails = satelliteOfficeRepository.findAllSateliteDetails(getUnitDetails.getLocationCode());
							//UnitOffice unitDetails = unitOfficeRepository.getAllUnitDetails(getUnitDetails.getLocationCode());
							String srNumberUnitCode = satellite.getSatelliteCode();
							String userNameUnitCode = satelliteDetails.getSatelliteCode();
							if(getSrNumberDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode) && getUnitDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode)&& loggedInUsername!=userName) {
								response.put(Constant.STATUS, 200);
								response.put(Constant.MESSAGE, Constant.SUCCESS);
								response.put("Data", mstResponse);
							}else {
								response.put(Constant.STATUS, 201);
								response.put(Constant.DATA, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user zonal code");
							}
						}
						
							
					else {
						response.put(Constant.STATUS, 201);
						response.put(Constant.DATA, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user location Type");
						//response.put("Data", mstResponse);
					}
				}
			//	response.put(Constant.STATUS, 200);                 
			//	response.put(Constant.MESSAGE, Constant.SUCCESS);                
			//	response.put("Data", mstResponse);           
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Exception : " + ex.getMessage());
		}
		return null;
	}
	
	@GetMapping(value = "/usermgmt/getUnitDetails/{srNumber}")
	public ResponseEntity<Map<String, Object>> getUnitDetails(@PathVariable("userName") String userName) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			List<UnitModel> mstResponse = masterUsersService.getUnitDetails(userName);
			if (mstResponse == null || mstResponse.isEmpty())
			{                 
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);           
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
		}
		return null;
	}
	
	
	 @GetMapping(value = "/usermgmt/getZOUnitDetails/{unitCode}")
	public ResponseEntity<Map<String, Object>> getZoUnitDetails(@PathVariable("unitCode") String unitCode) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			List<ZOModel> mstResponse = masterUsersService.getZoUnitDetails(unitCode);
			if (mstResponse == null || mstResponse.isEmpty())
			{                 
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);           
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
		}
		return null;
	}

	
	/*
	 * Description: This function is used for getting data from MASTER USERS Module by using srNumber for getting AdminOffices
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@GetMapping(value = "/usermgmt/getAllAdminOffices")
    public ResponseEntity<Map<String, Object>> getAllAdminOffices(@RequestParam String userName) throws Exception {
          Map<String, Object> response = new HashMap<String, Object>();
          response.put(Constant.STATUS, 0);
          response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

          try {
                 MasterUsersEntity user = masterUserRepository.findUserByUserName(userName);
                 if(user != null) {
                        if(user.getLocationType().equals("CO")) {
                              List<CoOfficeModel> mstResponse = masterUsersService.getCoDetails(userName);
                              if (!(mstResponse == null || mstResponse.isEmpty()))  {                 
                                     response.put(Constant.STATUS, 1);                 
                                     response.put(Constant.MESSAGE, Constant.SUCCESS); 
                                     response.put("UserType", user.getLocationType());
                                    response.put("Data", mstResponse);  
                                    response.put("SrNumber", user.getSrNumber2());
                              }else {
                            	  response.put(Constant.STATUS, 10);                 
                                  response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND); 
                                  response.put("UserType", user.getLocationType());
                                  response.put("Data", null);
                                  response.put("SrNumber", user.getSrNumber2());
                              }
                       }else if(user.getLocationType().equals("ZO")) {
                              List<ZOModel> mstResponse = masterUsersService.getZoDetails(userName);
                              if (!(mstResponse == null || mstResponse.isEmpty()))  {                 
                                     response.put(Constant.STATUS, 1);                 
                                     response.put(Constant.MESSAGE, Constant.SUCCESS);
                                     response.put("UserType", user.getLocationType());
                                    response.put("Data", mstResponse); 
                                    response.put("SrNumber", user.getSrNumber2());
                              }else {
                            	  response.put(Constant.STATUS, 10);                 
                                  response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND); 
                                  response.put("UserType", user.getLocationType());
                                  response.put("Data", null);
                                  response.put("SrNumber", user.getSrNumber2());
                              }
                       }else if(user.getLocationType().equals("UO")) {
                              List<UOModel> mstResponse = masterUsersService.getUoDetails(userName);
                              if (!(mstResponse == null || mstResponse.isEmpty())) {                 
                                     response.put(Constant.STATUS, 1);                 
                                     response.put(Constant.MESSAGE, Constant.SUCCESS); 
                                     response.put("UserType", user.getLocationType());
                                    response.put("Data", mstResponse);
                                    response.put("SrNumber", user.getSrNumber2());
                                    
                              }else {
                            	  response.put(Constant.STATUS, 10);                 
                                  response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND); 
                                  response.put("UserType", user.getLocationType());
                                  response.put("Data", null);
                                  response.put("SrNumber", user.getSrNumber2());
                              }
                       }else {
                    	   response.put(Constant.STATUS, 10);                 
                           response.put(Constant.MESSAGE, Constant.NOT_FOUND); 
                           response.put("UserType", user.getLocationType() + " does not belong to UO CO ZO");
                           response.put("Data", null);
                           response.put("SrNumber", user.getSrNumber2());
                       }
                 }
                 else {
                       response.put(Constant.STATUS, 10);                
                       response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND); 
                 }
                 return ResponseEntity.ok().body(response);
          } catch (Exception ex) {
                 logger.info("Exception : " + ex.getMessage());
          }
          return null;
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
	
	/*
	 * Description: This function is used for searching the data in MASTER USERS Module using the filters like EMAILID, SRNUMBER, USERNAME
	 * Table Name- MASTER USERS
	 * Author- Nandini R
	 */
	
	@PostMapping(("/searchUserRoleMapping"))
	ResponseEntity<Map<String, Object>> getSearchByUserName(@RequestBody(required = false) getAllUserRoleDetailsModel masterUsersEntity) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		//List<MasterUsersEntity> mstResponse = masterUsersService.getSearchByUserName(masterUsersEntity.getSrNumber(),masterUsersEntity.getEmailId(),masterUsersEntity.getUserName());
		List<MasterUsersEntity> mstResponse = masterUsersService.getSearchByUserName(masterUsersEntity.getSrNumber(),masterUsersEntity.getUserName(),masterUsersEntity.getLoggedInUsername());
		//logger.info("UserRoleMapping.getUserRoleBySrNumber::" + mstResponse);

		try {
			if (mstResponse == null || mstResponse.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
		         
						 if (mstResponse!=null ) {
							
							MasterUsersEntity getSrNumberDetails =  masterUserRepository.getAllMasterUserDetail(masterUsersEntity.getSrNumber());
							MasterUsersEntity getUnitDetails =  masterUserRepository.getAllMasterUserDetailByUserName(masterUsersEntity.getLoggedInUsername());
							if (getUnitDetails==null) {
								response.put(Constant.STATUS, 200);
								response.put(Constant.MESSAGE, Constant.SUCCESS);
								response.put("Data", mstResponse);
								
							}
							else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("CO") && getUnitDetails.getLocationType().equalsIgnoreCase("CO")) {
							response.put(Constant.STATUS, 200);
							response.put(Constant.MESSAGE, Constant.SUCCESS);
							response.put("Data", mstResponse);
							}
							else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("ZO") && getUnitDetails.getLocationType().equalsIgnoreCase("CO")) {
								response.put(Constant.STATUS, 200);
								response.put(Constant.MESSAGE, Constant.SUCCESS);
								response.put("Data", mstResponse);
							}
							else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("UO") && getUnitDetails.getLocationType().equalsIgnoreCase("CO")) {
								response.put(Constant.STATUS, 200);
								response.put(Constant.MESSAGE, Constant.SUCCESS);
								response.put("Data", mstResponse);	
							}
							else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("SO") && getUnitDetails.getLocationType().equalsIgnoreCase("CO")) {
								response.put(Constant.STATUS, 200);
								response.put(Constant.MESSAGE, Constant.SUCCESS);
								response.put("Data", mstResponse);	
							}
							else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("CO") && getUnitDetails.getLocationType().equalsIgnoreCase("ZO")) {
								response.put(Constant.STATUS, 201);
								response.put(Constant.MESSAGE, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user location Type");	
							}
							
							else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("ZO") && getUnitDetails.getLocationType().equalsIgnoreCase("ZO")) {
								
								ZonalOffice zoOfficeDetails=zonalOfficeRepository.getZonalOfficeDetails(getSrNumberDetails.getLocationCode());
								ZonalOffice zoDetails=zonalOfficeRepository.getZonalOfficeDetails(getUnitDetails.getLocationCode());
								
								String srNumberUnitCode = zoOfficeDetails.getZonalCode();
								String userNameUnitCode = zoDetails.getZonalCode();
								if(getSrNumberDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode) && getUnitDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode)) {
									response.put(Constant.STATUS, 200);
									response.put(Constant.MESSAGE, Constant.SUCCESS);
									response.put("Data", mstResponse);
								}else {
									response.put(Constant.STATUS, 201);
									response.put(Constant.MESSAGE, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user zonal code");
								}
							}
								else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("UO") && getUnitDetails.getLocationType().equalsIgnoreCase("ZO")) {
									ZonalOffice zoOffice=zonalOfficeRepository.getZonalOfficeDetails(getUnitDetails.getLocationCode());
									List<UnitOffice>  Units=unitOfficeRepository.getUnnitsFromZonalCode(zoOffice.getZonalId());
									
									boolean unitPresent = false;
									
									 for (UnitOffice unitOffice : Units) {
										 if (unitOffice.getUnitCode().equalsIgnoreCase(getSrNumberDetails.getLocationCode())) {
											 unitPresent = true;
											 
											  } 
										 try {
										  if (unitPresent) {
					                    	  response.put(Constant.STATUS, 200);
												response.put(Constant.MESSAGE, Constant.SUCCESS);
												response.put("Data", mstResponse);
												
											}
										 else  {
					                    	  response.put(Constant.STATUS, 201);
												response.put(Constant.MESSAGE, "This unit does not belong to the particular Zonal Unit");
					                      }
										 }catch (Exception ex) {
												logger.error(" get Unit Code search  exception occured." + ex.getMessage());
												throw new UnitCodeServiceException ("Internal server error");
											}
									 }
								}
								else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("SO") && getUnitDetails.getLocationType().equalsIgnoreCase("ZO")) {
									response.put(Constant.STATUS, 201);
									response.put(Constant.MESSAGE, "Invalid SrNumber, The srNumber which you are trying to search does not belong to logged in user location Type");	
								}
								else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("CO") && getUnitDetails.getLocationType().equalsIgnoreCase("UO")) {
									response.put(Constant.STATUS, 201);
									response.put(Constant.MESSAGE, "Invalid SrNumber, The srNumber which you are trying to search does not belong to logged in user location Type");	
								}
								else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("ZO") && getUnitDetails.getLocationType().equalsIgnoreCase("UO")) {
									response.put(Constant.STATUS, 201);
									response.put(Constant.MESSAGE, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user location Type");	
								}
								else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("UO") && getUnitDetails.getLocationType().equalsIgnoreCase("UO")) {
									UnitOffice unit = unitOfficeRepository.getAllUnitDetails(getSrNumberDetails.getLocationCode());	
									UnitOffice unitDetails = unitOfficeRepository.getAllUnitDetails(getUnitDetails.getLocationCode());
									
									String srNumberUnitCode = unit.getUnitCode();
									String userNameUnitCode = unitDetails.getUnitCode();
									if(getSrNumberDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode) && getUnitDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode)) {
										response.put(Constant.STATUS, 200);
										response.put(Constant.MESSAGE, Constant.SUCCESS);
										response.put("Data", mstResponse);
									}else {
										response.put(Constant.STATUS, 201);
										response.put(Constant.MESSAGE, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user zonal code");
									}
								}else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("SO") && getUnitDetails.getLocationType().equalsIgnoreCase("UO")) {
									SatelliteOffice satellite = satelliteOfficeRepository.findAllSateliteDetails(getSrNumberDetails.getLocationCode());
									UnitOffice unitDetails = unitOfficeRepository.getAllUnitDetails(getUnitDetails.getLocationCode());
									String srNumberUnitCode = satellite.getSatelliteCode();
									String userNameUnitCode = unitDetails.getUnitCode();
									if(getSrNumberDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode) && getUnitDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode)) {
										response.put(Constant.STATUS, 200);
										response.put(Constant.MESSAGE, Constant.SUCCESS);
										response.put("Data", mstResponse);
									}else {
										response.put(Constant.STATUS, 201);
										response.put(Constant.MESSAGE, "Invalid SrNumber, The srNumber which you are trying to search does not belong to logged in user zonal code");
									}
								}else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("CO") && getUnitDetails.getLocationType().equalsIgnoreCase("SO")) {
									response.put(Constant.STATUS, 201);
									response.put(Constant.MESSAGE, "Invalid SrNumber, The srNumber which you are trying to search does not belong to logged in user location Type");	
								}
								else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("ZO") && getUnitDetails.getLocationType().equalsIgnoreCase("SO")) {
									response.put(Constant.STATUS, 201);
									response.put(Constant.MESSAGE, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user location Type");	
								}
								else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("UO") && getUnitDetails.getLocationType().equalsIgnoreCase("SO")) {
									response.put(Constant.STATUS, 201);
									response.put(Constant.MESSAGE, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user location Type");	
								}else if(getSrNumberDetails.getLocationType().equalsIgnoreCase("ZO") && getUnitDetails.getLocationType().equalsIgnoreCase("UO")) {
									SatelliteOffice satellite = satelliteOfficeRepository.findAllSateliteDetails(getSrNumberDetails.getLocationCode());
									SatelliteOffice satelliteDetails = satelliteOfficeRepository.findAllSateliteDetails(getUnitDetails.getLocationCode());
									//UnitOffice unitDetails = unitOfficeRepository.getAllUnitDetails(getUnitDetails.getLocationCode());
									String srNumberUnitCode = satellite.getSatelliteCode();
									String userNameUnitCode = satelliteDetails.getSatelliteCode();
									if(getSrNumberDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode) && getUnitDetails.getLocationCode().equalsIgnoreCase(srNumberUnitCode)) {
										response.put(Constant.STATUS, 200);
										response.put(Constant.MESSAGE, Constant.SUCCESS);
										response.put("Data", mstResponse);
									}else {
										response.put(Constant.STATUS, 201);
										response.put(Constant.MESSAGE, "Invalid SrNumber, The srNumber which you are trying to search does not belong to logged in user zonal code");
									}
								}
								
									
							else {
								response.put(Constant.STATUS, 201);
								response.put(Constant.MESSAGE, "Invalid SrNumber, The SrNumber which you are trying to search does not belong to logged in user location Type");
								//response.put("Data", mstResponse);
							}
						}
						//response.put(Constant.STATUS, 200);                 
						//response.put(Constant.MESSAGE, Constant.SUCCESS);                
						//response.put("Data", mstResponse);           
					
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
		}
		return null;

	}
	
	
	/*@GetMapping(value = "/usermgmt/getUserBySrNumber/{srNumber}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> getUserBySrNumber(@PathVariable String srNumber) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		logger.info("Method Start--getUserBySrNumber--");
		
		try {
			MasterUsersEntity objMasterUsersEntity = masterUsersService.getUserBySrNumber(srNumber);
			logger.info("MasterUserEntity.getUserBySrNumber::"+objMasterUsersEntity.getMasterUserId());
			if(objMasterUsersEntity == null || objMasterUsersEntity.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NOT_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", objMasterUsersEntity);
			}
			logger.info("Method End--getUserBySrNumber--");
			return ResponseEntity.accepted().body(response);
		}
		catch (Exception ex) {
			logger.info("Exception"+ ex.getMessage());
		}
		return null;
	}*/
	
	@GetMapping(value = "/usermgmt/getUserBySrNumber/{userName}")
	public ResponseEntity<Map<String, Object>>  getUserBySrNumber(@PathVariable String userName)  throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			MasterUsersEntity mstResponse = masterUsersService.getUserByUserName(userName);
			if (mstResponse == null)
			{                 	
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NOT_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);           
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
		}
		return null;
	}
	
	@GetMapping(value = "/usermgmt/getUnitDetailsByUserName/{userName}")
	public ResponseEntity<Map<String, Object>> getUnitDetailsByUserName(@PathVariable("userName") String userName) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			List<UsersModal> mstResponse = masterUsersService.getUnitDetailsByUserName(userName);
			if (mstResponse == null || mstResponse.isEmpty())
			{                 
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);           
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.error(" get unit Details By User name exception occured." + ex.getMessage());
		}
		return null;
	}

	
	@GetMapping(value = "/usermgmt/getUserDetailsByUnitCode/{locationCode}")
	public ResponseEntity<Map<String, Object>>  getUserDetailsByUnitCode(@PathVariable String locationCode)  throws Exception {
		
		
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		

		try {
			List<MasterUsersEntity> mstResponse = masterUsersService.getUserDetailsByUnitCode(locationCode);
			if (mstResponse == null)
			{                 	
				response.put(Constant.STATUS, 10);                
				response.put(Constant.MESSAGE, Constant.NOT_FOUND);             
			} 
			else 
			{                 
				response.put(Constant.STATUS, 1);                 
				response.put(Constant.MESSAGE, Constant.SUCCESS);                
				response.put("Data", mstResponse);           
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
		}
		return null;
	}


	 @GetMapping(value = "/usermgmt/checkUsersExistsinePGSBySRnumber/{srNumber}/{unit}")
		public ResponseEntity<Object> checkUsersExistsinePGSBySRnumbe(@PathVariable("srNumber") String srNumber) throws Exception
		{
		try {
			
		return masterUsersService.checkUsersExistsinePGSBySRnumber(srNumber);
		} catch (Exception e) {
		// TODO Auto-generated catch block
		throw new Exception ("Internal Server Error");
		}
	}
	 
		@PostMapping(value = "/usermgmt/getEPGSAndMPHUserDetails")
		public ResponseEntity<Map<String, Object>> getEPGSAndMPHUserDetails(@RequestBody List<String> userNames) throws Exception 
		{
			try
			{
				return masterUsersService.getEPGSAndMPHUserDetails(userNames);
			}
			catch (Exception e) 
			{
				throw new Exception ("Internal Server Error");
			}
		}
		
		@GetMapping(value = "/usermgmt/getParentOffices")
	    public ResponseEntity<Map<String, Object>> getParentOffices(@RequestParam String operatingUnitType, @RequestParam String operatingUnitCode) throws Exception {
	          Map<String, Object> response = new HashMap<String, Object>();
	          response.put(Constant.STATUS, 0);
	          response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	          try {
	        	  OfficeCodeModel officeCodeModel = new OfficeCodeModel();
	        	  ObjectMapper Obj = new ObjectMapper();  
	        	  if(operatingUnitType.equalsIgnoreCase("UO")) {
	        		  int count = masterUsersService.checkIfUnitCodeIsValid(operatingUnitCode);
	        		  if(count > 0) {	        		  
		        		  String zonecode = masterUsersService.getZonalCodeForUnitCode(operatingUnitCode);
		        		  officeCodeModel.setZOCode(zonecode);
		        		  officeCodeModel.setCOCode("C900");
		        		  String jsonStr = Obj.writeValueAsString(officeCodeModel);  
		        		  response.put(Constant.STATUS, 1);                 
	                      response.put(Constant.MESSAGE, Constant.SUCCESS); 
	                      response.put("Data", jsonStr);  
	        		  }else {
	        			  response.put(Constant.STATUS, 1);                 
	                      response.put(Constant.MESSAGE, "Invalid input");
                      }
	        	  }else if(operatingUnitType.equalsIgnoreCase("ZO")) {
	        		  int count = masterUsersService.checkIfZonalCodeIsValid(operatingUnitCode);
	        		  if(count > 0) {
		        		  officeCodeModel.setCOCode("C900");
		        		  String jsonStr = Obj.writeValueAsString(officeCodeModel);  
		        		  response.put(Constant.STATUS, 1);                 
	                      response.put(Constant.MESSAGE, Constant.SUCCESS); 
	                      response.put("Data", jsonStr);  
	        		  }else {
	        			  response.put(Constant.STATUS, 1);                 
	                      response.put(Constant.MESSAGE, "Invalid input");
	        		  }
	        	  }
	                 return ResponseEntity.ok().body(response);
	          } catch (Exception ex) {
	                 logger.info("Exception : " + ex.getMessage());
	          }
	          return null;
	    }
	
		
		@GetMapping(value = "/usermgmt/getUserDetailsBasedOnUnit")
	    public ResponseEntity<Map<String, Object>> getUserDetailsBasedOnUnit(@RequestParam String username, @RequestParam String operatingUnitCode) throws Exception {
			try
			{
				return masterUsersService.getUserDetailsBasedOnUnit(username, operatingUnitCode);
			}
			catch (Exception e) 
			{
				throw new Exception ("Internal Server Error");
			}
	    }
		
		@GetMapping(value = "/usermgmt/getLoginFlagStatus/{srNumber}")
		public ResponseEntity<Map<String, Object>> getLoginFlagStatus(@PathVariable String srNumber) throws Exception {

			try
			{
				return masterUsersService.getLoginFlagStatus(srNumber);
			}
			catch(Exception e)
			{
				throw new Exception ("Internal Server Error");
			}
		}
		 
		@PostMapping(value = "/usermgmt/getUserDetailsForConcurreciaAPIBySrNumber")
		public List<UserDetailsResponse> getUserDetailsForConcurreciaAPIBySrNumber(@RequestBody List<String> srnumbers) throws Exception {

			try
			{
				return masterUsersService.getUserDetailsForConcurreciaAPIBySrNumber(srnumbers);
			}
			catch(Exception e)
			{
				throw new Exception ("Internal Server Error");
			}
		}
		
		@PostMapping(value = "/usermgmt/getUserDetailsForConcurreciaAPIByEmail")
		public List<UserDetailsResponse> getUserDetailsForConcurreciaAPIByEmail(@RequestBody List<String> email) throws Exception {

			try
			{
				return masterUsersService.getUserDetailsForConcurreciaAPIByEmail(email);
			}
			catch(Exception e)
			{
				throw new Exception ("Internal Server Error");
			}
		}
		
		@PostMapping(value = "/usermgmt/syncUserDetailsWithConcurrecia/{srNumber}")
		public ResponseEntity<Map<String, Object>> syncUserDetailsWithConcurrecia(@PathVariable String srNumber) throws Exception {

			try
			{
				return masterUsersService.syncUserDetailsWithConcurrecia(srNumber);
			}
			catch(Exception e)
			{
				throw new Exception ("Internal Server Error");
			}
		}
		
		@GetMapping(value = "/usermgmt/checkTdsDetailsForUser/{username}")
		public ResponseEntity<Map<String, Object>> checkTdsDetailsForUser(@PathVariable String username) throws Exception {

			try
			{
				return masterUsersService.checkTdsDetailsForUser(username);
			}
			catch(Exception e)
			{
				throw new Exception ("Internal Server Error");
			}
		}
		
		@GetMapping(value = "/usermgmt/checkBrsAndTokenDetailsForUser/{username}")
		public ResponseEntity<Map<String, Object>> checkBrsAndTokenDetailsForUser(@PathVariable String username) throws Exception {

			try
			{
				return masterUsersService.checkBrsAndTokenDetailsForUser(username);
			}
			catch(Exception e)
			{
				throw new Exception ("Internal Server Error");
			}
		}
		
		
}
