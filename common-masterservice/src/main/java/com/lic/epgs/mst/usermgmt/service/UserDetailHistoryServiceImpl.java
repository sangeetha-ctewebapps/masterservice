package com.lic.epgs.mst.usermgmt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.lic.epgs.mst.usermgmt.controller.UserManagementController;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptandDecryptAES;
import com.lic.epgs.mst.usermgmt.entity.PortalMasterEntity;
import com.lic.epgs.mst.usermgmt.entity.UserDetailHistoryEntity;
import com.lic.epgs.mst.usermgmt.entity.UserRoleMappingEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserDetailHistoryException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserManagementException;
import com.lic.epgs.mst.usermgmt.repository.UserHistoryDetailsRepository;

@Service
@Transactional
public class UserDetailHistoryServiceImpl implements UserDetailHistoryService {

	@Autowired
	UserHistoryDetailsRepository  userHistoryDetailsRepository;
	
	@Autowired
	private EncryptandDecryptAES encryptandDecryptAES;
	
	private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);
	
	@Override
	public ResponseEntity<Object> saveUserDetailsHistory(UserDetailHistoryEntity userHistoryEntity)
			throws UserDetailHistoryException {
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> response1 = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter UserManagementService : " + methodName);

		try {
			Date today = new Date();
			if (userHistoryEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} 
                        if (!userHistoryEntity.getAction().equalsIgnoreCase("add")) {
						UserDetailHistoryEntity userDetailHistoryEntity2=userHistoryDetailsRepository.getLatestrecordBasedOnUserName(userHistoryEntity.getUsername());
						userHistoryEntity.setCreatedby(userDetailHistoryEntity2.getCreatedby());
						userHistoryEntity.setCreatedon(userDetailHistoryEntity2.getCreatedon());
					}
					
				UserDetailHistoryEntity userHistoryDetails = userHistoryDetailsRepository.save(userHistoryEntity);
					response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", userHistoryDetails.getUserDetailHistoryId());
					ObjectMapper Obj = new ObjectMapper();
					String jsonStr = Obj.writeValueAsString(response);

					String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
					response1.put(Constant.STATUS, 200);
					response1.put(Constant.MESSAGE, Constant.SUCCESS);
					response1.put("body", encaccessResponse);
					
				
			return new ResponseEntity<Object>(response1, HttpStatus.OK);


		} catch (Exception exception) {
			logger.info("Exception " + exception.getMessage());
			throw new UserDetailHistoryException("Internal Server Error");
		}
	}

//	@Override
//	public ResponseEntity<Object> getUserDetailsHistoryBasedOnUsername(String username,Date startDate,Date endDate)
//			throws UserDetailHistoryException {
//		Map<String, Object> response = new HashMap<String, Object>();
//		Map<String, Object> response1 = new HashMap<String, Object>();
//		response.put(Constant.STATUS, 0);
//		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
//		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
//		logger.info("Enter UserManagementService : " + methodName);
//
//		try {
//			Date today = new Date();
//			if (username.isEmpty() && startDate.) {
//				response.put(Constant.STATUS, 0);
//				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
//			} 
//                        if () {
//						UserDetailHistoryEntity userDetailHistoryEntity2=userHistoryDetailsRepository.getLatestrecordBasedOnUserName(userHistoryEntity.getUsername());
//						userHistoryEntity.setCreatedby(userDetailHistoryEntity2.getCreatedby());
//						userHistoryEntity.setCreatedon(userDetailHistoryEntity2.getCreatedon());
//					}
//					
//				UserDetailHistoryEntity userHistoryDetails = userHistoryDetailsRepository.save(userHistoryEntity);
//					response.put(Constant.STATUS, 200);
//					response.put(Constant.MESSAGE, Constant.SUCCESS);
//					response.put("Data", userHistoryDetails.getUserDetailHistoryId());
//					ObjectMapper Obj = new ObjectMapper();
//					String jsonStr = Obj.writeValueAsString(response);
//
//					String encaccessResponse = encryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
//					response1.put(Constant.STATUS, 200);
//					response1.put(Constant.MESSAGE, Constant.SUCCESS);
//					response1.put("body", encaccessResponse);
//					
//				
//			return new ResponseEntity<Object>(response1, HttpStatus.OK);
//
//
//		} catch (Exception exception) {
//			logger.info("Exception " + exception.getMessage());
//			throw new UserDetailHistoryException("Internal Server Error");
//		}
//	}


	
	@Override
	public ResponseEntity<Object> getUserDetailsHistoryBasedOnUsername(String srnumber, String fromDate, String toDate, String loggedInUserUnitCode) throws UserDetailHistoryException 
	{
		logger.info("getUserDetailsHistoryBasedOnUsername method started");
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> response1 = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		List<UserDetailHistoryEntity> getAllUserFromHistory = new ArrayList<UserDetailHistoryEntity>();
		try
		{
			if((srnumber.isEmpty()) && (fromDate.isEmpty()) && (toDate.isEmpty()))
			{
				getAllUserFromHistory = userHistoryDetailsRepository.getAllUsersHistory(loggedInUserUnitCode);
			}
			else if((!srnumber.isEmpty()) && (fromDate.isEmpty()) && (toDate.isEmpty()))
			{
				getAllUserFromHistory = userHistoryDetailsRepository.getAllUserInfoBasedOnUsername(srnumber, loggedInUserUnitCode);
			}
			else if((srnumber.isEmpty()) && (!fromDate.isEmpty()) && (!toDate.isEmpty()))
			{
				getAllUserFromHistory = userHistoryDetailsRepository.getAllUserInfoBasedOnDate( fromDate, toDate, loggedInUserUnitCode);
			}
			else if((!srnumber.isEmpty()) && (!fromDate.isEmpty()) && (!toDate.isEmpty()))
			{
				getAllUserFromHistory = userHistoryDetailsRepository.getAllUserInfoBasedOnUsernameAndDate(srnumber, fromDate, toDate, loggedInUserUnitCode);
			}
			else if((srnumber.isEmpty()) && (!fromDate.isEmpty()) && (toDate.isEmpty()))
			{
				getAllUserFromHistory = userHistoryDetailsRepository.getAllUsersHistoryBasedOnFromDate( fromDate, loggedInUserUnitCode);
			}
			else if((srnumber.isEmpty()) && (fromDate.isEmpty()) && (!toDate.isEmpty()))
			{
				getAllUserFromHistory = userHistoryDetailsRepository.getAllUsersHistoryBasedOnToDate(toDate, loggedInUserUnitCode);
			}
			
			if(getAllUserFromHistory.size() > 0)
			{ 
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", getAllUserFromHistory);
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
			e.printStackTrace();
		}
		logger.info("getAllUsersPendingForApproval method ended");
		return new ResponseEntity<Object>(response1, HttpStatus.OK);
	}

}
