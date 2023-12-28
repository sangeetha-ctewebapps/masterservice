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

import com.lic.epgs.mst.entity.ReasonForClaim;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ReasonForClaimException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.ReasonForClaimService;

@RestController
@CrossOrigin("*")
public class ReasonForClaimController {

	private static final Logger logger = LoggerFactory.getLogger(ReasonForClaimController.class);

	@Autowired
	private ReasonForClaimService reasonforclaimService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/ReasonForClaim")
	public List<ReasonForClaim> getAllReasonForClaim() throws ResourceNotFoundException, ReasonForClaimException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ReasonForClaim> reasonforclaim = reasonforclaimService.getAllReasonForClaim();

			if (reasonforclaim.isEmpty()) {
				logger.debug("inside ReasonForClaim controller getAllReasonForClaim() method");
				logger.info("ReasonForClaim list is empty ");
				throw new ResourceNotFoundException("ReasonForClaim not found");
			}
			return reasonforclaim;
		} catch (Exception ex) {
			logger.info("Internal Server Error");
			throw new ReasonForClaimException("internal server error");
		}
	}

	@GetMapping("/ReasonForClaim/{id}")
	public ResponseEntity<ReasonForClaim> getReasonForClaimById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(reasonforclaimService.getById(id));

	}

	@GetMapping("/ReasonForClaimByCode/{code}")
	public ResponseEntity<ReasonForClaim> getReasonForClaimByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(reasonforclaimService.getReasonForClaimByCode(code));

	}

}
