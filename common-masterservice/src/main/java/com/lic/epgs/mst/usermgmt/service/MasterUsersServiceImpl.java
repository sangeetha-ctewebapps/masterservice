package com.lic.epgs.mst.usermgmt.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.entity.UnitEntity;
import com.lic.epgs.mst.entity.ZonalEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.modal.COModel;
import com.lic.epgs.mst.modal.CoOfficeModel;
import com.lic.epgs.mst.modal.SOModel;
import com.lic.epgs.mst.modal.UOModel;
import com.lic.epgs.mst.modal.UnitModel;
import com.lic.epgs.mst.modal.UsersModal;
import com.lic.epgs.mst.modal.ZOModel;
import com.lic.epgs.mst.repository.JDBCConnection;
import com.lic.epgs.mst.repository.UnitEntityRepository;
import com.lic.epgs.mst.repository.ZonalEntityRepository;
import com.lic.epgs.mst.repository.ZonalOfficeRepository;
import com.lic.epgs.mst.usermgmt.entity.CoAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterApplicationModule;
import com.lic.epgs.mst.usermgmt.entity.MasterRolesDisplayRolesMappingEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersHistoryDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.SOAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.UOAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleTypeMappingEntity;
import com.lic.epgs.mst.usermgmt.entity.ZOAdminEntity;
import com.lic.epgs.mst.usermgmt.modal.AppRoleTypeModel;
import com.lic.epgs.mst.usermgmt.modal.AssignRolesModel;
import com.lic.epgs.mst.usermgmt.modal.LoggedInUserModel;
import com.lic.epgs.mst.usermgmt.modal.MasterUnitForGC;
import com.lic.epgs.mst.usermgmt.modal.SrnumberUsernameModel;
import com.lic.epgs.mst.usermgmt.modal.UserRoleModel;
import com.lic.epgs.mst.usermgmt.modal.displayRoleTypeNameModel;
import com.lic.epgs.mst.usermgmt.modal.getAppRoleTypeModel;
import com.lic.epgs.mst.usermgmt.repository.CoAdminRepository;
import com.lic.epgs.mst.usermgmt.repository.CommonConfigRepository;
import com.lic.epgs.mst.usermgmt.repository.EnableDisableUserRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterRolesDisplayRolesMappingRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersHistoryDetailsRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersRepository;
import com.lic.epgs.mst.usermgmt.repository.ModuleRepository;
import com.lic.epgs.mst.usermgmt.repository.PortalMasterRepository;
import com.lic.epgs.mst.usermgmt.repository.SoAdminRepository;
import com.lic.epgs.mst.usermgmt.repository.UoAdminRepository;
import com.lic.epgs.mst.usermgmt.repository.UserRoleTypeRepository;
import com.lic.epgs.mst.usermgmt.repository.ZOAdminRepository;
import com.lic.epgs.rhssomodel.UserDetailsResponse;

@Service
@Transactional
public class MasterUsersServiceImpl  implements MasterUsersService{

	private static final Logger logger = LoggerFactory.getLogger(MasterUsersServiceImpl.class);
     
	@Autowired
	CommonConfigRepository commonConfigRepository;
	
	@Autowired
	MasterUsersRepository masterUserRepository;
	
	@Autowired
	MasterRolesDisplayRolesMappingRepository masterRolesDisplayRolesMappingRepository;
	
	@Autowired
	private MasterUsersHistoryDetailsRepository masterUsersHistoryDetailsRepository;
	
	@Autowired
	JDBCConnection jdbcConnection;
	
	@Autowired
	SoAdminRepository soAdminRepository;

	@Autowired
	UnitEntityRepository unitEntityRepository;
	
	@Autowired
	PortalMasterRepository portalMasterRepository;
	
	@Autowired
	private ZOAdminRepository zoAdminRepository;
	
	@Autowired
	UoAdminRepository uoAdminRepository;
	
	@Autowired
	ZonalOfficeRepository zonalOfficeRepository; 
	
	@Autowired
	ZonalEntityRepository zonalEntityRepository;
	
	@Autowired
	private CoAdminRepository coAdminRepository;
	
	@Autowired
	private  UserRoleTypeRepository userRoleTypeRepository;
	
	@Autowired
	private  ModuleRepository moduleRepository;
	
	@Autowired
	private EnableDisableUserRepository enableDisableUserRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	public HttpHeaders restHeader() {

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		return headers;
	}

	String className = this.getClass().getSimpleName();
	
	@Value("${tdsDetailsAPIUrl}")
	private String tdsDetailsAPIUrl;
	
	@Value("${brsAndTokenNoAPIUrl}")
	private String brsAndTokenNoAPIUrl;

	/*
	 * Description: This function is used for getting data from MASTER USERS Module by using locationId
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@Override
	public MasterUsersEntity getUserRoleByLocation(Long locationId) throws Exception {
		logger.info("Start getUserRoleByLocation"); 


		logger.info("locationId--"+locationId);

		MasterUsersEntity objMasterUsersEntity = masterUserRepository.getUserRoleByLocation(locationId);

		logger.info("End getUserRoleByLocation");

		return objMasterUsersEntity;
	}
	
	public List<SrnumberUsernameModel> getAllUsersBySrNumber(String locationCode) {
		logger.info("Start getServiceDetailsByUnitCode");
		 
		
		List<SrnumberUsernameModel> modalList = new ArrayList();
		
		List<MasterUsersEntity> objUnitEntity = masterUserRepository.findBySrNumberUserName(locationCode);
		for(int i=0 ; i <objUnitEntity.size();i ++) {
			SrnumberUsernameModel unitModel = new SrnumberUsernameModel();
		unitModel.setSrNumber(objUnitEntity.get(i).getSrNumber2());
		unitModel.setUserName(objUnitEntity.get(i).getUserName());
		modalList.add(unitModel);
		}
		
		//unitModel.setUnitEmailId(objUnitEntity.getEmailId());
		//unitModel.setUnitCode(unitCode+"");
		logger.info("End getClaimAmountDetails");
		
		return modalList;
	}

	/*
	 * Description: This function is used for searching the data in MASTER USERS Module using the filters like LOCATION, SRNUMBER, USERNAME
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@Override
	public ResponseEntity<Map<String, Object>> masterUserSearch(String location,String locationType, String srNumber, String userName,String typeOfUser) throws SQLException {
		 Map<String, Object> response = new HashMap<String, Object>();
		 //   Map<String, Object> response1 = new HashMap<String, Object>();
		    response.put(Constant.STATUS, 0);
		    response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		    String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		    LoggingUtil.logInfo(className, methodName, "Started");
		    List<MasterUsersEntity> objMasterUsersEntity = new ArrayList<MasterUsersEntity>();
		    List<MasterUsersEntity> objMasterUsersEntityList = new ArrayList<MasterUsersEntity>();
		  
		    try {
		    	
		    	
		    	if(typeOfUser != null && typeOfUser.equalsIgnoreCase("ActiveUsers")) {
		    		objMasterUsersEntity = masterUserRepository.getAllActiveOrdinaryUsers(locationType, location, userName, srNumber);
		    	}else if(typeOfUser != null && typeOfUser.equalsIgnoreCase("DisableUsers")) {
		    		objMasterUsersEntity = masterUserRepository.getAllDisabledOrdinaryUsers(locationType, location, userName, srNumber);
		    	}else if(typeOfUser != null && typeOfUser.equalsIgnoreCase("ActiveAdminUsers")) {
		    		objMasterUsersEntity = masterUserRepository.getAllActiveAdminUsers(locationType, location, userName, srNumber);
		    	}else if(typeOfUser != null && typeOfUser.equalsIgnoreCase("DisableAdminUsers")) {
		    		objMasterUsersEntity = masterUserRepository.getAllDisabledAdminUsers(locationType, location, userName, srNumber);
		    	}
		    	if(objMasterUsersEntity != null) {
		    		for (int i = 0; i < objMasterUsersEntity.size(); i++) {
		    			
		    		/*	Date deActivatedDate = objMasterUsersEntity.get(i).getDeActivatedDate();
				    	Date date = new Date();
				    	long duration  = date.getTime() -  deActivatedDate.getTime();
				    	long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);*/
		    			  MasterUsersEntity masterUsersEntity = new MasterUsersEntity();
		    			masterUsersEntity.setMasterUserId(objMasterUsersEntity.get(i).getMasterUserId());
		    			masterUsersEntity.setUserName(objMasterUsersEntity.get(i).getUserName());
		    			masterUsersEntity.setFullName(objMasterUsersEntity.get(i).getFullName());
		    			masterUsersEntity.setSrNumber(objMasterUsersEntity.get(i).getSrNumber());
		    			masterUsersEntity.setEmailId(objMasterUsersEntity.get(i).getEmail());
		    			masterUsersEntity.setMobile(objMasterUsersEntity.get(i).getMobile());
		    			masterUsersEntity.setCadre(objMasterUsersEntity.get(i).getCadre());
		    			masterUsersEntity.setDesignation(objMasterUsersEntity.get(i).getDesignation());
		    			masterUsersEntity.setLocationType(objMasterUsersEntity.get(i).getLocationType());
		    			masterUsersEntity.setLocation(objMasterUsersEntity.get(i).getLocation());
		    			masterUsersEntity.setClassName(objMasterUsersEntity.get(i).getClassName());
		    			masterUsersEntity.setModifiedBy(objMasterUsersEntity.get(i).getModifiedBy());
		    			masterUsersEntity.setModifiedOn(objMasterUsersEntity.get(i).getModifiedOn());
		    			masterUsersEntity.setCreatedBy(objMasterUsersEntity.get(i).getCreatedBy());
		    			masterUsersEntity.setCreatedOn(objMasterUsersEntity.get(i).getCreatedOn());
		    			masterUsersEntity.setLocation(objMasterUsersEntity.get(i).getLocation());
		    			masterUsersEntity.setDateOfBirth(objMasterUsersEntity.get(i).getDateOfBirth());
		    			masterUsersEntity.setSex(objMasterUsersEntity.get(i).getSex());
		    			masterUsersEntity.setDateOfAppointment(objMasterUsersEntity.get(i).getDateOfAppointment());
		    			masterUsersEntity.setMaritalStatus(objMasterUsersEntity.get(i).getMaritalStatus());
		    			masterUsersEntity.setStatus(objMasterUsersEntity.get(i).getStatus());
		    			masterUsersEntity.setIpAddress(objMasterUsersEntity.get(i).getIpAddress());
		    			masterUsersEntity.setLocation(objMasterUsersEntity.get(i).getLocation());
		    			masterUsersEntity.setLocationCode(objMasterUsersEntity.get(i).getLocationCode());
		    			masterUsersEntity.setEmail(objMasterUsersEntity.get(i).getEmailId());
		    			masterUsersEntity.setFirstName(objMasterUsersEntity.get(i).getFirstName());
		    			masterUsersEntity.setMiddleName(objMasterUsersEntity.get(i).getMiddleName());
		    			masterUsersEntity.setLastName(objMasterUsersEntity.get(i).getLastName());
		    			masterUsersEntity.setIsActive(objMasterUsersEntity.get(i).getIsActive());
		    			masterUsersEntity.setIsDeleted(objMasterUsersEntity.get(i).getIsDeleted());
		    			masterUsersEntity.setCategory(objMasterUsersEntity.get(i).getCategory());
		    			masterUsersEntity.setIsAdmin(objMasterUsersEntity.get(i).getIsAdmin());
		    			masterUsersEntity.setSrNumber2(objMasterUsersEntity.get(i).getSrNumber2());
		    			masterUsersEntity.setIsLoggedIn(objMasterUsersEntity.get(i).getIsLoggedIn());
		    			masterUsersEntity.setDeActivatedDate(objMasterUsersEntity.get(i).getDeActivatedDate());
		    			masterUsersEntity.setDeactivatedDays(objMasterUsersEntity.get(i).getDeactivatedDays());
		    			objMasterUsersEntityList.add(masterUsersEntity);
		    		}
		    		
		    		response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", objMasterUsersEntityList);
			    	
		    	}else {
		    		response.put(Constant.STATUS, 201);
					response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
				//	ObjectMapper Obj = new ObjectMapper(); 
				//	 String jsonStr = Obj.writeValueAsString(response);				
					//ENcryption Technique
					// String encaccessResponse = EncryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);				
				//	response1.put(Constant.STATUS, 200);                 
					//response1.put(Constant.MESSAGE, Constant.SUCCESS);                
					//response1.put("body", encaccessResponse); 
	    	
		        
		    }
		    	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		    
		    }catch (Exception ex) {
		        logger.info("Could not disable mph admin user due to : " + ex.getMessage());
		    }
			return null;
		    
	 }

	
	@Override
    public ResponseEntity<Map<String, Object>> addMasterUser(MasterUsersEntity masterUsersEntity) throws Exception{
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(Constant.STATUS, 0);
        response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggingUtil.logInfo(className, methodName, "Started");
     
        Long maxAdminCoCount=commonConfigRepository.getValueFromCommonConfig("MAXADMINCOUNTCO");
        Long maxAdminZoCount=commonConfigRepository.getValueFromCommonConfig("MAXADMINCOUNTZO");
        Long maxAdminUoCount=commonConfigRepository.getValueFromCommonConfig("MAXADMINCOUNTUO");
        
        try {
            Date date = new Date();
            if (masterUsersEntity == null) {
                response.put(Constant.STATUS, 201);
                response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);               
                
            } else {
            	
            	//Check max admin count
            	if(masterUsersEntity != null && masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")) {
	                int count = masterUserRepository.getMaxAdminCount(masterUsersEntity.getLocationCode());
	                if(masterUsersEntity.getLocationType().equalsIgnoreCase("CO")) {
	                	if(count >= maxAdminCoCount) {
	                		response.put(Constant.STATUS, 201);
	                		response.put(Constant.MESSAGE, Constant.FAILED);
	                        response.put(Constant.DATA,"This user cannot be added as Admin as limit of max 10 admins has been reached.");
	                        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
	                	}
	                }else if(masterUsersEntity.getLocationType().equalsIgnoreCase("ZO")) {
	                	if(count >= maxAdminZoCount) {
	                		response.put(Constant.STATUS, 201);
	                		response.put(Constant.MESSAGE, Constant.FAILED);
	                        response.put(Constant.DATA,"This user cannot be added as Admin as limit of max 4 admins has been reached.");
	                        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
	                	}
	                }else if(masterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {
	                	if(count >= maxAdminUoCount) {
	                		response.put(Constant.STATUS, 201);
	                		response.put(Constant.MESSAGE, Constant.FAILED);
	                        response.put(Constant.DATA,"This user cannot be added as Admin as limit of max 4 admins has been reached.");
	                        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
	                	}
	                }
                }
            	
                if (isValid(masterUsersEntity, "ADD")) {
                    masterUsersEntity.setCreatedOn(date);
                    masterUsersEntity.setModifiedOn(date);
                    masterUsersEntity.setDateOfAppointment(date);
                    
                    if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("CO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("CO")) {
                    	
                    	masterUsersEntity.setUserName(masterUsersEntity.getUserName().toLowerCase());
                    	masterUsersEntity.setEmail(masterUsersEntity.getEmail().toLowerCase());
                    	masterUsersEntity.setEmailId(masterUsersEntity.getEmailId().toLowerCase());
                    	masterUsersEntity.setSrNumber(masterUsersEntity.getSrNumber().toLowerCase());
                    	
                        MasterUsersEntity masterUser = masterUserRepository.save(masterUsersEntity);
                        MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
                        MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
                        historyDetails.setAdminUserName(userDetails.getUserName());
                        historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
                        historyDetails.setAdminUserLocationType(userDetails.getLocationType());
                        historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
                        historyDetails.setUserName(masterUsersEntity.getUserName());
                        historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
                        historyDetails.setRemarks(masterUsersEntity.getRemarks());
                        historyDetails.setUserLocationType(masterUsersEntity.getLocationType());
                        historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
                        historyDetails.setAdminUserActivityPerformed("User Added");
                        historyDetails.setUserModule("NA");
                        if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")) {
                         	historyDetails.setIsUserAdmin("ADMN");	
                         }else if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
                         	historyDetails.setIsUserAdmin("ORD");
                        }
                        if (masterUsersEntity.getCategory().equalsIgnoreCase("Marketing_Officer")) {
                      	  historyDetails.setIsUserMarketingNonMarketing("MKTG OFF");
                        }else if(masterUsersEntity.getCategory().equalsIgnoreCase("Non- Marketing_Officer")) {
                      	  historyDetails.setIsUserMarketingNonMarketing("NONMKTG OFF");
                        }
                        historyDetails.setUserOldRoles("NA");
                        historyDetails.setUserNewRoles("NA");
                        historyDetails.setCreatedBy(userDetails.getUserName());
                        historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
                        masterUsersHistoryDetailsRepository.save(historyDetails);
                        response.put(Constant.STATUS, 200);
                        response.put(Constant.MESSAGE, Constant.SUCCESS);
                        response.put("Data", masterUsersEntity.getMasterUserId());


                }

                    else if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("CO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("ZO")) {
                    	 	
                    	 	masterUsersEntity.setUserName(masterUsersEntity.getUserName().toLowerCase());
                    	 	masterUsersEntity.setEmail(masterUsersEntity.getEmail().toLowerCase());
                    	 	masterUsersEntity.setEmailId(masterUsersEntity.getEmailId().toLowerCase());
                    	 	masterUsersEntity.setSrNumber(masterUsersEntity.getSrNumber().toLowerCase());
                     	
                            MasterUsersEntity masterUser = masterUserRepository.save(masterUsersEntity);
                            MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
                            MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
                            historyDetails.setAdminUserName(userDetails.getUserName());
                            historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
                            historyDetails.setAdminUserLocationType(userDetails.getLocationType());
                            historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
                            historyDetails.setUserName(masterUsersEntity.getUserName());
                            historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
                            historyDetails.setRemarks(masterUsersEntity.getRemarks());
                            historyDetails.setUserLocationType(masterUsersEntity.getLocationType());
                            historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
                            historyDetails.setAdminUserActivityPerformed("User Added");
                            historyDetails.setUserModule("NA");
                            if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")) {
                             	historyDetails.setIsUserAdmin("ADMN");	
                             }else if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
                             	historyDetails.setIsUserAdmin("ORD");
                            }
                            if (masterUsersEntity.getCategory().equalsIgnoreCase("Marketing_Officer")) {
                            	  historyDetails.setIsUserMarketingNonMarketing("MKTG OFF");
                            }else if(masterUsersEntity.getCategory().equalsIgnoreCase("Non- Marketing_Officer")) {
                            	  historyDetails.setIsUserMarketingNonMarketing("NONMKTG OFF");
                            }
                            historyDetails.setUserOldRoles("NA");
                            historyDetails.setUserNewRoles("NA");
                            historyDetails.setCreatedBy(userDetails.getUserName());
                            historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
                            masterUsersHistoryDetailsRepository.save(historyDetails);
                            response.put(Constant.STATUS, 200);
                            response.put(Constant.MESSAGE, Constant.SUCCESS);
                            response.put("Data", masterUsersEntity.getMasterUserId());


                    }
                     else if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("CO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {

                        
                        	
                        	masterUsersEntity.setUserName(masterUsersEntity.getUserName().toLowerCase());
                        	masterUsersEntity.setEmail(masterUsersEntity.getEmail().toLowerCase());
                        	masterUsersEntity.setEmailId(masterUsersEntity.getEmailId().toLowerCase());
                        	masterUsersEntity.setSrNumber(masterUsersEntity.getSrNumber().toLowerCase());
                        	
                            MasterUsersEntity masterUser = masterUserRepository.save(masterUsersEntity);
                            MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
                            MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
                            historyDetails.setAdminUserName(userDetails.getUserName());
                            historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
                            historyDetails.setAdminUserLocationType(userDetails.getLocationType());
                            historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
                            historyDetails.setUserName(masterUsersEntity.getUserName());
                            historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
                            historyDetails.setRemarks(masterUsersEntity.getRemarks());
                            historyDetails.setUserLocationType(masterUsersEntity.getLocationType());
                            historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
                            historyDetails.setAdminUserActivityPerformed("User Added");
                            historyDetails.setUserModule("NA");
                            if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")) {
                             	historyDetails.setIsUserAdmin("ADMN");	
                             }else if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
                             	historyDetails.setIsUserAdmin("ORD");
                            }
                            if (masterUsersEntity.getCategory().equalsIgnoreCase("Marketing_Officer")) {
                          	  historyDetails.setIsUserMarketingNonMarketing("MKTG OFF");
                            }else if(masterUsersEntity.getCategory().equalsIgnoreCase("Non- Marketing_Officer")) {
                          	  historyDetails.setIsUserMarketingNonMarketing("NONMKTG OFF");
                            }
                            historyDetails.setUserOldRoles("NA");
                            historyDetails.setUserNewRoles("NA");
                            historyDetails.setCreatedBy(userDetails.getUserName());
                            historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
                            masterUsersHistoryDetailsRepository.save(historyDetails);
                            response.put(Constant.STATUS, 200);
                            response.put(Constant.MESSAGE, Constant.SUCCESS);
                            response.put("Data", masterUsersEntity.getMasterUserId());

                    }
                     else if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("ZO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("ZO")) {
                    	 	
                    	 	masterUsersEntity.setUserName(masterUsersEntity.getUserName().toLowerCase());
                     		masterUsersEntity.setEmail(masterUsersEntity.getEmail().toLowerCase());
                     		masterUsersEntity.setEmailId(masterUsersEntity.getEmailId().toLowerCase());
                     		masterUsersEntity.setSrNumber(masterUsersEntity.getSrNumber().toLowerCase());
                     	
                            MasterUsersEntity masterUser = masterUserRepository.save(masterUsersEntity);
                            MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
                            MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
                            historyDetails.setAdminUserName(userDetails.getUserName());
                            historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
                            historyDetails.setAdminUserLocationType(userDetails.getLocationType());
                            historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
                            historyDetails.setUserName(masterUsersEntity.getUserName());
                            historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
                            historyDetails.setRemarks(masterUsersEntity.getRemarks());
                            historyDetails.setUserLocationType(masterUsersEntity.getLocationType());
                            historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
                            historyDetails.setAdminUserActivityPerformed("User Added");
                            historyDetails.setUserModule("NA");
                            if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")) {
                             	historyDetails.setIsUserAdmin("ADMN");	
                             }else if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
                             	historyDetails.setIsUserAdmin("ORD");
                            }
                            if (masterUsersEntity.getCategory().equalsIgnoreCase("Marketing_Officer")) {
                          	  historyDetails.setIsUserMarketingNonMarketing("MKTG OFF");
                            }else if(masterUsersEntity.getCategory().equalsIgnoreCase("Non- Marketing_Officer")) {
                          	  historyDetails.setIsUserMarketingNonMarketing("NONMKTG OFF");
                            }
                            historyDetails.setUserOldRoles("NA");
                            historyDetails.setUserNewRoles("NA");
                            historyDetails.setCreatedBy(userDetails.getUserName());
                            historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
                            masterUsersHistoryDetailsRepository.save(historyDetails);
                            response.put(Constant.STATUS, 200);
                            response.put(Constant.MESSAGE, Constant.SUCCESS);
                            response.put("Data", masterUsersEntity.getMasterUserId());        
                    }

                     else  if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("ZO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {

                        if(masterUsersEntity.getLocationType().equalsIgnoreCase("UO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")) {
                        	
                        	masterUsersEntity.setUserName(masterUsersEntity.getUserName().toLowerCase());
                        	masterUsersEntity.setEmail(masterUsersEntity.getEmail().toLowerCase());
                        	masterUsersEntity.setEmailId(masterUsersEntity.getEmailId().toLowerCase());
                        	masterUsersEntity.setSrNumber(masterUsersEntity.getSrNumber().toLowerCase());
                        	
                            MasterUsersEntity masterUser = masterUserRepository.save(masterUsersEntity);
                            MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
                            MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
                            historyDetails.setAdminUserName(userDetails.getUserName());
                            historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
                            historyDetails.setAdminUserLocationType(userDetails.getLocationType());
                            historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
                            historyDetails.setUserName(masterUsersEntity.getUserName());
                            historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
                            historyDetails.setRemarks(masterUsersEntity.getRemarks());
                            historyDetails.setUserLocationType(masterUsersEntity.getLocationType());
                            historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
                            historyDetails.setAdminUserActivityPerformed("User Added");
                            historyDetails.setUserModule("NA");
                            if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")) {
                             	historyDetails.setIsUserAdmin("ADMN");	
                             }else if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
                             	historyDetails.setIsUserAdmin("ORD");
                            }
                            if (masterUsersEntity.getCategory().equalsIgnoreCase("Marketing_Officer")) {
                          	  historyDetails.setIsUserMarketingNonMarketing("MKTG OFF");
                            }else if(masterUsersEntity.getCategory().equalsIgnoreCase("Non- Marketing_Officer")) {
                          	  historyDetails.setIsUserMarketingNonMarketing("NONMKTG OFF");
                            }
                            historyDetails.setUserOldRoles("NA");
                            historyDetails.setUserNewRoles("NA");
                            historyDetails.setCreatedBy(userDetails.getUserName());
                            historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
                            masterUsersHistoryDetailsRepository.save(historyDetails);
                            response.put(Constant.STATUS, 200);
                            response.put(Constant.MESSAGE, Constant.SUCCESS);
                            response.put("Data", masterUsersEntity.getMasterUserId());

                        }else {
                            response.put(Constant.STATUS, 201);
                            response.put(Constant.MESSAGE, Constant.FAILED);
                            response.put(Constant.DATA,"Ordinary users can be added by their respective Admin only");
                        }
                    } 
                     else if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("UO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {
                    	
                    	masterUsersEntity.setUserName(masterUsersEntity.getUserName().toLowerCase());
                     	masterUsersEntity.setEmail(masterUsersEntity.getEmail().toLowerCase());
                     	masterUsersEntity.setEmailId(masterUsersEntity.getEmailId().toLowerCase());
                     	masterUsersEntity.setSrNumber(masterUsersEntity.getSrNumber().toLowerCase());
                     	
                        MasterUsersEntity masterUser = masterUserRepository.save(masterUsersEntity);
                        MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
                        MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
                        historyDetails.setAdminUserName(userDetails.getUserName());
                        historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
                        historyDetails.setAdminUserLocationType(userDetails.getLocationType());
                        historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
                        historyDetails.setUserName(masterUsersEntity.getUserName());
                        historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
                        historyDetails.setRemarks(masterUsersEntity.getRemarks());
                        historyDetails.setUserLocationType(masterUsersEntity.getLocationType());
                        historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
                        historyDetails.setAdminUserActivityPerformed("User Added");
                        historyDetails.setUserModule("NA");
                        if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")) {
                         	historyDetails.setIsUserAdmin("ADMN");	
                         }else if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
                         	historyDetails.setIsUserAdmin("ORD");
                        }
                        if (masterUsersEntity.getCategory().equalsIgnoreCase("Marketing_Officer")) {
                      	  historyDetails.setIsUserMarketingNonMarketing("MKTG OFF");
                        }else if(masterUsersEntity.getCategory().equalsIgnoreCase("Non- Marketing_Officer")) {
                      	  historyDetails.setIsUserMarketingNonMarketing("NONMKTG OFF");
                        }
                        historyDetails.setUserOldRoles("NA");
                        historyDetails.setUserNewRoles("NA");
                        historyDetails.setCreatedBy(userDetails.getUserName());
                        historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
                        masterUsersHistoryDetailsRepository.save(historyDetails);
                        response.put(Constant.STATUS, 200);
                        response.put(Constant.MESSAGE, Constant.SUCCESS);
                        response.put("Data", masterUsersEntity.getMasterUserId());        
                } 

                     else if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("UO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("SO")) {

                    if(masterUsersEntity.getLocationType().equalsIgnoreCase("SO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
                    	masterUsersEntity.setUserName(masterUsersEntity.getUserName().toLowerCase());
                    	masterUsersEntity.setEmail(masterUsersEntity.getEmail().toLowerCase());
                    	masterUsersEntity.setEmailId(masterUsersEntity.getEmailId().toLowerCase());
                    	masterUsersEntity.setSrNumber(masterUsersEntity.getSrNumber().toLowerCase());
                    	
                        MasterUsersEntity masterUser = masterUserRepository.save(masterUsersEntity);
                        MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
                        MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
                        historyDetails.setAdminUserName(userDetails.getUserName());
                        historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
                        historyDetails.setAdminUserLocationType(userDetails.getLocationType());
                        historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
                        historyDetails.setUserName(masterUsersEntity.getUserName());
                        historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
                        historyDetails.setRemarks(masterUsersEntity.getRemarks());
                        historyDetails.setUserLocationType(masterUsersEntity.getLocationType());
                        historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
                        historyDetails.setAdminUserActivityPerformed("User Added");
                        historyDetails.setUserModule("NA");
                        if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")) {
                         	historyDetails.setIsUserAdmin("ADMN");	
                         }else if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
                         	historyDetails.setIsUserAdmin("ORD");
                        }
                        if (masterUsersEntity.getCategory().equalsIgnoreCase("Marketing_Officer")) {
                      	  historyDetails.setIsUserMarketingNonMarketing("MKTG OFF");
                        }else if(masterUsersEntity.getCategory().equalsIgnoreCase("Non- Marketing_Officer")) {
                      	  historyDetails.setIsUserMarketingNonMarketing("NONMKTG OFF");
                        }
                        historyDetails.setUserOldRoles("NA");
                        historyDetails.setUserNewRoles("NA");
                        historyDetails.setCreatedBy(userDetails.getUserName());
                        historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
                        masterUsersHistoryDetailsRepository.save(historyDetails);
                        response.put(Constant.STATUS, 200);
                        response.put(Constant.MESSAGE, Constant.SUCCESS);
                        response.put("Data", masterUsersEntity.getMasterUserId());

                    }else {
                        response.put(Constant.STATUS, 201);
                        response.put(Constant.MESSAGE,"SO users can have only ordinary user access");
                    }
                     } 
                     else {
                            response.put(Constant.STATUS, 201);
                            response.put(Constant.DATA, "User does not belong to the allowed location type");
                            response.put(Constant.MESSAGE, Constant.FAILED);
                        }

                    if(masterUsersEntity.getLocationType().equalsIgnoreCase("CO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y") && response!=null && !String.valueOf(response.get(Constant.STATUS)).equalsIgnoreCase("201")) {
                        CoAdminEntity coAdmin = new CoAdminEntity();
                        coAdmin.setMasterUserId(masterUsersEntity.getMasterUserId());
                        coAdmin.setCoAdminName(masterUsersEntity.getLocation());
                        coAdmin.setLocationCode(masterUsersEntity.getLocationCode());
                        coAdmin.setIsActive(masterUsersEntity.getIsActive());
                        coAdmin.setIsDeleted(masterUsersEntity.getIsDeleted());
                        coAdmin.setCreatedBy(masterUsersEntity.getCreatedBy());
                        coAdmin.setCreatedOn(date);
                        coAdmin.setModifiedBy(masterUsersEntity.getModifiedBy());
                        coAdmin.setModifiedOn(date);
                        CoAdminEntity CoAdmin = coAdminRepository.save(coAdmin);
                        UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
                        userRoleTypeMappingEntity.setAppModuleId(1L);;
                        userRoleTypeMappingEntity.setRoleTypeId(11L);
                        userRoleTypeMappingEntity.setMasterUserId(coAdmin.getMasterUserId());
                        userRoleTypeMappingEntity.setIsActive("Y");
                        userRoleTypeMappingEntity.setIsDeleted("N");
                    //    userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
                        userRoleTypeMappingEntity.setModifiedBy(masterUsersEntity.getModifiedBy());
                        userRoleTypeMappingEntity.setModifiedOn(date);
                        userRoleTypeMappingEntity.setCreatedBy(masterUsersEntity.getCreatedBy());
                        userRoleTypeMappingEntity.setCreatedOn(date);
                        userRoleTypeRepository.save(userRoleTypeMappingEntity);

                    }
                     if(masterUsersEntity.getLocationType().equalsIgnoreCase("ZO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y") && response!=null && !String.valueOf(response.get(Constant.STATUS)).equalsIgnoreCase("201")) {
                        ZOAdminEntity zoAdmin = new ZOAdminEntity();

                        zoAdmin.setMasterUserId(masterUsersEntity.getMasterUserId());
                        zoAdmin.setLocation(masterUsersEntity.getLocation());
                        zoAdmin.setLocationCode(masterUsersEntity.getLocationCode());
                        zoAdmin.setIsActive(masterUsersEntity.getIsActive());
                        zoAdmin.setIsDeleted(masterUsersEntity.getIsDeleted());
                        zoAdmin.setCreatedBy(masterUsersEntity.getCreatedBy());
                        zoAdmin.setCreatedOn(date);
                        zoAdmin.setModifiedBy(masterUsersEntity.getModifiedBy());
                        zoAdmin.setModifiedOn(date);
                        ZOAdminEntity ZoAdmin = zoAdminRepository.save(zoAdmin);
                        UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
                        userRoleTypeMappingEntity.setAppModuleId(1L);;
                        userRoleTypeMappingEntity.setRoleTypeId(11L);
                        userRoleTypeMappingEntity.setMasterUserId(zoAdmin.getMasterUserId());
                        userRoleTypeMappingEntity.setIsActive("Y");
                        userRoleTypeMappingEntity.setIsDeleted("N");
                    //    userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
                        userRoleTypeMappingEntity.setModifiedBy(masterUsersEntity.getModifiedBy());
                        userRoleTypeMappingEntity.setModifiedOn(date);
                        userRoleTypeMappingEntity.setCreatedBy(masterUsersEntity.getCreatedBy());
                        userRoleTypeMappingEntity.setCreatedOn(date);
                        userRoleTypeRepository.save(userRoleTypeMappingEntity);
                    }
                      if(masterUsersEntity.getLocationType().equalsIgnoreCase("UO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y") && response!=null && !String.valueOf(response.get(Constant.STATUS)).equalsIgnoreCase("201")) {

                          UOAdminEntity uoAdmin = new UOAdminEntity();

 


                            uoAdmin.setLocation(masterUsersEntity.getLocation());
                            uoAdmin.setLocationCode(masterUsersEntity.getLocationCode());
                            uoAdmin.setLocationType(masterUsersEntity.getLocationType());
                            uoAdmin.setMasterUserId(masterUsersEntity.getMasterUserId());
                            uoAdmin.setIsActive(masterUsersEntity.getIsActive());
                            uoAdmin.setIsDeleted(masterUsersEntity.getIsDeleted());
                            uoAdmin.setModifiedOn(date);
                            uoAdmin.setModifiedBy(masterUsersEntity.getModifiedBy());
                            uoAdmin.setCreatedBy(masterUsersEntity.getCreatedBy());
                            uoAdmin.setCreatedOn(date);
                            UOAdminEntity UoAdmin = uoAdminRepository.save(uoAdmin);
                            UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
                            userRoleTypeMappingEntity.setAppModuleId(1L);;
                            userRoleTypeMappingEntity.setRoleTypeId(11L);
                            userRoleTypeMappingEntity.setMasterUserId(uoAdmin.getMasterUserId());
                            userRoleTypeMappingEntity.setIsActive("Y");
                            userRoleTypeMappingEntity.setIsDeleted("N");
                        //    userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
                            userRoleTypeMappingEntity.setModifiedBy(masterUsersEntity.getModifiedBy());
                            userRoleTypeMappingEntity.setModifiedOn(date);
                            userRoleTypeMappingEntity.setCreatedBy(masterUsersEntity.getCreatedBy());
                            userRoleTypeMappingEntity.setCreatedOn(date);
                            userRoleTypeRepository.save(userRoleTypeMappingEntity);
                      }

                      if (masterUsersEntity.getLocationType().equalsIgnoreCase("SO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")&& response!=null && !String.valueOf(response.get(Constant.STATUS)).equalsIgnoreCase("201")) {

                          SOAdminEntity soAdmin = new SOAdminEntity();

 

                            soAdmin.setLocation(masterUsersEntity.getLocation());
                            soAdmin.setLocationCode(masterUsersEntity.getLocationCode());
                            soAdmin.setLocationType(masterUsersEntity.getLocationType());
                            soAdmin.setMasterUserId(masterUsersEntity.getMasterUserId());
                            soAdmin.setIsActive(masterUsersEntity.getIsActive());
                            soAdmin.setIsDeleted(masterUsersEntity.getIsDeleted());
                            soAdmin.setModifiedOn(date);
                            soAdmin.setModifiedBy(masterUsersEntity.getModifiedBy());
                            soAdmin.setCreatedBy(masterUsersEntity.getCreatedBy());
                            soAdmin.setCreatedOn(date);
                            UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
                            userRoleTypeMappingEntity.setAppModuleId(1L);;
                            userRoleTypeMappingEntity.setRoleTypeId(11L);
                            userRoleTypeMappingEntity.setMasterUserId(soAdmin.getMasterUserId());
                            userRoleTypeMappingEntity.setIsActive("Y");
                            userRoleTypeMappingEntity.setIsDeleted("N");
                        //    userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
                            userRoleTypeMappingEntity.setModifiedBy(masterUsersEntity.getModifiedBy());
                            userRoleTypeMappingEntity.setModifiedOn(date);
                            userRoleTypeMappingEntity.setCreatedBy(masterUsersEntity.getCreatedBy());
                            userRoleTypeMappingEntity.setCreatedOn(date);
                            userRoleTypeRepository.save(userRoleTypeMappingEntity);
                      }

            } else {
                response.put(Constant.STATUS, 201);
                response.put(Constant.DATA, "User already added in system");
                response.put(Constant.MESSAGE, Constant.FAILED);
            }

 

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            }

        } catch (Exception exception) {
            logger.info("Exception" + exception.getMessage());
        }
        return null;
    }

	private boolean isValid(MasterUsersEntity masterUsersEntity, String operation) {
		Integer result = masterUserRepository.findDuplicate(masterUsersEntity.getSrNumber2());
		if(operation.equals("ADD") && !(result > 0)) {
			return true;
		}
		if(operation.equals("EDIT") && !(result > 1)) {
			return true;
		}
		return false;
	}

	/*
	 * Description: This function is used for updating the data in MASTER USERS Module
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@Override
	public ResponseEntity<Map<String, Object>> editMasterUser(MasterUsersEntity masterUsersEntity) throws Exception{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		
		Long maxAdminCoCount=commonConfigRepository.getValueFromCommonConfig("MAXADMINCOUNTCO");
        Long maxAdminZoCount=commonConfigRepository.getValueFromCommonConfig("MAXADMINCOUNTZO");
        Long maxAdminUoCount=commonConfigRepository.getValueFromCommonConfig("MAXADMINCOUNTUO");

		try {

			
			if (masterUsersEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} else {
				
				
				
				 //Check max admin count
				MasterUsersEntity masterUserExisting = masterUserRepository.getMasterUserDetail(masterUsersEntity.getMasterUserId());	
				String loggedInUsername = masterUsersEntity.getLoggedInUsername();
				String userName = masterUserExisting.getUserName();
				String newLocationCode = masterUsersEntity.getLocationCode();
		        String newLocationType = masterUsersEntity.getLocationType();
				if(loggedInUsername.equalsIgnoreCase(userName))  {
						
					    response.put(Constant.STATUS, 201);
						response.put(Constant.MESSAGE, Constant.FAILED);
						response.put(Constant.DATA, "User cannot be edited as logged in user and user being edited are same.");
						 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
					
				}
				else if(masterUsersEntity != null && masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")) {
	                int count = masterUserRepository.getMaxAdminCount(masterUsersEntity.getLocationCode());
	                if(masterUsersEntity.getLocationType().equalsIgnoreCase("CO")) {
	                	if(count >= maxAdminCoCount && !(masterUserExisting.getIsAdmin().equalsIgnoreCase(masterUsersEntity.getIsAdmin()))) {
	                        response.put(Constant.STATUS, 201);
	                        response.put(Constant.MESSAGE, Constant.FAILED);
	                        response.put(Constant.DATA,"This user cannot be changed as Admin as limit of max 10 admins has been reached.");
	                        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
	                	}
	                }else if(masterUsersEntity.getLocationType().equalsIgnoreCase("ZO")) {
	                	if(count >= maxAdminZoCount && !(masterUserExisting.getIsAdmin().equalsIgnoreCase(masterUsersEntity.getIsAdmin()))) {
	                		response.put(Constant.STATUS, 201);
	                		response.put(Constant.MESSAGE, Constant.FAILED);
	                        response.put(Constant.DATA,"This user cannot be changed as Admin as limit of max 4 admins has been reached.");
	                        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
	                	}
	                }else if(masterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {
	                	if(count >= maxAdminUoCount && !(masterUserExisting.getIsAdmin().equalsIgnoreCase(masterUsersEntity.getIsAdmin()))) {
	                		response.put(Constant.STATUS, 201);
	                		response.put(Constant.MESSAGE, Constant.FAILED);
	                        response.put(Constant.DATA,"This user cannot be changed as Admin as limit of max 4 admins has been reached.");
	                        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
	                	}
	                }
                }
				
				MasterUsersEntity masterUsersEntitynew = masterUserRepository.getAllMasterUserDetailByUserName(masterUsersEntity.getUserName());
				String oldisAdminValue = masterUsersEntitynew.getIsAdmin();
				String oldCategoryValue = masterUsersEntitynew.getCategory();
				String oldLocationCode = masterUserExisting.getLocationCode();
                String oldLocationType = masterUserExisting.getLocationType();
				if (isValid(masterUsersEntity, "EDIT")) {
					
					Date date = new Date();
					MasterUsersEntity existingMasterUser = masterUserRepository.getMasterUserDetail(masterUsersEntity.getMasterUserId());
					if(!existingMasterUser.getLocationCode().equalsIgnoreCase(masterUsersEntity.getLocationCode())) {
						final String uri = "http://10.240.3.146:80/HRM_WebService/service/WSJ_51_00003/post";
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);	
							Map<String, String> requestBody = new HashMap<String, String>();
							requestBody.put("srnumber", masterUsersEntity.getSrNumber2());
							requestBody.put("emailId", "");
							ObjectMapper mapper = new ObjectMapper();
							HttpEntity formEntity = new HttpEntity<Map<String, String>>(requestBody, headers);
						ResponseEntity<String> responseFormAPI = restTemplate.postForEntity(uri, formEntity, String.class);
						logger.info("RESPONSE FROM THE REST TEMPLATE");
						
					if (responseFormAPI.getStatusCode() == HttpStatus.OK && responseFormAPI.getStatusCodeValue() == 200) 
					{
					UserDetailsResponse accessResponse = new UserDetailsResponse();
					Long Expiryday=commonConfigRepository.getValueFromCommonConfig("MAXDAYSFORUSEREXPIRY");   // to cooment 	
					accessResponse = mapper.readValue(responseFormAPI.getBody(), UserDetailsResponse.class);
						if(accessResponse.getUnitCode().equalsIgnoreCase("C099"))
						{
							accessResponse.setUnitCode("C900");				
						}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO01")) {
							accessResponse.setUnitCode("100");
						}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO02")) {
							accessResponse.setUnitCode("200");
						}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO03")) {
							accessResponse.setUnitCode("300");
						}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO04")) {
							accessResponse.setUnitCode("400");
						}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO05")) {
							accessResponse.setUnitCode("500");
						}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO06")) {
							accessResponse.setUnitCode("600");
						}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO07")) {
							accessResponse.setUnitCode("700");
						}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO08")) {
							accessResponse.setUnitCode("800");
						}
						
//						if(accessResponse.getUnitCode().equalsIgnoreCase("C900")) {
//							masterUsersEntity.setLocation("PGS Central Office Mumbai");
//							masterUsersEntity.setLocationCode("C900");
//							masterUsersEntity.setLocationType("CO");
//							if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")) {
//								CoAdminEntity  coAdminEntity=new CoAdminEntity();
//								coAdminEntity.setCoAdminName("PGS Central Office Mumbai");
//								coAdminEntity.setIsActive("Y");
//								coAdminEntity.setIsDeleted("N");
//								coAdminEntity.setCreatedBy("SYADMIN");
//								coAdminEntity.setCreatedOn(date);
//								coAdminEntity.setModifiedBy("SYADMIN");
//								coAdminEntity.setModifiedOn(date);
//							}
//						}else if(accessResponse.getUnitCode().equalsIgnoreCase("100")|| accessResponse.getUnitCode().equalsIgnoreCase("200") || accessResponse.getUnitCode().equalsIgnoreCase("300")
//								|| accessResponse.getUnitCode().equalsIgnoreCase("400") || accessResponse.getUnitCode().equalsIgnoreCase("500") || accessResponse.getUnitCode().equalsIgnoreCase("600")
//								|| accessResponse.getUnitCode().equalsIgnoreCase("700") || accessResponse.getUnitCode().equalsIgnoreCase("800")) {
//							ZonalEntity zoEntity =	zonalEntityRepository.getZoneDetailsByZoneCode(accessResponse.getUnitCode());
////							masterUsersEntity.setLocation(zoEntity.getDescription());
////							masterUsersEntity.setLocationType("ZO");
////							masterUsersEntity.setLocationCode(zoEntity.getZonalCode());
//						}else if(accessResponse.getUnitCode().startsWith("G") || accessResponse.getUnitCode().startsWith("g")) {
//							UnitEntity unitCodeEntity = unitEntityRepository
//									.getServiceDetailsByUnitCode(accessResponse.getUnitCode());
////							masterUsersEntity.setLocation(unitCodeEntity.getDescription());
////							masterUsersEntity.setLocationCode(unitCodeEntity.getUnitCode());
//						}
//						
						
						    if(!accessResponse.getUnitCode().equalsIgnoreCase(masterUsersEntity.getLocationCode())) {
								int maxExpiryDays=Expiryday.intValue();
						    	existingMasterUser.setUserexpiryon(setExpiry(maxExpiryDays));
						    }else {
						    	
						    	existingMasterUser.setUserexpiryon(null);
						    }
					}
					}else {
						existingMasterUser.setLocation(masterUsersEntity.getLocation());
						existingMasterUser.setLocationType(masterUsersEntity.getLocationType());
						existingMasterUser.setLocationCode(masterUsersEntity.getLocationCode());
					}
					
					
					existingMasterUser.setMasterUserId(masterUsersEntity.getMasterUserId());
					existingMasterUser.setSrNumber(masterUsersEntity.getSrNumber());
					existingMasterUser.setMobile(masterUsersEntity.getMobile());
					existingMasterUser.setEmailId(masterUsersEntity.getEmail());
					existingMasterUser.setCadre(masterUsersEntity.getCadre());
					existingMasterUser.setDesignation(masterUsersEntity.getDesignation());
					existingMasterUser.setClassName(masterUsersEntity.getClassName());
					existingMasterUser.setModifiedBy(masterUsersEntity.getModifiedBy());
					existingMasterUser.setLocation(masterUsersEntity.getLocation());
					existingMasterUser.setModifiedOn(date);
					existingMasterUser.setCreatedBy(masterUsersEntity.getCreatedBy()); 
					existingMasterUser.setCreatedOn(masterUsersEntity.getCreatedOn());
					existingMasterUser.setLocationType(masterUsersEntity.getLocationType());
					existingMasterUser.setDateOfBirth(masterUsersEntity.getDateOfBirth());
					existingMasterUser.setSex(masterUsersEntity.getSex());
					existingMasterUser.setDateOfAppointment(masterUsersEntity.getDateOfAppointment());
					existingMasterUser.setMaritalStatus(masterUsersEntity.getMaritalStatus());
					existingMasterUser.setStatus(masterUsersEntity.getStatus());
					existingMasterUser.setIpAddress(masterUsersEntity.getIpAddress());
					existingMasterUser.setLocationCode(masterUsersEntity.getLocationCode());
					existingMasterUser.setEmail(masterUsersEntity.getEmailId());
					existingMasterUser.setUserName(masterUsersEntity.getUserName());
					existingMasterUser.setFirstName(masterUsersEntity.getFirstName());
					existingMasterUser.setLastName(masterUsersEntity.getLastName());
					existingMasterUser.setFullName(masterUsersEntity.getFullName());
					existingMasterUser.setMiddleName(masterUsersEntity.getMiddleName());
					existingMasterUser.setIsActive(masterUsersEntity.getIsActive());
					existingMasterUser.setIsDeleted(masterUsersEntity.getIsDeleted());
					existingMasterUser.setCategory(masterUsersEntity.getCategory());
					existingMasterUser.setIsAdmin(masterUsersEntity.getIsAdmin());
					existingMasterUser.setDeActivatedDate(masterUsersEntity.getDeActivatedDate());
					
					
					if(masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")) {
						coAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
						zoAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
						uoAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
						
						userRoleTypeRepository.DeleteAdminBymasterId(masterUsersEntity.getMasterUserId());
					}
					
					 
					   if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("CO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("CO")) {
							
						  masterUserRepository.save(existingMasterUser);
						  if((oldisAdminValue != null && !oldisAdminValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin())) || (oldCategoryValue != null && !oldCategoryValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin())) ) {
							  MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
	                          MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
	                          historyDetails.setAdminUserName(userDetails.getUserName());
	                          historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
	                          historyDetails.setAdminUserLocationType(userDetails.getLocationType());
	                          historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
	                          historyDetails.setUserName(masterUsersEntity.getUserName());
	                          historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
	                          historyDetails.setRemarks(masterUsersEntity.getRemarks());
	                          historyDetails.setUserLocationType(masterUsersEntity.getLocationType());    
                              if(oldLocationType != null && newLocationType != null && !oldLocationType.equalsIgnoreCase(newLocationType)) {
                                  historyDetails.setUserLocationType(oldLocationType + " to " + newLocationType);
                              }
                              historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
                              if(oldLocationCode != null && newLocationCode != null && !oldLocationCode.equalsIgnoreCase(newLocationCode)) {
                                  historyDetails.setUserLocationCode(oldLocationCode + " to " + newLocationCode);
                              }
	                          historyDetails.setAdminUserActivityPerformed("User Edited");
	                          historyDetails.setUserModule("NA");
	                          historyDetails.setIsUserAdmin(oldisAdminValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin()) ? "NA" : masterUsersEntity.getIsAdmin());
	                          if(historyDetails.getIsUserAdmin() != null && !historyDetails.getIsUserAdmin().equalsIgnoreCase("NA") && historyDetails.getIsUserAdmin().equalsIgnoreCase("Y")) {
	                        	  historyDetails.setIsUserAdmin("ORD to ADMN");  
	                          }else if(historyDetails.getIsUserAdmin() != null && !historyDetails.getIsUserAdmin().equalsIgnoreCase("NA") && historyDetails.getIsUserAdmin().equalsIgnoreCase("N")) {
	                        	  historyDetails.setIsUserAdmin("ADMN to ORD");
	                          }
	                          historyDetails.setIsUserMarketingNonMarketing(oldCategoryValue.equalsIgnoreCase(masterUsersEntity.getCategory()) ? "NA" : masterUsersEntity.getCategory());
	                          if(historyDetails.getIsUserMarketingNonMarketing() != null && !historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("NA") && historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("Marketing_Officer")) {
	                        	  historyDetails.setIsUserMarketingNonMarketing("NONMKTG OFF to MKTG OFF");  
	                          }else if(historyDetails.getIsUserMarketingNonMarketing() != null && !historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("NA") && historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("Non- Marketing_Officer")) {
	                        	  historyDetails.setIsUserMarketingNonMarketing("MKTG OFF to NONMKTG OFF");
	                          }
	                          historyDetails.setUserOldRoles("NA");
	                          historyDetails.setUserNewRoles("NA");
	                          historyDetails.setCreatedBy(userDetails.getUserName());
	                          historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
	                          historyDetails.setUserexpiryon(existingMasterUser.getUserexpiryon());
	                          historyDetails.setCategory(masterUsersEntity.getCategory());
	                          masterUsersHistoryDetailsRepository.save(historyDetails);
						  }
						  response.put(Constant.STATUS, 200);
							response.put(Constant.MESSAGE, Constant.SUCCESS);
							response.put("Data", "Updated Master User for: " + masterUsersEntity.getMasterUserId());
						
					}
					  else if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("CO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("ZO")) {
						
						  if((oldisAdminValue != null && !oldisAdminValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin())) || (oldCategoryValue != null && !oldCategoryValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin()))) {
							  MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
	                          MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
	                          historyDetails.setAdminUserName(userDetails.getUserName());
	                          historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
	                          historyDetails.setAdminUserLocationType(userDetails.getLocationType());
	                          historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
	                          historyDetails.setUserName(masterUsersEntity.getUserName());
	                          historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
	                          historyDetails.setRemarks(masterUsersEntity.getRemarks());
	                          historyDetails.setUserLocationType(masterUsersEntity.getLocationType());
	                          historyDetails.setRemarks(masterUsersEntity.getRemarks());
                              if(oldLocationType != null && newLocationType != null && !oldLocationType.equalsIgnoreCase(newLocationType)) {
                                  historyDetails.setUserLocationType(oldLocationType + " to " + newLocationType);
                              }
                              historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
                              if(oldLocationCode != null && newLocationCode != null && !oldLocationCode.equalsIgnoreCase(newLocationCode)) {
                                  historyDetails.setUserLocationCode(oldLocationCode + " to " + newLocationCode);
                              }
	                          historyDetails.setAdminUserActivityPerformed("User Edited");
	                          historyDetails.setUserModule("NA");
	                          historyDetails.setIsUserAdmin(oldisAdminValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin()) ? "NA" : masterUsersEntity.getIsAdmin());
	                          if(historyDetails.getIsUserAdmin() != null && !historyDetails.getIsUserAdmin().equalsIgnoreCase("NA") && historyDetails.getIsUserAdmin().equalsIgnoreCase("Y")) {
	                        	  historyDetails.setIsUserAdmin("ORD to ADMN");  
	                          }else if(historyDetails.getIsUserAdmin() != null && !historyDetails.getIsUserAdmin().equalsIgnoreCase("NA") && historyDetails.getIsUserAdmin().equalsIgnoreCase("N")) {
	                        	  historyDetails.setIsUserAdmin("ADMN to ORD");
	                          }
	                          historyDetails.setIsUserMarketingNonMarketing(oldCategoryValue.equalsIgnoreCase(masterUsersEntity.getCategory()) ? "NA" : masterUsersEntity.getCategory());
	                          if(historyDetails.getIsUserMarketingNonMarketing() != null && !historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("NA") && historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("Marketing_Officer")) {
	                        	  historyDetails.setIsUserMarketingNonMarketing("NONMKTG OFF to MKTG OFF");  
	                          }else if(historyDetails.getIsUserMarketingNonMarketing() != null && !historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("NA") && historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("Non- Marketing_Officer")) {
	                        	  historyDetails.setIsUserMarketingNonMarketing("MKTG OFF to NONMKTG OFF");
	                          }
	                          historyDetails.setUserOldRoles("NA");
	                          historyDetails.setUserNewRoles("NA");
	                          historyDetails.setCreatedBy(userDetails.getUserName());
	                          historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
	                          historyDetails.setUserexpiryon(existingMasterUser.getUserexpiryon());
	                          historyDetails.setCategory(masterUsersEntity.getCategory());
	                          masterUsersHistoryDetailsRepository.save(historyDetails);	
						}
						  response.put(Constant.STATUS, 200);
							response.put(Constant.MESSAGE, Constant.SUCCESS);
							response.put("Data", "Updated Master User for: " + masterUsersEntity.getMasterUserId());
					}
					   
					  else if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("CO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {
							
						  if((oldisAdminValue != null && !oldisAdminValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin())) || (oldCategoryValue != null && !oldCategoryValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin()))) {
							  MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
	                          MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
	                          historyDetails.setAdminUserName(userDetails.getUserName());
	                          historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
	                          historyDetails.setAdminUserLocationType(userDetails.getLocationType());
	                          historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
	                          historyDetails.setUserName(masterUsersEntity.getUserName());
	                          historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
	                          historyDetails.setRemarks(masterUsersEntity.getRemarks());
	                          historyDetails.setUserLocationType(masterUsersEntity.getLocationType());    
                              if(oldLocationType != null && newLocationType != null && !oldLocationType.equalsIgnoreCase(newLocationType)) {
                                  historyDetails.setUserLocationType(oldLocationType + " to " + newLocationType);
                              }
                              historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
                              if(oldLocationCode != null && newLocationCode != null && !oldLocationCode.equalsIgnoreCase(newLocationCode)) {
                                  historyDetails.setUserLocationCode(oldLocationCode + " to " + newLocationCode);
                              }
	                          historyDetails.setAdminUserActivityPerformed("User Edited");
	                          historyDetails.setUserModule("NA");
	                          historyDetails.setIsUserAdmin(oldisAdminValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin()) ? "NA" : masterUsersEntity.getIsAdmin());
	                          if(historyDetails.getIsUserAdmin() != null && !historyDetails.getIsUserAdmin().equalsIgnoreCase("NA") && historyDetails.getIsUserAdmin().equalsIgnoreCase("Y")) {
	                        	  historyDetails.setIsUserAdmin("ORD to ADMN");  
	                          }else if(historyDetails.getIsUserAdmin() != null && !historyDetails.getIsUserAdmin().equalsIgnoreCase("NA") && historyDetails.getIsUserAdmin().equalsIgnoreCase("N")) {
	                        	  historyDetails.setIsUserAdmin("ADMN to ORD");
	                          }
	                          historyDetails.setIsUserMarketingNonMarketing(oldCategoryValue.equalsIgnoreCase(masterUsersEntity.getCategory()) ? "NA" : masterUsersEntity.getCategory());
	                          if(historyDetails.getIsUserMarketingNonMarketing() != null && !historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("NA") && historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("Marketing_Officer")) {
	                        	  historyDetails.setIsUserMarketingNonMarketing("NONMKTG OFF to MKTG OFF");  
	                          }else if(historyDetails.getIsUserMarketingNonMarketing() != null && !historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("NA") && historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("Non- Marketing_Officer")) {
	                        	  historyDetails.setIsUserMarketingNonMarketing("MKTG OFF to NONMKTG OFF");
	                          }
	                          historyDetails.setUserOldRoles("NA");
	                          historyDetails.setUserNewRoles("NA");
	                          historyDetails.setCreatedBy(userDetails.getUserName());
	                          historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
	                          historyDetails.setUserexpiryon(existingMasterUser.getUserexpiryon());
	                          historyDetails.setCategory(masterUsersEntity.getCategory());
	                          masterUsersHistoryDetailsRepository.save(historyDetails);	
						}
						  response.put(Constant.STATUS, 200);
							response.put(Constant.MESSAGE, Constant.SUCCESS);
							response.put("Data", "Updated Master User for: " + masterUsersEntity.getMasterUserId());
					}
					   
					   
					  else if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("ZO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("ZO")) {
							
						 masterUserRepository.save(existingMasterUser);
						 if((oldisAdminValue != null && !oldisAdminValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin())) || (oldCategoryValue != null && !oldCategoryValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin()))) {
							 MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
	                         MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
	                         historyDetails.setAdminUserName(userDetails.getUserName());
	                         historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
	                         historyDetails.setAdminUserLocationType(userDetails.getLocationType());
	                         historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
	                         historyDetails.setUserName(masterUsersEntity.getUserName());
	                         historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
	                         historyDetails.setRemarks(masterUsersEntity.getRemarks());
	                         historyDetails.setUserLocationType(masterUsersEntity.getLocationType());    
                             if(oldLocationType != null && newLocationType != null && !oldLocationType.equalsIgnoreCase(newLocationType)) {
                                 historyDetails.setUserLocationType(oldLocationType + " to " + newLocationType);
                             }
                             historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
                             if(oldLocationCode != null && newLocationCode != null && !oldLocationCode.equalsIgnoreCase(newLocationCode)) {
                                 historyDetails.setUserLocationCode(oldLocationCode + " to " + newLocationCode);
                             }
	                         historyDetails.setAdminUserActivityPerformed("User Edited");
	                         historyDetails.setUserModule("NA");
	                         historyDetails.setIsUserAdmin(oldisAdminValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin()) ? "NA" : masterUsersEntity.getIsAdmin());
	                         if(historyDetails.getIsUserAdmin() != null && !historyDetails.getIsUserAdmin().equalsIgnoreCase("NA") && historyDetails.getIsUserAdmin().equalsIgnoreCase("Y")) {
	                        	  historyDetails.setIsUserAdmin("ORD to ADMN");  
	                          }else if(historyDetails.getIsUserAdmin() != null && !historyDetails.getIsUserAdmin().equalsIgnoreCase("NA") && historyDetails.getIsUserAdmin().equalsIgnoreCase("N")) {
	                        	  historyDetails.setIsUserAdmin("ADMN to ORD");
	                          }
	                          historyDetails.setIsUserMarketingNonMarketing(oldCategoryValue.equalsIgnoreCase(masterUsersEntity.getCategory()) ? "NA" : masterUsersEntity.getCategory());
	                          if(historyDetails.getIsUserMarketingNonMarketing() != null && !historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("NA") && historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("Marketing_Officer")) {
	                        	  historyDetails.setIsUserMarketingNonMarketing("NONMKTG OFF to MKTG OFF");  
	                          }else if(historyDetails.getIsUserMarketingNonMarketing() != null && !historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("NA") && historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("Non- Marketing_Officer")) {
	                        	  historyDetails.setIsUserMarketingNonMarketing("MKTG OFF to NONMKTG OFF");
	                          }
	                         historyDetails.setUserOldRoles("NA");
	                         historyDetails.setUserNewRoles("NA");
	                         historyDetails.setCreatedBy(userDetails.getUserName());
	                         historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
	                         historyDetails.setUserexpiryon(existingMasterUser.getUserexpiryon());
	                         historyDetails.setCategory(masterUsersEntity.getCategory());
	                         masterUsersHistoryDetailsRepository.save(historyDetails);
						 }
						 response.put(Constant.STATUS, 200);
							response.put(Constant.MESSAGE, Constant.SUCCESS);
							response.put("Data", "Updated Master User for: " + masterUsersEntity.getMasterUserId());
					
					 }
					  /*else if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("ZO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {
						
						if(masterUsersEntity.getLocationType().equalsIgnoreCase("UO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")) {
							masterUserRepository.save(existingMasterUser);
							 MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
	                            MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
	                            historyDetails.setAdminUserName(userDetails.getUserName());
	                            historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
	                            historyDetails.setAdminUserLocationType(userDetails.getLocationType());
	                            historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
	                            historyDetails.setUserName(masterUsersEntity.getUserName());
	                            historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
	                            historyDetails.setUserLocationType(masterUsersEntity.getLocationType());
	                            historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
	                            historyDetails.setAdminUserActivityPerformed("User Edited");
	                            historyDetails.setUserModule(null);
	                            historyDetails.setIsUserAdmin(MasterUsersEntitynew.getIsAdmin().equalsIgnoreCase(masterUsersEntity.getIsAdmin()) ? null : masterUsersEntity.getIsAdmin());
	                            historyDetails.setIsUserMarketingNonMarketing(MasterUsersEntitynew.getCategory().equalsIgnoreCase(masterUsersEntity.getCategory()) ? null : masterUsersEntity.getCategory());
	                            historyDetails.setUserOldRoles(null);
	                            historyDetails.setUserNewRoles(null);
	                            historyDetails.setCreatedBy(userDetails.getUserName());
	                            historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
	                            masterUsersHistoryDetailsRepository.save(historyDetails);
							response.put(Constant.STATUS, 200);
							response.put(Constant.MESSAGE, Constant.SUCCESS);
							response.put("Data", "Updated Master User for: " + masterUsersEntity.getMasterUserId());
							
						}else {
							response.put(Constant.STATUS, 201);
							response.put(Constant.MESSAGE,"Ordinary users can be edited by their respective Admin only");
						}
					} */
					 else if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("UO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {
						
						masterUserRepository.save(existingMasterUser);
						if((oldisAdminValue != null && !oldisAdminValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin())) || (oldCategoryValue != null && !oldCategoryValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin()))) {
							 MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
	                         MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
	                         historyDetails.setAdminUserName(userDetails.getUserName());
	                         historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
	                         historyDetails.setAdminUserLocationType(userDetails.getLocationType());
	                         historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
	                         historyDetails.setUserName(masterUsersEntity.getUserName());
	                         historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
	                         historyDetails.setRemarks(masterUsersEntity.getRemarks());
	                         historyDetails.setUserLocationType(masterUsersEntity.getLocationType());    
                             if(oldLocationType != null && newLocationType != null && !oldLocationType.equalsIgnoreCase(newLocationType)) {
                                 historyDetails.setUserLocationType(oldLocationType + " to " + newLocationType);
                             }
                             historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
                             if(oldLocationCode != null && newLocationCode != null && !oldLocationCode.equalsIgnoreCase(newLocationCode)) {
                                 historyDetails.setUserLocationCode(oldLocationCode + " to " + newLocationCode);
                             }
	                         historyDetails.setAdminUserActivityPerformed("User Edited");
	                         historyDetails.setUserModule("NA");
	                         historyDetails.setIsUserAdmin(oldisAdminValue.equalsIgnoreCase(masterUsersEntity.getIsAdmin()) ? "NA" : masterUsersEntity.getIsAdmin());
	                         if(historyDetails.getIsUserAdmin() != null && !historyDetails.getIsUserAdmin().equalsIgnoreCase("NA") && historyDetails.getIsUserAdmin().equalsIgnoreCase("Y")) {
	                        	  historyDetails.setIsUserAdmin("ORD to ADMN");  
	                          }else if(historyDetails.getIsUserAdmin() != null && !historyDetails.getIsUserAdmin().equalsIgnoreCase("NA") && historyDetails.getIsUserAdmin().equalsIgnoreCase("N")) {
	                        	  historyDetails.setIsUserAdmin("ADMN to ORD");
	                          }
	                          historyDetails.setIsUserMarketingNonMarketing(oldCategoryValue.equalsIgnoreCase(masterUsersEntity.getCategory()) ? "NA" : masterUsersEntity.getCategory());
	                          if(historyDetails.getIsUserMarketingNonMarketing() != null && !historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("NA") && historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("Marketing_Officer")) {
	                        	  historyDetails.setIsUserMarketingNonMarketing("NONMKTG OFF to MKTG OFF");  
	                          }else if(historyDetails.getIsUserMarketingNonMarketing() != null && !historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("NA") && historyDetails.getIsUserMarketingNonMarketing().equalsIgnoreCase("Non- Marketing_Officer")) {
	                        	  historyDetails.setIsUserMarketingNonMarketing("MKTG OFF to NONMKTG OFF");
	                          }
	                         historyDetails.setUserOldRoles("NA");
	                         historyDetails.setUserNewRoles("NA");
	                         historyDetails.setCreatedBy(userDetails.getUserName());
	                         historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
	                         historyDetails.setUserexpiryon(existingMasterUser.getUserexpiryon());
	                         historyDetails.setCategory(masterUsersEntity.getCategory());
	                         masterUsersHistoryDetailsRepository.save(historyDetails);
						}
						response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", "Updated Master User for: " + masterUsersEntity.getMasterUserId());	
				} /*else if(masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("UO") &&masterUsersEntity.getLocationType().equalsIgnoreCase("SO")) {
					
					if(masterUsersEntity.getLocationType().equalsIgnoreCase("SO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
						masterUserRepository.save(existingMasterUser);
						 MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUsersEntity.getLoggedInUsername());
                         MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
                         historyDetails.setAdminUserName(userDetails.getUserName());
                         historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
                         historyDetails.setAdminUserLocationType(userDetails.getLocationType());
                         historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
                         historyDetails.setUserName(masterUsersEntity.getUserName());
                         historyDetails.setUserSrnumber(masterUsersEntity.getSrNumber2());
                         historyDetails.setUserLocationType(masterUsersEntity.getLocationType());
                         historyDetails.setUserLocationCode(masterUsersEntity.getLocationCode());
                         historyDetails.setAdminUserActivityPerformed("User Edited");
                         historyDetails.setUserModule(null);
                         historyDetails.setIsUserAdmin(MasterUsersEntitynew.getIsAdmin().equalsIgnoreCase(masterUsersEntity.getIsAdmin()) ? null : masterUsersEntity.getIsAdmin());
                         historyDetails.setIsUserMarketingNonMarketing(MasterUsersEntitynew.getCategory().equalsIgnoreCase(masterUsersEntity.getCategory()) ? null : masterUsersEntity.getCategory());
                         historyDetails.setUserOldRoles(null);
                         historyDetails.setUserNewRoles(null);
                         historyDetails.setCreatedBy(userDetails.getUserName());
                         historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
                         masterUsersHistoryDetailsRepository.save(historyDetails);
						response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", "Updated Master User for: " + masterUsersEntity.getMasterUserId());
						
					}else {
						response.put(Constant.STATUS, 201);
						response.put(Constant.MESSAGE,"SO users can have ordinary access only");
					}
				}*/
				else {
					response.put(Constant.STATUS, 201);
					response.put(Constant.MESSAGE, Constant.FAILED);
					response.put(Constant.DATA, "User cannot edit the data of diffrent location type");
				}
					
					if(masterUsersEntity.getLocationType().equalsIgnoreCase("CO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
						
						//CoAdminEntity coAdmin =coAdminRepository.getCoAdminDetailByID(coAdminId);
	                	//Long masterUserId = coAdmin.getMasterUserId();
	                	//MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
	                	if (masterUsersEntity.getIsActive().equalsIgnoreCase("Y") & masterUsersEntity.getIsDeleted().equalsIgnoreCase("N") &  masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
	                	userRoleTypeRepository.DeleteAdminBymasterId(masterUsersEntity.getMasterUserId());
	                	coAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
	                	} else {
	                		userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
	                	}
					}
	                	
	                	if(masterUsersEntity.getLocationType().equalsIgnoreCase("ZO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
							
							//CoAdminEntity coAdmin =coAdminRepository.getCoAdminDetailByID(coAdminId);
		                	//Long masterUserId = coAdmin.getMasterUserId();
		                	//MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
		                	if (masterUsersEntity.getIsActive().equalsIgnoreCase("Y") & masterUsersEntity.getIsDeleted().equalsIgnoreCase("N") &  masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
		                	userRoleTypeRepository.DeleteAdminBymasterId(masterUsersEntity.getMasterUserId());
		                	zoAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
		                	} else {
		                		userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
		                	}
		                			
					}
                     if(masterUsersEntity.getLocationType().equalsIgnoreCase("UO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
							
							//CoAdminEntity coAdmin =coAdminRepository.getCoAdminDetailByID(coAdminId);
		                	//Long masterUserId = coAdmin.getMasterUserId();
		                	//MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
		                	if (masterUsersEntity.getIsActive().equalsIgnoreCase("Y") & masterUsersEntity.getIsDeleted().equalsIgnoreCase("N") &  masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
		                	userRoleTypeRepository.DeleteAdminBymasterId(masterUsersEntity.getMasterUserId());
		                	uoAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
		                	} else {
		                		userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
		                	}	
					}
                     if(masterUsersEntity.getLocationType().equalsIgnoreCase("SO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
							
							//CoAdminEntity coAdmin =coAdminRepository.getCoAdminDetailByID(coAdminId);
		                	//Long masterUserId = coAdmin.getMasterUserId();
		                	//MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
		                	if (masterUsersEntity.getIsActive().equalsIgnoreCase("Y") & masterUsersEntity.getIsDeleted().equalsIgnoreCase("N") &  masterUsersEntity.getIsAdmin().equalsIgnoreCase("N")) {
		                	userRoleTypeRepository.DeleteAdminBymasterId(masterUsersEntity.getMasterUserId());
		                	soAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
		                	} else {
		                		userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
		                	}	
					}
                     else if(masterUsersEntity.getLocationType().equalsIgnoreCase("CO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y")  && masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("CO")) {
						
						CoAdminEntity coEntity = coAdminRepository.findDuplicateByMasterUserId(masterUsersEntity.getMasterUserId());
					if(coEntity!=null) {
						
						response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", "Updated Master User for: " + masterUsersEntity.getMasterUserId());
					} else {
						CoAdminEntity coAdmin = new CoAdminEntity();
						coAdmin.setMasterUserId(masterUsersEntity.getMasterUserId());
						coAdmin.setCoAdminName(masterUsersEntity.getLocation());
						coAdmin.setLocationCode(masterUsersEntity.getLocationCode());
						coAdmin.setIsActive(masterUsersEntity.getIsActive());
						coAdmin.setIsDeleted(masterUsersEntity.getIsDeleted());
						coAdmin.setCreatedBy(masterUsersEntity.getCreatedBy());
					    coAdmin.setCreatedOn(date);
					    coAdmin.setModifiedBy(masterUsersEntity.getModifiedBy());
					    coAdmin.setModifiedOn(date);
					    CoAdminEntity CoAdmin = coAdminRepository.save(coAdmin);
					    UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
						userRoleTypeMappingEntity.setAppModuleId(1L);;
						userRoleTypeMappingEntity.setRoleTypeId(11L);
						userRoleTypeMappingEntity.setMasterUserId(coAdmin.getMasterUserId());
						userRoleTypeMappingEntity.setIsActive("Y");
						userRoleTypeMappingEntity.setIsDeleted("N");
					//	userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
						userRoleTypeMappingEntity.setModifiedBy(masterUsersEntity.getModifiedBy());
						userRoleTypeMappingEntity.setModifiedOn(date);
						userRoleTypeMappingEntity.setCreatedBy(masterUsersEntity.getCreatedBy());
						userRoleTypeMappingEntity.setCreatedOn(date);
						userRoleTypeRepository.save(userRoleTypeMappingEntity);
						
					}
					}
					else if(masterUsersEntity.getLocationType().equalsIgnoreCase("ZO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y") && (masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("ZO") || masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("CO"))) {
						
						 ZOAdminEntity zoEntity = zoAdminRepository.findDuplicateByMasterUserId(masterUsersEntity.getMasterUserId());
						 
						 if(zoEntity!=null) {
							 response.put(Constant.STATUS, 200);
								response.put(Constant.MESSAGE, Constant.SUCCESS);
								response.put("Data", "Updated Master User for: " + masterUsersEntity.getMasterUserId());
						 } else {
							 
						 
						 ZOAdminEntity zoAdmin = new ZOAdminEntity();
						
						zoAdmin.setMasterUserId(masterUsersEntity.getMasterUserId());
						zoAdmin.setLocation(masterUsersEntity.getLocation());
						zoAdmin.setLocationCode(masterUsersEntity.getLocationCode());
						zoAdmin.setIsActive(masterUsersEntity.getIsActive());
						zoAdmin.setIsDeleted(masterUsersEntity.getIsDeleted());
						zoAdmin.setCreatedBy(masterUsersEntity.getCreatedBy());
					    zoAdmin.setCreatedOn(date);
					    zoAdmin.setModifiedBy(masterUsersEntity.getModifiedBy());
					    zoAdmin.setModifiedOn(date);
					    ZOAdminEntity ZoAdmin = zoAdminRepository.save(zoAdmin);
					    UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
						userRoleTypeMappingEntity.setAppModuleId(1L);;
						userRoleTypeMappingEntity.setRoleTypeId(11L);
						userRoleTypeMappingEntity.setMasterUserId(zoAdmin.getMasterUserId());
						userRoleTypeMappingEntity.setIsActive("Y");
						userRoleTypeMappingEntity.setIsDeleted("N");
					//	userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
						userRoleTypeMappingEntity.setModifiedBy(masterUsersEntity.getModifiedBy());
						userRoleTypeMappingEntity.setModifiedOn(date);
						userRoleTypeMappingEntity.setCreatedBy(masterUsersEntity.getCreatedBy());
						userRoleTypeMappingEntity.setCreatedOn(date);
						userRoleTypeRepository.save(userRoleTypeMappingEntity);
					}
					 }
					 else  if(masterUsersEntity.getLocationType().equalsIgnoreCase("UO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y") && (masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("UO") || masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("CO"))) {
						  
						 UOAdminEntity uoEntity = uoAdminRepository.findDuplicateByMasterUserId(masterUsersEntity.getMasterUserId());
						
					if(uoEntity!=null) {
						response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", "Updated Master User for: " + masterUsersEntity.getMasterUserId());
					} else {
						  UOAdminEntity uoAdmin = new UOAdminEntity();
							uoAdmin.setLocation(masterUsersEntity.getLocation());
							uoAdmin.setLocationCode(masterUsersEntity.getLocationCode());
							uoAdmin.setLocationType(masterUsersEntity.getLocationType());
							uoAdmin.setMasterUserId(masterUsersEntity.getMasterUserId());
							uoAdmin.setIsActive(masterUsersEntity.getIsActive());
							uoAdmin.setIsDeleted(masterUsersEntity.getIsDeleted());
							uoAdmin.setModifiedOn(date);
							uoAdmin.setModifiedBy(masterUsersEntity.getModifiedBy());
							uoAdmin.setCreatedBy(masterUsersEntity.getCreatedBy());
							uoAdmin.setCreatedOn(date);
							UOAdminEntity UoAdmin = uoAdminRepository.save(uoAdmin);
							UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
							userRoleTypeMappingEntity.setAppModuleId(1L);;
							userRoleTypeMappingEntity.setRoleTypeId(11L);
							userRoleTypeMappingEntity.setMasterUserId(uoAdmin.getMasterUserId());
							userRoleTypeMappingEntity.setIsActive("Y");
							userRoleTypeMappingEntity.setIsDeleted("N");
						//	userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
							userRoleTypeMappingEntity.setModifiedBy(masterUsersEntity.getModifiedBy());
							userRoleTypeMappingEntity.setModifiedOn(date);
							userRoleTypeMappingEntity.setCreatedBy(masterUsersEntity.getCreatedBy());
							userRoleTypeMappingEntity.setCreatedOn(date);
							userRoleTypeRepository.save(userRoleTypeMappingEntity);
					  }
					  }
					  
					 else  if (masterUsersEntity.getLocationType().equalsIgnoreCase("SO") && masterUsersEntity.getIsAdmin().equalsIgnoreCase("Y") && masterUsersEntity.getLoggedInUserLoacationType().equalsIgnoreCase("SO")) {
						  
						  SOAdminEntity soEntity = soAdminRepository.findDuplicateByMasterUserId(masterUsersEntity.getMasterUserId());
						  if(soEntity!=null) {
							  response.put(Constant.STATUS, 200);
								response.put(Constant.MESSAGE, Constant.SUCCESS);
								response.put("Data", "Updated Master User for: " + masterUsersEntity.getMasterUserId());
						  } else {
						  SOAdminEntity soAdmin = new SOAdminEntity();

							soAdmin.setLocation(masterUsersEntity.getLocation());
							soAdmin.setLocationCode(masterUsersEntity.getLocationCode());
							soAdmin.setLocationType(masterUsersEntity.getLocationType());
							soAdmin.setMasterUserId(masterUsersEntity.getMasterUserId());
							soAdmin.setIsActive(masterUsersEntity.getIsActive());
							soAdmin.setIsDeleted(masterUsersEntity.getIsDeleted());
							soAdmin.setModifiedOn(date);
							soAdmin.setModifiedBy(masterUsersEntity.getModifiedBy());
							soAdmin.setCreatedBy(masterUsersEntity.getCreatedBy());
							soAdmin.setCreatedOn(date);
							 SOAdminEntity SoAdmin = soAdminRepository.save(soAdmin);
							UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
							userRoleTypeMappingEntity.setAppModuleId(1L);;
							userRoleTypeMappingEntity.setRoleTypeId(11L);
							userRoleTypeMappingEntity.setMasterUserId(soAdmin.getMasterUserId());
							userRoleTypeMappingEntity.setIsActive("Y");
							userRoleTypeMappingEntity.setIsDeleted("N");
						//	userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
							userRoleTypeMappingEntity.setModifiedBy(masterUsersEntity.getModifiedBy());
							userRoleTypeMappingEntity.setModifiedOn(date);
							userRoleTypeMappingEntity.setCreatedBy(masterUsersEntity.getCreatedBy());
							userRoleTypeMappingEntity.setCreatedOn(date);
							userRoleTypeRepository.save(userRoleTypeMappingEntity);
					  }
					  }

					
				}
				
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
		
			
			}catch (Exception ex) {
			ex.printStackTrace();
			logger.info("Could not update Master User  due to : " + ex.getMessage());
		}
		return null;
	}

	/*
	 * Description: This function is used for soft deleting the data in MASTER USERS Module by using primary key
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@Override
	public ResponseEntity<Map<String, Object>> deleteUser(Long masterUserId) throws Exception{
		logger.info("Enter UserManagementService : deleteUser");
		try {

			Map<String, Object> response = new HashMap<String, Object>();
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

			if (masterUserId == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} else {
				if(!isValidDeletion(masterUserId)) {
					response.put(Constant.STATUS, 10);
					response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
				}

				else {
					masterUserRepository.findDeletedById(masterUserId);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Deleted Master User Id : " + masterUserId );
				}
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception ex) {
			logger.info("Could not delete user due to : " + ex.getMessage());
			return null;
		}
	}

	private boolean isValidDeletion(Long id) {
		Optional<MasterUsersEntity> result = masterUserRepository.findById(id);
		if(!result.isPresent()) {
			return false;
		}

		if(result.get().getIsDeleted()!= null && result.get().getIsDeleted().equals("Y")) {
			return false;
		}
		if(result.get().getIsActive()!= null && result.get().getIsActive().equals("N")) {
			return false;
		}

		else { return true;  }
	}

	/*
	 * Description: This function is used for getting data from MASTER USERS Module by using srnumber for identifying the admin presence
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@Override
	public List<COModel> getAllAdminOfficeDetails(String userName) throws Exception {
		ResultSet rs = null;
		PreparedStatement preparestatements = null;
		String sql = null;
		Connection connection = jdbcConnection.getConnection();

		try {
			
			MasterUsersEntity user = masterUserRepository.findUserByUserName(userName);
			if(user != null) {
				if(user.getLocationType().equals("CO")) {
					sql = "SELECT DISTINCT CO.DESCRIPTION , ZO.ZONAL_ID , ZO.ZONAL_CODE ,ZO.DESCRIPTION,\r\n" + 
							"UO.UNIT_ID,UO.UNIT_CODE , UO.CITY_NAME, UO.DESCRIPTION , MS.SATELLITE_ID, MS.SATELLITE_CODE FROM MASTER_CO_OFFICE CO, MASTER_ZONAL ZO ,\r\n" + 
							"MASTER_UNIT UO , MASTER_SATELLITE MS, MASTER_USERS MU\r\n" + 
							"WHERE CO.CO_ID = ZO.CO_ID AND ZO.ZONAL_ID = UO.ZONAL_ID AND UO.UNIT_ID = MS.UNIT_ID AND\r\n" + 
							"MU.USERNAME =?";

					preparestatements = connection.prepareStatement(sql);
					preparestatements.setString(1, userName);

					rs = preparestatements.executeQuery();
					List<COModel> modelDetails = new ArrayList<>();

					while (rs.next()) {
						COModel model = new COModel();
						model.setCoDesc(rs.getString(1));
						model.setZonalId(rs.getLong(2));
						model.setZonalCode(rs.getString(3));
						model.setZonalDesc(rs.getString(4));
						model.setUnitId(rs.getLong(5));
						model.setUnitCode(rs.getString(6));
						model.setUnitCityName(rs.getString(7));
						model.setUnitDesc(rs.getString(8));
						model.setSatelliteId(rs.getLong(9));
						model.setSatelliteCode(rs.getString(10));
						modelDetails.add(model);
					}
					return modelDetails;

				}
				if(user.getLocationType().equals("ZO")) {

					sql = "SELECT DISTINCT ZO.ZONAL_ID , ZO.ZONAL_CODE ,ZO.DESCRIPTION,\r\n" + 
							"UO.UNIT_ID,UO.UNIT_CODE, UO.CITY_NAME, UO.DESCRIPTION , MS.SATELLITE_ID, MS.SATELLITE_CODE FROM MASTER_ZONAL ZO ,\r\n" + 
							"MASTER_UNIT UO , MASTER_SATELLITE MS, MASTER_USERS MU\r\n" + 
							"WHERE ZO.ZONAL_ID = UO.ZONAL_ID AND UO.UNIT_ID = MS.UNIT_ID AND\r\n" + 
							"MU.USERNAME =?";

					preparestatements = connection.prepareStatement(sql);
					preparestatements.setString(1, userName);
					rs = preparestatements.executeQuery();

					List<COModel> modelDetails = new ArrayList<>();

					while (rs.next()) {
						COModel model = new COModel();
						model.setZonalId(rs.getLong(1));
						model.setZonalCode(rs.getString(2));
						model.setZonalDesc(rs.getString(3));
						model.setUnitId(rs.getLong(4));
						model.setUnitCode(rs.getString(5));
						model.setUnitCityName(rs.getString(6));
						model.setUnitDesc(rs.getString(7));
						model.setSatelliteId(rs.getLong(8));
						model.setSatelliteCode(rs.getString(9));
						modelDetails.add(model);

					}
					return modelDetails;
				}
				if(user.getLocationType().equals("UO")) {
					sql = "SELECT UO.UNIT_ID,UO.UNIT_CODE , UO.CITY_NAME, UO.DESCRIPTION , MS.SATELLITE_ID, MS.SATELLITE_CODE FROM " + 
							"MASTER_UNIT UO , MASTER_SATELLITE MS, MASTER_USERS MU " + 
							"WHERE MU.location_code=UO.UNIT_CODE and UO.UNIT_ID = MS.UNIT_ID and " + 
							"MU.USERNAME =?";

					preparestatements = connection.prepareStatement(sql);
					preparestatements.setString(1, userName);
					rs = preparestatements.executeQuery();

					List<COModel> modelDetails = new ArrayList<>();

					while (rs.next()) {
						COModel model = new COModel();

						model.setUnitId(rs.getLong(1));
						model.setUnitCode(rs.getString(2));
						model.setUnitCityName(rs.getString(3));
						model.setUnitDesc(rs.getString(4));
						model.setSatelliteId(rs.getLong(5));
						model.setSatelliteCode(rs.getString(6));
						modelDetails.add(model);

					}
					return modelDetails;
				}
				/*if(user.getLocationType().equals("SO") || user.getLocationType().equals("Satelite")) {
					sql = "SELECT MS.* FROM MASTER_SATELLITE MS, MASTER_USERS MU\n"
							+" WHERE UO.UNIT_ID = MS.UNIT_ID AND MU.SRNUMBER =: srNumber";
				}*/
			}

		} 
		catch (Exception e) {
			logger.info("Exception : " + e.getMessage());
		} finally {
			if (rs!= null && !rs.isClosed()) {
				rs.close();
			}
			if (preparestatements != null) {
				preparestatements.close();
			}
			if(!connection.isClosed()) {
				connection.close();
			}
			preparestatements = null;
		}

		return null;
	}
	
	 @Override
     public  ResponseEntity<Map<String, Object>> assignRolesToUser(ArrayList<AssignRolesModel> assignRolesModel,String remarks) throws Exception {
     
                     logger.info("Enter UserRoleTypeMapping : add userRoleType ");
                     Map<String, Object> response = new HashMap<String, Object>();
                     response.put(Constant.STATUS, 0);
                     response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
                     String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
                     Map<Long,Object> oldRolesMap = new HashMap<Long, Object>(); 
                     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
     				LocalDateTime now = LocalDateTime.now(); 

                     try {
                                     Date date = new Date();
                                     if (assignRolesModel == null) {
                                                     response.put(Constant.STATUS, 0);
                                                     response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
                                     } 
                                     else {
                                    	 MasterUsersEntity masterUserEntity =  masterUserRepository.getAllMasterUserDetail(assignRolesModel.get(0).getSrNumber());
                                    	 MasterUsersEntity loggedInUserDetails = masterUserRepository.getAllMasterUserDetailByUserName(assignRolesModel.get(0).getLoggedInUsername());
                                    	 //Getting existing roles for the user
                                    //	 if(masterUserEntity.getLocationType().equalsIgnoreCase("CO") && loggedInUserDetails.getLocationType().equalsIgnoreCase("CO")) {
                                    	 if(loggedInUserDetails.getLocationType().equalsIgnoreCase("CO") && (masterUserEntity.getLocationType().equalsIgnoreCase("CO") || masterUserEntity.getLocationType().equalsIgnoreCase("ZO") || masterUserEntity.getLocationType().equalsIgnoreCase("UO"))) {	
                                    	 List<UserRoleTypeMappingEntity> mappingEntities = userRoleTypeRepository.getUserRoleTypeByRoleMapId(masterUserEntity.getMasterUserId());
                                    	 List<Long> oldRoleTypes = new ArrayList();
                                    	 Long appmoduleId = 0l;
                                    	 for(int i=0;i<mappingEntities.size();i++) 
                                    	 {
                                    		 UserRoleTypeMappingEntity mappingEntity = mappingEntities.get(i);
                                    		 
                                    		 if(mappingEntity.getAppModuleId() == appmoduleId || i==0) {
                                    			 oldRoleTypes.add(mappingEntity.getRoleTypeId());
                                    			 appmoduleId =  mappingEntity.getAppModuleId(); 
                                    		 }else {                                    			 
                                    			 oldRolesMap.put(appmoduleId, new ArrayList<>(oldRoleTypes));
                                    			 oldRoleTypes.clear();
                                    			 oldRoleTypes.add(mappingEntity.getRoleTypeId());
                                    			 appmoduleId =  mappingEntity.getAppModuleId(); 
                                    		 } 
                                    		 if(i==mappingEntities.size()-1) {
                                    			 oldRolesMap.put(appmoduleId, new ArrayList<>(oldRoleTypes)); 
                                    		 }
                                    		 
                                    	 }
                                    	//Getting existing roles for the user
                                    	 
                                                     UserRoleTypeMappingEntity commonAdmin = userRoleTypeRepository.findAdminBymasterId(masterUserEntity.getMasterUserId());
                                                     if(commonAdmin!=null) {
                                                                     
                                                                     userRoleTypeRepository.DeleteBymasterId(masterUserEntity.getMasterUserId());
                                                                     UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
                                                                                     userRoleTypeMappingEntity.setAppModuleId(1L);;
                                                                                     userRoleTypeMappingEntity.setRoleTypeId(11L);
                                                                                     userRoleTypeMappingEntity.setMasterUserId(masterUserEntity.getMasterUserId());
                                                                                     userRoleTypeMappingEntity.setIsActive("Y");
                                                                                     userRoleTypeMappingEntity.setIsDeleted("N");
                                                                     //     userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
                                                                                     userRoleTypeMappingEntity.setModifiedBy(loggedInUserDetails.getUserName());
                                                                                     userRoleTypeMappingEntity.setModifiedOn(date);
                                                                                     userRoleTypeMappingEntity.setCreatedBy(loggedInUserDetails.getUserName());
                                                                                     userRoleTypeMappingEntity.setCreatedOn(date);
                                                                                     
                                                                                     userRoleTypeRepository.save(userRoleTypeMappingEntity);
                                                                     //userRoleTypeRepository.updateAdminBymasterId(masterEntity.getMasterUserId());
                                                                     
                                                     }else {
                                                                     userRoleTypeRepository.DeleteBymasterId(masterUserEntity.getMasterUserId());
                                                     }
                                                     
                                                     List<AssignRolesModel> assignRolesModelNew = new ArrayList();
                                                
                                                     for(AssignRolesModel obj: assignRolesModel ) {
                                                    	 
                                                                     Long appModuleId = obj.getAppModuleId();
                                                                     if(appModuleId==2) {
                                                                                     AssignRolesModel model = new AssignRolesModel();
                                                                                     List<String> displayRoleTypes = obj.getDisplayRoleType();
                                                                                     List<String> displayRoleTypesNew = new ArrayList();
                                                                                     for(String displayRoleType:displayRoleTypes) {
                                                                                                     if(displayRoleType.equalsIgnoreCase("Checker")) {
                                                                                                                     displayRoleTypesNew.add("Checker");
                                                                                                                     model.setAppModuleId(6l);
                                                                                                                     model.setAppModuleName("CLAIM");  
                                                                                                                     model.setSrNumber(obj.getSrNumber());
                                                                                                                     model.setLoggedInUsername(obj.getLoggedInUsername());
                                                                                                     }else if(displayRoleType.equalsIgnoreCase("Maker")) {
                                                                                                                     displayRoleTypesNew.add("Maker");
                                                                                                                     model.setAppModuleId(6l);
                                                                                                                     model.setAppModuleName("CLAIM");
                                                                                                                     model.setSrNumber(obj.getSrNumber());
                                                                                                                     model.setLoggedInUsername(obj.getLoggedInUsername());
                                                                                                     }
                                                                                     }
                                                                                     model.setDisplayRoleType(displayRoleTypesNew);
                                                                                     assignRolesModelNew.add(model);
                                                                     }

                                                     }


                                                     for(int i=0;i<assignRolesModelNew.size();i++) {
                                                         assignRolesModel.add(assignRolesModelNew.get(i)) ;
                                                     }
                                                     
                                                     
                                                     
                                                     for(AssignRolesModel obj: assignRolesModel ) {                                                                  
                                                                     String srNumber = obj.getSrNumber();
                                                                     Long appModuleId = obj.getAppModuleId();
                                                                     String oldRoleNames = null;
                                                                     String moduleName = "";
                                                                     MasterUsersEntity masterEntity = null;
                                                                     MasterUsersEntity masterEntityAdmin = null;
                                                                     char[] first= null;
                                                                     char[] second= null;
                                                                     
                                                                   //getting diaplay role names based on old role type ids 
                                                                     List<Long> oldRoles = (List<Long>)oldRolesMap.get(appModuleId);
                                                                     List<String> oldDisplayRoleNames  = new ArrayList();  
                                                                	 if(oldRoles != null && oldRoles.size() > 0) {
	                                                                     for(Long str : oldRoles) {
	                                                                    	 MasterRolesDisplayRolesMappingEntity rolesMappingEntity = masterRolesDisplayRolesMappingRepository.getDisplayRoleNameBasedOnRoleType(str);
	                                                                    	 oldDisplayRoleNames.add(rolesMappingEntity.getDisplayRoleTypeName());
	                                                                    	 moduleName = rolesMappingEntity.getAppModuleName();
	                                                                     }
	                                                                      oldRoleNames = String.join(",", oldDisplayRoleNames);
	                                                                      first = oldRoleNames.toCharArray();
	                                                                      Arrays.sort(first); 
                                                                     }
                                                                	  //getting diaplay role names based on old role type ids
                                                                	 masterEntity =  masterUserRepository.getAllMasterUserDetail(srNumber);
                                                                     masterEntityAdmin =  masterUserRepository.getAllMasterUserDetailByUserName(obj.getLoggedInUsername());
                                                                     
                                                                     List<String> displayRoleTypeNames = new ArrayList();
                                                                     displayRoleTypeNames = obj.getDisplayRoleType();
                                                                     if(displayRoleTypeNames != null && displayRoleTypeNames.size() > 0) {     
                                                                                     for(String displayRoleTypeName : displayRoleTypeNames) {                                                                                                     
                                                                                                     MasterRolesDisplayRolesMappingEntity  displayRoles = masterRolesDisplayRolesMappingRepository.getMasterRoleTypeDetails(appModuleId, displayRoleTypeName, masterEntity.getLocationType());
                                                                                                     if (displayRoles!=null) {
                                                                                                       UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
                                                                                                                     userRoleTypeMappingEntity.setAppModuleId(displayRoles.getAppModuleId());;
                                                                                                                     userRoleTypeMappingEntity.setRoleTypeId(displayRoles.getRoleTypeId());
                                                                                                                     userRoleTypeMappingEntity.setMasterUserId(masterEntity.getMasterUserId());
                                                                                                                     userRoleTypeMappingEntity.setIsActive("Y");
                                                                                                                     userRoleTypeMappingEntity.setIsDeleted("N");
                                                                                                     //     userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
                                                                                                                     userRoleTypeMappingEntity.setModifiedBy(loggedInUserDetails.getUserName());
                                                                                                                     userRoleTypeMappingEntity.setModifiedOn(date);
                                                                                                                     userRoleTypeMappingEntity.setCreatedBy(loggedInUserDetails.getUserName());
                                                                                                                     userRoleTypeMappingEntity.setCreatedOn(date);
                                                                                                                     userRoleTypeRepository.save(userRoleTypeMappingEntity);
                                                                                                                   
                                                                                                     }
                                                                                     }
                                                                                     
                                                                                     if(!moduleRepository.findModuleNameById(appModuleId).equalsIgnoreCase("CLAIM"))
                                                                                     {
                                                                                    	 String newRoleNames = String.join(",", obj.getDisplayRoleType());
                                                                                    	 second = newRoleNames.toCharArray();
                                                                                    	 Arrays.sort(second);
                                                                                    	 if((moduleName != null && !moduleName.equalsIgnoreCase(moduleRepository.findModuleNameById(appModuleId))) ||
                                                                                        			(moduleName != null && moduleName.equalsIgnoreCase(moduleRepository.findModuleNameById(appModuleId)) && !Arrays.equals(first, second))) {
	                                                                                    	MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
	                                                                                    	historyDetails.setAdminUserName(masterEntityAdmin.getUserName());
	                                                                                    	historyDetails.setAdminUserSrnumber(masterEntityAdmin.getSrNumber2());
	                                                                                    	historyDetails.setAdminUserLocationType(masterEntityAdmin.getLocationType());
	                                                                                    	historyDetails.setAdminUserLocationCode(masterEntityAdmin.getLocationCode());
	                                                                                    	historyDetails.setUserName(masterEntity.getUserName());
	                                                                                     	historyDetails.setUserSrnumber(masterEntity.getSrNumber2());
	                                                                                     	historyDetails.setUserLocationType(masterEntity.getLocationType());
	                                                                                     	historyDetails.setUserLocationCode(masterEntity.getLocationCode());
	                                                                                     	historyDetails.setRemarks(remarks);
	                                                                                     	if(oldRoleNames == null) {
	                                                                                     		historyDetails.setAdminUserActivityPerformed("Roles Assigned");
	                                                                                     		historyDetails.setUserOldRoles("NA");
	                                                                                     	}else {
	                                                                                     		historyDetails.setAdminUserActivityPerformed("Roles Modified");
	                                                                                     		historyDetails.setUserOldRoles(oldRoleNames);
	                                                                                     	}
	                                                                                    	historyDetails.setUserModule(moduleRepository.findModuleNameById(appModuleId));
	                                                                                    	historyDetails.setIsUserAdmin("NA");
	                                                                                    	historyDetails.setIsUserMarketingNonMarketing("NA");	                                                                                    	
	                                                                                    	historyDetails.setUserNewRoles(String.join(",", obj.getDisplayRoleType()));
	                                                                                    	historyDetails.setCreatedBy(loggedInUserDetails.getUserName());
	                                                                                    	historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
	                                                                                    	masterUsersHistoryDetailsRepository.save(historyDetails);
                                                                                    	 }
                                                                                     }
                                                                     }else if(oldRoleNames != null) {
                                                                    	 if(!moduleRepository.findModuleNameById(appModuleId).equalsIgnoreCase("CLAIM"))
                                                                         {
                                                                    		 String newRoleNames = String.join(",", obj.getDisplayRoleType());
                                                                        	 second = newRoleNames.toCharArray();
                                                                        	 Arrays.sort(second);
                                                                        	 if((moduleName != null && !moduleName.equalsIgnoreCase(moduleRepository.findModuleNameById(appModuleId))) ||
                                                                            			(moduleName != null && moduleName.equalsIgnoreCase(moduleRepository.findModuleNameById(appModuleId)) && !Arrays.equals(first, second))) {
	                                                                        	MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
	                                                                        	historyDetails.setAdminUserName(masterEntityAdmin.getUserName());
	                                                                        	historyDetails.setAdminUserSrnumber(masterEntityAdmin.getSrNumber2());
	                                                                        	historyDetails.setAdminUserLocationType(masterEntityAdmin.getLocationType());
	                                                                        	historyDetails.setAdminUserLocationCode(masterEntityAdmin.getLocationCode());
	                                                                        	historyDetails.setUserName(masterEntity.getUserName());
	                                                                         	historyDetails.setUserSrnumber(masterEntity.getSrNumber2());
	                                                                         	historyDetails.setUserLocationType(masterEntity.getLocationType());
	                                                                         	historyDetails.setUserLocationCode(masterEntity.getLocationCode());
	                                                                         	historyDetails.setRemarks(remarks);
	                                                                        	historyDetails.setAdminUserActivityPerformed("Roles Modified");
	                                                                        	historyDetails.setUserModule(moduleRepository.findModuleNameById(appModuleId));
	                                                                        	historyDetails.setIsUserAdmin("NA");
	                                                                        	historyDetails.setIsUserMarketingNonMarketing("NA");
	                                                                        	historyDetails.setUserOldRoles(oldRoleNames);
	                                                                        	historyDetails.setUserNewRoles(obj.getDisplayRoleType().size() > 0 ? String.join(",", obj.getDisplayRoleType()):null);
	                                                                        	historyDetails.setCreatedBy(loggedInUserDetails.getUserName());
	                                                                        	historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
	                                                                        	masterUsersHistoryDetailsRepository.save(historyDetails);
                                                                    		 }
                                                                         } 
                                                                     }
                                                     }
                                                     response.put(Constant.STATUS, 200);
                                                     response.put(Constant.MESSAGE, Constant.SUCCESS);
                                                     response.put("Data", "Roles Assigned to user sucessfully " );
                                     }
                                    	 else if((masterUserEntity.getLocationType().equalsIgnoreCase("ZO") || masterUserEntity.getLocationType().equalsIgnoreCase("UO")) && loggedInUserDetails.getLocationType().equalsIgnoreCase("ZO")) {
                                    		
                                        	 List<UserRoleTypeMappingEntity> mappingEntities = userRoleTypeRepository.getUserRoleTypeByRoleMapId(masterUserEntity.getMasterUserId());
                                        	 List<Long> oldRoleTypes = new ArrayList();
                                        	 Long appmoduleId = 0l;
                                        	 for(int i=0;i<mappingEntities.size();i++) 
                                        	 {
                                        		 UserRoleTypeMappingEntity mappingEntity = mappingEntities.get(i);
                                        		 
                                        		 if(mappingEntity.getAppModuleId() == appmoduleId || i==0) {
                                        			 oldRoleTypes.add(mappingEntity.getRoleTypeId());
                                        			 appmoduleId =  mappingEntity.getAppModuleId(); 
                                        		 }else {                                    			 
                                        			 oldRolesMap.put(appmoduleId, new ArrayList<>(oldRoleTypes));
                                        			 oldRoleTypes.clear();
                                        			 oldRoleTypes.add(mappingEntity.getRoleTypeId());
                                        			 appmoduleId =  mappingEntity.getAppModuleId(); 
                                        		 } 
                                        		 if(i==mappingEntities.size()-1) {
                                        			 oldRolesMap.put(appmoduleId, new ArrayList<>(oldRoleTypes)); 
                                        		 }
                                        		 
                                        	 }
                                        	//Getting existing roles for the user
                                        	 
                                                         UserRoleTypeMappingEntity commonAdmin = userRoleTypeRepository.findAdminBymasterId(masterUserEntity.getMasterUserId());
                                                         if(commonAdmin!=null) {
                                                                         
                                                                         userRoleTypeRepository.DeleteBymasterId(masterUserEntity.getMasterUserId());
                                                                         UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
                                                                                         userRoleTypeMappingEntity.setAppModuleId(1L);;
                                                                                         userRoleTypeMappingEntity.setRoleTypeId(11L);
                                                                                         userRoleTypeMappingEntity.setMasterUserId(masterUserEntity.getMasterUserId());
                                                                                         userRoleTypeMappingEntity.setIsActive("Y");
                                                                                         userRoleTypeMappingEntity.setIsDeleted("N");
                                                                         //     userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
                                                                                         userRoleTypeMappingEntity.setModifiedBy(loggedInUserDetails.getUserName());
                                                                                         userRoleTypeMappingEntity.setModifiedOn(date);
                                                                                         userRoleTypeMappingEntity.setCreatedBy(loggedInUserDetails.getUserName());
                                                                                         userRoleTypeMappingEntity.setCreatedOn(date);
                                                                                         userRoleTypeRepository.save(userRoleTypeMappingEntity);
                                                                         //userRoleTypeRepository.updateAdminBymasterId(masterEntity.getMasterUserId());
                                                                         
                                                         }else {
                                                                         userRoleTypeRepository.DeleteBymasterId(masterUserEntity.getMasterUserId());
                                                         }
                                                         
                                                         List<AssignRolesModel> assignRolesModelNew = new ArrayList();
                                                    
                                                         for(AssignRolesModel obj: assignRolesModel ) {
                                                        	 
                                                                         Long appModuleId = obj.getAppModuleId();
                                                                         if(appModuleId==2) {
                                                                                         AssignRolesModel model = new AssignRolesModel();
                                                                                         List<String> displayRoleTypes = obj.getDisplayRoleType();
                                                                                         List<String> displayRoleTypesNew = new ArrayList();
                                                                                         for(String displayRoleType:displayRoleTypes) {
                                                                                                         if(displayRoleType.equalsIgnoreCase("Checker")) {
                                                                                                                         displayRoleTypesNew.add("Checker");
                                                                                                                         model.setAppModuleId(5l);
                                                                                                                         model.setAppModuleName("CLAIM");  
                                                                                                                         model.setSrNumber(obj.getSrNumber());
                                                                                                                         model.setLoggedInUsername(obj.getLoggedInUsername());
                                                                                                         }else if(displayRoleType.equalsIgnoreCase("Maker")) {
                                                                                                                         displayRoleTypesNew.add("Maker");
                                                                                                                         model.setAppModuleId(5l);
                                                                                                                         model.setAppModuleName("CLAIM");
                                                                                                                         model.setSrNumber(obj.getSrNumber());
                                                                                                                         model.setLoggedInUsername(obj.getLoggedInUsername());
                                                                                                         }
                                                                                         }
                                                                                         model.setDisplayRoleType(displayRoleTypesNew);
                                                                                         assignRolesModelNew.add(model);
                                                                         }

                                                         }


                                                         for(int i=0;i<assignRolesModelNew.size();i++) {
                                                             assignRolesModel.add(assignRolesModelNew.get(i)) ;
                                                         }
                                                         
                                                         
                                                         
                                                         for(AssignRolesModel obj: assignRolesModel ) {                                                                  
                                                                         String srNumber = obj.getSrNumber();
                                                                         Long appModuleId = obj.getAppModuleId();
                                                                         String oldRoleNames = null;
                                                                         String moduleName = "";
                                                                         MasterUsersEntity masterEntity = null;
                                                                         MasterUsersEntity masterEntityAdmin = null;
                                                                         char[] first= null;
                                                                         char[] second= null;
                                                                         
                                                                       //getting diaplay role names based on old role type ids 
                                                                         List<Long> oldRoles = (List<Long>)oldRolesMap.get(appModuleId);
                                                                         List<String> oldDisplayRoleNames  = new ArrayList();  
                                                                    	 if(oldRoles != null && oldRoles.size() > 0) {
    	                                                                     for(Long str : oldRoles) {
    	                                                                    	 MasterRolesDisplayRolesMappingEntity rolesMappingEntity = masterRolesDisplayRolesMappingRepository.getDisplayRoleNameBasedOnRoleType(str);
    	                                                                    	 oldDisplayRoleNames.add(rolesMappingEntity.getDisplayRoleTypeName());
    	                                                                    	 moduleName = rolesMappingEntity.getAppModuleName();
    	                                                                     }
    	                                                                      oldRoleNames = String.join(",", oldDisplayRoleNames);
    	                                                                      first = oldRoleNames.toCharArray();
    	                                                                      Arrays.sort(first);
                                                                         }
                                                                    	  //getting diaplay role names based on old role type ids
                                                                    	 masterEntity =  masterUserRepository.getAllMasterUserDetail(srNumber);
                                                                         masterEntityAdmin =  masterUserRepository.getAllMasterUserDetailByUserName(obj.getLoggedInUsername());
                                                                         
                                                                         List<String> displayRoleTypeNames = new ArrayList();
                                                                         displayRoleTypeNames = obj.getDisplayRoleType();
                                                                         if(displayRoleTypeNames != null && displayRoleTypeNames.size() > 0) {     
                                                                                         for(String displayRoleTypeName : displayRoleTypeNames) {
                                                                                                         MasterRolesDisplayRolesMappingEntity  displayRoles = masterRolesDisplayRolesMappingRepository.getMasterRoleTypeDetails(appModuleId, displayRoleTypeName, masterEntity.getLocationType());
                                                                                                         if (displayRoles!=null) {
                                                                                                           UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
                                                                                                                         userRoleTypeMappingEntity.setAppModuleId(displayRoles.getAppModuleId());;
                                                                                                                         userRoleTypeMappingEntity.setRoleTypeId(displayRoles.getRoleTypeId());
                                                                                                                         userRoleTypeMappingEntity.setMasterUserId(masterEntity.getMasterUserId());
                                                                                                                         userRoleTypeMappingEntity.setIsActive("Y");
                                                                                                                         userRoleTypeMappingEntity.setIsDeleted("N");
                                                                                                         //     userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
                                                                                                                         userRoleTypeMappingEntity.setModifiedBy(loggedInUserDetails.getUserName());
                                                                                                                         userRoleTypeMappingEntity.setModifiedOn(date);
                                                                                                                         userRoleTypeMappingEntity.setCreatedBy(loggedInUserDetails.getUserName());
                                                                                                                         userRoleTypeMappingEntity.setCreatedOn(date);
                                                                                                                         userRoleTypeRepository.save(userRoleTypeMappingEntity);
                                                                                                                       
                                                                                                         }
                                                                                         }
                                                                                         
                                                                                         if(!moduleRepository.findModuleNameById(appModuleId).equalsIgnoreCase("CLAIM"))
                                                                                         {
                                                                                        	 String newRoleNames = String.join(",", obj.getDisplayRoleType());
                                                                                        	 second = newRoleNames.toCharArray();
                                                                                        	 Arrays.sort(second);
                                                                                        	 if((moduleName != null && !moduleName.equalsIgnoreCase(moduleRepository.findModuleNameById(appModuleId))) ||
                                                                                            			(moduleName != null && moduleName.equalsIgnoreCase(moduleRepository.findModuleNameById(appModuleId)) && !Arrays.equals(first, second))) {
	                                                                                        	MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
	                                                                                        	historyDetails.setAdminUserName(masterEntityAdmin.getUserName());
	                                                                                        	historyDetails.setAdminUserSrnumber(masterEntityAdmin.getSrNumber2());
	                                                                                        	historyDetails.setAdminUserLocationType(masterEntityAdmin.getLocationType());
	                                                                                        	historyDetails.setAdminUserLocationCode(masterEntityAdmin.getLocationCode());
	                                                                                        	historyDetails.setUserName(masterEntity.getUserName());
	                                                                                         	historyDetails.setUserSrnumber(masterEntity.getSrNumber2());
	                                                                                         	historyDetails.setUserLocationType(masterEntity.getLocationType());
	                                                                                         	historyDetails.setUserLocationCode(masterEntity.getLocationCode());
	                                                                                         	historyDetails.setRemarks(remarks);
	                                                                                         	if(oldRoleNames == null) {
	                                                                                         		historyDetails.setAdminUserActivityPerformed("Roles Assigned");
	                                                                                         		historyDetails.setUserOldRoles("NA");
	                                                                                         	}else {
	                                                                                         		historyDetails.setAdminUserActivityPerformed("Roles Modified");
	                                                                                         		historyDetails.setUserOldRoles(oldRoleNames);
	                                                                                         	}
	                                                                                        	historyDetails.setUserModule(moduleRepository.findModuleNameById(appModuleId));
	                                                                                        	historyDetails.setIsUserAdmin("NA");
	                                                                                        	historyDetails.setIsUserMarketingNonMarketing("NA");
	                                                                                        	historyDetails.setUserNewRoles(String.join(",", obj.getDisplayRoleType()));
	                                                                                        	historyDetails.setCreatedBy(loggedInUserDetails.getUserName());
	                                                                                        	historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
	                                                                                        	masterUsersHistoryDetailsRepository.save(historyDetails);
                                                                                        	 }
                                                                                         }
                                                                         }else if(oldRoleNames != null) {
                                                                        	 if(!moduleRepository.findModuleNameById(appModuleId).equalsIgnoreCase("CLAIM"))
                                                                             {
                                                                        		 String newRoleNames = String.join(",", obj.getDisplayRoleType());
                                                                            	 second = newRoleNames.toCharArray();
                                                                            	 Arrays.sort(second);
                                                                            	 if((moduleName != null && !moduleName.equalsIgnoreCase(moduleRepository.findModuleNameById(appModuleId))) ||
                                                                                			(moduleName != null && moduleName.equalsIgnoreCase(moduleRepository.findModuleNameById(appModuleId)) && !Arrays.equals(first, second))) {
	                                                                            	MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
	                                                                            	historyDetails.setAdminUserName(masterEntityAdmin.getUserName());
	                                                                            	historyDetails.setAdminUserSrnumber(masterEntityAdmin.getSrNumber2());
	                                                                            	historyDetails.setAdminUserLocationType(masterEntityAdmin.getLocationType());
	                                                                            	historyDetails.setAdminUserLocationCode(masterEntityAdmin.getLocationCode());
	                                                                            	historyDetails.setUserName(masterEntity.getUserName());
	                                                                             	historyDetails.setUserSrnumber(masterEntity.getSrNumber2());
	                                                                             	historyDetails.setUserLocationType(masterEntity.getLocationType());
	                                                                             	historyDetails.setUserLocationCode(masterEntity.getLocationCode());
	                                                                             	historyDetails.setRemarks(remarks);
	                                                                            	historyDetails.setAdminUserActivityPerformed("Roles Modified");
	                                                                            	historyDetails.setUserModule(moduleRepository.findModuleNameById(appModuleId));
	                                                                            	historyDetails.setIsUserAdmin("NA");
	                                                                            	historyDetails.setIsUserMarketingNonMarketing("NA");
	                                                                            	historyDetails.setUserOldRoles(oldRoleNames);
	                                                                            	historyDetails.setUserNewRoles(obj.getDisplayRoleType().size() > 0 ? String.join(",", obj.getDisplayRoleType()):null);
	                                                                            	historyDetails.setCreatedBy(loggedInUserDetails.getUserName());
	                                                                            	historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
	                                                                            	masterUsersHistoryDetailsRepository.save(historyDetails);
                                                                        		 }
                                                                             } 
                                                                         }
                                                         }
                                                         response.put(Constant.STATUS, 200);
                                                         response.put(Constant.MESSAGE, Constant.SUCCESS);
                                                         response.put("Data", "Roles Assigned to user sucessfully " );
                                         }
                                    	 else if(masterUserEntity.getLocationType().equalsIgnoreCase("UO") && loggedInUserDetails.getLocationType().equalsIgnoreCase("UO")) {
                                        	 List<UserRoleTypeMappingEntity> mappingEntities = userRoleTypeRepository.getUserRoleTypeByRoleMapId(masterUserEntity.getMasterUserId());
                                        	 List<Long> oldRoleTypes = new ArrayList();
                                        	 Long appmoduleId = 0l;
                                        	 for(int i=0;i<mappingEntities.size();i++) 
                                        	 {
                                        		 UserRoleTypeMappingEntity mappingEntity = mappingEntities.get(i);
                                        		 
                                        		 if(mappingEntity.getAppModuleId() == appmoduleId || i==0) {
                                        			 oldRoleTypes.add(mappingEntity.getRoleTypeId());
                                        			 appmoduleId =  mappingEntity.getAppModuleId(); 
                                        		 }else {                                    			 
                                        			 oldRolesMap.put(appmoduleId, new ArrayList<>(oldRoleTypes));
                                        			 oldRoleTypes.clear();
                                        			 oldRoleTypes.add(mappingEntity.getRoleTypeId());
                                        			 appmoduleId =  mappingEntity.getAppModuleId(); 
                                        		 } 
                                        		 if(i==mappingEntities.size()-1) {
                                        			 oldRolesMap.put(appmoduleId, new ArrayList<>(oldRoleTypes)); 
                                        		 }
                                        		 
                                        	 }
                                        	//Getting existing roles for the user
                                        	 
                                                           UserRoleTypeMappingEntity commonAdmin = userRoleTypeRepository.findAdminBymasterId(masterUserEntity.getMasterUserId());
                                                         if(commonAdmin!=null) {
                                                                         
                                                                         userRoleTypeRepository.DeleteBymasterId(masterUserEntity.getMasterUserId());
                                                                         UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
                                                                                         userRoleTypeMappingEntity.setAppModuleId(1L);;
                                                                                         userRoleTypeMappingEntity.setRoleTypeId(11L);
                                                                                         userRoleTypeMappingEntity.setMasterUserId(masterUserEntity.getMasterUserId());
                                                                                         userRoleTypeMappingEntity.setIsActive("Y");
                                                                                         userRoleTypeMappingEntity.setIsDeleted("N");
                                                                         //     userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
                                                                                         userRoleTypeMappingEntity.setModifiedBy(loggedInUserDetails.getUserName());
                                                                                         userRoleTypeMappingEntity.setModifiedOn(date);
                                                                                         userRoleTypeMappingEntity.setCreatedBy(loggedInUserDetails.getUserName());
                                                                                         userRoleTypeMappingEntity.setCreatedOn(date);
                                                                                         userRoleTypeRepository.save(userRoleTypeMappingEntity);
                                                                         //userRoleTypeRepository.updateAdminBymasterId(masterEntity.getMasterUserId());
                                                                         
                                                         }else {
                                                                         userRoleTypeRepository.DeleteBymasterId(masterUserEntity.getMasterUserId());
                                                         }
                                                         
                                                         List<AssignRolesModel> assignRolesModelNew = new ArrayList();
                                                    
                                                         for(AssignRolesModel obj: assignRolesModel ) {
                                                        	 
                                                                         Long appModuleId = obj.getAppModuleId();
                                                                         if(appModuleId==2) {
                                                                                         AssignRolesModel model = new AssignRolesModel();
                                                                                         List<String> displayRoleTypes = obj.getDisplayRoleType();
                                                                                         List<String> displayRoleTypesNew = new ArrayList();
                                                                                         for(String displayRoleType:displayRoleTypes) {
                                                                                                         if(displayRoleType.equalsIgnoreCase("Checker")) {
                                                                                                                         displayRoleTypesNew.add("Checker");
                                                                                                                         model.setAppModuleId(5l);
                                                                                                                         model.setAppModuleName("CLAIM");  
                                                                                                                         model.setSrNumber(obj.getSrNumber());
                                                                                                                         model.setLoggedInUsername(obj.getLoggedInUsername());
                                                                                                         }else if(displayRoleType.equalsIgnoreCase("Maker")) {
                                                                                                                         displayRoleTypesNew.add("Maker");
                                                                                                                         model.setAppModuleId(5l);
                                                                                                                         model.setAppModuleName("CLAIM");
                                                                                                                         model.setSrNumber(obj.getSrNumber());
                                                                                                                         model.setLoggedInUsername(obj.getLoggedInUsername());
                                                                                                         }
                                                                                         }
                                                                                         model.setDisplayRoleType(displayRoleTypesNew);
                                                                                         assignRolesModelNew.add(model);
                                                                         }

                                                         }


                                                         for(int i=0;i<assignRolesModelNew.size();i++) {
                                                             assignRolesModel.add(assignRolesModelNew.get(i)) ;
                                                         }
                                                         
                                                         
                                                         
                                                         for(AssignRolesModel obj: assignRolesModel ) {                                                                  
                                                                         String srNumber = obj.getSrNumber();
                                                                         Long appModuleId = obj.getAppModuleId();
                                                                         String oldRoleNames = null;
                                                                         String moduleName = "";
                                                                         MasterUsersEntity masterEntity = null;
                                                                         MasterUsersEntity masterEntityAdmin = null;
                                                                         char[] first= null;
                                                                         char[] second= null; 
                                                                       //getting diaplay role names based on old role type ids 
                                                                         List<Long> oldRoles = (List<Long>)oldRolesMap.get(appModuleId);
                                                                         List<String> oldDisplayRoleNames  = new ArrayList();  
                                                                    	 if(oldRoles != null && oldRoles.size() > 0) {
    	                                                                     for(Long str : oldRoles) {
    	                                                                    	 MasterRolesDisplayRolesMappingEntity rolesMappingEntity = masterRolesDisplayRolesMappingRepository.getDisplayRoleNameBasedOnRoleType(str);
    	                                                                    	 oldDisplayRoleNames.add(rolesMappingEntity.getDisplayRoleTypeName());
    	                                                                    	 moduleName = rolesMappingEntity.getAppModuleName();
    	                                                                     }
    	                                                                      oldRoleNames = String.join(",", oldDisplayRoleNames);
    	                                                                      first = oldRoleNames.toCharArray();
    	                                                                      Arrays.sort(first);
                                                                         }
                                                                    	  //getting diaplay role names based on old role type ids
                                                                    	 masterEntity =  masterUserRepository.getAllMasterUserDetail(srNumber);
                                                                         masterEntityAdmin =  masterUserRepository.getAllMasterUserDetailByUserName(obj.getLoggedInUsername());
                                                                         
                                                                         List<String> displayRoleTypeNames = new ArrayList();
                                                                         displayRoleTypeNames = obj.getDisplayRoleType();
                                                                         if(displayRoleTypeNames != null && displayRoleTypeNames.size() > 0) {     
                                                                                         for(String displayRoleTypeName : displayRoleTypeNames) {                                                                                                     
                                                                                                         MasterRolesDisplayRolesMappingEntity  displayRoles = masterRolesDisplayRolesMappingRepository.getMasterRoleTypeDetails(appModuleId, displayRoleTypeName, masterEntity.getLocationType());
                                                                                                         if (displayRoles!=null) {
                                                                                                           UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
                                                                                                                         userRoleTypeMappingEntity.setAppModuleId(displayRoles.getAppModuleId());;
                                                                                                                         userRoleTypeMappingEntity.setRoleTypeId(displayRoles.getRoleTypeId());
                                                                                                                         userRoleTypeMappingEntity.setMasterUserId(masterEntity.getMasterUserId());
                                                                                                                         userRoleTypeMappingEntity.setIsActive("Y");
                                                                                                                         userRoleTypeMappingEntity.setIsDeleted("N");
                                                                                                          //     userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
                                                                                                                         userRoleTypeMappingEntity.setModifiedBy(loggedInUserDetails.getUserName());
                                                                                                                         userRoleTypeMappingEntity.setModifiedOn(date);
                                                                                                                         userRoleTypeMappingEntity.setCreatedBy(loggedInUserDetails.getUserName());
                                                                                                                         userRoleTypeMappingEntity.setCreatedOn(date);
                                                                                                                         userRoleTypeRepository.save(userRoleTypeMappingEntity);
                                                                                                                       
                                                                                                         }
                                                                                         }
                                                                                         
                                                                                         if(!moduleRepository.findModuleNameById(appModuleId).equalsIgnoreCase("CLAIM"))
                                                                                         {
                                                                                        	 String newRoleNames = String.join(",", obj.getDisplayRoleType());
                                                                                        	 second = newRoleNames.toCharArray();
                                                                                        	 Arrays.sort(second);
                                                                                        	 if((moduleName != null && !moduleName.equalsIgnoreCase(moduleRepository.findModuleNameById(appModuleId))) ||
                                                                                            			(moduleName != null && moduleName.equalsIgnoreCase(moduleRepository.findModuleNameById(appModuleId)) && !Arrays.equals(first, second))) {
	                                                                                        	MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
	                                                                                        	historyDetails.setAdminUserName(masterEntityAdmin.getUserName());
	                                                                                        	historyDetails.setAdminUserSrnumber(masterEntityAdmin.getSrNumber2());
	                                                                                        	historyDetails.setAdminUserLocationType(masterEntityAdmin.getLocationType());
	                                                                                        	historyDetails.setAdminUserLocationCode(masterEntityAdmin.getLocationCode());
	                                                                                        	historyDetails.setUserName(masterEntity.getUserName());
	                                                                                         	historyDetails.setUserSrnumber(masterEntity.getSrNumber2());
	                                                                                         	historyDetails.setUserLocationType(masterEntity.getLocationType());
	                                                                                         	historyDetails.setUserLocationCode(masterEntity.getLocationCode());
	                                                                                         	historyDetails.setRemarks(remarks);
	                                                                                         	if(oldRoleNames == null) {
	                                                                                         		historyDetails.setAdminUserActivityPerformed("Roles Assigned");
	                                                                                         		historyDetails.setUserOldRoles("NA");
	                                                                                         	}else {
	                                                                                         		historyDetails.setAdminUserActivityPerformed("Roles Modified");
	                                                                                         		historyDetails.setUserOldRoles(oldRoleNames);
	                                                                                         	}
	                                                                                        	historyDetails.setUserModule(moduleRepository.findModuleNameById(appModuleId));
	                                                                                        	historyDetails.setIsUserAdmin("NA");
	                                                                                        	historyDetails.setIsUserMarketingNonMarketing("NA");
	                                                                                        	historyDetails.setUserNewRoles(String.join(",", obj.getDisplayRoleType()));
	                                                                                        	historyDetails.setCreatedBy(loggedInUserDetails.getUserName());
	                                                                                        	historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
	                                                                                        	masterUsersHistoryDetailsRepository.save(historyDetails);
                                                                                        	 }
                                                                                         }
                                                                         }else if(oldRoleNames != null) {
                                                                        	 if(!moduleRepository.findModuleNameById(appModuleId).equalsIgnoreCase("CLAIM"))
                                                                             {
                                                                        		 String newRoleNames = String.join(",", obj.getDisplayRoleType());
                                                                            	 second = newRoleNames.toCharArray();
                                                                            	 Arrays.sort(second);
                                                                            	 if((moduleName != null && !moduleName.equalsIgnoreCase(moduleRepository.findModuleNameById(appModuleId))) ||
                                                                                			(moduleName != null && moduleName.equalsIgnoreCase(moduleRepository.findModuleNameById(appModuleId)) && !Arrays.equals(first, second))) {
	                                                                            	MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
	                                                                            	historyDetails.setAdminUserName(masterEntityAdmin.getUserName());
	                                                                            	historyDetails.setAdminUserSrnumber(masterEntityAdmin.getSrNumber2());
	                                                                            	historyDetails.setAdminUserLocationType(masterEntityAdmin.getLocationType());
	                                                                            	historyDetails.setAdminUserLocationCode(masterEntityAdmin.getLocationCode());
	                                                                            	historyDetails.setUserName(masterEntity.getUserName());
	                                                                             	historyDetails.setUserSrnumber(masterEntity.getSrNumber2());
	                                                                             	historyDetails.setUserLocationType(masterEntity.getLocationType());
	                                                                             	historyDetails.setUserLocationCode(masterEntity.getLocationCode());
	                                                                             	historyDetails.setRemarks(remarks);
	                                                                            	historyDetails.setAdminUserActivityPerformed("Roles Modified");
	                                                                            	historyDetails.setUserModule(moduleRepository.findModuleNameById(appModuleId));
	                                                                            	historyDetails.setIsUserAdmin("NA");
	                                                                            	historyDetails.setIsUserMarketingNonMarketing("NA");
	                                                                            	historyDetails.setUserOldRoles(oldRoleNames);
	                                                                            	historyDetails.setUserNewRoles(obj.getDisplayRoleType().size() > 0 ? String.join(",", obj.getDisplayRoleType()):null);
	                                                                            	historyDetails.setCreatedBy(loggedInUserDetails.getUserName());
	                                                                            	historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
	                                                                            	masterUsersHistoryDetailsRepository.save(historyDetails);
                                                                        		 }
                                                                             } 
                                                                         }
                                                         }
                                                         response.put(Constant.STATUS, 200);
                                                         response.put(Constant.MESSAGE, Constant.SUCCESS);
                                                         response.put("Data", "Roles Assigned to user sucessfully " );
                                         }
                                    	 else {
                                             response.put(Constant.STATUS, 201);
                                             response.put(Constant.DATA, "User Cannot Assign Roles to diffrent location Type");
                                             response.put(Constant.MESSAGE, Constant.FAILED);
                                         }
                                     }

                                     return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

                     } catch (Exception exception) {
                                     exception.printStackTrace();
                                     logger.info("Could not save  userRoleType : " + exception.getMessage());
                     }
                     return null;
     }

     /*
     * Description: This function is used for getting data from MASTER USERS Module by using srNumber for getting UnitDetails
     * Table Name- MASTER_USERS
     * Author- Nandini R
     */
     
    
	/*
	 * Description: This function is used for getting data from MASTER USERS Module by using srNumber for getting UnitDetails
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@Override
	public List<UnitModel> getUnitDetails(String userName) throws Exception {

		List<UnitModel> modelList = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement preparestatements = null;
		String sql = null;
		Connection connection = jdbcConnection.getConnection();
		try {
			
			sql = "select MU.LOCATION_TYPE, UN.* from master_users MU, "
					+"master_unit UN where MU.LOCATION_CODE = UN.UNIT_CODE AND MU.USERNAME=?";
			preparestatements = connection.prepareStatement(sql);
			preparestatements.setString(1, userName);

			rs = preparestatements.executeQuery();

			while (rs.next()) {
				UnitModel  modal= new UnitModel();
				modal.setServicingUnitCode(rs.getString(3));
				modal.setServicingUnitDesc(rs.getString(4));
				modal.setSeriveUnitType(rs.getString(1));
				modelList.add(modal);
			}
			return modelList;
		}
		catch (Exception e) {
			logger.info("Exception"+e.getMessage());
		}
		finally {
			if (rs!= null && !rs.isClosed()) {
				rs.close();
			}
			if (preparestatements != null) {
				preparestatements.close();
			}
			if(!connection.isClosed()) {
				connection.close();
			}
			preparestatements = null;
		}

		return null;
	}
	
	@Override
	public List<MasterUnitForGC> getStateDetailsForGC(String srNumber) throws Exception {

		List<MasterUnitForGC> modelList = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement preparestatements = null;
		String sql = null;
		Connection connection = jdbcConnection.getConnection();
		try {
			
//			sql = "select MU.*, UN.STATE_NAME from master_users MU, "
//					+"master_unit UN where MU.LOCATION_CODE = UN.UNIT_CODE AND MU.SRNUMBER=?";
			sql = "select MU.MASTER_USER_ID,MU.USERNAME,MU.SRNUMBER,MU.MOBILE,MU.EMAILID,MU.LOCATION_TYPE,MU.FIRST_NAME, \r\n" +
					 "MU.LAST_NAME,MU.LOCATION_CODE,UN.state_name from master_users MU, master_unit UN  \r\n" +
					 "where MU.LOCATION_CODE = UN.UNIT_CODE AND MU.SRNUMBER= :srNumber";
			preparestatements = connection.prepareStatement(sql);
			preparestatements.setString(1, srNumber);

			rs = preparestatements.executeQuery();

			while (rs.next()) {
				MasterUnitForGC  modal= new MasterUnitForGC();
				modal.setMasterUserId(rs.getLong("MASTER_USER_ID"));
				modal.setUserName(rs.getString("USERNAME"));
				modal.setSrNumber(rs.getString("SRNUMBER"));
				modal.setMobile(rs.getString("MOBILE"));
				modal.setEmailId(rs.getString("EMAILID"));
				modal.setLocationType(rs.getString("LOCATION_TYPE"));
				modal.setFirstName(rs.getString("FIRST_NAME"));
				modal.setFirstName(rs.getString("LAST_NAME"));
				modal.setLocationCode(rs.getString("LOCATION_CODE"));
				modal.setStatename(rs.getString("STATE_NAME"));
				modelList.add(modal);
			}
			return modelList;
		}
		catch (Exception e) {
			logger.info("Exception"+e.getMessage());
		}
		finally {
			if (rs!= null && !rs.isClosed()) {
				rs.close();
			}
			if (preparestatements != null) {
				preparestatements.close();
			}
			if(!connection.isClosed()) {
				connection.close();
			}
			preparestatements = null;
		}

		return null;
	}
	/*
	 * Description: This function is used for getting data from MASTER USERS Module by using srNumber for getting AdminOffices
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@Override
	public List<CoOfficeModel> getCoDetails(String userName) throws Exception {
		Connection connection = jdbcConnection.getConnection();
		PreparedStatement coStmt = null;
		ResultSet coResult = null;
		PreparedStatement zoStmt = null;
		ResultSet zoResult = null;
		PreparedStatement uoStmt = null;
		ResultSet uoResult = null;
		PreparedStatement soStmt = null;
		ResultSet soResult = null;
		
		try {
			String CoSelectSql = "SELECT CO.CO_ID, CO.CO_CODE, CO.DESCRIPTION FROM MASTER_CO_OFFICE CO, MASTER_USERS MU, \r\n" + 
					"MASTER_ZONAL ZO WHERE CO.CO_ID = ZO.CO_ID AND CO.CO_CODE=MU.LOCATION_CODE AND MU.USERNAME =?";

			coStmt = connection.prepareStatement(CoSelectSql);
			coStmt.setString(1, userName);
			coResult = coStmt.executeQuery();
			List<CoOfficeModel> modelDetails = new ArrayList<>();
			
			if (coResult.next()) {
				CoOfficeModel model = new CoOfficeModel();
				model.setCoId(coResult.getLong(1));
				model.setCoCode(coResult.getString(2));
				model.setCoDescription(coResult.getString(3));
				//				model.setZoList(getZoDetails(srNumber));
				modelDetails.add(model);
			
				
			for (CoOfficeModel co : modelDetails) { 
				Long coId = co.getCoId();
				String ZOSelect = "SELECT ZO.ZONAL_ID, ZO.ZONAL_CODE, ZO.DESCRIPTION FROM MASTER_ZONAL ZO WHERE ZO.CO_ID =?";
				zoStmt = connection.prepareStatement(ZOSelect);
				zoStmt.setLong(1, coId);
				zoResult = zoStmt.executeQuery();
				List<ZOModel> zoList = new ArrayList<>();

				while(zoResult.next()) {
					ZOModel zoModel = new ZOModel();
					zoModel.setZoId(zoResult.getLong(1));
					zoModel.setZoCode(zoResult.getString(2));
					zoModel.setZoDescription(zoResult.getString(3));
					zoList.add(zoModel);
				}

				for(ZOModel zo: zoList) {
					Long zoId = zo.getZoId();
					String UoSelect = "SELECT UO.UNIT_ID,UO.UNIT_CODE , UO.CITY_NAME, UO.DESCRIPTION FROM MASTER_UNIT UO WHERE UO.ZONAL_ID =?";
					uoStmt = connection.prepareStatement(UoSelect);
					uoStmt.setLong(1, zoId);
					uoResult = uoStmt.executeQuery();
					List<UOModel> uoList = new ArrayList<>();
					
					while(uoResult.next()) {
						UOModel uoModel = new UOModel();
						uoModel.setUnitId(uoResult.getLong(1));
						uoModel.setUnitCode(uoResult.getString(2));
						uoModel.setUnitCity(uoResult.getString(3));
						uoModel.setUnitDescription(uoResult.getString(4));
						uoList.add(uoModel);
					}
					for(UOModel uo: uoList) {
						Long uoId = uo.getUnitId();
						String soSelect = "SELECT MS.SATELLITE_ID, MS.SATELLITE_CODE, MS.DESCRIPTION FROM MASTER_SATELLITE MS WHERE MS.UNIT_ID=?";
						soStmt = connection.prepareStatement(soSelect);
						soStmt.setLong(1, uoId);
						soResult = soStmt.executeQuery();
						List<SOModel> soList = new ArrayList<>();
						
						while(soResult.next()) {
							SOModel soModel = new SOModel();
							soModel.setSoId(soResult.getLong(1));
							soModel.setSoCode(soResult.getString(2));
							soModel.setSoDescription(soResult.getString(3));
							soList.add(soModel);
						}
						uo.setSoList(soList);
					}
					zo.setUoList(uoList);
				}
				co.setZoList(zoList);
			}
			
		}
			return modelDetails;
		} catch(Exception ex) {
			logger.info("Exception: "+ex.getMessage());
		}
		finally {
			if (coResult!= null && !coResult.isClosed()) {
				coResult.close();
			}
			if (coStmt != null) {
				coStmt.close();
			}
			
			if (zoResult!= null && !zoResult.isClosed()) {
				zoResult.close();
			}
			if (zoStmt != null) {
				zoStmt.close();
			}
			
			if (uoResult!= null && !uoResult.isClosed()) {
				uoResult.close();
			}
			if (uoStmt != null) {
				uoStmt.close();
			}
			if (soResult!= null && !soResult.isClosed()) {
				soResult.close();
			}
			if (soStmt != null) {
				soStmt.close();
			}
			coStmt = null;
			zoStmt = null;
			uoStmt = null;
			soStmt = null;
			connection.close();
		}
		return null;
	}
	
	
	  @Override
      public List<UserRoleModel> getUserRoleDetails(String srNumber,String loggedInUsername) throws Exception {
		  Map<String, Object> response = new HashMap<String, Object>();
                      Connection connection = jdbcConnection.getConnection();
                      PreparedStatement uoStmt = null;
                      ResultSet uoResult = null;
                      PreparedStatement soStmt = null;
                      ResultSet soResult = null;
                      PreparedStatement roleStmt = null;
                      ResultSet roleResult = null;
                      displayRoleTypeNameModel displayModelforPMJJBY = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforGENERALACCOUNTS = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforSWAV = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforCLAIM = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforMANDHAN = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforANNUITY = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforLEAVEENCASHMENT = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforRYTHU = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforSKDRDP = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforSUPERANN = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforGIRLCHILD = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforGSLI = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforActurial = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforSSS = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforGI = new displayRoleTypeNameModel();
                      displayRoleTypeNameModel displayModelforGRATUITY = new displayRoleTypeNameModel();
                      List<String> displayRoleTypesForPMJJBY = new ArrayList();
                      List<String> displayRoleTypesForGENERALACCOUNTS = new ArrayList();
                      List<String> displayRoleTypesForSWAV = new ArrayList();
                      List<String> displayRoleTypesForCLAIM = new ArrayList();
                      List<String> displayRoleTypesForMANDHAN = new ArrayList();
                      List<String> displayRoleTypesForANNUITY = new ArrayList();
                      List<String> displayRoleTypesForLEAVEENCASHMENT = new ArrayList();
                      List<String> displayRoleTypesForRYTHU = new ArrayList();
                      List<String> displayRoleTypesForSKDRDP = new ArrayList();
                      List<String> displayRoleTypesForSUPERANN = new ArrayList();
                      List<String> displayRoleTypesForGIRLCHILD = new ArrayList();
                      List<String> displayRoleTypesForGSLI = new ArrayList();
                      List<String> displayRoleTypesForActurial= new ArrayList();
                      List<String> displayRoleTypesForSSS = new ArrayList();
                      List<String> displayRoleTypesForGI= new ArrayList();
                      List<String> displayRoleTypesForGRATUITY = new ArrayList();
                      
                      try {
                    	 
                    	  
                                      String uoSelectSql = "SELECT * FROM MASTER_USERS where IS_ACTIVE = 'Y' and IS_DELETED = 'N' and SRNUMBER_MAIN = ?";
                                      uoStmt = connection.prepareStatement(uoSelectSql);
                                      uoStmt.setString(1, srNumber);
                                      uoResult = uoStmt.executeQuery();
                                      List<UserRoleModel> uoList = new ArrayList<>();

                                      if (uoResult.next()) {
                                                      UserRoleModel userRoleModel = new UserRoleModel();
                                                      userRoleModel.setUserName(uoResult.getString("USERNAME"));
                                                      userRoleModel.setSrNumber(uoResult.getString("SRNUMBER_MAIN"));
                                                      userRoleModel.setMasterUserId(uoResult.getLong("MASTER_USER_ID"));
                                                      userRoleModel.setLocationType(uoResult.getString("LOCATION_TYPE"));
                                                      userRoleModel.setLocation(uoResult.getString("LOCATION"));
                                                      userRoleModel.setIsUserAdmin(uoResult.getString("ISADMIN"));
                                                      userRoleModel.setUserCategory(uoResult.getString("CATEGORY"));
                                                      userRoleModel.setEmailId(uoResult.getString("EMAILID"));
                                                      uoList.add(userRoleModel);
                                      
                                      for(UserRoleModel uo: uoList) {
                                                      Long masterUserId = uo.getMasterUserId();
                                                      String soSelect = "select  distinct ROLETYPEID,appmoduleid from user_roletype_mapping where master_user_id = ?";
                                                      soStmt = connection.prepareStatement(soSelect);
                                                      soStmt.setLong(1, masterUserId);
                                                      soResult = soStmt.executeQuery();
                                                      List<getAppRoleTypeModel> appRoleType = new ArrayList<>();
                                                      while(soResult.next()) {
                                                                      getAppRoleTypeModel roleTypeModel = new getAppRoleTypeModel();
                                                                      roleTypeModel.setRoleTypeId(soResult.getLong(1));
                                                                      roleTypeModel.setAppModuleId(soResult.getLong(2));
                                                                      appRoleType.add(roleTypeModel);
                                                      }
                                                      List<displayRoleTypeNameModel> roleType = new ArrayList<>();
                                                      
                                                      List<MasterApplicationModule> masterApplicationModules = new ArrayList();
                                                      masterApplicationModules = moduleRepository.findAllModule();
                                                      
                                                      for(MasterApplicationModule applicationModule : masterApplicationModules) {
                                                                      if(applicationModule.getAppModule().equalsIgnoreCase("PMJJBY")) {
                                                                                      displayModelforPMJJBY.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforPMJJBY.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypesForPmjjby = new ArrayList();
                                                                                      applicbleRoleTypesForPmjjby.add("View Role");
                                                                                      applicbleRoleTypesForPmjjby.add("Maker");
                                                                                      applicbleRoleTypesForPmjjby.add("Checker");
                                                                                      applicbleRoleTypesForPmjjby.add("Approver");
                                                                                      displayModelforPMJJBY.setApplicableRoleTypes(applicbleRoleTypesForPmjjby);
                                                                      }else if(applicationModule.getAppModule().equalsIgnoreCase("GENERAL_ACCOUNTS")) {
                                                                    	  displayModelforGENERALACCOUNTS.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                    	  displayModelforGENERALACCOUNTS.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypesForAccounting = new ArrayList();
                                                                                      applicbleRoleTypesForAccounting.add("View Role");
                                                                                      applicbleRoleTypesForAccounting.add("Maker");
                                                                                      applicbleRoleTypesForAccounting.add("Checker");
                                                                                      applicbleRoleTypesForAccounting.add("Approver");
                                                                                      displayModelforGENERALACCOUNTS.setApplicableRoleTypes(applicbleRoleTypesForAccounting);
                                                                      }else if(applicationModule.getAppModule().equalsIgnoreCase("SWAVLAMBAN")) {
                                                                                      displayModelforSWAV.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforSWAV.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypes = new ArrayList();
                                                                                      applicbleRoleTypes.add("View Role");
                                                                                      applicbleRoleTypes.add("Maker");
                                                                                      applicbleRoleTypes.add("Checker");
                                                                                      displayModelforSWAV.setApplicableRoleTypes(applicbleRoleTypes);
                                                                      }/*else if(applicationModule.getAppModule().equalsIgnoreCase("CLAIM")) {
                                                                                      displayModelforCLAIM.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforCLAIM.setAppModuleName(applicationModule.getAppModule());
                                                                      }*/else if(applicationModule.getAppModule().equalsIgnoreCase("MANDHAN")) {
                                                                                      displayModelforMANDHAN.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforMANDHAN.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypes = new ArrayList();
                                                                                      applicbleRoleTypes.add("View Role");
                                                                                      applicbleRoleTypes.add("Maker");
                                                                                      applicbleRoleTypes.add("Checker");
                                                                                      applicbleRoleTypes.add("Approver");
                                                                                      displayModelforMANDHAN.setApplicableRoleTypes(applicbleRoleTypes);
                                                                      }else if(applicationModule.getAppModule().equalsIgnoreCase("ANNUITY")) {
                                                                                      displayModelforANNUITY.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforANNUITY.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypes = new ArrayList();
                                                                                      applicbleRoleTypes.add("View Role");
                                                                                      applicbleRoleTypes.add("Maker");
                                                                                      applicbleRoleTypes.add("Checker");
                                                                                      applicbleRoleTypes.add("Approver");
                                                                                      displayModelforANNUITY.setApplicableRoleTypes(applicbleRoleTypes);
                                                                      }else if(applicationModule.getAppModule().equalsIgnoreCase("LEAVE_ENCASHMENT")) {
                                                                    	  displayModelforLEAVEENCASHMENT.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                    	  displayModelforLEAVEENCASHMENT.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypes = new ArrayList();
                                                                                      applicbleRoleTypes.add("ViewRole");
                                                                                      applicbleRoleTypes.add("Maker");
                                                                                      applicbleRoleTypes.add("Checker");
                                                                                      applicbleRoleTypes.add("Approver");
                                                                                      applicbleRoleTypes.add("Underwriter");
                                                                                      displayModelforLEAVEENCASHMENT.setApplicableRoleTypes(applicbleRoleTypes);
                                                                      }else if(applicationModule.getAppModule().equalsIgnoreCase("RYTHU_BIMA")) {
                                                                                      displayModelforRYTHU.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforRYTHU.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypes = new ArrayList();
                                                                                      applicbleRoleTypes.add("ViewRole");
                                                                                      applicbleRoleTypes.add("Maker");
                                                                                      applicbleRoleTypes.add("Checker");
                                                                                      applicbleRoleTypes.add("Approver");
                                                                                      applicbleRoleTypes.add("Underwriter");
                                                                                      displayModelforRYTHU.setApplicableRoleTypes(applicbleRoleTypes);
                                                                      }else if(applicationModule.getAppModule().equalsIgnoreCase("SKDRDP")) {
                                                                                      displayModelforSKDRDP.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforSKDRDP.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypes = new ArrayList();
                                                                                      applicbleRoleTypes.add("View Role");
                                                                                      applicbleRoleTypes.add("Maker");
                                                                                      applicbleRoleTypes.add("Checker");
                                                                                      applicbleRoleTypes.add("Approver");
                                                                                      applicbleRoleTypes.add("Underwriter");
                                                                                      displayModelforSKDRDP.setApplicableRoleTypes(applicbleRoleTypes);
                                                                      }else if(applicationModule.getAppModule().equalsIgnoreCase("SUPERANNUATION")) {
                                                                                      displayModelforSUPERANN.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforSUPERANN.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypes = new ArrayList();
                                                                                      applicbleRoleTypes.add("View Role");
                                                                                      applicbleRoleTypes.add("Maker");
                                                                                      applicbleRoleTypes.add("Checker");
                                                                                      applicbleRoleTypes.add("Approver");
                                                                                      applicbleRoleTypes.add("Underwriter");
                                                                                      displayModelforSUPERANN.setApplicableRoleTypes(applicbleRoleTypes);
                                                                      }else if(applicationModule.getAppModule().equalsIgnoreCase("GIRL_CHILD")) {
                                                                                      displayModelforGIRLCHILD.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforGIRLCHILD.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypes = new ArrayList();
                                                                                      applicbleRoleTypes.add("View Role");
                                                                                      applicbleRoleTypes.add("Maker");
                                                                                      applicbleRoleTypes.add("Checker");
                                                                                      applicbleRoleTypes.add("Approver");
                                                                                      applicbleRoleTypes.add("Underwriter");
                                                                                      displayModelforGIRLCHILD.setApplicableRoleTypes(applicbleRoleTypes);
                                                                      }else if(applicationModule.getAppModule().equalsIgnoreCase("GI")) {
                                                                                      displayModelforGI.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforGI.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypes = new ArrayList();
                                                                                      applicbleRoleTypes.add("View Role");
                                                                                      applicbleRoleTypes.add("Maker");
                                                                                      applicbleRoleTypes.add("Checker");
                                                                                      applicbleRoleTypes.add("Approver");
                                                                                      displayModelforGI.setApplicableRoleTypes(applicbleRoleTypes);
                                                                      }else if(applicationModule.getAppModule().equalsIgnoreCase("GRATUITY")) {
                                                                                      displayModelforGRATUITY.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforGRATUITY.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypes = new ArrayList();
                                                                                      applicbleRoleTypes.add("View Role");
                                                                                      applicbleRoleTypes.add("Maker");
                                                                                      applicbleRoleTypes.add("Checker");
                                                                                      applicbleRoleTypes.add("Approver");
                                                                                      displayModelforGRATUITY.setApplicableRoleTypes(applicbleRoleTypes);
                                                                      }else if(applicationModule.getAppModule().equalsIgnoreCase("GSLI")) {
                                                                                      displayModelforGSLI.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforGSLI.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypes = new ArrayList();
                                                                                      applicbleRoleTypes.add("View Role");
                                                                                      applicbleRoleTypes.add("Maker");
                                                                                      applicbleRoleTypes.add("Checker");
                                                                                      applicbleRoleTypes.add("Approver");
                                                                                      applicbleRoleTypes.add("Underwriter");
                                                                                      displayModelforGSLI.setApplicableRoleTypes(applicbleRoleTypes);
                                                                      }else if(applicationModule.getAppModule().equalsIgnoreCase("ACTURIAL")) {
                                                                                      displayModelforActurial.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforActurial.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypes = new ArrayList();
                                                                                      applicbleRoleTypes.add("View Role");
                                                                                      applicbleRoleTypes.add("Maker");
                                                                                      applicbleRoleTypes.add("Checker");
                                                                                      applicbleRoleTypes.add("Approver");
                                                                                      applicbleRoleTypes.add("Underwriter");
                                                                                      displayModelforActurial.setApplicableRoleTypes(applicbleRoleTypes);
                                                                      }else if(applicationModule.getAppModule().equalsIgnoreCase("SSS")) {
                                                                                      displayModelforSSS.setAppModuleId(String.valueOf(applicationModule.getModuleId()));
                                                                                      displayModelforSSS.setAppModuleName(applicationModule.getAppModule());
                                                                                      List<String> applicbleRoleTypes = new ArrayList();
                                                                                      applicbleRoleTypes.add("View Role");
                                                                                      applicbleRoleTypes.add("Maker");
                                                                                      applicbleRoleTypes.add("Checker");
                                                                                      applicbleRoleTypes.add("Approver");
                                                                                      displayModelforSSS.setApplicableRoleTypes(applicbleRoleTypes); 
                                                                      }
                                                      }
                                                      for(getAppRoleTypeModel arm: appRoleType) {
                                                                      Long roleTypeId = arm.getRoleTypeId();
                                                                      Long appModuleId = arm.getAppModuleId();
                                                                      String roleSelect = "select APP_MODULE_ID,APP_MODULE_NAME,DISPLAY_ROLE_TYPE_NAME from master_roles_displayroles_mapping where role_type_id = ? and app_module_id =?";
                                                                      roleStmt = connection.prepareStatement(roleSelect);
                                                                      roleStmt.setLong(1, roleTypeId);
                                                                      roleStmt.setLong(2, appModuleId);
                                                                      roleResult = roleStmt.executeQuery();
                                                                      
                                                                      while(roleResult.next()) {
                                                                    	  if(roleResult.getString(2).equalsIgnoreCase("PMJJBY") || roleResult.getString(2).equalsIgnoreCase("CLAIM")) {
                                                                        	  if((roleResult.getString(3).equalsIgnoreCase("Maker") && !displayRoleTypesForPMJJBY.contains("Maker")) || 
                                                                        			  (roleResult.getString(3).equalsIgnoreCase("Checker") && !displayRoleTypesForPMJJBY.contains("Checker")) ||
                                                                        			  (roleResult.getString(3).equalsIgnoreCase("View Role") && !displayRoleTypesForPMJJBY.contains("View Role")) || 
                                                                        			  (roleResult.getString(3).equalsIgnoreCase("Approver") && !displayRoleTypesForPMJJBY.contains("Approver"))){
                                                                                          displayRoleTypesForPMJJBY.add(roleResult.getString(3));
                                                                        	  		  }}else if(roleResult.getString(2).equalsIgnoreCase("GENERAL_ACCOUNTS")) {
                                                                                          displayRoleTypesForGENERALACCOUNTS.add(roleResult.getString(3));
                                                                          }else if(roleResult.getString(2).equalsIgnoreCase("SWAVLAMBAN")) {
                                                                                                      displayRoleTypesForSWAV.add(roleResult.getString(3));
                                                                                      }/*else if(roleResult.getString(2).equalsIgnoreCase("CLAIM")) {
                                                                                                      displayRoleTypesForCLAIM.add(roleResult.getString(3));
                                                                                      }*/else if(roleResult.getString(2).equalsIgnoreCase("MANDHAN")) {
                                                                                                      displayRoleTypesForMANDHAN.add(roleResult.getString(3));
                                                                                      }else if(roleResult.getString(2).equalsIgnoreCase("ANNUITY")) {
                                                                                                      displayRoleTypesForANNUITY.add(roleResult.getString(3));
                                                                                      }else if(roleResult.getString(2).equalsIgnoreCase("LEAVE_ENCASHMENT")) {
                                                                                                      displayRoleTypesForLEAVEENCASHMENT.add(roleResult.getString(3));
                                                                                      }else if(roleResult.getString(2).equalsIgnoreCase("RYTHU_BIMA")) {
                                                                                                      displayRoleTypesForRYTHU.add(roleResult.getString(3));
                                                                                      }else if(roleResult.getString(2).equalsIgnoreCase("SKDRDP")) {
                                                                                                      displayModelforSKDRDP.setAppModuleName(roleResult.getString(2));
                                                                                                      displayRoleTypesForSKDRDP.add(roleResult.getString(3));
                                                                                      }else if(roleResult.getString(2).equalsIgnoreCase("SUPERANNUATION")) {
                                                                                                      displayRoleTypesForSUPERANN.add(roleResult.getString(3));
                                                                                      }else if(roleResult.getString(2).equalsIgnoreCase("GIRL_CHILD")) {
                                                                                                      displayRoleTypesForGIRLCHILD.add(roleResult.getString(3));
                                                                                      }else if(roleResult.getString(2).equalsIgnoreCase("GI")) {
                                                                                                      displayRoleTypesForGI.add(roleResult.getString(3));
                                                                                      }else if(roleResult.getString(2).equalsIgnoreCase("GRATUITY")) {
                                                                                                      displayRoleTypesForGRATUITY.add(roleResult.getString(3));
                                                                                      }else if(roleResult.getString(2).equalsIgnoreCase("GSLI")) {
                                                                                                      displayRoleTypesForGSLI.add(roleResult.getString(3));
                                                                                      }else if(roleResult.getString(2).equalsIgnoreCase("ACTURIAL")) {
                                                                                                      displayRoleTypesForActurial.add(roleResult.getString(3));
                                                                                      }else if(roleResult.getString(2).equalsIgnoreCase("SSS")) {
                                                                                                      displayRoleTypesForSSS.add(roleResult.getString(3));
                                                                                      }                                                                                              
                                                      }                                                              
                                      }                                                              
                                                      displayModelforPMJJBY.setDisplayRoleTypes(displayRoleTypesForPMJJBY);
                                                      displayModelforGENERALACCOUNTS.setDisplayRoleTypes(displayRoleTypesForGENERALACCOUNTS);
                                                      displayModelforSWAV.setDisplayRoleTypes(displayRoleTypesForSWAV);
                                                      //displayModelforCLAIM.setDisplayRoleTypes(displayRoleTypesForCLAIM);
                                                      displayModelforMANDHAN.setDisplayRoleTypes(displayRoleTypesForMANDHAN);
                                                      displayModelforANNUITY.setDisplayRoleTypes(displayRoleTypesForANNUITY);
                                                      displayModelforLEAVEENCASHMENT.setDisplayRoleTypes(displayRoleTypesForLEAVEENCASHMENT);
                                                      displayModelforRYTHU.setDisplayRoleTypes(displayRoleTypesForRYTHU);
                                                      displayModelforSKDRDP.setDisplayRoleTypes(displayRoleTypesForSKDRDP);
                                                      displayModelforSUPERANN.setDisplayRoleTypes(displayRoleTypesForSUPERANN);
                                                      displayModelforGIRLCHILD.setDisplayRoleTypes(displayRoleTypesForGIRLCHILD);
                                                      displayModelforGSLI.setDisplayRoleTypes(displayRoleTypesForGSLI);
                                                      displayModelforActurial.setDisplayRoleTypes(displayRoleTypesForActurial);
                                                      displayModelforSSS.setDisplayRoleTypes(displayRoleTypesForSSS);
                                                      displayModelforGI.setDisplayRoleTypes(displayRoleTypesForGI);
                                                      displayModelforGRATUITY.setDisplayRoleTypes(displayRoleTypesForGRATUITY);
                                                      
                                                      roleType.add(displayModelforPMJJBY);
                                                      roleType.add(displayModelforGENERALACCOUNTS);
                                                      roleType.add(displayModelforSWAV);
                                                      //roleType.add(displayModelforCLAIM);
                                                      roleType.add(displayModelforMANDHAN);
                                                      roleType.add(displayModelforANNUITY);
                                                      roleType.add(displayModelforLEAVEENCASHMENT);
                                                      roleType.add(displayModelforRYTHU);
                                                      roleType.add(displayModelforSKDRDP);
                                                      roleType.add(displayModelforSUPERANN);
                                                      roleType.add(displayModelforGIRLCHILD);
                                                      roleType.add(displayModelforGSLI);
                                                      roleType.add(displayModelforActurial);
                                                      roleType.add(displayModelforSSS);          
                                                      roleType.add(displayModelforGI);
                                                      roleType.add(displayModelforGRATUITY);             
                                      
                                                      uo.setRoleType(roleType);
                                                      }                                              
                                      }
                    	  
                                      return uoList;
                    	  
                      }catch(Exception ex) {
                                      ex.printStackTrace();
                                      logger.info("Exception: "+ex.getMessage());
                      }
                      finally {
                                      if (uoResult!= null && !uoResult.isClosed()) {
                                                      uoResult.close();
                                      }
                                      if (uoStmt != null) {
                                                      uoStmt.close();
                                      }
                                      if (soResult!= null && !soResult.isClosed()) {
                                                      soResult.close();
                                      }
                                      if (soStmt != null) {
                                                      soStmt.close();
                                      }
                                      if (roleResult!= null && !roleResult.isClosed()) {
                                                      roleResult.close();
                                      }
                                      if (soStmt != null) {
                                                      soStmt.close();
                                      }
                                      uoStmt = null;
                                      soStmt = null;
                                      roleStmt = null;
                                      connection.close();
                      }
                      return null;
      }

	@Override
	public List<ZOModel> getZoDetails(String userName) throws Exception {
		Connection connection = jdbcConnection.getConnection();
		PreparedStatement zoStmt = null;
		ResultSet zoResult = null;
		PreparedStatement uoStmt = null;
		ResultSet uoResult = null;
		PreparedStatement soStmt = null;
		ResultSet soResult = null;
		
		try {
			/*String zoSelectSql = "SELECT ZO.ZONAL_ID, ZO.ZONAL_CODE, ZO.DESCRIPTION FROM MASTER_ZONAL ZO, MASTER_USERS MU, \r\n" + 
					"MASTER_UNIT UO WHERE ZO.ZONAL_ID=UO.ZONAL_ID AND ZO.ZONAL_CODE=MU.LOCATION_CODE AND MU.USERNAME =?";*/
			  
			String zoSelectSql = "SELECT ZO.ZONAL_ID, ZO.ZONAL_CODE, ZO.DESCRIPTION FROM MASTER_ZONAL ZO, MASTER_USERS MU \r\n" + 
					"WHERE ZO.ZONAL_CODE=MU.LOCATION_CODE AND MU.USERNAME =?";
			zoStmt = connection.prepareStatement(zoSelectSql);
			zoStmt.setString(1, userName);
			zoResult = zoStmt.executeQuery();
			List<ZOModel> zoList = new ArrayList<>();

			while(zoResult.next()) {
				ZOModel zoModel = new ZOModel();
				zoModel.setZoId(zoResult.getLong(1));
				zoModel.setZoCode(zoResult.getString(2));
				zoModel.setZoDescription(zoResult.getString(3));
				zoList.add(zoModel);
			}

			for(ZOModel zo: zoList) {
				Long zoId = zo.getZoId();
				String UoSelect = "SELECT UO.UNIT_ID,UO.UNIT_CODE , UO.CITY_NAME, UO.DESCRIPTION FROM MASTER_UNIT UO WHERE UO.ZONAL_ID =?";
				uoStmt = connection.prepareStatement(UoSelect);
				uoStmt.setLong(1, zoId);
				uoResult = uoStmt.executeQuery();
				List<UOModel> uoList = new ArrayList<>();
				while(uoResult.next()) {
					UOModel uoModel = new UOModel();
					uoModel.setUnitId(uoResult.getLong(1));
					uoModel.setUnitCode(uoResult.getString(2));
					uoModel.setUnitCity(uoResult.getString(3));
					uoModel.setUnitDescription(uoResult.getString(4));
					uoList.add(uoModel);
				}
				for(UOModel uo: uoList) {
					Long uoId = uo.getUnitId();
					String soSelect = "SELECT MS.SATELLITE_ID, MS.SATELLITE_CODE, MS.DESCRIPTION FROM MASTER_SATELLITE MS WHERE MS.UNIT_ID=?";
					soStmt = connection.prepareStatement(soSelect);
					soStmt.setLong(1, uoId);
					soResult = soStmt.executeQuery();
					List<SOModel> soList = new ArrayList<>();
					while(soResult.next()) {
						SOModel soModel = new SOModel();
						soModel.setSoId(soResult.getLong(1));
						soModel.setSoCode(soResult.getString(2));
						soModel.setSoDescription(soResult.getString(3));
						soList.add(soModel);
					}
					uo.setSoList(soList);
				}
				zo.setUoList(uoList);
			}
			return zoList;

		} catch(Exception ex) {
			logger.info("Exception: "+ex.getMessage());
		}
		finally {
			if (zoResult!= null && !zoResult.isClosed()) {
				zoResult.close();
			}
			if (zoStmt != null) {
				zoStmt.close();
			}
			if (uoResult!= null && !uoResult.isClosed()) {
				uoResult.close();
			}
			if (uoStmt != null) {
				uoStmt.close();
			}
			if (soResult!= null && !soResult.isClosed()) {
				soResult.close();
			}
			if (soStmt != null) {
				soStmt.close();
			}
			zoStmt = null;
			uoStmt = null;
			soStmt = null;
			connection.close();
		}
		return null;
	}
	
	
	 @Override
	public List<ZOModel> getZoUnitDetails(String unitCode) throws Exception {
		Connection connection = jdbcConnection.getConnection();
		PreparedStatement zoStmt = null;
		ResultSet zoResult = null;
		PreparedStatement uoStmt = null;
		ResultSet uoResult = null;
		PreparedStatement soStmt = null;
		ResultSet soResult = null;
		
		try {
			/*String zoSelectSql = "SELECT ZO.ZONAL_ID, ZO.ZONAL_CODE, ZO.DESCRIPTION FROM MASTER_ZONAL ZO, MASTER_USERS MU, \r\n" + 
					"MASTER_UNIT UO WHERE ZO.ZONAL_ID=UO.ZONAL_ID AND ZO.ZONAL_CODE=MU.LOCATION_CODE AND MU.USERNAME =?";*/
			  
			String zoSelectSql = "SELECT ZO.ZONAL_ID, ZO.ZONAL_CODE, ZO.DESCRIPTION FROM MASTER_ZONAL ZO\r\n" + 
					"					WHERE ZO.ZONAL_CODE=?";
			zoStmt = connection.prepareStatement(zoSelectSql);
			zoStmt.setString(1, unitCode);
			zoResult = zoStmt.executeQuery();
			List<ZOModel> zoList = new ArrayList<>();

			while(zoResult.next()) {
				ZOModel zoModel = new ZOModel();
				zoModel.setZoId(zoResult.getLong(1));
				zoModel.setZoCode(zoResult.getString(2));
				zoModel.setZoDescription(zoResult.getString(3));
				zoList.add(zoModel);
			}

			for(ZOModel zo: zoList) {
				Long zoId = zo.getZoId();
				String UoSelect = "SELECT UO.UNIT_ID,UO.UNIT_CODE , UO.CITY_NAME, UO.DESCRIPTION FROM MASTER_UNIT UO WHERE UO.ZONAL_ID =?";
				uoStmt = connection.prepareStatement(UoSelect);
				uoStmt.setLong(1, zoId);
				uoResult = uoStmt.executeQuery();
				List<UOModel> uoList = new ArrayList<>();
				while(uoResult.next()) {
					UOModel uoModel = new UOModel();
					uoModel.setUnitId(uoResult.getLong(1));
					uoModel.setUnitCode(uoResult.getString(2));
					uoModel.setUnitCity(uoResult.getString(3));
					uoModel.setUnitDescription(uoResult.getString(4));
					uoList.add(uoModel);
				}
				for(UOModel uo: uoList) {
					Long uoId = uo.getUnitId();
					String soSelect = "SELECT MS.SATELLITE_ID, MS.SATELLITE_CODE, MS.DESCRIPTION FROM MASTER_SATELLITE MS WHERE MS.UNIT_ID=?";
					soStmt = connection.prepareStatement(soSelect);
					soStmt.setLong(1, uoId);
					soResult = soStmt.executeQuery();
					List<SOModel> soList = new ArrayList<>();
					while(soResult.next()) {
						SOModel soModel = new SOModel();
						soModel.setSoId(soResult.getLong(1));
						soModel.setSoCode(soResult.getString(2));
						soModel.setSoDescription(soResult.getString(3));
						soList.add(soModel);
					}
					uo.setSoList(soList);
				}
				zo.setUoList(uoList);
			}
			return zoList;

		} catch(Exception ex) {
			logger.info("Exception: "+ex.getMessage());
		}
		finally {
			if (zoResult!= null && !zoResult.isClosed()) {
				zoResult.close();
			}
			if (zoStmt != null) {
				zoStmt.close();
			}
			if (uoResult!= null && !uoResult.isClosed()) {
				uoResult.close();
			}
			if (uoStmt != null) {
				uoStmt.close();
			}
			if (soResult!= null && !soResult.isClosed()) {
				soResult.close();
			}
			if (soStmt != null) {
				soStmt.close();
			}
			zoStmt = null;
			uoStmt = null;
			soStmt = null;
			connection.close();
		}
		return null;
	}

	@Override
	public List<UOModel> getUoDetails(String userName) throws Exception {
		Connection connection = jdbcConnection.getConnection();
		PreparedStatement uoStmt = null;
		ResultSet uoResult = null;
		PreparedStatement soStmt = null;
		ResultSet soResult = null;
		
		try {
			String uoSelectSql = "SELECT DISTINCT UO.UNIT_ID,UO.UNIT_CODE , UO.CITY_NAME, UO.DESCRIPTION FROM MASTER_UNIT UO , MASTER_USERS MU\r\n" + 
					"WHERE MU.LOCATION_CODE=UO.UNIT_CODE AND MU.USERNAME =?";
			uoStmt = connection.prepareStatement(uoSelectSql);
			uoStmt.setString(1, userName);
			uoResult = uoStmt.executeQuery();
			List<UOModel> uoList = new ArrayList<>();

			while(uoResult.next()) {
				UOModel uoModel = new UOModel();
				uoModel.setUnitId(uoResult.getLong(1));
				uoModel.setUnitCode(uoResult.getString(2));
				uoModel.setUnitCity(uoResult.getString(3));
				uoModel.setUnitDescription(uoResult.getString(4));
				uoList.add(uoModel);
			}
			for(UOModel uo: uoList) {
				Long uoId = uo.getUnitId();
				String soSelect = "SELECT MS.SATELLITE_ID, MS.SATELLITE_CODE, MS.DESCRIPTION FROM MASTER_SATELLITE MS WHERE MS.UNIT_ID=?";
				soStmt = connection.prepareStatement(soSelect);
				soStmt.setLong(1, uoId);
				soResult = soStmt.executeQuery();
				List<SOModel> soList = new ArrayList<>();
				while(soResult.next()) {
					SOModel soModel = new SOModel();
					soModel.setSoId(soResult.getLong(1));
					soModel.setSoCode(soResult.getString(2));
					soModel.setSoDescription(soResult.getString(3));
					soList.add(soModel);
				}
				uo.setSoList(soList);
			}
			return uoList;
		} catch(Exception ex) {
			logger.info("Exception: "+ex.getMessage());
		}
		finally {
			if (uoResult!= null && !uoResult.isClosed()) {
				uoResult.close();
			}
			if (uoStmt != null) {
				uoStmt.close();
			}
			if (soResult!= null && !soResult.isClosed()) {
				soResult.close();
			}
			if (soStmt != null) {
				soStmt.close();
			}
			uoStmt = null;
			soStmt = null;
			connection.close();
		}
		return null;
	}

	/*
	 * Description: This function is used for getting data from MASTER USERS Module by using emailId for identifying the users
	 * Table Name- MASTER_USERS
	 * Author- Nandini R
	 */
	
	@Override
	public List<MasterUsersEntity> getAllInActiveUsersByLocationType(String locationType) throws SQLException {
		ResultSet rs = null;
		PreparedStatement preparestatements = null;
		String sql = null;
		Connection connection = jdbcConnection.getConnection();

		try {
			
		  MasterUsersEntity user = masterUserRepository.getAllInActiveLocationType(locationType);
			if(user != null) {
				if(user.getLocationType().equalsIgnoreCase("UO")) {
					sql = "select * from master_users where location_type = 'UO' and is_active = 'N' and is_deleted = 'Y' order by modifiedon desc";

					preparestatements = connection.prepareStatement(sql);
					//preparestatements.setString(1, emailId);

					rs = preparestatements.executeQuery();
					List<MasterUsersEntity> modelDetails = new ArrayList<>();

					while (rs.next()) {
						MasterUsersEntity masterSearch = new MasterUsersEntity();
						masterSearch.setMasterUserId(rs.getLong("MASTER_USER_ID"));
						masterSearch.setUserName(rs.getString("USERNAME"));
						masterSearch.setSrNumber(rs.getString("SRNUMBER"));
						masterSearch.setMobile(rs.getString("MOBILE"));
						masterSearch.setEmailId(rs.getString("EMAILID"));
						masterSearch.setCadre(rs.getString("CADRE"));
						masterSearch.setDesignation(rs.getString("DESIGNATION"));
						masterSearch.setClassName(rs.getString("CLASS"));
						masterSearch.setModifiedBy(rs.getString("MODIFIEDBY"));
						masterSearch.setModifiedOn(rs.getDate("MODIFIEDON"));
						masterSearch.setCreatedBy(rs.getString("CREATEDBY"));
						masterSearch.setCreatedOn(rs.getDate("CREATEDON"));
						masterSearch.setLocationType(rs.getString("LOCATION_TYPE"));
						masterSearch.setLocation(rs.getString("LOCATION"));
						masterSearch.setCategory(rs.getString("CATEGORY"));
						masterSearch.setDateOfAppointment(rs.getDate("DATEOFAPPOINTMENT"));
						masterSearch.setDateOfBirth(rs.getDate("DATEOFBIRTH"));
						masterSearch.setEmail(rs.getString("EMAIL"));
						masterSearch.setFirstName(rs.getString("FIRST_NAME"));
						masterSearch.setIpAddress(rs.getString("IP_ADDRESS"));
						masterSearch.setIsActive(rs.getString("IS_ACTIVE"));
						masterSearch.setIsDeleted(rs.getString("IS_DELETED"));
						masterSearch.setLastName(rs.getString("LAST_NAME"));
						masterSearch.setLocationCode(rs.getString("LOCATION_CODE"));
						masterSearch.setMaritalStatus(rs.getString("MARITAL_STATUS"));
						masterSearch.setMiddleName(rs.getString("MIDDLE_NAME"));
						masterSearch.setSex(rs.getString("SEX"));
						masterSearch.setStatus(rs.getString("STATUS"));
						modelDetails.add(masterSearch);
					}
					return modelDetails;
				}
				if(user.getLocationType().equalsIgnoreCase("ZO")) {

					sql = "select * from master_users where location_type in ('UO','ZO') and is_active = 'N' and is_deleted = 'Y' order by modifiedon desc";

					preparestatements = connection.prepareStatement(sql);
					//preparestatements.setString(1, emailId);
					rs = preparestatements.executeQuery();

					List<MasterUsersEntity> modelDetails = new ArrayList<>();

					while (rs.next()) {
						MasterUsersEntity masterSearch = new MasterUsersEntity();
						masterSearch.setMasterUserId(rs.getLong("MASTER_USER_ID"));
						masterSearch.setUserName(rs.getString("USERNAME"));
						masterSearch.setSrNumber(rs.getString("SRNUMBER"));
						masterSearch.setMobile(rs.getString("MOBILE"));
						masterSearch.setEmailId(rs.getString("EMAILID"));
						masterSearch.setCadre(rs.getString("CADRE"));
						masterSearch.setDesignation(rs.getString("DESIGNATION"));
						masterSearch.setClassName(rs.getString("CLASS"));
						masterSearch.setModifiedBy(rs.getString("MODIFIEDBY"));
						masterSearch.setModifiedOn(rs.getDate("MODIFIEDON"));
						masterSearch.setCreatedBy(rs.getString("CREATEDBY"));
						masterSearch.setCreatedOn(rs.getDate("CREATEDON"));
						masterSearch.setLocationType(rs.getString("LOCATION_TYPE"));
						masterSearch.setLocation(rs.getString("LOCATION"));
						masterSearch.setCategory(rs.getString("CATEGORY"));
						masterSearch.setDateOfAppointment(rs.getDate("DATEOFAPPOINTMENT"));
						masterSearch.setDateOfBirth(rs.getDate("DATEOFBIRTH"));
						masterSearch.setEmail(rs.getString("EMAIL"));
						masterSearch.setFirstName(rs.getString("FIRST_NAME"));
						masterSearch.setIpAddress(rs.getString("IP_ADDRESS"));
						masterSearch.setIsActive(rs.getString("IS_ACTIVE"));
						masterSearch.setIsDeleted(rs.getString("IS_DELETED"));
						masterSearch.setLastName(rs.getString("LAST_NAME"));
						masterSearch.setLocationCode(rs.getString("LOCATION_CODE"));
						masterSearch.setMaritalStatus(rs.getString("MARITAL_STATUS"));
						masterSearch.setMiddleName(rs.getString("MIDDLE_NAME"));
						masterSearch.setSex(rs.getString("SEX"));
						masterSearch.setStatus(rs.getString("STATUS"));
						modelDetails.add(masterSearch);

					}
					return modelDetails;
				}
				if(user.getLocationType().equalsIgnoreCase("CO")) {
					sql = "select * from master_users where location_type in ('CO','ZO') and is_active = 'N' and is_deleted = 'Y' order by modifiedon desc";

					preparestatements = connection.prepareStatement(sql);
					//preparestatements.setString(1, emailId);
					rs = preparestatements.executeQuery();

					List<MasterUsersEntity> modelDetails = new ArrayList<>();

					while (rs.next()) {
						MasterUsersEntity masterSearch = new MasterUsersEntity();
						masterSearch.setMasterUserId(rs.getLong("MASTER_USER_ID"));
						masterSearch.setUserName(rs.getString("USERNAME"));
						masterSearch.setSrNumber(rs.getString("SRNUMBER"));
						masterSearch.setMobile(rs.getString("MOBILE"));
						masterSearch.setEmailId(rs.getString("EMAILID"));
						masterSearch.setCadre(rs.getString("CADRE"));
						masterSearch.setDesignation(rs.getString("DESIGNATION"));
						masterSearch.setClassName(rs.getString("CLASS"));
						masterSearch.setModifiedBy(rs.getString("MODIFIEDBY"));
						masterSearch.setModifiedOn(rs.getDate("MODIFIEDON"));
						masterSearch.setCreatedBy(rs.getString("CREATEDBY"));
						masterSearch.setCreatedOn(rs.getDate("CREATEDON"));
						masterSearch.setLocationType(rs.getString("LOCATION_TYPE"));
						masterSearch.setLocation(rs.getString("LOCATION"));
						masterSearch.setCategory(rs.getString("CATEGORY"));
						masterSearch.setDateOfAppointment(rs.getDate("DATEOFAPPOINTMENT"));
						masterSearch.setDateOfBirth(rs.getDate("DATEOFBIRTH"));
						masterSearch.setEmail(rs.getString("EMAIL"));
						masterSearch.setFirstName(rs.getString("FIRST_NAME"));
						masterSearch.setIpAddress(rs.getString("IP_ADDRESS"));
						masterSearch.setIsActive(rs.getString("IS_ACTIVE"));
						masterSearch.setIsDeleted(rs.getString("IS_DELETED"));
						masterSearch.setLastName(rs.getString("LAST_NAME"));
						masterSearch.setLocationCode(rs.getString("LOCATION_CODE"));
						masterSearch.setMaritalStatus(rs.getString("MARITAL_STATUS"));
						masterSearch.setMiddleName(rs.getString("MIDDLE_NAME"));
						masterSearch.setSex(rs.getString("SEX"));
						masterSearch.setStatus(rs.getString("STATUS"));
						modelDetails.add(masterSearch);

					}
					return modelDetails;
				}
				/*if(user.getLocationType().equals("SO") || user.getLocationType().equals("Satelite")) {
					sql = "SELECT MS.* FROM MASTER_SATELLITE MS, MASTER_USERS MU\n"
							+" WHERE UO.UNIT_ID = MS.UNIT_ID AND MU.SRNUMBER =: srNumber";
				}*/
			}

		} 
		catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			throw new SQLException ("Internal server error");
		} finally {
			if (rs!= null && !rs.isClosed()) {
				rs.close();
			}
			if (preparestatements != null) {
				preparestatements.close();
			}
			if(!connection.isClosed()) {
				connection.close();
			}
			preparestatements = null;
		}
		return null;

	}
	
	@Override
	public List<MasterUsersEntity> getAllUsersByLocationType(String emailId) throws Exception {
		ResultSet rs = null;
		PreparedStatement preparestatements = null;
		String sql = null;
		Connection connection = jdbcConnection.getConnection();

		try {
			
		  MasterUsersEntity user = masterUserRepository.getAllLocationTypeByEmail(emailId);
			if(user != null) {
				if(user.getLocationType().equalsIgnoreCase("UO")) {
					sql = "select * from master_users where location_type = 'UO' and is_active = 'Y' and is_deleted = 'N' order by modifiedon desc";

					preparestatements = connection.prepareStatement(sql);
					//preparestatements.setString(1, emailId);

					rs = preparestatements.executeQuery();
					List<MasterUsersEntity> modelDetails = new ArrayList<>();

					while (rs.next()) {
						MasterUsersEntity masterSearch = new MasterUsersEntity();
						masterSearch.setMasterUserId(rs.getLong("MASTER_USER_ID"));
						masterSearch.setUserName(rs.getString("USERNAME"));
						masterSearch.setSrNumber(rs.getString("SRNUMBER"));
						masterSearch.setMobile(rs.getString("MOBILE"));
						masterSearch.setEmailId(rs.getString("EMAILID"));
						masterSearch.setCadre(rs.getString("CADRE"));
						masterSearch.setDesignation(rs.getString("DESIGNATION"));
						masterSearch.setClassName(rs.getString("CLASS"));
						masterSearch.setModifiedBy(rs.getString("MODIFIEDBY"));
						masterSearch.setModifiedOn(rs.getDate("MODIFIEDON"));
						masterSearch.setCreatedBy(rs.getString("CREATEDBY"));
						masterSearch.setCreatedOn(rs.getDate("CREATEDON"));
						masterSearch.setLocationType(rs.getString("LOCATION_TYPE"));
						masterSearch.setLocation(rs.getString("LOCATION"));
						masterSearch.setCategory(rs.getString("CATEGORY"));
						masterSearch.setDateOfAppointment(rs.getDate("DATEOFAPPOINTMENT"));
						masterSearch.setDateOfBirth(rs.getDate("DATEOFBIRTH"));
						masterSearch.setEmail(rs.getString("EMAIL"));
						masterSearch.setFirstName(rs.getString("FIRST_NAME"));
						masterSearch.setIpAddress(rs.getString("IP_ADDRESS"));
						masterSearch.setIsActive(rs.getString("IS_ACTIVE"));
						masterSearch.setIsDeleted(rs.getString("IS_DELETED"));
						masterSearch.setLastName(rs.getString("LAST_NAME"));
						masterSearch.setLocationCode(rs.getString("LOCATION_CODE"));
						masterSearch.setMaritalStatus(rs.getString("MARITAL_STATUS"));
						masterSearch.setMiddleName(rs.getString("MIDDLE_NAME"));
						masterSearch.setSex(rs.getString("SEX"));
						masterSearch.setStatus(rs.getString("STATUS"));
						masterSearch.setSrNumber2(rs.getString("SRNUMBER_MAIN"));
						modelDetails.add(masterSearch);
					}
					return modelDetails;
				}
				if(user.getLocationType().equalsIgnoreCase("ZO")) {

					sql = "select * from master_users where location_type in ('UO','ZO') and is_active = 'Y' and is_deleted = 'N' order by modifiedon desc";

					preparestatements = connection.prepareStatement(sql);
					//preparestatements.setString(1, emailId);
					rs = preparestatements.executeQuery();

					List<MasterUsersEntity> modelDetails = new ArrayList<>();

					while (rs.next()) {
						MasterUsersEntity masterSearch = new MasterUsersEntity();
						masterSearch.setMasterUserId(rs.getLong("MASTER_USER_ID"));
						masterSearch.setUserName(rs.getString("USERNAME"));
						masterSearch.setSrNumber(rs.getString("SRNUMBER"));
						masterSearch.setMobile(rs.getString("MOBILE"));
						masterSearch.setEmailId(rs.getString("EMAILID"));
						masterSearch.setCadre(rs.getString("CADRE"));
						masterSearch.setDesignation(rs.getString("DESIGNATION"));
						masterSearch.setClassName(rs.getString("CLASS"));
						masterSearch.setModifiedBy(rs.getString("MODIFIEDBY"));
						masterSearch.setModifiedOn(rs.getDate("MODIFIEDON"));
						masterSearch.setCreatedBy(rs.getString("CREATEDBY"));
						masterSearch.setCreatedOn(rs.getDate("CREATEDON"));
						masterSearch.setLocationType(rs.getString("LOCATION_TYPE"));
						masterSearch.setLocation(rs.getString("LOCATION"));
						masterSearch.setCategory(rs.getString("CATEGORY"));
						masterSearch.setDateOfAppointment(rs.getDate("DATEOFAPPOINTMENT"));
						masterSearch.setDateOfBirth(rs.getDate("DATEOFBIRTH"));
						masterSearch.setEmail(rs.getString("EMAIL"));
						masterSearch.setFirstName(rs.getString("FIRST_NAME"));
						masterSearch.setIpAddress(rs.getString("IP_ADDRESS"));
						masterSearch.setIsActive(rs.getString("IS_ACTIVE"));
						masterSearch.setIsDeleted(rs.getString("IS_DELETED"));
						masterSearch.setLastName(rs.getString("LAST_NAME"));
						masterSearch.setLocationCode(rs.getString("LOCATION_CODE"));
						masterSearch.setMaritalStatus(rs.getString("MARITAL_STATUS"));
						masterSearch.setMiddleName(rs.getString("MIDDLE_NAME"));
						masterSearch.setSex(rs.getString("SEX"));
						masterSearch.setStatus(rs.getString("STATUS"));
						modelDetails.add(masterSearch);

					}
					return modelDetails;
				}
				if(user.getLocationType().equalsIgnoreCase("CO")) {
					sql = "select * from master_users where location_type in ('CO','ZO') and is_active = 'Y' and is_deleted = 'N' order by modifiedon desc";

					preparestatements = connection.prepareStatement(sql);
					//preparestatements.setString(1, emailId);
					rs = preparestatements.executeQuery();

					List<MasterUsersEntity> modelDetails = new ArrayList<>();

					while (rs.next()) {
						MasterUsersEntity masterSearch = new MasterUsersEntity();
						masterSearch.setMasterUserId(rs.getLong("MASTER_USER_ID"));
						masterSearch.setUserName(rs.getString("USERNAME"));
						masterSearch.setSrNumber(rs.getString("SRNUMBER"));
						masterSearch.setMobile(rs.getString("MOBILE"));
						masterSearch.setEmailId(rs.getString("EMAILID"));
						masterSearch.setCadre(rs.getString("CADRE"));
						masterSearch.setDesignation(rs.getString("DESIGNATION"));
						masterSearch.setClassName(rs.getString("CLASS"));
						masterSearch.setModifiedBy(rs.getString("MODIFIEDBY"));
						masterSearch.setModifiedOn(rs.getDate("MODIFIEDON"));
						masterSearch.setCreatedBy(rs.getString("CREATEDBY"));
						masterSearch.setCreatedOn(rs.getDate("CREATEDON"));
						masterSearch.setLocationType(rs.getString("LOCATION_TYPE"));
						masterSearch.setLocation(rs.getString("LOCATION"));
						masterSearch.setCategory(rs.getString("CATEGORY"));
						masterSearch.setDateOfAppointment(rs.getDate("DATEOFAPPOINTMENT"));
						masterSearch.setDateOfBirth(rs.getDate("DATEOFBIRTH"));
						masterSearch.setEmail(rs.getString("EMAIL"));
						masterSearch.setFirstName(rs.getString("FIRST_NAME"));
						masterSearch.setIpAddress(rs.getString("IP_ADDRESS"));
						masterSearch.setIsActive(rs.getString("IS_ACTIVE"));
						masterSearch.setIsDeleted(rs.getString("IS_DELETED"));
						masterSearch.setLastName(rs.getString("LAST_NAME"));
						masterSearch.setLocationCode(rs.getString("LOCATION_CODE"));
						masterSearch.setMaritalStatus(rs.getString("MARITAL_STATUS"));
						masterSearch.setMiddleName(rs.getString("MIDDLE_NAME"));
						masterSearch.setSex(rs.getString("SEX"));
						masterSearch.setStatus(rs.getString("STATUS"));
						modelDetails.add(masterSearch);

					}
					return modelDetails;
				}
				/*if(user.getLocationType().equals("SO") || user.getLocationType().equals("Satelite")) {
					sql = "SELECT MS.* FROM MASTER_SATELLITE MS, MASTER_USERS MU\n"
							+" WHERE UO.UNIT_ID = MS.UNIT_ID AND MU.SRNUMBER =: srNumber";
				}*/
			}

		} 
		catch (Exception e) {
			logger.info("Exception : " + e.getMessage());
		} finally {
			if (rs!= null && !rs.isClosed()) {
				rs.close();
			}
			if (preparestatements != null) {
				preparestatements.close();
			}
			if(!connection.isClosed()) {
				connection.close();
			}
			preparestatements = null;
		}

		return null;
	}
	/*
	 * Description: This function is used for searching the data in MASTER USERS Module using the filters like EMAILID, SRNUMBER, USERNAME
	 * Table Name- MASTER USERS
	 * Author- Nandini R
	 */
		
	 public List<MasterUsersEntity> getSearchByUserName(String srNumber, String userName,String loggedInUsername) throws SQLException {
		logger.info("Start Service  master users Search");
		ResultSet rs = null;
		List<MasterUsersEntity> UsersSearchList = null;
		PreparedStatement preparestatements = null;
		String sqlmu = null;
		Connection connection = jdbcConnection.getConnection();

		try {
			
			sqlmu = "SELECT MU.MASTER_USER_ID, MU.SRNUMBER, MU.CADRE, MU.EMAILID,MU.DESIGNATION, MU.CLASS, MU.EMAIL, MU.USERNAME,MU.MOBILE,MU.LOCATION,MU.LOCATION_TYPE,MU.IS_ACTIVE \r\n" + 
					"															                  ,MU.IS_DELETED,MU.MODIFIEDBY,MU.MODIFIEDON,MU.SRNUMBER_MAIN,MU.FULL_NAME FROM MASTER_USERS MU \r\n" + 
					"															WHERE IS_ACTIVE = 'Y' and IS_DELETED = 'N' AND (MU.SRNUMBER_MAIN = :srNumber ) or (MU.USERNAME =:userName)\r\n" + 
					"                													order by mu.modifiedon desc";

			/*sqlmu = " SELECT MU.MASTER_USER_ID, MU.SRNUMBER, MU.CADRE, MU.EMAILID,MU.DESIGNATION, MU.CLASS, MU.EMAIL, MU.USERNAME,MU.MOBILE,MU.IS_ACTIVE,MU.IS_DELETED,MU.MODIFIEDBY,MU.MODIFIED FROM MASTER_USERS MU\r\n"
					+ "        		WHERE ((UPPER(MU.SRNUMBER) LIKE UPPER('%'||srNumber||'%') ) OR (srNumber ='')) \r\n"
					+ "        		          AND ((UPPER(MU.EMAIL) LIKE UPPER('%'||email||'%') ) OR (email =''))\r\n"
					+ "    			       AND ((UPPER(MU.USERNAME) LIKE UPPER('%'||userName||'%') ) OR (userName ='')) AND \r\n"
					+ "           ((UPPER(MU.LOCATION_CODE) LIKE UPPER('%'||locationCode||'%') ) OR (locationCode ='')) AND\r\n"
					+ "           ((UPPER(MU.LOCATION_TYPE) LIKE UPPER('%'||locationType||'%') ) OR (locationType =''))\r\n"
					+ "           order by mu.modifiedon desc";*/
			
			preparestatements = connection.prepareStatement(sqlmu);
			preparestatements.setString(1, srNumber);
			preparestatements.setString(2, userName);
			rs = preparestatements.executeQuery();

			UsersSearchList = new ArrayList<>();
			while (rs.next()) {
				MasterUsersEntity masterUsersEntity = new MasterUsersEntity();
				
				masterUsersEntity.setMasterUserId(rs.getLong("MASTER_USER_ID"));
				masterUsersEntity.setSrNumber(rs.getString("SRNUMBER"));
				masterUsersEntity.setCadre(rs.getString("CADRE"));
				masterUsersEntity.setClassName(rs.getString("CLASS"));
				masterUsersEntity.setDesignation(rs.getString("DESIGNATION"));
				masterUsersEntity.setEmail(rs.getString("EMAIL"));
				masterUsersEntity.setEmailId(rs.getString("EMAILID"));
				masterUsersEntity.setUserName(rs.getString("USERNAME"));
				masterUsersEntity.setLocation(rs.getString("LOCATION"));
				masterUsersEntity.setLocationType(rs.getString("LOCATION_TYPE"));
				masterUsersEntity.setMobile(rs.getString("MOBILE"));
				masterUsersEntity.setIsActive(rs.getString("IS_ACTIVE"));
				masterUsersEntity.setIsDeleted(rs.getString("IS_DELETED"));
				masterUsersEntity.setModifiedBy(rs.getString("MODIFIEDBY"));
				masterUsersEntity.setModifiedOn(rs.getDate("MODIFIEDON"));
				masterUsersEntity.setSrNumber2(rs.getString("SRNUMBER_MAIN"));
				masterUsersEntity.setFullName(rs.getString("FULL_NAME"));
				UsersSearchList.add(masterUsersEntity);
			}	

			logger.info("city code Search executed successfully.");
			return UsersSearchList;
		} catch (Exception e) {
			logger.info("exception occured." + e.getMessage());
			throw e;
		} finally {
			if (null != rs && !rs.isClosed()) {
				rs.close();
			}
			if (preparestatements != null) {
				preparestatements.close();
			}
			if(!connection.isClosed()) {
				connection.close();
			}
			preparestatements = null;
		}


	}



	
@Override
	public MasterUsersEntity getUserByUserName(String userName) throws Exception {
		logger.info("Start getUserBySrNumber"); 


		logger.info("locationId--"+userName);

		MasterUsersEntity objMasterUsersEntity = masterUserRepository.getUserByUserName(userName);

		logger.info("End getUserBySrNumber");

		return objMasterUsersEntity;
	}

/*@Override
public int findDisabledByUsername(String username) throws Exception {
	logger.info("Start getUserBySrNumber"); 


	logger.info("locationId--"+username);

	int updatedrecords = masterUserRepository.findDisabledByUsername(username);

	logger.info("End getUserBySrNumber");

	return updatedrecords;
}*/
	
@Override
public List<UsersModal> getUnitDetailsByUserName(String userName) throws Exception {

	List<UsersModal> usersList = new ArrayList<>();
	ResultSet rs = null;
	PreparedStatement preparestatements = null;
	String sql = null;
	Connection connection = jdbcConnection.getConnection();
	try {
		
		sql = "SELECT MU.LOCATION_TYPE, MU.LOCATION, MU.LOCATION_CODE, MU.IS_ACTIVE FROM MASTER_USERS MU\n"
				+ "							WHERE ((UPPER(MU.USERNAME) LIKE UPPER('%'||?||'%') ) OR (? =''))\n"
				+ "							order by mu.MODIFIEDON desc";
		preparestatements = connection.prepareStatement(sql);
		preparestatements.setString(1, userName);
		preparestatements.setString(2, userName);
		rs = preparestatements.executeQuery();

		while (rs.next()) {
			UsersModal  modal= new UsersModal();
			modal.setLocationType(rs.getString(1));
			modal.setLocation(rs.getString(2));
			modal.setLocationCode(rs.getString(3));
			modal.setIsActive(rs.getString(4));
			usersList.add(modal);
		}
		return usersList;
	}
	catch (Exception e) {
		logger.info("Exception"+e.getMessage());
	}
	finally {
		if (rs!= null && !rs.isClosed()) {
			rs.close();
		}
		if (preparestatements != null) {
			preparestatements.close();
		}
		if(!connection.isClosed()) {
			connection.close();
		}
		preparestatements = null;
	}

	return null;
}

@Override
public ResponseEntity<Map<String, Object>> updateLoggedInUserFlag(LoggedInUserModel loggedInUserModel) throws Exception {
	logger.info("Enter UserManagementService : logoutUser");
	try { Map<String, Object> response = new HashMap<String, Object>();
	if (loggedInUserModel == null) {
	response.put(Constant.STATUS, 201);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	} else {
		masterUserRepository.updateLoggedInUserFlag(loggedInUserModel.getUserName(),loggedInUserModel.getLoginFlag());
		response.put(Constant.STATUS, 200);
		response.put(Constant.MESSAGE, Constant.SUCCESS);		
	}
	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	} catch (Exception ex) {
		logger.info("Could not logout user due to : " + ex.getMessage());
		return null;
	}
	}

@Override
public List<MasterUsersEntity> getUserDetailsByUnitCode(String locationCode) throws Exception {
	logger.info("Start getUserDetailsByUnitCode"); 


	logger.info("locationCode--"+locationCode);

	List<MasterUsersEntity> objMasterUsersEntity = masterUserRepository.getUserDetailsByUnitCode(locationCode);

	logger.info("End getUserDetailsByUnitCode");

	return objMasterUsersEntity;
}

@Override
public ResponseEntity<Object> checkUsersExistsinePGSBySRnumber(String srNumber) throws Exception {
	 HashMap<String, Object> response = new HashMap<String, Object>();
	   Map<String, Object> response1 = new HashMap<String, Object>();
	   response.put(Constant.STATUS, 0);
	   response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	   String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	   logger.info("Enter UserManagementService : " + methodName);

	   try {
	   	   

	    	  if (srNumber == null) {
	            response.put(Constant.STATUS, 201);
	            response.put(Constant.MESSAGE, Constant.NOT_FOUND);
	        } 
	            else {
	            	
	            	 MasterUsersEntity   resCount = masterUserRepository.checkUsersExistsinePGSBySRnumber(srNumber);

	           	   if (resCount !=null) {
	           	   	response.put(Constant.STATUS, 200);
	           	   	response.put(Constant.MESSAGE, Constant.SRNUMBER_FOUND);
	           	   	response.put("DATA", Constant.SUCCESS);
	           	//   	return new ResponseEntity<Object>(response, HttpStatus.OK);
	            	
	           	   }
	            	//	EnableDisableUserEntity enableDisableUserEntity = enableDisableUserRepository.getInvalidAttemptCount(userName);
	           	 MasterUsersEntity portalMasterEntity = masterUserRepository.checkUsersExistsinePGSBySRnumberwithOtherUnit(srNumber);
	            		if(portalMasterEntity != null )
	            		{
	            			response.put(Constant.STATUS, 200);
	                	   	response.put(Constant.MESSAGE, Constant.UNIT_FOUND);
	                	   	response.put("DATA", Constant.SUCCESS);
	                //	   	return new ResponseEntity<Object>(response, HttpStatus.OK);
	            			
	            		}
	            		else
	            		{
	            			MasterUsersEntity enableDisableUserEntity = masterUserRepository.checkUsersExistsinePGSBySRnumberNotActive(srNumber);
	                		if(srNumber != null  )
	                		{
	                			response.put(Constant.STATUS, 200);
	                    	   	response.put(Constant.MESSAGE, Constant.DEACTIVE_USER_FOUND);
	                    	   	response.put("DATA", Constant.SUCCESS);
	                			}
		            			
		            	
			            		
	                		MasterUsersEntity checkUser = masterUserRepository.checkActiveStatusBySrNumber(srNumber);
	                		if(srNumber != null) {
	                			response.put(Constant.STATUS, 200);
	                    	   	response.put(Constant.MESSAGE, Constant.SRNUMBER_FOUND);
	                    	   	response.put("DATA", Constant.SUCCESS);
	                			}
			            		
			            
		    
		            		else
		            		{
		            			response.put(Constant.STATUS, 201);
		        	            response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		            		}
	            		
	            		}
	  
//	            		  return new ResponseEntity<Object>(response, HttpStatus.OK);
	            		  return new ResponseEntity<Object>(response, HttpStatus.OK);
	           
	            	}
	             }
        		
	 

	    catch (Exception exception) {
	   logger.info("Exception : " + exception.getMessage());
	   throw new Exception ("Internal Server Error");
	   }
	return null;
	   
	   //return null;
	   

	

}

@Override
public ResponseEntity<Map<String, Object>> getEPGSAndMPHUserDetails(List<String> userNames) throws Exception 
{
	 Map<String, Object> response = new HashMap<String, Object>();
	 //   Map<String, Object> response1 = new HashMap<String, Object>();
	    response.put(Constant.STATUS, 0);
	    response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	    String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	    LoggingUtil.logInfo(className, methodName, "Started");
	    JSONArray userDetailsArray = new JSONArray();
	    try {
	    	for (String username : userNames) 
	    	{
	    		PortalMasterEntity portalMasterEntity = portalMasterRepository.getMasterUserIdByUserName(username);
	    		if(portalMasterEntity == null)
	    		{
	    			MasterUsersEntity masterUsersEntity = masterUserRepository.checkUsersExistsinePGS(username);
	    			if(masterUsersEntity != null)
	    			{
		    			JSONObject userObj = new JSONObject();
		    			userObj.put("username", username);
		    			userObj.put("app_key", "epgs");
		    			ObjectMapper obj = new ObjectMapper();
		    			userObj.put("userDetails", obj.writeValueAsString(masterUsersEntity));
		    			if(userObj != null)
		    			{
		    				userDetailsArray.put(userObj);
		    			}
	    			}
	    		}
	    		else
	    		{
	    			JSONObject userObj = new JSONObject();
	    			userObj.put("username", username);
	    			userObj.put("app_key", "mph");
	    			ObjectMapper obj = new ObjectMapper();
	    			userObj.put("userDetails", obj.writeValueAsString(portalMasterEntity));
	    			if(userObj != null)
	    			{
	    				userDetailsArray.put(userObj);
	    			}
	    		}
	    			
			}
	    	
	    	if(userDetailsArray != null) 
	    	{
		    	response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", userDetailsArray.toString());
	    	}
	    	else 
	    	{
	    		response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
	        
	    	}
	    	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	    
	    }catch (Exception ex) {
	        logger.info("Could not find user details due to  : " + ex.getMessage());
	        ex.printStackTrace();
	    }
		return null;
}


@Override
public ResponseEntity<Map<String, Object>> findDisabledByUsername(MasterUsersEntity masterUserEntity) throws Exception {
logger.info("Enter UserManagementService : logoutUser");
try { Map<String, Object> response = new HashMap<String, Object>();
response.put(Constant.STATUS, 201);
response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG); 
if (masterUserEntity == null) {
response.put(Constant.STATUS, 201);
response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
} else {
	
	if(masterUserEntity.getLoggedInUsername().equalsIgnoreCase(masterUserEntity.getUserName())) {
		
		  response.put(Constant.STATUS, 201);
			response.put(Constant.MESSAGE, Constant.FAILED);
			response.put(Constant.DATA, "User cannot be disabled as logged in user and user being disabled are same.");
		
	}
	else {
	 int updateResponse = masterUserRepository.findDisabledByUsername(masterUserEntity.getUserName(),masterUserEntity.getLocationType(), masterUserEntity.getIsAdmin());
	 MasterUsersEntity masterUsersEntity = null; 
	 if(updateResponse == 1)
	 {
		 masterUsersEntity = new MasterUsersEntity();
		 masterUsersEntity = masterUserRepository.findDisableUserUsingSrNumber(masterUserEntity.getUserName());
		 
	 }
	
	if(masterUsersEntity != null)
	{
		if(masterUserEntity.getLocationType().equalsIgnoreCase("CO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
			
			//CoAdminEntity coAdmin =coAdminRepository.getCoAdminDetailByID(coAdminId);
        	//Long masterUserId = coAdmin.getMasterUserId();
        	//MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
        	if (masterUsersEntity.getIsActive().equalsIgnoreCase("N") & masterUsersEntity.getIsDeleted().equalsIgnoreCase("Y") &  masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
        	userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
        	coAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
        	} else {
        		userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
        	}
		}
        	
        	if(masterUserEntity.getLocationType().equalsIgnoreCase("ZO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
				
				//CoAdminEntity coAdmin =coAdminRepository.getCoAdminDetailByID(coAdminId);
            	//Long masterUserId = coAdmin.getMasterUserId();
            	//MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
            	if (masterUsersEntity.getIsActive().equalsIgnoreCase("N") & masterUsersEntity.getIsDeleted().equalsIgnoreCase("Y") &  masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
            	userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
            	zoAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
            	} else {
            		userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
            	}
            			
		}
         if(masterUserEntity.getLocationType().equalsIgnoreCase("UO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
				
				//CoAdminEntity coAdmin =coAdminRepository.getCoAdminDetailByID(coAdminId);
            	//Long masterUserId = coAdmin.getMasterUserId();
            	//MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
            	if (masterUsersEntity.getIsActive().equalsIgnoreCase("N") & masterUsersEntity.getIsDeleted().equalsIgnoreCase("Y") &  masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
            	userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
            	uoAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
            	} else {
            		userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
            	}	
		}
         if(masterUserEntity.getLocationType().equalsIgnoreCase("SO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
				
				//CoAdminEntity coAdmin =coAdminRepository.getCoAdminDetailByID(coAdminId);
            	//Long masterUserId = coAdmin.getMasterUserId();
            	//MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
            	if (masterUsersEntity.getIsActive().equalsIgnoreCase("N") & masterUsersEntity.getIsDeleted().equalsIgnoreCase("Y") &  masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
            	userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
            	soAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
            	} else {
            		userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
            	}	
		}
         
         if(masterUserEntity.getLocationType().equalsIgnoreCase("CO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
 			
 			//CoAdminEntity coAdmin =coAdminRepository.getCoAdminDetailByID(coAdminId);
         	//Long masterUserId = coAdmin.getMasterUserId();
         	//MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
         	if (masterUsersEntity.getIsActive().equalsIgnoreCase("N") & masterUsersEntity.getIsDeleted().equalsIgnoreCase("Y") &  masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
         	userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
         	coAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
         	} else {
         		userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
         	}
 		}
         	
         	if(masterUserEntity.getLocationType().equalsIgnoreCase("ZO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
 				
 				//CoAdminEntity coAdmin =coAdminRepository.getCoAdminDetailByID(coAdminId);
             	//Long masterUserId = coAdmin.getMasterUserId();
             	//MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
             	if (masterUsersEntity.getIsActive().equalsIgnoreCase("N") & masterUsersEntity.getIsDeleted().equalsIgnoreCase("Y") &  masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
             	userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
             	zoAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
             	} else {
             		userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
             	}
             			
 		}
          if(masterUserEntity.getLocationType().equalsIgnoreCase("UO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
 				
 				//CoAdminEntity coAdmin =coAdminRepository.getCoAdminDetailByID(coAdminId);
             	//Long masterUserId = coAdmin.getMasterUserId();
             	//MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
             	if (masterUsersEntity.getIsActive().equalsIgnoreCase("N") & masterUsersEntity.getIsDeleted().equalsIgnoreCase("Y") &  masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
             	userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
             	uoAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
             	} else {
             		userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
             	}	
 		}
          if(masterUserEntity.getLocationType().equalsIgnoreCase("SO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
 				
 				//CoAdminEntity coAdmin =coAdminRepository.getCoAdminDetailByID(coAdminId);
             	//Long masterUserId = coAdmin.getMasterUserId();
             	//MasterUsersEntity masteruserEntity =masterUsersRepository.getMasterUserDetail(masterUserId);
             	if (masterUsersEntity.getIsActive().equalsIgnoreCase("N") & masterUsersEntity.getIsDeleted().equalsIgnoreCase("Y") &  masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
             	userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
             	soAdminRepository.findAndDeleteByMasterUserId(masterUsersEntity.getMasterUserId());
             	} else {
             		userRoleTypeRepository.DeleteBymasterId(masterUsersEntity.getMasterUserId());
             	}	
 		}
	}
	MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUserEntity.getLoggedInUsername());
	MasterUsersEntity disbleUsersdetails = masterUserRepository.findDisableUserUsingSrNumber(masterUserEntity.getUserName());
    MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
    historyDetails.setAdminUserName(userDetails.getUserName());
    historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
    historyDetails.setAdminUserLocationType(userDetails.getLocationType());
    historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
    historyDetails.setUserName(masterUserEntity.getUserName());
    historyDetails.setUserSrnumber(disbleUsersdetails.getSrNumber2());
    historyDetails.setUserLocationType(masterUserEntity.getLocationType());
    historyDetails.setUserLocationCode(disbleUsersdetails.getLocationCode());
    historyDetails.setAdminUserActivityPerformed("User Disabled");
    historyDetails.setUserModule("NA");
    historyDetails.setIsUserAdmin("NA");
    historyDetails.setIsUserMarketingNonMarketing("NA");
    historyDetails.setUserOldRoles("NA");
    historyDetails.setUserNewRoles("NA");
    historyDetails.setCreatedBy(userDetails.getUserName());
    historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
    masterUsersHistoryDetailsRepository.save(historyDetails);
response.put(Constant.STATUS, 200);
response.put(Constant.MESSAGE, Constant.SUCCESS);
response.put("Data",   masterUsersEntity.getUserName() +" user has been disabled successfully");
}
}
return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
} 

catch (Exception ex) {
logger.info("Could not logout user due to : " + ex.getMessage());
return null;
}
}


@Override
public ResponseEntity<Map<String, Object>> findEnabledByUsername(MasterUsersEntity masterUserEntity) throws Exception {
logger.info("Enter UserManagementService : logoutUser");

Long maxAdminCoCount=commonConfigRepository.getValueFromCommonConfig("MAXADMINCOUNTCO");
Long maxAdminZoCount=commonConfigRepository.getValueFromCommonConfig("MAXADMINCOUNTZO");
Long maxAdminUoCount=commonConfigRepository.getValueFromCommonConfig("MAXADMINCOUNTUO");

try { Map<String, Object> response = new HashMap<String, Object>();
response.put(Constant.STATUS, 201);
response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG); 
if (masterUserEntity == null) {
	response.put(Constant.STATUS, 201);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
}
else {
	
	 //Check max admin count
	if(masterUserEntity != null && masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
        int count = masterUserRepository.getMaxAdminCount(masterUserEntity.getLocationCode());
        if(masterUserEntity.getLocationType().equalsIgnoreCase("CO")) {
        	if(count >= maxAdminCoCount) {
        		response.put(Constant.STATUS, 201);
        		response.put(Constant.MESSAGE, Constant.FAILED);
                response.put(Constant.DATA,"This user cannot be enabled as Admin as limit of max 10 admins has been reached.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
        	}
        }else if(masterUserEntity.getLocationType().equalsIgnoreCase("ZO")) {
        	if(count >= maxAdminZoCount) {
        		response.put(Constant.STATUS, 201);
        		response.put(Constant.MESSAGE, Constant.FAILED);
                response.put(Constant.DATA,"This user cannot be enabled as Admin as limit of max 4 admins has been reached.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
        	}
        }else if(masterUserEntity.getLocationType().equalsIgnoreCase("UO")) {
        	if(count >= maxAdminUoCount ) {
        		response.put(Constant.STATUS, 201);
        		response.put(Constant.MESSAGE, Constant.FAILED);
                response.put(Constant.DATA,"This user cannot be enabled as Admin as limit of max 4 admins has been reached.");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
        	}
        }
    }
	
	 if(masterUserEntity.getLoggedInUserLoacationType().equalsIgnoreCase("CO")) {
			
		 int updateResponse = masterUserRepository.findEnabledByUsername(masterUserEntity.getUserName(),masterUserEntity.getLocationType(), masterUserEntity.getIsAdmin());
		 int updateCategory=  masterUserRepository.updateCategoryByUsername(masterUserEntity.getCategory(),masterUserEntity.getUserName());
		 enableDisableUserRepository.deleteUnsuccessfulLoginUser(masterUserEntity.getUserName());
		 MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUserEntity.getLoggedInUsername());
		 MasterUsersEntity enabledUserDetails = masterUserRepository.findUserByUserName(masterUserEntity.getUserName());
         MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
         historyDetails.setAdminUserName(userDetails.getUserName());
         historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
         historyDetails.setAdminUserLocationType(userDetails.getLocationType());
         historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
         historyDetails.setUserName(masterUserEntity.getUserName());
         historyDetails.setUserSrnumber(enabledUserDetails.getSrNumber2());
         historyDetails.setUserLocationType(masterUserEntity.getLocationType());
         historyDetails.setUserLocationCode(enabledUserDetails.getLocationCode());
         historyDetails.setAdminUserActivityPerformed("User Enabled");
         historyDetails.setUserModule("NA");
         historyDetails.setCategory(masterUserEntity.getCategory());
         if(masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
          	historyDetails.setIsUserAdmin("ADMN");	
          }else if(masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
          	historyDetails.setIsUserAdmin("ORD");
         }
         historyDetails.setIsUserMarketingNonMarketing("NA");
         historyDetails.setUserOldRoles("NA");
         historyDetails.setUserNewRoles("NA");
         historyDetails.setCreatedBy(userDetails.getUserName());
         historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
         masterUsersHistoryDetailsRepository.save(historyDetails);
			response.put(Constant.STATUS, 200);
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put("Data",  masterUserEntity.getUserName() +  " user has been enabled sucessfully");
			
		
	}
	 
	 if(masterUserEntity.getLoggedInUserLoacationType().equalsIgnoreCase("ZO") &&masterUserEntity.getLocationType().equalsIgnoreCase("ZO")) {
			
		 int updateResponse = masterUserRepository.findEnabledByUsername(masterUserEntity.getUserName(),masterUserEntity.getLocationType(), masterUserEntity.getIsAdmin());
		 enableDisableUserRepository.deleteUnsuccessfulLoginUser(masterUserEntity.getUserName());
		 MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUserEntity.getLoggedInUsername());
		 MasterUsersEntity enabledUserDetails = masterUserRepository.findUserByUserName(masterUserEntity.getUserName());
         MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
         historyDetails.setAdminUserName(userDetails.getUserName());
         historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
         historyDetails.setAdminUserLocationType(userDetails.getLocationType());
         historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
         historyDetails.setUserName(masterUserEntity.getUserName());
         historyDetails.setUserSrnumber(enabledUserDetails.getSrNumber2());
         historyDetails.setUserLocationType(masterUserEntity.getLocationType());
         historyDetails.setUserLocationCode(enabledUserDetails.getLocationCode());
         historyDetails.setCategory(masterUserEntity.getCategory());
         historyDetails.setAdminUserActivityPerformed("User Enabled");
         historyDetails.setUserModule("NA");
         if(masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
           	historyDetails.setIsUserAdmin("ADMN");	
           }else if(masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
           	historyDetails.setIsUserAdmin("ORD");
          }
         historyDetails.setIsUserMarketingNonMarketing("NA");
         historyDetails.setUserOldRoles("NA");
         historyDetails.setUserNewRoles("NA");
         historyDetails.setCreatedBy(userDetails.getUserName());
         historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
         masterUsersHistoryDetailsRepository.save(historyDetails);
		 response.put(Constant.STATUS, 200);
		 response.put(Constant.MESSAGE, Constant.SUCCESS);
		 response.put("Data", masterUserEntity.getUserName() +  " user has been enabled sucessfully " );	
	}
	
    if(masterUserEntity.getLoggedInUserLoacationType().equalsIgnoreCase("ZO") &&masterUserEntity.getLocationType().equalsIgnoreCase("UO")) {
		
		if(masterUserEntity.getLocationType().equalsIgnoreCase("UO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
			
		
			int updateResponse = masterUserRepository.findEnabledByUsername(masterUserEntity.getUserName(),masterUserEntity.getLocationType(), masterUserEntity.getIsAdmin());
			enableDisableUserRepository.deleteUnsuccessfulLoginUser(masterUserEntity.getUserName());
			MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUserEntity.getLoggedInUsername());
			MasterUsersEntity enabledUserDetails = masterUserRepository.findUserByUserName(masterUserEntity.getUserName());
	         MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
	         historyDetails.setAdminUserName(userDetails.getUserName());
	         historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
	         historyDetails.setAdminUserLocationType(userDetails.getLocationType());
	         historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
	         historyDetails.setUserName(masterUserEntity.getUserName());
	         historyDetails.setUserSrnumber(enabledUserDetails.getSrNumber2());
	         historyDetails.setUserLocationType(masterUserEntity.getLocationType());
	         historyDetails.setUserLocationCode(enabledUserDetails.getLocationCode());
	         historyDetails.setCategory(masterUserEntity.getCategory());
	         historyDetails.setAdminUserActivityPerformed("User Enabled");
	         historyDetails.setUserModule("NA");
	         if(masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
	           	historyDetails.setIsUserAdmin("ADMN");	
	           }else if(masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
	           	historyDetails.setIsUserAdmin("ORD");
	          }
	         historyDetails.setIsUserMarketingNonMarketing("NA");
	         historyDetails.setUserOldRoles("NA");
	         historyDetails.setUserNewRoles("NA");
	         historyDetails.setCreatedBy(userDetails.getUserName());
	         historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
	         masterUsersHistoryDetailsRepository.save(historyDetails);
			response.put(Constant.STATUS, 200);
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put("Data", masterUserEntity.getUserName() +  " user has been enabled sucessfully " );
			
		}else {
			response.put(Constant.STATUS, 201);
			response.put(Constant.DATA,"Ordinary users can be enabled by their respective Admin only");
			response.put(Constant.MESSAGE, Constant.FAILED);
		}
	} if(masterUserEntity.getLoggedInUserLoacationType().equalsIgnoreCase("UO") &&masterUserEntity.getLocationType().equalsIgnoreCase("UO")) {
		
		int updateResponse = masterUserRepository.findEnabledByUsername(masterUserEntity.getUserName(),masterUserEntity.getLocationType(), masterUserEntity.getIsAdmin());
		enableDisableUserRepository.deleteUnsuccessfulLoginUser(masterUserEntity.getUserName());
		MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUserEntity.getLoggedInUsername());
		MasterUsersEntity enabledUserDetails = masterUserRepository.findUserByUserName(masterUserEntity.getUserName());
        MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
        historyDetails.setAdminUserName(userDetails.getUserName());
        historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
        historyDetails.setAdminUserLocationType(userDetails.getLocationType());
        historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
        historyDetails.setUserName(masterUserEntity.getUserName());
        historyDetails.setUserSrnumber(enabledUserDetails.getSrNumber2());
        historyDetails.setUserLocationType(masterUserEntity.getLocationType());
        historyDetails.setUserLocationCode(enabledUserDetails.getLocationCode());
        historyDetails.setCategory(masterUserEntity.getCategory());
        historyDetails.setAdminUserActivityPerformed("User Enabled");
        historyDetails.setUserModule("NA");
        if(masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
          	historyDetails.setIsUserAdmin("ADMN");	
          }else if(masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
          	historyDetails.setIsUserAdmin("ORD");
         }
        historyDetails.setIsUserMarketingNonMarketing("NA");
        historyDetails.setUserOldRoles("NA");
        historyDetails.setUserNewRoles("NA");
        historyDetails.setCreatedBy(userDetails.getUserName());
        historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
        masterUsersHistoryDetailsRepository.save(historyDetails);
		response.put(Constant.STATUS, 200);
		response.put(Constant.MESSAGE, Constant.SUCCESS);
		response.put("Data", masterUserEntity.getUserName() +  " user has been enabled sucessfully " );		
} if(masterUserEntity.getLoggedInUserLoacationType().equalsIgnoreCase("UO") &&masterUserEntity.getLocationType().equalsIgnoreCase("SO")) {
	
	if(masterUserEntity.getLocationType().equalsIgnoreCase("SO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
		int updateResponse = masterUserRepository.findEnabledByUsername(masterUserEntity.getUserName(),masterUserEntity.getLocationType(), masterUserEntity.getIsAdmin());
		enableDisableUserRepository.deleteUnsuccessfulLoginUser(masterUserEntity.getUserName());
		MasterUsersEntity userDetails = masterUserRepository.findUserByUserName(masterUserEntity.getLoggedInUsername());
		MasterUsersEntity enabledUserDetails = masterUserRepository.findUserByUserName(masterUserEntity.getUserName());
        MasterUsersHistoryDetailsEntity historyDetails = new MasterUsersHistoryDetailsEntity();
        historyDetails.setAdminUserName(userDetails.getUserName());
        historyDetails.setAdminUserSrnumber(userDetails.getSrNumber2());
        historyDetails.setAdminUserLocationType(userDetails.getLocationType());
        historyDetails.setAdminUserLocationCode(userDetails.getLocationCode());
        historyDetails.setUserName(masterUserEntity.getUserName());
        historyDetails.setUserSrnumber(enabledUserDetails.getSrNumber2());
        historyDetails.setUserLocationType(masterUserEntity.getLocationType());
        historyDetails.setUserLocationCode(enabledUserDetails.getLocationCode());
        historyDetails.setCategory(masterUserEntity.getCategory());
        historyDetails.setAdminUserActivityPerformed("User Enabled");
        historyDetails.setUserModule("NA");
        if(masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
          	historyDetails.setIsUserAdmin("ADMN");	
          }else if(masterUserEntity.getIsAdmin().equalsIgnoreCase("N")) {
          	historyDetails.setIsUserAdmin("ORD");
         }
        historyDetails.setIsUserMarketingNonMarketing("NA");
        historyDetails.setUserOldRoles("NA");
        historyDetails.setUserNewRoles("NA");
        historyDetails.setCreatedBy(userDetails.getUserName());
        historyDetails.setCreatedOn(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
        masterUsersHistoryDetailsRepository.save(historyDetails);
		response.put(Constant.STATUS, 200);
		response.put(Constant.MESSAGE, Constant.SUCCESS);
		response.put("Data", masterUserEntity.getUserName() +  " user has been enabled sucessfully " );
		
	}else {
		response.put(Constant.STATUS, 201);
		response.put(Constant.MESSAGE,"So users can have ordinary user aceess only");
	}
}
	
	//MasterUsersEntity masterUsersEntity = null;
	//if(updateResponse == 1)
	//{
	//	masterUsersEntity = new MasterUsersEntity();
	//	masterUsersEntity = masterUserRepository.findEnableUserUsingSrNumber(masterUserEntity.getUserName());
	//}
	

	
		Date date = new Date();
		if(masterUserEntity.getLocationType().equalsIgnoreCase("CO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
			MasterUsersEntity masterUsersEntity =  masterUserRepository.findEnableUserUsingSrNumber(masterUserEntity.getUserName());
			CoAdminEntity coEntity = coAdminRepository.findDuplicateByMasterUserId(masterUsersEntity.getMasterUserId());
		if(coEntity!=null) {
			
			response.put(Constant.STATUS, 200);
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put("Data", masterUserEntity.getUserName() +  "user has been enabled sucessfully " );
		} else {
			
			CoAdminEntity coAdmin = new CoAdminEntity();
			coAdmin.setMasterUserId(masterUsersEntity.getMasterUserId());
			coAdmin.setCoAdminName(masterUsersEntity.getLocation());
			coAdmin.setLocationCode(masterUsersEntity.getLocationCode());
			coAdmin.setIsActive(masterUsersEntity.getIsActive());
			coAdmin.setIsDeleted(masterUsersEntity.getIsDeleted());
			coAdmin.setCreatedBy(masterUsersEntity.getCreatedBy());
		    coAdmin.setCreatedOn(date);
		    coAdmin.setModifiedBy(masterUsersEntity.getModifiedBy());
		    coAdmin.setModifiedOn(date);
		    CoAdminEntity CoAdmin = coAdminRepository.save(coAdmin);
		    UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
			userRoleTypeMappingEntity.setAppModuleId(1L);;
			userRoleTypeMappingEntity.setRoleTypeId(11L);
			userRoleTypeMappingEntity.setMasterUserId(coAdmin.getMasterUserId());
			userRoleTypeMappingEntity.setIsActive("Y");
			userRoleTypeMappingEntity.setIsDeleted("N");
		//	userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
			userRoleTypeMappingEntity.setModifiedBy(masterUsersEntity.getModifiedBy());
			userRoleTypeMappingEntity.setModifiedOn(date);
			userRoleTypeMappingEntity.setCreatedBy(masterUsersEntity.getCreatedBy());
			userRoleTypeMappingEntity.setCreatedOn(date);
			userRoleTypeRepository.save(userRoleTypeMappingEntity);
			response.put(Constant.STATUS, 200);
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put("Data", masterUserEntity.getUserName() +  " user has been enabled sucessfully " );
			
		}
		}
		 if(masterUserEntity.getLocationType().equalsIgnoreCase("ZO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
			
			 MasterUsersEntity masterUsersEntity =  masterUserRepository.findEnableUserUsingSrNumber(masterUserEntity.getUserName());
			 ZOAdminEntity zoEntity = zoAdminRepository.findDuplicateByMasterUserId(masterUsersEntity.getMasterUserId());
			 
			 if(zoEntity!=null) {
				 response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", masterUserEntity.getUserName() +  "user has been enabled sucessfully " );
			 } else {
				 
			 
			 ZOAdminEntity zoAdmin = new ZOAdminEntity();
			
			zoAdmin.setMasterUserId(masterUsersEntity.getMasterUserId());
			zoAdmin.setLocation(masterUsersEntity.getLocation());
			zoAdmin.setLocationCode(masterUsersEntity.getLocationCode());
			zoAdmin.setIsActive(masterUsersEntity.getIsActive());
			zoAdmin.setIsDeleted(masterUsersEntity.getIsDeleted());
			zoAdmin.setCreatedBy(masterUsersEntity.getCreatedBy());
		    zoAdmin.setCreatedOn(date);
		    zoAdmin.setModifiedBy(masterUsersEntity.getModifiedBy());
		    zoAdmin.setModifiedOn(date);
		    ZOAdminEntity ZoAdmin = zoAdminRepository.save(zoAdmin);
		    UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
			userRoleTypeMappingEntity.setAppModuleId(1L);;
			userRoleTypeMappingEntity.setRoleTypeId(11L);
			userRoleTypeMappingEntity.setMasterUserId(zoAdmin.getMasterUserId());
			userRoleTypeMappingEntity.setIsActive("Y");
			userRoleTypeMappingEntity.setIsDeleted("N");
		//	userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
			userRoleTypeMappingEntity.setModifiedBy(masterUsersEntity.getModifiedBy());
			userRoleTypeMappingEntity.setModifiedOn(date);
			userRoleTypeMappingEntity.setCreatedBy(masterUsersEntity.getCreatedBy());
			userRoleTypeMappingEntity.setCreatedOn(date);
			userRoleTypeRepository.save(userRoleTypeMappingEntity);
			response.put(Constant.STATUS, 200);
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put("Data", masterUserEntity.getUserName() +  " user has been enabled sucessfully " );
		}
		 }
		  if(masterUserEntity.getLocationType().equalsIgnoreCase("UO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
			  
			  MasterUsersEntity masterUsersEntity =  masterUserRepository.findEnableUserUsingSrNumber(masterUserEntity.getUserName());
			 UOAdminEntity uoEntity = uoAdminRepository.findDuplicateByMasterUserId(masterUsersEntity.getMasterUserId());
			
		if(uoEntity!=null) {
			response.put(Constant.STATUS, 200);
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put("Data", masterUserEntity.getUserName() +  "user has been enabled sucessfully " );
		} else {
			  UOAdminEntity uoAdmin = new UOAdminEntity();
				uoAdmin.setLocation(masterUsersEntity.getLocation());
				uoAdmin.setLocationCode(masterUsersEntity.getLocationCode());
				uoAdmin.setLocationType(masterUsersEntity.getLocationType());
				uoAdmin.setMasterUserId(masterUsersEntity.getMasterUserId());
				uoAdmin.setIsActive(masterUsersEntity.getIsActive());
				uoAdmin.setIsDeleted(masterUsersEntity.getIsDeleted());
				uoAdmin.setModifiedOn(date);
				uoAdmin.setModifiedBy(masterUsersEntity.getModifiedBy());
				uoAdmin.setCreatedBy(masterUsersEntity.getCreatedBy());
				uoAdmin.setCreatedOn(date);
				UOAdminEntity UoAdmin = uoAdminRepository.save(uoAdmin);
				UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
				userRoleTypeMappingEntity.setAppModuleId(1L);;
				userRoleTypeMappingEntity.setRoleTypeId(11L);
				userRoleTypeMappingEntity.setMasterUserId(uoAdmin.getMasterUserId());
				userRoleTypeMappingEntity.setIsActive("Y");
				userRoleTypeMappingEntity.setIsDeleted("N");
			//	userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
				userRoleTypeMappingEntity.setModifiedBy(masterUsersEntity.getModifiedBy());
				userRoleTypeMappingEntity.setModifiedOn(date);
				userRoleTypeMappingEntity.setCreatedBy(masterUsersEntity.getCreatedBy());
				userRoleTypeMappingEntity.setCreatedOn(date);
				userRoleTypeRepository.save(userRoleTypeMappingEntity);
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", masterUserEntity.getUserName() +  " user has been enabled sucessfully " );
		  }
		  }
		  
		  if (masterUserEntity.getLocationType().equalsIgnoreCase("SO") && masterUserEntity.getIsAdmin().equalsIgnoreCase("Y")) {
			  
			  MasterUsersEntity masterUsersEntity =  masterUserRepository.findEnableUserUsingSrNumber(masterUserEntity.getUserName());
			  SOAdminEntity soEntity = soAdminRepository.findDuplicateByMasterUserId(masterUsersEntity.getMasterUserId());
			  if(soEntity!=null) {
				  response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", masterUserEntity.getUserName() +  "user has been enabled sucessfully " );
			  } else {
			  SOAdminEntity soAdmin = new SOAdminEntity();

				soAdmin.setLocation(masterUserEntity.getLocation());
				soAdmin.setLocationCode(masterUsersEntity.getLocationCode());
				soAdmin.setLocationType(masterUsersEntity.getLocationType());
				soAdmin.setMasterUserId(masterUsersEntity.getMasterUserId());
				soAdmin.setIsActive(masterUsersEntity.getIsActive());
				soAdmin.setIsDeleted(masterUsersEntity.getIsDeleted());
				soAdmin.setModifiedOn(date);
				soAdmin.setModifiedBy(masterUsersEntity.getModifiedBy());
				soAdmin.setCreatedBy(masterUsersEntity.getCreatedBy());
				soAdmin.setCreatedOn(date);
				 SOAdminEntity SoAdmin = soAdminRepository.save(soAdmin);
				UserRoleTypeMappingEntity userRoleTypeMappingEntity = new UserRoleTypeMappingEntity();
				userRoleTypeMappingEntity.setAppModuleId(1L);;
				userRoleTypeMappingEntity.setRoleTypeId(11L);
				userRoleTypeMappingEntity.setMasterUserId(soAdmin.getMasterUserId());
				userRoleTypeMappingEntity.setIsActive("Y");
				userRoleTypeMappingEntity.setIsDeleted("N");
			//	userRoleTypeMappingEntity.setUserRoleTypeMappingId(userRoleTypeMappingEntity.getUserRoleTypeMappingId());
				userRoleTypeMappingEntity.setModifiedBy(masterUsersEntity.getModifiedBy());
				userRoleTypeMappingEntity.setModifiedOn(date);
				userRoleTypeMappingEntity.setCreatedBy(masterUsersEntity.getCreatedBy());
				userRoleTypeMappingEntity.setCreatedOn(date);
				userRoleTypeRepository.save(userRoleTypeMappingEntity);
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", masterUserEntity.getUserName() +  " user has been enabled sucessfully " );
		  }
		  } 
	}
return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 

}
catch (Exception ex) {
	ex.printStackTrace();
logger.info("Could not logout user due to : " + ex.getMessage());

}
return null;
}

public String getZonalCodeForUnitCode(String unitCode) throws Exception{
	String zoneCode = "";
	try {
		 zoneCode = zonalOfficeRepository.getZoneForUnitCode(unitCode);
	}catch (Exception ex) {
		logger.info("Exception occurred while getting Zone code for unit code..  " + ex.getMessage());
		return null;
	}
	return zoneCode;
}
	  
public int checkIfUnitCodeIsValid(String unitCode) throws Exception{
	int count = 0;
	try {
		count = unitEntityRepository.checkIfUnitCodeIsValid(unitCode);
	}catch (Exception ex) {
		logger.info("Exception occurred while checking validity of unit code..  " + ex.getMessage());
		return 0;
	}
	return count;
}

public int checkIfZonalCodeIsValid(String zonalCode) throws Exception{
	int count = 0;
	try {
		count = zonalOfficeRepository.checkIfZonalCodeIsValid(zonalCode);
	}catch (Exception ex) {
		logger.info("Exception occurred while checking validity of unit code..  " + ex.getMessage());
		return 0;
	}
	return count;
}

@Override
public ResponseEntity<Map<String, Object>> getValidateSrNumber(String srNumber, String isActiveFlag) throws Exception {
	 HashMap<String, Object> response = new HashMap<String, Object>();
	  // response.put(Constant.STATUS, 0);
	 //  response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	   String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	   logger.debug("Enter UserManagementService : " + methodName);

	   try {

	   int resCount = masterUserRepository.getvalidateSrNumber(srNumber, isActiveFlag);

	   if (resCount >= 1) {
	   	
	   	response.put(Constant.STATUS, 201);
	   	response.put("Data", Constant.DUPLICATE_ENTRY);
	   	response.put(Constant.MESSAGE, "User Already Added in the System");
	  // 	response.put("Count Of Users", resCount);
	  

	   } else {
	   	response.put(Constant.STATUS, 200);
	  // 	response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
	   	response.put("Data", Constant.SUCCESS);
	   	response.put(Constant.MESSAGE, "Unique SR Number found");
	   
	   }
	   return ResponseEntity.ok().body(response);

	   } catch (Exception exception) {
	   logger.debug("Exception : " + exception.getMessage());
	   throw new Exception ("Internal Server Error");
	   }
}

@Override
public ResponseEntity<Map<String, Object>> checkActiveSrNumber(String srNumber) throws Exception {
	 HashMap<String, Object> response = new HashMap<String, Object>();
	  // response.put(Constant.STATUS, 0);
	 //  response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	   String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	   logger.debug("Enter UserManagementService : " + methodName);

	   try {
		   
       MasterUsersEntity mstEntity = masterUserRepository.getAllMasterUserDetail(srNumber);
	   int resCount = masterUserRepository.checkActiveSrNumber(srNumber);

	   if (resCount >= 1) {
	   	
		   response.put(Constant.STATUS,200);
	   	response.put("Data", mstEntity.getUserName());
	   	response.put(Constant.STATUS, Constant.SUCCESS);
	   	
	  // 	response.put("Count Of Users", resCount);
	  

	   } else {
			response.put(Constant.STATUS, 201);
	  // 	response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
	   	response.put("Data", "No active user is present for that SR No.");
		response.put(Constant.STATUS, Constant.FAILED);
	   }
	   return ResponseEntity.ok().body(response);

	   } catch (Exception exception) {
	   logger.debug("Exception : " + exception.getMessage());
	   throw new Exception ("Internal Server Error");
	   }
}

@Override
public ResponseEntity<Map<String, Object>> getUserDetailsByEmployeeCode(String srNumber) throws Exception {
	 HashMap<String, Object> response = new HashMap<String, Object>();
	  // response.put(Constant.STATUS, 0);
	 //  response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	   String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	   logger.debug("Enter UserManagementService : " + methodName);

	   try {

		   MasterUsersEntity   resCount = masterUserRepository.getAllMasterUserDetail(srNumber);

	   if (resCount != null) {
	   	
	   	response.put(Constant.STATUS, 200);
	   	response.put("Data", Constant.SUCCESS);
	   	response.put("User Details", resCount);
	  

	   } else {
	   	response.put(Constant.STATUS, 200);
	  // 	response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
	   	response.put("Data", Constant.NO_DATA_FOUND);
	   
	   }
	   return ResponseEntity.ok().body(response);

	   } catch (Exception exception) {
	   logger.debug("Exception : " + exception.getMessage());
	   throw new Exception ("Internal Server Error");
	   }
}


@Override
public ResponseEntity<Map<String, Object>> getUserDetailsBasedOnUnit(String userName, String unitCode) throws Exception 
{
	 Map<String, Object> response = new HashMap<String, Object>();
	    response.put(Constant.STATUS, 0);
	    response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	    String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	    LoggingUtil.logInfo(className, methodName, "Started");
	    try {
	    	
	    	if(!userName.isEmpty() && !unitCode.isEmpty())
	    	{
	    		List<Map<String, Object>> list = masterUserRepository.getUserDetailsBasedOnUnitCode(userName, unitCode);
	    		if(list.size() > 0)
	    		{
	    			response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", list.get(0));
	    		}
	    		else
	    		{
	    			response.put(Constant.STATUS, 201);
					response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
	    		}
	    		System.out.println(list.size());
	    	}
	    	else
	    	{
	    		response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
	    	}
	    	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	    
	    }catch (Exception ex) {
	        logger.info("Could not find user details due to  : " + ex.getMessage());
	        ex.printStackTrace();
	    }
		return null;
}

@Override
public ResponseEntity<Map<String, Object>> getLoginFlagStatus(String srNumber) throws Exception {
	 HashMap<String, Object> response = new HashMap<String, Object>();
	  // response.put(Constant.STATUS, 0);
	 //  response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	   String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	   logger.debug("Enter UserManagementService : " + methodName);

	   try {

		   String loggedInFlag = masterUserRepository.getLoginFlagStatus(srNumber);

	   if (loggedInFlag != null) {
	   	
	   	response.put(Constant.STATUS, 200);
	   	response.put(Constant.MESSAGE, Constant.SUCCESS);
	   	response.put("Data", loggedInFlag);
	  

	   } else {
	   	response.put(Constant.STATUS, 201);
	   	response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
	   }
	   return ResponseEntity.ok().body(response);

	   } catch (Exception exception) {
	   logger.debug("Exception : " + exception.getMessage());
	   throw new Exception ("Internal Server Error");
	   }
}

@Override
public List<MasterUsersEntity> getAllUsersByParticularLocationType(String locationType) throws Exception {
	logger.info("Start Service  master users Search");
	ResultSet rs = null;
	List<MasterUsersEntity> UsersSearchList = null;
	PreparedStatement preparestatements = null;
	String sqlmu = null;
	Connection connection = jdbcConnection.getConnection();

	try {
		
		sqlmu = "select * from master_users where lower(location_type) = lower(:locationType) and is_active = 'Y' and is_deleted = 'N'  order by modifiedon desc";

		/*sqlmu = " SELECT MU.MASTER_USER_ID, MU.SRNUMBER, MU.CADRE, MU.EMAILID,MU.DESIGNATION, MU.CLASS, MU.EMAIL, MU.USERNAME,MU.MOBILE,MU.IS_ACTIVE,MU.IS_DELETED,MU.MODIFIEDBY,MU.MODIFIED FROM MASTER_USERS MU\r\n"
				+ "        		WHERE ((UPPER(MU.SRNUMBER) LIKE UPPER('%'||srNumber||'%') ) OR (srNumber ='')) \r\n"
				+ "        		          AND ((UPPER(MU.EMAIL) LIKE UPPER('%'||email||'%') ) OR (email =''))\r\n"
				+ "    			       AND ((UPPER(MU.USERNAME) LIKE UPPER('%'||userName||'%') ) OR (userName ='')) AND \r\n"
				+ "           ((UPPER(MU.LOCATION_CODE) LIKE UPPER('%'||locationCode||'%') ) OR (locationCode ='')) AND\r\n"
				+ "           ((UPPER(MU.LOCATION_TYPE) LIKE UPPER('%'||locationType||'%') ) OR (locationType =''))\r\n"
				+ "           order by mu.modifiedon desc";*/
		
		preparestatements = connection.prepareStatement(sqlmu);
		preparestatements.setString(1, locationType);
		//preparestatements.setString(2, srNumber);
		//preparestatements.setString(3, userName);
		//preparestatements.setString(4, userName);
		rs = preparestatements.executeQuery();

		UsersSearchList = new ArrayList<>();
		while (rs.next()) {
			
			MasterUsersEntity masterSearch = new MasterUsersEntity();
			masterSearch.setMasterUserId(rs.getLong("MASTER_USER_ID"));
			masterSearch.setUserName(rs.getString("USERNAME"));
			masterSearch.setSrNumber(rs.getString("SRNUMBER"));
			masterSearch.setMobile(rs.getString("MOBILE"));
			masterSearch.setEmailId(rs.getString("EMAILID"));
			masterSearch.setCadre(rs.getString("CADRE"));
			masterSearch.setDesignation(rs.getString("DESIGNATION"));
			masterSearch.setClassName(rs.getString("CLASS"));
			masterSearch.setModifiedBy(rs.getString("MODIFIEDBY"));
			masterSearch.setModifiedOn(rs.getDate("MODIFIEDON"));
			masterSearch.setCreatedBy(rs.getString("CREATEDBY"));
			masterSearch.setCreatedOn(rs.getDate("CREATEDON"));
			masterSearch.setLocationType(rs.getString("LOCATION_TYPE"));
			masterSearch.setLocation(rs.getString("LOCATION"));
			masterSearch.setCategory(rs.getString("CATEGORY"));
			masterSearch.setDateOfAppointment(rs.getDate("DATEOFAPPOINTMENT"));
			masterSearch.setDateOfBirth(rs.getDate("DATEOFBIRTH"));
			masterSearch.setEmail(rs.getString("EMAIL"));
			masterSearch.setFirstName(rs.getString("FIRST_NAME"));
			masterSearch.setIpAddress(rs.getString("IP_ADDRESS"));
			masterSearch.setIsActive(rs.getString("IS_ACTIVE"));
			masterSearch.setIsDeleted(rs.getString("IS_DELETED"));
			masterSearch.setLastName(rs.getString("LAST_NAME"));
			masterSearch.setLocationCode(rs.getString("LOCATION_CODE"));
			masterSearch.setMaritalStatus(rs.getString("MARITAL_STATUS"));
			masterSearch.setMiddleName(rs.getString("MIDDLE_NAME"));
			masterSearch.setSex(rs.getString("SEX"));
			masterSearch.setStatus(rs.getString("STATUS"));
			masterSearch.setSrNumber2(rs.getString("SRNUMBER_MAIN"));
			UsersSearchList.add(masterSearch);
		}	

		logger.info("city code Search executed successfully.");
		return UsersSearchList;
	} catch (Exception e) {
		logger.info("exception occured." + e.getMessage());
		throw e;
	} finally {
		if (null != rs && !rs.isClosed()) {
			rs.close();
		}
		if (preparestatements != null) {
			preparestatements.close();
		}
		if(!connection.isClosed()) {
			connection.close();
		}
		preparestatements = null;
	}


}

@Override
public List<MasterUsersEntity> getMasterUserSearchBySrNumber(String srNumber) throws SQLException {
	logger.info("Start Service  master users Search");
	ResultSet rs = null;
	List<MasterUsersEntity> UsersSearchList = null;
	PreparedStatement preparestatements = null;
	String sqlmu = null;
	Connection connection = jdbcConnection.getConnection();

	try {
		
		sqlmu = "SELECT MU.* FROM MASTER_USERS MU WHERE MU.SRNUMBER_MAIN = :srNumber";

		
		preparestatements = connection.prepareStatement(sqlmu);
		preparestatements.setString(1, srNumber);
		//preparestatements.setString(2, srNumber);
		//preparestatements.setString(3, userName);
		//preparestatements.setString(4, userName);
		rs = preparestatements.executeQuery();

		UsersSearchList = new ArrayList<>();
		while (rs.next()) {
			
			MasterUsersEntity masterSearch = new MasterUsersEntity();
			masterSearch.setMasterUserId(rs.getLong("MASTER_USER_ID"));
			masterSearch.setUserName(rs.getString("USERNAME"));
			masterSearch.setSrNumber(rs.getString("SRNUMBER"));
			masterSearch.setMobile(rs.getString("MOBILE"));
			masterSearch.setEmailId(rs.getString("EMAILID"));
			masterSearch.setCadre(rs.getString("CADRE"));
			masterSearch.setDesignation(rs.getString("DESIGNATION"));
			masterSearch.setClassName(rs.getString("CLASS"));
			masterSearch.setModifiedBy(rs.getString("MODIFIEDBY"));
			masterSearch.setModifiedOn(rs.getDate("MODIFIEDON"));
			masterSearch.setCreatedBy(rs.getString("CREATEDBY"));
			masterSearch.setCreatedOn(rs.getDate("CREATEDON"));
			masterSearch.setLocationType(rs.getString("LOCATION_TYPE"));
			masterSearch.setLocation(rs.getString("LOCATION"));
			masterSearch.setCategory(rs.getString("CATEGORY"));
			masterSearch.setDateOfAppointment(rs.getDate("DATEOFAPPOINTMENT"));
			masterSearch.setDateOfBirth(rs.getDate("DATEOFBIRTH"));
			masterSearch.setEmail(rs.getString("EMAIL"));
			masterSearch.setFirstName(rs.getString("FIRST_NAME"));
			masterSearch.setFullName(rs.getString("FULL_NAME"));
			masterSearch.setIpAddress(rs.getString("IP_ADDRESS"));
			masterSearch.setIsActive(rs.getString("IS_ACTIVE"));
			masterSearch.setIsDeleted(rs.getString("IS_DELETED"));
			masterSearch.setIsAdmin(rs.getString("ISADMIN"));
			masterSearch.setLastName(rs.getString("LAST_NAME"));
			masterSearch.setLocationCode(rs.getString("LOCATION_CODE"));
			masterSearch.setMaritalStatus(rs.getString("MARITAL_STATUS"));
			masterSearch.setMiddleName(rs.getString("MIDDLE_NAME"));
			masterSearch.setSex(rs.getString("SEX"));
			masterSearch.setStatus(rs.getString("STATUS"));
			masterSearch.setIsLoggedIn(rs.getString("IS_LOGGEDIN"));
			masterSearch.setSrNumber2(rs.getString("SRNUMBER_MAIN"));
			UsersSearchList.add(masterSearch);
			if((UsersSearchList.get(0).getIsActive().equalsIgnoreCase("Y") && UsersSearchList.get(0).getIsDeleted().equalsIgnoreCase("N") && UsersSearchList.get(0).getIsAdmin().equalsIgnoreCase("N"))) {
				UsersSearchList.get(0).setTypeOfUser("ActiveUsers");
			}
			else if((UsersSearchList.get(0).getIsActive().equalsIgnoreCase("N") && UsersSearchList.get(0).getIsDeleted().equalsIgnoreCase("Y") && UsersSearchList.get(0).getIsAdmin().equalsIgnoreCase("N"))) {
				UsersSearchList.get(0).setTypeOfUser("DisableUsers");
			}
			else if((UsersSearchList.get(0).getIsActive().equalsIgnoreCase("Y") && UsersSearchList.get(0).getIsDeleted().equalsIgnoreCase("N") && UsersSearchList.get(0).getIsAdmin().equalsIgnoreCase("Y"))) {
				UsersSearchList.get(0).setTypeOfUser("ActiveAdminUsers");
			}
			else if((UsersSearchList.get(0).getIsActive().equalsIgnoreCase("N") && UsersSearchList.get(0).getIsDeleted().equalsIgnoreCase("Y") && UsersSearchList.get(0).getIsAdmin().equalsIgnoreCase("Y"))) {
				UsersSearchList.get(0).setTypeOfUser("DisableAdminUsers");
			}
		}	

		logger.info("city code Search executed successfully.");
		return UsersSearchList;
	} catch (Exception e) {
		logger.info("exception occured." + e.getMessage());
		throw e;
	} finally {
		if (null != rs && !rs.isClosed()) {
			rs.close();
		}
		if (preparestatements != null) {
			preparestatements.close();
		}
		if(!connection.isClosed()) {
			connection.close();
		}
		preparestatements = null;
	}

}

@Override
public List<UserDetailsResponse> getUserDetailsForConcurreciaAPIBySrNumber(List<String> srNumbers) throws Exception 
{
	final String uri = "http://10.240.3.146:80/HRM_WebService/service/WSJ_51_00003/post";
	List<UserDetailsResponse> detailsResponses = new ArrayList();
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);

	for(String srNumber : srNumbers) {
		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("srnumber", srNumber);
		requestBody.put("emailId", "");
		HttpEntity formEntity = new HttpEntity<Map<String, String>>(requestBody, headers);
		ObjectMapper mapper = new ObjectMapper();
		try {
			logger.info("ENTERING THE REST TEMPLATE");
			ResponseEntity<String> response = restTemplate.postForEntity(uri, formEntity, String.class);
			logger.info("RESPONSE FROM THE REST TEMPLATE");
			if (response.getStatusCode() == HttpStatus.OK && response.getStatusCodeValue() == 200) {
				UserDetailsResponse accessResponse = new UserDetailsResponse();
				accessResponse = mapper.readValue(response.getBody(), UserDetailsResponse.class);
				detailsResponses.add(accessResponse);
			} 
		} catch (ResourceAccessException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	return detailsResponses;
}

@Override
public List<UserDetailsResponse> getUserDetailsForConcurreciaAPIByEmail(List<String> emails) throws Exception 
{
	final String uri = "http://10.240.3.146:80/HRM_WebService/service/WSJ_51_00003/post";
	List<UserDetailsResponse> detailsResponses = new ArrayList();
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
 
	for(String email : emails) {
		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("srnumber", "");
		requestBody.put("emailId", email);
		HttpEntity formEntity = new HttpEntity<Map<String, String>>(requestBody, headers);
		ObjectMapper mapper = new ObjectMapper();
		try {
			logger.info("ENTERING THE REST TEMPLATE");
			ResponseEntity<String> response = restTemplate.postForEntity(uri, formEntity, String.class);
			logger.info("RESPONSE FROM THE REST TEMPLATE");
			if (response.getStatusCode() == HttpStatus.OK && response.getStatusCodeValue() == 200) {
				UserDetailsResponse accessResponse = new UserDetailsResponse();
				accessResponse = mapper.readValue(response.getBody(), UserDetailsResponse.class);
				detailsResponses.add(accessResponse);
			} 
		} catch (ResourceAccessException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	return detailsResponses;
}

/*
 * @Override public ResponseEntity<Map<String, Object>>
 * syncUserDetailsWithConcurrecia(String srnumber) throws Exception {
 * logger.info("Entering syncUserDetailsWithConcurrecia method."); final String
 * uri = "http://10.240.3.146:80/HRM_WebService/service/WSJ_51_00003/post";
 * HashMap<String, Object> response = new HashMap<String, Object>();
 * List<UserDetailsResponse> detailsResponses = new ArrayList(); HttpHeaders
 * headers = new HttpHeaders();
 * headers.setContentType(MediaType.APPLICATION_JSON);
 * 
 * Map<String, String> requestBody = new HashMap<String, String>();
 * requestBody.put("srnumber", srnumber); requestBody.put("emailId", "");
 * HttpEntity formEntity = new HttpEntity<Map<String, String>>(requestBody,
 * headers); ObjectMapper mapper = new ObjectMapper(); try {
 * logger.info("ENTERING THE REST TEMPLATE"); ResponseEntity<String>
 * responseFormAPI = restTemplate.postForEntity(uri, formEntity, String.class);
 * logger.info("RESPONSE FROM THE REST TEMPLATE"); if
 * (responseFormAPI.getStatusCode() == HttpStatus.OK &&
 * responseFormAPI.getStatusCodeValue() == 200) { UserDetailsResponse
 * accessResponse = new UserDetailsResponse(); accessResponse =
 * mapper.readValue(responseFormAPI.getBody(), UserDetailsResponse.class);
 * 
 * MasterUsersEntity masterUsersEntity =
 * masterUserRepository.findBySrNumberMain(srnumber);
 * masterUsersEntity.setDesignation(accessResponse.getDesignation());
 * masterUsersEntity.setMobile(accessResponse.getMobileNumber());
 * masterUsersEntity.setFirstName(accessResponse.getFirstName());
 * masterUsersEntity.setMiddleName(accessResponse.getMiddleName());
 * masterUsersEntity.setLastName(accessResponse.getLastName());
 * masterUsersEntity.setClassName(String.valueOf(accessResponse.getClassCode()))
 * ; masterUsersEntity.setFullName(accessResponse.getFirstName() +" " +
 * accessResponse.getMiddleName() + " " + accessResponse.getLastName());
 * masterUsersEntity.setLocationCode(accessResponse.getUnitCode());
 * if(accessResponse.getUnitCode().startsWith("G") ||
 * accessResponse.getUnitCode().startsWith("g")) { UnitEntity unitEntity =
 * unitEntityRepository.getServiceDetailsByUnitCode(accessResponse.getUnitCode()
 * ); if(unitEntity != null) {
 * masterUsersEntity.setLocation(unitEntity.getDescription());
 * masterUsersEntity.setLocationType("UO"); } }else
 * if(Character.isDigit(accessResponse.getUnitCode().charAt(0))){ ZonalEntity
 * zonalEntity =
 * zonalEntityRepository.getZoneDetailsByZoneCode(accessResponse.getUnitCode());
 * if(zonalEntity != null) {
 * masterUsersEntity.setLocation(zonalEntity.getDescription());
 * masterUsersEntity.setLocationType("ZO"); } }else
 * if(accessResponse.getUnitCode().startsWith("C") ||
 * accessResponse.getUnitCode().startsWith("c")) {
 * masterUsersEntity.setLocation("C900");
 * masterUsersEntity.setLocationType("CO"); }else {
 * response.put(Constant.STATUS, 201); response.put(Constant.MESSAGE,
 * "Cannot update details for conventional user."); return
 * ResponseEntity.ok().body(response); } masterUsersEntity =
 * masterUserRepository.save(masterUsersEntity); if(masterUsersEntity != null) {
 * response.put(Constant.STATUS, 200); response.put(Constant.MESSAGE,
 * "User data updated successfully."); }else { response.put(Constant.STATUS,
 * 201); response.put(Constant.MESSAGE,
 * "Failed to update user data. Please try again after sometime."); } } else {
 * response.put(Constant.STATUS, 201); response.put(Constant.MESSAGE,
 * Constant.FAILED); } } catch (ResourceAccessException ex) {
 * ex.printStackTrace(); response.put(Constant.STATUS, 201);
 * response.put(Constant.MESSAGE,
 * "Failed to update user data. Please try again after sometime."); } catch
 * (Exception e) { e.printStackTrace(); response.put(Constant.STATUS, 201);
 * response.put(Constant.MESSAGE,
 * "Failed to update user data. Please try again after sometime."); } return
 * ResponseEntity.ok().body(response); }
 */

@Override
public ResponseEntity<Map<String, Object>> syncUserDetailsWithConcurrecia(String srnumber) throws Exception 
{
	logger.info("Entering syncUserDetailsWithConcurrecia method.");
	final String uri = "http://10.240.3.146:80/HRM_WebService/service/WSJ_51_00003/post";
	HashMap<String, Object> response = new HashMap<String, Object>();
	List<UserDetailsResponse> detailsResponses = new ArrayList();
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);	

		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("srnumber", srnumber);
		requestBody.put("emailId", "");
		HttpEntity formEntity = new HttpEntity<Map<String, String>>(requestBody, headers);
		ObjectMapper mapper = new ObjectMapper();
		try {
			logger.info("ENTERING THE REST TEMPLATE");
			ResponseEntity<String> responseFormAPI = restTemplate.postForEntity(uri, formEntity, String.class);
			logger.info("RESPONSE FROM THE REST TEMPLATE");
			if (responseFormAPI.getStatusCode() == HttpStatus.OK && responseFormAPI.getStatusCodeValue() == 200) {
				UserDetailsResponse accessResponse = new UserDetailsResponse();
				accessResponse = mapper.readValue(responseFormAPI.getBody(), UserDetailsResponse.class);
				
				MasterUsersEntity masterUsersEntity = masterUserRepository.findBySrNumberMain(srnumber);
				MasterUsersEntity masterUsersEntity2=new MasterUsersEntity();
				if(masterUsersEntity !=null) {
					masterUsersEntity2.setDesignation(accessResponse.getDesignation());
					masterUsersEntity2.setMobile(accessResponse.getMobileNumber());
					masterUsersEntity2.setFirstName(accessResponse.getFirstName());
					masterUsersEntity2.setMiddleName(accessResponse.getMiddleName());
					masterUsersEntity2.setLastName(accessResponse.getLastName());
					masterUsersEntity2.setClassName(String.valueOf(accessResponse.getClassCode()));
					masterUsersEntity2.setFullName(accessResponse.getFirstName() +" " +  accessResponse.getMiddleName() + " " + accessResponse.getLastName());
			//		masterUsersEntity2.setFullName(accessResponse.getFullName());
					masterUsersEntity2.setLocationCode(accessResponse.getUnitCode());
					masterUsersEntity2.setMasterUserId(masterUsersEntity.getMasterUserId());
					masterUsersEntity2.setUserName(accessResponse.getEmailId());
					masterUsersEntity2.setSrNumber(accessResponse.getEmailId());
					masterUsersEntity2.setEmail(accessResponse.getEmailId());
					masterUsersEntity2.setCadre(masterUsersEntity.getCadre());
					masterUsersEntity2.setClassName(masterUsersEntity.getClassName());
					masterUsersEntity2.setModifiedBy(masterUsersEntity.getModifiedBy());
					masterUsersEntity2.setModifiedOn(masterUsersEntity.getModifiedOn());
					masterUsersEntity2.setCreatedBy(masterUsersEntity.getCreatedBy());
					masterUsersEntity2.setCreatedOn(masterUsersEntity.getCreatedOn());
					masterUsersEntity2.setDateOfBirth(masterUsersEntity.getDateOfBirth());
					masterUsersEntity2.setSex(masterUsersEntity.getSex());
					masterUsersEntity2.setDateOfAppointment(masterUsersEntity.getDateOfAppointment());
					masterUsersEntity2.setMaritalStatus(masterUsersEntity.getMaritalStatus());
					masterUsersEntity2.setStatus(masterUsersEntity.getStatus());
					masterUsersEntity2.setIpAddress(masterUsersEntity.getIpAddress());
					masterUsersEntity2.setEmailId(accessResponse.getEmailId()+"@licindia.com");
					masterUsersEntity2.setIsActive(masterUsersEntity.getIsActive());
					masterUsersEntity2.setIsDeleted(masterUsersEntity.getIsDeleted());
					masterUsersEntity2.setCategory(masterUsersEntity.getCategory());
					masterUsersEntity2.setIsAdmin(masterUsersEntity.getIsAdmin());
					masterUsersEntity2.setDeActivatedDate(masterUsersEntity.getDeActivatedDate());
					masterUsersEntity2.setDeactivatedDays(masterUsersEntity.getDeactivatedDays());
					masterUsersEntity2.setSrNumber2(masterUsersEntity.getSrNumber2());
					masterUsersEntity2.setIsLoggedIn(masterUsersEntity.getIsLoggedIn());
					masterUsersEntity2.setLoggedInUserLoacationType(masterUsersEntity.getLoggedInUserLoacationType());
					masterUsersEntity2.setLoggedInUsername(masterUsersEntity.getLoggedInUsername());
					masterUsersEntity2.setTypeOfUser(masterUsersEntity.getTypeOfUser());
					masterUsersEntity2.setRemarks(masterUsersEntity.getRemarks());
					masterUsersEntity2.setCofinancialDesignation(masterUsersEntity.getCofinancialDesignation());
				if(accessResponse.getUnitCode().startsWith("G") || accessResponse.getUnitCode().startsWith("g")) {
					UnitEntity unitEntity = unitEntityRepository.getServiceDetailsByUnitCode(accessResponse.getUnitCode());
					if(unitEntity != null) {
						masterUsersEntity2.setLocation(unitEntity.getDescription());
						masterUsersEntity2.setLocationType("UO");
						masterUsersEntity2.setLocationCode(unitEntity.getUnitCode());
					}
				}else if(accessResponse.getUnitCode().startsWith("Z") || accessResponse.getUnitCode().startsWith("z")){
					String zonalCode = null;
					if(accessResponse.getUnitCode().equalsIgnoreCase("ZO01")) {
						zonalCode = "100";
					}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO02")){
						zonalCode = "200";
					}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO03")){
						zonalCode = "300";
					}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO04")){
						zonalCode = "400";
					}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO05")){
						zonalCode = "500";
					}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO06")){
						zonalCode = "600";
					}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO07")){
						zonalCode = "700";
					}else if(accessResponse.getUnitCode().equalsIgnoreCase("ZO08")){
						zonalCode = "800";
					}
					ZonalEntity zonalEntity = zonalEntityRepository.getZoneDetailsByZoneCode(zonalCode);
					if(zonalEntity != null) {
						masterUsersEntity2.setLocation(zonalEntity.getDescription());
						masterUsersEntity2.setLocationType("ZO");
						masterUsersEntity2.setLocationCode(zonalEntity.getZonalCode());
					}	
				}else if(accessResponse.getUnitCode().startsWith("C") || accessResponse.getUnitCode().startsWith("c")) {
					masterUsersEntity2.setLocation("PGS Central Office Mumbai");
					masterUsersEntity2.setLocationType("CO");
					masterUsersEntity2.setLocationCode("C900");
				} else { 
					response.put(Constant.STATUS, 201); 
					response.put(Constant.MESSAGE,	"Cannot update details for conventional user."); 
					return ResponseEntity.ok().body(response); 
				 }
					 
			//	masterUsersEntity = masterUserRepository.save(masterUsersEntity);
				if(masterUsersEntity != null) {				
					response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, "User data updated successfully.");
					response.put("Data", masterUsersEntity2);
				}else {
					response.put(Constant.STATUS, 201);
				   	response.put(Constant.MESSAGE, "Failed to update user data. Please try again after sometime.");
				}			   	
			} else {
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, "Failed to update user data. Please try again after sometime.");
			}
			}
		
		}catch (ResourceAccessException ex) {
			ex.printStackTrace();
			response.put(Constant.STATUS, 201);
			response.put(Constant.MESSAGE, "Failed to update user data. Please try again after sometime.");
		} catch (Exception e) {
			e.printStackTrace();
			response.put(Constant.STATUS, 201);
			response.put(Constant.MESSAGE, "Failed to update user data. Please try again after sometime.");
		}
		return ResponseEntity.ok().body(response);
}

@Override
public ResponseEntity<Map<String, Object>> checkTdsDetailsForUser(String username) throws Exception 
{
	logger.info("Entering checkTdsDetailsForUser method.");
	HashMap<String, Object> response = new HashMap<String, Object>();	
	response.put(Constant.STATUS, 200);
	response.put(Constant.MESSAGE, "No validations"); 
	 
	List<UserDetailsResponse> detailsResponses = new ArrayList();
	String locationCode = null;
	JSONObject jsonobj;
	try {
		//Check if date is between last 7 days of month
		 Date today = new Date();
		 Calendar calendar = Calendar.getInstance();  
	     calendar.setTime(today);
	     calendar.add(Calendar.MONTH, 1);  
	     calendar.set(Calendar.DAY_OF_MONTH, 1);  
	     calendar.add(Calendar.DATE, -1); 
	     Date lastDayOfMonth = calendar.getTime(); 
	     calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-6);	        
	     Date last7thday = calendar.getTime();
	     if((today.after(last7thday) || today.equals(last7thday)) && (today.before(lastDayOfMonth) || today.equals(lastDayOfMonth))) {
	    	 //Call Accounting API
	    	 MasterUsersEntity masterUsersEntity = masterUserRepository.getUserByUserName(username);
	    	 if(masterUsersEntity != null) {
	    		 List<UserRoleTypeMappingEntity> roleTypeMappingEntities= userRoleTypeRepository.checkIfUserHasTDSRoles(masterUsersEntity.getMasterUserId());
	    		 if(roleTypeMappingEntities != null && roleTypeMappingEntities.size() >= 1) {
		    		 locationCode = masterUsersEntity.getLocationCode();
		    		 if(locationCode != null) {
		    			 final String baseUrl = tdsDetailsAPIUrl + locationCode.trim();
		    			 HttpEntity<String> entity = new HttpEntity<String>(restHeader());
		    			 ResponseEntity<String> apiresponse = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);
		    			 if(apiresponse != null && apiresponse.getStatusCode().equals(HttpStatus.OK)) {
		    				 jsonobj = new JSONObject(apiresponse.getBody());
		    				 if(jsonobj != null) {
		    					 String incomeTaxVoucherGenerationStatus = jsonobj.getString("incomeTaxVoucherGenerationStatus");
		    					 if(incomeTaxVoucherGenerationStatus != null && !incomeTaxVoucherGenerationStatus.equalsIgnoreCase("") && incomeTaxVoucherGenerationStatus.equalsIgnoreCase("Yes")) {
		    						 response.put(Constant.STATUS, 201);
		    						 response.put(Constant.MESSAGE, "Generate the income tax voucher"); 
		    						 return ResponseEntity.ok().body(response);
		    					 }else if(incomeTaxVoucherGenerationStatus != null && !incomeTaxVoucherGenerationStatus.equalsIgnoreCase("") && incomeTaxVoucherGenerationStatus.equalsIgnoreCase("No")) {
		    						 response.put(Constant.STATUS, 200);
		    						 response.put(Constant.MESSAGE, "No validations"); 
		    						 return ResponseEntity.ok().body(response);
		    					 }
		    				 }
		    			 }	    			 
		    		 }
	    		 }
	    	 }	    	
	     }
	}catch (Exception e) {
			e.printStackTrace();
			response.put(Constant.STATUS, 202);
			response.put(Constant.MESSAGE, "Failed to check tds details for the user unit. Please try again after sometime.");
	}
	return ResponseEntity.ok().body(response);
}

@Override
public ResponseEntity<Map<String, Object>> checkBrsAndTokenDetailsForUser(String username) throws Exception 
{
	logger.info("Entering checkTdsDetailsForUser method.");
	HashMap<String, Object> response = new HashMap<String, Object>();	
	response.put(Constant.STATUS, 200);
	response.put(Constant.MESSAGE, "No validations"); 
	
	List<UserDetailsResponse> detailsResponses = new ArrayList();
	String locationCode = null;
	JSONObject jsonobj;
	try {
		//Check if date is between first 8 days of month
		 Date today = new Date();
		 Calendar calendar = Calendar.getInstance();  
	     calendar.setTime(today);
	     calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
	     Date firstday = calendar.getTime();
	     calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+7);	        
	     Date eighthday = calendar.getTime();
	     if((today.after(firstday) || today.equals(firstday)) && (today.before(eighthday) || today.equals(eighthday))) {
	        //Call Accounting API
	    	 MasterUsersEntity masterUsersEntity = masterUserRepository.getUserByUserName(username);
	    	 if(masterUsersEntity != null) {
	    		 List<UserRoleTypeMappingEntity> roleTypeMappingEntities= userRoleTypeRepository.checkIfUserHasTDSRoles(masterUsersEntity.getMasterUserId());
	    		 if(roleTypeMappingEntities != null && roleTypeMappingEntities.size() >= 1) {
		    		 locationCode = masterUsersEntity.getLocationCode();
		    		 if(locationCode != null) {
		    			 final String baseUrl = brsAndTokenNoAPIUrl + locationCode.trim();
		    			 HttpEntity<String> entity = new HttpEntity<String>(restHeader());
		    			 ResponseEntity<String> apiresponse = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);
		    			 if(apiresponse != null && apiresponse.getStatusCode().equals(HttpStatus.OK)) {
		    				 jsonobj = new JSONObject(apiresponse.getBody());
		    				 if(jsonobj != null) {
		    					 String tokenGenerationStatus = jsonobj.getString("tokenGenerationStatus");
		    					 String brsGenerationStatus = jsonobj.getString("brsGenerationStatus");
		    					 if(tokenGenerationStatus != null && brsGenerationStatus != null && (tokenGenerationStatus.equalsIgnoreCase("Yes") || brsGenerationStatus.equalsIgnoreCase("Yes"))) {
		    						 response.put(Constant.STATUS, 201);
		    						 response.put(Constant.MESSAGE, "Kindly Add BSR no. and Token no. to TDS Remittance Process"); 
		    						 return ResponseEntity.ok().body(response);
		    					 }else if(tokenGenerationStatus != null && brsGenerationStatus != null && (tokenGenerationStatus.equalsIgnoreCase("Yes") || brsGenerationStatus.equalsIgnoreCase("No"))){
		    						 response.put(Constant.STATUS, 201);
		    						 response.put(Constant.MESSAGE, "Kindly Add Token no. to TDS Remittance Process"); 
		    						 return ResponseEntity.ok().body(response);
		    					 }else {
		    						 response.put(Constant.STATUS, 200);
		    						 response.put(Constant.MESSAGE, "No validations"); 
		    						 return ResponseEntity.ok().body(response);
		    					 }
		    				 }
		    			 }	    			 
		    		 }
	    		 }
	    	 }	    	
	     }
	}catch (Exception e) {
			e.printStackTrace();
			response.put(Constant.STATUS, 202);
			response.put(Constant.MESSAGE, "Failed to check brs and token details for user unit. Please try again after sometime.");
	}
	return ResponseEntity.ok().body(response);
}


public  Date setExpiry(int maxExpirydays)
{
//    d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
//    return d;
	Calendar c= Calendar.getInstance();
	c.add(Calendar.DATE, maxExpirydays);
	Date d1=c.getTime();
	return d1;
}



}

