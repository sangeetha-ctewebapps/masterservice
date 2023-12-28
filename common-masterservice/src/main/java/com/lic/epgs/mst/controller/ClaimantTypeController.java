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

import com.lic.epgs.mst.entity.ClaimantType;
import com.lic.epgs.mst.exceptionhandling.ClaimantTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.ClaimantTypeService;

@RestController
@CrossOrigin("*")
public class ClaimantTypeController {

	@Autowired
	private ClaimantTypeService claimantService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ClaimantTypeController.class);

	@GetMapping("/claimants")
	public List<ClaimantType> getAllClaimant() throws ResourceNotFoundException, ClaimantTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		 try {
			List<ClaimantType> claimant = claimantService.getAllClaimant();

			if (claimant.isEmpty()) {
				logger.debug("inside Claimant Type Controller getAllClaimant() method");
				logger.info("claimant type list is empty ");
				throw new ResourceNotFoundException("claimant type is not found");
			}
			return claimant;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ClaimantTypeServiceException("internal server error");
		}
	}

	@GetMapping("/claimants/{id}")
	public ResponseEntity<ClaimantType> getClaimantById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Claimant search by id");
		return ResponseEntity.ok().body(claimantService.getClaimantById(id));

	}

	@GetMapping("/claimants/code/{code}")
	public ResponseEntity<ClaimantType> getClaimantByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("ClaimantType search by code");
		return ResponseEntity.ok().body(claimantService.getClaimantByCode(code));
	}

}
