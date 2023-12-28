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

import com.lic.epgs.mst.entity.TypeOfClaim;
import com.lic.epgs.mst.exceptionhandling.TypeOfClaimServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.TypeOfClaimService;

@RestController
@CrossOrigin("*")
public class TypeOfClaimController {
	@Autowired
	private TypeOfClaimService typeOfClaimService;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(TypeOfClaimController.class);

	@GetMapping("/typeOfClaim")
	 public List<TypeOfClaim> getAllTypeOfClaim() throws ResourceNotFoundException, TypeOfClaimServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			List<TypeOfClaim> cos = typeOfClaimService.getAllTypeOfClaim();
			if (cos.isEmpty()) {
				logger.debug("Getting All Type Of Claim");
				throw new ResourceNotFoundException("Type Of Claim is not found");
			}
			return cos;
		} catch (Exception ex) {
			throw new TypeOfClaimServiceException("Type Of Claim is not found");
		}
	}

	@GetMapping("/typeOfClaimById/{id}")
	public ResponseEntity<TypeOfClaim> getTypeOfClaimById(@PathVariable Long id) throws TypeOfClaimServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new Type Of Claim By Id");
			return ResponseEntity.ok().body(typeOfClaimService.getTypeOfClaimById(id));
		} catch (Exception e) {
			logger.error("Type Of Claim is not found with ID : Invalid Data", e);
			throw new TypeOfClaimServiceException("Type Of Claim is not found with ID" + id);
		}
	}

	@GetMapping("/typeOfClaimByCode/{code}")
	public ResponseEntity<TypeOfClaim> getTypeOfClaimByCode(@PathVariable String code) throws TypeOfClaimServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new Type Of Claim By code");
			return ResponseEntity.ok().body(typeOfClaimService.findByTypeOfClaimCode(code));
		} catch (Exception e) {
			logger.error("Type Of Claim is not found with code : Invalid Data", e);
			throw new TypeOfClaimServiceException("Type Of Claim is not found with code" + code);
		}
	}
}
