package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.SpecailClaimReasonEntity;
import com.lic.epgs.mst.exceptionhandling.AddressServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.SpecialClaimService;
	
    @RestController

	@CrossOrigin("*")
    public class SpecialClaimReasonController {
    	
   
		@Autowired
		private SpecialClaimService specialClaimService;
		String className = this.getClass().getSimpleName();

		private static final Logger logger = LoggerFactory.getLogger(BankDetailsController.class);

		 @GetMapping("/getAllSpeciaClaimReason")
		public List<SpecailClaimReasonEntity> getAllSpeciaClaimReason() throws ResourceNotFoundException, AddressServiceException {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LoggingUtil.logInfo(className, methodName, "Started");
			try {
				List<SpecailClaimReasonEntity> SCRDetails = specialClaimService.getAllDetails();

				if (SCRDetails.isEmpty()) {
					logger.debug("Inside SpecialClaimReasonController getAllDetails() method");
					logger.info("Special Claim Reason list is empty ");
					throw new ResourceNotFoundException("SpecialClaimReason  not found");
				}
				return SCRDetails;
			} catch (Exception ex) {
				logger.error("Internal Server Error");
				throw new AddressServiceException("internal server error");
			}
	

		 }
		
    }
