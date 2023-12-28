package com.lic.epgs.mst.usermgmt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.controller.ModuleController;
import com.lic.epgs.mst.usermgmt.entity.LoggedInUserSessionDetailsEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterApplicationModule;
import com.lic.epgs.mst.usermgmt.repository.LoggedinuserSessionDetailsRepository;
import com.lic.epgs.mst.usermgmt.repository.ModuleRepository;

@Service
@Transactional
public class ModuleServiceImpl implements ModuleService { 

	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private LoggedinuserSessionDetailsRepository loggedinuserSessionDetailsRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

	/*
	 * Description: This function is used for getting all the data from MASTER_APPLICAITON_MODULE 
	 * Table Name- MASTER_APPLICAITON_MODULE
	 * Author- Nandini R
	 */
	
	@Override
	 public List<MasterApplicationModule> getAllModule() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get Module list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return moduleRepository.findAllModule();
	}

	@Override
	public ResponseEntity<Map<String, Object>> saveLoggedinuserSessionDetails(
			LoggedInUserSessionDetailsEntity loggedInUserSessionDetailsEntity) throws Exception {
		logger.info("Enter UserRoleTypeMapping : add session details ");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		try {
			Date date = new Date();
			if (loggedInUserSessionDetailsEntity == null) {
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} 
				else  {
					loggedInUserSessionDetailsEntity.setCreatedOn(date);
					loggedInUserSessionDetailsEntity.setModifiedOn(date);
					//masterUsersLoginDetailsEntity.setLoggedOutTime(date);
					LoggedInUserSessionDetailsEntity masterUsersLoginDetails = loggedinuserSessionDetailsRepository.save(loggedInUserSessionDetailsEntity);
					response.put(Constant.STATUS, 201);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("ID",loggedInUserSessionDetailsEntity.getId()) ;
				}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Could not save  session details : " + exception.getMessage());
		}
		return null;
	}
	
}
