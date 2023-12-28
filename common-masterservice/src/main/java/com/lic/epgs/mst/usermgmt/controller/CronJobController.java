package com.lic.epgs.mst.usermgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.modal.ConcureciaUser;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersRepository;
import com.lic.epgs.mst.usermgmt.service.MasterUsersService;
import com.lic.epgs.mst.usermgmt.service.RedhatUserGenerationService;
import com.lic.epgs.rhssomodel.UserDetailsRequestModel;

@CrossOrigin("*")
@RestController
public class CronJobController {

	private static final Logger logger = LogManager.getLogger(CronJobController.class);

	@Autowired
	MasterUsersRepository masterUserRepository;

	@Autowired
	MasterUsersService masterUsersService;

	@Autowired
	private RedhatUserGenerationService redhatUserGenerationService;

	@PostMapping("/ADDUSERFROMCONCURECIA")
	public Map<String, Object> cronJobForInsertUsersIntoDB() {
		logger.info("ADDUSERFROMCONCURECIA Cron Started");
//		UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
		Map<String, Object> response = new HashMap<>(3);
		response.put(Constant.STATUS, 500);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		try {
			// bulkUploadCronJobService.addUsersIntoDBThroughBulkFileUploadRetrylogic();
			List<String> usersList = masterUserRepository.getAllUsersFromDB();
			int size = usersList.size();
			for (int i = 0; i < size; i++) {
				UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
				userDetailsRequestModel.setSrnumber(usersList.get(i));
				userDetailsRequestModel.setEmailId("string");
				ResponseEntity<Object> response2 = redhatUserGenerationService
						.getDetailsFromConcureciaApi(userDetailsRequestModel);
				logger.info("Response from Concurecia api"+response2.toString());
				ConcureciaUser conUsers = (ConcureciaUser) response2.getBody();
				logger.info("ConUsers model"+conUsers.toString());
				MasterUsersEntity masterUsers = (MasterUsersEntity) masterUserRepository
						.findBySrNumberMain(usersList.get(i));
				if (conUsers != null && masterUsers != null) {
					if (conUsers.getDesignation().equalsIgnoreCase(masterUsers.getDesignation()) == false) {
						masterUsers.setDesignation(conUsers.getDesignation());
					}
					if (conUsers.getMobileNumber().equalsIgnoreCase(masterUsers.getMobile()) == false) {
						masterUsers.setMobile(conUsers.getMobileNumber());
					}
					if (conUsers.getEmailId().equalsIgnoreCase(masterUsers.getEmailId()) == false) {
						masterUsers.setEmailId(conUsers.getEmailId());
					}
					if (conUsers.getClassCode().equalsIgnoreCase(masterUsers.getClassName()) == false) {
						masterUsers.setClassName(conUsers.getClassCode());
					}
					if (conUsers.getDateOfBirth().compareTo(masterUsers.getDateOfBirth()) != 0) {
						masterUsers.setDateOfBirth(conUsers.getDateOfBirth());
					}
					if (conUsers.getFirstName().equalsIgnoreCase(masterUsers.getFirstName()) == false) {
						masterUsers.setFirstName(conUsers.getFirstName());
					}
					if (conUsers.getMiddleName().equalsIgnoreCase(masterUsers.getMiddleName()) == false) {
						masterUsers.setMiddleName(conUsers.getMiddleName());
					}
					if (conUsers.getLastName().equalsIgnoreCase(masterUsers.getLastName()) == false) {
						masterUsers.setLastName(conUsers.getLastName());
					}
   		    	   if(conUsers.getUnitCode().equalsIgnoreCase(masterUsers.getLocationCode())==false)
		    	   {
		    		 masterUsers.setLocationCode(conUsers.getUnitCode());
		    	   }
				  if (conUsers.getIpAddress().equalsIgnoreCase(masterUsers.getIpAddress()) == false) {
						masterUsers.setIpAddress(conUsers.getIpAddress());
					}
					if (conUsers.getSrnumber().equalsIgnoreCase(masterUsers.getSrNumber2()) == false) {
						masterUsers.setSrNumber2(conUsers.getSrnumber());
					}
					if (conUsers.getPresent().equalsIgnoreCase(masterUsers.getIsActive()) == false) {
						masterUsers.setIsActive(conUsers.getPresent());
					}
					if(conUsers.getGender().equalsIgnoreCase(masterUsers.getSex())==false)
					{
						masterUsers.setSex(conUsers.getGender());
					}

					masterUserRepository.save(masterUsers);
					
				}
			}
			logger.info("ADDUSERFROMCONCURECIA Cron Ended");
			response.put(Constant.STATUS, 200);
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			return response;
		} catch (Exception e) {
			response.put(Constant.STATUS, 500);
			response.put(Constant.MESSAGE, Constant.FAILED);
			logger.error(" Cron job  exception occured." + e.getMessage());
			logger.info("Cron Job Ended :: ADDUSERFROMCONCURECIA");
			return response;
		}
	}

}
