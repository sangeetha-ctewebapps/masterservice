package com.lic.epgs.mst.usermgmt.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptandDecryptAES;
import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.RolesAssignmentHistory;
import com.lic.epgs.mst.usermgmt.exceptionhandling.RolesAssignmentException;
import com.lic.epgs.mst.usermgmt.repository.PortalMasterRepository;
import com.lic.epgs.mst.usermgmt.repository.RolesAssignmentRespository;
import com.lic.epgs.rhssomodel.RolesAndUsersListModel;
import com.lic.epgs.rhssomodel.RolesAssignmnetHistoryModel;
import com.lic.epgs.rhssomodel.UserCreationModel;

@Service
@Transactional
public class RolesAssignmentHistoryServiceImpl implements RolesAssignmentHistoryService{

	@Autowired
	private EncryptandDecryptAES encryptandDecryptAES;
	
	@Autowired
	RolesAssignmentRespository rolesAssigmentRepository;
	
	@Autowired
	PortalMasterRepository portalMasterRepository;
	
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(MphUserServiceImpl.class);
	
	@Override
	public ResponseEntity<Object> rolesAssigmentHistory(RolesAssignmentHistory rolesAssignmentHistory)
			throws RolesAssignmentException {
		Date date=new Date();
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> response1 = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug("Enter CoAdminService : " + methodName);
		RolesAssignmentHistory rolesAssignHistory=new RolesAssignmentHistory();

		try {
			if (rolesAssignmentHistory == null) {
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
					 rolesAssignHistory.setMphId(rolesAssignmentHistory.getMphId());	 
					 rolesAssignHistory.setMphName(rolesAssignmentHistory.getMphName());
					 rolesAssignHistory.setCreatedBy(rolesAssignmentHistory.getCreatedBy());
					 rolesAssignHistory.setCreatedOn(date);
					 rolesAssignHistory.setRoleName(rolesAssignmentHistory.getRoleName());
					 rolesAssignHistory.setRemarks(rolesAssignmentHistory.getRemarks());
					 rolesAssignHistory.setStatus("sentforapproval");
					 rolesAssignHistory.setWorkFlowStatus(1L);
					  
					 RolesAssignmentHistory	rolesAssignHist= rolesAssigmentRepository.save(rolesAssignHistory);	 
					
					 response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", "ROLES ASSIGNED SUCCESSFULLY ");
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
			 throw new RolesAssignmentException ("Internal Server Error");
		}
		//return null;

	}
	
	@Override
	public ResponseEntity<Object> getrolesAndUsesAssigmentHistory(RolesAssignmnetHistoryModel rolesAssignmentHistoryModel,UserCreationModel userCreationModel)
			throws RolesAssignmentException {
		Date date = new Date();
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> response1 = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug("Enter CoAdminService : " + methodName);
		RolesAssignmentHistory rolesAssignHistory = new RolesAssignmentHistory();
		List<RolesAssignmentHistory> rolesAssigmentList=new ArrayList<RolesAssignmentHistory>();
		List<PortalMasterEntity> usersAssigmentList=new ArrayList<PortalMasterEntity>();
		RolesAndUsersListModel rolesAndUsersListModel= new RolesAndUsersListModel();
		
		try {
//			
				if (rolesAssignmentHistoryModel.getAction() == null 
						&& rolesAssignmentHistoryModel.getStartDate() ==null
						&& rolesAssignmentHistoryModel.getEndDate()== null
						&& userCreationModel.getAction()==null 
						&& userCreationModel.getStartDate()==null
						&& userCreationModel.getEndDate()==null
						) { 

					 rolesAssigmentList = rolesAssigmentRepository
							.getRolesAssignmentByUsername(rolesAssignmentHistoryModel.getLoggedInUserName());
					 rolesAndUsersListModel.setRolesAssignmnetHistoryModel(rolesAssigmentList);
					 
					 usersAssigmentList = portalMasterRepository
						.getUserCreationByLoggedInUser(userCreationModel.getLoggedInUserName());
				 
						rolesAndUsersListModel.setUserCreationModel(usersAssigmentList);
					} 			

				 else if (rolesAssignmentHistoryModel.getStartDate().equalsIgnoreCase("")
							&& rolesAssignmentHistoryModel.getEndDate().equalsIgnoreCase("")
							&& rolesAssignmentHistoryModel.getAction()!=null) {  

						 rolesAssigmentList = rolesAssigmentRepository
								.getRolesAssignmentByUsernameAndStatus(
										rolesAssignmentHistoryModel.getLoggedInUserName(),
										rolesAssignmentHistoryModel.getAction());
							
							rolesAndUsersListModel.setRolesAssignmnetHistoryModel(rolesAssigmentList);
						
					} else if ( !rolesAssignmentHistoryModel.getStartDate().equalsIgnoreCase("")
							&& !rolesAssignmentHistoryModel.getEndDate().equalsIgnoreCase("")
							&& rolesAssignmentHistoryModel.getAction()!=null) {    
               
						DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
						Date startDated = inputFormat.parse(rolesAssignmentHistoryModel.getStartDate());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
						String startDated1 = dateFormat.format(startDated);
						
                        DateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
						Date endDated = inputFormat1.parse(rolesAssignmentHistoryModel.getEndDate());
						DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd"); 
						String endDated1 = dateFormat1.format(endDated);
						
						rolesAssignmentHistoryModel.setStartDate(startDated1);
						rolesAssignmentHistoryModel.setEndDate(endDated1);
						
						 rolesAssigmentList = rolesAssigmentRepository
								.getRolesAssignmentByUsernameAndStatusAndDate(
										rolesAssignmentHistoryModel.getLoggedInUserName(),
										rolesAssignmentHistoryModel.getAction(),
										rolesAssignmentHistoryModel.getStartDate(),
										rolesAssignmentHistoryModel.getEndDate());
						 
						 rolesAndUsersListModel.setRolesAssignmnetHistoryModel(rolesAssigmentList);
						 
					}else if( !rolesAssignmentHistoryModel.getStartDate().equalsIgnoreCase("")
							&& rolesAssignmentHistoryModel.getEndDate().equalsIgnoreCase("")
							&& rolesAssignmentHistoryModel.getAction().equalsIgnoreCase("")){  
						
						LocalDateTime ldt = LocalDateTime.now();
				        String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
				                                  .format(ldt);
	//			        System.out.println("Formatted Date in String format: "+currentDate);
				        rolesAssignmentHistoryModel.setEndDate(currentDate);
				        
				        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
						Date startDated = inputFormat.parse(rolesAssignmentHistoryModel.getStartDate());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
						String startDated1 = dateFormat.format(startDated);
						rolesAssignmentHistoryModel.setStartDate(startDated1);
						
						 rolesAssigmentList = rolesAssigmentRepository
								.getRolesAssignmentByUsernameAndStartDate(
										rolesAssignmentHistoryModel.getLoggedInUserName(),
										rolesAssignmentHistoryModel.getStartDate(),
										rolesAssignmentHistoryModel.getEndDate());
						 rolesAndUsersListModel.setRolesAssignmnetHistoryModel(rolesAssigmentList);
						 
					}else if(rolesAssignmentHistoryModel.getStartDate().equalsIgnoreCase("")
							 && !rolesAssignmentHistoryModel.getEndDate().equalsIgnoreCase("")
							 && rolesAssignmentHistoryModel.getAction()==null) {  
					
				        
				        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
						Date endDated = inputFormat.parse(rolesAssignmentHistoryModel.getEndDate());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
						String endDated1 = dateFormat.format(endDated);
						rolesAssignmentHistoryModel.setEndDate(endDated1);
						
						rolesAssigmentList = rolesAssigmentRepository
								.getRolesAssignmentByUsernameAndEndDate(
										rolesAssignmentHistoryModel.getLoggedInUserName(),
										rolesAssignmentHistoryModel.getEndDate());
						rolesAndUsersListModel.setRolesAssignmnetHistoryModel(rolesAssigmentList);
						
					}else if(!rolesAssignmentHistoryModel.getStartDate().equalsIgnoreCase("")
							 && rolesAssignmentHistoryModel.getEndDate().equalsIgnoreCase("")
							 && rolesAssignmentHistoryModel.getAction()!=null) {  
						
						LocalDateTime ldt = LocalDateTime.now();
				        String endDate = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
				                                  .format(ldt);
	//			        System.out.println("Formatted Date in String format: "+currentDate);
				        rolesAssignmentHistoryModel.setEndDate(endDate);
				        
				        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
						Date startDated = inputFormat.parse(rolesAssignmentHistoryModel.getStartDate());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
						String startDated1 = dateFormat.format(startDated);
						rolesAssignmentHistoryModel.setStartDate(startDated1);
						
						rolesAssigmentList = rolesAssigmentRepository.getRolesAssignmentByUsernameAndStatusAndStartDate(rolesAssignmentHistoryModel.getLoggedInUserName(),
								rolesAssignmentHistoryModel.getAction(),rolesAssignmentHistoryModel.getStartDate(),rolesAssignmentHistoryModel.getEndDate());
					    
						rolesAndUsersListModel.setRolesAssignmnetHistoryModel(rolesAssigmentList);
					
					}else if(rolesAssignmentHistoryModel.getStartDate().equalsIgnoreCase("")
							 && !rolesAssignmentHistoryModel.getEndDate().equalsIgnoreCase("")
							 && rolesAssignmentHistoryModel.getAction()!=null) {   
						
						DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
						Date endDated = inputFormat.parse(rolesAssignmentHistoryModel.getEndDate());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
						String endDated1 = dateFormat.format(endDated);
						rolesAssignmentHistoryModel.setEndDate(endDated1);
						
						rolesAssigmentList = rolesAssigmentRepository.getRolesAssignmentByUsernameAndStatusAndEndDate(rolesAssignmentHistoryModel.getLoggedInUserName(),
								rolesAssignmentHistoryModel.getAction(),rolesAssignmentHistoryModel.getEndDate());
						
						rolesAndUsersListModel.setRolesAssignmnetHistoryModel(rolesAssigmentList);
						
					}else if(userCreationModel.getStartDate().equals("")
							&& userCreationModel.getEndDate().equals("")
							&& userCreationModel.getAction()!=null) {   
						
						usersAssigmentList = portalMasterRepository
								.getUsersAssignmentByUsernameAndStatus(userCreationModel.getLoggedInUserName(), userCreationModel.getAction());
						
							rolesAndUsersListModel.setUserCreationModel(usersAssigmentList);
							
					}else if ( !userCreationModel.getStartDate().equals("")
							&& !userCreationModel.getEndDate().equals("")
							&& userCreationModel.getAction()!=null) {  
               
						DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
						Date startDated = inputFormat.parse(userCreationModel.getStartDate());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
						String startDated1 = dateFormat.format(startDated);
						
                        DateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
						Date endDated = inputFormat1.parse(userCreationModel.getEndDate());
						DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd"); 
						String endDated1 = dateFormat1.format(endDated);
						
						userCreationModel.setStartDate(startDated1);
						userCreationModel.setEndDate(endDated1);
						
						usersAssigmentList = portalMasterRepository
								.getUsersAssignmentByUsernameAndStatusAndDate(
										userCreationModel.getLoggedInUserName(),
										userCreationModel.getAction(),
										userCreationModel.getStartDate(),
										userCreationModel.getEndDate());
						rolesAndUsersListModel.setUserCreationModel(usersAssigmentList);
						
					}
//					else if( !userCreationModel.getStartDate().equals("")
//							&& userCreationModel.getEndDate().equals("")
//							&& userCreationModel.getAction()==null){  
//						
//						LocalDateTime ldt = LocalDateTime.now();
//				        String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
//				                                  .format(ldt);
//	//			        System.out.println("Formatted Date in String format: "+currentDate);
//				        userCreationModel.setEndDate(currentDate);
//				        
//				        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
//						Date startDated = inputFormat.parse(userCreationModel.getStartDate());
//						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
//						String startDated1 = dateFormat.format(startDated);
//						userCreationModel.setStartDate(startDated1);
//						
//						usersAssigmentList = portalMasterRepository
//								.getUsersAssignmentByUsernameAndStartDate(
//										userCreationModel.getLoggedInUserName(),
//										userCreationModel.getStartDate(),
//										userCreationModel.getEndDate());
//						rolesAndUsersListModel.setUserCreationModel(usersAssigmentList);
//					}
//					}else if(userCreationModel.getStartDate()==null
//							 && userCreationModel.getEndDate()!=null
//							 && userCreationModel.getAction()==null) {  
//					
//				        
//				        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
//						Date endDated = inputFormat.parse(userCreationModel.getEndDate());
//						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
//						String endDated1 = dateFormat.format(endDated);
//						userCreationModel.setEndDate(endDated1);
//						
//						usersAssigmentList = portalMasterRepository
//								.getUsersAssignmentByUsernameAndEndDate(
//										userCreationModel.getLoggedInUserName(),
//										userCreationModel.getEndDate());
//						rolesAndUsersListModel.setUserCreationModel(usersAssigmentList);
//						
//					}
						else if(!userCreationModel.getStartDate().equals("")
							 && userCreationModel.getEndDate().equals("")
							 && userCreationModel.getAction()!=null) {  
						
						LocalDateTime ldt = LocalDateTime.now();
				        String endDate = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
				                                  .format(ldt);
	//			        System.out.println("Formatted Date in String format: "+currentDate);
				        userCreationModel.setEndDate(endDate);
				        
				        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
						Date startDated = inputFormat.parse(userCreationModel.getStartDate());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
						String startDated1 = dateFormat.format(startDated);
						userCreationModel.setStartDate(startDated1);
						
						usersAssigmentList = portalMasterRepository.getUsersAssignmentByUsernameAndStatusAndStartDate(userCreationModel.getLoggedInUserName(),
								userCreationModel.getAction(),userCreationModel.getStartDate(),userCreationModel.getEndDate());
						
						rolesAndUsersListModel.setUserCreationModel(usersAssigmentList);
						
					}else if(userCreationModel.getStartDate().equals("")
							 && !userCreationModel.getEndDate().equals("")
							 && userCreationModel.getAction()!=null) {   
						
						DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
						Date endDated = inputFormat.parse(userCreationModel.getEndDate());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
						String endDated1 = dateFormat.format(endDated);
						userCreationModel.setEndDate(endDated1);
						
						usersAssigmentList = portalMasterRepository.getUsersAssignmentByUsernameAndStatusAndEndDate(userCreationModel.getLoggedInUserName(),
								userCreationModel.getAction(),userCreationModel.getEndDate());
						
						rolesAndUsersListModel.setUserCreationModel(usersAssigmentList);
					}
				
				if (rolesAndUsersListModel.isEmpty()) {
					response.put(Constant.STATUS, 201);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "NO DATA FOUND");
					ObjectMapper Obj = new ObjectMapper();
					String jsonStr = Obj.writeValueAsString(response);
					String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
					response1.put(Constant.STATUS, 200);
					response1.put(Constant.MESSAGE, Constant.SUCCESS);
					response1.put("body", encaccessResponse);
					return new ResponseEntity<Object>(response1, HttpStatus.OK);
				}
				
				
				response.put(Constant.STATUS, 200);
			    response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", rolesAndUsersListModel);

				ObjectMapper Obj = new ObjectMapper();
				String jsonStr = Obj.writeValueAsString(response);

				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
				response1.put(Constant.STATUS, 200);
				response1.put(Constant.MESSAGE, Constant.SUCCESS);
				response1.put("body", encaccessResponse);
				return new ResponseEntity<Object>(response1, HttpStatus.OK);
				

                   
				}

			
		 catch (Exception exception) {
			logger.error("Could not add Mph User " + exception.getMessage());
			throw new RolesAssignmentException("Internal Server Error");
		}

	}

	private Object convertToDatabaseColumn(Date startDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ResponseEntity<Object> getrolesAssigmentHistory(RolesAssignmentHistory rolesAssignmentHistory)
			throws RolesAssignmentException {
		Date date = new Date();
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> response1 = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug("Enter CoAdminService : " + methodName);
		RolesAssignmentHistory rolesAssignHistory = new RolesAssignmentHistory();

		try {
			if (rolesAssignmentHistory == null) {
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
				ObjectMapper Obj = new ObjectMapper();
				String jsonStr = Obj.writeValueAsString(response);

				// ENcryption Technique
				String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
				response1.put(Constant.STATUS, 201);
				response1.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
				response1.put("body", encaccessResponse);
				return new ResponseEntity<Object>(response1, HttpStatus.CREATED);
			} else {
				if (rolesAssignmentHistory.getStatus().equalsIgnoreCase(null)
						|| rolesAssignmentHistory.getStatus().isEmpty()) {

					List<RolesAssignmentHistory> rolesAssignmentList = rolesAssigmentRepository
							.getRolesAssignmentByUsername(rolesAssignmentHistory.getCreatedBy());

					if (rolesAssignmentList.isEmpty()) {
						response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", "NO DATA FOUND");
						ObjectMapper Obj = new ObjectMapper();
						String jsonStr = Obj.writeValueAsString(response);
						String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
						response1.put(Constant.STATUS, 200);
						response1.put(Constant.MESSAGE, Constant.SUCCESS);
						response1.put("body", encaccessResponse);
						return new ResponseEntity<Object>(response1, HttpStatus.OK);
					} else {

						response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", rolesAssignmentList);

						ObjectMapper Obj = new ObjectMapper();
						String jsonStr = Obj.writeValueAsString(response);

						String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
						response1.put(Constant.STATUS, 200);
						response1.put(Constant.MESSAGE, Constant.SUCCESS);
						response1.put("body", encaccessResponse);
						return new ResponseEntity<Object>(response1, HttpStatus.OK);

					}

				} else {

					List<RolesAssignmentHistory> rolesAssignmentList = rolesAssigmentRepository
							.getRolesAssignmentByUsernameAndStatus(rolesAssignmentHistory.getCreatedBy(),
									rolesAssignmentHistory.getStatus());
					if (rolesAssignmentList.isEmpty()) {
						response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", "NO DATA FOUND");
						ObjectMapper Obj = new ObjectMapper();
						String jsonStr = Obj.writeValueAsString(response);
						String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
						response1.put(Constant.STATUS, 200);
						response1.put(Constant.MESSAGE, Constant.SUCCESS);
						response1.put("body", encaccessResponse);
						return new ResponseEntity<Object>(response1, HttpStatus.OK);
					} else {

						response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", rolesAssignmentList);

						ObjectMapper Obj = new ObjectMapper();
						String jsonStr = Obj.writeValueAsString(response);

						String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
						response1.put(Constant.STATUS, 200);
						response1.put(Constant.MESSAGE, Constant.SUCCESS);
						response1.put("body", encaccessResponse);
						return new ResponseEntity<Object>(response1, HttpStatus.OK);

					}

				}

			}
		} catch (Exception exception) {
			logger.error("Could not add Mph User " + exception.getMessage());
			throw new RolesAssignmentException("Internal Server Error");
		}

	}
	
}
