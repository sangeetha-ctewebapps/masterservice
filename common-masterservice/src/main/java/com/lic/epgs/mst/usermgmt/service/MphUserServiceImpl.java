package com.lic.epgs.mst.usermgmt.service; 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.codehaus.jettison.json.JSONArray;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.numeric.BasicDecimalNumberEncryptor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.repository.JDBCConnection;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptandDecryptAES;
import com.lic.epgs.mst.usermgmt.entity.EnableDisableUserEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterPolicyDataEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRolesBulkEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.MphNameEntity;
import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.RolesAssignmentHistory;
import com.lic.epgs.mst.usermgmt.entity.UserDetailHistoryEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.MphUserServiceException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserRoleMappingException;
import com.lic.epgs.mst.usermgmt.modal.LoggedInUserModel;
import com.lic.epgs.mst.usermgmt.modal.RolesModel;
import com.lic.epgs.mst.usermgmt.modal.TokenModel;
import com.lic.epgs.mst.usermgmt.repository.EnableDisableUserRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterPolicyDataRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterRolesBulkRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersRepository;
import com.lic.epgs.mst.usermgmt.repository.MphNameRepository;
import com.lic.epgs.mst.usermgmt.repository.PortalMasterRepository;
import com.lic.epgs.mst.usermgmt.repository.RolesAssignmentRespository;
import com.lic.epgs.mst.usermgmt.repository.UserHistoryDetailsRepository;
import com.lic.epgs.rhssomodel.AccessTokenResponse;
import com.lic.epgs.rhssomodel.AddUserListModel;
import com.lic.epgs.rhssomodel.AddUserModel;
import com.lic.epgs.rhssomodel.CompositeRoleResponse;
import com.lic.epgs.rhssomodel.Credential;
import com.lic.epgs.rhssomodel.MphRoleMap;
import com.lic.epgs.rhssomodel.ResponseModel;
import com.lic.epgs.rhssomodel.RoleMap;
import com.lic.epgs.rhssomodel.RoleResponseModel;
import com.lic.epgs.rhssomodel.RolesListModel;
import com.lic.epgs.rhssomodel.UserResponse;
import com.lic.epgs.rhssomodel.UserResponseModel;




@Service
@Transactional
public class MphUserServiceImpl implements MphUserService{
	RestTemplate restTemplate = new RestTemplate();	
	

	
	
	@Autowired
	private PortalMasterRepository portalMasterRepository;
	
	@Autowired
	private MphNameRepository mphNameRepository;
	
	@Autowired
	private JDBCConnection jdbcConnection;
	
	@Autowired
	private RolesAssignmentRespository rolesAssignmentRespository;
	
	
	
	@Autowired
	private EncryptandDecryptAES encryptandDecryptAES;
	
	
	
	@Autowired
	private UserRoleMappingService userRoleMappingService;
	
	
       
    @Autowired
    MasterPolicyDataRepository masterPolicyDataRepository;
    
    @Autowired
	MasterRolesBulkRepository masterRolesBulkRepository;
	
    @Value("${invalid.credentials.sms.body}")
	private String invalidUserSmsBody;
     
    @Value("${invalid.credentials.email.body}")
 	private String invlidCredentialsEmailBody;
	
	@Value("${disable.user.sms.body}")
	private String disableUserSmsBody;
	
	@Value("${disable.user.email.body}")
	private String disableUserEmailBody;
	
	@Value("${enable.user.sms.body}")
	private String enableUserSmsBody;
	
	@Value("${enable.user.email.body}")
	private String enableUserEmailBody;	

	@Value("${max.attempt.reached.email.body}")
	private String maxAttemptReachedEmailBody;
	
	@Value("${max.attempt.reached.sms.body}")
	private String maxAttemptReachedSMSBody;
	
  
	@Value("${sms.url}")
	private String smsUrl;
	
	@Value("${email.url}")
	private String emailUrl;
	
	@Value("${new.user.email.body}")
	private String emailBody;
  
  
	@Value("${change.password.email.body}")
	private String changePassEmailBody;
	
	@Value("${reset.password.email.body}")
	private String resetPassEmailBody;
	
	@Value("${new.user.msg.body}")
	private String newSmsBody;
	
	@Value("${change.password.msg.body}")
	private String changePassSmsBody;
	
	@Value("${reset.password.msg.body}")
	private String resetPassSmsBody;
	
	
	@Autowired
	private Environment env;

	private int otp;
	private String otpKey="usermgmtotp";
	
	
	
	@Autowired
	private EnableDisableUserRepository enableDisableUserRepository;
	
	@Autowired
	private RedhatUserGenerationService redhatUserGenerationService;
	
	
	
	
	@Value("${rhsso.clientSecret}")
	private String clientSecret;
	
	
	
	
	@Value("${rhsso.makerid}")
	private String rhssomakerid;

	@Value("${rhsso.claimportalid}")
	private String rhssoclaimportalid;
	
	@Value("${application.url}")
	private String applicationurl;
	
	@Value("${rhsso.url}")
	private String rhssoUrl; 
	
    @Autowired
    private UserHistoryDetailsRepository userHistoryDetailsRepository;
    
    @Autowired
    MasterUsersRepository masterUsersRepository;
	
	public HttpHeaders restHeader() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		return headers;
	}

	
	
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(MphUserServiceImpl.class);

	@Override
	public ResponseEntity<Object> addMphUser(PortalMasterEntity portalMasterEntity)
			throws MphUserServiceException {
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> response1 = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug("Enter CoAdminService : " + methodName);

		try {
			if (portalMasterEntity == null) {
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
				 ObjectMapper Obj = new ObjectMapper(); 
				 String jsonStr = Obj.writeValueAsString(response); 
				
				//ENcryption Technique
				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
				response1.put(Constant.STATUS, 201);                 
				response1.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);                
				response1.put("body", encaccessResponse); 
				return new ResponseEntity<Object>(response1, HttpStatus.CREATED);
			}
				 else {
					 MasterPolicyDataEntity masterPolicyDataEntity = masterPolicyDataRepository.getUnitAndZoneFromPolicyNo(portalMasterEntity.getPolicyNumber());
					 portalMasterEntity.setZone(masterPolicyDataEntity.getZoneName());
					 int maxUserSequence = portalMasterRepository.getMaxUserSequence();
	                 int counter = maxUserSequence + 1;
//	                 if(portalMasterEntity.getUserId() != null && portalMasterEntity.getUserId().length() > 3) {
//                         
//                         long useridAndSeq = Long.valueOf(portalMasterEntity.getUserId().substring(3, String.valueOf(obj.getId()).length()));
//                         portalMasterEntity.setUserSequence(useridAndSeq);
//                  }else {                                                
                         String userId = "";
                         int length = String.valueOf(counter).length();
                         if(length==1) {
                                userId = portalMasterEntity.getUnit()+"0000"+String.valueOf(counter);
                         }else if(length==2) {
                                userId = portalMasterEntity.getUnit()+"000"+String.valueOf(counter);
                         }else if(length == 3) {
                                userId = portalMasterEntity.getUnit()+"00"+String.valueOf(counter);
                         }else if(length == 4) {
                                userId = portalMasterEntity.getUnit()+"0"+String.valueOf(counter);
                         }else if(length == 5) {
                                userId = portalMasterEntity.getUnit()+String.valueOf(counter);
                         }
                         portalMasterEntity.setUserSequence(counter);
                         portalMasterEntity.setUserId(userId);
                         
//                  } 
	                 
					 Date today = new Date();
					 portalMasterEntity.setCreatedOn(today);
					 portalMasterEntity.setModifiedOn(today);
					PortalMasterEntity portalAdmin = portalMasterRepository.save(portalMasterEntity);
					MasterUsersEntity masterUsersEntity=masterUsersRepository.getAllMasterUserDetailByUserName(portalMasterEntity.getCreatedBy());
					UserDetailHistoryEntity userDetailHistoryEntity = new UserDetailHistoryEntity();
					userDetailHistoryEntity.setUsername(portalMasterEntity.getUsername());
					userDetailHistoryEntity.setEmail(portalMasterEntity.getEmail());
					userDetailHistoryEntity.setMphname(portalMasterEntity.getMphName());
					userDetailHistoryEntity.setCreatedby(portalMasterEntity.getCreatedBy());
					userDetailHistoryEntity.setCreatedon(portalMasterEntity.getCreatedOn());	
					userDetailHistoryEntity.setAction(portalMasterEntity.getAction());
					userDetailHistoryEntity.setStatus(portalMasterEntity.getStatus());
					userDetailHistoryEntity.setRemark(portalMasterEntity.getRemarks());
					userDetailHistoryEntity.setUnitCode(portalMasterEntity.getUnit());
					userDetailHistoryEntity.setSrNumber(masterUsersEntity.getSrNumber2());
					UserDetailHistoryEntity userHistoryDetails = userHistoryDetailsRepository.save(userDetailHistoryEntity);
					
					response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "SEND FOR APPROVAL SUCCESSFULLY ");
					 ObjectMapper Obj = new ObjectMapper(); 
					 String jsonStr = Obj.writeValueAsString(response); 
					
					//ENcryption Technique
					String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
					response1.put(Constant.STATUS, 200);                 
					response1.put(Constant.MESSAGE, Constant.SUCCESS);                
					response1.put("body", encaccessResponse); 
					return new ResponseEntity<Object>(response1, HttpStatus.OK);
				}
			

		} catch (Exception exception) {
			logger.error("Could not add Mph User " + exception.getMessage());
			 throw new MphUserServiceException ("Internal Server Error");
		}
		//return null;

	}
	
	 @Override
	    public ResponseEntity<Object> deleteMphUser(PortalMasterEntity portalMasterEntity) throws MphUserServiceException{
	        // TODO Auto-generated method stub
			 //EnableDisableUserEntity enableDisableUserEntity= new EnableDisableUserEntity();
	        Map<String, Object> response = new HashMap<String, Object>();
	        Map<String, Object> response1 = new HashMap<String, Object>();
	        response.put(Constant.STATUS, 0);
	        response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	        LoggingUtil.logInfo(className, methodName, "Started");
	        Date today= new Date();
	        try {
	        	  if (portalMasterEntity == null) {
	                response.put(Constant.STATUS, 0);
	                response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	                ObjectMapper Obj = new ObjectMapper(); 
					 String jsonStr = Obj.writeValueAsString(response); 
					
					//ENcryption Technique
					String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
					response1.put(Constant.STATUS, 201);                 
					response1.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);                
					response1.put("body", encaccessResponse); 
					return new ResponseEntity<Object>(response, HttpStatus.OK);
	            } 
	                else {
	                	PortalMasterEntity portalMaster=portalMasterRepository.getMasterUserIdByPortalId(portalMasterEntity.getPortalUserId());
	                	portalMaster.setWorkflowStatus(1L);
	                	portalMaster.setStatus("SentForApproval");
	                	portalMaster.setAction("DELETE");
	                	portalMaster.setModifiedBy(portalMasterEntity.getModifiedBy());
	                	portalMaster.setModifiedOn(today);
	                	portalMaster.setRemarks(portalMasterEntity.getRemarks());
	                	portalMasterRepository.save(portalMaster);
	                	MasterUsersEntity masterUsersEntity=masterUsersRepository.getAllMasterUserDetailByUserName(portalMaster.getCreatedBy());
	                	
	                	UserDetailHistoryEntity userDetailHistoryEntity = new UserDetailHistoryEntity();
						userDetailHistoryEntity.setUsername(portalMaster.getUsername());
						userDetailHistoryEntity.setEmail(portalMaster.getEmail());
						userDetailHistoryEntity.setMphname(portalMaster.getMphName());
						userDetailHistoryEntity.setCreatedby(portalMaster.getCreatedBy());
						userDetailHistoryEntity.setCreatedon(portalMaster.getCreatedOn());
						userDetailHistoryEntity.setAction(portalMaster.getAction());
						userDetailHistoryEntity.setStatus(portalMaster.getStatus());
						userDetailHistoryEntity.setRemark(portalMaster.getRemarks());
						userDetailHistoryEntity.setUnitCode(portalMaster.getUnit());
						userDetailHistoryEntity.setSrNumber(masterUsersEntity.getSrNumber2());
						UserDetailHistoryEntity userHistoryDetails = userHistoryDetailsRepository.save(userDetailHistoryEntity);
						
	                	//enableDisableUserEntity.setUsername(portalMasterEntity.getUsername());
	                	//enableDisableUserEntity.setInvalidAttemptCount(5);
	                	//enableDisableUserEntity.setDisabledTime(today);
	                	//enableDisableUserEntity.setIsDisabled("Y");
	                	//enableDisableUserRepository.save(enableDisableUserEntity);
	            //	portalMasterRepository.findAndDeleteById(portalUserId);
	            	
	                    response.put(Constant.STATUS, 200);
	                    response.put(Constant.MESSAGE, Constant.SUCCESS);
	                    response.put("Data", "SentForApproval Sucessfully");
	                    ObjectMapper Obj = new ObjectMapper();  
						 String jsonStr = Obj.writeValueAsString(response); 
						
						//ENcryption Technique
						String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
						response1.put(Constant.STATUS, 200);                 
						response1.put(Constant.MESSAGE, Constant.SUCCESS);                
						response1.put("body", encaccessResponse); 
						return new ResponseEntity<Object>(response1, HttpStatus.OK);
	                 }
	 
	        } catch (Exception ex) {
	            logger.debug("Could not delete mph portal user due to : " + ex.getMessage());
	            throw new MphUserServiceException ("Internal Server Error");
	        }
	        //return null;
	 
	    }

	
	 @Override
	 public AccessTokenResponse generateToken() 
		{	
			AccessTokenResponse accessResponse = null;
			try
			{
				logger.info("Method - generateToken Started");
				final String baseUrl = rhssoUrl + "/auth/realms/{realms}/protocol/openid-connect/token";
				TokenModel tokenModelRequest = new TokenModel();
				tokenModelRequest.setClient_id("usermgmt-claim-portal");
				tokenModelRequest.setClient_secret(clientSecret);
				tokenModelRequest.setGrant_type("password");
				tokenModelRequest.setClientName("usermgmt_claim_portal");
				tokenModelRequest.setUsername("indianbankmaster");
				tokenModelRequest.setPassword("aW5kaWFuYmFua21hc3Rlcg==");

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

	@Override
	public ResponseEntity<Object> deleteRolesForSuperAdmin(RolesAssignmentHistory rolesAssignmentEntity)
			throws MphUserServiceException {
		  Map<String, Object> response = new HashMap<String, Object>();
	        Map<String, Object> response1 = new HashMap<String, Object>();
	        response.put(Constant.STATUS, 0);
	        response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	        LoggingUtil.logInfo(className, methodName, "Started");
	        Date today= new Date();
	        try {
	        	  if (rolesAssignmentEntity == null) {
	                response.put(Constant.STATUS, 0);
	                response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	                ObjectMapper Obj = new ObjectMapper(); 
					 String jsonStr = Obj.writeValueAsString(response); 
					
					//ENcryption Technique
					String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
					response1.put(Constant.STATUS, 201);                 
					response1.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);                
					response1.put("body", encaccessResponse); 
					return new ResponseEntity<Object>(response, HttpStatus.OK);
	            } 
	                else {
	                	RolesAssignmentHistory portalMaster=rolesAssignmentRespository.getAllRoleDetailByPortalrolesid(rolesAssignmentEntity.getPortalRolesId());
	                	portalMaster.setWorkFlowStatus(1L);
	                	portalMaster.setStatus("SentForApproval");
	                	portalMaster.setModifiedBy(rolesAssignmentEntity.getModifiedBy());
	                	portalMaster.setModifiedOn(today);
	                	rolesAssignmentRespository.save(portalMaster);
	                	//enableDisableUserEntity.setUsername(portalMasterEntity.getUsername());
	                	//enableDisableUserEntity.setInvalidAttemptCount(5);
	                	//enableDisableUserEntity.setDisabledTime(today);
	                	//enableDisableUserEntity.setIsDisabled("Y");
	                	//enableDisableUserRepository.save(enableDisableUserEntity);
	            //	portalMasterRepository.findAndDeleteById(portalUserId);
	            	
	                    response.put(Constant.STATUS, 200);
	                    response.put(Constant.MESSAGE, Constant.SUCCESS);
	                    response.put("Data", "SentForApproval Sucessfully");
	                    ObjectMapper Obj = new ObjectMapper();  
						 String jsonStr = Obj.writeValueAsString(response); 
						
						//ENcryption Technique
						String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
						response1.put(Constant.STATUS, 200);                 
						response1.put(Constant.MESSAGE, Constant.SUCCESS);                
						response1.put("body", encaccessResponse); 
						return new ResponseEntity<Object>(response1, HttpStatus.OK);
	                 }
	 
	        } catch (Exception ex) {
	            logger.debug("Could not delete mph portal user due to : " + ex.getMessage());
	            throw new MphUserServiceException ("Internal Server Error");
	        }
	        //return null;
	 
	    }

	@Override
	public ResponseEntity<Object> approveRejectUser(org.json.JSONArray userArray) throws MphUserServiceException {
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> response1 = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug("Enter approveOrAddUser : " + methodName);
		String token = "Bearer "+generateToken().getAccess_token();
		try {
			int userAddedCount = 0;
			int userFailedCount = 0;
			int userArrayLength = userArray.length();
			for(int i = 0; i < userArrayLength; i++)
			{
				boolean adminRolesAssigned = false;
				JSONObject userObj = userArray.getJSONObject(i);
				int updateResponse = 0;
				ArrayList<String> roleKeys = new ArrayList<String>();
				if(userObj.getString("action").equalsIgnoreCase("ADD"))
				{
					if(userObj.getString("decision").equalsIgnoreCase("Approve"))
					{
						String mphName = userObj.getString("mphName");
						MphNameEntity mphNameEntity = mphNameRepository.checkMphAvailability(mphName);
						if(mphNameEntity != null)
						{
							PortalMasterEntity portalMasterEntity = portalMasterRepository.getMasterUserIdByUserName(userObj.getString("username"));
			                  
						    ArrayList<Credential> credentialList = new ArrayList();
							ArrayList<String> grouplist = new ArrayList();
			                
							AddUserListModel addUserModel1 = new AddUserListModel();
							grouplist.add(mphNameEntity.getMphKey());
							addUserModel1.setUsername(portalMasterEntity.getUsername());
							addUserModel1.setEnabled(true);
							addUserModel1.setFirstName(portalMasterEntity.getFullName());
							addUserModel1.setLastName(portalMasterEntity.getFullName());
							addUserModel1.setEmail(portalMasterEntity.getEmail());
							addUserModel1.setGroups(grouplist);
							Credential credentials = new Credential();
							credentials.setType("password");
							credentials.setValue(portalMasterEntity.getUsername());
							credentialList.add(credentials);
			
							addUserModel1.setCredentials(credentialList);
			
							ResponseEntity<Object> rm = redhatUserGenerationService.addUser(token, Constant.RHSSO_REALM, addUserModel1);
							String userId = "";
							if(rm.getStatusCode().equals(HttpStatus.OK))
							{
									try
									{
										ResponseEntity<String> allUsersResponse = redhatUserGenerationService.searchUserByEmail(token, Constant.RHSSO_REALM, portalMasterEntity.getEmail());
						            	if(allUsersResponse.getStatusCode().equals(HttpStatus.OK))
						            	{	
						            		logger.debug("approveOrAddUser response from RHSSO API " + allUsersResponse.getBody());
						            		JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(allUsersResponse.getBody());
						            	 	logger.debug("approveOrAddUser JSON plain object " + plainJSONObject);
						            	 	ObjectMapper ob = new ObjectMapper();
						            	 	UserResponseModel urm = ob.readValue(plainJSONObject.toString(), UserResponseModel.class);
						            	 	for (UserResponse userResp : urm.getUserlist()) 
						            	 	{
												if(userResp.getEmail().equalsIgnoreCase(portalMasterEntity.getEmail()))
												{
													userId = userResp.getId();
													logger.debug("approveOrAddUser user id is : " + userId);	
												}
											}
						            	}
									}
									catch(Exception e)
									{
										logger.error("Unable to fetch userid from RHSSO API " + e.getMessage());
									}
							}
							
							MasterPolicyDataEntity masterPolicyDataEntity = masterPolicyDataRepository.getUnitAndZoneFromPolicyNo(userObj.getString("policyNumber"));
							List<MasterRolesBulkEntity> masterRolesBulkEntities = masterRolesBulkRepository.getAllRolesUnderThatStream(masterPolicyDataEntity.getStream());
							ArrayList<RolesModel> rolesmodelArrayList = new ArrayList<>();
							RolesListModel rolesmodel1 = new RolesListModel();
							
							RolesModel roleModel = new RolesModel();
							roleModel.setClientRole(true);
					    	roleModel.setComposite(false);
					    	roleModel.setName(Constant.RHSSO_REALM_ADMIN_NAME);
					    	roleModel.setId(Constant.RHSSO_REALM_ADMIN_ID);

							rolesmodelArrayList.add(roleModel);

							rolesmodel1.setRealm(Constant.RHSSO_REALM);
							rolesmodel1.setUserid(userId);
							rolesmodel1.setClientid(Constant.RHSSO_REALM_CLIENT);
							logger.info("approveOrAddUser assigning roles API");
							ResponseEntity<Object> assignRolesRealmResponse = redhatUserGenerationService.assignRoleToUser(token, rolesmodel1, rolesmodelArrayList);
							if(userObj.getString("isMphAdmin").equalsIgnoreCase("Y"))
							{
								MasterRolesBulkEntity adminRoleEntity = masterRolesBulkRepository.getRoleDetailsFromRoleName(Constant.RHSSO_PORTAL_ADMIN_NAME);
								RolesModel adminRoleModel = new RolesModel();
								adminRoleModel.setClientRole(true);
								adminRoleModel.setComposite(false);
								adminRoleModel.setName(adminRoleEntity.getRoleName());
								adminRoleModel.setId(adminRoleEntity.getRoleId());
								
								rolesmodelArrayList.clear();
								rolesmodelArrayList.add(adminRoleModel);

								rolesmodel1.setRealm(Constant.RHSSO_REALM);
								rolesmodel1.setUserid(userId);
								rolesmodel1.setClientid(adminRoleEntity.getClientId());
								logger.info("approveOrAddUser assigning roles API");
								ResponseEntity<Object> adminRolesResponse = redhatUserGenerationService.assignRoleToUser(token, rolesmodel1, rolesmodelArrayList);
								if(adminRolesResponse.getStatusCode().equals(HttpStatus.OK))
								{
									adminRolesAssigned = true;
									if(!roleKeys.contains(adminRoleEntity.getRoleKey()))
									{
										roleKeys.add(adminRoleEntity.getRoleKey());
									}
								}
							}
							
							int rolesAssignedCount = 0;
							for (MasterRolesBulkEntity masterRolesBulkEntity : masterRolesBulkEntities) 
							{
								rolesmodelArrayList.clear();
								RolesModel roleModelCP = new RolesModel();
								roleModelCP.setClientRole(Boolean.parseBoolean(masterRolesBulkEntity.getClientRole()));
								roleModelCP.setComposite(Boolean.parseBoolean(masterRolesBulkEntity.getComposite()));
								if(masterRolesBulkEntity.getRoleName().toLowerCase().contains("_maker")) {
									roleModelCP.setName("maker");
								}else {
									roleModelCP.setName(masterRolesBulkEntity.getRoleName());
								}
								
								roleModelCP.setId(masterRolesBulkEntity.getRoleId());
								rolesmodelArrayList.add(roleModelCP);
								rolesmodel1.setRealm(masterRolesBulkEntity.getRhssoRealm());
								rolesmodel1.setUserid(userId);
								rolesmodel1.setClientid(masterRolesBulkEntity.getClientId());
								ResponseEntity<Object> assignRolesResponse = redhatUserGenerationService.assignRoleToUser(token, rolesmodel1, rolesmodelArrayList);
								if(assignRolesResponse.getStatusCode().equals(HttpStatus.OK) && assignRolesRealmResponse.getStatusCode().equals(HttpStatus.OK))
								{
									if(userObj.getString("isMphAdmin").equalsIgnoreCase("Y"))
									{
										if(adminRolesAssigned)
										{
											rolesAssignedCount++;
										}
									}
									else
									{
										rolesAssignedCount++;
									}
									if(!roleKeys.contains(masterRolesBulkEntity.getRoleKey()))
									{
										if(!masterRolesBulkEntity.getRoleName().toLowerCase().contains("_maker"))
										{
											roleKeys.add(masterRolesBulkEntity.getRoleKey());
										}
									}
								}
							}
							if(rolesAssignedCount == masterRolesBulkEntities.size())
							{
								//update the status in master_portal_users
								updateResponse = portalMasterRepository.updateStatusOfCreatedUser(2L, "Approved", userObj.getString("username"), userObj.getString("checkerRemark"), userObj.getString("checkerName"), new Date(System.currentTimeMillis()));
								portalMasterRepository.updateAssignRoleFlagAndRoles("Y",userObj.getString("username"),String.join(",", roleKeys));
								mphNameRepository.updatePortalsByMphName(userObj.getString("mphName"),String.join(",", roleKeys));
								if(updateResponse == 1)
								{
									MasterUsersEntity masterUsersEntity=masterUsersRepository.getAllMasterUserDetailByUserName(userObj.getString("checkerName"));
									userAddedCount++;
									UserDetailHistoryEntity userDetailHistoryEntity = new UserDetailHistoryEntity();
									userDetailHistoryEntity.setUsername(userObj.getString("username"));
									userDetailHistoryEntity.setEmail(userObj.getString("email"));
									userDetailHistoryEntity.setMphname(userObj.getString("mphName"));
									userDetailHistoryEntity.setCreatedby(userObj.getString("createdBy"));
									userDetailHistoryEntity.setCreatedon(new Date(userObj.getLong("createdOn")));
									userDetailHistoryEntity.setAction(userObj.getString("action"));
									userDetailHistoryEntity.setStatus("Approved");
									userDetailHistoryEntity.setRemark(userObj.getString("checkerRemark"));
									userDetailHistoryEntity.setUnitCode(userObj.getString("unit"));
									userDetailHistoryEntity.setModifiedBy(userObj.getString("checkerName"));
									userDetailHistoryEntity.setModifiedOn(new Date(System.currentTimeMillis()));
									userDetailHistoryEntity.setSrNumber(masterUsersEntity.getSrNumber2());
									UserDetailHistoryEntity userHistoryDetails = userHistoryDetailsRepository.save(userDetailHistoryEntity);
									
								}
								else
								{
									userFailedCount++;
								}
							}
							else
							{
								userFailedCount++;
							}
						}
					}
					else
					{
						//update the status in master_portal_users
						updateResponse = portalMasterRepository.updateStatusOfCreatedUser(3L, "Rejected", userObj.getString("username"), userObj.getString("checkerRemark"), userObj.getString("checkerName"), new Date(System.currentTimeMillis()));
						if(updateResponse == 1)
						{
							MasterUsersEntity masterUsersEntity=masterUsersRepository.getAllMasterUserDetailByUserName(userObj.getString("checkerName"));
							userAddedCount++;
							UserDetailHistoryEntity userDetailHistoryEntity = new UserDetailHistoryEntity();
							userDetailHistoryEntity.setUsername(userObj.getString("username"));
							userDetailHistoryEntity.setEmail(userObj.getString("email"));
							userDetailHistoryEntity.setMphname(userObj.getString("mphName"));
							userDetailHistoryEntity.setCreatedby(userObj.getString("createdBy"));
							userDetailHistoryEntity.setCreatedon(new Date(userObj.getLong("createdOn")));
							userDetailHistoryEntity.setAction(userObj.getString("action"));
							userDetailHistoryEntity.setStatus("Rejected");
							userDetailHistoryEntity.setRemark(userObj.getString("checkerRemark"));
							userDetailHistoryEntity.setUnitCode(userObj.getString("unit"));
							userDetailHistoryEntity.setModifiedBy(userObj.getString("checkerName"));
							userDetailHistoryEntity.setModifiedOn(new Date(System.currentTimeMillis()));
							userDetailHistoryEntity.setSrNumber(masterUsersEntity.getSrNumber2());
							UserDetailHistoryEntity userHistoryDetails = userHistoryDetailsRepository.save(userDetailHistoryEntity);
						}
						else
						{
							userFailedCount++;
						}
					}
				}
				else if(userObj.getString("action").equalsIgnoreCase("DELETE"))
				{
					if(userObj.getString("decision").equalsIgnoreCase("Approve"))
					{
						PortalMasterEntity portalMasterEntity = portalMasterRepository.getMasterUserIdByUserName(userObj.getString("username"));
						String userId = "";
						try
						{
							ResponseEntity<String> allUsersResponse = redhatUserGenerationService.searchUserByEmail(token, Constant.RHSSO_REALM, portalMasterEntity.getEmail());
			            	if(allUsersResponse.getStatusCode().equals(HttpStatus.OK))
			            	{	
			            		logger.debug("approveOrAddUser response from RHSSO API " + allUsersResponse.getBody());
			            		JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(allUsersResponse.getBody());
			            		
			            		
			            	 	logger.debug("approveOrAddUser JSON plain object " + plainJSONObject);
			            	 	ObjectMapper ob = new ObjectMapper();
			            	 	UserResponseModel urm = ob.readValue(plainJSONObject.toString(), UserResponseModel.class);
			            	 	for (UserResponse userResp : urm.getUserlist()) 
			            	 	{
									if(userResp.getEmail().equalsIgnoreCase(portalMasterEntity.getEmail()))
									{
										userId = userResp.getId();
										logger.debug("approveOrAddUser user id is : " + userId);	
									}
								}
			            	}
						}
						catch(Exception e)
						{
							logger.error("Unable to fetch userid from RHSSO API " + e.getMessage());
						}
						 try 
						 {
							ResponseEntity<Object> deleteUserResponse = redhatUserGenerationService.deleteUser(token, Constant.RHSSO_REALM, userId);
							JSONObject plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(deleteUserResponse.getBody().toString());
		            	 	logger.debug("deleteAllBankDetails deleted user response json : " + plainJSONObject);
		            	 	ResponseModel rm = new ObjectMapper().readValue(plainJSONObject.toString(), ResponseModel.class);
		            	 	if(rm != null && rm.getStatus() == 200)
							{
		            	 		//update the status in master_portal_users
		            	 		portalMasterRepository.findAndDeleteById(portalMasterEntity.getPortalUserId());
		            	 		updateResponse = portalMasterRepository.updateStatusOfCreatedUser(2L, "Approved", userObj.getString("username"), userObj.getString("checkerRemark"), userObj.getString("checkerName"), new Date(System.currentTimeMillis()));
								if(updateResponse == 1)
								{
									MasterUsersEntity masterUsersEntity=masterUsersRepository.getAllMasterUserDetailByUserName(userObj.getString("checkerName"));
									userAddedCount++;
									UserDetailHistoryEntity userDetailHistoryEntity = new UserDetailHistoryEntity();
									userDetailHistoryEntity.setUsername(userObj.getString("username"));
									userDetailHistoryEntity.setEmail(userObj.getString("email"));
									userDetailHistoryEntity.setMphname(userObj.getString("mphName"));
									userDetailHistoryEntity.setCreatedby(userObj.getString("createdBy"));
									userDetailHistoryEntity.setCreatedon(new Date(userObj.getLong("createdOn")));
									userDetailHistoryEntity.setAction(userObj.getString("action"));
									userDetailHistoryEntity.setStatus("Approved");
									userDetailHistoryEntity.setRemark(userObj.getString("checkerRemark"));
									userDetailHistoryEntity.setUnitCode(userObj.getString("unit"));
									userDetailHistoryEntity.setModifiedBy(userObj.getString("checkerName"));
									userDetailHistoryEntity.setModifiedOn(new Date(System.currentTimeMillis()));
									userDetailHistoryEntity.setSrNumber(masterUsersEntity.getSrNumber2());
									UserDetailHistoryEntity userHistoryDetails = userHistoryDetailsRepository.save(userDetailHistoryEntity);
								}
								else
								{
									userFailedCount++;
								}
							}
						 } 
						 catch (Exception e) 
						 {
							logger.debug("deleting user from RHSSO exception occured :" + e.getMessage());
							userFailedCount++;
						 }
						
					}
					else
					{
						//update the status in master_portal_users
						updateResponse = portalMasterRepository.updateStatusOfCreatedUser(3L, "Rejected", userObj.getString("username"), userObj.getString("checkerRemark"), userObj.getString("checkerName"), new Date(System.currentTimeMillis()));
						if(updateResponse == 1)
						{
							MasterUsersEntity masterUsersEntity=masterUsersRepository.getAllMasterUserDetailByUserName(userObj.getString("checkerName"));
							userAddedCount++;
							UserDetailHistoryEntity userDetailHistoryEntity = new UserDetailHistoryEntity();
							userDetailHistoryEntity.setUsername(userObj.getString("username"));
							userDetailHistoryEntity.setEmail(userObj.getString("email"));
							userDetailHistoryEntity.setMphname(userObj.getString("mphName"));
							userDetailHistoryEntity.setCreatedby(userObj.getString("createdBy"));
							userDetailHistoryEntity.setCreatedon(new Date(userObj.getLong("createdOn")));
							userDetailHistoryEntity.setAction(userObj.getString("action"));
							userDetailHistoryEntity.setStatus("Rejected");
							userDetailHistoryEntity.setRemark(userObj.getString("checkerRemark"));
							userDetailHistoryEntity.setUnitCode(userObj.getString("unit"));
							userDetailHistoryEntity.setModifiedBy(userObj.getString("checkerName"));
							userDetailHistoryEntity.setModifiedOn(new Date(System.currentTimeMillis()));
							userDetailHistoryEntity.setSrNumber(masterUsersEntity.getSrNumber2());
							UserDetailHistoryEntity userHistoryDetails = userHistoryDetailsRepository.save(userDetailHistoryEntity);
						}
						else
						{
							userFailedCount++;
						}
					}
				}
				
			}
			if(userAddedCount == userArrayLength)
			{
				response.put(Constant.STATUS, 200);
            	response.put(Constant.MESSAGE, Constant.SUCCESS);
            	response.put("Data", "All Users processed Sucessfully");
			}
			else
			{
				response.put(Constant.STATUS, 200);
            	response.put(Constant.MESSAGE, Constant.SUCCESS);
            	response.put("Data", "Users Approved Successfully : "+userAddedCount+" Users Failed to Approved : "+userFailedCount);
			}
			
        ObjectMapper Obj = new ObjectMapper();  
		 String jsonStr = Obj.writeValueAsString(response); 
		
		//ENcryption Technique
		String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
		response1.put(Constant.STATUS, 200);                 
		response1.put(Constant.MESSAGE, Constant.SUCCESS);                
		response1.put("body", encaccessResponse); 
			
		} catch (Exception exception) {
			logger.error("Could not add Mph User in RHSSO" + exception.getMessage());
			 throw new MphUserServiceException ("Internal Server Error");
		}
		return new ResponseEntity<Object>(response1, HttpStatus.OK);

	}
	
	
	@Override
	public ResponseEntity<Object> getAllAdminOrdinaryUsers(String is_MphAdmin, String is_Active, String loggedInUserUnitCode) throws Exception 
	{
		logger.info("getAllAdminOrdinaryUsers method started");
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> response1 = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		List<PortalMasterEntity> activeInactiveAdminOrdinaryUsers = new ArrayList<PortalMasterEntity>();
		try
		{
			//List<PortalMasterEntity> portalMasterEntities = new arra;
			String is_deleted = "";
			if(is_Active.equalsIgnoreCase("Y"))
			{
				is_deleted = "N";
				activeInactiveAdminOrdinaryUsers  = portalMasterRepository.getAllAdminOrdinaryUsers(is_MphAdmin,is_Active,is_deleted,loggedInUserUnitCode);
			}
			else
			{
				is_deleted = "Y";
				activeInactiveAdminOrdinaryUsers = portalMasterRepository.getAllAdminOrdinaryUsers(is_MphAdmin,is_Active,is_deleted,loggedInUserUnitCode);
			}
			if(activeInactiveAdminOrdinaryUsers.size() > 0)
			{
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", activeInactiveAdminOrdinaryUsers);
				ObjectMapper Obj = new ObjectMapper();
				String jsonStr = Obj.writeValueAsString(response);
				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
				response1.put(Constant.STATUS, 200);
				response1.put(Constant.MESSAGE, Constant.SUCCESS);
				response1.put("body", encaccessResponse);
			}
			else
			{
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.FAILED);
				response.put("Data", Constant.NO_DATA_FOUND);
				ObjectMapper Obj = new ObjectMapper();
				String jsonStr = Obj.writeValueAsString(response);
				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
				response1.put(Constant.STATUS, 200);
				response1.put(Constant.MESSAGE, Constant.SUCCESS);
				response1.put("body", encaccessResponse);
			}
		}
		catch(Exception e)
		{
			logger.debug("Exception Occurred while fetching Active/InActive Users ::"+e.getMessage());
		}
		logger.info("getAllAdminOrdinaryUsers method ended");
		return new ResponseEntity<Object>(response1, HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<Object> getAllUsersPendingForApproval(String action, String fromDate, String toDate, String loggedInUserUnitCode) throws Exception 
	{
		logger.info("getAllUsersPendingForApproval method started");
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> response1 = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		List<PortalMasterEntity> allPendingForApprovalsUsers = new ArrayList<PortalMasterEntity>();
		try
		{
			if((action.isEmpty()) && (fromDate.isEmpty()) && (toDate.isEmpty()))
			{
				allPendingForApprovalsUsers = portalMasterRepository.getAllUsersPendingForApproval("sentforapproval",loggedInUserUnitCode);
			}
			else if((!action.isEmpty()) && (fromDate.isEmpty()) && (toDate.isEmpty()))
			{
				allPendingForApprovalsUsers = portalMasterRepository.getAllUsersPendingForApproval("sentforapproval", action, loggedInUserUnitCode);
			}
			else if((action.isEmpty()) && (!fromDate.isEmpty()) && (!toDate.isEmpty()))
			{
				allPendingForApprovalsUsers = portalMasterRepository.getAllUsersPendingForApproval("sentforapproval", fromDate, toDate, loggedInUserUnitCode);
			}
			else if((!action.isEmpty()) && (!fromDate.isEmpty()) && (!toDate.isEmpty()))
			{
				allPendingForApprovalsUsers = portalMasterRepository.getAllUsersPendingForApproval("sentforapproval", action, fromDate, toDate, loggedInUserUnitCode);
			}
			else if((action.isEmpty()) && (!fromDate.isEmpty()) && (toDate.isEmpty()))
			{
				allPendingForApprovalsUsers = portalMasterRepository.getAllUsersPendingForApprovalAfterFromDate("sentforapproval", fromDate, loggedInUserUnitCode);
			}
			else if((action.isEmpty()) && (fromDate.isEmpty()) && (!toDate.isEmpty()))
			{
				allPendingForApprovalsUsers = portalMasterRepository.getAllUsersPendingForApprovalBeforeToDate("sentforapproval", toDate, loggedInUserUnitCode);
			}
			
			if(allPendingForApprovalsUsers.size() > 0)
			{ 
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", allPendingForApprovalsUsers);
				ObjectMapper Obj = new ObjectMapper();
				String jsonStr = Obj.writeValueAsString(response);
				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
				response1.put(Constant.STATUS, 200);
				response1.put(Constant.MESSAGE, Constant.SUCCESS);
				response1.put("body", encaccessResponse);
			}
			else
			{
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.FAILED);
				response.put("Data", Constant.NO_DATA_FOUND);
				ObjectMapper Obj = new ObjectMapper();
				String jsonStr = Obj.writeValueAsString(response);
				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
				response1.put(Constant.STATUS, 200);
				response1.put(Constant.MESSAGE, Constant.SUCCESS);
				response1.put("body", encaccessResponse);
			}
		}
		catch(Exception e)
		{
			logger.debug("Exception Occurred while fetching Active/InActive Users ::"+e.getMessage());
		}
		logger.info("getAllUsersPendingForApproval method ended");
		return new ResponseEntity<Object>(response1, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Object> getAllUsersBasedOnStatus(String status, String loggedInUserUnitCode) throws Exception 
	{
		logger.info("getAllUsersBasedOnStatus method started");
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> response1 = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		List<PortalMasterEntity> allUsersBasedOnStatusList = new ArrayList<PortalMasterEntity>();
		try
		{
			allUsersBasedOnStatusList = portalMasterRepository.getAllUsersBasedOnStatus(status, loggedInUserUnitCode);
			if(allUsersBasedOnStatusList.size() > 0)
			{
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", allUsersBasedOnStatusList);
				ObjectMapper Obj = new ObjectMapper();
				String jsonStr = Obj.writeValueAsString(response);
				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
				response1.put(Constant.STATUS, 200);
				response1.put(Constant.MESSAGE, Constant.SUCCESS);
				response1.put("body", encaccessResponse);
			}
			else
			{
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.FAILED);
				response.put("Data", Constant.NO_DATA_FOUND);
				ObjectMapper Obj = new ObjectMapper();
				String jsonStr = Obj.writeValueAsString(response);
				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
				response1.put(Constant.STATUS, 200);
				response1.put(Constant.MESSAGE, Constant.SUCCESS);
				response1.put("body", encaccessResponse);
			}
		}
		catch(Exception e)
		{
			logger.debug("Exception Occurred while fetching Users ::"+e.getMessage());
		}
		logger.info("getAllUsersBasedOnStatus method ended");
		return new ResponseEntity<Object>(response1, HttpStatus.OK);
	}
	
	 @Override
	   public ResponseEntity<Object> getValidateUserName(String username)
	   throws MphUserServiceException {
	   HashMap<String, Object> response = new HashMap<String, Object>();
	   Map<String, Object> response1 = new HashMap<String, Object>();
	   response.put(Constant.STATUS, 0);
	   response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	   String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	   logger.debug("Enter UserManagementService : " + methodName);
	   String token = generateToken().getAccess_token();
	   try {

	   int resCount = portalMasterRepository.getvalidateUser(username);
	   boolean usernamePresent = false;
	   ResponseEntity<String> rrm = redhatUserGenerationService.searchUserByUsernameForSuperAdmin(token, "usermgmt_claim_portal", username);
	   if(rrm.getStatusCode().equals(HttpStatus.OK))
	   {	
		   	JSONObject plainJSONObject = new JSONObject();
			logger.debug("getValidateEmail response from RHSSO API " + rrm.getBody());
			plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(rrm.getBody());
		 	logger.debug("getValidateEmail JSON plain object " + plainJSONObject);
		 	UserResponseModel urm = new ObjectMapper().readValue(plainJSONObject.toString(), UserResponseModel.class);
		 	for (UserResponse userResponse : urm.getUserlist()) 
		 	{
				if(userResponse.getUsername().equalsIgnoreCase(username))
				{
					usernamePresent = true;
					break;
				}
			}
	   }
	   if (resCount == 0 && !usernamePresent) {
	   	
		   response.put(Constant.STATUS, 201);
		   	response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
		   	response.put("User Not Present", methodName);
		   	ObjectMapper Obj = new ObjectMapper(); 
		   	 String jsonStr = Obj.writeValueAsString(response); 
		   	
		   	//ENcryption Technique
		   	String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
		   	response1.put(Constant.STATUS, 201);                 
		   	response1.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);                
		   	response1.put("body", encaccessResponse); 
		   	return new ResponseEntity<Object>(response1, HttpStatus.OK);

	   } 
	   else
	   {
		   	response.put(Constant.STATUS, 200);
		   	response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);
		   	response.put("Count Of Users", resCount);
		   	ObjectMapper Obj = new ObjectMapper(); 
		   	 String jsonStr = Obj.writeValueAsString(response); 
		   	
		   	//ENcryption Technique
		   	String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
		   	response1.put(Constant.STATUS, 200);                 
		   	response1.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);                
		   	response1.put("body", encaccessResponse); 
		   	return new ResponseEntity<Object>(response1, HttpStatus.OK);

		   
	   }

	   } catch (Exception exception) {
	   logger.debug("Exception : " + exception.getMessage());
	   throw new MphUserServiceException ("Internal Server Error");
	   }
	   //return null;
	   }

	   @Override
	   public ResponseEntity<Object> getValidateEmail(String email) throws MphUserServiceException {
	   HashMap<String, Object> response = new HashMap<String, Object>();
	   Map<String, Object> response1 = new HashMap<String, Object>();
	   response.put(Constant.STATUS, 0);
	   response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	   String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	   logger.debug("Enter UserManagementService : " + methodName);
	   String token = generateToken().getAccess_token();
	   try {
	   	   

	   int resCount = portalMasterRepository.getValidateEmail(email);
	   boolean emailPresent = false;
	   ResponseEntity<String> rrm = redhatUserGenerationService.searchUserByEmail(token, "usermgmt_claim_portal", email);
	   if(rrm.getStatusCode().equals(HttpStatus.OK))
	   {	
		   	JSONObject plainJSONObject = new JSONObject();
			logger.debug("getValidateEmail response from RHSSO API " + rrm.getBody());
			plainJSONObject = EncryptandDecryptAES.DecryptAESECBPKCS5Padding(rrm.getBody());
		 	logger.debug("getValidateEmail JSON plain object " + plainJSONObject);
		 	UserResponseModel urm = new ObjectMapper().readValue(plainJSONObject.toString(), UserResponseModel.class);
		 	for (UserResponse userResponse : urm.getUserlist()) 
		 	{
				if(userResponse.getEmail().equalsIgnoreCase(email))
				{
					emailPresent = true;
					break;
				}
			}
	   }
	   if (resCount == 0 && !emailPresent) {
		   
		   response.put(Constant.STATUS, 201);
		   	response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
		   	response.put("User Not Present", methodName);
		   	ObjectMapper Obj = new ObjectMapper(); 
		   	 String jsonStr = Obj.writeValueAsString(response); 
		   	//ENcryption Technique
		   	String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
		   	response1.put(Constant.STATUS, 201);                 
		   	response1.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);                
		   	response1.put("body", encaccessResponse); 
		   	return new ResponseEntity<Object>(response1, HttpStatus.OK);   
		   
	   } else {
			response.put(Constant.STATUS, 200);
		   	response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);
		   	response.put("Count Of Users", resCount);
		   	ObjectMapper Obj = new ObjectMapper(); 
		   	 String jsonStr = Obj.writeValueAsString(response); 
		   	 
		   	
		   	//ENcryption Technique
		   	String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
		   	response1.put(Constant.STATUS, 200);                 
		   	response1.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);                
		   	response1.put("body", encaccessResponse); 
		   	return new ResponseEntity<Object>(response1, HttpStatus.OK);
	   }

	   } catch (Exception exception) {
	   logger.debug("Exception : " + exception.getMessage());
	   throw new MphUserServiceException ("Internal Server Error");
	   }
	   //return null;
	   }


	   @Override
	   public ResponseEntity<Object> getValidateMobile(String mobile)
	   throws MphUserServiceException {
	   HashMap<String, Object> response = new HashMap<String, Object>();
	   Map<String, Object> response1 = new HashMap<String, Object>();
	   response.put(Constant.STATUS, 0);
	   response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	   String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	   logger.debug("Enter UserManagementService : " + methodName);
	   try {

	   int resCount = portalMasterRepository.getValidateMobile(mobile);

	   if (resCount >= 1) {
	   	response.put(Constant.STATUS, 200);
	   	response.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);
	   	response.put("Count Of Users", resCount);
	   	
	   	ObjectMapper Obj = new ObjectMapper(); 
	   	 String jsonStr = Obj.writeValueAsString(response); 
	   	
	   	//ENcryption Technique
	   	String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
	   	response1.put(Constant.STATUS, 200);                 
	   	response1.put(Constant.MESSAGE, Constant.DUPLICATE_ENTRY);                
	   	response1.put("body", encaccessResponse); 
	   	return new ResponseEntity<Object>(response1, HttpStatus.OK);


	   } else  {
	   	response.put(Constant.STATUS, 201);
	   	response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
	   	response.put("User Not Present", methodName);
	   	
	   	ObjectMapper Obj = new ObjectMapper(); 
	   	 String jsonStr = Obj.writeValueAsString(response); 
	   	//ENcryption Technique
	   	String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
	   	response1.put(Constant.STATUS, 201);                 
	   	response1.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);                
	   	response1.put("body", encaccessResponse); 
	   	return new ResponseEntity<Object>(response1, HttpStatus.OK);

	   }

	   } catch (Exception exception) {
	   logger.debug("Exception : " + exception.getMessage());
	   throw new MphUserServiceException ("Internal Server Error");
	   }
	   //return null;
	   }	

	   @Override
	   public ResponseEntity<Object> updateLoggedInUserFlagDetails(String userName, String loginFlag) throws Exception {
	   	logger.info("Enter updateLoggedInUserFlagDetails : logoutUser");
	   	try { 
	   	Map<String, Object> response = new HashMap<String, Object>();
	   	Map<String, Object> response1 = new HashMap<String, Object>();
	   	ObjectMapper Obj = new ObjectMapper();
	   	if (userName == null && loginFlag == null) {
	   	response.put(Constant.STATUS, 201);
	   	response.put(Constant.MESSAGE, Constant.FAILED);
	   	response.put("Data", Constant.FAILED);
		   String jsonStr = Obj.writeValueAsString(response);
	  //ENcryption Technique
	 	   String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
	 	   response1.put(Constant.STATUS, 200);                 
	 	   response1.put(Constant.MESSAGE, Constant.SUCCESS);                
	 	   response1.put("body", encaccessResponse); 
	   	} else {
	   		masterUsersRepository.updateLoggedInUserFlag(userName,loginFlag);
	   		response.put(Constant.STATUS, 200);
	   		response.put(Constant.MESSAGE, Constant.SUCCESS);
	   		response.put("Data", "Flag Updated");
		   String jsonStr = Obj.writeValueAsString(response);
	   	//ENcryption Technique
	 	   String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
	 	   response1.put(Constant.STATUS, 200);                 
	 	   response1.put(Constant.MESSAGE, Constant.SUCCESS);                
	 	   response1.put("body", encaccessResponse); 
	   	}
	   	return new ResponseEntity<Object>(response1, HttpStatus.OK);
	   	} catch (Exception ex) {
	   		logger.info("Could not logout user due to : " + ex.getMessage());
	   		return null;
	   	}
	   	}
	   
	   @Override
	   public ResponseEntity<Object> getUserDetailsInSuperAdmin(String username){
	   	logger.info("Enter getUserDetailsInSuperAdmin");
	   	try { 
	   	Map<String, Object> response = new HashMap<String, Object>();
	   	Map<String, Object> response1 = new HashMap<String, Object>();
	   	ObjectMapper Obj = new ObjectMapper();
	   	if (username == null) {
	   	response.put(Constant.STATUS, 201);
	   	response.put(Constant.MESSAGE, Constant.FAILED);
	   	response.put("Data", Constant.NO_DATA_FOUND);
		   String jsonStr = Obj.writeValueAsString(response);
	  //ENcryption Technique
	 	   String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
	 	   response1.put(Constant.STATUS, 200);                 
	 	   response1.put(Constant.MESSAGE, Constant.SUCCESS);                
	 	   response1.put("body", encaccessResponse); 
	   	} else {
	   		PortalMasterEntity portalMasterEntity = portalMasterRepository.getMasterUserIdByUserName(username);
	   		response.put(Constant.STATUS, 200);
	   		response.put(Constant.MESSAGE, Constant.SUCCESS);
	   		response.put("Data", portalMasterEntity);
		   String jsonStr = Obj.writeValueAsString(response);
	   	//ENcryption Technique
	 	   String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
	 	   response1.put(Constant.STATUS, 200);                 
	 	   response1.put(Constant.MESSAGE, Constant.SUCCESS);                
	 	   response1.put("body", encaccessResponse); 
	   	}
	   	return new ResponseEntity<Object>(response1, HttpStatus.OK);
	   	} catch (Exception ex) {
	   		logger.info("Could not fetch user details : " + ex.getMessage());
	   		return null;
	   	}
	   	}
	   
	   
	   @Override
		public ResponseEntity<Object> checkPolicyAvailability(String policyNumber, String loggedInUserUnitCode) throws Exception 
		{
			 
			logger.info("checkPolicyAvailability method started");
			Map<String, Object> response = new HashMap<String, Object>(4);
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			ObjectMapper Obj = new ObjectMapper(); 
			Map<String, Object> response1 = new HashMap<String, Object>();
			boolean policynumber = false;
			try
			{
				MasterPolicyDataEntity policyData = masterPolicyDataRepository.checkPolicyAvailability(policyNumber);
				if(loggedInUserUnitCode != null && policyData != null && policyData.getUnitCode() != null && !loggedInUserUnitCode.equalsIgnoreCase(policyData.getUnitCode())) {
					response.put(Constant.STATUS, 201);
					response.put(Constant.MESSAGE, Constant.ERROR);
					response.put(Constant.DATA, "This policy belongs to different unit");				
					String jsonStr = Obj.writeValueAsString(response); 			
					String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
					response1.put(Constant.STATUS, 201);                 
					response1.put(Constant.MESSAGE, Constant.ERROR);                
					response1.put("body", encaccessResponse); 
					return new ResponseEntity <Object>(response1,HttpStatus.OK);
				}
				
				if(policyData!=null) {
					
					MphNameEntity mphNameEntity = mphNameRepository.checkMphAvailabilityBasedOnPolicyNumber(policyNumber);
					
					if (mphNameEntity== null) {
						        
						 String firstLetterWord = policyData.getMphName();
						    String str = "";
						        boolean v = true;
						        for (int i = 0; i < firstLetterWord.length(); i++)
						        {
						            // If it is space, set v as true.
						            if (firstLetterWord.charAt(i) == ' ')
						            {
						                v = true;
						            }
						            else if (firstLetterWord.charAt(i) != ' ' && v == true)
						            {
						            	
						                str += (firstLetterWord.charAt(i));
						                v = false;
						            }
						        }
						 
						Date date = new Date();
						MphNameEntity mphEntity = new MphNameEntity();
						mphEntity.setMphName(policyData.getMphName());
						mphEntity.setMphCode(policyData.getMphCode());
						mphEntity.setMphKey("MPH_"+str);
					    mphEntity.setIsActive('Y');
					    mphEntity.setIsDeleted('N');
					    mphEntity.setCreatedBy("sysadmin");
					    mphEntity.setCreatedOn(date);
					    mphEntity.setModifiedBy("sysadmin");
					    mphEntity.setModifiedOn(date);
					    mphEntity.setMphAdminMaxCOunt(25L);
					    mphEntity.setFirstAdminCreated('N');
					    mphEntity.setPortalsAssigned(null);
					    mphEntity.setPolicyNumber(policyData.getPolicyNumber());
					    long maxMphId = mphNameRepository.count();
						mphEntity.setMphId(String.valueOf(maxMphId+1));
						MphNameEntity entity = null;
				
						entity =  mphNameRepository.save(mphEntity);
						if(entity != null)
						{
							final String baseUrl = rhssoUrl + "/auth/admin/realms/{realm}/groups";
							
							 HttpHeaders headers = new HttpHeaders();
							headers.add(HttpHeaders.AUTHORIZATION,"Bearer "+generateToken().getAccess_token());
							headers.setContentType(MediaType.APPLICATION_JSON);
							
							
							Map<String, String> uriParam = new HashMap<>();
							uriParam.put("realm",Constant.RHSSO_REALM);
							
							JSONObject jsonObj = new JSONObject();
							jsonObj.put("name", "MPH_"+str);
							
							
							HttpEntity formEntity = new HttpEntity(jsonObj.toString(), headers);
							logger.debug("formEntity "+ formEntity);
					    	//ObjectMapper mapper = new ObjectMapper();
							
							logger.debug("baseUrl:"+baseUrl);
							logger.debug("formEntity:"+formEntity);
							//ResponseEntity<Object> response = restTemplate.postForEntity(baseUrl, formEntity, Object.class, uriParam);
							ResponseEntity<String> res = restTemplate.postForEntity(baseUrl, formEntity, String.class, uriParam);
							logger.debug("response for add group "+ res);
							logger.debug("response for add group 2 "+ res.getStatusCode());
							
							MphNameEntity mphName = mphNameRepository.checkMphAvailabilityBasedOnPolicyNumber(policyNumber);
							
							if(mphName.getFirstAdminCreated() == 'Y')
							{
								policynumber = true;
								response.put(Constant.STATUS, 201);
								response.put(Constant.MESSAGE, Constant.ERROR);
								response.put(Constant.DATA, "First Admin already present");
								
								String jsonStr = Obj.writeValueAsString(response); 
								
								//ENcryption Technique
								String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
								response1.put(Constant.STATUS, 201);                 
								response1.put(Constant.MESSAGE, Constant.ERROR);                
								response1.put("body", encaccessResponse); 
								return new ResponseEntity <Object>(response1,HttpStatus.OK);
								
							}else {
							response.put(Constant.STATUS, 200);
							response.put(Constant.MESSAGE, Constant.SUCCESS);
							response.put(Constant.DATA, mphName.getMphName());
							String jsonStr = Obj.writeValueAsString(response); 
							
							//ENcryption Technique
							String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
							response1.put(Constant.STATUS, 200);                 
							response1.put(Constant.MESSAGE, Constant.SUCCESS);                
							response1.put("body", encaccessResponse); 
							return new ResponseEntity <Object>(response1,HttpStatus.OK);
						}
						
					}
					        }
				
					
				
					else{
						
						if(mphNameEntity.getFirstAdminCreated() == 'Y')
						{
							policynumber = true;
							response.put(Constant.STATUS, 201);
							response.put(Constant.MESSAGE, Constant.ERROR);
							response.put(Constant.DATA, "First Admin already present");
							
							String jsonStr = Obj.writeValueAsString(response); 
							
							//ENcryption Technique
							String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
							response1.put(Constant.STATUS, 201);                 
							response1.put(Constant.MESSAGE, Constant.ERROR);                
							response1.put("body", encaccessResponse); 
							return new ResponseEntity <Object>(response1,HttpStatus.OK);
							
						}else {
						response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put(Constant.DATA, mphNameEntity.getMphName());
						String jsonStr = Obj.writeValueAsString(response); 
						
						//ENcryption Technique
						String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
						response1.put(Constant.STATUS, 200);                 
						response1.put(Constant.MESSAGE, Constant.SUCCESS);                
						response1.put("body", encaccessResponse); 
						return new ResponseEntity <Object>(response1,HttpStatus.OK);
					}
				}
					
				}
				else {
					response.put(Constant.STATUS, 201);
					response.put(Constant.MESSAGE, Constant.ERROR);
					response.put(Constant.DATA, "Policy Details not present");
					
					String jsonStr = Obj.writeValueAsString(response); 
					
					//ENcryption Technique
					String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
					response1.put(Constant.STATUS, 201);                 
					response1.put(Constant.MESSAGE, Constant.ERROR);                
					response1.put("body", encaccessResponse); 
					return new ResponseEntity <Object>(response1,HttpStatus.OK);
					
				}
				
				        
				
			
			}
				
			catch(Exception e)
			{
				logger.debug("Exception Occurred while checking Policy Availability ::"+e.getMessage());
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.ERROR);
				response.put(Constant.DATA, "Policy Details not present");
				
				String jsonStr = Obj.writeValueAsString(response); 
				
				//ENcryption Technique
				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
				response1.put(Constant.STATUS, 201);                 
				response1.put(Constant.MESSAGE, Constant.ERROR);                
				response1.put("body", encaccessResponse); 
			}
			logger.info("checkPolicyAvailability method ended");
			return new ResponseEntity <Object>(response1,HttpStatus.OK);
		}
 
}
