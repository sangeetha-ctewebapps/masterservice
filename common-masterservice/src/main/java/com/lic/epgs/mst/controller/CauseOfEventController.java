package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.CauseOfEventEntity;
import com.lic.epgs.mst.entity.ReasonForClaim;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ReasonForClaimException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.CauseOfEventService;

@RestController
@CrossOrigin("*")
public class CauseOfEventController {
	
	@Autowired
	CauseOfEventService causeOfEventService;
	
	private static final Logger logger = LoggerFactory.getLogger(CauseOfEventController.class);
	
	String className = this.getClass().getSimpleName();

	 @GetMapping("/getAllCauseOfEvent")
	public List<CauseOfEventEntity> getAllCauseOfEvent() throws ResourceNotFoundException, ReasonForClaimException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<CauseOfEventEntity> reasonforclaim = causeOfEventService.getAllCauseOfEvent();

			if (reasonforclaim.isEmpty()) {
				logger.debug("inside ReasonForClaim controller getAllReasonForClaim() method");
				logger.info("ReasonForClaim list is empty ");
				throw new ResourceNotFoundException("ReasonForClaim not found");
			}
			return reasonforclaim;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ReasonForClaimException("internal server error");
		}
	}

	@GetMapping("/CauseOfEvent/{causeId}")
	public ResponseEntity<CauseOfEventEntity> getCauseOfEventById(@PathVariable Long causeId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" +causeId );

		return ResponseEntity.ok().body(causeOfEventService.getById(causeId));

	}

	/*
	 * @GetMapping("/CauseOfEventByCode/{causeOfEventCode}") public
	 * ResponseEntity<CauseOfEventEntity> getCauseOfEventByCode(@PathVariable String
	 * causeOfEventCode) { String methodName =
	 * Thread.currentThread().getStackTrace()[1].getMethodName();
	 * LoggingUtil.logInfo(className, methodName, "Started" + causeOfEventCode);
	 * 
	 * return ResponseEntity.ok().body(causeOfEventService.getCauseOfEventByCode(
	 * causeOfEventCode));
	 * 
	 * }
	 */


}
