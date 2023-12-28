package com.lic.epgs.mst.usermgmt.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lic.epgs.mst.usermgmt.modal.RolesModel;
import com.lic.epgs.mst.usermgmt.modal.TokenModel;
import com.lic.epgs.mst.usermgmt.modal.TokenUserModel;
import com.lic.epgs.mst.usermgmt.repository.EnableDisableUserRepository;
import com.lic.epgs.mst.usermgmt.repository.LoggedinuserSessionDetailsRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersLoginDetailsRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersRepository;
import com.lic.epgs.mst.usermgmt.repository.MphNameRepository;
import com.lic.epgs.mst.usermgmt.repository.PortalMasterRepository;
import com.lic.epgs.rhssomodel.AccessTokenResponse;
import com.lic.epgs.rhssomodel.AddUserListModel;
import com.lic.epgs.rhssomodel.ClientResponse;
import com.lic.epgs.rhssomodel.CompositeRoleResponse;
import com.lic.epgs.rhssomodel.MphRoleMap;
import com.lic.epgs.rhssomodel.RequestTokenModel;
import com.lic.epgs.rhssomodel.ResetPasswordModel;
import com.lic.epgs.rhssomodel.ResetPasswordRequestModel;
import com.lic.epgs.rhssomodel.ResponseModel;
import com.lic.epgs.rhssomodel.RoleMap;
import com.lic.epgs.rhssomodel.RoleResponseModel;
import com.lic.epgs.rhssomodel.RolesListModel;
import com.lic.epgs.rhssomodel.SessionModel;
import com.lic.epgs.rhssomodel.TokenStatus;
import com.lic.epgs.rhssomodel.UpdateUserModel;
import com.lic.epgs.rhssomodel.UserDetailsRequestModel;
import com.lic.epgs.rhssomodel.UserDetailsResponse;
import com.lic.epgs.rhssomodel.UserResponse;
import com.lic.epgs.rhssomodel.UserResponseModel;
import com.lic.epgs.rhssomodel.UserSessionResponse;
import com.lic.epgs.rhssomodel.ValidateEmailMobileRequestModel;
import com.lic.epgs.mst.entity.UnitCodeEntity;
import com.lic.epgs.mst.entity.UnitEntity;
import com.lic.epgs.mst.entity.ZonalEntity;
import com.lic.epgs.mst.repository.UnitCodeRepository;
import com.lic.epgs.mst.repository.UnitEntityRepository;
import com.lic.epgs.mst.repository.ZonalEntityRepository;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptandDecryptAES;
import com.lic.epgs.mst.usermgmt.entity.*;

@Service
public class RedhatUserGenerationService {

	private static final Logger logger = LoggerFactory.getLogger(RedhatUserGenerationService.class);

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private PortalMasterRepository portalMasterRepository;
	
	@Autowired
	private UnitEntityRepository unitEntityRepository;
	
	@Autowired
	private LoggedinuserSessionDetailsRepository loggedinuserSessionDetailsRepository;
	
	@Autowired
	private RedhatUserGenerationService redhatUserGenerationService;
	
	@Autowired
	MphNameRepository mphNameRepository;
	
	@Autowired
	private ZonalEntityRepository zonalEntityRepository;
	
	@Autowired
	private MasterUsersRepository masterUsersRepository;

	@Autowired
	private EncryptandDecryptAES encryptandDecryptAES;
	
	
	@Autowired
	private EnableDisableUserRepository enableDisableUserRepository;
	
	@Autowired
	MasterUsersLoginDetailsRepository masterUsersLoginDetailsRepository;
	
	@Value("${rhsso.url}")
	private String rhssoUrl;
	
	@Value("${application.url}")
	private String applicationUrl;

	public HttpHeaders restHeader() {

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		return headers;
	}

	public HttpHeaders restHeaders() {

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		// headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		// headers.setContentLength(100L);
		// headers.setConnection( (List<String>) MediaType.APPLICATION_FORM_URLENCODED);
		// headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
		// headers.setAccept(Arrays.asList(MediaType.MULTIPART_FORM_DATA));

		return headers;
	}
	
	
	public ResponseEntity<Object> updateUser(String token,String realms,String userid,UpdateUserModel updateUserModel) {
		ResponseModel rm = new ResponseModel();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// String resetApiUrl = rhssoUrl +
		// "/auth/admin/realms/{realms}/users/{userid}/reset-password";
	    	String resetApiUrl = rhssoUrl + "/auth/admin/realms/{realms}/users/{userid}";
		
		/* MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		//requestBody.add("id", updateUserModel.getId());
		//requestBody.add("createdTimestamp", updateUserModel.getCreatedTimestamp());
		requestBody.add("username", updateUserModel.getUsername());
		requestBody.add("enabled", updateUserModel.getEnabled());
		requestBody.add("totp", updateUserModel.getTotp());
		requestBody.add("email", updateUserModel.getEmail());*/
		//logger.info("reqiestbody"+requestBody);
	    	
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms",realms);
		uriParam.put("userid", userid);
		UriComponents resetApibuilder = UriComponentsBuilder.fromHttpUrl(resetApiUrl).build();
		HttpEntity resetentity = new HttpEntity<>(headers);
		try {
			
			ObjectMapper Obj = new ObjectMapper(); 
			 String jsonStr = Obj.writeValueAsString(updateUserModel); 
			
			
			HttpEntity formEntity = new HttpEntity(jsonStr, headers);
			logger.info("formEntity "+ formEntity);
			
			ResponseEntity<String> resetEntityResponse = restTemplate.exchange(resetApibuilder.toUriString(), HttpMethod.PUT,
					formEntity, String.class, uriParam);
			if (resetEntityResponse.getStatusCode().equals(HttpStatus.NO_CONTENT)
					&& resetEntityResponse.getStatusCodeValue() == 204) {
				rm.setStatus(200);
				rm.setMessage("Success");
				rm.setDescription("User has been updated");
				// String jsonStr1 = Obj.writeValueAsString(rm); 
				//	String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr1);	
					
					return new ResponseEntity<Object>(rm, HttpStatus.OK);
			} else {
				rm.setStatus(202);
				rm.setMessage("failure");
				rm.setDescription("Update user failure");
				// String jsonStr1 = Obj.writeValueAsString(rm); 
				//	String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr1);	
					
					return new ResponseEntity<Object>(rm, HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			rm.setStatus(1);
			rm.setMessage("failure");
			rm.setDescription(  e.getMessage());
			return new ResponseEntity<Object>(HttpStatus.ACCEPTED);
		
		}
	
	}
	
	
	
	public Map<String, Object> getEmployeeDetailFromSsO(TokenUserModel TokenUserModelRequest) {

		HashMap<String, String> map = new HashMap<String, String>();
		Map<String, Object> ob = new HashMap<String, Object>();
		final String baseUrl = rhssoUrl+"/auth/realms/epgs-claim-portal/protocol/openid-connect/token/introspect";

		TokenUserModel tokenUserModel = new TokenUserModel();

		tokenUserModel.setClient_id(TokenUserModelRequest.getClient_id());
		tokenUserModel.setToken(TokenUserModelRequest.getToken());
		tokenUserModel.setClient_secret(TokenUserModelRequest.getClient_secret());
		tokenUserModel.setUsername(TokenUserModelRequest.getUsername());
		HttpEntity<TokenUserModel> httpEntityObj = new HttpEntity<TokenUserModel>(tokenUserModel, restHeaders());

		logger.info(
				"RedhatUserGenerationService : getEmployeeDetailFromSsO : client_id " + tokenUserModel.getClient_id());
		logger.info("RedhatUserGenerationService : getEmployeeDetailFromSsO : token " + tokenUserModel.getToken());
		logger.info("RedhatUserGenerationService : getEmployeeDetailFromSsO : client_secret "
				+ tokenUserModel.getClient_secret());
		logger.info(
				"RedhatUserGenerationService : getEmployeeDetailFromSsO : user_name " + tokenUserModel.getUsername());

		ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.POST,
				httpEntityObj, new ParameterizedTypeReference<Map<String, Object>>() {
				});
		ob = responseEntity.getBody();
		logger.info("RedhatUserGenerationService : getEmployeeDetailFromSsO : token " + ob);

		return ob;
	}

	/*public ResponseEntity<Object> loginAuthenticateRhsso(TokenModel tokenModelRequest) {
		final String baseUrl = rhssoUrl + "/auth/realms/{realms}/protocol/openid-connect/token";
		if (StringUtils.isBlank(tokenModelRequest.getClient_id())) {
			tokenModelRequest.setClient_id("usermgmt-claim-portal");
		}
		if (StringUtils.isBlank(tokenModelRequest.getClient_secret())) {
//			tokenModelRequest.setClient_secret("3a7ffc28-638b-478f-8092-b8b2b1a3d5b4");	//	SIT
			tokenModelRequest.setClient_secret("c9a4303f-a82f-4751-a723-a9cf52b85acf"); //  UAT


		}
		if (StringUtils.isBlank(tokenModelRequest.getGrant_type())) {
			tokenModelRequest.setGrant_type("password");
		}
		if (StringUtils.isBlank(tokenModelRequest.getClientName())) {
			tokenModelRequest.setClientName("usermgmt_claim_portal");
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		requestBody.add("username", tokenModelRequest.getUsername());
		requestBody.add("password", tokenModelRequest.getPassword());
		requestBody.add("client_id", tokenModelRequest.getClient_id());
		requestBody.add("grant_type", tokenModelRequest.getGrant_type());
		requestBody.add("client_secret", tokenModelRequest.getClient_secret());
		logger.info(tokenModelRequest.toString());
		HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", tokenModelRequest.getClientName());
		ObjectMapper mapper = new ObjectMapper();
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, formEntity, String.class, uriParam);
			if (response.getStatusCode() == HttpStatus.OK && response.getStatusCodeValue() == 200) {
				AccessTokenResponse accessResponse = mapper.readValue(response.getBody(), AccessTokenResponse.class);
				logger.info("Response --" + accessResponse.getAccess_token());
				logger.info("Response --" + accessResponse.getExpires_in());
				logger.info("Response --" + accessResponse.getToken_type());
				return new ResponseEntity<Object>(accessResponse, HttpStatus.OK);
			} else {
				ResponseModel rm = new ResponseModel();
				rm.setStatus(1);
				rm.setMessage("Failure");
				rm.setDescription("Invalid User Credential");
				return new ResponseEntity<Object>(rm, HttpStatus.UNAUTHORIZED);
			}
		} catch (ResourceAccessException ex) {
			return new ResponseEntity<Object>("resource not accesible", HttpStatus.NOT_FOUND);
			
		} catch (Exception e) {
			ResponseModel rm = new ResponseModel();
			rm.setStatus(1);
			rm.setMessage("Failure");
			rm.setDescription(e.getMessage());
			return new ResponseEntity<Object>(rm, HttpStatus.NOT_FOUND);
		}
	}*/
	
	public ResponseEntity<Object> checkActiveSessionsForUser(String userName) throws Exception {
		 HashMap<String, Object> response = new HashMap<String, Object>();
		 List<LoggedInUserSessionDetailsEntity> userSessionDetails = new ArrayList();
		 try {
			 userSessionDetails = loggedinuserSessionDetailsRepository.getAllUserSessionsBasedOnUserName(userName); 
			 if(userSessionDetails != null && userSessionDetails.size() >= 1) {
					response.put("status", 201);
					response.put("message", "Failure");
					response.put("description", "You did not log out properly or you are already logged in with another browser or tab. Do you want to kill all previous sessions and proceed ?");
					return new ResponseEntity<Object>(response, HttpStatus.CREATED);
			 }else {
				 	response.put("status", 200);
				 	response.put("message", "Success");
					response.put("description", "User does not have any previous session and can proceed ahead.");	
					return new ResponseEntity<Object>(response, HttpStatus.OK);
			 }
		 }catch(Exception e){
			 response.put("status", 202);
			 response.put("message", "Failure");
			 response.put("description", "There is some issue with the service. Please try again after some time.");
			 return new ResponseEntity<Object>(response, HttpStatus.ACCEPTED);
		 }
		 
	 }
	 
	 @Transactional
	 public ResponseEntity<Object> clearActiveSessionsForUser(String username) throws Exception {
		 HashMap<String, Object> response = new HashMap<String, Object>();
		 try {
			AccessTokenResponse accessResponse = generateToken();
			String token = null;
			String userId = null;
			String realm = "epgs";
			if(accessResponse != null) {
				token = accessResponse.getAccess_token();
			}
			UserResponseModel userResponseModel = searchUserByUsername(token, realm, username);
			if(userResponseModel != null) {
				List<UserResponse> userResponses = userResponseModel.getUserlist();
				if(userResponses != null && userResponses.size() >=1) {
					UserResponse userResponse = userResponses.get(0);
					userId = userResponse.getId();
				}
			}
			String userApiuri = rhssoUrl + "auth/admin/realms/{realm}/users/{userid}/logout";
			HttpHeaders headers = new HttpHeaders();
			String token1 = "Bearer "+token;
			headers.add(HttpHeaders.AUTHORIZATION, token1);
			UriComponents userApibuilder = UriComponentsBuilder.fromHttpUrl(userApiuri).build();
	
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
			Map<String, String> uriParam = new HashMap<>();
			uriParam.put("realm", realm);
			uriParam.put("userid", userId);
			HttpEntity resetentity = new HttpEntity<>(headers);
			ResponseEntity<?> resetEntityResponse = restTemplate.exchange(userApibuilder.toUriString(), HttpMethod.POST, resetentity, String.class, uriParam);
			if (resetEntityResponse.getStatusCode().equals(HttpStatus.NO_CONTENT)&& resetEntityResponse.getStatusCodeValue() == 204) {
				loggedinuserSessionDetailsRepository.DeleteLoggedInUserByUserName(username);
				response.put("status", 200);
			 	response.put("message", "Success");
				response.put("description", "All previous sessions for the user have been cleared");				
				Date date = new Date();
				masterUsersLoginDetailsRepository.updateLogOutTime(username, date);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			} else {
				response.put("status", 201);
			 	response.put("message", "Failure");
				response.put("description", "There is some issue with the service. Please try again after some time.");
				return new ResponseEntity<Object>(response, HttpStatus.CREATED);
			}
		 }catch(Exception e){
			 response.put("status", 201);
			 response.put("message", "Failure");
			 response.put("description", "There is some issue with the service. Please try again after some time.");	
			 return new ResponseEntity<Object>(response, HttpStatus.CREATED);
		 }
		 
	 }
	
	public ResponseEntity<Object> loginAuthenticateRhsso(TokenModel tokenModelRequest) throws Exception {
		final String baseUrl = rhssoUrl + "/auth/realms/{realms}/protocol/openid-connect/token";
		int maxCount = 5;
		String loginStatusDescription =null;
		Date date = new Date();
		int invalidAttemptCount = 0;
		if (StringUtils.isBlank(tokenModelRequest.getClient_id())) {
			tokenModelRequest.setClient_id("usermgmt-claim-portal");
		}
		if (StringUtils.isBlank(tokenModelRequest.getClient_secret())) {
			tokenModelRequest.setClient_secret("3a7ffc28-638b-478f-8092-b8b2b1a3d5b4");
		}
		if (StringUtils.isBlank(tokenModelRequest.getGrant_type())) {
			tokenModelRequest.setGrant_type("password");
		}
		if (StringUtils.isBlank(tokenModelRequest.getClientName())) {
			tokenModelRequest.setClientName("usermgmt_claim_portal");
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		requestBody.add("username", tokenModelRequest.getUsername());
		requestBody.add("password", tokenModelRequest.getPassword());
		requestBody.add("client_id", tokenModelRequest.getClient_id());
		requestBody.add("grant_type", tokenModelRequest.getGrant_type());
		requestBody.add("client_secret", tokenModelRequest.getClient_secret());
		HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", tokenModelRequest.getClientName());
		ObjectMapper mapper = new ObjectMapper();
		try {

			// PortalMasterEntity portalMasterEntitObj =
			// portalMasterRepository.getMasterUserIdByUserName(tokenModelRequest.getUsername());

			// String logoutFlag = portalMasterEntitObj.getLogOut();

			// logger.debug("logoutFlag::"+logoutFlag);

			// if(logoutFlag.equalsIgnoreCase("y"))
//				{		
			MasterUsersEntity masterUserEntity = masterUsersRepository
					.findDisabledUserByUserName(tokenModelRequest.getUsername());
			
			MasterUsersEntity masterUserEntity1 = masterUsersRepository.getUserByUserName(tokenModelRequest.getUsername());
			

			if (masterUserEntity != null && masterUserEntity.getIsActive().equalsIgnoreCase("N")
					&& masterUserEntity.getIsDeleted().equalsIgnoreCase("Y")) {

				HashMap<String, Object> response = new HashMap<String, Object>();
				response.put("status", 202);
				response.put("message", "Failure");
				response.put("description", "User has Been Disabled in our Epgs System");
				return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
			}

			EnableDisableUserEntity enableDisableUserEntity = enableDisableUserRepository
					.getInvalidAttemptCount(tokenModelRequest.getUsername());
			if ((enableDisableUserEntity != null && enableDisableUserEntity.getInvalidAttemptCount() == 5
					&& enableDisableUserEntity.getIsDisabled().equalsIgnoreCase("Y"))) {
				HashMap<String, Object> response = new HashMap<String, Object>();
				response.put("status", 202);
				response.put("message", "Failure");
				response.put("description", "User is Disabled. Please contact your admin.");
				response.put("isFiveAttempt", "Y");

				// ObjectMapper Obj = new ObjectMapper();
				// String jsonStr = Obj.writeValueAsString(response);

				// ENcryption Technique
				// String encaccessResponse =
				// encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);

				return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
			}  else {
				if(masterUserEntity1.getUserexpiryon() != null) {
					String currentDatestr= new SimpleDateFormat("yyyy-MM-dd").format(date);	
					String expiryDatestr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(masterUserEntity1.getUserexpiryon());
					
					Date currentDate=new SimpleDateFormat("yyyy-MM-dd").parse(currentDatestr);
					Date expiryDate =new SimpleDateFormat("yyyy-MM-dd").parse(expiryDatestr); 				
					
					if (currentDate.after(expiryDate)) {
						final String uri = "http://10.240.3.146:80/HRM_WebService/service/WSJ_51_00003/post";
						HttpHeaders headers1 = new HttpHeaders();
						headers1.setContentType(MediaType.APPLICATION_JSON);
						Map<String, String> requestBody1 = new HashMap<String, String>();
						requestBody1.put("srnumber", masterUserEntity1.getSrNumber2());
						requestBody1.put("emailId", "");
						ObjectMapper mapper1 = new ObjectMapper();
						HttpEntity formEntity1 = new HttpEntity<Map<String, String>>(requestBody1, headers1);
						ResponseEntity<String> responseFormAPI = restTemplate.postForEntity(uri, formEntity1, String.class);
						logger.info("RESPONSE FROM THE REST TEMPLATE");
						if (responseFormAPI.getStatusCode() == HttpStatus.OK
								&& responseFormAPI.getStatusCodeValue() == 200) {
							UserDetailsResponse accessResponse = new UserDetailsResponse();
							accessResponse = mapper1.readValue(responseFormAPI.getBody(), UserDetailsResponse.class);
							if (accessResponse.getUnitCode().startsWith("G")
									|| accessResponse.getUnitCode().startsWith("g")) {
								
								UnitEntity unitCodeEntity = unitEntityRepository
										.getServiceDetailsByUnitCode(accessResponse.getUnitCode());
								if(unitCodeEntity !=null ) {
									masterUserEntity1.setLocationCode(unitCodeEntity.getUnitCode());
									masterUserEntity1.setLocation(unitCodeEntity.getDescription());
									masterUserEntity1.setLocationType("UO");
									masterUserEntity1.setUserexpiryon(null);
								masterUsersRepository.save(masterUserEntity1);
								loginStatusDescription="Your temporary access for this location has expired. You have been reset to your base location. Please contact your admin for any queries.";
//								HashMap<String, Object> response = new HashMap<String, Object>();
//								response.put("status", 200);
//								response.put("message", "Success");
//								response.put("description", "Your temporary access for this location has expired. You have been reset to your base location. Please contact your admin for any queries.");
//																return new ResponseEntity<Object>(response, HttpStatus.CREATED);
								}
							} else if (accessResponse.getUnitCode().startsWith("Z")	|| accessResponse.getUnitCode().startsWith("z"))
							{
	
								if (accessResponse.getUnitCode().equalsIgnoreCase("ZO01")) {
									ZonalEntity zonalEntity = zonalEntityRepository.getZoneDetailsByZoneCode("100");
									masterUserEntity1.setLocationCode(zonalEntity.getZonalCode());
									masterUserEntity1.setLocation(zonalEntity.getDescription());
									masterUserEntity1.setLocationType("ZO");
								} else if (accessResponse.getUnitCode().equalsIgnoreCase("ZO02")) {
									ZonalEntity zonalEntity = zonalEntityRepository.getZoneDetailsByZoneCode("200");
									masterUserEntity1.setLocationCode(zonalEntity.getZonalCode());
									masterUserEntity1.setLocation(zonalEntity.getDescription());
									masterUserEntity1.setLocationType("ZO");
								} else if (accessResponse.getUnitCode().equalsIgnoreCase("ZO03")) {
									ZonalEntity zonalEntity = zonalEntityRepository.getZoneDetailsByZoneCode("300");
									masterUserEntity1.setLocationCode(zonalEntity.getZonalCode());
									masterUserEntity1.setLocation(zonalEntity.getDescription());
									masterUserEntity1.setLocationType("ZO");
								} else if (accessResponse.getUnitCode().equalsIgnoreCase("ZO04")) {
									ZonalEntity zonalEntity = zonalEntityRepository.getZoneDetailsByZoneCode("400");
									masterUserEntity1.setLocationCode(zonalEntity.getZonalCode());
									masterUserEntity1.setLocation(zonalEntity.getDescription());
									masterUserEntity1.setLocationType("ZO");
								} else if (accessResponse.getUnitCode().equalsIgnoreCase("ZO05")) {
									ZonalEntity zonalEntity = zonalEntityRepository.getZoneDetailsByZoneCode("500");
									masterUserEntity1.setLocationCode(zonalEntity.getZonalCode());
									masterUserEntity1.setLocation(zonalEntity.getDescription());
									masterUserEntity1.setLocationType("ZO");
								} else if (accessResponse.getUnitCode().equalsIgnoreCase("ZO06")) {
									ZonalEntity zonalEntity = zonalEntityRepository.getZoneDetailsByZoneCode("600");
									masterUserEntity1.setLocationCode(zonalEntity.getZonalCode());
									masterUserEntity1.setLocation(zonalEntity.getDescription());
									masterUserEntity1.setLocationType("ZO");
								} else if (accessResponse.getUnitCode().equalsIgnoreCase("ZO07")) {
									ZonalEntity zonalEntity = zonalEntityRepository.getZoneDetailsByZoneCode("700");
									masterUserEntity1.setLocationCode(zonalEntity.getZonalCode());
									masterUserEntity1.setLocation(zonalEntity.getDescription());
									masterUserEntity1.setLocationType("ZO");
								} else if (accessResponse.getUnitCode().equalsIgnoreCase("ZO08")) {
									ZonalEntity zonalEntity = zonalEntityRepository.getZoneDetailsByZoneCode("800");
									masterUserEntity1.setLocationCode(zonalEntity.getZonalCode());
									masterUserEntity1.setLocation(zonalEntity.getDescription());
									masterUserEntity1.setLocationType("ZO");
								}
								masterUserEntity1.setUserexpiryon(null);
								masterUsersRepository.save(masterUserEntity1);
								loginStatusDescription="Your temporary access for this location has expired. You have been reset to your base location. Please contact your admin for any queries.";
//								HashMap<String, Object> response = new HashMap<String, Object>();
//								response.put("status", 200);
//								response.put("message", "Failure");
//								response.put("description", "Your temporary access for this location has expired. You have been reset to your base location. Please contact your admin for any queries.");
//								return new ResponseEntity<Object>(response, HttpStatus.CREATED);
								
							} else if (accessResponse.getUnitCode().startsWith("C")
									|| accessResponse.getUnitCode().startsWith("c")) {
								if (accessResponse.getUnitCode().equalsIgnoreCase("C099")) {
									masterUserEntity1.setLocationCode("C900");
									masterUserEntity1.setLocation("PGS Central Office Mumbai");
									masterUserEntity1.setLocationType("CO");
									masterUserEntity1.setUserexpiryon(null);
									masterUsersRepository.save(masterUserEntity1);
									loginStatusDescription="Your temporary access for this location has expired. You have been reset to your base location. Please contact your admin for any queries.";
//									HashMap<String, Object> response = new HashMap<String, Object>();
//									response.put("status", 200);
//									response.put("message", "Success");
//									response.put("description", "Your temporary access for this location has expired. You have been reset to your base location. Please contact your admin for any queries.");
//									response.put("isTemporaryDateExpired", "Y");
//									return new ResponseEntity<Object>(response, HttpStatus.CREATED);
								}
							}
							else {
								HashMap<String, Object> response = new HashMap<String, Object>();
								response.put("status", 202);
								response.put("message", "Success");
								response.put("description", "Your temporary access has expired. Please contact you admin for any queries");
								response.put("isTemporaryDateExpired", "Y");
								return new ResponseEntity<Object>(response, HttpStatus.CREATED);
							}
						//}
					}
				}
				}
				ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, formEntity, String.class,
						uriParam);

				logger.debug("Response code from RHSSO login ------" + response.getStatusCodeValue());
				if (response.getStatusCode() == HttpStatus.OK && response.getStatusCodeValue() == 200) {
					HashMap<String, Object> response1 = new HashMap<String, Object>();
					AccessTokenResponse accessResponse1 = mapper.readValue(response.getBody(),
							AccessTokenResponse.class);
					logger.debug("Response --" + accessResponse1.getAccess_token());
					logger.debug("Response --" + accessResponse1.getExpires_in());
					logger.debug("Response --" + accessResponse1.getToken_type());
					
					enableDisableUserRepository.deleteUnsuccessfulLoginUser(tokenModelRequest.getUsername());
					// Date date = new Date();
					LoggedInUserSessionDetailsEntity loggedInUserSessionDetailsEntity = new LoggedInUserSessionDetailsEntity();
					loggedInUserSessionDetailsEntity.setUserName(tokenModelRequest.getUsername());
					loggedInUserSessionDetailsEntity.setIpAddress(null);
					loggedInUserSessionDetailsEntity.setLoggedinUser(tokenModelRequest.getUsername());
					loggedInUserSessionDetailsEntity.setLoggedinOn(date);
					loggedInUserSessionDetailsEntity.setRemarks(null);
					loggedInUserSessionDetailsEntity.setCreatedBy(tokenModelRequest.getUsername());
					loggedInUserSessionDetailsEntity.setCreatedOn(date);
					loggedInUserSessionDetailsEntity.setModifiedBy(tokenModelRequest.getUsername());
					loggedInUserSessionDetailsEntity.setModifiedOn(date);
					loggedInUserSessionDetailsEntity.setBrowser(tokenModelRequest.getBrowser());
					loggedinuserSessionDetailsRepository.save(loggedInUserSessionDetailsEntity);
                       
					   response1.put("loginStatusDescription", loginStatusDescription);
					   response1.put("accessResponse1", accessResponse1);
					
					   logger.info("USER LOGGED IN SUCCESSFULLY "+tokenModelRequest.getUsername()+" AT TIME :"+date);
					   logger.debug("USER LOGGED IN SUCCESSFULLY "+tokenModelRequest.getUsername()+" AT TIME :"+date);
					   logger.error("USER LOGGED IN SUCCESSFULLY "+tokenModelRequest.getUsername()+" AT TIME :"+date);
					   
					return new ResponseEntity<Object>(response1, HttpStatus.OK);
				} else {
					ResponseModel rm = new ResponseModel();
					rm.setStatus(202);
					rm.setMessage("Failure");
					rm.setDescription("Invalid User Credential");
					
					logger.info("USER "+tokenModelRequest.getUsername()+"PROVIDED WRONG CREDENTIALS AT TIME :"+date);
					logger.debug("USER "+tokenModelRequest.getUsername()+"PROVIDED WRONG CREDENTIALS AT TIME :"+date);
					logger.error("USER "+tokenModelRequest.getUsername()+"PROVIDED WRONG CREDENTIALS AT TIME :"+date);
					   
					// ObjectMapper Obj = new ObjectMapper();
					// String jsonStr = Obj.writeValueAsString(rm);

					// ENcryption Technique
					// String encaccessResponse =
					// encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);

					return new ResponseEntity<Object>(rm, HttpStatus.OK);
				}
			}

		} catch (ResourceAccessException ex) {
			ResponseModel rm = new ResponseModel();
			rm.setStatus(1);
			rm.setMessage("resource not accesible");
			rm.setDescription(ex.getMessage());

			// String encrm = encryptandDecryptAES.EncryptAESECBPKCS5Padding(rm.toString());
			return new ResponseEntity<Object>(rm, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
//				ResponseModel rm = new ResponseModel();
//				rm.setStatus(202);
//				rm.setMessage("Failure");
//				rm.setDescription(e.getMessage());
			e.printStackTrace();
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put("status", 202);
			response.put("isFiveAttempt", "N");
			// Insert record in enable_disable_user for invalid attempt.
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			Date date1 = ts;
			EnableDisableUserEntity enableDisableUserEntity = new EnableDisableUserEntity();
			enableDisableUserEntity = enableDisableUserRepository
					.getInvalidAttemptCount(tokenModelRequest.getUsername());
			if (enableDisableUserEntity != null && enableDisableUserEntity.getId() != null
					&& enableDisableUserEntity.getInvalidAttemptCount() < 5) {
				invalidAttemptCount = enableDisableUserEntity.getInvalidAttemptCount();
				invalidAttemptCount += 1;
				enableDisableUserEntity.setInvalidAttemptCount(invalidAttemptCount);
				enableDisableUserEntity.setDisabledTime(date1);
				if (invalidAttemptCount == 5) {
					// sent identifier to UI for disabling
					enableDisableUserEntity.setIsDisabled("Y");
					response.put("isFiveAttempt", "Y");
					masterUsersRepository.DisabledByUsername(tokenModelRequest.getUsername());
					
					logger.info("USER "+tokenModelRequest.getUsername()+"IS DISABLED AT TIME :"+date);
					   logger.debug("USER "+tokenModelRequest.getUsername()+"IS DISABLED AT TIME :"+date);
					   logger.error("USER "+tokenModelRequest.getUsername()+"IS DISABLED AT TIME :"+date);
					   
					// portalMasterRepository.disableUserByuserName(tokenModelRequest.getUsername());
				}

				enableDisableUserRepository.save(enableDisableUserEntity);
			} else if (enableDisableUserEntity == null) {
				EnableDisableUserEntity enableDisableUserEntity1 = new EnableDisableUserEntity();
				enableDisableUserEntity1.setUsername(tokenModelRequest.getUsername());
				enableDisableUserEntity1.setInvalidAttemptCount(1);
				enableDisableUserEntity1.setIsDisabled("N");
				enableDisableUserEntity1.setDisabledTime(date);
				enableDisableUserRepository.save(enableDisableUserEntity1);
			}
			response.put("message", "Failure");
			int remainingCount = 0;
			if (enableDisableUserEntity == null) {
				remainingCount = maxCount - 1;
			} else {
				remainingCount = maxCount - enableDisableUserEntity.getInvalidAttemptCount();
			}
			response.put("description", "Invalid User Credentials. Remaining attempts " + remainingCount);
			logger.info("INVALID USER CREDENTIALS ENTERED : "+tokenModelRequest.getUsername()+" : " + date + " : REMAINING COUNT " + " : " + remainingCount  );
			logger.error("INVALID USER CREDENTIALS ENTERED : "+tokenModelRequest.getUsername()+" : " + date + " : REMAINING COUNT " + " : " + remainingCount  );
			logger.debug("INVALID USER CREDENTIALS ENTERED : "+tokenModelRequest.getUsername()+" : " + date + " : REMAINING COUNT " + " : " + remainingCount  );
			logger.debug("Exception Message from RHSSO ------" + e.getMessage());
			logger.error("Exception Message from RHSSO -:::::: " + e);

			// ObjectMapper Obj = new ObjectMapper();
			// String jsonStr = Obj.writeValueAsString(response);

			// ENcryption Technique
			// String encaccessResponse =
			// encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);

			return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
		}
		
	}

	public ResponseEntity<Object> genetateAccessTokenForUser(TokenModel TokenModelRequest) {
		final String baseUrl = rhssoUrl + "/auth/realms/{realms}/protocol/openid-connect/token";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		requestBody.add("username", TokenModelRequest.getUsername());
		requestBody.add("password", TokenModelRequest.getPassword());
		requestBody.add("client_id", TokenModelRequest.getClient_id());
		requestBody.add("grant_type", TokenModelRequest.getGrant_type());
		requestBody.add("client_secret", TokenModelRequest.getClient_secret());
		HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", TokenModelRequest.getClientName());
		ObjectMapper mapper = new ObjectMapper();
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, formEntity, String.class, uriParam);
			if (response.getStatusCode() == HttpStatus.OK && response.getStatusCodeValue() == 200) {
				AccessTokenResponse accessResponse = mapper.readValue(response.getBody(), AccessTokenResponse.class);
				logger.info("Response --" + accessResponse.getAccess_token());
				logger.info("Response --" + accessResponse.getExpires_in());
				logger.info("Response --" + accessResponse.getToken_type());
				return new ResponseEntity<Object>(accessResponse, HttpStatus.OK);
			} else {
				ResponseModel rm = new ResponseModel();
				rm.setStatus(1);
				rm.setMessage("Failure");
				rm.setDescription("Invalid User Credential");
				return new ResponseEntity<Object>(rm, HttpStatus.UNAUTHORIZED);
			}
		} catch (ResourceAccessException ex) {
			return new ResponseEntity<Object>("resource not accesible", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			ResponseModel rm = new ResponseModel();
			rm.setStatus(1);
			rm.setMessage("Failure");
			rm.setDescription(e.getMessage());
			return new ResponseEntity<Object>(rm, HttpStatus.NOT_FOUND);
		}

	}
	
	public UserResponseModel getUsers(String token, String realm) throws Exception {
		UserResponseModel urm = new UserResponseModel();
		String userApiuri = rhssoUrl + "/auth/admin/realms/{realms}/users";
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, token);
		UriComponents userApibuilder = UriComponentsBuilder.fromHttpUrl(userApiuri)
				.build();
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", realm);
		try {
			ResponseEntity<UserResponse[]> userEntityResponse = restTemplate.exchange(userApibuilder.toUriString(),
					HttpMethod.GET, entity, UserResponse[].class, uriParam);
			List<UserResponse> userObj = Arrays.asList(userEntityResponse.getBody());
			urm.setMessage("Success");
			urm.setStatus(200);
			urm.setUserlist(userObj);
		//	ObjectMapper Obj = new ObjectMapper(); 
			// String jsonStr = Obj.writeValueAsString(urm); 
			
			//ENcryption Technique
		//	String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
			
			
			
			return urm;
		} catch (Exception e) {
			urm.setStatus(201);
			urm.setMessage("GetUser : Failure : " + e.getMessage());
			urm.setUserlist(null);
			//ObjectMapper Obj = new ObjectMapper(); 
			// String jsonStr = Obj.writeValueAsString(urm); 
				//ENcryption Technique
			//String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
			return urm;
		}

	}
/*
	public RoleResponseModel getAllRolesByUsername(String token, String username, String realm, String clientid) {
		RoleResponseModel roleResponse = new RoleResponseModel();
		MphRoleMap mphRoleMapObj = new MphRoleMap();
		
		UserResponseModel userObj = getUser(token, realm, username);
		if (userObj.getStatus() == 0) {
			if (!userObj.getUserlist().isEmpty() && userObj.getUserlist().size() > 0) {
				String userId = userObj.getUserlist().get(0).getId();
				List<RoleMap> roleMapList = getRoleMapping(token, realm, userId, clientid);
				if (roleMapList != null && !roleMapList.isEmpty()) {
					
					for(int i=0; i<=roleMapList.size();i++) {
					if(roleMapList.get(i).getRoleName().contains("MPH")) {
						
						mphRoleMapObj.setRoleType(roleMapList.get(i).getRoleName());
						mphRoleMapObj.setRoleName(roleMapList.get(i).getRoleType());
						
						logger.info("ROleName:"+roleMapList.get(i).getRoleName()+"ROleType:"+roleMapList.get(i).getRoleType());
						logger.info("MphName:"+mphRoleMapObj.getRoleName());
						if(mphRoleMapObj.getRoleName().equalsIgnoreCase("MPH_SBI"))
							mphRoleMapObj.setMphName("SBI Bank");
						if(mphRoleMapObj.getRoleName().equalsIgnoreCase("MPH_RBL"))
							mphRoleMapObj.setMphName("RBL Bank");
						if(mphRoleMapObj.getRoleName().equalsIgnoreCase("MPH_ICICI"))
							mphRoleMapObj.setMphName("ICICI Bank");
						
						logger.info("MphName:"+mphRoleMapObj.getMphName());
						
					}
					}
					
					roleResponse.setData(roleMapList);
					roleResponse.setMessage("Success");
					roleResponse.setStatus(0);
					roleResponse.setMphData(mphRoleMapObj);
				} else {
					roleResponse.setMessage("Failure no role found");
					roleResponse.setStatus(1);
				}
			} else {
				roleResponse.setData(null);
				roleResponse.setMessage("Failure no user found");
				roleResponse.setStatus(1);
			}
		} else {
			roleResponse.setData(null);
			roleResponse.setMessage(userObj.getMessage());
			roleResponse.setStatus(1);
		}
		return roleResponse;
	}
*/
	
	public RoleResponseModel getAllRolesByUsername(String token, String username, String realm, String clientid) {
		RoleResponseModel roleResponse = new RoleResponseModel();
		MphRoleMap mphRoleMapObj = new MphRoleMap();



		UserResponseModel userObj = getUser(token, realm, username);
		if (userObj.getStatus() == 0) {
		if (!userObj.getUserlist().isEmpty() && userObj.getUserlist().size() > 0) {
		String userId = userObj.getUserlist().get(0).getId();
		List<RoleMap> roleMapList = getRoleMapping(token, realm, userId, clientid);
		if (roleMapList != null && !roleMapList.isEmpty()) {
		logger.info("ROleName outside if:" + roleMapList.get(0).getRoleName() + "ROleType:"
		+ roleMapList.get(0).getRoleType());
		logger.info("MphName outside if:" + mphRoleMapObj.getRoleName());



		for (RoleMap rm : roleMapList) {
		if (rm.getRoleName().contains("MPH"))
		// if(roleMapList.get(i).getRoleName().contains("MPH_SBI") ||
		// roleMapList.get(i).getRoleName().contains("MPH_ICICI") ||
		// roleMapList.get(i).getRoleName().contains("MPH_RBL"))

		{

			logger.info("ROleName:" + rm.getRoleName() + "ROleType:" + rm.getRoleType());
			mphRoleMapObj.setRoleType(rm.getRoleType());
		mphRoleMapObj.setRoleName(rm.getRoleName());
		MphNameEntity mphNameObj = mphNameRepository.getMPHName(mphRoleMapObj.getRoleName() + "");
		mphRoleMapObj.setMphName(mphNameObj.getMphName());
		/*
		* logger.info("MphName:"+mphRoleMapObj.getRoleName());
		* if(mphRoleMapObj.getRoleName().equalsIgnoreCase("MPH_SBI"))
		* mphRoleMapObj.setMphName("SBI Bank");
		* if(mphRoleMapObj.getRoleName().equalsIgnoreCase("MPH_RBL"))
		* mphRoleMapObj.setMphName("RBL Bank");
		* if(mphRoleMapObj.getRoleName().equalsIgnoreCase("MPH_ICICI"))
		* mphRoleMapObj.setMphName("ICICI Bank");
		* if(mphRoleMapObj.getRoleName().equalsIgnoreCase("MPH_INDIANBANK"))
		*
		* mphRoleMapObj.setMphName("INDIAN Bank");
		*/
		logger.info("MphName:" + mphRoleMapObj.getMphName());



		}
		}
		roleResponse.setData(roleMapList);
		roleResponse.setMessage("Success");
		roleResponse.setStatus(0);
		roleResponse.setMphData(mphRoleMapObj);



		} else {
		roleResponse.setMessage("Failure no role found");
		roleResponse.setStatus(1);
		}
		} else {
		roleResponse.setData(null);
		roleResponse.setMessage("Failure no user found");
		roleResponse.setStatus(1);
		}
		} else {
		roleResponse.setData(null);
		roleResponse.setMessage(userObj.getMessage());
		roleResponse.setStatus(1);
		}
		return roleResponse;
		}
	private UserResponseModel getUser(String token, String realm, String username) {
		UserResponseModel urm = new UserResponseModel();
		String userApiuri = rhssoUrl + "/auth/admin/realms/{realms}/users";
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, token);
		UriComponents userApibuilder = UriComponentsBuilder.fromHttpUrl(userApiuri).queryParam("username", username)
				.build();
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", realm);
		try {
			ResponseEntity<UserResponse[]> userEntityResponse = restTemplate.exchange(userApibuilder.toUriString(),
					HttpMethod.GET, entity, UserResponse[].class, uriParam);
			List<UserResponse> userObj = Arrays.asList(userEntityResponse.getBody());
			urm.setMessage("Success");
			urm.setStatus(0);
			urm.setUserlist(userObj);
		} catch (Exception e) {
			urm.setStatus(1);
			urm.setMessage("GetUser : Failure : " + e.getMessage());
			urm.setUserlist(null);
		}
		return urm;
	}

	private List<ClientResponse> getClient(String token, String realm, String clientId) {
		String clientApiuri = rhssoUrl + "/auth/admin/realms/{realms}/clients";
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, token);
		UriComponents clientApibuilder = UriComponentsBuilder.fromHttpUrl(clientApiuri).queryParam("clientId", clientId)
				.build();
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", realm);
		ResponseEntity<ClientResponse[]> clientEntityResponse = restTemplate.exchange(clientApibuilder.toUriString(),
				HttpMethod.GET, entity, ClientResponse[].class, uriParam);
		List<ClientResponse> clientObj = Arrays.asList(clientEntityResponse.getBody());
		return clientObj;
	}

	private List<RoleMap> getRoleMapping(String token, String realm, String userId, String clientid) {
		String roleApiuri = rhssoUrl
				+ "/auth/admin/realms/{realms}/users/{userid}/role-mappings/clients/{clientid}/composite";
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, token);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", realm);
		uriParam.put("userid", userId);
		uriParam.put("clientid", clientid);
		UriComponents roleApibuilder = UriComponentsBuilder.fromHttpUrl(roleApiuri).build();
		List<RoleMap> roleMapList = new ArrayList<RoleMap>();
		try {
			ResponseEntity<CompositeRoleResponse[]> roleEntityResponse = restTemplate.exchange(
					roleApibuilder.toUriString(), HttpMethod.GET, entity, CompositeRoleResponse[].class, uriParam);
			List<CompositeRoleResponse> compositeRoleList = Arrays.asList(roleEntityResponse.getBody());
			/* Being Developed for only one role type */
			Optional<CompositeRoleResponse> compositeKey = compositeRoleList.stream()
					.filter(CompositeRoleResponse::isComposite).findFirst();

			for (CompositeRoleResponse crr : compositeRoleList) {
				RoleMap rm = new RoleMap();
				if (!crr.isComposite()) {
					rm.setRoleName(crr.getName());
					if(compositeKey.isPresent())
					{
						rm.setRoleType(compositeKey.get().getName());
					}
					roleMapList.add(rm);
				}
			}
			return roleMapList;
		} catch (Exception e) {
			return roleMapList;
		}
	}

	public AccessTokenResponse getRefreshTokenWithAccessToken(RequestTokenModel refreshtokenmodel) {
		final String uri = rhssoUrl + "/auth/realms/{realms}/protocol/openid-connect/token";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();

		requestBody.add("client_id", refreshtokenmodel.getClient_id());
		requestBody.add("client_secret", refreshtokenmodel.getClient_secret());
		requestBody.add("refresh_token", refreshtokenmodel.getRefreshToken());
		requestBody.add("grant_type", "refresh_token");
		HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", refreshtokenmodel.getRealms());
		AccessTokenResponse response = restTemplate.postForObject(uri, formEntity, AccessTokenResponse.class, uriParam);
		return response;
	}

	public TokenStatus checkAccessToken(RequestTokenModel refreshtokenmodel) {
		final String uri = rhssoUrl + "/auth/realms/{realms}/protocol/openid-connect/token/introspect";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();

		requestBody.add("token", refreshtokenmodel.getAccessToken());
		requestBody.add("client_id", refreshtokenmodel.getClient_id());
		requestBody.add("client_secret", refreshtokenmodel.getClient_secret());
		HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", refreshtokenmodel.getRealms());
		TokenStatus response = restTemplate.postForObject(uri, formEntity, TokenStatus.class, uriParam);
		return response;
	}
	
	
	/*public Map<String, Object> getDetailsFromConcureciaApi(UserDetailsResponse userDetailsRequestModel) {
		final String uri = applicationUrl + "/HRM_WebService/service/WSJ_51_00003/post";
		
		//restTemplate.setMessageConverters(getXmlMessageConverters());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String, Object> ob = new HashMap<String, Object>();
		//MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		UserDetailsResponse userDetails = new UserDetailsResponse();
		userDetails.setSrnumber(userDetailsRequestModel.getSrnumber());
		userDetails.setEmailId(userDetailsRequestModel.getEmailId());
		HttpEntity<UserDetailsResponse> httpEntityObj = new HttpEntity<UserDetailsResponse>(userDetails,headers);

		
		ResponseEntity<String> responseEntity = restTemplate.exchange(applicationUrl, HttpMethod.POST,
				httpEntityObj,String.class );
		ob = responseEntity.getBody();
		logger.info("RedhatUserGenerationService : getEmployeedetailsfromConcurrecia : " + ob);

		return ob;
	}*/
	
	public ResponseEntity<Object> deleteUser(String token, String realms ,String userid) throws Exception {
		ResponseModel rm = new ResponseModel();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// String resetApiUrl = rhssoUrl +
		// "/auth/admin/realms/{realms}/users/{userid}/reset-password";
		String resetApiUrl = rhssoUrl + "/auth/admin/realms/{realms}/users/{userid}";

		 Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", realms);
		uriParam.put("userid", userid);
		UriComponents resetApibuilder = UriComponentsBuilder.fromHttpUrl(resetApiUrl).build();
		HttpEntity resetentity = new HttpEntity<>(headers);
		try {
			ResponseEntity<?> resetEntityResponse = restTemplate.exchange(resetApibuilder.toUriString(), HttpMethod.DELETE,
					resetentity, String.class, uriParam);
			if (resetEntityResponse.getStatusCode().equals(HttpStatus.NO_CONTENT)
					&& resetEntityResponse.getStatusCodeValue() == 204) {
				rm.setStatus(200);
				rm.setMessage("Success");
				rm.setDescription("User has been deleted");
				ObjectMapper Obj = new ObjectMapper(); 
				 String jsonStr = Obj.writeValueAsString(rm); 
				
				//ENcryption Technique
				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
					return new ResponseEntity<Object>(encaccessResponse, HttpStatus.OK);
			} else {
				rm.setStatus(201);
				rm.setMessage("failure");
				rm.setDescription("delete User failure");
				ObjectMapper Obj = new ObjectMapper(); 
				 String jsonStr = Obj.writeValueAsString(rm); 
				
				//ENcryption Technique
				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
					return new ResponseEntity<Object>(encaccessResponse, HttpStatus.OK);
			}
		
		} catch (Exception e) {
			rm.setStatus(201);
			rm.setMessage("failure");
			rm.setDescription("userId" +userid+  "error :" + e.getMessage());
			ObjectMapper Obj = new ObjectMapper(); 
			 String jsonStr = Obj.writeValueAsString(rm); 
			
			//ENcryption Technique
			String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
				return new ResponseEntity<Object>(encaccessResponse, HttpStatus.OK);
		}
	}
	
	public ResponseEntity<String> searchUserByEmail(String token, String realm, String email) throws Exception {
		UserResponseModel urm = new UserResponseModel();
		String userApiuri = rhssoUrl + "/auth/admin/realms/{realms}/users";
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, token);
		UriComponents userApibuilder = UriComponentsBuilder.fromHttpUrl(userApiuri).queryParam("email", email).build();
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", realm);
		try {
			ResponseEntity<UserResponse[]> userEntityResponse = restTemplate.exchange(userApibuilder.toUriString(),
					HttpMethod.GET, entity, UserResponse[].class, uriParam);
			List<UserResponse> userObj = Arrays.asList(userEntityResponse.getBody()).stream().filter(s -> s.getEmail().equalsIgnoreCase(email)).collect(Collectors.toList());
			urm.setMessage("Success");
			urm.setStatus(0);
			urm.setUserlist(userObj);
			ObjectMapper Obj = new ObjectMapper(); 
			 String jsonStr = Obj.writeValueAsString(urm); 
			
			//ENcryption Technique
			String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);			
			return new ResponseEntity<String>(encaccessResponse, HttpStatus.OK);
		} catch (Exception e) {
			urm.setStatus(1);
			urm.setMessage("GetUser : Failure : " + e.getMessage());
			urm.setUserlist(null);
			ObjectMapper Obj = new ObjectMapper(); 
			 String jsonStr = Obj.writeValueAsString(urm); 
				//ENcryption Technique
			String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
			return new ResponseEntity<String>(encaccessResponse, HttpStatus.UNAUTHORIZED);
		}

	}
	
	public ResponseEntity<Object> getDetailsFromConcureciaApi(UserDetailsRequestModel userDetailsRequestModel) {
		final String uri = applicationUrl + "/HRM_WebService/service/WSJ_51_00003/post";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> requestBody = new HashMap<String, String>();

		requestBody.put("srnumber", userDetailsRequestModel.getSrnumber());
		requestBody.put("emailId", userDetailsRequestModel.getEmailId());
		HttpEntity formEntity = new HttpEntity<Map<String, String>>(requestBody, headers);
		logger.debug("formEntity "+ formEntity);
		logger.debug("formEntity "+ formEntity);
		//Map<String, String> uriParam = new HashMap<>();
		//uriParam.put("realms", refreshtokenmodel.getRealms());
		ObjectMapper mapper = new ObjectMapper();
		try {
			logger.info("ENTERING THE REST TEMPLATE");
			ResponseEntity<String> response = restTemplate.postForEntity(uri, formEntity, String.class);
			logger.info("RESPONSE FROM THE REST TEMPLATE");
			if (response.getStatusCode() == HttpStatus.OK && response.getStatusCodeValue() == 200) {
				UserDetailsResponse accessResponse = mapper.readValue(response.getBody(), UserDetailsResponse.class);
				String mobile = accessResponse.getMobileNumber();
				
				String newMobile = "";
				int ind = 0;
				for (int i = 0; i < mobile.length(); i++) {
		            char p = mobile.charAt(i);
		            if (p != '0') {
		                ind = i;
		                break;
		            }
		        }
				newMobile = mobile.substring(ind, mobile.length());
				accessResponse.setMobileNumber(newMobile);
				
				logger.debug("Response --" + accessResponse.getClassCode());
				logger.debug("Response --" + accessResponse.getSrnumber());
				logger.error("Response --" + accessResponse.getEmailId());
				return new ResponseEntity<Object>(accessResponse, HttpStatus.OK);
			} else {
				logger.info("MOVING INTO ELSE BLOCK");
				ResponseModel rm = new ResponseModel();
				rm.setStatus(1);
				rm.setMessage("Failure");
				rm.setDescription("Invalid User Detail");
				return new ResponseEntity<Object>(rm, HttpStatus.UNAUTHORIZED);
			}
		} catch (ResourceAccessException ex) {
			return new ResponseEntity<Object>("resource not accesible", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			ResponseModel rm = new ResponseModel();
			rm.setStatus(1);
			rm.setMessage("Failure");
			rm.setDescription(e.getMessage());
			return new ResponseEntity<Object>(rm, HttpStatus.NOT_FOUND);
		}

	}
	
	public ResponseEntity<Object> getAllSessionDetailsFromRhsso(SessionModel sessionModelRequest) {
		

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> requestBody = new HashMap<String, String>();

		requestBody.put("access_token", sessionModelRequest.getAccessToken());
		requestBody.put("refreshToken", sessionModelRequest.getRefreshToken());
		requestBody.put("client_id", sessionModelRequest.getClient_id());
		requestBody.put("client_secret", sessionModelRequest.getClient_secret());
		requestBody.put("realms", sessionModelRequest.getRealms());
		requestBody.put("username", sessionModelRequest.getUserName());
		requestBody.put("userId", sessionModelRequest.getUserId());
		requestBody.put("refreshToken", sessionModelRequest.getRefreshToken());
		requestBody.put("browser",sessionModelRequest.getBrowser());
		
		HttpEntity formEntity = new HttpEntity<Map<String, String>>(requestBody, headers);
		logger.debug("formEntity "+ formEntity);
		logger.debug("formEntity "+ formEntity);
		//Map<String, String> uriParam = new HashMap<>();
		//uriParam.put("realms", refreshtokenmodel.getRealms());
		ObjectMapper mapper = new ObjectMapper();
		try {
			RequestTokenModel refreshtokenmodel = new RequestTokenModel();
			refreshtokenmodel.setAccessToken(sessionModelRequest.getAccessToken());
			refreshtokenmodel.setRefreshToken(sessionModelRequest.getRefreshToken());
			refreshtokenmodel.setClient_id(sessionModelRequest.getClient_id());
			refreshtokenmodel.setClient_secret(sessionModelRequest.getClient_secret());
			refreshtokenmodel.setRealms(sessionModelRequest.getRealms());
			TokenStatus response = redhatUserGenerationService.checkAccessToken(refreshtokenmodel);
			if (response.isActive()) {
			UserResponseModel urm = new UserResponseModel();
			String userApiuri = rhssoUrl + "/auth/admin/realms/{realms}/users/{userId}/sessions";
			HttpHeaders headersNew = new HttpHeaders();
			headersNew.setContentType(MediaType.APPLICATION_JSON);
			String token1 = "Bearer "+generateToken().getAccess_token();
			headersNew.add(HttpHeaders.AUTHORIZATION, token1);
			UriComponents userApibuilder = UriComponentsBuilder.fromHttpUrl(userApiuri)
						.build();
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headersNew);
			Map<String, String> uriParam = new HashMap<>();
			uriParam.put("realms", sessionModelRequest.getRealms());
			uriParam.put("userId", response.getSub());
				
			ResponseEntity<UserSessionResponse[]> userEntityResponse = restTemplate.exchange(userApibuilder.toUriString(),
			HttpMethod.GET, entity, UserSessionResponse[].class, uriParam);
			List<UserSessionResponse> userObj = Arrays.asList(userEntityResponse.getBody());
			if(userObj.size()>1) {
			ResponseModel rm = new ResponseModel();
			rm.setStatus(201);
			rm.setMessage("Failure");
			rm.setDescription("More than one Active Session is active for this user");
			return new ResponseEntity<Object>(rm, HttpStatus.UNAUTHORIZED);
					} else {
				List<LoggedInUserSessionDetailsEntity> userSessionDetails = loggedinuserSessionDetailsRepository.getAllUserSessionsBasedOnUserNameAndBrowser(sessionModelRequest.getUserName(),sessionModelRequest.getBrowser());
				if(userSessionDetails.size()>=1) {
				String browser = sessionModelRequest.getBrowser();
				if(!userSessionDetails.get(0).getBrowser().equalsIgnoreCase(browser)) {
				ResponseModel rm = new ResponseModel();
				rm.setStatus(201);
				rm.setMessage("Failure");
				rm.setDescription("User is already logged in with another browser");
				return new ResponseEntity<Object>(rm, HttpStatus.UNAUTHORIZED);
				} else {
							ResponseModel rm = new ResponseModel();
							rm.setStatus(200);
							rm.setMessage("Success");
							rm.setDescription("Get All User session successfull");
							return new ResponseEntity<Object>(rm, HttpStatus.OK);
						}
					}else {
						ResponseModel rm = new ResponseModel();
						rm.setStatus(201);
						rm.setMessage("Failure");
						rm.setDescription("User is already logged in with another browser");
						return new ResponseEntity<Object>(rm, HttpStatus.UNAUTHORIZED);
					}
					
					}
					
					
				
				
			} else {
				logger.info("MOVING INTO ELSE BLOCK");
				ResponseModel rm = new ResponseModel();
				rm.setStatus(201);
				rm.setMessage("Failure");
				rm.setDescription("Token has been expired");
				return new ResponseEntity<Object>(rm, HttpStatus.UNAUTHORIZED);
			}
		} catch (ResourceAccessException ex) {
			return new ResponseEntity<Object>("resource not accesible", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			ResponseModel rm = new ResponseModel();
			rm.setStatus(201);
			rm.setMessage("Failure");
			rm.setDescription(e.getMessage());
			return new ResponseEntity<Object>(rm, HttpStatus.NOT_FOUND);
		}

	}

	public ResponseModel validateEmailMobile(String token, String username,
			ValidateEmailMobileRequestModel validateRequestModel) {
		/* TODO Email and Mobile validation */
		ResponseModel rm = new ResponseModel();
		try {
			String prefix = "LICRESETPWD_" + username;
			String sha256hex = DigestUtils.sha256Hex(prefix);
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, token);
			rm.setStatus(0);
			rm.setMessage("success");
			rm.setDescription(sha256hex);
		} catch (Exception e) {
			rm.setStatus(1);
			rm.setMessage("failure");
			rm.setDescription("Error occured : " + e.getMessage());
		}
		return rm;
	}
	
	
	public ResponseEntity<String> searchUserByUsernameForSuperAdmin(String token, String realm, String userName) throws Exception {
		UserResponseModel urm = new UserResponseModel();
		String userApiuri = rhssoUrl + "/auth/admin/realms/{realms}/users";
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, token);
		UriComponents userApibuilder;
		
		PortalMasterEntity portalMasterEntity = portalMasterRepository.getMasterUserIdByUserName(userName);
		
		if(portalMasterEntity == null)
		{
			userApibuilder = UriComponentsBuilder.fromHttpUrl(userApiuri).queryParam("username", userName).build();
		}
		else
		{
			userApibuilder = UriComponentsBuilder.fromHttpUrl(userApiuri).queryParam("email", portalMasterEntity.getEmail()).build();
		}
		 
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", realm);
		try {
			ResponseEntity<UserResponse[]> userEntityResponse = restTemplate.exchange(userApibuilder.toUriString(),
					HttpMethod.GET, entity, UserResponse[].class, uriParam);
			List<UserResponse> userObj = Arrays.asList(userEntityResponse.getBody());
			urm.setMessage("Success");
			urm.setStatus(0);
			urm.setUserlist(userObj);
			ObjectMapper Obj = new ObjectMapper(); 
			 String jsonStr = Obj.writeValueAsString(urm); 
			
			//ENcryption Technique
			String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);			
			return new ResponseEntity<String>(encaccessResponse, HttpStatus.OK);
		} catch (Exception e) {
			urm.setStatus(1);
			urm.setMessage("GetUser : Failure : " + e.getMessage());
			urm.setUserlist(null);
			ObjectMapper Obj = new ObjectMapper(); 
			 String jsonStr = Obj.writeValueAsString(urm); 
				//ENcryption Technique
			String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
			return new ResponseEntity<String>(encaccessResponse, HttpStatus.UNAUTHORIZED);
		}

	}	
//	public UserResponseModel searchUserByUsername(String token, String realm, String userName) throws Exception {
//		UserResponseModel urm = new UserResponseModel();
//		String userApiuri = rhssoUrl + "/auth/admin/realms/{realms}/users";
//		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.AUTHORIZATION, token);
//		UriComponents userApibuilder = UriComponentsBuilder.fromHttpUrl(userApiuri).queryParam("search", userName).build();
//		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
//		Map<String, String> uriParam = new HashMap<>();
//		uriParam.put("realms", realm);
//		try {
//			ResponseEntity<UserResponse[]> userEntityResponse = restTemplate.exchange(userApibuilder.toUriString(),
//					HttpMethod.GET, entity, UserResponse[].class, uriParam);
//			List<UserResponse> userObj = Arrays.asList(userEntityResponse.getBody());
//			urm.setMessage("Success");
//			urm.setStatus(200);
//			urm.setUserlist(userObj);
//			//ObjectMapper Obj = new ObjectMapper(); 
//			// String jsonStr = Obj.writeValueAsString(urm); 
//			
//			//ENcryption Technique
//		//	String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);			
//			//return new ResponseEntity<String>(urm, HttpStatus.OK);
//		} catch (Exception e) {
//			urm.setStatus(201);
//			urm.setMessage("GetUser : Failure : " + e.getMessage());
//			urm.setUserlist(null);
//			//ObjectMapper Obj = new ObjectMapper(); 
//			// String jsonStr = Obj.writeValueAsString(urm); 
//				//ENcryption Technique
//			//String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
//			//return new ResponseEntity<String>(encaccessResponse, HttpStatus.UNAUTHORIZED);
//		}
//		return urm;
//
//	}
	
	
	public UserResponseModel searchUserByUsername(String token, String realm, String userName) throws Exception {
		UserResponseModel urm = new UserResponseModel();
		String userApiuri = rhssoUrl + "/auth/admin/realms/{realms}/users";
		HttpHeaders headers = new HttpHeaders();
		String token1 = "Bearer "+generateToken().getAccess_token();
		headers.add(HttpHeaders.AUTHORIZATION, token1);
		UriComponents userApibuilder = UriComponentsBuilder.fromHttpUrl(userApiuri).queryParam("search", userName).build();
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", realm);
		List<UserResponse> userResponse=new ArrayList<>();
		try {
			ResponseEntity<UserResponse[]> userEntityResponse = restTemplate.exchange(userApibuilder.toUriString(),
					HttpMethod.GET, entity, UserResponse[].class, uriParam);
			List<UserResponse> userObj = Arrays.asList(userEntityResponse.getBody());
			
			if(userObj!=null && userObj.size()>0) {
				for(UserResponse userResponse1 : userObj) {
					if(userResponse1 != null && userResponse1.getUsername().equalsIgnoreCase(userName)) {
						userResponse.add(userResponse1);
						break;
					}
				}
			}
			
			urm.setMessage("Success");
			urm.setStatus(200);
			urm.setUserlist(userResponse);
			//ObjectMapper Obj = new ObjectMapper(); 
			// String jsonStr = Obj.writeValueAsString(urm); 
			
			//ENcryption Technique
		//	String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);			
			//return new ResponseEntity<String>(urm, HttpStatus.OK);
		} catch (Exception e) {
			urm.setStatus(201);
			urm.setMessage("GetUser : Failure : " + e.getMessage());
			urm.setUserlist(null);
			//ObjectMapper Obj = new ObjectMapper(); 
			// String jsonStr = Obj.writeValueAsString(urm); 
				//ENcryption Technique
			//String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
			//return new ResponseEntity<String>(encaccessResponse, HttpStatus.UNAUTHORIZED);
		}
		return urm;

	}
	
	

	public ResponseModel resetPassword(String token, String username,
			ResetPasswordRequestModel resetPasswordRequestModel) {
		ResponseModel rm = new ResponseModel();
		/* TODO Email and Mobile validation */
		String prefix = "LICRESETPWD_" + username;
		String sha256hex = DigestUtils.sha256Hex(prefix);
		Boolean valid = sha256hex.compareTo(resetPasswordRequestModel.getSha()) == 0 ? true : false;
		if (valid) {
			UserResponseModel userObj = getUser(token, resetPasswordRequestModel.getRealm(), username);
			if (userObj.getStatus() == 0) {
				if (!userObj.getUserlist().isEmpty() && userObj.getUserlist().size() > 0) {
					String userId = userObj.getUserlist().get(0).getId();
					rm = resetPassword(token, resetPasswordRequestModel, userId);
				} else {
					rm.setDescription("no user found");
					rm.setMessage("Failure");
					rm.setStatus(1);
				}
			} else {
				rm.setStatus(1);
				rm.setMessage("failure");
				rm.setDescription(userObj.getMessage());
			}
		} else {
			rm.setStatus(1);
			rm.setMessage("failure");
			rm.setDescription("Invalid SHA");
		}

		return rm;
	}

	private ResponseModel resetPassword(String token, ResetPasswordRequestModel resetPasswordRequestModel,
			String userId) {
		ResponseModel rm = new ResponseModel();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		String resetApiUrl = rhssoUrl + "/auth/admin/realms/{realms}/users/{userid}/reset-password";

		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("realms", resetPasswordRequestModel.getRealm());
		uriParam.put("userid", userId);
		UriComponents resetApibuilder = UriComponentsBuilder.fromHttpUrl(resetApiUrl).build();
		ResetPasswordModel rpm = new ResetPasswordModel();
		rpm.setTemporary(false);
		rpm.setType("password");
		rpm.setValue(resetPasswordRequestModel.getPassword());
		HttpEntity<ResetPasswordModel> resetentity = new HttpEntity<>(rpm, headers);
		try {
			ResponseEntity<?> resetEntityResponse = restTemplate.exchange(resetApibuilder.toUriString(), HttpMethod.PUT,
					resetentity, String.class, uriParam);
			if (resetEntityResponse.getStatusCode().equals(HttpStatus.NO_CONTENT)
					&& resetEntityResponse.getStatusCodeValue() == 204) {
				rm.setStatus(0);
				rm.setMessage("Success");
				rm.setDescription("Password has been reset");
			} else {
				rm.setStatus(1);
				rm.setMessage("failure");
				rm.setDescription("reset password failure");
			}
			return rm;
		} catch (Exception e) {
			rm.setStatus(1);
			rm.setMessage("failure");
			rm.setDescription(resetPasswordRequestModel.toString() + "userId" + userId + "error :" + e.getMessage());
			return rm;
		}
	}
	
	 public ResponseEntity<Object> addUser(String token, String realm, AddUserListModel addUserModel) {
			final String baseUrl = rhssoUrl + "/auth/admin/realms/{realm}/users";
			
			 HttpHeaders headers = new HttpHeaders();
			 headers.add(HttpHeaders.AUTHORIZATION, token);
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			
			Map<String, String> uriParam = new HashMap<>();
			uriParam.put("realm",realm);
//			logger.debug("realms         "+realm);
			
		/*	MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
			requestBody.add("username", addUserModel.getUsername());
			//To be removed
			requestBody.add("enabled", "true");
			requestBody.add("firstName", addUserModel.getFirstName());
			requestBody.add("lastName", addUserModel.getLastName());
			requestBody.add("email", addUserModel.getEmail());*/
			
			//requestBody.add("groups", addUserModel.getGroups());
			//requestBody.add("credentials", addUserModel.getCredentials());
//			logger.debug("username "+ addUserModel.getUsername());
//			logger.debug("lastName "+ addUserModel.getLastName());
//			logger.debug("email "+ addUserModel.getEmail());
			//logger.debug("groups "+ addUserModel.getGroups());
			//logger.debug("credentials "+ addUserModel.getCredentials());
//			logger.debug("requestBody "+ requestBody);
			
			
			ResponseModel rm = new ResponseModel();
			try {
				
				ObjectMapper Obj = new ObjectMapper(); 
				 String jsonStr = Obj.writeValueAsString(addUserModel); 
				
				
				HttpEntity formEntity = new HttpEntity(jsonStr, headers);
				logger.debug("formEntity "+ formEntity);
		    	//ObjectMapper mapper = new ObjectMapper();
				
				logger.debug("baseUrl:"+baseUrl);
				logger.debug("formEntity:"+formEntity);
				//ResponseEntity<Object> response = restTemplate.postForEntity(baseUrl, formEntity, Object.class, uriParam);
				ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, formEntity, String.class, uriParam);
				logger.debug("response for add user "+ response);
				logger.debug("response for add user 2 "+ response.getStatusCode());
				if (response.getStatusCode().equals(HttpStatus.CREATED) && response.getStatusCodeValue() == 201) {
					rm.setStatus(201);
					rm.setMessage("Success");
					rm.setDescription("User has been added");
					
					return new ResponseEntity<Object>(rm, HttpStatus.OK);

				} else {
					rm.setStatus(202);
					rm.setMessage("failure");
					rm.setDescription("add user failure");
					
					return new ResponseEntity<Object>(rm, HttpStatus.ACCEPTED);
				}
			
			} catch (HttpServerErrorException ex) {
				rm.setStatus(1);
				rm.setMessage("failure");
				rm.setDescription( addUserModel.toString() + "error :" + ex.getMessage());
				logger.error(" add user exception occured." + ex.getMessage());
				return new ResponseEntity<Object>(HttpStatus.ACCEPTED);
			
			} 
			catch (Exception e) {
				rm.setStatus(1);
				rm.setMessage("failure");
				rm.setDescription( addUserModel.toString() + "error :" + e.getMessage());
				logger.error(" add user   exception occured." + e.getMessage());
				return new ResponseEntity<Object>(HttpStatus.ACCEPTED);
			}

		}
	 
	 
	 public ResponseModel getlogoutAllSessionsForUser(String token, String realm, String userId,String userName) throws Exception {
			ResponseModel rm = new ResponseModel();
			Date date=new Date();
			String userApiuri = rhssoUrl + "auth/admin/realms/{realm}/users/{userid}/logout";
			HttpHeaders headers = new HttpHeaders();
			String token1 = "Bearer "+generateToken().getAccess_token();
			headers.add(HttpHeaders.AUTHORIZATION, token1);
			UriComponents userApibuilder = UriComponentsBuilder.fromHttpUrl(userApiuri).build();

			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);
			Map<String, String> uriParam = new HashMap<>();
			uriParam.put("realm", realm);
			uriParam.put("userid", userId);
			HttpEntity resetentity = new HttpEntity<>(headers);
			try {
				ResponseEntity<?> resetEntityResponse = restTemplate.exchange(userApibuilder.toUriString(), HttpMethod.POST,
						resetentity, String.class, uriParam);
				if (resetEntityResponse.getStatusCode().equals(HttpStatus.NO_CONTENT)
						&& resetEntityResponse.getStatusCodeValue() == 204) {
					loggedinuserSessionDetailsRepository.DeleteLoggedInUserByUserName(userName);
					rm.setStatus(200);
					rm.setMessage("Success");
					rm.setDescription("User has been logged out");
					
					logger.info("USER "+userName+ " LOGGED OUT AT "+date);
					logger.debug("USER "+userName+ " LOGGED OUT AT "+date);
					logger.error("USER "+userName+ " LOGGED OUT AT "+date);
					
				} else {
					rm.setStatus(201);
					rm.setMessage("failure");
					rm.setDescription("user logout failure");
				}
				return rm;
			} catch (Exception e) {
				rm.setStatus(1);
				rm.setMessage("failure");
				rm.setDescription(  "userId" + userId + "error :" + e.getMessage());
				return rm;
			}
		}
	 
	 
	 private List<HttpMessageConverter<?>> getXmlMessageConverters() {
		    XStreamMarshaller marshaller = new XStreamMarshaller();
		    marshaller.setAnnotatedClasses();
		    MarshallingHttpMessageConverter marshallingConverter = 
		      new MarshallingHttpMessageConverter(marshaller);

		    List<HttpMessageConverter<?>> converters = new ArrayList<>();
		    converters.add(marshallingConverter);
		    return converters;
		}
	 
		public ResponseEntity<Object> assignRoleToUser(String token, RolesListModel rolemodel, ArrayList<RolesModel> roleModelList)
		//public ResponseModel assignRoleToUser(String token, String realm, String userid, String clientid)
		{
			String apiUrl = rhssoUrl + "/auth/admin/realms/{realm}/users/{userid}/role-mappings/clients/{clientid}";
			
			
			 ResponseModel rm = new ResponseModel();
			//RoleResponseModel rm = new RoleResponseModel();
			HttpHeaders headers = new HttpHeaders();
			 headers.add(HttpHeaders.AUTHORIZATION, token);
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			/*MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<String, Object>();
			requestBody.add("id", rolemodel.getId());
			requestBody.add("name", rolemodel.getName());
			requestBody.add("composite", rolemodel.isComposite());
			requestBody.add("clientRole", rolemodel.isClientRole());
			logger.debug(rolemodel.getId());
			logger.debug(rolemodel.getName());
			logger.debug("requestBody " +requestBody);*/
			// String resetApiUrl = rhssoUrl +
			// "/auth/admin/realms/{realms}/users/{userid}/reset-password";
			
			
			Map<String, String> uriParam = new HashMap<>();
			uriParam.put("realm", rolemodel.getRealm());
			uriParam.put("userid", rolemodel.getUserid());
			uriParam.put("clientid", rolemodel.getClientid());
			logger.debug("token" + token +"realm" +rolemodel.getRealm()+ "userid" +rolemodel.getUserid()+ "clientid" +rolemodel.getClientid()+ "");
			UriComponents resetApibuilder = UriComponentsBuilder.fromHttpUrl(apiUrl).build();
			
			/*
			RolesModel rpm = new RolesModel();
			rpm.setId(rolemodel.getId());
			rpm.setName(rolemodel.getName());
			rpm.setComposite(rolemodel.isComposite());
			rpm.setClientRole(rolemodel.isClientRole());
			HttpEntity<RolesModel> resetentity = new HttpEntity<>(rpm, headers);
			*/
			
			//HttpEntity resetentity = new HttpEntity<>(headers);
			//HttpEntity resetentity = new HttpEntity<>(requestBody, headers);
			//logger.debug("API url "+ resetApibuilder);
			try {
				
				ObjectMapper Obj = new ObjectMapper(); 
				
				 String jsonStr = Obj.writeValueAsString(roleModelList); 
				
				
				HttpEntity formEntity = new HttpEntity(jsonStr, headers);
				logger.debug("baseUrl:"+apiUrl);
				logger.debug("formEntity:"+formEntity);
				
				ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, formEntity, String.class, uriParam);
				//ResponseEntity<String> resetEntityResponse = restTemplate.exchange(resetApibuilder.toUriString(), HttpMethod.POST,resetentity, String.class, uriParam);
			//	ResponseEntity<RolesModel[]> resetEntityResponse = restTemplate.exchange(resetApibuilder.toUriString(), HttpMethod.POST,resetentity, RolesModel[].class, uriParam);
				logger.debug("response for assign user "+ response);
				logger.debug("response for assign user 2 "+ response.getStatusCode());
				
				if (response.getStatusCode().equals(HttpStatus.NO_CONTENT)
						&& response.getStatusCodeValue() == 204) {
					rm.setStatus(0);
					rm.setMessage("Success: Role assignment has been done");
					//rm.setDescription("Role assignment has been done");
					 String jsonStr1 = Obj.writeValueAsString(rm); 
	         String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr1);	
					
					return new ResponseEntity<Object>(encaccessResponse, HttpStatus.OK);

				} else {
					rm.setStatus(1);
					rm.setMessage("failure: Role assignment for user failed");
					//rm.setDescription("Role assignment for user failed");
					 String jsonStr1 = Obj.writeValueAsString(rm); 
	          String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr1);	
					
					return new ResponseEntity<Object>(encaccessResponse, HttpStatus.UNAUTHORIZED);

				}
			} 
			catch (RestClientException ex) {
				rm.setStatus(1);
				rm.setMessage("failure");
				return new ResponseEntity<Object>(rm, HttpStatus.UNAUTHORIZED);
				}
			
			catch (Exception e) {
				rm.setStatus(1);
				rm.setMessage("failure");
				//rm.setDescription("userId" +userid+ "clientid" +clientid+  "error :" + e.getMessage());
				logger.error(" assign role to users exception occured." + e.getMessage());
				return new ResponseEntity<Object>(rm, HttpStatus.UNAUTHORIZED);
			}
			
		}
		
		 
		 public AccessTokenResponse generateToken() 
			{	
				AccessTokenResponse accessResponse = null;
				try
				{
					logger.info("Method - generateToken Started");
					final String baseUrl = rhssoUrl + "/auth/realms/{realms}/protocol/openid-connect/token";
					TokenModel tokenModelRequest = new TokenModel();
					tokenModelRequest.setClient_id("epgssit-v1-1");
					tokenModelRequest.setClient_secret("eeef4aa4-c227-42b9-9a25-96dbc59275d7");
					tokenModelRequest.setGrant_type("password");
					tokenModelRequest.setClientName("epgs");
					tokenModelRequest.setUsername("tokenadminuser");
					tokenModelRequest.setPassword("dG9rZW5hZG1pbnVzZXI=");

					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
					MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
					requestBody.add("username", tokenModelRequest.getUsername());
					requestBody.add("password", tokenModelRequest.getPassword());
					requestBody.add("client_id", tokenModelRequest.getClient_id());
					requestBody.add("grant_type", tokenModelRequest.getGrant_type());
					requestBody.add("client_secret", tokenModelRequest.getClient_secret());
					HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
					Map<String, String> uriParam = new HashMap<>();
					uriParam.put("realms", tokenModelRequest.getClientName());
					ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, formEntity, String.class, uriParam);
					ObjectMapper mapper = new ObjectMapper();
					logger.debug("Response code from Token API ------" + response.getStatusCodeValue());
					if (response.getStatusCode() == HttpStatus.OK && response.getStatusCodeValue() == 200) 
					{
						accessResponse = mapper.readValue(response.getBody(), AccessTokenResponse.class);
						logger.debug("Response --" + accessResponse.getAccess_token());
						logger.debug("Response --" + accessResponse.getExpires_in());
						logger.debug("Response --" + accessResponse.getToken_type());
					} 
					logger.info("Method - generateToken Ended");
				}
				catch(Exception e)
				{
					logger.error("Generate token exception occured." + e.getMessage());
					logger.info("Method - generateToken Started");
				}
				return accessResponse;
			}

	/*
	 * public Map<String, Object> getAllRolesOfTokenUser(TokenUserModel
	 * TokenUserModelRequest) {
	 * 
	 * HashMap<String, String> map = new HashMap<String, String>(); Map<String,
	 * Object> ob = new HashMap<String, Object>(); final String baseUrl =
	 * "https://d1npsso.licindia.com:8443/auth/realms/epgs-claim-portal/protocol/openid-connect/token/introspect";
	 * 
	 * TokenUserModel tokenUserModel = new TokenUserModel();
	 * 
	 * tokenUserModel.setClient_id("usermgmt-claim-portal");
	 * tokenUserModel.setToken(TokenUserModelRequest.getToken());
	 * tokenUserModel.setClient_secret("9625d1d9-751b-493b-a17e-88c0c591f7db");
	 * tokenUserModel.setUsername("sandy123");
	 * 
	 * 
	 * 
	 * HttpEntity<TokenUserModel> entity = new
	 * HttpEntity<TokenUserModel>(tokenUserModel, restHeaders());
	 * 
	 * ResponseEntity<Map<String, Object>> responseEntity =
	 * restTemplate.exchange(baseUrl, HttpMethod.POST, entity, new
	 * ParameterizedTypeReference<Map<String, Object>>() { }); ob =
	 * responseEntity.getBody();
	 * logger.info("RedhatUserGenerationService :  getAllRolesOfTokenUser : token "
	 * + ob );
	 * 
	 * 
	 * return ob; }
	 */
}
