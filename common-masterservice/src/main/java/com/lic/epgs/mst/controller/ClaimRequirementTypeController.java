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

import com.lic.epgs.mst.entity.ClaimRequirementType;
import com.lic.epgs.mst.exceptionhandling.ClaimRequirementTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.ClaimRequirementTypeService;

@RestController
@CrossOrigin("*")
public class ClaimRequirementTypeController {

	@Autowired
	private ClaimRequirementTypeService claimService;

	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(ClaimRequirementTypeController.class);

	 @GetMapping("/claimReq")
	public List<ClaimRequirementType> getAllClaimReqTypes()
			throws ResourceNotFoundException, ClaimRequirementTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ClaimRequirementType> claims = claimService.getAllClaim();

			if (claims.isEmpty()) {
				logger.debug("inside claim requirement type Controller getAllClaim() method");
				logger.info("claim requirement type list is empty ");
				throw new ResourceNotFoundException("claim requirement type not found");
			}
			return claims;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ClaimRequirementTypeServiceException("internal server error");
		}
	}

	@GetMapping("/claimReq/{id}")
	public ResponseEntity<ClaimRequirementType> getClaimById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("ClaimRequirementType search by id");
		return ResponseEntity.ok().body(claimService.getClaimById(id));

	}

	@GetMapping("/claimReq/code/{code}")
	public ResponseEntity<ClaimRequirementType> getClaimByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("ClaimRequirementType search by Code");
		return ResponseEntity.ok().body(claimService.getClaimByCode(code)); // getCustomerByCode(code));

	}

}
