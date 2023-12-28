package com.lic.epgs.mst.usermgmt.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptandDecryptAES;
import com.lic.epgs.mst.usermgmt.entity.MasterApplicationModule;
import com.lic.epgs.mst.usermgmt.entity.MasterChangePassSmsEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterEmailEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleIdEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleURMEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleMappingEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.EpgsUserServiceException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.MphUserServiceException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserManagementException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserRoleMappingException;
import com.lic.epgs.mst.usermgmt.modal.EmailModel;
import com.lic.epgs.mst.usermgmt.modal.EmailRequestModel;
import com.lic.epgs.mst.usermgmt.modal.MasterRoleModal;
import com.lic.epgs.mst.usermgmt.modal.SMSModel;
import com.lic.epgs.mst.usermgmt.modal.SmsRequestModel;
import com.lic.epgs.mst.usermgmt.repository.EmailChangePassRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterRoleIdRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterRoleRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterRoleURMRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersRepository;
import com.lic.epgs.mst.usermgmt.repository.ModuleRepository;
import com.lic.epgs.mst.usermgmt.repository.PortalMasterRepository;
import com.lic.epgs.mst.usermgmt.repository.UserRoleMappingRepository;

/**
 * @author SK00611397
 *
 */
@Service
@Transactional
public class UserManagementService {
	private static final Logger logger = LogManager.getLogger(UserManagementService.class);
	
	@Autowired
	PortalMasterRepository portalMasterRepository;
	
	@Autowired
	private UserManagementService userManagementService;
	
	@Autowired
	EmailChangePassRepository emailChangePassRepository;
	
	@Autowired
	private UserRoleMappingRepository userRoleMappingRepository;

	@Autowired
	private MasterUsersRepository masterUsersRepository;

	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private MasterRoleURMRepository masterRoleURMRepository;
	
	@Autowired
	SmsChangePassRepository smsChangePassRepository;
	
	@Autowired
	private EncryptandDecryptAES encryptandDecryptAES;
	
	@Value("${sms.url}")
	private String smsUrl;
	
	@Value("${email.url}")
	private String emailUrl;
	
	@Value("${new.user.email.body}")
	private String emailBody;
	
	@Value("${edit.user.email.body}")
	private String editUserEmailBdy;
	
	@Value("${assignroles.user.email.body}")
	private String rolesAssignmenEmailBdy;
	
	@Value("${editroles.user.email.body}")
	private String editRolesEmailBdy;
	
	@Value("${enableUser.user.email.body}")
	private String enableUser;
	
	@Value("${disableUser.user.email.body}")
	private String disableUser;
	
	@Value("${mph.new.user.email.body}")
	private String mphNewUserEmailBody;
	
	@Value("${mph.new.user.msg.body}")
	private String mphNewUserSMSBody;
	
	RestTemplate restTemplate = new RestTemplate();	
	/*
	 * Description: This function is used for getting all the active data from USER ROLE MAPPING Module
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */

	 public ResponseEntity<Map<String, Object>> getAllActiveUserRole() throws UserManagementException{

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService :  " + methodName);

		try {
			List<UserRoleMappingEntity> userRoleMappingEntity = userRoleMappingRepository.getAllActiveUser();

			if (userRoleMappingEntity.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", userRoleMappingEntity);
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Could not save user role due to : " + exception.getMessage());
			throw new UserManagementException ("Internal Server Error");
		}
		//return null;
	}
	
	/*
	 * Description: This function is used for adding the data into USER ROLE MAPPING Module
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */

	public ResponseEntity<Map<String, Object>> addUserRole(UserRoleMappingEntity userRoleMappingEntity) throws UserManagementException {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService : " + methodName);

		try {
			Date date = new Date();
			if (userRoleMappingEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} else {
				if (isValid(userRoleMappingEntity, "ADD")) {
					userRoleMappingEntity.setCreatedOn(date);
					userRoleMappingEntity.setModifiedOn(date);
					UserRoleMappingEntity userRoleMapping = userRoleMappingRepository.save(userRoleMappingEntity);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", userRoleMappingEntity.getUserRoleMappingId());
				} else {
					response.put(Constant.STATUS, 11);
					response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);

				}
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Exception " + exception.getMessage());
			throw new UserManagementException ("Internal Server Error");
		}
		//return null;
	}

	private boolean isValid(UserRoleMappingEntity userRoleMappingEntity, String operation) {
		List<UserRoleMappingEntity> result = userRoleMappingRepository
				.findDuplicate(userRoleMappingEntity.getMasterUserId(),userRoleMappingEntity.getRoleId());
		if (operation.equals("ADD") && !(result.size() > 0)) {
			return true;
		}
		if (operation.equals("EDIT") && !(result.size() > 1)) {
			return true;
		}
		return false;
	}

	/*
	 * Description: This function is used for updating the data in USER ROLE MAPPING Module
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	
	public ResponseEntity<Map<String, Object>> updateUserRole(UserRoleMappingEntity userRoleMappingEntity) throws UserManagementException{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService :  " + methodName);

		try {

			if (userRoleMappingEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} else {
				if (isValid(userRoleMappingEntity, "EDIT")) {
					Date date = new Date();
					UserRoleMappingEntity userRoleMapping = new UserRoleMappingEntity();

					userRoleMapping = userRoleMappingRepository
							.getUserRoleDetail(userRoleMappingEntity.getUserRoleMappingId());

					userRoleMapping.setMasterUserId(userRoleMappingEntity.getMasterUserId());
					userRoleMapping.setRoleId(userRoleMappingEntity.getRoleId());
					userRoleMapping.setIsActive(userRoleMappingEntity.getIsActive());
					userRoleMapping.setIsDeleted(userRoleMappingEntity.getIsDeleted());
					userRoleMapping.setModifiedBy(userRoleMappingEntity.getModifiedBy());
					userRoleMapping.setModifiedOn(date);
					userRoleMapping.setCreatedBy(userRoleMappingEntity.getCreatedBy());
					userRoleMapping.setCreatedOn(userRoleMappingEntity.getCreatedOn());

					userRoleMappingRepository.save(userRoleMapping);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "updated User Role for - " + userRoleMappingEntity.getUserRoleMappingId());
				} else {
					response.put(Constant.STATUS, 11);
					response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);
				}
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.error("Could not update user role due to : " + exception);
			throw new UserManagementException ("Internal Server Error");
		}
		//return null;
	}

	/*
	 * Description: This function is used for soft deleting the data in USER ROLE MAPPING Module by using primary key
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	
	public ResponseEntity<Map<String, Object>> deleteUserRole(Long userRoleMapId) throws UserManagementException{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService :  " + methodName);

		try {

			if (userRoleMapId == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} else {
				if (!isValidDeletion(userRoleMapId)) {
					response.put(Constant.STATUS, 10);
					response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
				} else {
					userRoleMappingRepository.findAndDeleteById(userRoleMapId);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Deleted user Role Map Id : " + userRoleMapId);
				}
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception ex) {
			logger.info("Could not delete user role due to : " + ex.getMessage());
			throw new UserManagementException ("Internal Server Error");
		}
		//return null;
	}

	private boolean isValidDeletion(Long id) {
		Optional<UserRoleMappingEntity> result = userRoleMappingRepository.findById(id);
		if (!result.isPresent()) {
			return false;
		}
		if (result.get().getIsDeleted() != null && result.get().getIsDeleted().equals("Y")) {
			return false;
		}
		if (result.get().getIsActive() != null && result.get().getIsActive().equals("N")) {
			return false;
		}

		else {
			return true;
		}
	}

	/*
	 * Description: This function is used for getting all data from MASTER USERS Module
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	public ResponseEntity<Map<String, Object>> getAllMasterUsersDetail() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService :  " + methodName);

		try {

			List<MasterUsersEntity> masterUsersDetailList = masterUsersRepository.findAllMasterUsersDetail();

			if (masterUsersDetailList.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", masterUsersDetailList);

			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Could not get Master User data : " + exception.getMessage());
		}
		return null;
	}

	/*
	 * Description: This function is used for getting all the MasterUserRole data from USER ROLE MAPPING Module by srNumber
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	
	public ResponseEntity<Map<String, Object>> getAllMasterUserRole(String srNumber) throws UserManagementException{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService :  " + methodName);

		try {

			List<MasterUsersEntity> masterUsersDetailList = masterUsersRepository.findBySrNumber(srNumber);

			if (masterUsersDetailList.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", masterUsersDetailList);
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Exception : " + exception.getMessage());
			throw new UserManagementException ("Internal Server Error");
		}
		//return null;
	}
	
	/*
	 * Description: This function is used for getting all the MasterUserRole data from USER ROLE MAPPING Module by emailId
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	
	public ResponseEntity<Map<String, Object>> getAllMasterUserRoleByEmail(String emailId) throws UserManagementException{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService :  " + methodName);

		try {

			List<MasterUsersEntity> masterUsersDetailList = masterUsersRepository.findByEmail(emailId);

			if (masterUsersDetailList.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", masterUsersDetailList);
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Exception : " + exception.getMessage());
			throw new UserManagementException ("Internal Server Error");
		}
		//return null;
	}
	
	/*
	 * Description: This function is used for checkAdminAccess from USER ROLE MAPPING Module by emailId
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	
	public ResponseEntity<Map<String, Object>> checkAdminAccessByEmail(String emailId) throws UserManagementException{
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService :  " + methodName);

		try {

			List<String> masterUsersDetailList = masterUsersRepository.checkAdminAccessByEmail(emailId);

			if (masterUsersDetailList.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", masterUsersDetailList);
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Exception : " + exception.getMessage());
			throw new UserManagementException ("Internal Server Error");
		}
		//return null;
	}

	/*
	 * Description: This function is used for getting all the Role data from USER ROLE MAPPING Module 
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	
	public ResponseEntity<Map<String, Object>> getAllUserRoles() throws UserManagementException{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.FAILED);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService :  " + methodName);

		try {
			List<UserRoleMappingEntity> userRoleMappingEntities = userRoleMappingRepository.findAll();

			if (userRoleMappingEntities.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", userRoleMappingEntities);
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Exception : " + exception.getMessage());
			throw new UserManagementException ("Internal Server Error");
		}
		//return null;
	}

	/*
	 * Description: This function is used for getting all the UserRole data from USER ROLE MAPPING Module by masterUserId
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	public ResponseEntity<Map<String, Object>> getUserRoleById(Long masterUserId) throws UserManagementException{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService :  " + methodName);
		try {

			List<MasterRoleURMEntity> masterRoleEntity = masterRoleURMRepository.getMasterRoleEntity(masterUserId);

			if (masterRoleEntity == null || masterRoleEntity.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", masterRoleEntity);
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Exception : " + exception.getMessage());
			throw new UserManagementException ("Internal Server Error");
		}
		//return null;
	}

	/*
	 * Description: This function is used for getting all the data from MASTER_APPLICAITON_MODULE 
	 * Table Name- MASTER_APPLICAITON_MODULE
	 * Author- Nandini R
	 */
	
	public ResponseEntity<Map<String, Object>> getAllModule() throws UserManagementException{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService :  " + methodName);

		try {

			List<MasterApplicationModule> objMasterApplication = moduleRepository.findAll();

			if (objMasterApplication.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", objMasterApplication);

			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Exception : " + exception.getMessage());
			throw new UserManagementException ("Internal Server Error");
		}
		//return null;
	}

	/*
	 * Description: This function is used for getting all the MasterUserRole data from USER ROLE MAPPING Module by location
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	
	public ResponseEntity<Map<String, Object>> getAllMasterUserRoleByLocation(String location) throws UserManagementException{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService :  " + methodName);

		try {

			List<MasterUsersEntity> masterUsersDetailList = masterUsersRepository.findByLocation(location);

			if (masterUsersDetailList.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", masterUsersDetailList);
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Exception : " + exception.getMessage());
			throw new UserManagementException ("Internal Server Error");
		}
		//return null;
	}
	
	/*
	 * Description: This function is used for getting all the MasterUserRole data from USER ROLE MAPPING Module by locationCode
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	public ResponseEntity<Map<String, Object>> getAllMasterUserRoleByLocationCode(String locationCode) throws UserManagementException{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService :  " + methodName);

		try {

			List<MasterUsersEntity> masterUsersDetailList = masterUsersRepository.findByLocationCode(locationCode);

			if (masterUsersDetailList.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", masterUsersDetailList);
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Exception : " + exception.getMessage());
			throw new UserManagementException ("Internal Server Error");
		}
		//return null;
	}
	/*
	 * Description: This function is used for getting all the MasterUserRole data from USER ROLE MAPPING Module by RoleName
	 * Table Name- USER_ROLE_MAPPING
	 * Author- Nandini R
	 */
	public ResponseEntity<Map<String, Object>> getUserByRoleName(MasterRoleModal masterRoleModal) throws UserManagementException {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService :  " + methodName);

		try {
			List<MasterUsersEntity> masterRoleEntityList = masterUsersRepository
					.getUserByRoleName(masterRoleModal.getRoleName(), masterRoleModal.getRoleType());

			if (masterRoleEntityList.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", masterRoleEntityList);
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Exception : " + exception.getMessage());
			throw new UserManagementException ("Internal Server Error");
		}
		//return null;
	}

	public ResponseEntity<Object> sendEmail(EmailModel emailModel)
			{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> response1 = new HashMap<String, Object>();
		MasterEmailEntity masteremailDetailsEntity = new MasterEmailEntity();
		EmailRequestModel emailRequestModel = new EmailRequestModel();
		Date date = new Date();
		String EmailBdy = "";
		int currentAttemptCount;
		//MasterUsersEntity masterUserEntity= masterUsersRepository.getUserByUserName(emailModel.getUsername());
		//MasterUsersEntity loggedInUserEntity= masterUsersRepository.getUserByUserName(emailModel.getLogggedInUserUserName());
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			if (emailModel.getSource().equalsIgnoreCase("userCreation")) {
				EmailBdy = emailBody.replace("{username}", emailModel.getUsername());
				emailRequestModel.setTo(emailModel.getEmail());
				emailRequestModel.setSubject("New user credentials");
			}else if (emailModel.getSource().equalsIgnoreCase("editUser")) {
				EmailBdy = editUserEmailBdy.replace("{username}", emailModel.getUsername());
				emailRequestModel.setTo((emailModel.getEmail()));
				emailRequestModel.setSubject("User Edited");
			}else if (emailModel.getSource().equalsIgnoreCase("rolesassignment")) {
				EmailBdy = rolesAssignmenEmailBdy.replace("{username}", emailModel.getUsername());
				emailRequestModel.setTo((emailModel.getEmail()));
				emailRequestModel.setSubject("Roles Assignment ");
			}else if (emailModel.getSource().equalsIgnoreCase("editroles")) {
				EmailBdy = editRolesEmailBdy.replace("{username}", emailModel.getUsername());
				emailRequestModel.setTo(emailModel.getEmail());
				emailRequestModel.setSubject("Roles Edited");
			}else if (emailModel.getSource().equalsIgnoreCase("enableuser")) {
				EmailBdy = enableUser.replace("{username}", emailModel.getUsername());
				emailRequestModel.setTo(emailModel.getEmail());
				emailRequestModel.setSubject("User Enabled");
			}else if (emailModel.getSource().equalsIgnoreCase("disableuser")) {
				EmailBdy = disableUser.replace("{username}", emailModel.getUsername());
				emailRequestModel.setTo(emailModel.getEmail());
				emailRequestModel.setSubject("User Disabled");
			}else if (emailModel.getSource().equalsIgnoreCase("newSuperAdminUser")) {
				EmailBdy = mphNewUserEmailBody.replace("{username}", emailModel.getUsername());
				emailRequestModel.setTo(emailModel.getEmail());
				emailRequestModel.setSubject("New user credentials");
			}  
				
			emailRequestModel.setEmailBody(EmailBdy);			
			emailRequestModel.setPdfFile(new ArrayList<String>());

			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(emailRequestModel);
			HttpEntity formEntity = new HttpEntity(jsonStr, headers);
			logger.debug("JSON String :" + jsonStr);
			logger.debug("Form entity :" + formEntity);

			ResponseEntity responseFromApi = restTemplate.exchange(emailUrl, HttpMethod.POST, formEntity, String.class);

			logger.debug("Resonse :" + responseFromApi.getBody());
			logger.debug("Response status code :" + responseFromApi.getStatusCode());

			JSONObject jsonObject = new JSONObject(responseFromApi.getBody().toString());
			if (jsonObject != null && jsonObject.getString("returnCode") != null) {

				String statusFromService = jsonObject.getString("returnCode");

				if (statusFromService.equals("0")) {

					masteremailDetailsEntity.setSource(emailModel.getSource());
					masteremailDetailsEntity.setEmailId(emailRequestModel.getTo());
					masteremailDetailsEntity.setUserName(emailModel.getUsername());
					masteremailDetailsEntity.setResponseMessage(EmailBdy);
					masteremailDetailsEntity.setStatus("Success");
					masteremailDetailsEntity.setReturnCode("0");
					masteremailDetailsEntity.setCreatedBy(emailModel.getLogggedInUserUserName());
					masteremailDetailsEntity.setCreatedOn(date);
					masteremailDetailsEntity.setModifiedOn(date);
					masteremailDetailsEntity.setModifiedBy(emailModel.getLogggedInUserUserName());
					emailChangePassRepository.save(masteremailDetailsEntity);
					
					response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, "Success");
					response.put("Description", "Email sent successfully");
			//		response.put(Constant.DATA,"This user cannot be added as Admin as limit of max 4 admins has been reached.");

				} else {

					masteremailDetailsEntity.setSource(emailModel.getSource());
					masteremailDetailsEntity.setEmailId(emailRequestModel.getTo());
					masteremailDetailsEntity.setUserName(emailModel.getUsername());
					masteremailDetailsEntity.setResponseMessage(EmailBdy);
					masteremailDetailsEntity.setStatus("Fail");
					masteremailDetailsEntity.setReturnCode("1");
					masteremailDetailsEntity.setCreatedBy(emailModel.getLogggedInUserUserName());
					masteremailDetailsEntity.setCreatedOn(date);
					masteremailDetailsEntity.setModifiedOn(date);
					masteremailDetailsEntity.setModifiedBy(emailModel.getLogggedInUserUserName());
					emailChangePassRepository.save(masteremailDetailsEntity);
					
					response.put(Constant.STATUS, 201);
					response.put(Constant.MESSAGE, "Failure");
					response.put("Description", "Could not send email.");
				//	response.put(Constant.DATA,"This user cannot be added as Admin as limit of max 4 admins has been reached.");
				}
			}
		} catch (Exception e) {
			logger.error("sendEmail exception occured :" + e.getMessage());

			masteremailDetailsEntity.setSource(emailModel.getSource());
			masteremailDetailsEntity.setEmailId(emailRequestModel.getTo());
			masteremailDetailsEntity.setUserName(emailModel.getUsername());
			masteremailDetailsEntity.setResponseMessage(EmailBdy);
			masteremailDetailsEntity.setStatus("Fail");
			masteremailDetailsEntity.setReturnCode("1");
			masteremailDetailsEntity.setCreatedBy(emailModel.getLogggedInUserUserName());
			masteremailDetailsEntity.setCreatedOn(date);
			masteremailDetailsEntity.setModifiedOn(date);
			masteremailDetailsEntity.setModifiedBy(emailModel.getLogggedInUserUserName());
			emailChangePassRepository.save(masteremailDetailsEntity);
			
			
			response.put(Constant.STATUS, 201);
			response.put(Constant.MESSAGE, "Failure");
			response.put("Description", "Could not send email.");
			//response.put(Constant.DATA,"This user cannot be added as Admin as limit of max 4 admins has been reached.");

		//	throw new EpgsUserServiceException("Internal Server Error");
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	
	
	public ResponseEntity<Object> sendSMS(SMSModel smsModel) {

		MasterChangePassSmsEntity masterChangePassSmsEntity = new MasterChangePassSmsEntity();
		SmsRequestModel smsRequestModel = new SmsRequestModel();
		final String newUserSmsBody = mphNewUserSMSBody.replace("{username}", smsModel.getUsername());
		String mobilejsonStr = null;
		HashMap<String, Object> response = new HashMap<String, Object>();
		HttpHeaders headers = new HttpHeaders();
		String statusFromServiceForMobileSMS = "";
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date date = ts;
		try {
			// SMS
			logger.info("Method Start--sendSMS--");
			smsRequestModel.setName(smsModel.getUsername());
			smsRequestModel.setMobileNo("91" + smsModel.getMobile());
			smsRequestModel.setMsgTxt(newUserSmsBody);
			smsRequestModel.setDivisionCode("G101");
			smsRequestModel.setPolicyNumber("");

 

			try {
				ObjectMapper mapper = new ObjectMapper();

				mobilejsonStr = mapper.writeValueAsString(smsRequestModel);
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				logger.error(" sendSMS exception occured." + e1.getMessage());
			}
			HttpEntity mobileformEntity = new HttpEntity(mobilejsonStr, headers);

 

			ResponseEntity mobileResponseFromApi = restTemplate.exchange(smsUrl, HttpMethod.POST,
					mobileformEntity, String.class);
			logger.debug("Resonse :" + mobileResponseFromApi.getBody());
			logger.debug("Response status code :" + mobileResponseFromApi.getStatusCode());
			JSONObject mobileJsonObject = new JSONObject(mobileResponseFromApi.getBody().toString());
			if (mobileJsonObject != null && mobileJsonObject.getString("returnCode") != null) {

 

				statusFromServiceForMobileSMS = mobileJsonObject.getString("returnCode");

 

				if (statusFromServiceForMobileSMS.equalsIgnoreCase("0")) {
					masterChangePassSmsEntity.setSource("newUser");
					masterChangePassSmsEntity.setMobile(smsModel.getMobile());
					masterChangePassSmsEntity.setUsername(smsModel.getUsername());
					masterChangePassSmsEntity.setResponsemsg(smsRequestModel.getMsgTxt());
					masterChangePassSmsEntity.setStatus("Success");
					masterChangePassSmsEntity.setReturncode("0");
					masterChangePassSmsEntity.setCreatedOn(date);
					masterChangePassSmsEntity.setCreatedBy(smsModel.getUsername());
					masterChangePassSmsEntity.setModifiedon(date);
					masterChangePassSmsEntity.setModifiedby(smsModel.getUsername());
					smsChangePassRepository.saveAndFlush(masterChangePassSmsEntity);

					response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, "Success");
					response.put("Description", "SMS sent successfully");

 

				} else {
					masterChangePassSmsEntity.setSource("newUser");
					masterChangePassSmsEntity.setMobile(smsModel.getMobile());
					masterChangePassSmsEntity.setUsername(smsModel.getUsername());
					masterChangePassSmsEntity.setResponsemsg(smsRequestModel.getMsgTxt());
					masterChangePassSmsEntity.setStatus("Fail");
					masterChangePassSmsEntity.setReturncode("0");
					masterChangePassSmsEntity.setCreatedOn(date);
					masterChangePassSmsEntity.setCreatedBy(smsModel.getUsername());
					masterChangePassSmsEntity.setModifiedon(date);
					masterChangePassSmsEntity.setModifiedby(smsModel.getUsername());
					smsChangePassRepository.saveAndFlush(masterChangePassSmsEntity);

					response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, "Success");
					response.put("Description", "Could not send SMS");

 

				}

 

			}

 

		} catch (Exception e) {
			logger.error(" send Sms exception occured." + e.getMessage());
			masterChangePassSmsEntity.setSource("newUser");
			masterChangePassSmsEntity.setMobile(smsModel.getMobile());
			masterChangePassSmsEntity.setUsername(smsModel.getUsername());
			masterChangePassSmsEntity.setResponsemsg(smsRequestModel.getMsgTxt());
			masterChangePassSmsEntity.setStatus("Fail");
			masterChangePassSmsEntity.setReturncode("0");
			masterChangePassSmsEntity.setCreatedOn(date);
			masterChangePassSmsEntity.setCreatedBy(smsModel.getUsername());
			masterChangePassSmsEntity.setModifiedon(date);
			masterChangePassSmsEntity.setModifiedby(smsModel.getUsername());
			smsChangePassRepository.saveAndFlush(masterChangePassSmsEntity);
			response.put(Constant.STATUS, 200);
			response.put(Constant.MESSAGE, "Success");
			response.put("Description", "Could not send SMS");
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> sendEmailAndSmsToBulkUser(JSONArray userArray) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> response1 = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		SMSModel smsModel = new SMSModel();
		EmailModel emailModel = new EmailModel();

		logger.debug("Enter approveOrAddUser : " + methodName);
		// String token = "Bearer "+generateToken().getAccess_token();
		int successEmailCount = 0;
		int failedEmailCount = 0;
		int successSmsCount = 0;
		int failedSmsCount = 0;
		int userArrayLength = userArray.length();
		try {

			for (int i = 0; i < userArrayLength; i++) {
                       
				JSONObject userObj = userArray.getJSONObject(i);
				 String action=userObj.getString("decision");
				 if(!action.equalsIgnoreCase("Approve")) {
					 continue;
				 }
				emailModel.setUsername(userObj.getString("username"));
				emailModel.setSource("newSuperAdminUser");
				emailModel.setEmail(userObj.getString("email"));
				emailModel.setLogggedInUserUserName(userObj.getString("checkerName"));

				smsModel.setUsername(userObj.getString("username"));
				smsModel.setSource("newUser");
				Long mob = userObj.getLong("mobile");
				String mobile = String.valueOf(mob);
				smsModel.setMobile(mobile);
				smsModel.setLogggedInUserUserName(userObj.getString("checkerName"));
				try {
					ResponseEntity<Object> resEntity = userManagementService.sendEmail(emailModel);
					if (resEntity.getStatusCode() != null && resEntity.getStatusCode().value() == 200) {
						successEmailCount++;
					} else {
						failedEmailCount++;
					}
				} catch (Exception e) {
					logger.error("Could not add Mph User in RHSSO" + e.getMessage());
				} finally {
					try {
						ResponseEntity<Object> resetEntity = userManagementService.sendSMS(smsModel);
						if (resetEntity.getStatusCode() != null && resetEntity.getStatusCode().value() == 200) {
							successSmsCount++;
						} else {
							failedSmsCount++;
						}
					} catch (Exception e) {
						logger.error("Could not add Mph User in RHSSO" + e.getMessage());
					}
				}
			}
		} catch (Exception e) {

			logger.error("Could not add Mph User in RHSSO" + e.getMessage());

		} finally {
			if (successEmailCount == successSmsCount && successEmailCount == userArrayLength) {
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", "All Users processed Sucessfully");
			}
			else if(successEmailCount==0 && successSmsCount==0) {
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data","Email SMS not sent to Users");
			}else {
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data",
						"Email SMS sent Successfully");
			}

			ObjectMapper Obj = new ObjectMapper();
			String jsonStr = Obj.writeValueAsString(response);

			String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
			response1.put(Constant.STATUS, 200);
			response1.put(Constant.MESSAGE, Constant.SUCCESS);
			response1.put("body", encaccessResponse);

		}
		return new ResponseEntity<Object>(response1, HttpStatus.OK);

	}
}
